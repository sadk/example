package org.lsqt.act.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.FormBaoXiao;
import org.lsqt.act.model.FormBaoXiaoQuery;

public interface FormBaoXiaoService {
	
	Page<FormBaoXiao> queryForPage(FormBaoXiaoQuery query);

	FormBaoXiao saveOrUpdate(FormBaoXiao model);

	int deleteById(Long... ids);
	
	Collection<FormBaoXiao> getAll();
}
