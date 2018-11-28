package org.lsqt.components.log;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;






/**
 * 用户日志跟踪器工厂
 * 说明:默认使用日志组件的实现，后续再使用其它实现，可自由切换
 * 后续版本改进，
 *     1.日志写入内存数据库 
 *     2.添加内置查看操作界面
 *     3.内存数据库可通过操作界面，持久到物理MySql数据库
 *     4.物理数据库可归档日志，自动归档和手动归档
 *     5.添加内置web访问，需加一个内嵌web服务器
 *     
 * @author Sky
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public final class Configuration {
	
	//日志实例构造器
	private static List<Constructor> constructors=new ArrayList<Constructor>() ;
	
	//日志框架整体实现类
	private static Map<String,String> configImpl=new ConcurrentHashMap<String, String>();
	
	private static Map<String,String> configMap=new ConcurrentHashMap<String, String>();
	
	private static Map<String,Constructor> configEnable=new ConcurrentHashMap<String,Constructor>();
	
	//默认console、database、file实现
	 static {
		 configImpl.put("log.writer.console", "org.lsqt.components.log.ConsoleWriter");
		 configImpl.put("log.writer.database", "org.lsqt.components.log.DbWriter"); 
		 configImpl.put("log.writer.file", "org.lsqt.components.log.FileWriter");
        try {
        	for(String key: configImpl.keySet()){
				Class implClass =  Loader.classForName(configImpl.get(key));
                 Constructor  constructor = implClass.getConstructor(new Class[] { Class.class });
                 constructors.add(constructor);
                 
                 configEnable.put(key, constructor);
        	}
        	 
        } catch (Throwable t) {
        	t.printStackTrace();
        }
	}
	
	 /**
	  * 获取日志框架配置
	  * @return
	  */
	 private static final String configPath="/log.properties";
	 public static Map<String,String> getConfigMap(){
		if (configMap.isEmpty()) {
			Properties temp = new Properties();
			try {
				temp.load(Configuration.class.getResourceAsStream(configPath));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Set<Entry<Object, Object>> set = temp.entrySet();
			
			Map<String,String> map=new HashMap<String,String>();
			for(Entry e: set){
				map.put(e.getKey().toString(), e.getValue()==null? "":e.getValue().toString());
			}
			return map;
		}
		 return configMap;
	 }
	 
	 /**
	  * 获取日志器
	  * @param clazz -
	  * @return -
	  */
	 public static List<Log> getLogs(Class clazz){
		 List<Log> list=new ArrayList<Log>();
        try {
        	for(Constructor<?> c: constructors){
        		if(isConstructorEnable(c)){
	        		Log e= (Log) c.newInstance(new Object[] { clazz }); //实例化具体日志实现
	        		list.add(e);
        		}
        	}
        } catch (Throwable t) {
            throw new RuntimeException("Error creating logger for class " + clazz + ".  Cause: " + t, t);
        }
        
        return list;
    }
	 
	 private static boolean isConstructorEnable(Constructor c){
		Map<String,String> config = getConfigMap();
		final String ROOT="log.writer.logger";
		String setted=config.get(ROOT);
		if(setted!=null && (!"".equals(setted.trim()))){
			for(String n: setted.split(",")){
				String key="log.writer.".concat(n);
				if(configEnable.get(key).toString().equalsIgnoreCase(c.toGenericString())){
					return true;
				}
			}
		}
		 return false;
	 }
}
