package org.lsqt.act.service.support;

import org.lsqt.act.model.ActRunningContext;

public interface Command <T> {
	 
	T execute(ActRunningContext context);
	
	void executeCancel(ActRunningContext context);
}
