package org.lsqt.act.service.support;

import org.lsqt.act.model.ActRunningContext;

/**
 * 驳回到上一步
 * @author admin
 *
 */
public class RejectToChoosedCommand implements Command<Void> {
	RejectToChoosedHandler handler;

	public RejectToChoosedCommand(RejectToChoosedHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public Void execute(ActRunningContext context) {
		handler.setActRunningConext(context);
		handler.handle();
		return null;
	}

	@Override
	public void executeCancel(ActRunningContext context) {
		
	}

}
