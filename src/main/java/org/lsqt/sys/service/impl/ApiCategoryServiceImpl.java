package org.lsqt.sys.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.lsqt.sys.model.ApiCategory;
import org.lsqt.sys.model.ApiCategoryQuery;
import org.lsqt.sys.service.ApiCategoryService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;

/**
 * 报表分类管理相关
 * @author mmyuan
 *
 */
@Service
public class ApiCategoryServiceImpl implements ApiCategoryService{
	
	@Inject private Db db;
	
	public ApiCategory getById(Long id) {
		return db.getById(ApiCategory.class, id);
	}
	
	public Page<ApiCategory>  queryForPage(ApiCategoryQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), ApiCategory.class, query);
	}

	public List<ApiCategory> getAll(){
		  return db.queryForList("getAll", ApiCategory.class);
	}
	
	public List<ApiCategory> queryForList(ApiCategoryQuery query) {
		return db.queryForList("queryForPage", ApiCategory.class, query);
	}
	
	public ApiCategory saveOrUpdate(ApiCategory model) {
		if(model.getPid() == null) {
			model.setPid(0L);
		}
		
		db.saveOrUpdate(model);  
		
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		ApiCategory parent = db.getById(ApiCategory.class, model.getPid());
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = db.getById(ApiCategory.class, parent.getPid());
		}
		
		if (!parentIds.isEmpty()) {
			Collections.reverse(parentIds);
			model.setNodePath(StringUtil.join(parentIds, ",")+",");
			db.update(model, "nodePath");
		}
		
		return model;
	}

	public int deleteById(Long ... ids) {
		if(ids == null || ids.length==0) {
			return 0;
		}
		int cnt = 0;
		for(Long id: ids){
			ApiCategory apiCategory = db.getById(ApiCategory.class, id);
			if(apiCategory!=null){
				String sql="delete from %s where node_path like %s";
				int temp = db.executeUpdate(String.format(sql, db.getFullTable(ApiCategory.class),"'"+apiCategory.getNodePath()+"%'"));
				cnt += temp;
			}
		}
		return cnt;
	}
	
 


	public void repairNodePath() {
		List<ApiCategory> list =  db.queryForList("getAll", ApiCategory.class);
		for (ApiCategory model : list) {
			model.setUpdateTime(new Date());
			repairNodePath(model, list);
		}
	}
	// -------------------------------  修复节点路径辅助方法  -------------------------------
	private ApiCategory repairNodePath(ApiCategory model,List<ApiCategory> data) {
		int cnt = 0;
		int maxLoop = data.size();
		
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		ApiCategory parent = getRepairNodeById(model.getPid(),data);
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
	
	private ApiCategory getRepairNodeById(Long id,List<ApiCategory> data) {
		for(ApiCategory model : data) {
			if(model.getId().longValue() == id) {
				return model;
			}
		}
		return null;
	}
	
}
