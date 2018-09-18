package httpclient;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.components.mvc.util.ActionFormUtil;
import org.lsqt.components.util.collection.MapUtil;
import org.lsqt.uum.model.User;

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
	
	public static class Service {
		public ServiceHeader SERVICE_HEADER;
		public ServiceBody SERVICE_BODY;
		
		public static class ServiceHeader {
			public String SERVICE_ID;
			public String ORG;
			public String CHANNEL_ID;
			public String ACQ_ID;
			public String SUB_TERMINAL_TYPE="Web"; //终端类型
			public String SERVICESN;//请求交易流水号
			public String OP_ID; //操作员号
			public String REQUEST_TIME;
			public String VERSION_ID;
			public String MAC;
			
			public String CONTRACT_NO;
			public String CUSTOMER_NO;
			public String ID_NO;
			
			
		}
		
		public static class ServiceBody {
			public Object REQUEST;

		}
	}

	public String toJSONString() {
		return JSON.toJSONString(this, true);
	}
	
	public static void main(String[] args) {
		Map<String,String> map = new HashMap<>();
		map.put("CERT_ID", "130901196604155209");
		
		User form = new User();
		form.setId(2343L);
		form.setLoginName("张三");
		form.setBirthday(new Date());
		
		
		Req<User> req = Req.newInstance(form).contractNo("这是合同号").idNo("这是身分证号").customerNo("这是客户号");
		
		
		System.out.println(req.toJSONString());
	}
}
