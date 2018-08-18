package org.lsqt.act.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.act.model.RunTask;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 待办任务URL自动跳转,适配移动端和PC端
 * @author mmyuan
 *
 */
@Controller(mapping={"/api/task_url_service"})
public class ApiTaskUrlServiceController {
	private static final Logger  log = LoggerFactory.getLogger(ApiTaskUrlServiceController.class);
	
	private final static String[] agent = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" }; // 定义移动端请求的所有可能类型
	 
	@Inject private Db db;
	
	 
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping = { "/converted_redirect", "/m/converted_redirect" }, text = "待办链接手机与PC端平台跳转适配")
	public Result convertedRedirect(String runTaskId,String isDebug) {
		try {

			HttpServletRequest request = ContextUtil.getRequest();
			HttpServletResponse response = ContextUtil.getResponse();

			
			
	        System.out.println("---------获取请求头方式-------------");  
	        // 拿到所有请求头  
	        Enumeration<String> e1 = request.getHeaderNames();  
	        while (e1.hasMoreElements()) {  
	            String headerName = (String) e1.nextElement();  
	            String headValue = request.getHeader(headerName);  
	          	log.debug(headerName + "=" + headValue);  
	        }  
			
	        
	        
	        
	        
			if (StringUtil.isBlank(runTaskId)) {
				return Result.fail("中转链接参数不能为空");
			}

			RunTask model = db.getById(RunTask.class, runTaskId);
			if (model == null) {
				
				//return Result.fail("当前待办任务不存在或已被处理");
				response.sendRedirect(request.getContextPath()+"/mobile_500.html");
				return null;
			}

			String userAgent = request.getHeader("user-agent").toLowerCase();
			boolean isMobile = checkAgentIsMobile(userAgent);
			log.debug("请求是否来自移动端:" + isMobile+",点击的用户是:"+model.getTaskUserName());
			
			if ("true".equals(isDebug)) {
				isMobile = true;
			}

			String json = model.getExtData();// CodeUtil.decode(data);
			log.debug(json);

			Map<String, Object> map = JSON.parseObject(json, Map.class);
			if (isMobile) {
				Object mobileUrl = map.get("mobileUrl");
				if (mobileUrl != null) {
					response.sendRedirect(mobileUrl.toString());
				} else {
					log.error("mobileUrl参数为空");
					return Result.fail("mobileUrl参数为空");
				}
			} else {
				Object pcUrl = map.get("pcUrl");
				if (pcUrl != null) {
					response.sendRedirect(pcUrl.toString()+"&isCloseWindow=true");// 后置参数用于控制，是否关闭窗口
					return null;
				} else {
					log.error("pcUrl参数为空");
					return Result.fail("pcUrl参数为空");
				}
			}

			return Result.ok();
		} catch (Exception e) {
			log.error("Error saving model", e);
			return Result.fail(e);
		}

	}
	
	@RequestMapping(mapping = { "/decode", "/m/decode" }, text = "移动端待办详情，参数包解密接口")
	public Result decode(String paramData) {
		if (StringUtil.isBlank(paramData)) {
			return Result.fail("paramData参数不能为空");
		}

		log.debug("解密参数：" + paramData);

		String rs;
		try {
			rs = new String(Base64.getDecoder().decode(URLDecoder.decode(paramData, "utf8")), "utf8");
			return Result.ok("请求成功!", rs);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
			
			return Result.fail("paramData="+paramData+", error is :"+e.getMessage());
		}
	}
	
	/**
	 * 判断User-Agent 是不是来自于手机
	 * 
	 * @param ua
	 * @return
	 */
	public static boolean checkAgentIsMobile(String ua) {
		boolean flag = false;
		
		if(ua.indexOf("micromessenger")>-1){ //微信客户端
			return true;
		}
		
		if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
			// 排除 苹果桌面系统
			if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
				for (String item : agent) {
					if (ua.contains(item)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}
}
