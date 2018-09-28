package ${pkg}.service;

import java.util.List;

import org.lsqt.components.db.Page;
import ${pkg}.model.${Model};
import ${pkg}.model.${Model}Query;

public interface ${Model}Service {
	
	${Model} getById(Long id);
	
	${Model} saveOrUpdate(${Model} model);

	int deleteById(Long... ids);
	
	List<${Model}> getAll();
	
	Page<${Model}> queryForPage(${Model}Query query);
	
	List<${Model}> queryForList(${Model}Query query);
}
