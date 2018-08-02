package org.lsqt.yue.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.yue.model.Tech;
import org.lsqt.yue.model.TechQuery;
import org.lsqt.yue.service.TechService;

@Service
public class TechServiceImpl implements TechService{
	
	@Inject private Db db;
	
	public Page<Tech>  queryForPage(TechQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(),Tech.class, query);
	}

	public List<Tech> getAll(){
		  return db.queryForList("queryForPage",Tech.class);
	}
	
	public Tech saveOrUpdate(Tech model) {
		if(StringUtil.isBlank(model.getAppCode())){
			model.setAppCode(Application.APP_CODE_DEFAULT);
		}
		
		db.saveOrUpdate(model);
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Tech parent = db.getById(Tech.class, model.getPid());
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = db.getById(Tech.class, parent.getPid());
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
			Tech tech = db.getById(Tech.class, id);
			if(tech!=null){
				String sql = null;
				int temp = 0;
				
				TechQuery query = new TechQuery();
				query.setNodePath(tech.getNodePath());
				List<Tech> list = db.queryForList("queryForPage", Tech.class, query); // 获取多层
				for(Tech g: list) {
					sql = "delete from yue_tech where id=?";
					temp = db.executeUpdate(sql, g.getId());
					cnt+=temp;
				}
				
				// 删除当前机构和（多层）子机构
				sql="delete from %s where node_path like %s";
				temp = db.executeUpdate(String.format(sql, db.getFullTable(Tech.class),"'"+tech.getNodePath()+"%'"));
				cnt += temp ;
			}
		}
		
		db.deleteById(Tech.class, Arrays.asList(ids).toArray());
		return cnt;
	}

	public List<Tech> getAllChildNodes(Long id) {
		List<Tech> result = new ArrayList<>();
		Tech Tech = db.getById(Tech.class, id);
		if(Tech!=null) {
			TechQuery query = new TechQuery();
			query.setNodePath(Tech.getNodePath());
			result = db.queryForList("queryForPage", Tech.class, query);
		}
		
		return result;
	}
}
