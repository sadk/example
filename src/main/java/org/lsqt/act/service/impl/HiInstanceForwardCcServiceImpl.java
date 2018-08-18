package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.act.model.HiInstanceForwardCc;
import org.lsqt.act.model.HiInstanceForwardCcQuery;
import org.lsqt.act.service.HiInstanceForwardCcService;

@Service
public class HiInstanceForwardCcServiceImpl implements HiInstanceForwardCcService{
	
	@Inject private Db db;
	
	public Page<HiInstanceForwardCc>  queryForPage(HiInstanceForwardCcQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), HiInstanceForwardCc.class, query);
	}

	public List<HiInstanceForwardCc> getAll(){
		  return db.queryForList("getAll", HiInstanceForwardCc.class);
	}
	
	public HiInstanceForwardCc saveOrUpdate(HiInstanceForwardCc model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(HiInstanceForwardCc.class, Arrays.asList(ids).toArray());
	}
}
