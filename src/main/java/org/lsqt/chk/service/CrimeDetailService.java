package org.lsqt.chk.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.chk.model.CrimeDetail;
import org.lsqt.chk.model.CrimeDetailQuery;

public interface CrimeDetailService {
	
	CrimeDetail getById(Long id);
	
	List<CrimeDetail> queryForList(CrimeDetailQuery query);
	
	Page<CrimeDetail> queryForPage(CrimeDetailQuery query);

	CrimeDetail saveOrUpdate(CrimeDetail model);

	int deleteById(Long... ids);
	
	Collection<CrimeDetail> getAll();
}
