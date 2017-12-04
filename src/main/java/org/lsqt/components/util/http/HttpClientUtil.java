package org.lsqt.components.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServlet;

/**
 * 
 * @author Sky
 *
 */
public class HttpClientUtil {
	
	public static enum Method{
		GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;
	}
	private static boolean isDebug=true;
	
	/**
	 * 
	 * @param aUrl
	 * @param params
	 * @param method
	 * @param proxyHost
	 * @param proxyPort
	 * @return
	 * @throws IOException
	 */
	private static String request(String aUrl,Map<String,Object> params,Method method,String proxyHost,String proxyPort) throws IOException{
		
		long t0=System.nanoTime();
		
		HttpURLConnection httpURLConn = null;
		OutputStreamWriter osw = null;
		
		InputStream	httpInputStream = null;
		Reader httpInputStreamReader = null;
		try {
				if(params!=null && params.size()>0) {
					if(aUrl.indexOf("?")==-1){
						aUrl=aUrl.concat("?");
					}
					
					String queryParam="";
					for(Entry<String,Object> ent: params.entrySet()){
						queryParam =queryParam
							.concat(ent.getKey())
							.concat("=")
							.concat(ent.getValue() == null ? "" : ent.getValue().toString())
							.concat("&");
					}
					if(queryParam.endsWith("&")){
						queryParam=queryParam.substring(0,queryParam.length()-1);
					}
					
					aUrl=aUrl.concat(queryParam);
				}
			
				URL url=new URL(aUrl);
				if(isDebug){
					System.out.println("\n  ---- 请求url: ".concat(aUrl));
					System.out.println("  ---- 请求方法: "+ (method==null? Method.POST.name() : method.name()) );
					boolean isProxy=(proxyHost!=null && proxyPort!=null);
					System.out.println("  ---- 是否走代理: "+isProxy);
					if(isProxy){
						System.out.println("  ---- 代理机器host: "+proxyHost+", 端口:"+proxyPort);
					}
				}
				
				
				if(proxyHost!=null && proxyPort!=null){
					 Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, Integer.valueOf(proxyPort)));
					 httpURLConn= (HttpURLConnection)url.openConnection(proxy);
				}else{
					 httpURLConn= (HttpURLConnection)url.openConnection();
				}
				
				
	            httpURLConn.setDoOutput(true);
	            httpURLConn.setRequestMethod(method == null ? Method.POST.name(): method.name());
	            httpURLConn.setUseCaches(false);
	            httpURLConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	            httpURLConn.connect();
	           
	            /**/
	            final String defaultCharset="UTF-8";
	            osw = new OutputStreamWriter(httpURLConn.getOutputStream(),defaultCharset );
				osw.write(aUrl);
				osw.flush();
				osw.close();
				
				StringBuilder buffer = new StringBuilder();
				
				httpInputStream = httpURLConn.getInputStream();
				httpInputStreamReader = new InputStreamReader(httpInputStream, defaultCharset);
				
				BufferedReader br = new BufferedReader(httpInputStreamReader);
				
				String temp="";
				while ((temp = br.readLine()) != null) {
					buffer.append(temp);
				}
				
				System.out.println("  ---- 返回结果: "+buffer);
				return buffer.toString();
		}catch(IOException ioe){
			System.out.println("  ---- 获取结果失败: "+ioe.getMessage()+" \n");
			throw ioe;
		} finally {
			try {
				if (osw != null) {
					osw.close();
				}
			} finally {
				try {
					if (httpInputStreamReader != null) {
						httpInputStreamReader.close();
					}
				} finally {
					try {
						if (httpInputStream != null) {
							httpInputStream.close();
						}
					} finally {
						if (httpURLConn != null) {
							httpURLConn.disconnect();
						}
					}
				}
			}
			long t1 = System.nanoTime();
			System.out.printf("  ---- 方法耗时: %.2fs%n", (t1 - t0) * 1e-9);
		}
	}
	
	public static String execute(String aUrl) throws IOException {
		return request(aUrl, null, null, null, null);
	}
	
	public static String execute(String aUrl,Method method) throws IOException{
		return request(aUrl, null, method, null, null);
	}
	
	public static String execute(String aUrl,Map<String,Object> params) throws IOException{
		return request(aUrl, params, null, null, null);
	}
	
	public static String execute(String aUrl,Map<String,Object> params,Method method) throws IOException{
		return request(aUrl, params, null, null, null);
	}
	
	public static String execute(String aUrl,Method method,String proxyHost,String proxyPort) throws IOException{
		return request(aUrl, null, method, proxyHost, proxyPort);
	}
	
	public static String execute(String aUrl,Map<String,Object> params,String proxyHost,String proxyPort) throws IOException{
		return request(aUrl, params, null, proxyHost, proxyPort);
	}
	
	public static String execute(String aUrl,Map<String,Object> params,Method method,String proxyHost,String proxyPort) throws IOException{
		return request(aUrl, params, method, proxyHost, proxyPort);
	}
	
	public static void main(String args[]) throws IOException{
		String aUrl="http://oschina.net";
		String proxyHost="10.10.60.13";
		String proxyPort="744";
		
		
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", 34L);
		
		
		//execute(aUrl);
		//execute(aUrl, Method.POST);
		execute(aUrl, params);
		//execute(aUrl, params, Method.GET);
		//execute(aUrl, Method.GET, proxyHost, proxyPort);
		//execute(aUrl, params, Method.GET, proxyHost, proxyPort);
		
		try{
			
		}catch(Exception e){
			
		}finally{
			
		}
	}
}
