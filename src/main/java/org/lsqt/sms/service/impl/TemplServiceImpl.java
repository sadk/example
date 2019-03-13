package org.lsqt.sms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.sms.model.Remark;
import org.lsqt.sms.model.Request;
import org.lsqt.sms.model.Schedule;
import org.lsqt.sms.model.Templ;
import org.lsqt.sms.model.TemplQuery;
import org.lsqt.sms.service.TemplService;
import org.lsqt.sms.util.ConfigPropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
@Service
public class TemplServiceImpl implements TemplService {

	private static final Logger  log = LoggerFactory.getLogger(TemplServiceImpl.class);
	private String adv = ConfigPropertiesUtil.getValue("api.sms.channel.tencent.adv"); //
	private String src = ConfigPropertiesUtil.getValue("api.sms.channel.tencent.src");
	@Inject private Db db;
	
	@Override
	public Page<Templ> queryForPage(TemplQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Templ.class, query);
	}

//	@Override
//	public Templ saveOrUpdate(Templ model) {
//		return db.saveOrUpdate(model);
//	}
	
	@Override
	public String saveOrUpdate(Templ model) {
		if(model.getTemplId()==null) {
			//向腾讯云发送短信文案备案请求获取文案id
			
			JSONObject jo=getTemplId(model);
			
			if(jo.containsKey("Error")) {
				return jo.getJSONObject("Error").getString("Message");
			}
			if(!"0".equals(jo.getJSONObject("data").getString("ret"))){
				return jo.getJSONObject("data").getString("msg");
			}
			model.setTemplId(jo.getJSONObject("data").getString("data"));
			model.setTemplStatus("1");
			db.executeUpdate("insert into sms_templ_info (templ_id,templ_name,templ_content,sign_id,launch_time,launch_num) values (?,?,?,?,?,?)", model.getTemplId(),model.getTemplName(),model.getTemplContent(),model.getSignId(),model.getLaunchTime(),model.getLaunchNum());
			return null;
		}else {
			//向腾讯云发送状态更新请求获取文案状态
			JSONObject jo=getTemplStatus(model);
			if(jo.containsKey("Error")) {
				return jo.getJSONObject("Error").getString("Message");
			}
			jo=jo.getJSONObject("data");
			if(jo.getIntValue("ret")==0) {
				JSONObject result=(JSONObject) jo.getJSONArray("data").get(0);
				model.setTemplStatus(result.getString("status"));
				model.setReply(result.getString("reply"));
			}else {
				return jo.getString("msg");
			}
			db.executeUpdate("update sms_templ_info set templ_status=?,reply=?,update_time=now() where templ_id = ?", model.getTemplStatus(),model.getReply(),model.getTemplId());
			return null;
		}
		
	}
	
	public  JSONObject getTemplId(Templ model) {
		Map<String, Object> params = new HashMap<>();

		Remark remark=new Remark();
		remark.setSign(model.getSignName());
		remark.setNum(model.getLaunchNum());
		List<Schedule> list=new ArrayList<Schedule>();
		Schedule sche=new Schedule();
		sche.setSche_num(model.getLaunchNum());
		sche.setSche_time(model.getLaunchTime());
		list.add(sche);
		remark.setSche(list);
		params.put("adv",adv);
		params.put("src",src);
		params.put("name", model.getTemplName());
		params.put("content", model.getTemplContent());
		params.put("remark", remark);
		Request req = Request.newInstance();
		String json = req.api("/query").serivceId("sms-create-templ").params(params).execute();
		System.out.println("创建文案结果：============="+json);
		log.info("查询文案状态结果：============="+json);
		return ((JSONObject) JSONObject.parse(json)).getJSONObject("Response");
	}
	
	public static JSONObject getTemplStatus(Templ model) {
		Map<String, Object> params = new HashMap<>();
		long[] arr=new long[1];
		arr[0]=Long.parseLong(model.getTemplId());
		params.put("templ_id", arr);
		Request req = Request.newInstance();
		String json = req.api("/query").serivceId("sms-query-templ").params(params).execute();
		System.out.println("查询文案状态结果：============="+json);
		log.info("查询文案状态结果：============="+json);
		return ((JSONObject) JSONObject.parse(json)).getJSONObject("Response");
	}

	@Override
	public String delete(Long[] ids, Long[] templIds) {
		Map<String, Object> params = new HashMap<>();
		params.put("templ_id", templIds);
		Request req = Request.newInstance();
		String json = req.api("/query").serivceId("sms-delete-templ").params(params).execute();
		System.out.println("查询文案状态结果：============="+json);
		log.info("查询文案状态结果：============="+json);
		JSONObject jo=((JSONObject) JSONObject.parse(json)).getJSONObject("Response");
		if(jo.containsKey("Error")) {
			return jo.getJSONObject("Error").getString("Message");
		}
		jo=jo.getJSONObject("data");
		if(jo.getIntValue("ret")==0) {

			db.deleteById(Templ.class, Arrays.asList(ids).toArray());
		}else {
			return jo.getString("msg");
		}
		return null;
	}


	


}
