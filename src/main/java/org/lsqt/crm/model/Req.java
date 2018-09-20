package org.lsqt.crm.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.mvc.util.ActionFormUtil;
import org.lsqt.components.util.collection.MapUtil;
import org.lsqt.crm.util.HttpClient;

import com.alibaba.fastjson.JSON;

public class Req<T> {
	public Service SERVICE;
	
	private Req() {
		 
	}
	
	public static <T> Req<T> newInstance(T form) {
		Req<T> req = new Req<T>();
		req.SERVICE = new Service();
		req.SERVICE.SERVICE_HEADER = new Service.ServiceHeader();
		req.SERVICE.SERVICE_BODY = new Service.ServiceBody();
		
		if(ActionFormUtil.isCanBeBeanType(form.getClass())) {
			@SuppressWarnings("unchecked")
			Map<String,Object> formMap = JSON.parseObject(JSON.toJSONString(form),Map.class) ;
			List<String> keyList = MapUtil.toKeyList(formMap);
			for(String k: keyList) {
				Object value = formMap.get(k);
				formMap.remove(k);
				formMap.put(k.toUpperCase(), value);
			}
			
			req.SERVICE.SERVICE_BODY.REQUEST = formMap;
		} else {
			req.SERVICE.SERVICE_BODY.REQUEST = form;
		}
		return req;
	}

	// ------------------------------  请求头部字段  Begin:-----------------------------------------
	/**
	 * 交易服务码
	 * @param serviceId 交易服务码
	 * @return
	 */
	public Req<T> serviceId(String serviceId) {
		this.SERVICE.SERVICE_HEADER.SERVICE_ID = serviceId;
		return this;
	}
	
	/**
	 * 服务接入渠道编号 : 渠道编号API 
	 * @param channelId 服务接入渠道编号(默认BANK)
	 * @return
	 */
	public Req<T> channelId(String channelId) {
		this.SERVICE.SERVICE_HEADER.CHANNEL_ID = channelId;
		return this;
	}
	
	/**
	 * 添加合同号
	 * @param contractNo
	 * @return
	 */
	public Req<T> contractNo(String contractNo) {
		this.SERVICE.SERVICE_HEADER.CONTRACT_NO = contractNo;
		return this;
	}
	
	/**
	 * 添加客户号
	 * @param customerNo
	 * @return
	 */
	public Req<T> customerNo(String customerNo) {
		this.SERVICE.SERVICE_HEADER.CUSTOMER_NO = customerNo;
		return this;
	}
	
	/**
	 * 添加身份证号
	 * @param idNo
	 * @return
	 */
	public Req<T> idNo(String idNo) {
		this.SERVICE.SERVICE_HEADER.ID_NO = idNo;
		return this;
	}
	// ------------------------------  请求头部字段  END!!-----------------------------------------
	public static class Service {
		public ServiceHeader SERVICE_HEADER;
		public ServiceBody SERVICE_BODY;
		
		public static class ServiceHeader {
			public String SERVICE_ID;
			public String ORG="000000000001";
			public String CHANNEL_ID="BANK";
			public String ACQ_ID="00130000"; 
			public String SUB_TERMINAL_TYPE="Web"; //终端类型
			public String SERVICESN=System.currentTimeMillis()+"";//请求交易流水号
			public String OP_ID= ContextUtil.getLoginName(); //操作员号
			public String REQUEST_TIME= new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			public String VERSION_ID="01";
			public String MAC="mac";
			
			public String CONTRACT_NO;
			public String CUSTOMER_NO;
			public String ID_NO;
			
			
		}
		
		public static class ServiceBody {
			public Object REQUEST;

		}
	}

	public String toJSONString(boolean ...isPrety) {
		if(isPrety.length ==0) {
			return JSON.toJSONString(this);
		}
		return JSON.toJSONString(this, isPrety[0]);
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
		
		
	}
}
