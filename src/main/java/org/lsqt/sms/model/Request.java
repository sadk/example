package org.lsqt.sms.model;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.lsqt.sms.util.ConfigPropertiesUtil;
import org.lsqt.sms.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 腾讯公共参数
 * @author mm
 *
 */
public class Request {
	private static final Logger log = LoggerFactory.getLogger(Request.class);
	
	private String url = ConfigPropertiesUtil.getValue("api.sms.channel.tencent.url");
	private String appKey = ConfigPropertiesUtil.getValue("api.sms.channel.tencent.appkey");
	private String appId = ConfigPropertiesUtil.getValue("api.sms.channel.tencent.appid"); // 应用id，服务调用方的唯一标志
	
	private long timestamp = System.currentTimeMillis(); // 时间戳，取当前时间，精确到毫秒
		private Map<String,Object> params; // 业务参数，不同接口有不同的格式，可空
		private File file; // 业务参数，上传的文件，可空
		private String serviceId; // 服务id，服务的唯一标志
		private String sign; // 签名，AppKey+Timestamp+ServiceId+Params 连接的字符串做md5。

		private Map<String,Object> remark = new LinkedHashMap<>(); // 业务参数，不同接口有不同的格式，可空

	private Request() {

	}

	public static Request newInstance() {
		return new Request();
	}

	public Request api(String api) {
		if (api.startsWith("/")) {
			this.url = this.url.concat(api);
		} else {
			this.url = this.url.concat("/").concat(api);
		}

		return this;
	}

	public Request serivceId(String serviceId) {
		this.serviceId = serviceId;
		return this;
	}

	public Request file(File file) {
		this.file = file;
		return this;
	}


	public Request params(Map<String,Object> params) {
		this.params = params;
		return this;
	}
	
	/**	
	public Request paramsPut(String key,Object value) {
		this.params.put(key, value);
		return this;
	}
	*/
	
	/**
	 * 执行请求
	 * @return
	 */
	public String execute(String...returnCode) {
		final String paramJSON = JSON.toJSONString(params);
		final String sign = MD5Util.MD5Encode(appKey+timestamp+serviceId+paramJSON,"UTF-8");

		Map<String,Object> debugResult = new LinkedHashMap<>();
		debugResult.put("AppId",appId);
		debugResult.put("File",file);
		debugResult.put("Timestamp",timestamp);
		debugResult.put("Params",params);
		debugResult.put("ServiceId",this.serviceId);
		debugResult.put("Sign",sign);
		
		log.info("发送请求:\n"+JSON.toJSONString(debugResult, true));
		
		
		String content = null;

		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpPost httppost = new HttpPost(url);

			MultipartEntityBuilder meb = MultipartEntityBuilder.create();
			meb.addPart("AppId", new StringBody(appId, ContentType.TEXT_PLAIN));

			if (this.file != null) {
				FileBody f = new FileBody(file);
				meb.addPart("File", f);
			}
			meb.addPart("Timestamp", new StringBody(timestamp + "", ContentType.TEXT_PLAIN));
			meb.addPart("Params", new StringBody(paramJSON, ContentType.APPLICATION_JSON)); // 这个要转json字符串吗？
			meb.addPart("ServiceId", new StringBody(serviceId, ContentType.TEXT_PLAIN));
			meb.addPart("Sign", new StringBody(sign, ContentType.TEXT_PLAIN));

			HttpEntity reqEntity = meb.build();
			httppost.setEntity(reqEntity);

			try (CloseableHttpResponse response = httpclient.execute(httppost)) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					if(returnCode!=null && returnCode.length > 0){
						content = EntityUtils.toString(response.getEntity(), returnCode[0]);
					}else{
						content = EntityUtils.toString(response.getEntity(), "UTF-8");
					}
					log.info("响应请求:\n"+content);
				} else {
					log.info("无响应");
				}
				EntityUtils.consume(resEntity);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return content;
	}
	
	public static void main(String[] args) {

		// 1.号码包上传API
		/*
		Map<String, Object> params = new HashMap<>();
		params.put("adv", "100007265092");
		params.put("src", "marketing_cloud_test");
		params.put("data_type", 2);
		params.put("is_md5", 0);

		File file = new File("E:\\newCrmProject\\new_crm_platform\\src\\main\\java\\org\\lsqt\\sms\\model\\mobile.txt");
		Request req = Request.newInstance();
		String content = req.api("/data/store").serivceId("sms-data-upload").file(file).params(params).execute();
		System.out.println("号码包上传返回:"+content);
		JSONObject jsbContent = JSON.parseObject(content);
		JSONObject jsbData = jsbContent.getJSONObject("Response").getJSONObject("data");
		String msg = jsbData.getString("msg");
		if(StringUtil.isBlank(msg)){
			int packageId = jsbData.getIntValue("data");
			System.out.println(packageId);
		}

*/
		// 2.人群规模&状态cgi

//		Map<String, Object> params = new HashMap<>();
//		params.put("package_id", Arrays.asList(1771));
//		Request req = Request.newInstance();
//		String content = req.api("/query").serivceId("sms-query-crowd").params(params).execute();
//		System.out.println("查询号码包上传状态返回:"+content);
//		JSONObject jsbContent = JSON.parseObject(content);
//		JSONObject jsbData = jsbContent.getJSONObject("Response").getJSONObject("data");
//		String msg = jsbData.getString("msg");
//		int num = 0;
//		int status = 0;
//		int packageNo = 0;
//		if(StringUtil.isBlank(msg)){
//			JSONArray arrayData = jsbData.getJSONArray("data");
//			JSONObject arrayObj = arrayData.getJSONObject(0);
//			num = arrayObj.getIntValue("num");
//			status = arrayObj.getIntValue("status");
//			packageNo = arrayObj.getIntValue("package_id");
//			System.out.println("上传包号："+packageNo+" 上传号码个数:"+num+" 上传包状态:"+status);
//		}



		//3.创建短信签名
//		Map<String, Object> params = new HashMap<>();
//		params.put("adv", "100007265092");
//		params.put("src", "marketing_cloud_test");
//		params.put("name", "点金");
//		Request req = Request.newInstance();
//		String content = req.api("/query").serivceId("sms-create-sign").params(params).execute();
//		System.out.println("创建短信签名返回:"+content);

		//4.签名状态查询
//		Map<String, Object> params = new HashMap<>();
//		params.put("sign_id", Arrays.asList(169849));
//
//		Request req = Request.newInstance();
//		String content = req.api("/query").serivceId("sms-query-sign").params(params).execute();
//
//		System.out.println("签名状态查询返回:"+content);
		

//		//5.文案创建
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("adv", "100007265092");
//		params.put("src", "marketing_cloud_test");
//		params.put("name", "点金");
//		params.put("content", "恭喜您获得了288元少儿英语外教1对1新生体验课！点击领取 https://activity.vipkid.com.cn/activity/regSuccess?channel_id=6449&channel_keyword=20180911b2 回T退订");
//		params.put("remark", "这是一个备注");
//
//		Map<String,Object> remark = new HashMap<>();
//		Map<String,Object> item1 = new HashMap<>();
//		Map<String,Object> item2 = new HashMap<>();
//
//		item1.put("sche_time", "2018-09-11 15:30");
//		item1.put("sche_num", 50000);
//
//		item2.put("sche_time", "2018-09-12 15:00");
//		item2.put("sche_num", 50000);
//
//		remark.put("sign", "vipkid");
//		remark.put("num", 10000);
//		remark.put("sche", Arrays.asList(item1,item2));
//
//		params.put("remark", remark);
//		Request req = Request.newInstance();
//		String content = req.api("/query").serivceId("sms-create-templ").params(params).execute();
//


		//3.创建投放
//		Map<String, Object> params = new HashMap<>();
//		Map<String, Object> scheMap = new HashMap<>();
//		scheMap.put("num",1);
//		scheMap.put("bgn_time",201811261500L);
//		params.put("adv", "100007265092");
//		params.put("src", "marketing_cloud_test");
//		params.put("package_id", 1771);
//		params.put("sign_id", 169849);
//		params.put("templ_id", 169850);
//		params.put("sche", Arrays.asList(scheMap));
//
//		Request req = Request.newInstance();
//		String content = req.api("/query").serivceId("sms-create-advertise").params(params).execute();
//		System.out.println("创建投放返回:"+content);
//		JSONObject jsbContent = JSON.parseObject(content);
//		JSONObject jsbData = jsbContent.getJSONObject("Response").getJSONObject("data");
//		String msg = jsbData.getString("msg");
//		if(StringUtil.isBlank(msg)){
//			int launchId = jsbData.getIntValue("data");
//			System.out.println(launchId);
//		}


		//4.投放状态查询
//		Map<String, Object> params = new HashMap<>();
//		params.put("launch_id", Arrays.asList(169849));
//
//		Request req = Request.newInstance();
//		String content = req.api("/query").serivceId("sms-query-sign").params(params).execute();
//
//		System.out.println("签名状态查询返回:"+content);
//
		Map<String, Object> params = new HashMap<>();
		params.put("launch_id", Arrays.asList(867));
		Request req = Request.newInstance();
		String content = req.api("/data/get").serivceId("sms-data-get").params(params).execute("GBK");
		System.out.println("下载回执单返回:"+content);

	}
}

