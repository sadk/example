package org.lsqt.rst.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.rst.model.Company;
import org.lsqt.rst.model.CompanyAdmin;
import org.lsqt.rst.model.CompanyAdminQuery;
import org.lsqt.rst.model.CompanyQuery;
import org.lsqt.rst.model.StoreInfo;
import org.lsqt.rst.model.StoreInfoQuery;
import org.lsqt.rst.model.StoreManager;
import org.lsqt.rst.model.StoreManagerQuery;
import org.lsqt.rst.model.User;
import org.lsqt.rst.model.UserQuery;
import org.lsqt.rst.service.CompanyAdminService;




@Controller(mapping={"/rst/company_admin"})
public class CompanyAdminController {
	
	@Inject private CompanyAdminService companyAdminService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public CompanyAdmin getById(Long id) throws IOException {
		return companyAdminService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<CompanyAdmin> queryForPage(CompanyAdminQuery query) throws IOException {
		return companyAdminService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<CompanyAdmin> getAll() {
		return companyAdminService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public CompanyAdmin saveOrUpdate(CompanyAdmin form) {
		return companyAdminService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return companyAdminService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	
	
	// ------------------------------------ 驻场管理员维护:一个厂区有多个驻场管理员 -------------------------------------
	
	@RequestMapping(mapping = { "/save_manager", "/m/save_manager" },text="保存驻场管理员")
	public List<CompanyAdmin> saveManager(String companyCode, String userCodes) {
		List<CompanyAdmin> list = new ArrayList<>();
		if (StringUtil.isNotBlank(companyCode, userCodes)) {
			List<String> userCodeList = StringUtil.split(userCodes, ",");
			for (String userCode : userCodeList) {
				list.add(saveOneManager(companyCode, userCode));
			}
		}
		return list;
	}

	private CompanyAdmin saveOneManager(String companyCode, String userCode) {
		CompanyAdminQuery query = new CompanyAdminQuery();
		query.setCompanyCode(companyCode);
		query.setUserCode(userCode);
		CompanyAdmin model = db.queryForObject("queryForPage", CompanyAdmin.class, query);

		if (model != null) {
			model.setUpdateDate(new Date());
			db.update(model, "updateDate");
		} else {
			UserQuery uq = new UserQuery();
			uq.setCode(userCode);
			User user = db.queryForObject("queryForPage", User.class, uq);

			CompanyQuery sq = new CompanyQuery();
			sq.setCode(companyCode);
			Company com = db.queryForObject("queryForPage", Company.class, sq);

			model = new CompanyAdmin();
			model.setCompanyCode(companyCode);
			model.setCompanyName(com.getShortName());
			
			model.setUserCode(userCode);
			model.setUserName(user.getRealName());

			model.setCreateDate(new Date());
			model.setUpdateDate(new Date());
			db.save(model);

		}

		return model;
	}
	
	@RequestMapping(mapping = { "/delete_manager", "/m/delete_manager" }, text = "删除驻场管理员")
	public int deleteManager(String companyCode, String userCodes) {
		int cnt = 0;
		if (StringUtil.isNotBlank(companyCode, userCodes)) {
			List<String> userCodeList = StringUtil.split(userCodes, ",");
			for (String userCode : userCodeList) {
				String sql = String.format("delete from %s where company_id=? and company_admin_id=?", db.getFullTable(CompanyAdmin.class));
				int c = db.executeUpdate(sql, companyCode, userCode);
				cnt += c;
			}
		}
		return cnt;
	}
	
	
}
