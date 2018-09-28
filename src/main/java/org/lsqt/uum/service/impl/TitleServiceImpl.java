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
import org.lsqt.uum.model.Org;
import org.lsqt.uum.model.Title;
import org.lsqt.uum.model.TitleQuery;
import org.lsqt.uum.service.TitleService;

@Service
public class TitleServiceImpl implements TitleService{
	
	@Inject private Db db;
	
	public Page<Title>  queryForPage(TitleQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Title.class, query);
	}

	public List<Title> getAll(){
		  return db.queryForList("queryForPage", Title.class);
	}
	
	public Title saveOrUpdate(Title model) {
		if(StringUtil.isBlank(model.getAppCode())){
			model.setAppCode(Application.APP_CODE_DEFAULT);
		}
		
		db.saveOrUpdate(model);
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Title parent = db.getById(Title.class, model.getPid());
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = db.getById(Title.class, parent.getPid());
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
			Title org = db.getById(Title.class, id);
			if(org!=null){
				String sql="delete from %s where node_path like %s";
				int temp = db.executeUpdate(String.format(sql, db.getFullTable(Title.class),"'"+org.getNodePath()+"%'"));
				cnt += temp;
			}
		}
		
		db.deleteById(Org.class, Arrays.asList(ids).toArray());
		return cnt;
	}
}
