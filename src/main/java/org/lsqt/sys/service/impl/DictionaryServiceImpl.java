package org.lsqt.sys.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lsqt.act.model.NodeButton;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.model.DictionaryQuery;
import org.lsqt.sys.service.DictionaryService;

@Service
public class DictionaryServiceImpl implements DictionaryService{
	
	@Inject private Db db;
	
	public Page<Dictionary>  queryForPage(DictionaryQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Dictionary.class, query);
	}

	public List<Dictionary> getAll(){
		  return db.queryForList("getAll", Dictionary.class);
	}
	
	public List<Dictionary> queryForList(DictionaryQuery query) {
		return db.queryForList("queryForPage", Dictionary.class, query);
	}
	
	public Dictionary saveOrUpdate(Dictionary model) {
		db.saveOrUpdate(model);
		
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Dictionary parent = db.getById(Dictionary.class, model.getPid());
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = db.getById(Dictionary.class, parent.getPid());
		}
		
		if (!parentIds.isEmpty()) {
			Collections.reverse(parentIds);
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
			Dictionary Dictionary = db.getById(Dictionary.class, id);
			if(Dictionary!=null){
				String sql="delete from %s where node_path like %s";
				int temp = db.executeUpdate(String.format(sql, db.getFullTable(Dictionary.class),"'"+Dictionary.getNodePath()+"%'"));
				cnt += temp;
			}
		}
		return cnt;
	}
	
	public List<Dictionary> getOptionByCode(String code,String appCode) {
		return getOptionByCode(code,appCode,1); // 1=启用 0=禁用
	}
	
	@SuppressWarnings("unchecked")
	public List<Dictionary> getOptionByCode(String code,String appCode,Integer enable ) {
		if(StringUtil.isBlank(appCode)) {
			appCode = Application.APP_CODE_DEFAULT;
		}
		
		if(StringUtil.isBlank(code)) {
			return ArrayUtil.EMPTY_LIST;
		}
		
		return db.queryForList("getOptionByCode", Dictionary.class, code, appCode,enable);
	}
}
