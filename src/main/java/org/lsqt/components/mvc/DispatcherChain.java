package org.lsqt.components.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.mvc.impl.UrlMappingDefinition;
import org.lsqt.components.mvc.util.RequestUtil;
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
	private int state = STATE_NO_WORK;
	
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
		
		Chain formMapBindingChain = new FormMapBindingChain(request, response); //绑定表单数据:Form URL
		Chain formMapBindingPayloadChain = new FormMapBindingPayloadChain(configuration,request, response); //绑定表单数据:equest Payload格式：ContentType: application/json
		
		
		Chain verificationCodeChain = new VerificationCodeChain(request, response); //是否验证码验证通过（默认无验证码）
		Chain authenticationChain = new AuthenticationChain(configuration,request,response);//用户是否登陆
		Chain globalBeforeRequestChain = new GlobalBeforeRequestChain(request, response);//全局请求处理
		
		Chain authenticationDataQueryChain = new JurisdictionChain(configuration,request,response);//用户数据查询权限
		
		Chain beforeHandleChain = new BeforeHandleChain(configuration, request);
		Chain controllerInvokeChain = new ControllerInvokeChain(configuration, request);
		
		chainList.add(startUpRequestChain);
		chainList.add(anonymousAccessChain);
		chainList.add(characterEncodingChain);
		chainList.add(contextMapBindingChain);
		chainList.add(formMapBindingChain);
		chainList.add(formMapBindingPayloadChain);
		chainList.add(verificationCodeChain);
		chainList.add(authenticationChain);
		chainList.add(globalBeforeRequestChain);
		chainList.add(authenticationDataQueryChain);
		chainList.add(beforeHandleChain);
		chainList.add(controllerInvokeChain);
		
		Object modelAndView = null;
		for (Chain chain : chainList) {
			
			modelAndView = chain.handle();

			//正在启动或匿名访问
			if (chain.getState() == Chain.STATE_IS_STARTING 
					|| chain.getState() == Chain.STATE_IS_STATIC_OR_ESCAPE_ACCESS) {
				this.filterChain.doFilter(request, response);
				return null;
			}
			
			//已redirect
			if (chain.getState() == Chain.STATE_IS_REDIRECTED) { // 如:已 send redirect!
				return null;
			}

		}
		
		// 选择相应的视图呈现
		final UrlMappingDefinition urlMapping = configuration.getUrlMappingRoute().find(RequestUtil.getRequestURI(request));
		ViewHandler viewHandler = new ViewSelectHandler(request, response);
		viewHandler.resolve(urlMapping, modelAndView);
		
	 
		if (!response.isCommitted()) {
			filterChain.doFilter(request, response);
		}
		
		return null;
	}

}

