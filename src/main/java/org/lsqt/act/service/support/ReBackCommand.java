package org.lsqt.act.service.support;

import org.lsqt.act.model.ActRunningContext;

/**
 * 撤回
 * @author mmyuan
 *
 */
public class ReBackCommand  implements Command<Void> {
	ReBackHandler handler;

	public ReBackCommand(ReBackHandler handler) {
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