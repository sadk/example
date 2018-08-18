package org.lsqt.act.service.support;

import org.lsqt.act.model.ActRunningContext;

/**
 * 不同意-驳回到拟稿人
 * @author mmyuan
 *
 */
public class DisagreeCommand implements Command<Void>{
	private DisagreeHandler handler ;
	public DisagreeCommand(DisagreeHandler handler) {
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
