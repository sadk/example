package org.lsqt.act.service.support;

import org.lsqt.act.model.ActRunningContext;

/**
 * 流程常规下一步逻辑处理
 * @author mmyuan
 *
 */
public class GeneralCompleteCommand implements Command<Void>{
	GeneralCompleteHandler handler;
	
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
