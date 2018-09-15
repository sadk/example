package org.lsqt.uum.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.uum.model.Res;
import org.lsqt.uum.model.ResQuery;

public interface ResService {
	
	List<Res> queryForList(ResQuery query);
	
	Page<Res> queryForPage(ResQuery query);

	Res saveOrUpdate(Res model);

	int deleteById(Long... ids);
	
	Collection<Res> getAll();
	
	/**
	 * 修复节点路径
	 */
	void repairNodePath() ;
	
	/**
	 * 获取当前结点下所有节点(包含根结点)
	 * @param id
	 * @return
	 */
	List<Res> getAllChildNodes(Long id);
}
