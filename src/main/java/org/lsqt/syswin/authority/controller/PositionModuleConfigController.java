package org.lsqt.syswin.authority.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.PositionModuleConfig;
import org.lsqt.syswin.authority.model.PositionModuleConfigQuery;
import org.lsqt.syswin.authority.service.PositionModuleConfigService;




@Controller(mapping={"/positionModuleConfig"})
public class PositionModuleConfigController {
	
	@Inject private PositionModuleConfigService positionModuleConfigService; 
	
	@Inject private PlatformDb db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<PositionModuleConfig> queryForPage(PositionModuleConfigQuery query) throws IOException {
		return positionModuleConfigService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<PositionModuleConfig> getAll() {
		return positionModuleConfigService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public PositionModuleConfig saveOrUpdate(PositionModuleConfig form) {
		return positionModuleConfigService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return positionModuleConfigService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	 
}
