package org.lsqt.cms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.lsqt.cms.model.Resource;
import org.lsqt.cms.model.ResourceQuery;
import org.lsqt.cms.service.ResourceService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;

@Service
public class ResourceServiceImpl implements ResourceService{
	
	@Inject private Db db;
	
	public Page<Resource>  queryForPage(ResourceQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Resource.class, query);
	}
	
	public List<Resource>  queryForList(ResourceQuery query) {
		return db.queryForList("queryForPage", Resource.class, query);
	}


	public List<Resource> getAll(){
		  return db.queryForList("getAll", Resource.class);
	}
	
	public Resource saveOrUpdate(Resource model) {
		if(StringUtil.isBlank(model.getAppCode())){
			model.setAppCode(Application.APP_CODE_DEFAULT);
		}
		
		db.saveOrUpdate(model);
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Resource parent = db.getById(Resource.class, model.getPid());
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = db.getById(Resource.class, parent.getPid());
		}
		
		if (!parentIds.isEmpty()) {
			Collections.reverse(parentIds);
			model.setNodePath(StringUtil.join(parentIds, ","));
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
			Resource org = db.getById(Resource.class, id);
			if(org!=null){
				String sql = null;
				int temp = 0;
				
				// 删除当前机构和（多层）子机构
				sql="delete from %s where node_path like %s";
				temp = db.executeUpdate(String.format(sql, db.getFullTable(Resource.class),"'"+org.getNodePath()+"%'"));
				cnt += temp ;
			}
		}
		
		db.deleteById(Resource.class, Arrays.asList(ids).toArray());
		return cnt;
	}

	public List<Resource> getAllChildNodes(Long id) {
		List<Resource> result = new ArrayList<>();
		Resource org = db.getById(Resource.class, id);
		if(org!=null) {
			ResourceQuery query = new ResourceQuery();
			query.setNodePath(org.getNodePath());
			result = db.queryForList("queryForPage", Resource.class, query);
		}
		
		return result;
	}
}
