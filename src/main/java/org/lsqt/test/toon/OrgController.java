package org.lsqt.test.toon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import com.alibaba.fastjson.JSON;
/**
import com.qitoon.framework.organ.param.EmployeeListResult;
import com.qitoon.framework.organ.param.OrganUnitListResult;
import com.qitoon.framework.organ.param.PositionListResult;
import com.qitoon.framework.organ.pojo.Employee;
import com.qitoon.framework.organ.pojo.OrganUnit;
import com.qitoon.framework.organ.service.IEmployeeService;
import com.qitoon.framework.organ.service.IOrganUnitService;
import com.qitoon.framework.organ.service.IOrganizationService;
*/
@Controller(mapping={"/toon/org"})
public class OrgController {
	/**
	@Inject private IEmployeeService employeeService;
	@Inject private IOrganizationService organizationService;
	@Inject private IOrganUnitService organUnitService;
	*/
	
	@RequestMapping(mapping = { "/get_employee_list_by_organizationid", "/m/get_employee_list_by_organizationid" },text="获取一个企业下的所有用户")
	public List<Map> getEmployeeListByOrganizationId(String orgId) {
		List<Map> rssss = new ArrayList<>();
		/**
		EmployeeListResult rs = employeeService.getEmployeeListByOrganizationId(1427598448086793L);
		for(Employee e:  rs.getData()) {
			if(e!=null) {
				if(e.getEmployeeId() == 6943) {
					System.out.println(e.getMainPositionId());
					System.out.println(JSON.toJSONString(e,true));
				}
			}
			
			Map<String,Object> tttt= JSON.parseObject(JSON.toJSONString(e),Map.class);
			tttt.put("mainPositionId", e.getMainPositionId()+"");
			rssss.add(tttt);
		}
		*/
		return rssss;
	}
	public static void main(String[] args) {//9814662524231152   9814662524231151
		System.out.println(Long.MAX_VALUE);
	}

	/**
	@RequestMapping(mapping = { "/get_all_organ_units", "/m/get_all_organ_units" },text="获取组织机构的所有组织单元列表")
	public OrganUnitListResult getEmployeeListByOrganizationId2(String orgId) {
		OrganUnitListResult rs = organizationService.getAllOrganUnits(1427598448086793L);
		return rs;
	}
	
	@RequestMapping(mapping = { "/get_all_positions", "/m/get_all_positions" },text="获取组织机构的所有组织单元列表")
	public PositionListResult getAllPositions(String orgId) {
		PositionListResult  rs = organizationService.getAllPositions(1427598448086793L);
		return rs;
	}
	
	@RequestMapping(mapping = { "/getEmployeesCascade", "/m/getEmployeesCascade" },text="获取组织机构的所有组织单元列表")
	public EmployeeListResult getEmployeesCascade() {
		OrganUnit query = new OrganUnit();
		query.setOrganUnitId(5555L);
		query.setOrganizationId(1427598448086793L);
		EmployeeListResult  rs = organUnitService.getEmployeesCascade(query);
		return rs;
	}
	
	*/
}
