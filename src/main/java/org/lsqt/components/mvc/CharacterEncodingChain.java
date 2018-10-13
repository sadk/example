package org.lsqt.components.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符集设置
 * @author mm
 *
 */
public class CharacterEncodingChain implements Chain{
	private static final Logger log = LoggerFactory.getLogger(CharacterEncodingChain.class);
	
	private boolean enable = true;
	private int order = 300;
	private int state = STATE_NO_WORK;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private String characterEncoding = "utf-8";
	
	public CharacterEncodingChain(HttpServletRequest request,HttpServletResponse response) {
		this.request = request;
		this.response = response;
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
		response.setCharacterEncoding(characterEncoding);
		 
		request.setCharacterEncoding(characterEncoding);
		 
		this.state = STATE_IS_CONTINUE_TO_EXECUTE;
		
		log.debug("characterEncoding = {}",characterEncoding);
		return null;
	}

	public String getCharacterEncoding() {
		return characterEncoding;
	}

	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}
	
}

