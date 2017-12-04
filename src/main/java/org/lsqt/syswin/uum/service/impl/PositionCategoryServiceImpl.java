package org.lsqt.syswin.uum.service.impl;

import java.util.ArrayList;
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
import org.lsqt.syswin.uum.model.PositionCategory;
import org.lsqt.syswin.uum.model.PositionCategoryQuery;
import org.lsqt.syswin.uum.service.PositionCategoryService;

@Service
public class PositionCategoryServiceImpl implements PositionCategoryService{
	
	@Inject private PlatformDb db;
	
	public Page<PositionCategory>  queryForPage(PositionCategoryQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), PositionCategory.class, query);
	}
	
	public List<PositionCategory>  queryForList(PositionCategoryQuery query) {
		return db.queryForList("queryForPage", PositionCategory.class, query);
	}

	public List<PositionCategory> getAll(){
		  return db.queryForList("getAll", PositionCategory.class);
	}
	
	public PositionCategory saveOrUpdate(PositionCategory model) {
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
				
				PositionCategory parent = db.getById(PositionCategory.class, model.getPid());
				while (parent != null) {
					parentIds.add(parent.getId());

					parent = db.getById(PositionCategory.class, parent.getPid());
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
				String sql = "delete from t_power_duties_type where node_path like ?"; //级联删除子级
				for(Long id: ids) {
					PositionCategory temp = db.getById(PositionCategory.class, id);
					if(temp!=null && StringUtil.isNotBlank(temp.getNodePath())) {
						db.executeUpdate(sql, temp.getNodePath()+"%");
					}
				}
			}
			
		});
		
		return ids.length;
	}
	
	public void repairNodePath() {
		List<PositionCategory> list =  db.queryForList("getAll", PositionCategory.class);
		for (PositionCategory model : list) {
			model.setUpdateTime(new Date());
			repairNodePath(model, list);
		}
	}
	// -------------------------------  辅助方法  -------------------------------
	PositionCategory repairNodePath(PositionCategory model,List<PositionCategory> data) {
		int cnt = 0;
		int maxLoop = data.size();
		
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		PositionCategory parent = getRepairNodeById(model.getPid(),data);
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
	
	PositionCategory getRepairNodeById(Long id,List<PositionCategory> data) {
		for(PositionCategory model : data) {
			if(model.getId().longValue() == id) {
				return model;
			}
		}
		return null;
	}
}
