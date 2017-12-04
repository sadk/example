package org.lsqt.syswin.authority.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.syswin.authority.model.PositionPermitConfig;
import org.lsqt.syswin.authority.model.PositionPermitConfigQuery;
import org.lsqt.syswin.authority.service.PositionPermitConfigService;

@Service
public class PositionPermitConfigServiceImpl implements PositionPermitConfigService{
	
	@Inject private Db db;
	
	public Page<PositionPermitConfig>  queryForPage(PositionPermitConfigQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), PositionPermitConfig.class, query);
	}

	public List<PositionPermitConfig> getAll(){
		  return db.queryForList("getAll", PositionPermitConfig.class);
	}
	
	public PositionPermitConfig saveOrUpdate(PositionPermitConfig model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(PositionPermitConfig.class, Arrays.asList(ids).toArray());
	}
}
