package org.lsqt.syswin.authority.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.syswin.authority.model.Range;
import org.lsqt.syswin.authority.model.RangeQuery;

public interface RangeService {
	
	Page<Range> queryForPage(RangeQuery query);

	Range saveOrUpdate(Range model);

	int deleteById(Long... ids);
	
	Collection<Range> getAll();
}
