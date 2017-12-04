package org.lsqt.syswin.uum.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.syswin.uum.model.Org;
import org.lsqt.syswin.uum.model.OrgQuery;

public interface OrgService {
	
	Page<Org> queryForPage(OrgQuery query);

	List<Org> queryForList(OrgQuery query);
	
	Org saveOrUpdate(Org model);

	int deleteById(Long... ids);
	
	Collection<Org> getAll();
	
	/**
	 * 修复节点路径
	 */
	void repairNodePath() ;
	
	/**
	 * 填充节点路径以"-->"分割,如：集团总部-->上海分公司-->普陀花园
	 */
	void fillNodePath() ;
}
