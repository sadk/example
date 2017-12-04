package org.lsqt.sys.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Machine;
import org.lsqt.sys.model.MachineQuery;
import org.lsqt.sys.service.MachineService;

@Service
public class MachineServiceImpl implements MachineService{
	
	@Inject private Db db;
	
	public Page<Machine>  queryForPage(MachineQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Machine.class, query);
	}

	public List<Machine> getAll(){
		  return db.queryForList("getAll", Machine.class);
	}
	
	public Machine saveOrUpdate(Machine model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Machine.class, Arrays.asList(ids).toArray());
	}
}
