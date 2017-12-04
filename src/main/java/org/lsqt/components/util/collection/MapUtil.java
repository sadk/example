package org.lsqt.components.util.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MapUtil {
	
	
	public static <K> List<K> toKeyList(Map<K,?> map){
		List<K> list=new ArrayList<K>();
		Set<?> entry=map.entrySet();
		for(Object e:entry){
			list.add((K)((Entry) e).getKey());
		}
		return list;
	}
	
	
	public static <V> List<V> toValueList(Map<?,V> map){
		List<V> list=new ArrayList<V>();
		Set<?> entry=map.entrySet();
		for(Object e:entry){
			list.add((V) ((Entry) e).getValue());
		}
		return list;
	}
	
	public static void main(String ars[]){
		Map<String,Long> map=new HashMap();
		map.put("aaa", 10L);
		map.put("bbb", 20L);
		
		System.out.println(toKeyList(map));
		
		System.out.println(toValueList(map));
		
	}
}
