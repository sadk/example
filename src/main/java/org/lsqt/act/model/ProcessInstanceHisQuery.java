package org.lsqt.act.model;

import java.util.List;

/**
 * 已结束的流程实例
 * @author mmyuan
 *
 */
public class ProcessInstanceHisQuery {
	private List<String> instanceIdList ;

	public List<String> getInstanceIdList() {
		return instanceIdList;
	}

	public void setInstanceIdList(List<String> instanceIdList) {
		this.instanceIdList = instanceIdList;
	}
}
