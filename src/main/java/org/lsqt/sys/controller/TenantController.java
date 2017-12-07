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
import org.lsqt.sys.model.Tenant;
import org.lsqt.sys.model.TenantQuery;
import org.lsqt.sys.service.TenantService;

@Controller(mapping={"/tenant"})
public class TenantController {
	
	@Inject private TenantService tenantService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Tenant> queryForPage(TenantQuery query) throws IOException {
		return tenantService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Tenant> getAll() {
		return tenantService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Tenant saveOrUpdate(Tenant form) {
		return tenantService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return tenantService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
