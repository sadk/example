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

	
	public static void main(String[] args) {
		Map<String,String> map = new HashMap<>();
		map.put("CERT_ID", "130901196604155209");
		
		User user = new User();
		user.setId(2343L);
		user.setLoginName("张三");
		user.setBirthday(new Date());
		
		
		Req<User> req = Req.newInstance(user);
		
		System.out.println(JSON.toJSONString(req, true));
	}
}
