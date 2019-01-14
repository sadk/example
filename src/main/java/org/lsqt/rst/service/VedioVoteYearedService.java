package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.VedioVoteYeared;
import org.lsqt.rst.model.VedioVoteYearedQuery;

public interface VedioVoteYearedService {
	
	VedioVoteYeared getById(Long id);
	
	List<VedioVoteYeared> queryForList(VedioVoteYearedQuery query);
	
	Page<VedioVoteYeared> queryForPage(VedioVoteYearedQuery query);

	VedioVoteYeared saveOrUpdate(VedioVoteYeared model);

	int deleteById(Long... ids);
	
	Collection<VedioVoteYeared> getAll();
}
