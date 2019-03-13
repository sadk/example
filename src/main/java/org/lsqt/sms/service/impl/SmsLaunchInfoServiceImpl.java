package org.lsqt.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sms.model.*;
import org.lsqt.sms.service.SmsLaunchInfoService;
import org.lsqt.sms.util.CommonUtils;
import org.lsqt.sms.util.ConfigPropertiesUtil;
import org.lsqt.sms.util.DateUtil;
import org.lsqt.sms.util.ExcelExportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class SmsLaunchInfoServiceImpl implements SmsLaunchInfoService {

	private static final Logger log = LoggerFactory.getLogger(SmsLaunchInfoServiceImpl.class);

	@Inject
    private Db db;
	private String adv = ConfigPropertiesUtil.getValue("api.sms.channel.tencent.adv");
	private String src = ConfigPropertiesUtil.getValue("api.sms.channel.tencent.src");
	private String launchTimeSecond = ConfigPropertiesUtil.getValue("api.sms.channel.tencent.launch.time.second");


	public List<SmsLaunchInfo>  queryForList(SmsLaunchInfoQuery query) {
		return db.queryForList("queryForPage",SmsLaunchInfo.class, query);
	}
	
	public Page<SmsLaunchInfo> queryForPage(SmsLaunchInfoQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), SmsLaunchInfo.class, query);
	}

	public List<SmsLaunchInfo> getAll(){
		  return db.queryForList("getAll", SmsLaunchInfo.class);
	}

	@Override
	public String updateLaunchStatus(Long id) {
		Map<String, Object> params = new HashMap<>();
		Long launchId = db.executeQueryForObject("select launch_id from sms_launch_info where id = ?", Long.class, id);
		if (launchId == null || launchId == 0L) {
			return "当前记录无投放ID";
		}
		params.put("launch_id", Arrays.asList(launchId));

		Request req = Request.newInstance();
		String content = req.api("/query").serivceId("sms-query-advertise").params(params).execute();
		log.debug("创建投放返回: {0}", content);
		JSONObject jsbContent = JSON.parseObject(content);
		JSONObject response = jsbContent.getJSONObject("Response");
		if (response.containsKey("Error")) {
			return response.getJSONObject("Error").getString("Message");
		}
		JSONObject jsbData = response.getJSONObject("data");
		if (jsbData.getIntValue("ret") == 0) {
			JSONArray dataArray = jsbData.getJSONArray("data");
			if (dataArray != null && dataArray.size() > 0) {
				JSONObject data = dataArray.getJSONObject(0);
				int backEndStatus = data.getIntValue("status");
				JSONArray scheArray = data.getJSONArray("sche");

				if (scheArray.size() > 0) {
					JSONObject scheData = scheArray.getJSONObject(0);
					int sendStatus = scheData.getIntValue("status");
					String sendMsg = scheData.getString("msg");
					long launchBgnTime = scheData.getLongValue("launch_bgn_time");
					long launchEndTime = scheData.getLongValue("launch_end_time");
					int succNum = scheData.getIntValue("succ_num");
					int failNum = scheData.getIntValue("fail_num");
					int recvNum = scheData.getIntValue("recv_num");

					Date launchBgnDate = null;
					Date launchEndDate = null;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
					try {
						if (launchBgnTime > 0L) {
							launchBgnDate = sdf.parse(String.valueOf(launchBgnTime));
						}
						if (launchEndTime > 0L) {
							launchEndDate = sdf.parse(String.valueOf(launchEndTime));
						}
					} catch (ParseException e) {
						return "接口时间格式错误";
					}
					db.executeUpdate("update sms_launch_info set launch_status = ?, send_status = ?, send_msg = ?, launch_bgn_time = ?, "
							+ "launch_end_time = ?, succ_num = ?, fail_num = ?, recv_num = ?, update_time = now() "
							+ "where id = ?", backEndStatus, sendStatus, sendMsg, launchBgnDate, launchEndDate, succNum, failNum, recvNum, id);

				} else {

					db.executeUpdate("update sms_launch_info set launch_status = ?, update_time = now() "
							+ "where id = ?", backEndStatus, id);
				}

			} else {
				return "更新失败";
			}
		}
		return jsbData.getString("msg");
	}

	/**
	 * 获取投放回执详情
	 *
	 * @param launchId
	 * @return
	 */
	@Override
	public String getSmsData(Integer launchId) {

		Map<String, Object> params = new HashMap<>();
		params.put("launch_id", Arrays.asList(launchId));
		Request req = Request.newInstance();
		String content = req.api("/data/get").serivceId("sms-data-get").params(params).execute("GBK");
		return content;
	}

	@Override
	public String checkBeforeSave(SmsLaunchInfo form) {
		Date bgnDateTime = form.getBgnTime();
		long maxIntervalSeconds = launchTimeSecond == null ? 600 : Long.valueOf(launchTimeSecond);

		long bgnTimeMiliseconds = bgnDateTime.getTime();
		long currentMiliseconds = System.currentTimeMillis();
		long gap = (bgnTimeMiliseconds - currentMiliseconds) / 1000;

		if (gap <= 0) {
			return "-1";
		}

		if (gap <= maxIntervalSeconds) {
			return "1";
		}

		return null;
	}

	public String saveOrUpdate(SmsLaunchInfo model) {

		Long num = model.getNum();
		String bgnTimeStr = DateUtil.formatDate(model.getBgnTime(),null);
		Long bgnTime = Long.valueOf(bgnTimeStr);
		int packageId = Integer.parseInt(model.getPackageId());
		Long signId = model.getSignId();
		Long templId = model.getTemplId();

		List<Map<String, Object>> queryData = db.executeQuery("select launch_num, launch_time from sms_templ_info where templ_id = ? and templ_status = 0 ", templId);

		if (queryData == null || queryData.size() == 0) {
			return "未查询到对应文案配置";
		}

		Object templLauchNum = queryData.get(0).get("launch_num");
		if (templLauchNum == null) {
			return "文案未配置投放数目";
		} else if (!num.equals((Long) templLauchNum)) {
			return "文案配置的投放数目与当前投放数目不一致";
		}

		Object lauchTime = queryData.get(0).get("launch_time");
		if (lauchTime == null) {
			return "文案未配置投放时间";
		}

		Map<String, Object> params = new HashMap<>();
		Map<String, Object> scheMap = new HashMap<>();
		scheMap.put("num",num);
		scheMap.put("bgn_time",bgnTime);
		params.put("adv", adv);
		params.put("src", src);
		params.put("package_id", packageId);
		params.put("sign_id", signId);
		params.put("templ_id", templId);
		params.put("sche", Arrays.asList(scheMap));

		Request req = Request.newInstance();
		String content = req.api("/query").serivceId("sms-create-advertise").params(params).execute();
		log.debug("创建投放返回: {0}", content);
		JSONObject jsbContent = JSON.parseObject(content);
		JSONObject response = jsbContent.getJSONObject("Response");
		if (response.containsKey("Error")) {
			return response.getJSONObject("Error").getString("Message");
		}
		JSONObject jsbData = response.getJSONObject("data");
		if (jsbData.getIntValue("ret") == 0) {
			int launchId = jsbData.getIntValue("data");
			log.debug("launchId = {0}", launchId);
			model.setLaunchId(String.valueOf(launchId));
			//db.saveOrUpdate(model);
			db.executeUpdate("insert into sms_launch_info " +
							"(launch_id, package_id, sign_id, templ_id, num, bgn_time, create_time) " +
							"VALUES (?, ?, ?, ?, ?, ?, now()) ",
					model.getLaunchId(), model.getPackageId(), model.getSignId(), model.getTemplId(),
					model.getNum(), model.getBgnTime()
			);
			db.executeUpdate("update sms_signature_info set use_status = ?, update_time = now() where signature_id = ? ", 1, signId);
			db.executeUpdate("update sms_templ_info set use_status = ?, update_time = now() where templ_id = ? ", 1, templId);
		}
		return jsbData.getString("msg");
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(SmsLaunchInfo.class, Arrays.asList(ids).toArray());
	}

	public String cancelLaunch(Long launchId) {
		if (launchId == null) {
			return "请传入投放id";
		}

		Map<String, Object> params = new HashMap<>();
		params.put("adv", adv);
		params.put("src", src);
		params.put("launch_id", launchId);

		JSONObject jsonObject = CommonUtils.sendRequest("/query", "sms-cancel-advertise", params);
		JSONObject response = jsonObject.getJSONObject("Response");
		if(response.containsKey("Error")) {
			return response.getJSONObject("Error").getString("Message");
		}
		JSONObject data = response.getJSONObject("data");
		if (data.getIntValue("ret") == 0) {
			db.executeUpdate("update sms_launch_info set launch_status = ?, update_time = now() "
					+ "where launch_id = ?", -1, launchId);
			// -1 表示取消投放
		}

		return data.getString("msg");
	}

	@Override
	public Result<Map<String, Object>> getMarketingDataFilePath(Long[] launchIds, String bgnTime, String endTime) {
		ExcelExportUtils<LaunchMarketingInfo> et = new ExcelExportUtils<>();
		HttpServletRequest req = ContextUtil.getRequest();
		File fileSavePath = new File(req.getServletContext().getRealPath("/") + "/download");
		if (!fileSavePath.exists()) {
			fileSavePath.mkdir();
		}

		File marketingFileForDownload = new File(fileSavePath, "marketing_data" + "_" + System.currentTimeMillis() + ".xlsx");

		Result<List<LaunchMarketingInfo>> queryResult = this.queryMarketingData(launchIds, bgnTime, endTime);
		try (FileOutputStream fos = new FileOutputStream(marketingFileForDownload)) {
			String[] headers = {"投放ID", "投放成功的数量", "接收成功的数量", "PV", "UV", "开始时间", "结束时间"};
			et.excelDowmForData("营销效果导出", headers, queryResult.getData(), fos, 0);
			return Result.ok(marketingFileForDownload.getAbsolutePath());
		} catch (Exception ioe) {
			log.debug(ioe.getMessage());
			return Result.fail("生成文件失败, 错误信息: " + queryResult.getDesc());
		}
	}

	@Override
	public Result<List<LaunchMarketingInfo>> queryMarketingData(Long[] launchIds, String bgnTime, String endTime) {
		if (launchIds == null || launchIds.length == 0) {
			Result.fail("投放id不可为空");
		}

		if (StringUtil.isBlank(bgnTime)) {
			Result.fail("开始日期不可为空");
		}

		if (StringUtil.isBlank(endTime)) {
			Result.fail("结束日期不可为空");
		}

		int intBgnTime = Integer.valueOf(bgnTime.replaceAll("[[\\s-:punct:]]",""));
		int intEndTime = Integer.valueOf(endTime.replaceAll("[[\\s-:punct:]]",""));

		Map<String, Object> params = new HashMap<>();
		List<Map<String, Object>> launchs = new ArrayList<>();
		for (Long launchId : launchIds) {
			Map<String, Object> launch = new HashMap<>();
			launch.put("launch_id", launchId);
			launch.put("bgn_time", intBgnTime);
			launch.put("end_time", intEndTime);
			launchs.add(launch);
		}
		params.put("launch", launchs);

		JSONObject jsonObject = CommonUtils.sendRequest("/query", "sms-query-statistic", params);
		JSONObject response = jsonObject.getJSONObject("Response");
		if(response.containsKey("Error")) {
			return Result.fail(response.getJSONObject("Error").getString("Message"));
		}

		JSONObject data = response.getJSONObject("data");
		if (data.getIntValue("ret") == 0) {
			JSONArray jsonArray = data.getJSONArray("data");
			List<LaunchMarketingInfo> marketingInfos = new ArrayList<>();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject marketingData = jsonArray.getJSONObject(i);
				int responseLaunchId = marketingData.getIntValue("launch_id");
				int recvSucc = marketingData.getIntValue("recv_succ");
				int sendSucc = marketingData.getIntValue("send_succ");
				JSONArray detailArray = marketingData.getJSONArray("detail");

				if (detailArray.size() == 0) {
					marketingInfos.add(new LaunchMarketingInfo().
							setLaunchId(CommonUtils.valueOf(responseLaunchId))
							.setRecvSuccNum(CommonUtils.valueOf(recvSucc))
							.setSendSuccNum(CommonUtils.valueOf(sendSucc))
							.setBgnTime(CommonUtils.valueOf(bgnTime))
							.setEndTime(CommonUtils.valueOf(endTime))
					);
				} else {
					for (int y = 0; y < detailArray.size(); y++) {
						JSONObject detailObject = detailArray.getJSONObject(y);
						int pv = detailObject.getIntValue("pv");
						int uv = detailObject.getIntValue("uv");
						int bgnTimeResult = detailObject.getIntValue("bgn_time");
						int endTimeResult = detailObject.getIntValue("end_time");

						LaunchMarketingInfo marketingInfo = new LaunchMarketingInfo();
						marketingInfo.setLaunchId(CommonUtils.valueOf(responseLaunchId))
								.setRecvSuccNum(CommonUtils.valueOf(recvSucc))
								.setSendSuccNum(CommonUtils.valueOf(sendSucc))
								.setPv(CommonUtils.valueOf(pv))
								.setUv(CommonUtils.valueOf(uv))
								.setBgnTime(CommonUtils.valueOf(bgnTimeResult))
								.setEndTime(CommonUtils.valueOf(endTimeResult));
						marketingInfos.add(marketingInfo);
					}
				}

			}

			return Result.ok(marketingInfos, null);
		}

		return Result.fail(Collections.EMPTY_LIST, data.getString("msg"));
	}
}
