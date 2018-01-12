package org.lsqt.syswin.authority.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.PositionPermitConfig;
import org.lsqt.syswin.authority.model.PositionPermitResult;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.PositionQuery;
import org.lsqt.syswin.uum.service.PositionService;

import com.alibaba.fastjson.JSON;

/**
 * 岗位授权（org数据)配置
 *
 */
@Controller(mapping={"/syswin/position_org_permission"})
public class PositionOrgPermissionController {
	
	@Inject private PositionService positionService;
	@Inject private PlatformDb db;
	 
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/save_position_org_config","/m/save_position_org_config" },text="重构优化性能：（核心1）保存岗位的授权配置")
	public void savePositionOrgConfig(String positionIds,String itemsJson) {
		
		List<Long> orgIdsQuery = new ArrayList<>();
		List<Long> orgIdsUseness = new ArrayList<>();
		if (StringUtil.isNotBlank(itemsJson)) {
			List<Map> item = JSON.parseArray(itemsJson, Map.class);
			for (Map m : item) {
				Object orgId = m.get("orgId");
				Object permitType = m.get("permitType");
				
				if (orgId != null && permitType != null) {
					if (PositionPermitResult.TYPE_QUERY == Integer.valueOf(permitType.toString())) {
						orgIdsQuery.add(Long.valueOf(orgId.toString()));
					} else if (PositionPermitResult.TYPE_USENESS == Integer.valueOf(permitType.toString())) {
						orgIdsUseness.add(Long.valueOf(orgId.toString()));
					}
				}
			}
		}
		
		List<Long> positionIdList = StringUtil.split(Long.class,positionIds, ",");
		
		db.executeUpdate(String.format("delete from t_power_duties_permit_config where duties_id in (%s)",StringUtil.join(positionIdList, ",")));
		
		PositionQuery query = new PositionQuery();
		query.setIds(positionIds);
		List<Position> list = db.queryForList("queryForPage", Position.class, query);
		
		
		List<PositionPermitConfig> ppcList = new ArrayList<>();
	 
		for(Position position: list) {
			if(StringUtil.isBlank(position.getNodePath())) {
				throw new RuntimeException("ID为"+position.getId()+"的岗位节点路径为空");
			}
			PositionPermitConfig model = new PositionPermitConfig();
			model.setPositionId(position.getId());
			model.setPositionIdParent(position.getPid());
			model.setNodePath(position.getNodePath());
			model.setOrgIdsQuery(StringUtil.join(orgIdsQuery, ","));
			model.setOrgIdsUseness(StringUtil.join(orgIdsUseness, ","));
			 
			ppcList.add(model);
			
			// 保存本人权限数据
			
			// 如果当前岗位有被引用到“直属下级、所有下级”，从root岗重新解析入库

		}
		
		db.batchSave(ppcList);
	 
		
 
	}
	
 
}
