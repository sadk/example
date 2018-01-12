package org.lsqt.syswin.authority.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.syswin.authority.model.PositionPermitConfig;
import org.lsqt.syswin.authority.model.PositionPermitConfigQuery;

public interface PositionPermitConfigService {
	
	Page<PositionPermitConfig> queryForPage(PositionPermitConfigQuery query);

	PositionPermitConfig saveOrUpdate(PositionPermitConfig model);

	int deleteById(Long... ids);
	
	Collection<PositionPermitConfig> getAll();
}
