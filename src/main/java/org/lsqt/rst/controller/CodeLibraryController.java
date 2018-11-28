package org.lsqt.rst.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.rst.model.CodeLibrary;
import org.lsqt.rst.model.CodeLibraryQuery;
import org.lsqt.rst.service.CodeLibraryService;




@Controller(mapping={"/rst/code_library"})
public class CodeLibraryController {
	
	@Inject private CodeLibraryService codeLibraryService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public CodeLibrary getById(Long id) throws IOException {
		return codeLibraryService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<CodeLibrary> queryForPage(CodeLibraryQuery query) throws IOException {
		return codeLibraryService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<CodeLibrary> queryForList(CodeLibraryQuery query) throws IOException {
		return codeLibraryService.queryForList(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<CodeLibrary> getAll() {
		return codeLibraryService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public CodeLibrary saveOrUpdate(CodeLibrary form) {
		return codeLibraryService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return codeLibraryService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
