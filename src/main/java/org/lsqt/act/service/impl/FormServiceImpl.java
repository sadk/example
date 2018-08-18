package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.act.model.Form;
import org.lsqt.act.model.FormQuery;
import org.lsqt.act.service.FormService;

@Service
public class FormServiceImpl implements FormService{
	
	@Inject private Db db;
	
	public Page<Form>  queryForPage(FormQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Form.class, query);
	}

	public List<Form> getAll(){
		  return db.queryForList("getAll", Form.class);
	}
	
	public Form saveOrUpdate(Form model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Form.class, Arrays.asList(ids).toArray());
	}
}
