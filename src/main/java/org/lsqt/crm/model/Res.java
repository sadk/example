package org.lsqt.crm.model;

import java.util.HashMap;
import java.util.Map;

import org.lsqt.crm.util.HttpClient;

import com.alibaba.fastjson.JSON;

public class Res {

	private String status;
	private String code;
	private String desc;

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	public <T> T getData(Class<T> clazz) {
		String json = JSON.toJSONString(SERVICE.SERVICE_BODY.RESPONSE);
		return JSON.parseObject(json, clazz);
	}
	
	
	
	public Service SERVICE;
	private Res() {
		 
	}
	
	
	public static Res newInstance(String jsonMsg) {
		Res res = JSON.parseObject(jsonMsg, Res.class);
		
		res.setCode(res.SERVICE.SERVICE_HEADER.SERV_RESPONSE.CODE);
		res.setDesc(res.SERVICE.SERVICE_HEADER.SERV_RESPONSE.DESC);
		res.setStatus(res.SERVICE.SERVICE_HEADER.SERV_RESPONSE.STATUS);
		return res;
	}
	
 
	public static class Service {
		public ServiceHeader SERVICE_HEADER;
		public ServiceBody SERVICE_BODY;
		
		public static class ServiceHeader {
			public String SERVICE_ID;
			public String ORG;
			public String CHANNEL_ID;
			//public String ACQ_ID;
			public String SUB_TERMINAL_TYPE="Web"; //终端类型
			public String SERVICESN;//请求交易流水号
			public String OP_ID; //操作员号
			public String REQUEST_TIME;
			public String VERSION_ID;
			public String MAC;
			 
			public ServResponse SERV_RESPONSE;
			
			public static class ServResponse {
				public String STATUS;
				public String CODE;
				public String DESC;
				
			}
			
			
		}
		
		public static class ServiceBody {
			public Object RESPONSE;

		}
	}
	
	

	public String toJSONString() {
		return JSON.toJSONString(this, true);
	}
	
	public static void main(String[] args) throws Exception {
		Map<String,String> param = new HashMap<>();
		param.put("CERT_ID", "130901196604155209");
		param.put("CUST_ID", "1000001993");
		
		Req<Map<String,String>> req = Req.newInstance(param).idNo("130901196604155209");
		
		String jsonMsg = req.toJSONString(true);
		System.out.println("发送的报文:"+jsonMsg);
		
		String responseMsg = HttpClient.post("http://172.16.5.233:8888/account",jsonMsg);
		System.out.println("响应的报文:"+responseMsg);
		
		
		
		Res res = Res.newInstance(responseMsg);
		System.out.println("获取强类型对象:"+res.getData(Map.class));
	}
}
