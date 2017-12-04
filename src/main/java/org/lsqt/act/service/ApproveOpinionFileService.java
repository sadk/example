package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.ApproveOpinionFile;
import org.lsqt.act.model.ApproveOpinionFileQuery;

public interface ApproveOpinionFileService {
	
	List<ApproveOpinionFile> queryForList(ApproveOpinionFileQuery query);
	
	Page<ApproveOpinionFile> queryForPage(ApproveOpinionFileQuery query);

	ApproveOpinionFile saveOrUpdate(ApproveOpinionFile model);

	int deleteById(Long... ids);
	
	Collection<ApproveOpinionFile> getAll();
}
