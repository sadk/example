package org.lsqt.chk.controller;

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
import org.lsqt.chk.model.CrimeDetail;
import org.lsqt.chk.model.CrimeDetailQuery;
import org.lsqt.chk.service.CrimeDetailService;




@Controller(mapping={"/chk/crime_detail"})
public class CrimeDetailController {
	
	@Inject private CrimeDetailService crimeDetailService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public CrimeDetail getById(Long id) throws IOException {
		return crimeDetailService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<CrimeDetail> queryForPage(CrimeDetailQuery query) throws IOException {
		return crimeDetailService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<CrimeDetail> queryForList(CrimeDetailQuery query) throws IOException {
		return crimeDetailService.queryForList(query);
	}
	
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<CrimeDetail> getAll() {
		return crimeDetailService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public CrimeDetail saveOrUpdate(CrimeDetail form) {
		return crimeDetailService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return crimeDetailService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
