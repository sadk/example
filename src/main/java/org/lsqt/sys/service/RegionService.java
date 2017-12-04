package org.lsqt.sys.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Region;
import org.lsqt.sys.model.RegionQuery;

public interface RegionService {
	
	Page<Region> queryForPage(RegionQuery query);

	Region saveOrUpdate(Region model);

	int deleteById(Long... ids);
	
	Collection<Region> getAll();
	
	List<Region> queryForList(RegionQuery query);
	
	List<Region> getOptionByCode(String code,String appCode) ;
}
