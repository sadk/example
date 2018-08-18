package org.lsqt.act.service.support;

import java.io.IOException;

import org.lsqt.act.model.ActRunningContext;

import freemarker.template.TemplateException;

/**
 * 加签命令
 * @author mmyuan
 *
 */
public class AssignCommand implements Command<Void> {
	AssignHandler handler;
	
	public AssignCommand(AssignHandler handler) {
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
