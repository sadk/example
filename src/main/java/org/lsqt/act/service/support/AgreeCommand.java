package org.lsqt.act.service.support;

import org.lsqt.act.model.ActRunningContext;

/**
 * 同意
 * @author admin
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
		return null;
	}

}
