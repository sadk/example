package org.lsqt.sys.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.sys.model.ApiParam;
import org.lsqt.sys.model.ApiParamQuery;
import org.lsqt.sys.service.ApiParamService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;

import com.alibaba.fastjson.JSON;




@Controller(mapping={"/api/param"})
public class ApiParamController {
	
	@Inject private ApiParamService paramService; 
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public ApiParam getById(Long id) throws IOException {
		return paramService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<ApiParam> queryForPage(ApiParamQuery query) {
		return paramService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<ApiParam> getAll() {
		return paramService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public ApiParam saveOrUpdate(ApiParam form) {
		return paramService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/save_or_update_json", "/m/save_or_update_json" })
	public void saveOrUpdateJson(String data) {
		List<ApiParam> list = JSON.parseArray(data, ApiParam.class);
		for(ApiParam e: list) {
			paramService.saveOrUpdate(e);
		}
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return paramService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
