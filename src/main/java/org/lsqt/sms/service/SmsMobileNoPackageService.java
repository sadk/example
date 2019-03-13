package org.lsqt.sms.service;

import org.lsqt.components.db.Page;
import org.lsqt.sms.model.SmsMobileNoPackage;
import org.lsqt.sms.model.SmsMobileNoPackageQuery;

import java.io.File;
import java.util.Collection;
import java.util.List;

public interface SmsMobileNoPackageService {
	
	List<SmsMobileNoPackage> queryForList(SmsMobileNoPackageQuery query);
	
	Page<SmsMobileNoPackage> queryForPage(SmsMobileNoPackageQuery query);

	SmsMobileNoPackage saveOrUpdate(SmsMobileNoPackage model);

	String deleteById(Long... ids);
	
	Collection<SmsMobileNoPackage> getAll();

	String uploadMobilePackage(File file, int lineNums);

	String updatePackageStatus(Long id);
}
