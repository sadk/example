package org.lsqt.components.ext.ueditor;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 百度富文本编辑器,图片路径等bug修复
 * @author yuanmm
 */
public class UrlFix {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String toJSONFix(String json) {
		
		Map map = JSON.parseObject(json, Map.class);
		if(map.containsKey("state") && "SUCCESS".equals(map.get("state"))){
			if (map.containsKey("list")) {
				List array = (JSONArray) map.get("list");
				if (array != null && array.isEmpty() == false) {
					for (Object e : array) {
						if (e instanceof Map) {
							Map ele = ((Map) e);
							if (ele.containsKey("url")) {
								String url = ele.get("url") + "";
								if (url.startsWith("//")) {
									String temp = url.substring(1, url.length());
									ele.put("url", temp);
								} else if (url.startsWith("\\")) {
									String temp  = "/"+url.substring(1, url.length());
									ele.put("url", temp);
								}
							}
						}
					}
				}
			}
			
			json = JSON.toJSONString(map);
		}
		return json;
	}
	
	
	public static void main(String args[]) {
		String json = "{\"state\": \"SUCCESS\",\"total\": 1,\"start\": 0, list: [{\"state\": \"SUCCESS\",\"url\": \"\\\\ueditor/jsp/upload/image/20160522/1463848324324016394.png\"},{\"state\": \"SUCCESS\",\"url\": \"//ueditor/jsp/upload/image/20160522/1463848324324016395.png\"}  ]}";
		
		
		
		System.out.println(UrlFix.toJSONFix(json));
		
	}
}
