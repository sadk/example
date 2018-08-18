package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.FormField;
import org.lsqt.act.model.FormFieldQuery;
import org.lsqt.components.db.Page;


public interface FormFieldService {
	
	Page<FormField> queryForPage(FormFieldQuery query);
	
	List<FormField> queryForList(FormFieldQuery query);

	FormField saveOrUpdate(FormField model);

	int deleteById(Long... ids);
	
	Collection<FormField> getAll();
	
	/**
	 * 跟据主表单主表名，导入主表的字段信息
	 * @param tableName
	 */
	void impColumsByTable(Long formId);
	
}
