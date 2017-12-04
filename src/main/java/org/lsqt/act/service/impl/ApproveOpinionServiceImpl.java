package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ApproveOpinionQuery;
import org.lsqt.act.service.ApproveOpinionService;

@Service
public class ApproveOpinionServiceImpl implements ApproveOpinionService{
	
	@Inject private Db db;
	
	public Page<ApproveOpinion>  queryForPage(ApproveOpinionQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), ApproveOpinion.class, query);
	}

	public List<ApproveOpinion>  queryForList(ApproveOpinionQuery query) {
		return db.queryForList("queryForPage", ApproveOpinion.class, query);
	}

	
	public List<ApproveOpinion> getAll(){
		  return db.queryForList("getAll", ApproveOpinion.class);
	}
	
	public ApproveOpinion saveOrUpdate(ApproveOpinion model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(ApproveOpinion.class, Arrays.asList(ids).toArray());
	}
}
