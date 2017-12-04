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
import org.lsqt.syswin.authority.model.MenuQuery;
import org.lsqt.syswin.authority.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{
	
	@Inject private PlatformDb db;
	
	public Page<Menu>  queryForPage(MenuQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Menu.class, query);
	}

	public List<Menu> queryForList(MenuQuery query) {
		return db.queryForList("queryForPage", Menu.class, query);
	}
	
	public List<Menu> getAll(){
		  return db.queryForList("getAll", Menu.class);
	}
	
	public Menu saveOrUpdate(Menu model) {
		db.executePlan(true,new Plan() {
			@Override
			public void doExecutePlan() throws DbException {
				db.saveOrUpdate(model);

				// 循环向上，处理节点路径
				List<Long> parentIds = new ArrayList<>();
				parentIds.add(model.getId());
				
				Menu parent = db.getById(Menu.class, model.getPid());
				while (parent != null) {
					parentIds.add(parent.getId());

					parent = db.getById(Menu.class, parent.getPid());
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
		return db.deleteById(Menu.class, Arrays.asList(ids).toArray());
	}

	public void repairNodePath() {
		List<Menu> list =  db.queryForList("getAll", Menu.class);
		for (Menu model : list) {
			model.setUpdateTime(new Date());
			repairNodePath(model, list);
		}
	}
	// -------------------------------  修复节点路径辅助方法  -------------------------------
	Menu repairNodePath(Menu model,List<Menu> data) {
		int cnt = 0;
		int maxLoop = data.size();
		
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Menu parent = getRepairNodeById(model.getPid(),data);
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
	
	Menu getRepairNodeById(Long id,List<Menu> data) {
		for(Menu model : data) {
			if(model.getId().longValue() == id) {
				return model;
			}
		}
		return null;
	}
}
