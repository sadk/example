package org.lsqt.crm.util;

import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class HttpClient {
	private static final Logger log = LoggerFactory.getLogger(HttpClient.class);
	/**
	 * 
	 * @param url 服务器api（不带参数），如 : http://IP:Port/account
	 * @param jsonMsg 请求报文信息
	 * @return
	 */
	public static String post(String url,String jsonMsg) throws Exception {
		long start = System.currentTimeMillis();
		 
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-type", "application/json; charset=utf-8"); //设置header

		
		StringEntity postingString = new StringEntity(jsonMsg,"UTF-8");
		post.setEntity(postingString);
		
		
		CloseableHttpResponse response = null;
		String rs = null;
		try {
			response = httpClient.execute(post);
			String content = EntityUtils.toString(response.getEntity(), "UTF-8");

			rs = JSON.toJSONString(JSON.parseObject(content, Map.class), true);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} finally {
				httpClient.close();
			}
		}
		
		log.info(" --- cost:"+(System.currentTimeMillis() - start) + url);
		
		return rs;
	}
}


