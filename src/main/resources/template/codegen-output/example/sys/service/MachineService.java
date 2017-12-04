package org.lsqt.sys.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Machine;
import org.lsqt.sys.model.MachineQuery;

public interface MachineService {
	
	Page<Machine> queryForPage(MachineQuery query);

	Machine saveOrUpdate(Machine model);

	int deleteById(Long... ids);
	
	Collection<Machine> getAll();
}
