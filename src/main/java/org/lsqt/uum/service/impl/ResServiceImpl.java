package org.lsqt.uum.service.impl;

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
import org.lsqt.uum.model.Res;
import org.lsqt.uum.model.ResQuery;
import org.lsqt.uum.service.ResService;

@Service
public class ResServiceImpl implements ResService{
	
	@Inject private Db db;
	
	public Page<Res>  queryForPage(ResQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Res.class, query);
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
}
