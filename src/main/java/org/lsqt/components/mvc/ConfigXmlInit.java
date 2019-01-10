
package org.lsqt.components.mvc;

import org.lsqt.components.context.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载框架xml配置文件
 * @author mm
 *
 */
public class ConfigXmlInit implements Order,Initialization {
	private static final Logger log = LoggerFactory.getLogger(ConfigXmlInit.class);
	
	private int order = 300;
	
	/**
	 * 框架xml配置文件路径
	 */
	private String[] paths = null;
	
	public ConfigXmlInit(String ...paths) {
		this.paths = paths;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public void init() {
		if (this.paths == null || this.paths.length == 0) {
			return;
		}

		for (String path : paths) {
			log.info(String.format("loading xml file: %s",path));
		}
		
	}
}

