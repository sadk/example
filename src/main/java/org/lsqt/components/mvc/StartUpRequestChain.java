package org.lsqt.components.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 开始请求,第一链
 * @author mm
 *
 */
public class StartUpRequestChain implements Chain {
	private static final Logger log = LoggerFactory.getLogger(StartUpRequestChain.class);
	
	private int order = 0;
	private boolean enable = true;
	private int state = STATE_DO_NEXT_NOT_ALLOW;
	
	private Configuration configuration;
	public StartUpRequestChain(Configuration configuration) {
		this.configuration = configuration ;
	}
	
	public boolean isEnable() {
		return enable;
	}

	public int getOrder() {
		return this.order;
	}

	public int getState() {
		return state;
	}

	public Object handle() throws Exception {
		if (configuration.isInitialized()) {
			this.state = STATE_DO_NEXT_CONTINUE;
		} else {
			this.state = STATE_DO_NEXT_BREAK;
		}

		return null;
	}
}

