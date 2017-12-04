package org.lsqt.components.mvc;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.context.annotation.mvc.RequestPayload;
import org.lsqt.components.context.impl.bean.factory.AnnotationBeanFactory;
import org.lsqt.components.context.impl.bean.factory.SpringBeanFactoryAdapter;
import org.lsqt.components.context.spi.bean.BeanFactory;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.DbException;
import org.lsqt.components.mvc.spi.UrlMappingDefinition;
import org.lsqt.components.mvc.spi.exception.ApplicationException;
import org.lsqt.components.mvc.spi.impl.AnnotationUrlMappingRoute;
import org.lsqt.components.mvc.util.ActionFormUtil;
import org.lsqt.components.mvc.util.ParameterNameUtil;
import org.lsqt.components.mvc.util.ViewResolveFtlUtil;
import org.lsqt.components.mvc.util.ViewResolveJSONUtil;
import org.lsqt.components.util.ExceptionUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.User;
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
public class ApplicationFilter implements Filter{
	private static final Logger logger = LoggerFactory.getLogger(ApplicationFilter.class);
	
	private static final String  CHARACTER_ENCODING_UTF8 = "UTF-8";
	
	
	private static Set<String> URI_STATIC=new HashSet<>();
	private static Set<String> URI_ESCAPE=new HashSet<>();
	private static Set<String> URI_ANONYMOUS=new HashSet<>();
	
	
	private static volatile boolean isContainerInit = false;
	
	private static ExecutorService  executor = Executors.newSingleThreadExecutor(); 
	
	private final AnnotationBeanFactory factory = new AnnotationBeanFactory();
	private AnnotationUrlMappingRoute router ;
	private org.lsqt.components.db.Db db;
	
	private PlatformDb platformDb;
	
	private void initContainer(){
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
		platformDb = factory.getBean(PlatformDb.class);
	}
	
	
	public void init(FilterConfig filterConfig) throws ServletException {

		String staticRes = filterConfig.getInitParameter("static");
		if (StringUtil.isNotBlank(staticRes)) {
			URI_STATIC.addAll(StringUtil.split(String.class, staticRes, ","));
		}
		
		String escape = filterConfig.getInitParameter("escape");
		if (StringUtil.isNotBlank(staticRes)) {
			URI_ESCAPE.addAll(StringUtil.split(String.class, escape, ","));
		}
		
		String anonymous = filterConfig.getInitParameter("anonymous");
		if (StringUtil.isNotBlank(staticRes)) {
			URI_ANONYMOUS.addAll(StringUtil.split(String.class, anonymous, ","));
		}
		

		executor.execute(() -> {
			long begin = System.currentTimeMillis();
			initContainer();
			logger.info(" --- container stated cost " + (System.currentTimeMillis() - begin)+"(ms) ~!!!");
		});
	}

	
	
	
	/**
	 * 静态资源直接返回响应
	 * @param request
	 * @return
	 */
	protected  boolean isStatic(HttpServletRequest request){
		final String uri = request.getRequestURL()+"";

		if(uri.endsWith(".ico")) return true;
		if(uri.endsWith(".css")) return true;
		if(uri.endsWith(".js")) return true;
		
		if(uri.endsWith(".gif")) return true;
		if(uri.endsWith(".png")) return true;
		if(uri.endsWith(".swf")) return true;
		if(uri.endsWith(request.getContextPath()+"/")) return true;
		
		
		return false;
	}
	
	/**
	 * 脱离MVC管理的URL直接让后续的Filter或Servlet处理
	 * @param request
	 * @return
	 */
	protected static boolean isEscape(HttpServletRequest request){
		//request.getRequestURI()
		return false;
	}
	
	/**
	 * 匿名访问的URL直接让后续的Filter或Servlet处理
	 * @param request
	 * @return
	 */
	protected static boolean isAnonymous(HttpServletRequest request){
		
		return false;
	}
	
	
	protected boolean isLogin(HttpServletRequest request) {
		
		return true;
	}
	
	
	public static void main(String[] args) {
	      // 按指定模式在字符串查找
	      String line = "This order was placed for QT3000! OK?";
	      String pattern = "(\\D*)(\\d+)(.*)";
	 
	      // 创建 Pattern 对象
	      Pattern r = Pattern.compile(pattern);
	 
	      // 现在创建 matcher 对象
	      Matcher m = r.matcher(line);
	      if (m.find( )) {
	    	 System.out.println(m.group());
	    	 
	         System.out.println("Found value: " + m.group(0) );
	         System.out.println("Found value: " + m.group(1) );
	         System.out.println("Found value: " + m.group(2) );
	      } else {
	         System.out.println("NO MATCH");
	      }
	      
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
			System.out.println(" --- 系统正在启动...");
			filterChain.doFilter(request, response);
			return ;
		}
		
		if(isStatic(request)){ // 静态资源URI，直接返回到客户端
			filterChain.doFilter(request, response);
			return ;
		}
		
		if(isEscape(request)){ // 脱离MVC容器客理的URI，让后续Servlet或处理器处理
			filterChain.doFilter(request, response);
			return ;
		}
		
		/*
		if(isLogin(request) == false){
			boolean isExe = false;
			List<String> action = new ArrayList<>();
			
			//匿名用户访问
			action.add("/user/login");
			action.add("/user/save_or_update");
			
			for(String e: action){
				if(request.getRequestURI().toString().endsWith(e)){
					isExe = true;
				}
			}
			
			if(isExe == false) {
				String path = request.getContextPath();//获得上下文的路径,得到web项目
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
				response.sendRedirect(basePath+"index.html");
				return ;
			}
		}*/
		
		//logger.info(" --- "+ request.getRequestURL());
		setCharacterEncoding(request,response);
		
		//1.构建formMap
		//2.处理请求的URL与Controller的方法映射
		//3.前置后置处理，拦截器处理
		buildContextMap(request,response);
		
		buildFormMap(request,response);
		
		
		response.addHeader("Content-Type","text/html; charset=utf-8");
		response.addHeader("Date",new Date().toString());
		response.addHeader("Cache-Control","private");
		
		
		boolean isInvokedOk = true;
		Closeable out = null;
		try {
			out = buildInvokeParam(request,response);
		} catch (Exception e) {
			//打印到控制台
			e.printStackTrace();
			
			
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
			//logger.debug(request.getRequestURL()+" ===> "+ContextUtil.getFormMap()+"\n\n\n");
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
	private Closeable buildInvokeParam(HttpServletRequest request,HttpServletResponse response) throws ApplicationException{
		String uri = request.getRequestURI();
		logger.debug(request.getRequestURL()+" ===> "+ContextUtil.getFormMap());
		
		
		
		//bugFix: 去掉工程名前缀，如: http://ip:poart/工程名(也就是context)/user/login
		String ctx = request.getContextPath();
		uri = uri.substring(ctx.length(), uri.length());
	
		final UrlMappingDefinition urlMappingDef = router.find(uri);
		if (urlMappingDef == null) {
			return null;
		}
		
		if (urlMappingDef.getMethod() == null) {
			throw new ApplicationException("have not found url mapping definition method to invoke~!");
		}

		Object controller = factory.getBean(urlMappingDef.getControllerClass());
		
		if (controller == null) {
			throw new ApplicationException("have not found url mapping of the controller~!");
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
				logger.debug(" --- parse RequestPayload : "+request.getRequestURI()+"===>" + ContextUtil.getFormMap());
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
		Class<?>[] types = urlMappingDef.getMethod().getParameterTypes();
		
		
		
		
		List<String> methodInputParamNames = ParameterNameUtil.getParameterNames(urlMappingDef.getMethod());
		if(types.length != methodInputParamNames.size()) {
			throw new ApplicationException("param value count and param name count are't equal~!");
		}
		
		// 1.填充表单
		List<Object> methodInputParamValues = new ArrayList<Object>();
		for (int i=0;i<methodInputParamNames.size();i++) {
			Parameter param = urlMappingDef.getMethod().getParameters()[i];

			Class<?> paramType = types[i];
			String paramName = methodInputParamNames.get(i);
			Object paramValue = null;
			
			if (ActionFormUtil.isCanBeBaseType(paramType)) { // 处理常规类型
				paramValue = ActionFormUtil.prepareBaseValue(paramType,ContextUtil.getFormMap().get(paramName));
				
			} else if(ActionFormUtil.isCanBeDate(param)){ // 处理日期类型
				try {
					paramValue = ActionFormUtil.prepareDateValue(param,ContextUtil.getFormMap().get(paramName));
				} catch (ParseException e) {
					throw new ApplicationException("prepare Date Value fail~!",e);
				}
				
			} else if (ActionFormUtil.isCanBeBeanType(paramType)) { // 处理bean类型
				try {
					Object formBean = paramType.newInstance();
					paramValue = formBean;
					ActionFormUtil.fillBean(formBean, ContextUtil.getFormMap());
				} catch (Exception e) {
					throw new ApplicationException("action form to bean fail~!",e);
				}
			}
			
			methodInputParamValues.add(paramValue);
		}
		
		
		// 2.Action调用
		final class Result {
			public Object invokedResult;
		}
		final Result result = new Result();
		try {
			db.executePlan(()-> {
				try {
					long start = System.currentTimeMillis();
					
					result.invokedResult = urlMappingDef.getMethod().invoke(controller,methodInputParamValues.toArray());
					
					long end = System.currentTimeMillis();
					logger.info(" --- thread-id:"+Thread.currentThread().getId()+" Controller["+controller.getClass().getName()+"#"+urlMappingDef.getMethod().getName()+"("+methodInputParamValues+")] invok cost:"+(end-start)+"(ms)");
				
				} 
				catch (DbException e){
					throw e;
				}
				catch (ApplicationException e){
					throw e;
				}
				catch (Exception e) {
					throw new ApplicationException(e);
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ApplicationException(ex);
		} finally{
			db.close();
			platformDb.threadConnectionDestory();
		}
	
		// 3.展现视图
		Closeable closeable = null;
		try {
			Method method = urlMappingDef.getMethod();
			RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
			if (requestMapping == null) {
				return null;
			}

			View view = requestMapping.view();
			if (view == View.FTL) {
				PrintWriter out = response.getWriter();
				ViewResolveFtlUtil.resolve(out, urlMappingDef, result.invokedResult);

				closeable = out;

			} else if (view == View.JSON) {
				OutputStream out = response.getOutputStream();
				ViewResolveJSONUtil.resolve(out, urlMappingDef, result.invokedResult);
				response.setContentType("application/json; charset=utf-8");
				closeable = out;

			} else if (view == View.JSP) {
				String viewPrefix = requestMapping.path();
				if(!viewPrefix.endsWith("/")) {
					viewPrefix+="/";
				}
				Object rs = result.invokedResult;
				if(rs instanceof String) {
					String template = rs.toString();
					if(!template.endsWith(".jsp")) {
						template += ".jsp";
					}
					
					if(template.startsWith("redirect:")) {
						response.sendRedirect(viewPrefix+template);
					}
					request.getRequestDispatcher(viewPrefix+template).forward(request, response);
				}
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} 
		return closeable;
	}
	

	
	private void buildContextMap(HttpServletRequest request,HttpServletResponse response) {
		
		ContextUtil.getContextMap().put(ContextUtil.CONTEXT_REQUEST_OBJECT, request);
		ContextUtil.getContextMap().put(ContextUtil.CONTEXT_RESPONSE_OBJECT, response);
		
		// ---------------------------------------------测试代码,注意删除  Begin--------------------------------------------
		/*
		final Long testUserId=3890L;
		final String testUserName="刘泉志";
		final String testLoginNo="lqz";
		ContextUtil.getContextMap().put(ContextUtil.CONTEXT_LOGIN_ID_OBJECT,testUserId); //系统默主以刘泉志登陆系统
		ContextUtil.getContextMap().put(ContextUtil.CONTEXT_LOGIN_NAME_OBJECT,testUserName);
		User user= new User();
		user.setUserId(testUserId);
		user.setUserName(testUserName);
		user.setLoginNo(testLoginNo);
		ContextUtil.getContextMap().put(ContextUtil.CONTEXT_LOGIN_USER_OBJECT, user);
		*/
		// ---------------------------------------------测试代码,注意删除  End----------------------------------------------
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
		
	}
	
}
