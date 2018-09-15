package org.lsqt.uum.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.uum.model.Res;
import org.lsqt.uum.model.ResQuery;
import org.lsqt.uum.service.ResService;

@Service
public class ResServiceImpl implements ResService{
	
	@Inject private Db db;
	
	public Page<Res>  queryForPage(ResQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Res.class, query);
	}
	
	public List<Res>  queryForList(ResQuery query) {
		return db.queryForList("queryForPage", Res.class, query);
	}

	public List<Res> getAll(){
		  return db.queryForList("queryForPage", Res.class);
	}
	
	public Res saveOrUpdate(Res model) {
		if(StringUtil.isBlank(model.getAppCode())){
			model.setAppCode(Application.APP_CODE_DEFAULT);
		}
		
		db.saveOrUpdate(model);
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Res parent = db.getById(Res.class, model.getPid());
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = db.getById(Res.class, parent.getPid());
		}
		
		if (!parentIds.isEmpty()) {
			Collections.reverse(parentIds);
			model.setNodePath(StringUtil.join(parentIds, ",")+",");
			db.update(model, "nodePath");
		}
		
		
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		if(ids == null || ids.length==0) {
			return 0;
		}
		int cnt = 0;
		for(Long id: ids){
			Res res = db.getById(Res.class, id);
			if(res!=null){
				String sql="delete from %s where node_path like %s";
				int temp = db.executeUpdate(String.format(sql, db.getFullTable(Res.class),"'"+res.getNodePath()+"%'"));
				cnt += temp;
			}
		}
		
		db.deleteById(Res.class, Arrays.asList(ids).toArray());
		return cnt;
	}
	
	public List<Res> getAllChildNodes(Long id) {
		List<Res> result = new ArrayList<>();
		Res res = db.getById(Res.class, id);
		if(res!=null) {
			ResQuery query = new ResQuery();
			query.setNodePath(res.getNodePath());
			result = db.queryForList("queryForPage", Res.class, query);
		}
		
		return result;
	}
	
	public void repairNodePath() {
		List<Res> list =  db.queryForList("getAll", Res.class);
		for (Res model : list) {
			model.setUpdateTime(new Date());
			repairNodePath(model, list);
		}
	}
	
	// -------------------------------  辅助方法  -------------------------------
	private Res repairNodePath(Res model,List<Res> data) {
		int cnt = 0;
		int maxLoop = data.size();
		
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Res parent = getRepairNodeById(model.getPid(),data);
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = getRepairNodeById(parent.getPid(),data);
			
			cnt ++;
			
			if(cnt>maxLoop) { // 如果节点树产生死闭环，也可以跳出
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
	
	
	private Res getRepairNodeById(Long id,List<Res> data) {
		if(id==null) return null;
		
		for(Res model : data) {
			if(model.getId().longValue() == id) {
				return model;
			}
		}
		return null;
	}
}
