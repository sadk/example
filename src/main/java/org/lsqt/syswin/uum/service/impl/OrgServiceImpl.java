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
import org.lsqt.syswin.uum.model.Org;
import org.lsqt.syswin.uum.model.OrgQuery;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.service.OrgService;


@Service
public class OrgServiceImpl implements OrgService{
	
	@Inject private PlatformDb db;
	
	public Page<Org>  queryForPage(OrgQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Org.class, query);
	}
	
	public List<Org>  queryForList(OrgQuery query) {
		return db.queryForList("queryForPage", Org.class, query);
	}

	public List<Org> getAll(){
		  return db.queryForList("getAll", Org.class);
	}
	
	public Org saveOrUpdate(Org model) {

		db.saveOrUpdate(model);
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Org parent = db.getById(Org.class, model.getPid());
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = db.getById(Org.class, parent.getPid());
		}
		
		if (!parentIds.isEmpty()) {
			Collections.reverse(parentIds);
			model.setNodePath(StringUtil.join(parentIds, ","));
			db.update(model, "nodePath");
		}
		
		
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Org.class, Arrays.asList(ids).toArray());
	}
	
	public void repairNodePath() {
		List<Org> list =  db.queryForList("getAll", Org.class);
		for (Org model : list) {
			model.setUpdateTime(new Date());
			repairNodePath(model, list);
		}
	}
	
	public Org getUserMainOrg(Long userId) {
		return db.queryForObject("getUserMainOrg", Org.class, userId);
	}
	
	// -------------------------------  辅助方法  -------------------------------
	Org repairNodePath(Org model,List<Org> data) {
		int cnt = 0;
		int maxLoop = data.size();
		
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Org parent = getRepairNodeById(model.getPid(),data);
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
	
	Org getRepairNodeById(Long id,List<Org> data) {
		if(id==null) return null;
		
		for(Org model : data) {
			if(model.getId().longValue() == id) {
				return model;
			}
		}
		return null;
	}

	@Override
	public void fillNodePath() {
		List<Org> list = db.queryForList("getAll", Org.class);
		if (list != null) {
			for (Org e : list) {
				if (StringUtil.isNotBlank(e.getNodePath())) {
					List<Long> ids = StringUtil.split(Long.class,e.getNodePath(), ",");
					StringBuilder nodePathText = new StringBuilder();
					for(Long id: ids){
						if(id!=null){
							for(Org n: list){
								if(id.longValue() == n.getId()){
									nodePathText.append(n.getName()+"-->");
									break;
								}
							}
						}
					}
					e.setNodePathText(nodePathText.toString());
					db.update(e, "nodePathText");
				}
			}
		}
		
	}
}
