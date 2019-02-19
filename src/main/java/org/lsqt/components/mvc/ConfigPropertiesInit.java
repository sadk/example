package org.lsqt.components.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.lsqt.components.context.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 初使化配置文件（属性文件)
 * @author mm
 *
 */
public class ConfigPropertiesInit implements Order,Initialization {
	private static final Logger log = LoggerFactory.getLogger(ConfigPropertiesInit.class);
	private int order = 200;
	
	/**
	 * 框架的配置文件properties的路径
	 */
	private String[] paths = null;
	
	private static Properties PROP_ALL = new Properties();

	public ConfigPropertiesInit(String... paths) {
		this.paths = paths;
	}
	
	
	@Override
	public void init() throws IOException {
		if (this.paths == null || this.paths.length == 0) {
			return;
		}

		for (String path : paths) {
			Properties prop = new Properties();
			synchronized (ConfigPropertiesInit.class) {

				InputStream in = new ConfigPropertiesInit().getClass().getResourceAsStream(path);
				log.info(String.format("loading default config file: %s",path));
				try{
					prop.load(in);
				} catch(Exception ex) {
					log.info("has no default config file (path = {})",path);
				}
			}
			PROP_ALL.putAll(prop);
		}

	}



	@Override
	public int getOrder() {
		return this.order;
	}
	
	/**
	 * 获取框架配置文件值
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return PROP_ALL.getProperty(key);
	}

}

