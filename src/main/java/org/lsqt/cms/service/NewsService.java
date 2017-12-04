package org.lsqt.cms.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.cms.model.News;
import org.lsqt.cms.model.NewsQuery;
import org.lsqt.components.db.Page;

public interface NewsService {
	Page<News> queryForPage(NewsQuery query);

	News saveOrUpdate(News model);
	
	/**
	 * 
	 * @param model
	 * @param content 资讯Html内容
	 * @return
	 */
	News saveOrUpdate(News model,String content);

	int deleteById(Long... ids);
	
	Collection<News> getAll();
	
	List<News> queryForList(NewsQuery query);
}
