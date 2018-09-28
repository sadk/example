package ${pkg}.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import ${pkg}.model.${Model};
import ${pkg}.model.${Model}Query;
import ${pkg}.service.${Model}Service;

@Service
public class ${Model}ServiceImpl implements ${Model}Service{
	
	@Inject private Db db;
	
	public ${Model} getById(Long id) {
		return db.getById(${Model}.class, id) ;
	}
	
	public List<${Model}> queryForList(${Model}Query query) {
		return db.queryForList("queryForPage", ${Model}.class, query);
	}
	
	public Page<${Model}> queryForPage(${Model}Query query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), ${Model}.class, query);
	}

	public List<${Model}> getAll(){
		  return db.queryForList("getAll", ${Model}.class);
	}
	
	public ${Model} saveOrUpdate(${Model} model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(${Model}.class, Arrays.asList(ids).toArray());
	}
}
