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
import org.lsqt.rst.model.VedioVoteYeared;
import org.lsqt.rst.model.VedioVoteYearedQuery;
import org.lsqt.rst.service.VedioVoteYearedService;




@Controller(mapping={"/rst/vedio_vote_yeared"})
public class VedioVoteYearedController {
	
	@Inject private VedioVoteYearedService vedioVoteYearedService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public VedioVoteYeared getById(Long id) throws IOException {
		return vedioVoteYearedService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<VedioVoteYeared> queryForPage(VedioVoteYearedQuery query) throws IOException {
		return vedioVoteYearedService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<VedioVoteYeared> getAll() {
		return vedioVoteYearedService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public VedioVoteYeared saveOrUpdate(VedioVoteYeared form) {
		return vedioVoteYearedService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return vedioVoteYearedService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
