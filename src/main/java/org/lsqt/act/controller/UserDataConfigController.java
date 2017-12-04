package org.lsqt.act.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveObjectData;
import org.lsqt.act.model.UserDataConfig;
import org.lsqt.act.model.UserDataConfigParam;
import org.lsqt.act.model.UserDataConfigParamQuery;
import org.lsqt.act.model.UserDataConfigQuery;
import org.lsqt.act.model.UserDataMapping;
import org.lsqt.act.model.UserDataMappingQuery;
import org.lsqt.act.service.TaskService;
import org.lsqt.act.service.UserDataConfigService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.service.ApplicationService;

import com.alibaba.fastjson.JSON;
/**
import com.qitoon.framework.organ.param.EmployeeListResult;
import com.qitoon.framework.organ.param.OrganUnitListResult;
import com.qitoon.framework.organ.param.PositionListResult;
import com.qitoon.framework.organ.pojo.Employee;
import com.qitoon.framework.organ.pojo.OrganUnit;
import com.qitoon.framework.organ.pojo.Position;
import com.qitoon.framework.organ.service.IEmployeeService;
import com.qitoon.framework.organ.service.IOrganUnitService;
import com.qitoon.framework.organ.service.IOrganizationService;
import com.qitoon.framework.organ.service.IPositionService;
*/
/**
 * 
 * @author mmyuan
 *
 */
@Controller(mapping={"/act/user_data_config"})
public class UserDataConfigController {
	private static final String ORG_ID_香颂地产组织ID = "1427598448086793"; // 香颂地产组织ID
	
	@Inject private TaskService taskService;
	@Inject private ApplicationService applicationService;
	@Inject private UserDataConfigService userDataConfigService;
	@Inject private Db db;
	/**
	// 企通的接口
	@Inject private IEmployeeService employeeService;
	@Inject private IOrganizationService organizationService;
	@Inject private IOrganUnitService organUnitService;
	@Inject private IPositionService positionService ;
	*/
	@RequestMapping(mapping = { "/copy_into", "/m/copy_into" })
	public void copyInto(String appCode, String ids) {
		if(StringUtil.isNotBlank(appCode,ids)) {

			for (Long id : StringUtil.split(Long.class, ids, ",")) {
				// 构造配置对象
				UserDataConfig dbModel = db.getById(UserDataConfig.class, id);
				dbModel.setId(null);
				dbModel.setAppCode(appCode);
				db.save(dbModel);

				// 构造参数对象
				UserDataConfigParamQuery query = new UserDataConfigParamQuery();
				query.setConfigId(id.toString());
				List<UserDataConfigParam> list1 = db.queryForList("queryForPage", UserDataConfigParam.class, query);
				for (UserDataConfigParam p : list1) {
					p.setId(null);
					p.setConfigId(dbModel.getId().toString());
					db.save(p);
				}

				// 构造映射对象
				UserDataMappingQuery filter = new UserDataMappingQuery();
				filter.setConfigId(id.toString());
				List<UserDataMapping> list2 = db.queryForList("queryForPage", UserDataMapping.class, filter);
				for (UserDataMapping m : list2) {
					m.setId(null);
					m.setConfigId(id.toString());
					db.save(m);
				}
			}
			
		}
	}
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public UserDataConfig getById(Long id) {
		return db.getById(UserDataConfig.class, id);
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public List<UserDataConfig> saveOrUpdate(String data) {
		List<UserDataConfig> list = JSON.parseArray(data,UserDataConfig.class);
		for(UserDataConfig e: list) {
			e.setUpdateTime(new Date());
			db.saveOrUpdate(e);
		}
		return list;
	}
	
	@RequestMapping(mapping = { "/save_or_update_signle", "/m/save_or_update_signle" })
	public UserDataConfig saveOrUpdate(UserDataConfig e) {
		e.setUpdateTime(new Date());
		db.saveOrUpdate(e);
		return e;
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<UserDataConfig> queryForPage(UserDataConfigQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), UserDataConfig.class, query);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public void delete(String ids) {
		if (StringUtil.isNotBlank(ids)) {
			List<Long> list = StringUtil.split(Long.class, ids, ",");
			Long[] idArray = list.toArray(new Long[list.size()]);
			userDataConfigService.deleteById(idArray);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping = { "/view_data", "/m/view_data" })
	public ApproveObjectData viewData(Long configId) {
		List<ApproveObject> rs = new ArrayList<> ();
		
		ApproveObjectData data = userDataConfigService.viewData(configId);
		if(data.getMappedData()!=null && data.getMappedData() instanceof List) {
			List<Map<String,Object>> list =	(List<Map<String,Object>>) data.getMappedData();
			for(Map<String,Object> m : list) {
				ApproveObject obj = new ApproveObject();
				obj.setAppCode(m.get("appCode") == null ? "": m.get("appCode").toString());
				obj.setCode(m.get("code")  == null ? "": m.get("code").toString());
				obj.setEmail(m.get("email") == null ? "": m.get("email").toString());
				obj.setId(m.get("id")  == null ? "": m.get("id").toString());
				obj.setMobile(m.get("mobile") == null ? "": m.get("mobile").toString());
				obj.setName(m.get("name") == null ? "": m.get("name").toString());
				obj.setObject(m);
				obj.setPid(m.get("pid") == null ? "": m.get("pid").toString());
				obj.setRemark(m.get("remark") == null ? "": m.get("remark").toString());
				
				if(m.get("email")!=null) {
					obj.setRemark("邮件:" + m.get("email")+", 手机号:"+(m.get("mobile") == null ? "": m.get("mobile").toString()));
				}
				rs.add(obj);
			}
		}
		data.setMappedData(rs);
		
		if(data.getOriginalData()!=null) {
			try{
				Map<String,Object> temp = JSON.parseObject(data.getOriginalData().toString(), Map.class);
				data.setOriginalData(JSON.toJSONString(temp, true));
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return data;
	}
	
	@RequestMapping(mapping = { "/user_list", "/m/user_list" })
	public List<Map<String,Object>> getUserList(String orgId) throws IOException {
		List<Map<String,Object>> list = new ArrayList<>();
		
		if(StringUtil.isBlank(orgId)) {
			orgId = ORG_ID_香颂地产组织ID; 
		}
		/**
		EmployeeListResult rs = employeeService.getEmployeeListByOrganizationId(Long.valueOf(orgId));
		if(rs!=null && rs.getData()!=null && rs.getData().size()!=0) {
			for(Employee e: rs.getData()) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", e.getUserId());
				map.put("name", e.getEmployeeName());
				map.put("email",e.getEmail());
				map.put("mobile", e.getUserMobilePhone());
				list.add(map);
			}
		}
		*/
		return list;
	}
	 
	
	@RequestMapping(mapping = { "/org_list", "/m/org_list" },text="获取部门映射配置-构成url")
	public List<Map<String,Object>> getOrgList(String orgId) throws IOException {
		List<Map<String,Object>> rs = new ArrayList<>();
		
		if(StringUtil.isBlank(orgId)) {
			orgId = ORG_ID_香颂地产组织ID; 
		}
		/**
		OrganUnitListResult  temp = organizationService.getAllOrganUnits(Long.valueOf(orgId));
		if(temp!=null && temp.getData()!=null) {
			 for(OrganUnit e: temp.getData()) {
				 Map<String,Object> map = new HashMap<>();
				 map.put("id", e.getOrganUnitId());
				 map.put("pid", e.getParentId());
				 map.put("name", e.getOrganUnitName());
				 map.put("code",e.getExternalCode());
				 map.put("organizationId",e.getOrganizationId());
				 map.put("updateTime", new Date(e.getUpdateTime()));
				 rs.add(map);
			 }
		}
		*/
		return rs;
	}
	
	@RequestMapping(mapping = { "/position_list", "/m/position_list" },text="获取岗位配置-构成url")
	public List<Map<String,Object>> getPositionList(String orgId) throws IOException {
		List<Map<String,Object>> list = new ArrayList<>();
	
		if(StringUtil.isBlank(orgId)) {
			orgId = ORG_ID_香颂地产组织ID; 
		}
		
		List<Long> unitIds = new ArrayList<>();
		// 一次查出机构下所有岗位
		//PositionListResult temp = organizationService.getAllPositions(Long.valueOf(orgId)); 
		/**
		EmployeeListResult rss = employeeService.getEmployeeListByOrganizationId(Long.valueOf(orgId)); // 获取用户累坟其岗位
		if(rss!=null && rss.getData()!=null) {
			Set<Long> positionIds = new HashSet<>();
			for (Employee e: rss.getData()) {
				if(e == null) continue;
				positionIds.add(e.getMainPositionId());
			}
			PositionListResult tempList = positionService.getPositionListByPositionIds(Long.valueOf(orgId),new ArrayList<>(positionIds));
			if (tempList.getData() != null) {
				for (Position e: tempList.getData()) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("id", e.getPositionId()+"");
					map.put("name", e.getPositionName());
					map.put("code", e.getExternalCode());
					map.put("updateTime", new Date(e.getUpdateTime()));
					
					map.put("unitId", e.getOrganUnitId()+"");
					
					unitIds.add(e.getOrganUnitId());
					list.add(map);
				}
			}
		}
		*/
		/*
		if(temp!=null && temp.getData()!=null) {
			for(Position e: temp.getData()) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", e.getPositionId());
				map.put("name", e.getPositionName());
				map.put("code", e.getExternalCode());
				map.put("updateTime", new Date(e.getUpdateTime()));
				
				map.put("unitId", e.getOrganUnitId()+"");
				
				unitIds.add(e.getOrganUnitId());
				list.add(map);
			}
		}
		
		
		// 一次查出岗位对应的名称
		Map<String,String> map = new HashMap<>();
		OrganUnitListResult unitList = organUnitService.getOrganUnitListByUnitIds(Long.valueOf(orgId), unitIds);
		if(unitList!=null && unitList.getData()!=null) {
			for(OrganUnit e: unitList.getData()) {
				map.put(e.getOrganUnitId()+"", e.getOrganUnitName());
			}
		}
		
		for(Map<String,Object> e: list) {
			e.put("remark", map.get(e.get("unitId")));
		}
	*/
		System.out.println(list);
		return list;
	}
	
}
