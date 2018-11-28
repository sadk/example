package org.lsqt.cms.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.cms.model.Attachment;
import org.lsqt.cms.model.AttachmentQuery;
import org.lsqt.cms.service.AttachmentService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;

@Service
public class AttachmentServiceImpl implements AttachmentService {
	@Inject private Db db;
	
	public Page<Attachment>  queryForPage(AttachmentQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Attachment.class, query);
	}

	public List<Attachment> getAll(){
		  return db.queryForList("getAll", Attachment.class);
	}
	
	public Attachment saveOrUpdate(Attachment model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Attachment.class, Arrays.asList(ids).toArray());
	}

	@Override
	public List<Attachment> queryForList(AttachmentQuery query) {
		 return db.queryForList("queryForPage", Attachment.class, query);
	}
}
