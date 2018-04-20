package org.lsqt.act.service.support;

import org.lsqt.act.model.ActRunningContext;

/**
 * 加签命令
 * @author admin
 *
 */
public class AssiginCommand implements Command<Void> {
	AssiginHandler handler;
	
	public AssiginCommand(AssiginHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public Void execute(ActRunningContext context) {
		handler.setActRunningConext(context);
		handler.handle();
		return null;
	}

}
