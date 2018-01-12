package org.lsqt.syswin.authority.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.syswin.authority.model.PositionModuleConfig;
import org.lsqt.syswin.authority.model.PositionModuleConfigQuery;

public interface PositionModuleConfigService {
	
	Page<PositionModuleConfig> queryForPage(PositionModuleConfigQuery query);

	PositionModuleConfig saveOrUpdate(PositionModuleConfig model);

	int deleteById(Long... ids);
	
	Collection<PositionModuleConfig> getAll();
}
