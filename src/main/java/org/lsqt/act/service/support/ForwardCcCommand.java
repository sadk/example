package org.lsqt.act.service.support;

import org.lsqt.act.model.ActRunningContext;

/**
 * 流程常规转发处理
 * @author mmyuan
 *
 */
public class ForwardCcCommand implements Command<Void>{
	ForwardCcHandler handler;
	public ForwardCcCommand(ForwardCcHandler handler) {
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
