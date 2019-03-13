package org.lsqt.sys.controller;

import java.util.Collection;
import java.util.List;

import org.lsqt.sys.model.ApiRequestMock;
import org.lsqt.sys.model.ApiRequestMockQuery;
import org.lsqt.sys.service.ApiRequestMockService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;

import com.alibaba.fastjson.JSON;




@Controller(mapping={"/api/request_modk"})
public class ApiRequestMockController {
	
	@Inject private ApiRequestMockService requestMockService; 
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public ApiRequestMock getById(Long id)  {
		return requestMockService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<ApiRequestMock> queryForPage(ApiRequestMockQuery query) {
		return requestMockService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<ApiRequestMock> getAll() {
		return requestMockService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public ApiRequestMock saveOrUpdate(ApiRequestMock form) {
		return requestMockService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/save_or_update_json", "/m/save_or_update_json" })
	public void saveOrUpdateJson(String data) {
		List<ApiRequestMock> list = JSON.parseArray(data, ApiRequestMock.class);
		for(ApiRequestMock e: list) {
			requestMockService.saveOrUpdate(e);
		}
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return requestMockService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
