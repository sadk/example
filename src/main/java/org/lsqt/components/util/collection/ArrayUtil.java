package org.lsqt.components.util.collection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("rawtypes")
public class ArrayUtil {
	public static final List EMPTY_LIST = new ArrayList(0);
	
	public static String join(Collection<?> collection,String separator){
		if(collection == null || collection.size() == 0 || separator==null) return "";
		StringBuilder temp=new StringBuilder();
		
		Iterator<?>  iter = collection.iterator() ;
		
		int i = 0;
		while (iter.hasNext()) {
			i++;
			temp.append(iter.next());
			if(i != collection.size()) {
				temp.append(separator);
			}
		}
		return temp.toString();
	}
	
	public static String join(Object [] objs,String separator){
		if(objs == null || objs.length == 0) return "";
		StringBuilder temp=new StringBuilder();
		
		int i=0;
		for(Object e: objs) {
			i++;
			temp.append(e);
			if(i != objs.length) {
				temp.append(separator );
			}
		}
		return temp.toString();
	}
	
	public static String join(Object [] objs,String separator,boolean formatDate){
		if(objs == null || objs.length == 0) return "";
		
		StringBuilder temp=new StringBuilder();
		
		int i=0;
		for(Object e: objs) {
			i++;
			if(e!=null && java.util.Date.class.isAssignableFrom(e.getClass())) {
				Date dt = (Date)e;
				temp.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dt));
			}else {
				temp.append(e);
			}
			if(i != objs.length) {
				temp.append(separator );
			}
		}
		
		return temp.toString();
	}
	
	public static int sum(int [] array) {
		if (array == null) {
			throw new IllegalArgumentException("array can not be null");
		}
		int sum = 0;
		for(int e: array) {
			sum += e;
		}
		return sum;
	}
	
	public static String hello() {
		return "xxxxxxxxxx,hello";
	}
}
