package org.lsqt.syswin.uum.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.PositionQuery;
import org.lsqt.syswin.uum.service.PositionService;

@Service
public class PositionServiceImpl implements PositionService{
	
	@Inject private PlatformDb db;
	
	public Page<Position>  queryForPage(PositionQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Position.class, query);
	}

	public List<Position>  queryForList(PositionQuery query) {
		return db.queryForList("queryForPage", Position.class, query);
	}
	
	public List<Position> getAll(){
		  return db.queryForList("getAll", Position.class);
	}
	
	public Position saveOrUpdate(Position model) {
		if(StringUtil.isBlank(model.getParentName()) && model.getPid()!=null) {
			Position pModel = db.getById(Position.class, model.getPid());
			model.setParentName(pModel.getName());
		}
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Position.class, Arrays.asList(ids).toArray());
	}
	
	public void removePostionFromOrg(Long orgId,List<Long> postionIds) {
		
		if (orgId!=null && postionIds != null && !postionIds.isEmpty()) {
			String postionIdsSql = StringUtil.join(postionIds, ",");
			
			// 删除岗位
			String sql = "delete from %s where org_unit_id= %s and duties_id in(%s)";
			sql = String.format(db.getFullTable(Position.class), orgId, postionIdsSql);
			db.executeUpdate(sql);
			
			// 删除岗位用户关系
			sql = String.format("delete from t_user_duties where duties_id in (%s)", postionIdsSql);
			db.executeUpdate(sql);
			
			// 删除岗位角色关系
			sql = String.format("delete from t_power_duties_role where duties_id in (%s) ", postionIdsSql) ;
			db.executeUpdate(sql);
		}
	}

	public Position getUserMainPosition(Long userId) {
		return db.queryForObject("getUserMainPosition", Position.class, userId);
	}
	
	// -------------------------------------------------- 修复岗位节点路径 ------------------------------------
	public void repairNodePathForEmptyPath(){
		List<Position> list = db.queryForList("getAll", Position.class);
		for (Position model : list) {
			model.setUpdateTime(new Date());
			if(StringUtil.isBlank(model.getNodePath())) {
				repairNodePath(model, list);
			}
		}
	}
	
	public void repairNodePathForEmptyPid() {
		List<Position> list = db.queryForList("getAll", Position.class);
		for (Position model : list) {
			model.setUpdateTime(new Date());
			if(model.getPid() == null) {
				repairNodePath(model, list);
			}
		}
	}
	
	public void repairNodePath() {
		List<Position> list = db.queryForList("getAll", Position.class);
		for (Position model : list) {
			model.setUpdateTime(new Date());
			repairNodePath(model, list);
		}
	}
	
	Position repairNodePath(Position model,List<Position> data) {
		int cnt = 0;
		int maxLoop = data.size();
		
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Position parent = getRepairNodeById(model.getPid(),data);
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = getRepairNodeById(parent.getPid(),data);
			
			cnt ++;
			
			if(cnt>maxLoop) { // 如果岗位树产生死闭环，也可以跳出
				break;
			}
		}
		
		if (!parentIds.isEmpty()) {
			Collections.reverse(parentIds);
			model.setNodePath(StringUtil.join(parentIds, ",")+",");
			db.update(model,"nodePath","updateTime");
		}
		
		return model;
	}
	
	Position getRepairNodeById(Long id,List<Position> data) {
		if(id==null) return null;
		
		for(Position model : data) {
			if(model!=null && model.getId().longValue() == id) {
				return model;
			}
		}
		return null;
	}
}
