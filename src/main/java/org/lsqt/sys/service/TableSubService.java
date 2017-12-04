package org.lsqt.sys.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.TableSub;
import org.lsqt.sys.model.TableSubQuery;

public interface TableSubService {
	
	Page<TableSub> queryForPage(TableSubQuery query);

	TableSub saveOrUpdate(TableSub model);

	int deleteById(Long... ids);
	
	Collection<TableSub> getAll();
}
