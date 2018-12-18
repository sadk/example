package org.lsqt.rst.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.orm.ORMappingIdGenerator;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.StoreInfo;
import org.lsqt.rst.model.StoreInfoQuery;
import org.lsqt.rst.model.StoreManager;
import org.lsqt.rst.model.StoreRegion;
import org.lsqt.rst.service.StoreInfoService;

@Service
public class StoreInfoServiceImpl implements StoreInfoService{
	
	@Inject private Db db;
	
	public StoreInfo getById(Long id) {
		return db.getById(StoreInfo.class, id) ;
	}
	
	public List<StoreInfo> queryForList(StoreInfoQuery query) {
		return db.queryForList("queryForPage", StoreInfo.class, query);
	}
	
	public Page<StoreInfo> queryForPage(StoreInfoQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), StoreInfo.class, query);
	}

	public List<StoreInfo> getAll(){
		  return db.queryForList("getAll", StoreInfo.class);
	}
	
	public StoreInfo saveOrUpdate(StoreInfo model) {
		if (StringUtil.isBlank(model.getCode())) {
			model.setCode(new ORMappingIdGenerator().getUUID58().toString());
		}
		db.saveOrUpdate(model);

		
		String sql = "delete from bu_store_city_relationship where store_id=?";
		db.executeUpdate(sql, model.getCode());
		
		StoreRegion region = new StoreRegion();
		region.setProvince(model.getProvinceName());
		region.setProvinceCode(model.getProvinceCode());
		region.setCity(model.getCityName());
		region.setCityCode(model.getCityCode());
		region.setArea(model.getAreaName());
		region.setAreaCode(model.getAreaCode());
		region.setStoreCode(model.getCode());
		region.setStoreName(model.getName());
		region.setTenantCode(model.getTenantCode());
		db.saveOrUpdate(region);
		
		return model;
	}

	public int deleteById(Long... ids) {
		List<String> codeList = new ArrayList<>();
		for (Long id : ids) {
			StoreInfo model = getById(id);
			if (model != null) {
				codeList.add(model.getCode());
			}
		}

		if (codeList.size() > 0) { 
			// 级联删除门店管理员
			String sql = String.format("delete from %s where store_id in (%s)", db.getFullTable(StoreManager.class),
					StringUtil.join(codeList,",","'","'"));
			db.executeUpdate(sql);
		
			String sql2 = String.format("delete from bu_company_store_relationship where store_id in (%s)",
					StringUtil.join(codeList,",","'","'"));
			db.executeUpdate(sql2);
			
		}
		
		return db.deleteById(StoreInfo.class, Arrays.asList(ids).toArray());
	}
}
