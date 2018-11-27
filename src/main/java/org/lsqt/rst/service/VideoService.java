package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.Video;
import org.lsqt.rst.model.VideoQuery;

public interface VideoService {
	
	Video getById(Long id);
	
	List<Video> queryForList(VideoQuery query);
	
	Page<Video> queryForPage(VideoQuery query);

	Video saveOrUpdate(Video model);

	int deleteById(Long... ids);
	
	Collection<Video> getAll();
}
