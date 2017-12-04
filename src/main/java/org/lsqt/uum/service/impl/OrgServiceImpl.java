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
import org.lsqt.uum.model.Group;
import org.lsqt.uum.model.GroupQuery;
import org.lsqt.uum.model.Org;
import org.lsqt.uum.model.OrgQuery;
import org.lsqt.uum.service.OrgService;

@Service
public class OrgServiceImpl implements OrgService{
	
	@Inject private Db db;
	
	public Page<Org>  queryForPage(OrgQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Org.class, query);
	}

	public List<Org> getAll(){
		  return db.queryForList("queryForPage", Org.class);
	}
	
	public Org saveOrUpdate(Org model) {
		if(StringUtil.isBlank(model.getAppCode())){
			model.setAppCode(Application.APP_CODE_DEFAULT);
		}
		
		db.saveOrUpdate(model);
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Org parent = db.getById(Org.class, model.getPid());
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = db.getById(Org.class, parent.getPid());
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
			Org org = db.getById(Org.class, id);
			if(org!=null){
				String sql = null;
				int temp = 0;
				
				OrgQuery query = new OrgQuery();
				query.setNodePath(org.getNodePath());
				List<Org> list = db.queryForList("queryForPage", Org.class, query); // 获取多层
				for(Org g: list) {
					sql = "delete from uum_user_org where org_id=?";
					temp = db.executeUpdate(sql, g.getId());
					cnt+=temp;
				}
				
				// 删除当前机构和（多层）子机构
				sql="delete from %s where node_path like %s";
				temp = db.executeUpdate(String.format(sql, db.getFullTable(Org.class),"'"+org.getNodePath()+"%'"));
				cnt += temp ;
			}
		}
		
		db.deleteById(Org.class, Arrays.asList(ids).toArray());
		return cnt;
	}

	public List<Org> getAllChildNodes(Long id) {
		List<Org> result = new ArrayList<>();
		Org org = db.getById(Org.class, id);
		if(org!=null) {
			OrgQuery query = new OrgQuery();
			query.setNodePath(org.getNodePath());
			result = db.queryForList("queryForPage", Org.class, query);
		}
		
		return result;
	}
}
