package org.lsqt.uum.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.uum.model.Res;
import org.lsqt.uum.model.ResQuery;

public interface ResService {
	
	Page<Res> queryForPage(ResQuery query);

	Res saveOrUpdate(Res model);

	int deleteById(Long... ids);
	
	Collection<Res> getAll();
}
