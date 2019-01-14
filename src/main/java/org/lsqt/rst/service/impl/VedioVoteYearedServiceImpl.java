package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.VedioVoteYeared;
import org.lsqt.rst.model.VedioVoteYearedQuery;
import org.lsqt.rst.service.VedioVoteYearedService;

@Service
public class VedioVoteYearedServiceImpl implements VedioVoteYearedService{
	
	@Inject private Db db;
	
	public VedioVoteYeared getById(Long id) {
		return db.getById(VedioVoteYeared.class, id) ;
	}
	
	public List<VedioVoteYeared> queryForList(VedioVoteYearedQuery query) {
		return db.queryForList("queryForPage", VedioVoteYeared.class, query);
	}
	
	public Page<VedioVoteYeared> queryForPage(VedioVoteYearedQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), VedioVoteYeared.class, query);
	}

	public List<VedioVoteYeared> getAll(){
		  return db.queryForList("getAll", VedioVoteYeared.class);
	}
	
	public VedioVoteYeared saveOrUpdate(VedioVoteYeared model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(VedioVoteYeared.class, Arrays.asList(ids).toArray());
	}
}
