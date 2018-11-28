package org.lsqt.uum.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.uum.model.Group;
import org.lsqt.uum.model.GroupQuery;

public interface GroupService {
	
	Page<Group> queryForPage(GroupQuery query);

	Group saveOrUpdate(Group model);

	int deleteById(Long... ids);
	
	Collection<Group> getAll();
}
