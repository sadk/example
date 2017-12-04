package org.lsqt.syswin.authority.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.syswin.authority.model.Menu;
import org.lsqt.syswin.authority.model.MenuQuery;

public interface MenuService {
	List<Menu> queryForList(MenuQuery query) ;
	
	Page<Menu> queryForPage(MenuQuery query);

	Menu saveOrUpdate(Menu model);

	int deleteById(Long... ids);
	
	Collection<Menu> getAll();
	
	/**
	 * 修复节点路径
	 */
	void repairNodePath() ;
}
