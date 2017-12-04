package org.lsqt.cms.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.cms.model.NewsComment;
import org.lsqt.cms.model.NewsCommentQuery;
import org.lsqt.cms.service.NewsCommentService;




@Controller(mapping={"/news_comment"})
public class NewsCommentController {
	
	@Inject private NewsCommentService newsCommentService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<NewsComment> queryForPage(NewsCommentQuery query) throws IOException {
		return newsCommentService.queryForPage(query); 
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<NewsComment> getAll() {
		return newsCommentService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public NewsComment saveOrUpdate(NewsComment form) {
		if(StringUtil.isBlank(form.getAppCode())) {
			form.setAppCode(Application.APP_CODE_DEFAULT);
		}
		return newsCommentService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return newsCommentService.deleteById(list.toArray(new Long[list.size()]));
	}
}
