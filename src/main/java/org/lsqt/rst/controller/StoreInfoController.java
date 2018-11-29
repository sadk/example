package org.lsqt.rst.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.StoreInfo;
import org.lsqt.rst.model.StoreInfoQuery;
import org.lsqt.rst.model.StoreManager;
import org.lsqt.rst.model.StoreManagerQuery;
import org.lsqt.rst.model.User;
import org.lsqt.rst.model.UserQuery;
import org.lsqt.rst.service.StoreInfoService;



/**
 * <pre>
 *	select * from bu_company_admin_relationship ; -- 一对多:一个厂区有多个驻场管理员  
 *	select * from bu_company_store_relationship ; -- 一对多:一个门店管理多个厂区   另，门店管理多个厂区
 * </pre>
 * @author mm
 *
 */
@Controller(mapping={"/rst/store_info"})
public class StoreInfoController {
	
	@Inject private StoreInfoService storeInfoService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public StoreInfo getById(Long id) throws IOException {
		return storeInfoService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<StoreInfo> queryForPage(StoreInfoQuery query) throws IOException {
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		return storeInfoService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<StoreInfo> getAll() {
		return storeInfoService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public StoreInfo saveOrUpdate(StoreInfo form) {
		form.setTenantCode(ContextUtil.getLoginTenantCode());
		return storeInfoService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return storeInfoService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/save_manager", "/m/save_manager" },text="保存门店管理员")
	public List<StoreManager> saveManager(String storeCode, String userCodes) {
		List<StoreManager> list = new ArrayList<>();
		if (StringUtil.isNotBlank(storeCode, userCodes)) {
			List<String> userCodeList = StringUtil.split(userCodes, ",");
			for (String userCode : userCodeList) {
				list.add(saveOneManager(storeCode, userCode));
			}
		}
		return list;
	}

	private StoreManager saveOneManager(String storeCode, String userCode) {
		StoreManagerQuery query = new StoreManagerQuery();
		query.setStoreCode(storeCode);
		query.setManagerCode(userCode);
		StoreManager model = db.queryForObject("queryForPage", StoreManager.class, query);

		if (model != null) {
			model.setUpdateDate(new Date());
			db.update(model, "updateDate");
		} else {
			UserQuery uq = new UserQuery();
			uq.setCode(userCode);
			User user = db.queryForObject("queryForPage", User.class, uq);

			StoreInfoQuery sq = new StoreInfoQuery();
			sq.setCode(storeCode);
			StoreInfo store = db.queryForObject("queryForPage", StoreInfo.class, sq);

			model = new StoreManager();
			model.setStoreCode(storeCode);
			model.setManagerCode(userCode);
			model.setManagerName(user.getRealName());
			model.setStoreName(store.getName());
			model.setCreateDate(new Date());
			model.setUpdateDate(new Date());
			model.setTenantCode(ContextUtil.getLoginTenantCode());
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
