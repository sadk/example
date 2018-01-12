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
import org.lsqt.syswin.authority.model.PositionPermitResult;
import org.lsqt.syswin.authority.model.PositionPermitResultQuery;
import org.lsqt.syswin.authority.service.PositionPermitResultService;



@Deprecated
@Controller(mapping={"/positionPermitResult"})
public class PositionPermitResultController {
	
	@Inject private PositionPermitResultService positionPermitResultService; 
	
	@Inject private PlatformDb db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<PositionPermitResult> queryForPage(PositionPermitResultQuery query) throws IOException {
		return positionPermitResultService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<PositionPermitResult> getAll() {
		return positionPermitResultService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public PositionPermitResult saveOrUpdate(PositionPermitResult form) {
		return positionPermitResultService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return positionPermitResultService.deleteById(list.toArray(new Long[list.size()]));
	}
}
