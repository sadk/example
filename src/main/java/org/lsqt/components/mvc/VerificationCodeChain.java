package org.lsqt.components.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 验证码
 * @author mm
 *
 */
public class VerificationCodeChain implements Chain{
	private static final Logger log = LoggerFactory.getLogger(VerificationCodeChain.class);
	
	private boolean enable = true;
	private int order = 600;
	private int state = STATE_DO_NEXT_NOT_ALLOW;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	
	public VerificationCodeChain(HttpServletRequest request,HttpServletResponse response) {
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


	@Override
	public Object handle() {
		//暂时没有验证码!!!!
		
		this.state = STATE_DO_NEXT_CONTINUE;
		return null;
	}

}

