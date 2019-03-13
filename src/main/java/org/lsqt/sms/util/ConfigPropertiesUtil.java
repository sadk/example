package org.lsqt.sms.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropertiesUtil {
	static final Logger log = LoggerFactory.getLogger(ConfigPropertiesUtil.class);
	
	private static String PROP_FILE = "/config.properties";

	/**
	 * 
	 * @param classpathConfigFile
	 */
	public static void setPropFile(String classpathConfigFile) {
		PROP_FILE = classpathConfigFile;
	}
	
	
	private static Properties prop = new Properties();

	public static String getValue(String key) {
		if (prop.isEmpty()) {
			synchronized (ConfigPropertiesUtil.class) {
				try {
					InputStream in = new ConfigPropertiesUtil().getClass().getResourceAsStream(PROP_FILE);

					prop.load(in);
				} catch (IOException e) {
					log.error(e.getMessage());
					throw new RuntimeException(e.getCause());
				}
			}
		} else {
			return prop.getProperty(key);
		}

		log.debug("配置文件（config.properties）：\n" + JSON.toJSONString(prop, true));
		return prop.getProperty(key);
	}

}
