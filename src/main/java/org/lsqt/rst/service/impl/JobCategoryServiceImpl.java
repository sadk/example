package org.lsqt.rst.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.JobCategory;
import org.lsqt.rst.model.JobCategoryAttr;
import org.lsqt.rst.model.JobCategoryQuery;
import org.lsqt.rst.model.JobDefinition;
import org.lsqt.rst.service.JobCategoryService;
import org.lsqt.sys.model.Application;

@Service
public class JobCategoryServiceImpl implements JobCategoryService{
	
	@Inject private Db db;
	
	public JobCategory getById(Long id) {
		return db.getById(JobCategory.class, id);
	}
	
	public Page<JobCategory>  queryForPage(JobCategoryQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), JobCategory.class, query);
	}

	public List<JobCategory> getAll(){
		  return db.queryForList("getAll", JobCategory.class);
	}
	
	public List<JobCategory> queryForList(JobCategoryQuery query) {
		return db.queryForList("queryForPage", JobCategory.class, query);
	}
	
	public JobCategory saveOrUpdate(JobCategory model) {
		db.saveOrUpdate(model);
		
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		JobCategory parent = db.getById(JobCategory.class, model.getPid());
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = db.getById(JobCategory.class, parent.getPid());
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
		
		Set<Long> categoryIdSet = new HashSet<>();
		int cnt = 0;
		for (Long id : ids) {
			JobCategory jobCategory = db.getById(JobCategory.class, id);
			if (jobCategory != null) {
				String sql = "select * from %s where node_path like %s";
				sql = String.format(sql, db.getFullTable(JobCategory.class), "'" + jobCategory.getNodePath() + "%'");
				List<Map<String,Object>> data = db.executeQuery(sql);
				for (Map<String,Object> e : data) {
					categoryIdSet.add(Long.valueOf(e.get("id").toString()));
				}
			}
		}
		
		if (ArrayUtil.isNotBlank(categoryIdSet)) {
			for (Long id : categoryIdSet) {
				//级联删除岗位列表和分类属性
				db.executeUpdate("delete from "+db.getFullTable(JobCategoryAttr.class)+" where category_id=?", id);
				db.executeUpdate("delete from "+db.getFullTable(JobDefinition.class)+" where category_id=?", id);
			}
			db.deleteById(JobCategory.class, categoryIdSet.toArray());
		}
		return cnt;
	}
	
	public List<JobCategory> getOptionByCode(String code,String appCode) {
		return getOptionByCode(code,appCode,1); // 1=启用 0=禁用
	}
	
	@SuppressWarnings("unchecked")
	public List<JobCategory> getOptionByCode(String code,String appCode,Integer enable ) {
		/*if(StringUtil.isBlank(appCode)) {
			appCode = Application.APP_CODE_DEFAULT;
		}*/
		
		if(StringUtil.isBlank(code)) {
			return ArrayUtil.EMPTY_LIST;
		}
		
		return db.queryForList("getOptionByCode", JobCategory.class, code, appCode,enable);
	}
	
	
	public void repairNodePath() {
		List<JobCategory> list =  db.queryForList("getAll", JobCategory.class);
		for (JobCategory model : list) {
			model.setUpdateTime(new Date());
			repairNodePath(model, list);
		}
	}
	
	// -------------------------------  辅助方法  -------------------------------
	private JobCategory repairNodePath(JobCategory model,List<JobCategory> data) {
		int cnt = 0;
		int maxLoop = data.size();
		
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		JobCategory parent = getRepairNodeById(model.getPid(),data);
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
	
	
	private JobCategory getRepairNodeById(Long id,List<JobCategory> data) {
		if(id==null) return null;
		
		for(JobCategory model : data) {
			if(model.getId().longValue() == id) {
				return model;
			}
		}
		return null;
	}
}
