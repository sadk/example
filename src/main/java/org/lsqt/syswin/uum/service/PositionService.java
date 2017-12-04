package org.lsqt.syswin.uum.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.PositionQuery;

public interface PositionService {
	
	Page<Position> queryForPage(PositionQuery query);

	List<Position>  queryForList(PositionQuery query);
	
	Position saveOrUpdate(Position model);

	int deleteById(Long... ids);
	
	Collection<Position> getAll();
	
	/**
	 * 删除组织机构里的一个或多个岗位
	 * @param orgId 组织ID
	 * @param postionIds 岗位IDs
	 */
	void removePostionFromOrg(Long orgId,List<Long> postionIds);
	
	/***
	 * 获取用户的主岗位
	 * @param userId
	 * @return
	 */
	Position getUserMainPosition(Long userId);
	
	
	
	
	
	/**
	 * 修复岗位节点路径（如，直属上级岗位）
	 */
	void repairNodePath();
	
	/**
	 * 只修复nodePath为空的数据
	 */
	void repairNodePathForEmptyPath();
	
	/**
	 * 只修复pid为空的数据
	 */
	void repairNodePathForEmptyPid();
}
