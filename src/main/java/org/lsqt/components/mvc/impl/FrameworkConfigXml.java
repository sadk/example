package org.lsqt.components.mvc.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author admin
 *
 */
public interface FrameworkConfigXml {
	List<Pattern> getAnonymous() ;
	List<Pattern> getPermission();
	
	/**URL表示**/
	class Pattern {
		public String name;
		public String value;
		public String module;
	}
	
	/**数据库节点表示**/
	class Node{
	
		/** 读写分离类型 **/
		public static final String TYPE_READ="read";
		public static final String TYPE_WRITE="write";
		
		
		public String id;
		public String clazz;
		public String initMethod;
		public String destroyMethod;
		public String type;
		public Map<String,Object> properties=new HashMap<>();
	}
}
