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
import org.lsqt.rst.model.Company;
import org.lsqt.rst.model.CompanyQuery;
import org.lsqt.rst.model.StoreCompany;
import org.lsqt.rst.model.StoreCompanyQuery;
import org.lsqt.rst.model.StoreInfo;
import org.lsqt.rst.model.StoreInfoQuery;
import org.lsqt.rst.model.StoreManager;
import org.lsqt.rst.model.StoreManagerQuery;
import org.lsqt.rst.model.User;
import org.lsqt.rst.model.UserQuery;
import org.lsqt.rst.service.StoreCompanyService;



/**
 * <pre>
 * 	select * from bu_company_admin_relationship ; -- 一对多:一个厂区有多个驻场管理员  
 *	select * from bu_company_store_relationship ; -- 一对多:一个门店管理多个厂区   另，门店管理多个厂区
 * </pre>
 * 
 * 门店管理的厂区
 * 
 * @author mm
 *
 */
@Controller(mapping={"/rst/store_company"})
public class StoreCompanyController {
	
	@Inject private StoreCompanyService storeCompanyService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public StoreCompany getById(Long id) throws IOException {
		return storeCompanyService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<StoreCompany> queryForPage(StoreCompanyQuery query) throws IOException {
		return storeCompanyService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<StoreCompany> getAll() {
		return storeCompanyService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public StoreCompany saveOrUpdate(StoreCompany form) {
		return storeCompanyService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		if (StringUtil.isNotBlank(ids)) {
			List<Long> list = StringUtil.split(Long.class, ids, ",");
			return storeCompanyService.deleteById(list.toArray(new Long[list.size()]));
		}
		return 0;
	}
	
	
	// --------------------------------  门店管理的厂区 -----------------------------
	@RequestMapping(mapping = { "/save_manager", "/m/save_manager" },text="保存门店的厂区")
	public List<StoreCompany> saveManager(String storeCode, String companyCodes) {
		List<StoreCompany> list = new ArrayList<>();
		if (StringUtil.isNotBlank(storeCode, companyCodes)) {
			List<String> userCodeList = StringUtil.split(companyCodes, ",");
			for (String companyCode : userCodeList) {
				list.add(saveOneManager(storeCode, companyCode));
			}
		}
		return list;
	}

	private StoreCompany saveOneManager(String storeCode, String companyCode) {
		StoreCompanyQuery query = new StoreCompanyQuery();
		query.setStoreCode(storeCode);
		query.setCompanyCode(companyCode);
		StoreCompany model = db.queryForObject("queryForPage", StoreCompany.class, query);

		if (model != null) {
			model.setUpdateTime(new Date());
			db.update(model, "updateTime");
		} else {
			CompanyQuery uq = new CompanyQuery();
			uq.setCode(companyCode);
			Company com = db.queryForObject("queryForPage", Company.class, uq);

			StoreInfoQuery sq = new StoreInfoQuery();
			sq.setCode(storeCode);
			StoreInfo store = db.queryForObject("queryForPage", StoreInfo.class, sq);

			model = new StoreCompany();
			model.setStoreCode(storeCode);
			model.setStoreName(store.getName());
			
			model.setCompanyCode(companyCode);
			model.setCompanyName(com.getFullName());
			 
			model.setCreateTime(new Date());
			model.setUpdateTime(new Date());
			db.save(model);

		}

		return model;
	}
	
	@RequestMapping(mapping = { "/delete_manager", "/m/delete_manager" }, text = "删除门店管理员")
	public int deleteManager(String storeCode, String userCodes) {
		int cnt = 0;
		if (StringUtil.isNotBlank(storeCode, userCodes)) {
			List<String> userCodeList = StringUtil.split(userCodes, ",");
			for (String userCode : userCodeList) {
				String sql = String.format("delete from %s where store_id=? and manager_id=?", db.getFullTable(StoreManager.class));
				int c = db.executeUpdate(sql, storeCode, userCode);
				//db.batchUpdate(sql, fullArgs) ;
				cnt += c;
			}
		}
		return cnt;
	}
}
