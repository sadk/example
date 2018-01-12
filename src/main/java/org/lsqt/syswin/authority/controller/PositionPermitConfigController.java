package org.lsqt.syswin.authority.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.PositionPermitConfig;
import org.lsqt.syswin.authority.model.PositionPermitConfigQuery;
import org.lsqt.syswin.authority.model.PositionPermitResult;
import org.lsqt.syswin.authority.service.PositionPermitConfigService;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.PositionQuery;
import org.lsqt.syswin.uum.service.PositionService;

import com.alibaba.fastjson.JSON;

/**
 * 岗位授权（org数据)配置
 * @author admin
 *
 */
@Deprecated
@Controller(mapping={"/syswin/position_permit_config"})
public class PositionPermitConfigController {
	
	@Inject private PositionPermitConfigService positionPermitConfigService; 
	@Inject private PositionService positionService;
	@Inject private PlatformDb db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<PositionPermitConfig> queryForPage(PositionPermitConfigQuery query) throws IOException {
		return positionPermitConfigService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<PositionPermitConfig> getAll() {
		return positionPermitConfigService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public PositionPermitConfig saveOrUpdate(PositionPermitConfig form) {
		return positionPermitConfigService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return positionPermitConfigService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/get_dataquery_and_useness_config", "/m/get_dataquery_and_useness_config" },text="获取岗位的授权配置")
	public List<Map> getDataqueryAndUsenessConfig(Long positionId) {
		List<Map> list = new ArrayList<>(); // orgId,permitType=1 
		
		PositionPermitConfigQuery query = new PositionPermitConfigQuery();
		query.setPositionId(positionId);
		PositionPermitConfig cfg = db.queryForObject("queryForPage", PositionPermitConfig.class,query);
		
		if (cfg == null) {
			return list;
		}
		
		List<String> orgIdsQuery = StringUtil.split(cfg.getOrgIdsQuery(), ",");
		for(String e: orgIdsQuery) {
			if(StringUtil.isNotBlank(e)) {
				Map<String,Object> row = new HashMap<>();
				row.put("orgId", Long.valueOf(e));
				row.put("colType", "mySelf");
				list.add(row);
			}
		}
		
		List<String> orgIdsUseness = StringUtil.split(cfg.getOrgIdsUseness(), ",");
		//System.out.println(orgIdsUseness.size());
		for(String e: orgIdsUseness) {
			if(StringUtil.isNotBlank(e)) {
				Map<String,Object> row = new HashMap<>();
				row.put("orgId", Long.valueOf(e));
				row.put("colType", "useness");
				list.add(row);
			}
		}
		
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/save_position_config","/m/save_position_config" },text="（核心1）保存岗位的授权配置")
	public void savePositionConfig(String positionIds,String itemsJson) {
		
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
		
		Set<Long> refers = new HashSet<Long>();
		
		List<PositionPermitConfig> ppcList = new ArrayList<>();
		List<PositionPermitResult> pprList = new ArrayList<>();
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
			//db.save(model);
			ppcList.add(model);
			
			// 保存本人权限数据
			db.executeUpdate("delete from t_power_duties_permit_result where duties_id=? and level=?",position.getId(), PositionPermitResult.LEVEL_MYSELF);
			List<PositionPermitResult>  data = PositionPermitResult.prepreResultData(model, PositionPermitResult.LEVEL_MYSELF);
			for(PositionPermitResult d: data) {
				//db.save(d);
				pprList.add(d);
			}
			
			// 如果当前岗位有被引用到“直属下级、所有下级”，从root岗重新解析入库
			refers.add(position.getId());
			if(StringUtil.isNotBlank(position.getNodePath())) {
				refers.addAll(StringUtil.split(Long.class, position.getNodePath(), ","));
			}
		}
		
		db.batchSave(ppcList);
		db.batchSave(pprList);
		
		for(Long id: refers) {
			positionService.saveResolveResult(id);
		}
	}
	
 
}
