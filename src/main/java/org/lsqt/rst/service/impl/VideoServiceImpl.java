package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.Video;
import org.lsqt.rst.model.VideoQuery;
import org.lsqt.rst.service.VideoService;

@Service
public class VideoServiceImpl implements VideoService{
	
	@Inject private Db db;
	
	public Video getById(Long id) {
		return db.getById(Video.class, id) ;
	}
	
	public List<Video> queryForList(VideoQuery query) {
		return db.queryForList("queryForPage", Video.class, query);
	}
	
	public Page<Video> queryForPage(VideoQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Video.class, query);
	}

	public List<Video> getAll(){
		  return db.queryForList("getAll", Video.class);
	}
	
	public Video saveOrUpdate(Video model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Video.class, Arrays.asList(ids).toArray());
	}
}
