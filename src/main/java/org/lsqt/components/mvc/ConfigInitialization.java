package org.lsqt.components.mvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterConfig;

import org.lsqt.components.context.bean.BeanFactory;
import org.lsqt.components.mvc.impl.UrlMappingRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 框架配置初使化宏-->初使化--> 形成MVC组件
 * @author mm
 *
 */
public class ConfigInitialization implements Initialization  {
	private static final Logger log = LoggerFactory.getLogger(ConfigInitialization.class);
	
	private List<Initialization> macroList = new ArrayList<>();
	
	private FilterConfig filterConfig;
	
	public ConfigInitialization (FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
	
	private static String SERVLET_CONTEXT_REAL_PATH;
	
	/**
	 * 获取(Web容器)虚拟目录路径
	 * @return
	 */
	public static String getServletContextRealPath() {
		return SERVLET_CONTEXT_REAL_PATH;
	}
	
	@Override
	public void init() throws Exception {
		SERVLET_CONTEXT_REAL_PATH = filterConfig.getServletContext().getRealPath("/") ;
		
		Initialization bannerConfigInit = new BannerConfigInit(); 
		Initialization globalConstConfigInit = new GlobalConstConfigInit();
		Initialization webXmlConfigInit = new WebXmlConfigInit(filterConfig);
		Initialization configPropetiesInit = new ConfigPropertiesInit("/framework.cfg.properties");
		Initialization configXmlInit = new ConfigXmlInit("/framework.cfg.xml");
		Initialization configYmlInit = new ConfigYMLInit("/framework.cfg.yml");
		Initialization internalConstConfigInit = new InternalConstConfigInit(filterConfig);
		Initialization internalContainerInit = new InternalContainerInit();
		
		macroList.add(bannerConfigInit);
		macroList.add(globalConstConfigInit);
		macroList.add(webXmlConfigInit);
		macroList.add(configPropetiesInit);
		macroList.add(configXmlInit);
		macroList.add(configYmlInit);
		macroList.add(internalConstConfigInit);
		macroList.add(internalContainerInit);
		

		Order[] orders = new Order[macroList.size()];
		Arrays.sort(macroList.toArray(orders), (o1, o2) -> o1.getOrder() - o2.getOrder());

		for (Order e : orders) {
			((Initialization) e).init();
		}
		
	}
	
	
	// -----------------------------------------------------------------------------------
	/**
	 * 获取框架配置对象
	 * @return
	 */
	public Configuration getConfiguration() {
		Configuration config = new ConfigurationImpl(this.macroList); //各初使化组件
		return config;
	}
	
	class ConfigurationImpl implements Configuration {
		/**各初使化组件**/
		private List<Initialization> initializationList; 

		public ConfigurationImpl(List<Initialization> initializationList) {
			this.initializationList = initializationList;
		}
		
		/**
		 * 获取内部容器初使化组件
		 * @return
		 */
		private InternalContainerInit getInternalContainerInit() {
			for(Initialization e: initializationList) {
				if (e instanceof InternalContainerInit) {
					InternalContainerInit container = (InternalContainerInit) e;
					return container;
				}
			}
			return null;
		}
		
		private WebXmlConfigInit getWebXmlConfigInit() {
			for(Initialization e: initializationList) {
				if (e instanceof WebXmlConfigInit) {
					WebXmlConfigInit init = (WebXmlConfigInit) e;
					return init;
				}
			}
			return null;
		}
		
		public boolean isInitialized() {
			return getInternalContainerInit().isFinished();
		}
		
		public boolean isEnableLogin() {
			WebXmlConfigInit init = getWebXmlConfigInit();
			if (init !=null) {
				return init.isLoginEnabled();
			}
			return false;
		}

		public UrlMappingRoute getUrlMappingRoute() {
			return getInternalContainerInit().getUrlMappingRoute();
		}

		public BeanFactory getBeanFacotry() {
			return getInternalContainerInit().getBeanFactory();
		}

		public Set<String> getURIStatic() {
			return getWebXmlConfigInit().getURIStatic();
		}

		public Set<String> getURIEscape() {
			return  getWebXmlConfigInit().getURIEscape();
		}

		public Set<String> getURIAnonymous() {
			return getWebXmlConfigInit().getURIAnonymous();
		}
		
	}
}

