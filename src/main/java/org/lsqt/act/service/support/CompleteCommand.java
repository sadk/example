package org.lsqt.act.service.support;

import java.util.ArrayList;
import java.util.List;

import org.lsqt.act.model.ActRunningContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 审批按钮宏命令
 * @author mmyuan
 *
 */
public class CompleteCommand implements Command<Void>{
	private static final Logger  log = LoggerFactory.getLogger(CompleteCommand.class);
	
	private List<Command<Void>> cmdList= new ArrayList<>();
	public CompleteCommand() {}

	public CompleteCommand(List<Command<Void>> cmdList) {
		this.cmdList = cmdList;
	}
 
	@Override
	public Void execute(ActRunningContext context) {
		for(Command<Void> e: cmdList) {
			e.execute(context);
		}
		return null;
	}

	@Override
	public void executeCancel(ActRunningContext context) {
		for(Command<Void> e: cmdList) {
			e.executeCancel(context);
		}
	}
	
}
