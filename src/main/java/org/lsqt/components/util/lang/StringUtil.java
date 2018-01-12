package org.lsqt.components.util.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;


public class StringUtil {
	public static final String EMPTY="";
	
	private static final String PARTTERN_NUMBER= "^\\d+(\\.\\d+)?" ;
	public static boolean isNumeric(String text) {
		if(isBlank(text)){
			return false;
		}
		return Pattern.matches(PARTTERN_NUMBER, text);
		/*
		if (isBlank(text)) {
			return false;
		}
		final int sz = text.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(text.charAt(i)) == false) {
				return false;
			}
		}
		return true;
		*/
	}
	
	/**
	 * 入参全部为null或empty
	 * @param strs
	 * @return
	 */
	public static boolean isBlank(String ... strs){
		if(strs == null) return true;
		
		boolean isBlankAll = true;
		for (String e : strs) {
			if (e != null) {
				if (!"".equals(e.trim())) {
					return false;
				}
			}
		}
		 
		return isBlankAll;
	}
	
	/**
	 * 入参全部不能为null或empty
	 * @param strs
	 * @return
	 */
	public static boolean isNotBlank(String ... strs){
		if (strs == null) return false;

		for (String e : strs) {
			if(e==null || "".equals(e.trim())) return false;
			 
		}
		return true;
	}
	
	/**
	 * 默认以逗号分割，连接集合中的元素
	 * @param collection
	 * @param separator
	 * @return
	 */
	public static String join(Collection<?> collection){
    	return join(collection,",","","");
    }

	/**
	 * 用分割符连接集合中的元素
	 * @param collection
	 * @param separator
	 * @return
	 */
	public static String join(Collection<?> collection,String separator){
    	return join(collection,separator,"","");
    }
	
	/**
	 * 
	 * @param collection
	 * @param separator 
	 * @param prefix 每一个元素的前缀字符
	 * @param endfix 每一个元素的后缀字符
	 * @return
	 */
	public static String join(Collection<?> collection,String separator,String prefix,String endfix){
		if(collection.isEmpty()) return "";
		
		StringBuilder sb = new StringBuilder();
    	Iterator<?> i = collection.iterator();
    	sb.append(prefix+i.next()+endfix);
    	
    	while(i.hasNext()){
    		sb.append(separator);
    		sb.append(prefix+i.next()+endfix);
    	}
    	return sb.toString();
	}
	
	
	public static List<String> split(String text, String splitor) {
		if (text.startsWith(splitor)) {
			text = text.substring(splitor.length(), text.length());
		}
		return Arrays.asList(text.split(splitor));
	}
	
	public static void main(String[] args) {
		//String t=",6228,6232,";
		System.out.println(isNumeric("0143432432432424去2443423324.3234324"));
	}
	
	/**
	 * 字符串分割成数组
	 * @param type
	 * @param text
	 * @param splitor 
	 * @return
	 * @throws RuntimeException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> split(Class<T> type,String text,String splitor) throws RuntimeException{
		if (text == null || "".equals(text)) return new ArrayList<>(0);
		
		String [] strArr = text.split(splitor);
		
		if (String.class.isAssignableFrom(type)) {
			return new ArrayList(Arrays.asList(strArr));
		}  
		
		if (Byte.class.isAssignableFrom(type) || byte.class.isAssignableFrom(type)) {
			List<Byte> rs = new ArrayList<>();
			for (String e: strArr) {
				rs.add(Byte.valueOf(e));
			}
			return (List<T>) rs;
		} 
		
		if (Short.class.isAssignableFrom(type) || short.class.isAssignableFrom(type)) {
			List<Short> rs = new ArrayList<>();
			for (String e: strArr) {
				rs.add(Short.valueOf(e));
			}
			return (List<T>) rs;
		}
		
		if (Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type)) {
			List<Integer> rs = new ArrayList<>();
			for (String e: strArr) {
				rs.add(Integer.valueOf(e));
			}
			return (List<T>) rs;
		}   
		
		if (Long.class.isAssignableFrom(type) || long.class.isAssignableFrom(type)) {
			List<Long> rs = new ArrayList<>();
			for (String e: strArr) {
				rs.add(Long.valueOf(e));
			}
			return (List<T>) rs;
		}   
		
		if (Float.class.isAssignableFrom(type) || float.class.isAssignableFrom(type)) {
			List<Float> rs = new ArrayList<>();
			for (String e: strArr) {
				rs.add(Float.valueOf(e));
			}
			return (List<T>) rs;
		}
		
		if (Double.class.isAssignableFrom(type) || double.class.isAssignableFrom(type)) {
			List<Double> rs = new ArrayList<>();
			for (String e: strArr) {
				rs.add(Double.valueOf(e));
			}
			return (List<T>) rs;
		}
		
		if (Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type)) {
			List<Boolean> rs = new ArrayList<>();
			for (String e: strArr) {
				rs.add(Boolean.valueOf(e));
			}
			return (List<T>) rs;
		}
		
		if (java.lang.Character.class.isAssignableFrom(type) || char.class.isAssignableFrom(type)) {
			List<Character> rs = new ArrayList<>();
			for (String e: strArr) {
				rs.add(Character.valueOf(e.toCharArray()[0]));
			}
			return (List<T>) rs;
		}
		
		throw new RuntimeException("unsupport type: "+type);
	}
	
	
	public static String escapeSql(String str) {
		if (str == null) {
			return null;
		}
		return str.replace("'", "''");
	}
	
	public   void main1(String args[]){

		
		System.out.println(isNumeric("1112")+"xxxx");
		//System.out.println(StringEscapeUtils.escapeSql(sql));
		/*
		System.out.println(isNotBlank(null,null,null)); //false
		System.out.println(isNotBlank("","  ",null)); //false
		
		System.out.println(isNotBlank(null,""," 33  "));  //false
		System.out.println(isNotBlank("userSerivc3","")); //false
		System.out.println(isNotBlank("userSerivc3","111","  ")); //false
		System.out.println(isNotBlank("userSerivc3","111","sadfas")); //true
		*/
	}
}
