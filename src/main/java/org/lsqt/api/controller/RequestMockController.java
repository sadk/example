package org.lsqt.api.controller;

import java.util.Collection;
import java.util.List;

import org.lsqt.api.model.RequestMock;
import org.lsqt.api.model.RequestMockQuery;
import org.lsqt.api.service.RequestMockService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;

import com.alibaba.fastjson.JSON;




@Controller(mapping={"/api/request_modk"})
public class RequestMockController {
	
	@Inject private RequestMockService requestMockService; 
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public RequestMock getById(Long id)  {
		return requestMockService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<RequestMock> queryForPage(RequestMockQuery query) {
		return requestMockService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<RequestMock> getAll() {
		return requestMockService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public RequestMock saveOrUpdate(RequestMock form) {
		return requestMockService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/save_or_update_json", "/m/save_or_update_json" })
	public void saveOrUpdateJson(String data) {
		List<RequestMock> list = JSON.parseArray(data, RequestMock.class);
		for(RequestMock e: list) {
			requestMockService.saveOrUpdate(e);
		}
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return requestMockService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
