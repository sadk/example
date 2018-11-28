package ${pkg}.mapper;

import java.util.List;

import ${pkg}.model.${Model};  

/**
 * 
 * ${comment}
 *
 */
public interface ${Model}Mapper {
	${Model} getById(Long id);
	
	void ${Model} save(${Model} model);

	void update(${Model} model);
	
	int deleteById(Long... ids);
	
	List<${Model}> getAll();
	
	List<${Model}> queryForList(${Model}Query query);
	
	Page<${Model}> queryForPage(${Model}Query query);
}
