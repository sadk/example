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
import org.lsqt.syswin.authority.model.Range;
import org.lsqt.syswin.authority.model.RangeQuery;
import org.lsqt.syswin.authority.service.RangeService;




@Controller(mapping={"/syswin/range","/nv2/syswin/range"})
public class RangeController {
	
	@Inject private RangeService rangeService; 
	
	@Inject private PlatformDb db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Range> queryForPage(RangeQuery query) throws IOException {
		return rangeService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Range> getAll() {
		return rangeService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Range saveOrUpdate(Range form) {
		return rangeService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return rangeService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	 
	
}
