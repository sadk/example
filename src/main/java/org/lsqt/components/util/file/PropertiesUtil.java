package org.lsqt.components.util.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class PropertiesUtil {
	static final Logger log = LoggerFactory.getLogger(PropertiesUtil.class);
	
	private static String PROP_FILE = "/syswin.properties";

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
			synchronized (PropertiesUtil.class) {
				try {
					InputStream in = new PropertiesUtil().getClass().getResourceAsStream(PROP_FILE);

					prop.load(in);
				} catch (IOException e) {
					log.error(e.getMessage());
					throw new RuntimeException(e.getCause());
				}
			}
		} else {
			return prop.getProperty(key);
		}

		log.debug("配置文件（syswin.properties）：\n" + JSON.toJSONString(prop, true));
		return prop.getProperty(key);
	}

}
