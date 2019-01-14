package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.VideoYear;
import org.lsqt.rst.model.VideoYearQuery;

public interface VideoYearService {
	
	VideoYear getById(Long id);
	
	List<VideoYear> queryForList(VideoYearQuery query);
	
	Page<VideoYear> queryForPage(VideoYearQuery query);

	VideoYear saveOrUpdate(VideoYear model);

	int deleteById(Long... ids);
	
	Collection<VideoYear> getAll();
}
