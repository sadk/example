package org.lsqt.sms.service;

import org.lsqt.components.db.Page;
import org.lsqt.sms.model.SmsMobileNoPackage;
import org.lsqt.sms.model.SmsMobileNoPackageQuery;

import java.io.File;
import java.util.Collection;
import java.util.List;

public interface TencentSmsService {
	
	List<SmsMobileNoPackage> queryForList(SmsMobileNoPackageQuery query);
	
	Page<SmsMobileNoPackage> queryForPage(SmsMobileNoPackageQuery query);

	SmsMobileNoPackage saveOrUpdate(SmsMobileNoPackage model);

	int deleteById(Long... ids);
	
	Collection<SmsMobileNoPackage> getAll();

	String uploadMobilePackage(File file);
}
