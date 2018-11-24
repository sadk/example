package org.lsqt.rst.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.rst.model.WorkAddress;
import org.lsqt.rst.model.WorkAddressQuery;
import org.lsqt.rst.service.WorkAddressService;




@Controller(mapping={"/rst/work_address"})
public class WorkAddressController {
	
	@Inject private WorkAddressService workAddressService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public WorkAddress getById(Long id) throws IOException {
		return workAddressService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<WorkAddress> queryForPage(WorkAddressQuery query) throws IOException {
		return workAddressService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<WorkAddress> getAll() {
		return workAddressService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public WorkAddress saveOrUpdate(WorkAddress form) {
		return workAddressService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return workAddressService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	 
}
