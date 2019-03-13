package org.lsqt.sms.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.lsqt.components.util.collection.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClient {
	private static final Logger log = LoggerFactory.getLogger(HttpClient.class);
	
	/**
	 * UrlEncodedForm 方式请求
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String post(String url, Map<String,Object> paramMap) throws Exception {  
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
			throw e;
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				
			}
		}
		
		log.debug("httpClient response:"+content);
        return content;  
    }
	
    public static String appId = "a2Qpp944stdsxnrvh5lvj1gh635ix3s4";
    public static String appKey = "B2FNQ4JF4HWSRSA3J1QNPT1P5Y8S3C5V";
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
	
	 
	
    public static void upload(String url,File file) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);

            FileBody f = new FileBody(file);
            
            //请求参数
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("adv", "100007265092"); 
            params.put("src", "marketing_cloud"); 
            params.put("data_type", 2); 
            params.put("is_md5", 0);
            
            String paramJSON = JSON.toJSONString(params);
            
            

            Long timestamp = System.currentTimeMillis();
            String serviceId = "sms-data-upload";
            
            String sign = MD5Util.MD5Encode(appKey+timestamp+serviceId+paramJSON,"UTF-8");
            
            HttpEntity reqEntity = MultipartEntityBuilder.create()
            		.addPart("AppId",new StringBody(appId,ContentType.TEXT_PLAIN))
                    .addPart("File", f)
                    .addPart("Timestamp",new StringBody(timestamp+"",ContentType.TEXT_PLAIN))
                    .addPart("Params",new StringBody(JSON.toJSONString(params, true) ,ContentType.TEXT_PLAIN))
                    .addPart("ServiceId",new StringBody(serviceId ,ContentType.TEXT_PLAIN))
                    .addPart("Sign",new StringBody(sign ,ContentType.TEXT_PLAIN))
                    .build();


            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            
            try {
            	//
            	
            	
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                
                if (resEntity != null) {
                	String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                	System.out.println(content);
                }
                
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
    
    
	public static void main(String[] args) throws Exception {
		//1.号码包上传API
		File file = new File("E:/workspace/new_crm_platform/src/main/java/org/lsqt/crm/util/mobile.txt");
		upload("http://dmc.qq.com/data/store", file);
		
		//2.人群规模&状态cgi
		
	}
}


