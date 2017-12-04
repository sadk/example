package ${pkg}.mapper;

import java.util.List;

import com.syswin.common.page.Page;
import ${pkg}.model.${Model};  
import ${pkg}.model.${Model}Query;

/**
 * 
 * ${comment}
 *
 */
public interface ${Model}Mapper {
	${Model} getById(Long id);
	
	void save(${Model} model);

	void update(${Model} model);
	
	int deleteById(Long... ids);
	
	List<${Model}> getAll();
	
	/**
	 * 不分页pageNum=0;
	 * 分页请填充pageNum和pageSize属性;
	 * 
	 */
	List<${Model}> queryForList(${Model}Query query);
	
}
