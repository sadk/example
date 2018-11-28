package org.lsqt.act.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.lsqt.act.model.Category;
import org.lsqt.act.model.CategoryQuery;
import org.lsqt.act.service.CategoryService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;

/**
 * 流程分类管理相关
 * @author mmyuan
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Inject private Db db;
	
	public Page<Category>  queryForPage(CategoryQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Category.class, query);
	}

	public List<Category> getAll(){
		  return db.queryForList("getAll", Category.class);
	}
	
	public List<Category> queryForList(CategoryQuery query) {
		return db.queryForList("queryForPage", Category.class, query);
	}
	
	public Category saveOrUpdate(Category model) {
		if(model.getPid() == null) {
			model.setPid(0L);
		}
		
		db.saveOrUpdate(model);
		
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Category parent = db.getById(Category.class, model.getPid());
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = db.getById(Category.class, parent.getPid());
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
			Category Category = db.getById(Category.class, id);
			if(Category!=null){
				String sql="delete from %s where node_path like %s";
				int temp = db.executeUpdate(String.format(sql, db.getFullTable(Category.class),"'"+Category.getNodePath()+"%'"));
				cnt += temp;
			}
		}
		return cnt;
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> getOptionByCode(String code,String appCode) {
		if(StringUtil.isBlank(appCode)) {
			appCode = Application.APP_CODE_DEFAULT;
		}
		
		if(StringUtil.isBlank(code)) {
			return ArrayUtil.EMPTY_LIST;
		}
		
		return db.queryForList("getOptionByCode", Category.class, code, appCode);
	}


	public void repairNodePath() {
		List<Category> list =  db.queryForList("getAll", Category.class);
		for (Category model : list) {
			model.setUpdateTime(new Date());
			repairNodePath(model, list);
		}
	}
	// -------------------------------  修复节点路径辅助方法  -------------------------------
	Category repairNodePath(Category model,List<Category> data) {
		int cnt = 0;
		int maxLoop = data.size();
		
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Category parent = getRepairNodeById(model.getPid(),data);
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
	
	Category getRepairNodeById(Long id,List<Category> data) {
		for(Category model : data) {
			if(model.getId().longValue() == id) {
				return model;
			}
		}
		return null;
	}
	
}
