package org.lsqt.cms.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.cms.model.Resource;
import org.lsqt.cms.model.ResourceQuery;
import org.lsqt.components.db.Page;

public interface ResourceService {
	
	Page<Resource> queryForPage(ResourceQuery query);
	
	List<Resource> queryForList(ResourceQuery query);

	Resource saveOrUpdate(Resource model);

	int deleteById(Long... ids);
	
	Collection<Resource> getAll();
	
	/**
	 * 根据当前id获取多层子节点(含当前节点) 
	 * @param id 
	 * @return
	 */
	List<Resource> getAllChildNodes(Long id);  
	
 
}
