package org.lsqt.syswin.uum.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.PositionModuleConfig;
import org.lsqt.syswin.authority.model.PositionModuleConfigQuery;
import org.lsqt.syswin.authority.model.PositionPermitConfig;
import org.lsqt.syswin.authority.model.PositionPermitConfigQuery;
import org.lsqt.syswin.authority.model.PositionPermitResult;
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
	
	/**
	 * 获取当前岗位的下级岗。
	 * （注： 使终包含当前岗！）
	 * @param positionId 当前岗位ID
	 * @param isCascade 如果为true获取当前岗位的所有下级岗,否则只获取直属下级
	 * @return 获取下级岗位
	 */
	public List<Position> getChildPosition (Long positionId,boolean ...isCascade) {
		List<Position> rs = new ArrayList<>();
		
		Position root = db.getById(Position.class, positionId);
		if(root == null) {
			return rs;
		}
		
		if(isCascade==null || isCascade.length==0 || !isCascade[0]){ // 获取直属下级岗
			PositionQuery filter = new PositionQuery();
			filter.setPid(positionId);
			List<Position> list = db.queryForList("queryForPage", Position.class, filter); // 获取当前岗位的直接下级岗(一层)

			list.add(root);// 包含当前岗的记录
			 
			return list;
		}
		
		else if(isCascade[0]) {
			if (root!=null && StringUtil.isBlank(root.getNodePath())) {
				throw new RuntimeException(String.format("岗位ID为%s的岗位，节点路径为空",positionId));
			}
			if (root != null) {
				PositionQuery pq = new PositionQuery();
				pq.setNodePath(root.getNodePath() + "%");
				List<Position> list = db.queryForList("queryForPage", Position.class, pq);
				return list;
			}
		}
		
		return rs;
	}
	
	public Position saveOrUpdate(Position model) {
		// 保存基本字段
		if(model.getPid()!=null) {
			Position pModel = db.getById(Position.class, model.getPid());
			model.setParentName(pModel.getName());
		}else {
			model.setPid(null);
			model.setParentName(null);
		}
		db.saveOrUpdate(model);

		Long cnt = db.executeQueryForObject(String.format("select count(1) from %s ",db.getFullTable(Position.class)), Long.class);
		long c=0;
		
		// （循环向上）处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Position parent = db.getById(Position.class, model.getPid());
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = db.getById(Position.class, parent.getPid());
			
			c++;
			
			if(c>cnt) { // 如果树状结构被破坏成死闭环，总是能退出
				break;
			}
		}
		
		if (!parentIds.isEmpty()) {
			Collections.reverse(parentIds);
			model.setNodePath(StringUtil.join(parentIds, ",")+",");
			db.update(model, "nodePath");
		}
		
		db.saveOrUpdate(model);
		
		return model;
	}
	
	public int deleteById(Long ... ids) {
		List<Long> idList = new ArrayList<>();
		for(Long id: ids){
			idList.add(id);
		}
		if(ids == null || ids.length==0)return 0;
		
		// 删除岗位
		int cnt = db.deleteById(Position.class, Arrays.asList(ids).toArray());
	 
		// 删除岗位用户关系
		String sql = String.format("delete from t_user_duties where duties_id in (%s)", StringUtil.join(idList, ","));
		db.executeUpdate(sql);
					
		// 删除岗位角色关系
		sql = String.format("delete from t_power_duties_role where duties_id in (%s) ", StringUtil.join(idList, ",")) ;
		db.executeUpdate(sql);
				
		//// ----------------------------------------
		// 删除岗位的数据查询权限配置
		 sql = String.format("delete from t_power_duties_permit_config where duties_id in (%s)",  StringUtil.join(Arrays.asList(ids), ","));
		db.executeUpdate(sql);
		
		// 删除岗位的数据查询权限数据配置项
		sql = String.format("delete from t_power_duties_module_config where duties_id in (%s)",  StringUtil.join(Arrays.asList(ids), ","));
		db.executeUpdate(sql);
		
		sql = String.format("delete from t_power_duties_permit_result where duties_id in (%s)",  StringUtil.join(Arrays.asList(ids), ","));
		db.executeUpdate(sql);
		
		return cnt;
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
			
			// 删除岗位的数据查询权限配置
			sql = String.format("delete from t_power_duties_config where duties_id in (%s)", postionIdsSql);
			db.executeUpdate(sql);
			
			// 删除岗位的数据查询权限配置结果
			sql = String.format("delete  from t_power_duties_data_query_permit_result where duties_id in (%s)",postionIdsSql);
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
	
	
	
	public List<Long> getPostionIds(List<Position> list) {
		List<Long> rs = new ArrayList<>();
		if(list==null || list.isEmpty()){
			return rs;
		}
		for (Position p: list) {
			rs.add(p.getId());
		}
		return rs;
	}
	
	

	// --------------------------------------------------------------------------------------------
	public void saveResolveResult(Long positionId) {
		db.executeUpdate("delete from t_power_duties_permit_result where duties_id=?", positionId);

		//// 保存个人权限
		PositionPermitConfigQuery qr = new PositionPermitConfigQuery();
		qr.setPositionId(positionId);
		PositionPermitConfig cfg = db.queryForObject("queryForPage", PositionPermitConfig.class, qr);

		List<PositionPermitResult> data = PositionPermitResult.prepreResultData(cfg,PositionPermitResult.LEVEL_MYSELF);
		/*
		for(PositionPermitResult d: data) {
			db.save(d);
		}*/
		db.batchSave(data);
		
		
		
		//// 保存"所有数据"权限
		PositionModuleConfigQuery filter1 = new PositionModuleConfigQuery();
		filter1.setPositionId(positionId);
		filter1.setLevels("4");
		List<PositionModuleConfig> dtList1 = db.queryForList("queryForPage", PositionModuleConfig.class, filter1);
		if (dtList1 != null && !dtList1.isEmpty()) {
			
			List<PositionPermitResult> pprList = new ArrayList<>(); // 收集批量对象
			for (PositionModuleConfig e: dtList1) {
				
				if(cfg!=null && StringUtil.isNotBlank(cfg.getOrgIdsQuery())) {
					List<Long> ids = StringUtil.split(Long.class,cfg.getOrgIdsQuery(),",");
					for(Long id: ids) {
						// 查询
						PositionPermitResult model = new PositionPermitResult();
						model.setPositionId(positionId);
						model.setModuleCode(e.getModuleCode());
						model.setOrgId(id);
						model.setLevel(PositionPermitResult.LEVEL_ALL);
						model.setType(PositionPermitResult.TYPE_QUERY);
						//db.save(model);
						pprList.add(model);
					}
				}
				
				//使用
				if(cfg!=null && StringUtil.isNotBlank(cfg.getOrgIdsUseness())) {
					List<Long> ids = StringUtil.split(Long.class,cfg.getOrgIdsUseness(),",");
					for(Long id: ids) {
						// 查询
						PositionPermitResult model = new PositionPermitResult();
						model.setPositionId(positionId);
						model.setModuleCode(e.getModuleCode());
						model.setOrgId(id);
						model.setLevel(PositionPermitResult.LEVEL_ALL);
						model.setType(PositionPermitResult.TYPE_USENESS);
						//db.save(model);
						pprList.add(model);
					}
				}
			}
			//return;
			
			db.batchSave(pprList);
		}
		
		
		
		//// 如果当前岗有配置下级或所有下级,保存下级权限
		PositionModuleConfigQuery filter = new PositionModuleConfigQuery();
		filter.setPositionId(positionId);
		filter.setLevels("2,3");
		List<PositionModuleConfig> dtList = db.queryForList("queryForPage", PositionModuleConfig.class, filter);
		
		if (dtList == null || dtList.isEmpty()) {
			return;
		}
		
		for (PositionModuleConfig n : dtList) {
			saveResolveResult(positionId, n);
		}
	}
	
	

	/**
	 * 
	 * @param p 当前岗位
	 * @param n 当前岗对应的“岗位模块范置”
	 * @param isCascade
	 */
	public void saveResolveResult(Long positionId, PositionModuleConfig n) {
		
		Boolean isCascade = null;
		if (PositionPermitResult.lEVEL_NEXT == n.getLevel()) {
			isCascade = false;
		} else if (PositionPermitResult.LEVEL_CHILD == n.getLevel()) {
			isCascade = true;
		}
		List<Position> dt = getChildPosition(n.getPositionId(),isCascade);
		List<Long> pids= getPostionIds(dt);
		
		PositionPermitConfigQuery q = new PositionPermitConfigQuery();
		q.setPositionIds(StringUtil.join(pids, ","));
		List<PositionPermitConfig> ppcData = db.queryForList("queryForPage", PositionPermitConfig.class, q);
		
		Set<String> orgIdsQuery = new HashSet<>();
		Set<String>  orgIdsUsess = new HashSet<>();
		for (PositionPermitConfig ele:ppcData) {
			orgIdsQuery.addAll(StringUtil.split(ele.getOrgIdsQuery(), ","));
			orgIdsUsess.addAll(StringUtil.split(ele.getOrgIdsUseness(), ","));
		}
		
		List<PositionPermitResult> pprList = new ArrayList<>();
		for (String id : orgIdsQuery) {
			PositionPermitResult model = new PositionPermitResult();
			model.setPositionId(positionId);
			model.setLevel(n.getLevel());
			model.setModuleCode(n.getModuleCode());
			model.setOrgId(Long.valueOf(id));
			model.setType(PositionPermitResult.TYPE_QUERY);
			//db.save(model);
			pprList.add(model);
		}

		for (String id : orgIdsUsess) {
			PositionPermitResult model = new PositionPermitResult();
			model.setPositionId(positionId);
			model.setLevel(n.getLevel());
			model.setModuleCode(n.getModuleCode());
			model.setOrgId(Long.valueOf(id));
			model.setType(PositionPermitResult.TYPE_USENESS);
			//db.save(model);
			pprList.add(model);
		}
		
		db.batchSave(pprList);
	}
	
	
}
