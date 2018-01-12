package org.lsqt.syswin.authority.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.PositionModuleConfig;
import org.lsqt.syswin.authority.model.PositionModuleConfigQuery;
import org.lsqt.syswin.authority.service.PositionModuleConfigService;

@Service
public class PositionModuleConfigServiceImpl implements PositionModuleConfigService{
	
	@Inject private PlatformDb db;
	
	public Page<PositionModuleConfig>  queryForPage(PositionModuleConfigQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), PositionModuleConfig.class, query);
	}

	public List<PositionModuleConfig> getAll(){
		  return db.queryForList("getAll", PositionModuleConfig.class);
	}
	
	public PositionModuleConfig saveOrUpdate(PositionModuleConfig model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(PositionModuleConfig.class, Arrays.asList(ids).toArray());
	}
}
