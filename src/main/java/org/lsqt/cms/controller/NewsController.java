package org.lsqt.cms.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.cms.model.Content;
import org.lsqt.cms.model.ContentQuery;
import org.lsqt.cms.model.News;
import org.lsqt.cms.model.NewsQuery;
import org.lsqt.cms.service.NewsService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.Cache;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;

@Controller(mapping={"/news"})
public class NewsController {
	@Inject private NewsService newsService; 
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" }, view = View.JSON)
	public Page<News> queryForPage(NewsQuery query) throws IOException {
		return newsService.queryForPage(query);
	}
	
	@Cache(scope="application",timeout="30m")
	@RequestMapping(mapping = { "/all", "/m/all" },view = View.JSON)
	public Collection<News> all(NewsQuery query) {
		  return newsService.queryForList(query);
	}
	
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" }, view = View.JSON)
	public News saveOrUpdate(News form,String content) {
		if(StringUtil.isBlank(form.getName())) {
			form.setName(form.getTitle());
		}
		return newsService.saveOrUpdate(form,content);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" },view = View.JSON)
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return newsService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/get_content_by_news_id", "/m/get_content_by_news_id" })
	public Content getContentByNewsId(Long id) {
		 ContentQuery query = new ContentQuery();
		 query.setObjectId(id);
		 Content cnt = db.queryForObject("queryForPage", Content.class, query);
		 return cnt;
	}
	
}
