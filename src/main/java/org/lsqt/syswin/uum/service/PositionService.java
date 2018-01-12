package org.lsqt.syswin.uum.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.syswin.authority.model.PositionModuleConfig;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.PositionQuery;

public interface PositionService {
	
	Page<Position> queryForPage(PositionQuery query);

	List<Position>  queryForList(PositionQuery query);
	
	Position saveOrUpdate(Position model);

	int deleteById(Long... ids);
	
	Collection<Position> getAll();
	
	/**
	 * 获取当前岗位的下级岗。
	 * （注： 使终包含当前岗！）
	 * @param positionId 当前岗位ID
	 * @param isCascade 如果为true获取当前岗位的所有下级岗,否则只获取直属下级
	 * @return 获取下级岗位
	 */
	List<Position> getChildPosition (Long positionId,boolean ...isCascade) ;
	
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
	
	/**获取岗位ID**/
	List<Long> getPostionIds(List<Position> list);
	
	
	// ------------------------------------
	/**
	 * 解析岗位的权限数据
	 * @param positionId
	 */
	void saveResolveResult(Long positionId);
	
	/**
	 * 解析岗位的权限数据辅助方法
	 * @param positionId 
	 * @param conf 当前岗位配置的“岗位模块范围细分”记录
	 */
	void saveResolveResult(Long positionId, PositionModuleConfig conf);
}
