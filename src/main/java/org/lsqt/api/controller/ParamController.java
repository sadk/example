package org.lsqt.api.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.api.model.Param;
import org.lsqt.api.model.ParamQuery;
import org.lsqt.api.service.ParamService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;

import com.alibaba.fastjson.JSON;




@Controller(mapping={"/api/param"})
public class ParamController {
	
	@Inject private ParamService paramService; 
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public Param getById(Long id) throws IOException {
		return paramService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Param> queryForPage(ParamQuery query) {
		return paramService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Param> getAll() {
		return paramService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Param saveOrUpdate(Param form) {
		return paramService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/save_or_update_json", "/m/save_or_update_json" })
	public void saveOrUpdateJson(String data) {
		List<Param> list = JSON.parseArray(data, Param.class);
		for(Param e: list) {
			paramService.saveOrUpdate(e);
		}
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return paramService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
