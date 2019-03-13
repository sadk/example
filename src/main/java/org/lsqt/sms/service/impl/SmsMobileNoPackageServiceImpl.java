package org.lsqt.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sms.util.CommonUtils;
import org.lsqt.sms.util.ConfigPropertiesUtil;
import org.lsqt.sms.model.Request;
import org.lsqt.sms.model.SmsMobileNoPackage;
import org.lsqt.sms.model.SmsMobileNoPackageQuery;
import org.lsqt.sms.service.SmsMobileNoPackageService;

import java.io.File;
import java.util.*;

@Service
public class SmsMobileNoPackageServiceImpl implements SmsMobileNoPackageService {

	private String adv = ConfigPropertiesUtil.getValue("api.sms.channel.tencent.adv");
	private String src = ConfigPropertiesUtil.getValue("api.sms.channel.tencent.src");


	@Inject
    private Db db;
	
	public List<SmsMobileNoPackage>  queryForList(SmsMobileNoPackageQuery query) {
		return db.queryForList("queryForPage",SmsMobileNoPackage.class, query);
	}
	
	public Page<SmsMobileNoPackage> queryForPage(SmsMobileNoPackageQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), SmsMobileNoPackage.class, query);
	}

	public List<SmsMobileNoPackage> getAll(){
		  return db.queryForList("getAll", SmsMobileNoPackage.class);
	}
	
	public SmsMobileNoPackage saveOrUpdate(SmsMobileNoPackage model) {
		return db.saveOrUpdate(model);
	}

	public String deleteById(Long ... ids) {
		if (ids == null || ids.length == 0) {
			return "请传入id";
		}

		Object[] idArray = Arrays.asList(ids).toArray();
		Map<String, Object> params = new HashMap<>();
		params.put("package_id", idArray);

		JSONObject jsbContent = CommonUtils.sendRequest("/query", "sms-delete-crowd", params);
		JSONObject response = jsbContent.getJSONObject("Response");
		if (response.containsKey("Error")) {
			return response.getJSONObject("Error").getString("Message");
		}
		JSONObject jsbData = response.getJSONObject("data");
		if (jsbData.getIntValue("ret") == 0) {
			JSONArray jsonArray = jsbData.getJSONArray("data");
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			int status = jsonObject.getIntValue("status");
			int packageId = jsonObject.getIntValue("package_id");
			//db.deleteById(SmsMobileNoPackage.class, idArray);
			db.executeUpdate("update sms_mobile_no_package set package_status = ? where package_no = ?", status, packageId);
		}

		return jsbData.getString("msg");
	}


	public String uploadMobilePackage(File file,int lineNums) {

		// 号码包上传
		Map<String, Object> params = new HashMap<>();
		params.put("adv", adv);
		params.put("src", src);
		params.put("data_type", 2);
		params.put("is_md5", 0);
		Request req = Request.newInstance();
		String content = req.api("/data/store").serivceId("sms-data-upload").file(file).params(params).execute();
		JSONObject jsbContent = JSON.parseObject(content);
		JSONObject jsbData = jsbContent.getJSONObject("Response").getJSONObject("data");
		String msg = jsbData.getString("msg");
		int packageId = 0;
		//如果msg为空说明请求成功
		if(StringUtil.isBlank(msg)){
			//号码包ID
			packageId = jsbData.getIntValue("data");
			System.out.println(packageId);
		}else {
			return msg;
		}
		String packageName = "";
		int pot = file.getName().lastIndexOf(".");
		if (pot != -1) {
			packageName = file.getName().substring(0, pot);
		}
		String packageNo = String.valueOf(packageId);
		String packageStatus = "0";//提取中
		SmsMobileNoPackage smsMobileNoPackage = new SmsMobileNoPackage();
		smsMobileNoPackage.setPackageName(packageName);
		smsMobileNoPackage.setPackageNo(packageNo);
		smsMobileNoPackage.setPackageStatus(packageStatus);
		smsMobileNoPackage.setCreateTime(new Date());
		smsMobileNoPackage.setUpdateTime(new Date());

		smsMobileNoPackage.setLineNum((long) lineNums);
		db.saveOrUpdate(smsMobileNoPackage);
		return null;
		}

	@Override
	public String updatePackageStatus(Long id) {

		SmsMobileNoPackageQuery query = new SmsMobileNoPackageQuery();
		query.setIds(String.valueOf(id));
		SmsMobileNoPackage smsPackage = db.queryForObject("queryForPage",SmsMobileNoPackage.class,query);
		if(smsPackage==null){
			return "号码包信息不存在！";
		}
		String packageNo = smsPackage.getPackageNo();

		Map<String, Object> params = new HashMap<>();
		params.put("package_id", Arrays.asList(packageNo));
		Request req = Request.newInstance();
		String content = req.api("/query").serivceId("sms-query-crowd").params(params).execute();
		System.out.println("查询号码包上传状态返回:"+content);
		JSONObject jsbContent = JSON.parseObject(content);

		JSONObject errorObj = jsbContent.getJSONObject("Response").getJSONObject("Error");
		if(errorObj!=null){
			String message = errorObj.getString("Message");
			return message;
		}
		JSONObject jsbData = jsbContent.getJSONObject("Response").getJSONObject("data");
		String msg = jsbData.getString("msg");
		int num = 0;
		int status = 0;
		if(StringUtil.isBlank(msg)){
			JSONArray arrayData = jsbData.getJSONArray("data");
			JSONObject arrayObj = arrayData.getJSONObject(0);
			num = arrayObj.getIntValue("num");
			status = arrayObj.getIntValue("status");
			System.out.println("上传包号："+packageNo+" 上传号码个数:"+num+" 上传包状态:"+status);
			smsPackage.setPackageCounts(new Long((long)num));
			smsPackage.setPackageStatus(String.valueOf(status));
			smsPackage.setUpdateTime(new Date());
			db.update(smsPackage,"packageCounts","packageStatus","updateTime");
		}

		return null;
	}
}
