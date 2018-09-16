package org.lsqt.api.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.api.model.Definition;
import org.lsqt.api.model.DefinitionQuery;
import org.lsqt.api.service.DefinitionService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.CacheManage;

@Service
public class DefinitionServiceImpl implements DefinitionService{
	
	@Inject private Db db;
	
	public Page<Definition>  queryForPage(DefinitionQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Definition.class, query);
	}

	public List<Definition> getAll(){
		  return db.queryForList("getAll", Definition.class);
	}
	
	public Definition saveOrUpdate(Definition model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		int cnt = db.deleteById(Definition.class, Arrays.asList(ids).toArray());
		
		List<Long> list = Arrays.asList(ids);
		if(ArrayUtil.isNotBlank(list)) {
			db.executeUpdate("delete from api_param where definition_id in ("+StringUtil.join(list)+")");
		}
		
		
		return cnt;
	}

	public Definition getById(Long id) {
		return db.getById(Definition.class, id);
	}

	public List<Definition> queryForList(DefinitionQuery query) {
		return db.queryForList("queryForPage", Definition.class, query);
	}
}
