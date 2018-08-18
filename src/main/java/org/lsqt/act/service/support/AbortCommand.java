package org.lsqt.act.service.support;

import org.lsqt.act.model.ActRunningContext;

public class AbortCommand  implements Command<Void> {
	AbortHandler handler;

	public AbortCommand(AbortHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public Void execute(ActRunningContext context) {
		handler.setActRunningConext(context);
		handler.handle();
		handler.printCost();
		return null;
	}

	@Override
	public void executeCancel(ActRunningContext context) {
		handler.handleCancel(context);
	}
}