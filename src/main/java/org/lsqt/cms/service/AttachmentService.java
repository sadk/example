package org.lsqt.cms.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.cms.model.Attachment;
import org.lsqt.cms.model.AttachmentQuery;
import org.lsqt.components.db.Page;

public interface AttachmentService {
	Page<Attachment> queryForPage(AttachmentQuery query);

	Attachment saveOrUpdate(Attachment model);

	int deleteById(Long... ids);
	
	Collection<Attachment> getAll();
	
	List<Attachment> queryForList(AttachmentQuery query);
}
