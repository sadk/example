package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.ContractTemplate;
import org.lsqt.rst.model.ContractTemplateQuery;
import org.lsqt.rst.model.ContractTemplateVariable;
import org.lsqt.rst.service.ContractTemplateService;

@Service
public class ContractTemplateServiceImpl implements ContractTemplateService{
	
	@Inject private Db db;
	
	public ContractTemplate getById(Long id) {
		return db.getById(ContractTemplate.class, id) ;
	}
	
	public List<ContractTemplate> queryForList(ContractTemplateQuery query) {
		return db.queryForList("queryForPage", ContractTemplate.class, query);
	}
	
	public Page<ContractTemplate> queryForPage(ContractTemplateQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), ContractTemplate.class, query);
	}

	public List<ContractTemplate> getAll(){
		  return db.queryForList("getAll", ContractTemplate.class);
	}
	
	public ContractTemplate saveOrUpdate(ContractTemplate model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		if(ids!=null && ids.length >0) {
			//删除模板变量
			String sql = "delete from %s where template_id in (%s)";
			sql = String.format(sql, db.getFullTable(ContractTemplateVariable.class), StringUtil.join(Arrays.asList(ids)) );
			db.executeUpdate(sql);
			return db.deleteById(ContractTemplate.class, Arrays.asList(ids).toArray());
		}
		return 0;
	}
}
