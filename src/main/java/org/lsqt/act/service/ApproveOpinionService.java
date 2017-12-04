package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ApproveOpinionQuery;

public interface ApproveOpinionService {
	
	List<ApproveOpinion> queryForList(ApproveOpinionQuery query);
	
	Page<ApproveOpinion> queryForPage(ApproveOpinionQuery query);

	ApproveOpinion saveOrUpdate(ApproveOpinion model);

	int deleteById(Long... ids);
	
	Collection<ApproveOpinion> getAll();
}
