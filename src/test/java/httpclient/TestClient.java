package httpclient;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class TestClient {
	
	public static void main(String[] args) throws Exception {
		
		String url = "http://172.16.5.233:8888/account";
		String requestContent = IOUtils.toString(
				new FileInputStream(new File("E:\\workspace\\example\\src\\test\\java\\httpclient\\request.txt")));

		Long start = System.currentTimeMillis();
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		//post.setHeader("Content-type", "application/json"); //设置header

		
		StringEntity postingString = new StringEntity(requestContent);
		post.setEntity(postingString);
		org.apache.http.client.methods.CloseableHttpResponse response = httpClient.execute(post);
		String content = EntityUtils.toString(response.getEntity(),"UTF-8");
		
		String rs = JSON.toJSONString(JSON.parseObject(content, Map.class), true);
		System.out.println("http 状态码:"+response.getStatusLine().getStatusCode());
		    
		System.out.println(rs);

		response.close();
		httpClient.close();

		System.out.println(System.currentTimeMillis() - start);
	}
}