package org.lsqt.crm.util;

import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class HttpClient {
	
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
		//post.setHeader("Content-type", "application/json"); //设置header

		
		StringEntity postingString = new StringEntity(jsonMsg);
		post.setEntity(postingString);
		org.apache.http.client.methods.CloseableHttpResponse response = httpClient.execute(post);
		String content = EntityUtils.toString(response.getEntity(),"UTF-8");
		
		String rs = JSON.toJSONString(JSON.parseObject(content, Map.class), true);
		//System.out.println("http 状态码:"+response.getStatusLine().getStatusCode());
		    
		//System.out.println(rs);

		response.close();
		httpClient.close();
		 
		System.out.println(url + "cost:"+(System.currentTimeMillis() - start));
		
		return rs;
	}
}
