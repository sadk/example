package org.lsqt.rst.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.rst.model.UserContractInfo;
import org.lsqt.rst.model.UserContractInfoQuery;
import org.lsqt.rst.service.UserContractInfoService;




@Controller(mapping={"/rst/user_contract_info"})
public class UserContractInfoController {
	
	@Inject private UserContractInfoService userContractInfoService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public UserContractInfo getById(Long id) throws IOException {
		return userContractInfoService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<UserContractInfo> queryForPage(UserContractInfoQuery query) throws IOException {
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		return userContractInfoService.queryForPage(query);
	}
	
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public UserContractInfo saveOrUpdate(UserContractInfo form) {
		return userContractInfoService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return userContractInfoService.deleteById(list.toArray(new Long[list.size()]));
	}
}
