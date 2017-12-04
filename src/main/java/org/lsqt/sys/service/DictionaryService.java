package org.lsqt.sys.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.model.DictionaryQuery;

public interface DictionaryService {
	
	Page<Dictionary> queryForPage(DictionaryQuery query);

	Dictionary saveOrUpdate(Dictionary model);

	int deleteById(Long... ids);
	
	Collection<Dictionary> getAll();
	
	List<Dictionary> queryForList(DictionaryQuery query);
	
	List<Dictionary> getOptionByCode(String code,String appCode) ;
	
	/**
	 * 
	 * @param code
	 * @param appCode
	 * @param enable 1=启用 0=禁用
	 * @return
	 */
	List<Dictionary> getOptionByCode(String code,String appCode,Integer enable ) ;
}
