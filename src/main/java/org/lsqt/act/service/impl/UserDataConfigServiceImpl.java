package org.lsqt.act.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.lsqt.act.model.ApproveObjectData;
import org.lsqt.act.model.UserDataConfig;
import org.lsqt.act.model.UserDataConfigParam;
import org.lsqt.act.model.UserDataConfigParamQuery;
import org.lsqt.act.model.UserDataMapping;
import org.lsqt.act.model.UserDataMappingQuery;
import org.lsqt.act.service.UserDataConfigService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.ExceptionUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

@Service
public class UserDataConfigServiceImpl implements UserDataConfigService{
	static final Logger log = LoggerFactory.getLogger(UserDataConfigServiceImpl.class);
	
	@Inject private Db db;
	
	public void deleteById(Long ...ids) {
		if(ids!=null && ids.length>0) {
			for(Long id: ids) {
				// 删除结果集映射配置
				db.executeUpdate(String.format("delete from %s where config_id = ?", db.getFullTable(UserDataMapping.class)),id);
				
				// 删除参数配置
				db.executeUpdate(String.format("delete from %s where config_id = ?", db.getFullTable(UserDataConfigParam.class)),id);
				
				// 删除本身
				db.executeUpdate(String.format("delete from %s where id = ?", db.getFullTable(UserDataConfig.class)),id);
			}
		}
	}
	
	public ApproveObjectData viewData(Long configId){
		ApproveObjectData rs = new ApproveObjectData();
		
		UserDataConfig config = db.getById(UserDataConfig.class, configId);
		if(config == null) {
			return rs;
		}
 
		return viewRemoteData(config);
	}

	
	// --------------------------------------------------- 辅助方法  -----------------------------------------------------------
	/**
	 * 预览jar包接口数据
	 * @param config
	 * @return
	 */
	final ApproveObjectData viewLocalData(UserDataConfig config) {
		ApproveObjectData data = new ApproveObjectData();
		
		return data;
	}
	
	/**
	 * 预览rest接口数据
	 * @param config
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	final ApproveObjectData viewRemoteData(UserDataConfig config) {
		
		StringBuilder url = new StringBuilder(); // 获取url动态拼装接口地址
		url.append(config.getUrl()+"?time="+System.currentTimeMillis());
		
	 
		UserDataConfigParamQuery paramQuery = new UserDataConfigParamQuery();
		paramQuery.setConfigId(config.getId().toString());
		List<UserDataConfigParam> paramList = db.queryForList("queryForPage", UserDataConfigParam.class, paramQuery);
		for (UserDataConfigParam e : paramList) {
			url.append("&"+e.getParamCode()+"=" + e.getParamValue());
		}
		
		
		ApproveObjectData data = request(url.toString(), config.getMethod());
		if (ApproveObjectData.STATUS_OK == data.getStatus()) {
			Map<String,Object> remoteMap = new HashMap<>();
			try{
				remoteMap = JSON.parseObject(data.getOriginalData()+"", Map.class); //原始数据Map对象
			}catch(Exception ex) {
				data.setStatus(ApproveObjectData.STATUS_FAIL);
			}
			
			if (remoteMap == null || remoteMap.isEmpty()) {
				return data;
			}
			
		
			List<Map<String,Object>> dataList = new ArrayList<>();  // 原始数据
			
			if (StringUtil.isNotBlank(config.getDataProp())) {  // 指定取哪个字段数据
				for (String e : config.getDataProp().split("\\.")) {
					Object temp = remoteMap.get(e);

					if (temp instanceof Map) {
						remoteMap = (Map<String, Object>) remoteMap.get(e);
					} else if (temp instanceof List) {
						dataList = (List<Map<String, Object>>) temp;
					}
				}

			} else { // 没指定，就直接取返回值Map
				dataList.add(remoteMap);
			}
			
			if(dataList!=null) {
				List<Map<String,Object>> mappedList = new ArrayList<> ();
				UserDataMappingQuery mapQuery = new UserDataMappingQuery();
				mapQuery.setConfigId(config.getId().toString());
				List<UserDataMapping> mapList = db.queryForList("queryForPage", UserDataMapping.class, mapQuery);
				
				for(Map<String,Object> row: dataList) {
					Map<String, Object> temp = new HashMap<>();
					for (UserDataMapping m : mapList) {
						temp.put(m.getLocalField(), row.get(m.getRemoteField()));
					}
					mappedList.add(temp);
				}
				data.setMappedData(mappedList); // 映射后的数据
			}
		}
		
		return data;
	}
	
	final ApproveObjectData request(String url,String method) {
		log.info(" --- url:"+url+", method:"+method);
		StringBuilder message = new StringBuilder();
		message.append(" --- 请求的URL:   "+url+"\n --- 方法(默认get方法):   "+method);
		
		ApproveObjectData data = new ApproveObjectData();
		
		String json = null;
		if (StringUtil.isBlank(url,method)) {
			return data;
		}
		
		HttpUriRequest request = null;
		HttpResponse response = null;
		try{
			CloseableHttpClient httpclient = HttpClientBuilder.create().build();
			
			
			if ("post".equalsIgnoreCase(method)) {
				request = new HttpPost(url);
			}
			else if("get".equalsIgnoreCase(method)) {
				request = new HttpGet(url);
			}
			else if ("delete".equalsIgnoreCase(method)) {
				request = new HttpDelete(url);
			}
			else if ("put".equalsIgnoreCase(method)) {
				request = new HttpPut(url);
			}
			else if ("head".equalsIgnoreCase(method)) {
				request = new HttpHead(url);
			}
			else if ("trace".equalsIgnoreCase(method)) {
				request = new HttpTrace(url);
			}
			else if ("options".equalsIgnoreCase(method)) {
				request = new HttpOptions(url);
			}
			else if ("lock".equalsIgnoreCase(method)) {
				throw new UnsupportedOperationException("不支持lock请求方式");
			}
			else if ("mkcol".equalsIgnoreCase(method)) {
				throw new UnsupportedOperationException("不支持mkcol请求方式");
			}
			else if ("move".equalsIgnoreCase(method)) {
				throw new UnsupportedOperationException("不支持move请求方式");
			}
			else {
				request = new HttpGet(url); // 默认为get请求
			}
			
	        response = httpclient.execute(request);
			json = EntityUtils.toString(response.getEntity());
			log.info(json);
			data.setOriginalData(json);
			data.setStatus(ApproveObjectData.STATUS_OK);
			
			StatusLine sl = response.getStatusLine();
			if(response!=null && sl!=null) {
				message.append("\n ---返回信息:   "+sl);
				data.setStatus(response.getStatusLine().getStatusCode());
			}
			data.setMessage(message.toString());
		}catch(Exception ex) {
			ex.printStackTrace();
			
			if(response!=null && response.getStatusLine()!=null) {
				message.append("\n ---返回信息:   "+response.getStatusLine());
			}
			
			message.append("\n --- 请求异常:   "+ExceptionUtil.getStackTrace(ex));
			data.setMessage(message.toString());
		}
		
		return data;
	}

}


