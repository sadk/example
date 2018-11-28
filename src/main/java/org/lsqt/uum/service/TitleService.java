package org.lsqt.uum.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.uum.model.Title;
import org.lsqt.uum.model.TitleQuery;

public interface TitleService {
	
	Page<Title> queryForPage(TitleQuery query);

	Title saveOrUpdate(Title model);

	int deleteById(Long... ids);
	
	Collection<Title> getAll();
}
