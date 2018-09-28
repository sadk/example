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
import org.lsqt.sys.model.Dictionary;
import org.lsqt.uum.model.Group;
import org.lsqt.uum.model.GroupQuery;
import org.lsqt.uum.model.Org;
import org.lsqt.uum.model.Role;
import org.lsqt.uum.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService{
	
	@Inject private Db db;
	
	public Page<Group>  queryForPage(GroupQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Group.class, query);
	}

	public List<Group> getAll(){
		  return db.queryForList("queryForPage", Group.class);
	}
	
	public Group saveOrUpdate(Group model) {
		if(StringUtil.isBlank(model.getAppCode())){
			model.setAppCode(Application.APP_CODE_DEFAULT);
		}
		
		db.saveOrUpdate(model);
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Group parent = db.getById(Group.class, model.getPid());
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = db.getById(Group.class, parent.getPid());
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
			Group group = db.getById(Group.class, id);
			if(group!=null){
				String sql = null;
				int temp = 0;
				
				// 删除组时，级联删除组和用户的关系
				GroupQuery query = new GroupQuery();
				query.setNodePath(group.getNodePath());
				List<Group> list = db.queryForList("queryForPage", Group.class, query); // 获取多层
				for(Group g: list) {
					sql = "delete from uum_user_object where obj_id=? and obj_type=?";
					temp = db.executeUpdate(sql, g.getId(),Role.OBJ_TYPE_组);
					cnt+=temp;
				}
				
				// 删除当前组和（多层）子组
				sql="delete from %s where node_path like %s";
				temp =  db.executeUpdate(String.format(sql, db.getFullTable(Group.class),"'"+group.getNodePath()+"%'"));
				cnt += temp;
			}
		}
		
		db.deleteById(Group.class, Arrays.asList(ids).toArray());
		return cnt;
	}
}
