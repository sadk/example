package org.lsqt.sys.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.IdGenerator;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.orm.ORMappingIdGenerator;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.Region;
import org.lsqt.sys.model.RegionQuery;
import org.lsqt.sys.service.RegionService;

@Service
public class RegionServiceImpl implements RegionService{
	
	@Inject private Db db;
	
	public Page<Region>  queryForPage(RegionQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Region.class, query);
	}

	public List<Region> getAll(){
		  return db.queryForList("getAll", Region.class);
	}
	
	public List<Region> queryForList(RegionQuery query) {
		return db.queryForList("queryForPage", Region.class, query);
	}
	
	public Region saveOrUpdate(Region model) {
		String getByIdSql=String.format("select * from %s where id=?",db.getFullTable(Region.class));
		
		if(model.getId()==null){
			synchronized (model) {
				Long id = db.executeQueryForObject("select max(id)+1 from "+db.getFullTable(Region.class),Long.class); // 因要处理省市区三张表整合到一张表数据，没有做id映射
				model.setId(id);
				IdGenerator codeGen = new ORMappingIdGenerator();
				model.setCode(codeGen.getUUID58().toString());
				if(StringUtil.isBlank(model.getAppCode())) {
					model.setAppCode(Application.APP_CODE_DEFAULT);
				}
				if(model.getPid() == -1) {
					model.setType(Region.TYPE_NATIONAL);
				} else {
					Region temp = db.executeQueryForObject("getByIdSql", Region.class, model.getPid());
					if(temp!=null) {
						model.setType(temp.getType()+1);
					}
				}
				db.save(model);
			}
		} else {
			db.executeUpdate("update "+db.getFullTable(Region.class) + " set name=?,sn=?,remark=? where id=?", model.getName(),model.getSn(),model.getRemark(),model.getId());
			//db.update(model, "name","sn","remark");
		}
		
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		
		Region parent = db.executeQueryForObject(getByIdSql, Region.class, model.getPid());
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = db.executeQueryForObject(getByIdSql,Region.class, parent.getPid());
		}
		
		if (!parentIds.isEmpty()) {
			Collections.reverse(parentIds);
			model.setNodePath(StringUtil.join(parentIds, ","));
			db.executeUpdate(String.format("update %s set node_path=? where id=?",db.getFullTable(Region.class)),model.getNodePath(),model.getId());
		}
		
		return model;
	}

	public int deleteById(Long ... ids) {
		if(ids == null || ids.length==0) {
			return 0;
		}
		int cnt = 0;
		for(Long id: ids){
			Region Region = db.getById(Region.class, id);
			if(Region!=null){
				String sql="delete from %s where node_path like %s";
				int temp = db.executeUpdate(String.format(sql, db.getFullTable(Region.class),"'"+Region.getNodePath()+"%'"));
				cnt += temp;
			}
		}
		return cnt;
	}
	
	@SuppressWarnings("unchecked")
	public List<Region> getOptionByCode(String code,String appCode) {
		if(StringUtil.isBlank(appCode)) {
			appCode = Application.APP_CODE_DEFAULT;
		}
		
		if(StringUtil.isBlank(code)) {
			return ArrayUtil.EMPTY_LIST;
		}
		
		return db.queryForList("getOptionByCode", Region.class, code, appCode);
	}
}
