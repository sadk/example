package org.lsqt.sys.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.CacheManage;
import org.lsqt.sys.model.CacheManageQuery;
import org.lsqt.sys.model.CacheUrlParam;
import org.lsqt.sys.model.CacheUrlParamQuery;
import org.lsqt.sys.service.CacheManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Controller(mapping={"/cache_manage"})
public class CacheManageController {
	private static final Logger  log = LoggerFactory.getLogger(CacheManageController.class);
	@Inject private CacheManageService cacheManageService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<CacheManage> queryForPage(CacheManageQuery query) throws IOException {
		return cacheManageService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<CacheManage> getAll() {
		return cacheManageService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public CacheManage saveOrUpdate(CacheManage form) {
		return cacheManageService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return cacheManageService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/execute_refresh", "/m/execute_refresh" },text="后台刷新缓存")
	public Map<String,Object> executeRefresh(String ids) throws Exception{
		Map<String,Object> rs = new HashMap<String,Object>();
		if(StringUtil.isNotBlank(ids)) {
			List<Long> idList = StringUtil.split(Long.class,ids, ",");
			for (Long id : idList) {

				CacheManage model = db.getById(CacheManage.class, id);
				if (model != null) {
					CacheUrlParamQuery query = new CacheUrlParamQuery();
					query.setCacheId(model.getId());
					List<CacheUrlParam> list = db.queryForList("queryForPage", CacheUrlParam.class, query);
					StringBuilder paramText = new StringBuilder("?");
					for(CacheUrlParam p: list) {
						paramText.append(p.getParamCode()+"="+ (StringUtil.isBlank(p.getParamValue()) ? "":p.getParamValue()));
					}
					
					URL urlObj = new URL(model.getUrl()+paramText);
					HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

					String json = "";
					connection.setRequestMethod(StringUtil.isBlank(model.getUrlMethod()) ? "POST": model.getUrlMethod());
					if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
						json = IOUtils.toString(connection.getInputStream(), "utf-8");
						rs.put("message", json);
						
						log.debug("刷新缓存URL: "+model.getUrl()+" , 返回的JSON:"+json);
					} else {
						String temp = "刷新缓存失败，Http状态码："+connection.getResponseCode()+", 详情：" + connection.getResponseMessage();
						rs.put("message", temp);
						
						log.debug(temp);
					}
				}
			}
		}
		
		return rs;
	}
	
}
