package ${pkg}.service;

import java.util.List;

import com.syswin.common.page.Page;
import ${pkg}.model.${Model};
import ${pkg}.model.${Model}Query;

/**
 * ${comment}
 *
 */
public interface ${Model}Service {
	
	${Model} saveOrUpdate(${Model} model);

	int deleteById(Long... ids);
	
	List<${Model}> getAll();
	
	Page<${Model}> queryForPage(${Model}Query query);
	
	List<${Model}> queryForList(${Model}Query query);
}
