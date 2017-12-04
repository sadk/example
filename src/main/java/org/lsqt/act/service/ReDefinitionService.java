package org.lsqt.act.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.ReDefinition;
import org.lsqt.act.model.ReDefinitionQuery;

public interface ReDefinitionService {
	
	Page<ReDefinition> queryForPage(ReDefinitionQuery query);

	ReDefinition saveOrUpdate(ReDefinition model);

	int deleteById(Long... ids);
	
	Collection<ReDefinition> getAll();
	
	/**
	 * 拷贝流程的配置
	 * @param sourceDefinitionId 源流程定义ID
	 * @param targetDefinitionId 目标流程定义ID
	 * @return 返回新的流程定义对象
	 */
	ReDefinition copySettings(String sourceDefinitionId,String targetDefinitionId) ;
}
