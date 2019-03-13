package org.lsqt.sms.service.impl;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.sms.model.SmsMobileNoPackage;
import org.lsqt.sms.model.SmsMobileNoPackageQuery;
import org.lsqt.sms.service.TencentSmsService;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class TencentSmsServiceImpl implements TencentSmsService {
	
	@Inject
    private Db db;
	
	public List<SmsMobileNoPackage>  queryForList(SmsMobileNoPackageQuery query) {
		return db.queryForList("queryForPage",SmsMobileNoPackage.class, query);
	}
	
	public Page<SmsMobileNoPackage> queryForPage(SmsMobileNoPackageQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), SmsMobileNoPackage.class, query);
	}

	public List<SmsMobileNoPackage> getAll(){
		  return db.queryForList("getAll", SmsMobileNoPackage.class);
	}

	public SmsMobileNoPackage saveOrUpdate(SmsMobileNoPackage model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(SmsMobileNoPackage.class, Arrays.asList(ids).toArray());
	}

	@Override
	public String uploadMobilePackage(File file) {
		return null;
	}
}
