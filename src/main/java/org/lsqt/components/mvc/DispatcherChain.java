package org.lsqt.components.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.mvc.impl.UrlMappingDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 总处理器宏
 * @author mm
 *
 */
public class DispatcherChain implements Chain{
	private static final Logger log = LoggerFactory.getLogger(DispatcherChain.class);
	
	private boolean enable = true;
	private int order = 0;
	private int state = STATE_DO_NEXT_NOT_ALLOW;
	
	private ConfigInitialization configInitialization;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private FilterChain filterChain;
	
	private Configuration configuration;
	
	public DispatcherChain(ConfigInitialization configInitialization, FilterChain filterChain,
			HttpServletRequest request, HttpServletResponse response) {
		this.configInitialization = configInitialization;
		this.filterChain = filterChain;
		this.request = request;
		this.response = response;
		
		this.configuration = this.configInitialization.getConfiguration();
	}
	
	@Override
	public boolean isEnable() {		 
		return enable;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	@Override
	public int getState() {
		return this.state;
	}

	@Override
	public Object handle() throws Exception {
		List<Chain> chainList = new ArrayList<>();
		
		Chain startUpRequestChain = new StartUpRequestChain(configuration); //容器是否初使化
		Chain anonymousAccessChain = new AnonymousAccessChain(configuration,request); //是否是静态资源访问
		Chain characterEncodingChain = new CharacterEncodingChain(request, response); //设置请求编码格式
		Chain contextMapBindingChain = new ContextMapBindingChain(request, response); //绑定上下文对象（如request\response对象)
		Chain formMapBindingChain = new FormMapBindingChain(request, response); //绑定表单数据
		Chain verificationCodeChain = new VerificationCodeChain(request, response); //是否验证码验证通过（默认无验证码）
		Chain authenticationChain = new AuthenticationChain(configuration,request,response);//用户是否登陆
		Chain globalBeforeRequestChain = new GlobalBeforeRequestChain(request, response);//全局请求处理
		 
		Chain beforeHandleChain = new BeforeHandleChain(configuration, request);
		Chain controllerInvokeChain = new ControllerInvokeChain(configuration, request);
		
		chainList.add(startUpRequestChain);
		chainList.add(anonymousAccessChain);
		chainList.add(characterEncodingChain);
		chainList.add(contextMapBindingChain);
		chainList.add(formMapBindingChain);
		chainList.add(verificationCodeChain);
		chainList.add(authenticationChain);
		chainList.add(globalBeforeRequestChain);
		chainList.add(beforeHandleChain);
		chainList.add(controllerInvokeChain);
		
		Object modelAndView = null;
		for (Chain chain : chainList) {
			System.out.println(chain + "-----" + getRequestURI());
			modelAndView = chain.handle();

			if (chain.getState() == Chain.STATE_DO_NEXT_BREAK) { // 如:已 send redirect!
				return null;
			}

			if (chain.getState() != Chain.STATE_DO_NEXT_CONTINUE) {
				this.filterChain.doFilter(request, response);
				return null;
			}

		}
		
		// 选择相应的视图呈现
		final UrlMappingDefinition urlMapping = configuration.getUrlMappingRoute().find(getRequestURI());
		ViewHandler viewHandler = new ViewSelectHandler(request, response);
		viewHandler.resolve(urlMapping, modelAndView);
		
		
		
		if (!response.isCommitted()) {
			filterChain.doFilter(request, response);
		}
		
		return null;
	}

	/**
	 * 
	 * 获取请求的URI地址
	 * @param request
	 * @return
	 */
	private String getRequestURI() {
		String uri = request.getRequestURI();

		// bugFix: 去掉工程名前缀，如: http://ip:poart/工程名(也就是context)/user/login
		String ctx = request.getContextPath();
		uri = uri.substring(ctx.length(), uri.length());
		return uri;
	}
}

