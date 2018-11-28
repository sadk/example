package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.CodeLibrary;
import org.lsqt.rst.model.CodeLibraryQuery;
import org.lsqt.rst.service.CodeLibraryService;

@Service
public class CodeLibraryServiceImpl implements CodeLibraryService{
	
	@Inject private Db db;
	
	public CodeLibrary getById(Long id) {
		return db.getById(CodeLibrary.class, id) ;
	}
	
	public List<CodeLibrary> queryForList(CodeLibraryQuery query) {
		return db.queryForList("queryForPage", CodeLibrary.class, query);
	}
	
	public Page<CodeLibrary> queryForPage(CodeLibraryQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), CodeLibrary.class, query);
	}

	public List<CodeLibrary> getAll(){
		  return db.queryForList("getAll", CodeLibrary.class);
	}
	
	public CodeLibrary saveOrUpdate(CodeLibrary model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(CodeLibrary.class, Arrays.asList(ids).toArray());
	}
}
