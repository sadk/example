package org.lsqt.components.db;

import org.lsqt.components.db.DbException;

/**
 * 执行一个sql计划
 * @author yuanmm
 *
 */
public interface Plan {
	void doExecutePlan() throws DbException;

}
