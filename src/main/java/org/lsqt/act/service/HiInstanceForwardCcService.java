package org.lsqt.act.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.HiInstanceForwardCc;
import org.lsqt.act.model.HiInstanceForwardCcQuery;

public interface HiInstanceForwardCcService {
	
	Page<HiInstanceForwardCc> queryForPage(HiInstanceForwardCcQuery query);

	HiInstanceForwardCc saveOrUpdate(HiInstanceForwardCc model);

	int deleteById(Long... ids);
	
	Collection<HiInstanceForwardCc> getAll();
}
