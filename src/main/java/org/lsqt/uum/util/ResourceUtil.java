package org.lsqt.uum.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 
 * @author 获取属性文件
 *
 */
public class ResourceUtil {
	private static Properties prop = new Properties();

	public static String getValue(String key) {
		Object value = prop.get(key);
		
		if (value == null) {
			Resource resource = new ClassPathResource("config.properties");
			try {
				prop.load(resource.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		value = prop.get(key);
		return value == null ? "" : value.toString();
	}
	
	public static void main(String ...args){
		 System.out.println(getValue("sso.login.url"));
	}
}
