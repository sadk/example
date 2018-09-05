package org.lsqt.uum.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.lsqt.components.util.collection.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	/**
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String getResponse(String url, Map<String,Object> paramMap) {  
		log.debug("httpClient: url="+url+" ,params:"+paramMap);
		
		String content = null;
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		
		try {

			List<NameValuePair> param = new ArrayList<>();
			
			List<String> keys = MapUtil.toKeyList(paramMap);
			for (String key : keys) {
				Object value = paramMap.get(key);
				NameValuePair p = new BasicNameValuePair(key, value == null ? "" : value.toString());
				param.add(p);
			}
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "utf-8");
			post.setEntity(entity);

			HttpResponse httpResponse = httpClient.execute(post);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity2 = httpResponse.getEntity();
				content = EntityUtils.toString(entity2);
			}

		} catch (Exception e) {
			log.error("httpClient: url="+url+" ,params:"+paramMap +" ,error message:"+e.getMessage());
			return null;
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				
			}
		}
		
		log.debug("httpClient response:"+content);
        return content;  
    }
}
 