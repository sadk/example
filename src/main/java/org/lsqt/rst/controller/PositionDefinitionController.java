package org.lsqt.rst.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.IdGenerator;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.orm.ORMappingIdGenerator;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.rst.model.PositionDefinition;
import org.lsqt.rst.model.PositionDefinitionQuery;
import org.lsqt.rst.service.PositionDefinitionService;




@Controller(mapping={"/rst/position_definition"})
public class PositionDefinitionController {
	private IdGenerator idgen = new ORMappingIdGenerator();
	
	@Inject private PositionDefinitionService positionDefinitionService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public PositionDefinition getById(Long id) throws IOException {
		return positionDefinitionService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<PositionDefinition> queryForPage(PositionDefinitionQuery query) throws IOException {
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		return positionDefinitionService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<PositionDefinition> getAll() {
		return positionDefinitionService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public PositionDefinition saveOrUpdate(PositionDefinition form) {
		form.setTenantCode(ContextUtil.getLoginTenantCode());
		if (StringUtil.isBlank(form.getCode())) {
			form.setCode(idgen.getUUID58().toString());
		}
		return positionDefinitionService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return positionDefinitionService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/get_welfare_item_nos", "/m/get_welfare_item_nos" },text="获取职位的福利列表")
	public List<Map<String,Object>> getWelfareItemNos(String positionCode) {
		String sql = "select * from bu_job_welfare_relationship where job_id=?";
		return db.executeQuery(sql, positionCode);
	}
	
	@RequestMapping(mapping = { "/save_position_addresses", "/m/save_position_addresses" },text="保存职位工作地址")
	public int savePositionAddresses(String positionCode,String workAddressCodes) {
		int cnt = 0;
		if (StringUtil.isNotBlank( positionCode,workAddressCodes )) {
			String sql = "delete from bu_job_address_relationship where job_id=?";
			db.executeUpdate(sql, positionCode);
			
			List<String> addr = StringUtil.split(workAddressCodes, ",");
			for(String addrId : addr) {
				String sql2 = "insert into bu_job_address_relationship(job_id,addr_id,tenant_code) values(?,?,?)";
				int ct = db.executeUpdate(sql2, positionCode,addrId,ContextUtil.getLoginTenantCode());
				cnt += ct ;
			}
		}
		return cnt;
	}
	
	@RequestMapping(mapping = { "/delete_position_addresses", "/m/delete_position_addresses" },text="删除职位工作地址")
	public int deletePositionAddresses(String positionCode,String wordAddressCodes) {
		int cnt = 0;
		if (StringUtil.isNotBlank(positionCode, wordAddressCodes)) {

			List<String> addr = StringUtil.split(wordAddressCodes, ",");
			for (String addrId : addr) {
				String sql = "delete from bu_job_address_relationship where job_id=? and addr_id=?";

				int ct = db.executeUpdate(sql, positionCode, addrId);
				cnt += ct;
			}
		}
		return cnt;
	}
	
}
