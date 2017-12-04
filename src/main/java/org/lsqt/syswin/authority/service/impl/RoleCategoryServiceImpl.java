package org.lsqt.syswin.authority.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.DbException;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.Plan;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.Menu;
import org.lsqt.syswin.authority.model.RoleCategory;
import org.lsqt.syswin.authority.model.RoleCategoryQuery;
import org.lsqt.syswin.authority.service.RoleCategoryService;

@Service
public class RoleCategoryServiceImpl implements RoleCategoryService{
	
	@Inject private PlatformDb db;
	
	public Page<RoleCategory>  queryForPage(RoleCategoryQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), RoleCategory.class, query);
	}
	
	public List<RoleCategory>  queryForList(RoleCategoryQuery query) {
		return db.queryForList("queryForPage", RoleCategory.class, query);
	}

	public List<RoleCategory> getAll(){
		  return db.queryForList("getAll", RoleCategory.class);
	}
	
	public RoleCategory saveOrUpdate(RoleCategory model) {
		db.executePlan(new Plan() {
			@Override
			public void doExecutePlan() throws DbException {
				if(model.getId()== null) {
					if(model.getPid() == null) {
						model.setPid(0L);
					}
				}
				
				db.saveOrUpdate(model);

				// 循环向上，处理节点路径
				List<Long> parentIds = new ArrayList<>();
				parentIds.add(model.getId());
				
				RoleCategory parent = db.getById(RoleCategory.class, model.getPid());
				while (parent != null) {
					parentIds.add(parent.getId());

					parent = db.getById(RoleCategory.class, parent.getPid());
				}
				
				if (!parentIds.isEmpty()) {
					Collections.reverse(parentIds);
					model.setNodePath(StringUtil.join(parentIds, ",")+",");
					db.update(model, "nodePath");
				}
			}
		});
		return model;
	}

	public int deleteById(Long ... ids) {
		if(ids == null || ids.length==0) return 0;
		db.executePlan(new Plan(){

			@Override
			public void doExecutePlan() throws DbException {
				String sql = "delete from t_power_role_type where node_path like ?"; //级联删除子级
				for(Long id: ids) {
					RoleCategory temp = db.getById(RoleCategory.class, id);
					if(temp!=null && StringUtil.isNotBlank(temp.getNodePath())) {
						db.executeUpdate(sql, temp.getNodePath()+"%");
					}
				}
			}
			
		});
		
		return ids.length;
	}
	
	public void repairNodePath() {
		List<RoleCategory> list =  db.queryForList("getAll", RoleCategory.class);
		for (RoleCategory model : list) {
			model.setUpdateTime(new Date());
			repairNodePath(model, list);
		}
	}
	// -------------------------------  辅助方法  -------------------------------
	RoleCategory repairNodePath(RoleCategory model,List<RoleCategory> data) {
		int cnt = 0;
		int maxLoop = data.size();
		
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		RoleCategory parent = getRepairNodeById(model.getPid(),data);
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = getRepairNodeById(parent.getPid(),data);
			
			cnt ++;
			
			if(cnt>maxLoop) { // 如果组织树产生死闭环，也可以跳出
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
	
	RoleCategory getRepairNodeById(Long id,List<RoleCategory> data) {
		for(RoleCategory model : data) {
			if(model.getId().longValue() == id) {
				return model;
			}
		}
		return null;
	}
}
