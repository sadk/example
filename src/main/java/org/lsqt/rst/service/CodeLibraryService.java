package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.CodeLibrary;
import org.lsqt.rst.model.CodeLibraryQuery;

public interface CodeLibraryService {
	
	CodeLibrary getById(Long id);
	
	List<CodeLibrary> queryForList(CodeLibraryQuery query);
	
	Page<CodeLibrary> queryForPage(CodeLibraryQuery query);

	CodeLibrary saveOrUpdate(CodeLibrary model);

	int deleteById(Long... ids);
	
	Collection<CodeLibrary> getAll();
}
