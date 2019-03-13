package org.lsqt.sys.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.sys.model.ApiDefinition;
import org.lsqt.sys.model.ApiDefinitionQuery;
import org.lsqt.sys.service.ApiDefinitionService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;

@Service
public class ApiDefinitionServiceImpl implements ApiDefinitionService{
	
	@Inject private Db db;
	
	public Page<ApiDefinition>  queryForPage(ApiDefinitionQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), ApiDefinition.class, query);
	}

	public List<ApiDefinition> getAll(){
		  return db.queryForList("getAll", ApiDefinition.class);
	}
	
	public ApiDefinition saveOrUpdate(ApiDefinition model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		int cnt = db.deleteById(ApiDefinition.class, Arrays.asList(ids).toArray());
		
		List<Long> list = Arrays.asList(ids);
		if(ArrayUtil.isNotBlank(list)) {
			db.executeUpdate("delete from api_param where definition_id in ("+StringUtil.join(list)+")");
		}
		
		
		return cnt;
	}

	public ApiDefinition getById(Long id) {
		return db.getById(ApiDefinition.class, id);
	}

	public List<ApiDefinition> queryForList(ApiDefinitionQuery query) {
		return db.queryForList("queryForPage", ApiDefinition.class, query);
	}
}
