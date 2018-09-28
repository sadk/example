package ${pkg}.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import ${pkg}.model.${Model};
import ${pkg}.model.${Model}Query;

public interface ${Model}Service {
	
	${Model} getById(Long id);
	
	List<${Model}> queryForList(${Model}Query query);
	
	Page<${Model}> queryForPage(${Model}Query query);

	${Model} saveOrUpdate(${Model} model);

	int deleteById(Long... ids);
	
	Collection<${Model}> getAll();
}
