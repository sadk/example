package org.lsqt.cms.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.cms.model.NewsComment;
import org.lsqt.cms.model.NewsCommentQuery;
import org.lsqt.cms.service.NewsCommentService;

@Service
public class NewsCommentServiceImpl implements NewsCommentService{
	
	@Inject private Db db;
	
	public Page<NewsComment>  queryForPage(NewsCommentQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), NewsComment.class, query);
	}

	public List<NewsComment> getAll(){
		  return db.queryForList("getAll", NewsComment.class);
	}
	
	public NewsComment saveOrUpdate(NewsComment model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(NewsComment.class, Arrays.asList(ids).toArray());
	}
}
