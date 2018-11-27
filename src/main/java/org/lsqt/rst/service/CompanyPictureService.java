package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.CompanyPicture;
import org.lsqt.rst.model.CompanyPictureQuery;

public interface CompanyPictureService {
	
	CompanyPicture getById(Long id);
	
	List<CompanyPicture> queryForList(CompanyPictureQuery query);
	
	Page<CompanyPicture> queryForPage(CompanyPictureQuery query);

	CompanyPicture saveOrUpdate(CompanyPicture model);

	int deleteById(Long... ids);
	
	Collection<CompanyPicture> getAll();
}
