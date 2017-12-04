package org.lsqt.cms.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.cms.model.Content;
import org.lsqt.cms.model.ContentQuery;
import org.lsqt.cms.model.News;
import org.lsqt.cms.model.NewsQuery;
import org.lsqt.cms.service.NewsService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;

@Service
public class NewsServiceImpl implements NewsService {
	@Inject private Db db;
	
	public Page<News>  queryForPage(NewsQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), News.class, query);
	}

	public List<News> getAll(){
		  return db.queryForList("getAll", News.class);
	}
	
	public News saveOrUpdate(News model) {
		return db.saveOrUpdate(model);
	}

	public News saveOrUpdate(News model,String content) {
		db.saveOrUpdate(model);
		
		if(model.getId()==null){
			Content cnt = new Content();
			cnt.setAppCode(model.getAppCode());
			cnt.setCode("news_"+model.getCode());
			cnt.setContent(content);
			cnt.setTitle(model.getTitle());
			cnt.setType(Content.TYPE_NEWS);
			cnt.setObjectId(model.getId());
			db.save(cnt);
		}else {
			ContentQuery query = new ContentQuery();
			query.setObjectId(model.getId());
			Content m = db.queryForObject("queryForPage", Content.class, query);
			if(m!=null) {
				m.setObjectId(model.getId());
				m.setContent(content);
				m.setTitle(model.getTitle());
				db.update(m, "content","title","objectId");
			}
		}
		return model;
	}

	
	public int deleteById(Long ... ids) {
		return db.deleteById(News.class, Arrays.asList(ids).toArray());
	}

	@Override
	public List<News> queryForList(NewsQuery query) {
		 return db.queryForList("queryForPage", News.class, query);
	}

}
