package org.lsqt.act.service.support;

import org.lsqt.act.model.ActRunningContext;

/**
 * 同意
 * @author mmyuan
 *
 */
public class AgreeCommand implements Command<Void> {
	AgreeHandler handler;

	public AgreeCommand(AgreeHandler handler) {
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
