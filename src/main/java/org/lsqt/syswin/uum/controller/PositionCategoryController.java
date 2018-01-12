package org.lsqt.syswin.uum.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lsqt.act.model.UserRule;
import org.lsqt.act.model.UserRuleQuery;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.PositionCategory;
import org.lsqt.syswin.uum.model.PositionCategoryQuery;
import org.lsqt.syswin.uum.model.PositionQuery;
import org.lsqt.syswin.uum.service.PositionCategoryService;




@Controller(mapping={"/syswin/position_category"})
public class PositionCategoryController {
	
	@Inject private PositionCategoryService positionCategoryService; 
	
	@Inject private Db db;
	@Inject private PlatformDb db2;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<PositionCategory> queryForPage(PositionCategoryQuery query) throws IOException {
		return positionCategoryService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<PositionCategory> queryForList(PositionCategoryQuery query) throws IOException {
		return positionCategoryService.queryForList(query); 
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<PositionCategory> getAll() {
		return positionCategoryService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public PositionCategory saveOrUpdate(PositionCategory form) {
		if(StringUtil.isBlank(form.getName())) {
			
		}
		return positionCategoryService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return positionCategoryService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	// -------------------------------------------岗位分类与具体岗位关系--------------------------------------------------------------
	
	@RequestMapping(mapping = { "/save_positions_in_category", "/m/save_positions_in_category" },text="保存岗位类型下的具体岗")
	public int savePositionsInCategory(Long categroyId,String positionIds) {
		if(categroyId!=null && StringUtil.isNotBlank(positionIds)) {
			List<Long> posIds = StringUtil.split(Long.class,positionIds, ",");
			db2.executeUpdate("delete from t_power_duties_type_mid where type_id=? and duties_id in ("+StringUtil.join(posIds, ",")+")", categroyId);
			
			List<Long> args = new ArrayList<>();
			for (Long pid : posIds) {
				args.add(categroyId);
				args.add(pid);
			}

			return db2.batchUpdate("insert into t_power_duties_type_mid(type_id,duties_id) values(?,?)", args.toArray());
		}
		return 0;
	}
	
	@RequestMapping(mapping = { "/remove_positions_in_category", "/m/remove_positions_in_category" },text="移除岗位类型下的具体岗")
	public int removePositionsInCategory(Long categroyId,String positionIds) {
		if (categroyId != null && StringUtil.isNotBlank(positionIds)) {
			List<Long> posIds = StringUtil.split(Long.class, positionIds, ",");
			return db2.executeUpdate("delete from t_power_duties_type_mid where type_id=? and duties_id in (" + StringUtil.join(posIds, ",") + ")", categroyId);
		}
		return 0;
	}
	
	@RequestMapping(mapping = { "/init_position_category_from_user_rule", "/m/init_position_category_from_user_rule" },text="从用户规则表里获取岗位分类")
	public int initPositionCategoryFromUserRule() {
		int rows = 0;
		
		UserRuleQuery query = new UserRuleQuery();
		query.setEnable(UserRule.ENABLE_ON);
		List<UserRule> data = db.queryForList("queryForPage", UserRule.class, query);
		
		//List<PositionCategory> pcList = new ArrayList<>();
		
		List<Object> paramValues = new ArrayList<>(); // 岗位分类下的岗位  [岗位id,分类id, ...]
		for (UserRule r: data) {
			PositionCategory type = new PositionCategory();
			type.setCode(r.getCode());
			type.setName(r.getName());
			type.setRemark("数据来源于用户规则表");
			positionCategoryService.saveOrUpdate(type);
			
			
			Set<Long> userIdSet = new HashSet<>();
			
			String sql = "select user_ids userIds from ext_user_rule_matrix_dept_user where user_rule_id = ? and user_ids is not null" ;
			List<Map<String, Object>> uids = db.executeQuery(sql, r.getId());
			if (!uids.isEmpty()) {
				for (Map<String, Object> row : uids) {
					Object userIds = row.get("userIds");
					if (userIds != null) {
						String userIdsStr = userIds.toString();
						userIdSet.addAll(StringUtil.split(Long.class, userIdsStr, ","));
					}
				}
			}
			
			// 查找一堆用户的岗位 
			if (!userIdSet.isEmpty()) {
				PositionQuery args = new PositionQuery();
				args.setUserIds(StringUtil.join(userIdSet, ","));
				List<Position> list = db2.queryForList("queryForPage", Position.class, args);
				
				if (!list.isEmpty()) {
					for(Position p: list) {
						paramValues.add(p.getId());
						paramValues.add(type.getId());
					}
				}
			}
		}
		
		String sql = "insert into t_power_duties_type_mid(duties_id,type_id) values(?,?)";
		rows = db2.batchUpdate(sql, paramValues.toArray());
		
		return rows;
	}
}
