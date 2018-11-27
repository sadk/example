package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.CompanyPicture;
import org.lsqt.rst.model.CompanyPictureQuery;
import org.lsqt.rst.service.CompanyPictureService;

@Service
public class CompanyPictureServiceImpl implements CompanyPictureService{
	
	@Inject private Db db;
	
	public CompanyPicture getById(Long id) {
		return db.getById(CompanyPicture.class, id) ;
	}
	
	public List<CompanyPicture> queryForList(CompanyPictureQuery query) {
		return db.queryForList("queryForPage", CompanyPicture.class, query);
	}
	
	public Page<CompanyPicture> queryForPage(CompanyPictureQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), CompanyPicture.class, query);
	}

	public List<CompanyPicture> getAll(){
		  return db.queryForList("getAll", CompanyPicture.class);
	}
	
	public CompanyPicture saveOrUpdate(CompanyPicture model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(CompanyPicture.class, Arrays.asList(ids).toArray());
	}
}
