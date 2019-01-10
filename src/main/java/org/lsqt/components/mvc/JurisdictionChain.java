package org.lsqt.components.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.permission.JurisdictionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 数据查询权限处理器
 * @author mm
 *
 */
public class JurisdictionChain implements Chain{
	private static final Logger log = LoggerFactory.getLogger(JurisdictionChain.class);
	
	private boolean enable = true;
	private int order = 900;
	private int state = STATE_IS_CONTINUE_TO_EXECUTE; // 数据查询权限不影响执行链的中断！！！
	
	
	private Configuration configuration;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private JurisdictionHandler handler;
	
	public JurisdictionChain(Configuration configuration,HttpServletRequest request,HttpServletResponse response) {
		this.configuration = configuration;
		this.request = request;
		this.response = response;
	}

	public boolean isEnable() {
		return this.enable;
	}
	
	public int getOrder() {
		 
		return this.order;
	}

	public int getState() {
		 
		return this.state;
	}

	
	public Object handle() throws Exception {
		if (handler == null) {
			JurisdictionHandler handler = configuration.getBeanFacotry().getBean(JurisdictionHandler.class);
			if (handler != null) {
				if (handler.isEnable()) {
					handler.handle(configuration.getBeanFacotry());
				}
			}
		}
		return null;
	}
	
}

