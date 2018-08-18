package org.lsqt.act.service;

import java.util.Collection;

import org.activiti.engine.repository.Deployment;
import org.lsqt.act.model.ReModelInfo;
import org.lsqt.act.model.ReModelInfoQuery;
import org.lsqt.components.db.Page;

public interface ReModelInfoService {

	Page<ReModelInfo> queryForPage(ReModelInfoQuery query);

	ReModelInfo saveOrUpdate(ReModelInfo model);

	int deleteById(Long... ids);

	Collection<ReModelInfo> getAll();
	
	/**
	 *  根据modelId部署流程
	 * @param id 模型ID
	 * @return
	 */
	Deployment deploy(String id) ;
}
