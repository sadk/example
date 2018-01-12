package org.lsqt.syswin.authority.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.PositionPermitResult;
import org.lsqt.syswin.authority.model.PositionPermitResultQuery;
import org.lsqt.syswin.authority.service.PositionPermitResultService;

@Service
public class PositionPermitResultServiceImpl implements PositionPermitResultService{
	
	@Inject private PlatformDb db;
	
	public Page<PositionPermitResult>  queryForPage(PositionPermitResultQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), PositionPermitResult.class, query);
	}

	public List<PositionPermitResult> getAll(){
		  return db.queryForList("getAll", PositionPermitResult.class);
	}
	
	public PositionPermitResult saveOrUpdate(PositionPermitResult model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(PositionPermitResult.class, Arrays.asList(ids).toArray());
	}
}
