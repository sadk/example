package org.lsqt.components.mvc;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.lsqt.components.context.CacheReflectUtil;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.Result;
import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Dao;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.context.annotation.mvc.After;
import org.lsqt.components.context.annotation.mvc.Before;
import org.lsqt.components.context.annotation.mvc.Match;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.context.annotation.mvc.RequestPayload;
import org.lsqt.components.context.bean.BeanFactory;
import org.lsqt.components.context.factory.AnnotationBeanFactory;
import org.lsqt.components.context.factory.SpringBeanFactoryAdapter;
import org.lsqt.components.db.Db;
import org.lsqt.components.mvc.impl.AnnotationUrlMappingRoute;
import org.lsqt.components.mvc.impl.UrlMappingDefinition;
import org.lsqt.components.mvc.util.ActionFormUtil;
import org.lsqt.components.mvc.util.ParameterNameUtil;
import org.lsqt.components.mvc.util.ViewResolveFtlUtil;
import org.lsqt.components.mvc.util.ViewResolveJSONUtil;
import org.lsqt.components.util.ExceptionUtil;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.uum.util.CodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;

/**
 * <pre>
 * 1.IOC容器部分：初使化Bean容器
 *		BeanFactory :提供基础的获取bean实例方法
 *
 * 2.MVC部分：
 *   可构建HandlerChain,ChainScope; 构建HandlerFlow(flow 可以由多个处理器链构成),FlowScope（是否可为向导、webflow、流程集成？)
 *   处理请求，执行一系列业务方法后返回视图
 *   url的RESTFULL的交互可加密（对称和非对称，注解试、可插拔）
 *   
 * 3.其它:CGLib字节码（AOP）代理(暂不使用代理技术)、日志处理部分
 * 
 * 4.业务功能：uum\微信、银企功能（建行银企、e商贸通)、账户系统
 * </pre>
 * @author yuanmm
 *
 */
@Deprecated
public class ApplicationFilter implements Filter{
	private static final Logger log = LoggerFactory.getLogger(ApplicationFilter.class);
	
	private static final String  CHARACTER_ENCODING_UTF8 = "UTF-8";
	
	
	private static Set<String> URI_STATIC=new HashSet<>();
	private static Set<String> URI_ESCAPE=new HashSet<>();
	private static Set<String> URI_ANONYMOUS=new HashSet<>();
	
	
	private static volatile boolean isContainerInit = false;
	
	private static ExecutorService  executor = Executors.newSingleThreadExecutor(); 
	
	private final AnnotationBeanFactory factory = new AnnotationBeanFactory();
	private AnnotationUrlMappingRoute router ;
	private org.lsqt.components.db.Db db;
	
	private static String LOGIN_ENABLED ;
	
	private void initContainer() throws Exception{
		//容器
		factory.buildBeanMeta();
		factory.buildBean();
		factory.buildBeanDependency();
		
		// 注册spring容器
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:application.xml");
		BeanFactory springBeanFactory = new SpringBeanFactoryAdapter(app);
		factory.regist(springBeanFactory);
		
		//MVC
		router = new AnnotationUrlMappingRoute(factory.getBeanDefinitions());
		router.buildUrlMapping();
		
		isContainerInit = true;
		
		//DB 暂时配置在spring里
		db=factory.getBean(Db.class);
		
		
	}
	
	private List<String> contextWrap(FilterConfig filterConfig,List<String> list) {
		List<String> pathList = new ArrayList<>();
		if (ArrayUtil.isNotBlank(list)) {
			String contextPath = filterConfig.getServletContext().getContextPath();
			for(String p: list) {
				String wrapUrl=contextPath+p;
				pathList.add(wrapUrl);
				log.info("匿名访问url : {} ", wrapUrl);
			}
		}
		return pathList;
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		
		String staticRes = filterConfig.getInitParameter("static");
		if (StringUtil.isNotBlank(staticRes)) {
			URI_STATIC.addAll(contextWrap(filterConfig,StringUtil.split(String.class, staticRes, ",",true)));
		}
		
		String escape = filterConfig.getInitParameter("escape");
		if (StringUtil.isNotBlank(escape)) {
			URI_ESCAPE.addAll(contextWrap(filterConfig,StringUtil.split(String.class, escape, ",",true)));
		}
		
		String anonymous = filterConfig.getInitParameter("anonymous");
		if (StringUtil.isNotBlank(anonymous)) {
			URI_ANONYMOUS.addAll(contextWrap(filterConfig,StringUtil.split(String.class, anonymous, ",",true)));
		}
		
		LOGIN_ENABLED = filterConfig.getInitParameter("login");
		log.info("是否开启登陆验证: {}", LOGIN_ENABLED);
		
		// 静态资源访问的URL
		URI_STATIC.add(".*.ico");
		URI_STATIC.add(".*.css");
		URI_STATIC.add(".*.js");
		URI_STATIC.add(".*.html");
		URI_STATIC.add(".*.swf");
		URI_STATIC.add(".*.png");
		URI_STATIC.add(".*.gif");
		URI_STATIC.add(".*.jpg");
		URI_STATIC.add(".*.jpeg");
		URI_STATIC.add(".*.woff2");
		
		URI_STATIC.add(".*.txt");
		URI_STATIC.add(".*.xml");
		URI_STATIC.add(".*.pdf");
		URI_STATIC.add(".*.xls");
		URI_STATIC.add(".*.xlsx");
		URI_STATIC.add(".*.ppt");
		URI_STATIC.add(".*.doc");
		
		executor.execute(() -> {
			long begin = System.currentTimeMillis();
			try {
				initContainer();
			} catch (Exception e) {
				e.printStackTrace();
				//log.error(e.getMessage());
			}
			log.info(" --- container stated cost " + (System.currentTimeMillis() - begin)+"(ms) ~!!!");
		});
	}

	 
	
	
	
	/**
	 * 静态资源直接返回响应
	 * @param request
	 * @return
	 */
	private boolean isStatic(HttpServletRequest request) {
		if(ArrayUtil.isBlank(URI_STATIC)) {
			return false;
		}
		for(String pattern: URI_STATIC) {
			boolean isMatch = Pattern.matches(pattern, request.getRequestURI());
			if(isMatch){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 脱离MVC管理的URL直接让后续的Filter或Servlet处理
	 * @param request
	 * @return
	 */
	protected static boolean isEscape(HttpServletRequest request){
		if (ArrayUtil.isBlank(URI_ESCAPE)) {
			return false;
		}

		for (String pattern : URI_ESCAPE) {
			boolean isMatch = Pattern.matches(pattern, request.getRequestURI());
			if (isMatch) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 匿名访问的URL直接让后续的Filter或Servlet处理
	 * @param request
	 * @return
	 */
	private boolean isAnonymous(HttpServletRequest request) {
		if (ArrayUtil.isBlank(URI_ANONYMOUS)) {
			return false;
		}

		for (String pattern : URI_ANONYMOUS) {
			boolean isMatch = Pattern.matches(pattern, request.getRequestURI());
			if (isMatch) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean isLogined(HttpServletRequest request) {
		List<String> uidList = null;

		// 获取第一个cookie
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			return false;
		}
		
		for (Cookie e : cookies) {
			if (CodeUtil.UID.equals(e.getName())) {
				String uidValue = CodeUtil.simpleDecode(e.getValue());
				if (StringUtil.isNotBlank(uidValue)) {
					uidList = StringUtil.split(uidValue, ",");
				}
				break;
			}
		}

		// 获取第一个cookie
		List<String> uid2List = null;
		if (uidList != null) {
			for (Cookie e : cookies) {
				if (e.getName().equals(CodeUtil.simpleEncode(uidList.get(0) + "," + uidList.get(2)))) {
					String value = CodeUtil.simpleDecode(e.getValue());
					if (StringUtil.isNotBlank(value)) {
						uid2List = StringUtil.split(value, ",");
					}
					break;

				}
			}
		}

		// 比较密码+时间戳相等
		if ((uidList != null && uid2List != null) && (uidList.size() == 5 && uid2List.size() == 2)) {
			String accPwd1 = uidList.get(1);
			String accTime1 = uidList.get(2);

			String accPwd2 = uid2List.get(0);
			String accTime2 = uid2List.get(1);

			if (accPwd1.equals(accPwd2) && accTime1.equals(accTime2)) {
				return true;
			}
		}
		return false;
	}
	
	
	protected void setCharacterEncoding(HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding(CHARACTER_ENCODING_UTF8);
		try {
			request.setCharacterEncoding(CHARACTER_ENCODING_UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		
		if (!isContainerInit) { // 容器还没有初使化完成，跳过，什么也不干
			log.info(" --- 系统正在启动...");
			filterChain.doFilter(request, response);
			return ;
		}
		
		if(isStatic(request) || isEscape(request)){ // 静态资源URI，直接返回到客户端
			filterChain.doFilter(request, response);
			return ;
		}

		if ("true".equalsIgnoreCase(LOGIN_ENABLED) || "on".equalsIgnoreCase(LOGIN_ENABLED)) { // 开启登陆验证
			if (!isLogined(request) && !isAnonymous(request)) { // 没有登陆并且不是非匿名访问
				response.sendRedirect(request.getContextPath() + "/login.jsp");
				return;
			}
		}
		
		setCharacterEncoding(request,response);
		
		//1.构建formMap
		//2.处理请求的URL与Controller的方法映射
		//3.前置后置处理，拦截器处理
		buildContextMap(request,response);
		
		buildFormMap(request,response);
		
		
		response.addHeader("Content-Type","text/html; charset=utf-8");
		response.addHeader("Date",new Date().toString());
		response.addHeader("Cache-Control","private");
		
		
		
		
		
		final UrlMappingDefinition urlMappingDefinition = router.find(getRequestURI());
		
		boolean isInvokedOk = true;
		Closeable out = null;
		try {
			/* 
			Before处理异常、--> 选择相应的视图显示异常
			Before处理有异常（但补Result包裹）--> 返回Result不成功，选择相应的视图显示异常

			action异常、选择相应的视图显示异常

			After异常、选择相应的视图显示异常
			After异常（但补Result包裹）--> 返回Result不成功，选择相应的视图显示异常
			*/
			Result<Object> result= buildParamInvoke(urlMappingDefinition,request,response);
			if (result != null) {
				
				Object hook = result.getHook();
				if (hook!=null && (hook instanceof Closeable)) {
					out = (Closeable) hook;
				}
				
				if (!result.getIsSuccess()) {// 如果失败，选择相应的视图返回到浏览器
					Method method = urlMappingDefinition.getMethod();
					RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
					if (requestMapping != null) {
						View view = requestMapping.view();
						if (view == View.FTL) {

						}
						if (view == View.JSON) {
							OutputStream os = response.getOutputStream();
							ViewResolveJSONUtil.resolve(os, result.getData());
							response.setContentType("application/json; charset=utf-8");
							result.setHook(os);

						}
						if (view == View.JSP) {
							
						}
					}
				} 
				
				
			}
		} catch (Throwable e) {
			e.printStackTrace();
			
			if(e instanceof InvocationTargetException) {
				InvocationTargetException t = (InvocationTargetException) e ;
				e = t.getTargetException();
			}
			
			//打印到浏览器
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
			if(out instanceof PrintWriter){
				e.printStackTrace((PrintWriter)out);
				
			} else if(out instanceof ServletOutputStream)  {
				ServletOutputStream ops = (ServletOutputStream)out;
				ops.println(ExceptionUtil.getStackTrace(e));
			} else if(out == null) {
				out = response.getWriter();
				PrintWriter writer = (PrintWriter) out;
				writer.write(e.getMessage());
			}
			
			isInvokedOk = false;
		} finally {
			ContextUtil.clear();
			if (out != null) {
				out.close();
			}
		}
		
		if(isInvokedOk && response.isCommitted() == false) {
			filterChain.doFilter(request, response);
		}
	}
 
	@SuppressWarnings("unchecked")
	private  Result<Object> buildParamInvoke(UrlMappingDefinition urlMappingDef  , HttpServletRequest request,HttpServletResponse response) throws Exception{
		if (urlMappingDef == null) {
			return null;
		}
		
		if (urlMappingDef.getMethod() == null) {
			throw new Exception("have not found url mapping definition method to invoke~!");
		}

		Object controller = factory.getBean(urlMappingDef.getControllerClass());
		
		if (controller == null) {
			throw new Exception("have not found url mapping of the controller~!");
		}

		RequestPayload requestPayloadClass = urlMappingDef.getControllerClass().getAnnotation(RequestPayload.class); //类级别
		RequestPayload requestPayloadMethod = urlMappingDef.getMethod().getAnnotation(RequestPayload.class) ; // 方法级别
		if(requestPayloadClass!=null || requestPayloadMethod!=null) {
			try {

				InputStream in = request.getInputStream() ;
				//if(in.available()!=0){
					Map<String,Object> form = JSON.parseObject(IOUtils.toString(request.getInputStream(),"UTF-8"),Map.class);
					ContextUtil.getFormMap().putAll(form);
				//}
				log.debug(" --- parse RequestPayload : "+request.getRequestURI()+"===>" + ContextUtil.getFormMap());
			} catch (IOException e) {
				e.printStackTrace();
				return Result.fail(e.getMessage());
			} 
		}
		
		List<Object> methodInputParamValues = getMethodArgsValue(urlMappingDef.getMethod());
	
		
		// 2.Action调用
		final Result<Object> result = Result.ok();
		try {
			Object beforeMethodReturnObject = invokeBefore(urlMappingDef, methodInputParamValues.toArray()); // 可能有DB操作，记得处理事务！
			if (beforeMethodReturnObject instanceof Result<?>) {
				Result<Object> r = (Result<Object>) beforeMethodReturnObject;
				if (!r.getIsSuccess()) { // 抛出异常或返回失败，返回到浏览器
					return r;
				}
			}
			
			
			boolean isExcludeTransaction = false; // 是否脱离数据库(事务)环境
			boolean isTransaction = true; //开启事务
			
			Method action = urlMappingDef.getMethod();
			RequestMapping rm = action.getAnnotation(RequestMapping.class);
			if (rm != null) {
				isExcludeTransaction = rm.excludeTransaction();
				isTransaction = rm.isTransaction();
			}
			
			if (isExcludeTransaction) {
				result.setData(urlMappingDef.getMethod().invoke(controller, methodInputParamValues.toArray()));
			} else {
				db.executePlan(isTransaction, () -> {
					try {
						result.setData(urlMappingDef.getMethod().invoke(controller, methodInputParamValues.toArray()));
						
						//后置处理，一定纳入到当前事务中
						invokeAfter(urlMappingDef, result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
			
			 
		} finally{
			db.close();
		}
	

 
		
		// 3.展现视图
		Method method = urlMappingDef.getMethod();
		RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
		if (requestMapping == null) {
			return null;
		}

		View view = requestMapping.view();
		if (view == View.FTL) {
			
			PrintWriter out = response.getWriter();
			ViewResolveFtlUtil.resolve(out, requestMapping.path() , result.getData());
			
			result.setHook(out);
			
		} else if (view == View.JSON) {
			OutputStream out = response.getOutputStream();
			ViewResolveJSONUtil.resolve(out, result.getData());
			response.setContentType("application/json; charset=utf-8");
			
			result.setHook(out);

		} else if (view == View.JSP) {
			String viewPrefix = requestMapping.path();
			if(!viewPrefix.endsWith("/")) {
				viewPrefix+="/";
			}
			Object rs = result.getData();
			if(rs instanceof String) {
				String template = rs.toString();
				if(!template.endsWith(".jsp")) {
					template += ".jsp";
				}
				
				if(template.startsWith("redirect:")) {
					response.sendRedirect(viewPrefix+template);
				} else {
					request.getRequestDispatcher(viewPrefix+template).forward(request, response);
				}
			}
		}
 
		return result;
	}

	/**
	 * 绑定当前URL请求参数值到类的某个方法 (如:Controller的某个方法)
	 * @param method
	 * @return 
	 * @throws Exception
	 */
	private List<Object> getMethodArgsValue(Method method) throws Exception {
		Class<?>[] types = method.getParameterTypes();
		List<String> methodInputParamNames = ParameterNameUtil.getParameterNames(method);
		
		if(types.length != methodInputParamNames.size()) {
			throw new Exception("param value count and param name count are't equal~!");
		}
		
		// 1.填充表单
		List<Object> methodInputParamValues = new ArrayList<Object>();
		for (int i=0;i<methodInputParamNames.size();i++) {
			Parameter param = method.getParameters()[i];

			Class<?> paramType = types[i];
			String paramName = methodInputParamNames.get(i);
			Object paramValue = null;
			
			if (ActionFormUtil.isCanBeBaseType(paramType)) { // 处理常规类型
				paramValue = ActionFormUtil.prepareBaseValue(paramType,ContextUtil.getFormMap().get(paramName));
				
			} else if(ActionFormUtil.isCanBeDate(param)){ // 处理日期类型
				 
				paramValue = ActionFormUtil.prepareDateValue(param,ContextUtil.getFormMap().get(paramName));
				 
				
			} else if (ActionFormUtil.isCanBeBeanType(paramType)) { // 处理bean类型
					Object formBean = paramType.newInstance();
					paramValue = formBean;
					ActionFormUtil.fillBean(formBean, ContextUtil.getFormMap());
			}
			
			methodInputParamValues.add(paramValue);
		}
		return methodInputParamValues;
	}

	/**
	 * 
	 * 获取请求的URI地址
	 * @param request
	 * @return
	 */
	private String getRequestURI() {
		HttpServletRequest request = ContextUtil.getRequest();
		String uri = request.getRequestURI();

		// bugFix: 去掉工程名前缀，如: http://ip:poart/工程名(也就是context)/user/login
		String ctx = request.getContextPath();
		uri = uri.substring(ctx.length(), uri.length());
		return uri;
	}

	/**
	 * controller方法的前置处理
	 * @param urlMappingDefinition 当前请求对应的映射元信息
	 * @param params 当前请求controller方法的入参值
	 * @return
	 * @throws Exception
	 */
	private Object invokeBefore(final UrlMappingDefinition urlMappingDefinition,  Object ... params) throws Exception {
		if(urlMappingDefinition == null) return null;
		
		Object beforeMethodReturnObject = null; //前置处理器方法返回值
		
		Before before = urlMappingDefinition.getMethod().getAnnotation(Before.class);
		if (before != null) {
			Class<?> processClass = before.clazz();

			List<Method> processMethodList = CacheReflectUtil.getMethodList(processClass);

			if (ArrayUtil.isNotBlank(processMethodList)) {
				 
				Method processMethod = null; 
				final List<Object> processMethodParamValue = new ArrayList<>();

				if(processMethodList.size() == 1) {
					processMethod = processMethodList.get(0);
					processMethodParamValue.addAll(getMethodArgsValue(processMethod));
					
				} else {
					String controllerMethodName = urlMappingDefinition.getMethod().getName();
					Class<?>[] controllerMethodArgsTypes = urlMappingDefinition.getMethod().getParameterTypes();
					
					if (StringUtil.isBlank(before.method())) {// 如果没有标注哪个处理方法，用controller的源方法名和参数查找处理“方法”
						processMethod = processClass.getMethod(controllerMethodName, controllerMethodArgsTypes);
						processMethodParamValue.addAll(Arrays.asList(params));
	
					} else if (StringUtil.isNotBlank(before.method()) && before.args().length == 0) { // 如果标注了处理方法，没有标注方法参数，用源方法参数查找处理“方法”
						processMethod = processClass.getMethod(before.method(), controllerMethodArgsTypes);
						processMethodParamValue.addAll(getMethodArgsValue(processMethod));
						
					} else if (StringUtil.isNotBlank(before.method()) && before.args().length > 0) {// 即标注了处理方法又指定了方法参数类型，直接查找到处理“方法”
						processMethod = processClass.getMethod(before.method(), before.args());
						processMethodParamValue.addAll(getMethodArgsValue(processMethod));
					}
				}
				
				if (processMethod != null) {
					boolean isMatch = false;
					Match matcher = processMethod.getAnnotation(Match.class);
					
					if (matcher == null) {//不写match，controller的任何url都匹配，处理器执行！
						isMatch = true; 
					} else {
						String[] urls = matcher.mapping();
						if (ArrayUtil.isBlank(Arrays.asList(urls))) {
							return null;
						}
						 
						String currUrl = getRequestURI();
						
						Controller ctl = urlMappingDefinition.getControllerClass().getAnnotation(Controller.class);
						String [] ctlMapping = ctl.mapping();
						if (ArrayUtil.isBlank(Arrays.asList(ctlMapping))) { //控制器定义的url没有标注模块前缀
							for (String path: urls) {
								isMatch = Pattern.matches(path.concat(".*"), currUrl);
								if(isMatch) {
									break;
								}
							}
						} else {
							
							for (String ctlUrl : ctlMapping) {
								boolean isFind = false;
								for (String path : urls) {
									isFind = Pattern.matches(ctlUrl.concat(path).concat(".*"), currUrl);
									if (isFind) {
										break;
									}
								}
								if (isFind) {
									isMatch = isFind;
									break;
								}
							}
						}
					}
					
					
					
					if (isMatch && processMethod != null) {
						processMethod.setAccessible(true);

						Object bean = null;
						if (processClass.getAnnotation(Dao.class) != null
								|| processClass.getAnnotation(Service.class) != null
								|| processClass.getAnnotation(Component.class) != null
								|| processClass.getAnnotation(Controller.class) != null) {
							bean = factory.getBean(processClass);
						}

						if (bean == null) {// 单纯的POJO
							bean = processClass.newInstance();
						}

						
						
						//如果有前置事务处理
						boolean isExcludeTransaction = matcher.excludeTransaction(); // 是否脱离数据库(事务)环境
						boolean isTransaction = true; //开启事务
						
						if (isExcludeTransaction) {
							beforeMethodReturnObject = processMethod.invoke(bean, processMethodParamValue.toArray());
						} else {
							
							Result<Object> result = Result.ok();
							result.setHook(processMethod);
							result.setData(bean);
							
							db.executePlan(isTransaction, ()->{
								try {
									Method m = (Method)result.getHook();
									Object b = result.getData();
									result.setData( m.invoke(b, processMethodParamValue.toArray()));
								} catch (Exception e) {
									e.printStackTrace();
								}
							});
							
							beforeMethodReturnObject = result.getData();
						}
						
					}
				}
				
			} 
		}
		return beforeMethodReturnObject;
	}

	
	/**
	 * 请求后置处理
	 * @param urlMappingDefinition 当前URI映射定义
	 * @param controllerResult controller方法返回值
	 * @throws Exception
	 */
	private void invokeAfter(final UrlMappingDefinition urlMappingDefinition, final Result<Object> controllerResult) throws Exception {
		
		After after = urlMappingDefinition.getMethod().getAnnotation(After.class);
		if (after != null) {
			Class<?> processClass = after.clazz();

			List<Method> methodList = CacheReflectUtil.getMethodList(processClass);

			if (ArrayUtil.isNotBlank(methodList)) {
				Method processMethod = null;
				if (after.args() == null || after.args().length == 0) {
					for (Method m : methodList) {
						if (m.getName().equals(after.method())) {
							processMethod = m;
							break;
						}
					}
				} else {
					processMethod = processClass.getMethod(after.method(), after.args());
				}

				if (processMethod != null) {

					processMethod.setAccessible(true);

					Object bean = factory.getBean(processClass);
					if (bean == null) {
						bean = processClass.newInstance();
					}

					Object wrappedObject = processMethod.invoke(bean, controllerResult.getData());
					
					controllerResult.setData(wrappedObject);
				}

			} else {
				log.warn("类 {} 没有定义方法", processClass);
			}
		}
	}
	

	
	private void buildContextMap(HttpServletRequest request,HttpServletResponse response) {
		
		ContextUtil.getContextMap().put(ContextUtil.CONTEXT_REQUEST_OBJECT, request);
		ContextUtil.getContextMap().put(ContextUtil.CONTEXT_RESPONSE_OBJECT, response);
		
		// 获取第一个cookie
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			return ;
		}
		
		List<String> uidList = null;
		for (Cookie e : cookies) {
			if (CodeUtil.UID.equals(e.getName())) {
				String uidValue = CodeUtil.simpleDecode(e.getValue());
				if (StringUtil.isNotBlank(uidValue)) {
					uidList = StringUtil.split(uidValue, ",");
				}
				break;
			}
		}
		
		// 绑定用户ID到上下文
		if (ArrayUtil.isNotBlank(uidList)) {
			ContextUtil.getContextMap().put(ContextUtil.CONTEXT_LOGIN_ACCOUNT_OBJECT, uidList.get(0));
			ContextUtil.getContextMap().put(ContextUtil.CONTEXT_LOGIN_ID_OBJECT, uidList.get(3));
			ContextUtil.getContextMap().put(ContextUtil.CONTEXT_LOGIN_NAME_OBJECT, uidList.get(4));
		}	
	}
	
	
	private void buildFormMap(HttpServletRequest request,HttpServletResponse response)  {
		
		Set<Entry<String, String[]>> set = request.getParameterMap().entrySet();
		if (set == null) {
			return;
		}

		for (Entry<String, String[]> e : set) {
			if (e.getValue() != null && e.getValue().length == 1) {
				ContextUtil.getFormMap().put(e.getKey(), e.getValue()[0]);
				
			} else if (e.getValue() != null && e.getValue().length > 1) {
				// bug fix: 请求的地址为 http://localhost:8080/table/page?isQueryDb=true ，表单里也有 isQueryDb=true ,
				// <form action="http://localhost:8080/table/page?isQueryDb=true">
				//	<input name="isQueryDb" value="true">
				// </form>
				Set<String> temp = new HashSet<>();
				for (int i=0;i<e.getValue().length;i++) {
					temp.add(e.getValue()[i]);
				}
				if(temp.size()>1) {
					ContextUtil.getFormMap().put(e.getKey(), e.getValue());
				} else {
					ContextUtil.getFormMap().put(e.getKey(), e.getValue()[0]);
				}
				
			}
		}
	}
	
	@Override
	public void destroy() {
		ContextUtil.clear();
	}
	
}
