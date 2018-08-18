package org.lsqt.act.service.support;

import java.util.List;
import java.util.Map;

import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ApproveOpinionQuery;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.RunTaskAssignForwardCc;
import org.lsqt.act.model.Task;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.lang.StringUtil;

import com.alibaba.fastjson.JSON;

/**
 * 驳回到上一步（暂不实现）
 * @author mmyuan
 *
 */
public class RejectUpHandler {
	ActRunningContext context;
	
	public void setActRunningConext(ActRunningContext context) {
		this.context = context;
	}
	
	public void handle() {
		 
	}
	
	public void handleCancel(ActRunningContext context)  {
		
	}
}
