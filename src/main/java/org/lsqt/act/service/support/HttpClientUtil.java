package org.lsqt.act.service.support;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.lsqt.components.util.collection.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class HttpClientUtil {
	private static final Logger  log = LoggerFactory.getLogger(HttpClientUtil.class);
	
	/**
	 * 执行Post请求
	 * 
	 * @param url 
	 * @param params 多个请求参数: NameValuePair param1 = new BasicNameValuePair("name","张三");
	 * @return
	 */
	public static String doPost(String url, List<NameValuePair> params) { 
		String body = null;
		try {
			log.debug("doPost ==> " + url);

			if (ArrayUtil.isNotBlank(params)) {
				log.debug(JSON.toJSONString(params, true));
			}
			
			CloseableHttpClient httpClient = HttpClients.createDefault();// HttpClientBuilder.create().build();
			HttpPost httppost = new HttpPost(url);
			
			/*
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(4000)
					.setConnectionRequestTimeout(4000).setSocketTimeout(4000).build();
			httppost.setConfig(requestConfig);
			*/
			
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse httpresponse = httpClient.execute(httppost);

			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity, "UTF-8");
			
			if (entity != null) {
				EntityUtils.consumeQuietly(entity);
			}
			
			httpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return body;
	}
	
	/**
	 * 执行Get请求
	 * @param url
	 */
	public static String doGet(String url) {
		String json = null;
		try {
			String time = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
			log.debug("准备执行http_" + time + ":" + url);

			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(4000)
					.setConnectionRequestTimeout(4000).setSocketTimeout(4000).build();
			httpGet.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(httpGet);
			log.debug("得到的结果:" + response.getStatusLine());// 得到请求结果

			HttpEntity resEntity = response.getEntity();
			json = EntityUtils.toString(resEntity);

			log.debug("http角本返回值: " + json);

			EntityUtils.consumeQuietly(resEntity);

			httpclient.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return json;
	}
}
