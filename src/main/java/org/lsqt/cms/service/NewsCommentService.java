package org.lsqt.cms.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.cms.model.NewsComment;
import org.lsqt.cms.model.NewsCommentQuery;

public interface NewsCommentService {
	
	Page<NewsComment> queryForPage(NewsCommentQuery query);

	NewsComment saveOrUpdate(NewsComment model);

	int deleteById(Long... ids);
	
	Collection<NewsComment> getAll();
}
