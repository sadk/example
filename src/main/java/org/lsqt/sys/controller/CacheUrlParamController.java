package org.lsqt.sys.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.CacheUrlParam;
import org.lsqt.sys.model.CacheUrlParamQuery;
import org.lsqt.sys.service.CacheUrlParamService;

import com.alibaba.fastjson.JSON;




@Controller(mapping={"/cache_url_param"})
public class CacheUrlParamController {
	
	@Inject private CacheUrlParamService cacheUrlParamService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public CacheUrlParam getById(Long id) throws IOException {
		return db.getById(CacheUrlParam.class, id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<CacheUrlParam> queryForPage(CacheUrlParamQuery query) throws IOException {
		return cacheUrlParamService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<CacheUrlParam> getAll() {
		return cacheUrlParamService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public CacheUrlParam saveOrUpdate(CacheUrlParam form) {
		return cacheUrlParamService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/save_or_update_json", "/m/save_or_update_json" })
	public void saveOrUpdateJson(String data) {
		List<CacheUrlParam> list = JSON.parseArray(data, CacheUrlParam.class);
		for(CacheUrlParam e: list) {
			db.saveOrUpdate(e);
		}
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return cacheUrlParamService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
