package ${pkg}.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import ${pkg}.model.${Model};
import ${pkg}.model.${Model}Query;

public interface ${Model}Service {
	
	Page<${Model}> queryForPage(${Model}Query query);

	${Model} saveOrUpdate(${Model} model);

	int deleteById(Long... ids);
	
	Collection<${Model}> getAll();
}
