package org.lsqt.components.mvc;

import org.lsqt.components.context.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 全局固定配置 
 * @author mingmin.yuan
 *
 */
public class GlobalConstConfigInit implements Order,Initialization{
	private static final Logger log = LoggerFactory.getLogger(GlobalConstConfigInit.class);
	
	private int order = 0;

	public int getOrder() {
		return order;
	}


	public void setOrder(int order) {
		this.order = order;
	}

	public void init() {
		
		log.info("预初使化全局固定配置 ...");
	}

}
