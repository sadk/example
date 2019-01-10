package org.lsqt.components.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.Result;
import org.lsqt.components.context.annotation.mvc.RequestPayload;
import org.lsqt.components.mvc.impl.UrlMappingDefinition;
import org.lsqt.components.mvc.impl.UrlMappingRoute;
import org.lsqt.components.mvc.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 表单数据处理器链
 * @author mm
 *
 */
public class FormMapBindingPayloadChain implements Chain{
	private static final Logger log = LoggerFactory.getLogger(FormMapBindingPayloadChain.class);
	
	private boolean enable = true;
	private int order = 510;
	private int state = STATE_NO_WORK;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Configuration configuration;
	
	
	public FormMapBindingPayloadChain(Configuration configuration,HttpServletRequest request,HttpServletResponse response) {
		this.configuration = configuration;
		this.request = request;
		this.response = response;
	}


	@Override
	public boolean isEnable() {
		return this.enable;
	}


	@Override
	public int getOrder() {
		 
		return this.order;
	}


	@Override
	public int getState() {
		 
		return this.state;
	}

	public static void main(String[] args) {
		HashMap<String, Object> newMap =	new HashMap<>();
		newMap.put("key", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object handle() throws Exception {
		this.state = STATE_IS_CONTINUE_TO_EXECUTE;
		

		UrlMappingRoute route = configuration.getUrlMappingRoute();
		
		UrlMappingDefinition urlMappingDefinition = route.find(RequestUtil.getRequestURI(request));
		if(urlMappingDefinition == null) {
			return null;
		}
		
		// 请求头：content-type:application/json  
		RequestPayload requestPayloadClass = urlMappingDefinition.getControllerClass().getAnnotation(RequestPayload.class); //类级别
		RequestPayload requestPayloadMethod = urlMappingDefinition.getMethod().getAnnotation(RequestPayload.class) ; // 方法级别
		if (requestPayloadClass != null || requestPayloadMethod != null) {
			try {
				Map<String, Object> form = JSON.parseObject(IOUtils.toString(request.getInputStream(), "UTF-8"),Map.class);
				
				Iterator<Map.Entry<String, Object>> iter =  form.entrySet().iterator();
				
				Map<String, Object> concurrentForm = new ConcurrentHashMap<>();
				while (iter.hasNext()) {
					Entry<String, Object> entry = iter.next();
					concurrentForm.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
				}
				
				ContextUtil.getFormMap().putAll(concurrentForm);

			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		
		return null;
	}
	

	
}

