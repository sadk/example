package org.lsqt.act.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.PrintInfo;
import org.lsqt.act.model.PrintInfoQuery;

public interface PrintInfoService {
	
	Page<PrintInfo> queryForPage(PrintInfoQuery query);

	PrintInfo saveOrUpdate(PrintInfo model);

	int deleteById(Long... ids);
	
	Collection<PrintInfo> getAll();
}
