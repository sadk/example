package org.lsqt.sys.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Category;
import org.lsqt.sys.model.CategoryQuery;
import org.lsqt.sys.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Inject private Db db;
	
	@Override
	public Category getByCode(String code) {
		CategoryQuery query = new CategoryQuery();
		query.setCode(code);
		List<Category> list = db.queryForList("queryForPage",Category.class, query);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
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
			Collections.sort(parentIds);
			model.setNodePath(StringUtil.join(parentIds, ","));
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
			Category category = db.getById(Category.class, id);
			if(category!=null){
				String sql="delete from %s where node_path like %s";
				int temp = db.executeUpdate(String.format(sql, db.getFullTable(Category.class),"'"+category.getNodePath()+"%'"));
				cnt += temp;
			}
		}
		return cnt;
	}
}
