package org.lsqt.syswin.authority.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.syswin.authority.model.RangeValue;
import org.lsqt.syswin.authority.model.RangeValueQuery;

public interface RangeValueService {
	
	Page<RangeValue> queryForPage(RangeValueQuery query);

	RangeValue saveOrUpdate(RangeValue model);

	int deleteById(Long... ids);
	
	Collection<RangeValue> getAll();
	
	// --------------------------------------
	String viewSql(Long userId, String content) throws Exception;
}
