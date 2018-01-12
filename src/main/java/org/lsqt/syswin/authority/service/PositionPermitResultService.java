package org.lsqt.syswin.authority.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.syswin.authority.model.PositionPermitResult;
import org.lsqt.syswin.authority.model.PositionPermitResultQuery;

public interface PositionPermitResultService {
	
	Page<PositionPermitResult> queryForPage(PositionPermitResultQuery query);

	PositionPermitResult saveOrUpdate(PositionPermitResult model);

	int deleteById(Long... ids);
	
	Collection<PositionPermitResult> getAll();
}
