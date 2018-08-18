package org.lsqt.act.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.Form;
import org.lsqt.act.model.FormQuery;

/**
 * 表单管理
 * @author mmyuan
 *
 */
public interface FormService {
	
	Page<Form> queryForPage(FormQuery query);

	Form saveOrUpdate(Form model);

	int deleteById(Long... ids);
	
	Collection<Form> getAll();
}
