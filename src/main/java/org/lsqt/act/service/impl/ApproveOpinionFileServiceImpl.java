package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.act.model.ApproveOpinionFile;
import org.lsqt.act.model.ApproveOpinionFileQuery;
import org.lsqt.act.service.ApproveOpinionFileService;

@Service
public class ApproveOpinionFileServiceImpl implements ApproveOpinionFileService{
	
	@Inject private Db db;
	
	public List<ApproveOpinionFile>  queryForList(ApproveOpinionFileQuery query) {
		return db.queryForList("queryForPage", ApproveOpinionFile.class, query);
	}
	
	public Page<ApproveOpinionFile>  queryForPage(ApproveOpinionFileQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), ApproveOpinionFile.class, query);
	}

	public List<ApproveOpinionFile> getAll(){
		  return db.queryForList("getAll", ApproveOpinionFile.class);
	}
	
	public ApproveOpinionFile saveOrUpdate(ApproveOpinionFile model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(ApproveOpinionFile.class, Arrays.asList(ids).toArray());
	}
}
