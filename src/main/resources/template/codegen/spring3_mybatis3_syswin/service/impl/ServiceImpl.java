package ${pkg}.service.impl;

<#list columnList as column>
	<#if column.primaryKey == 1>
		<#assign pkColumn = column />
	</#if>
</#list>

import java.util.List;
import javax.annotation.Resource;


import org.springframework.stereotype.Service;



import com.syswin.common.page.Page;

import ${pkg}.model.${Model};
import ${pkg}.model.${Model}Query;
import ${pkg}.mapper.${Model}Mapper;
import ${pkg}.service.${Model}Service;

/**
 * ${comment}
 *
 */
@Service
public class ${Model}ServiceImpl implements ${Model}Service{
	
	@Resource private ${Model}Mapper ${model}Mapper;
	
	public ${Model} saveOrUpdate(${Model} model) {
		if (model.get${pkColumn.propertyName?cap_first}() == null) {
			${model}Mapper.save(model);
		} else {
			${model}Mapper.update(model);
		}
		return model;
	}

	public int deleteById(Long ... ids) {
		return ${model}Mapper.deleteById(ids);
	}
	
	public List<${Model}> getAll(){
		  return ${model}Mapper.getAll();
	}
	
	public Page<${Model}> queryForPage(${Model}Query query) {
		if (query.getPageNum() == null || query.getPageNum() == 0) {
			query.setPageNum(1);
		}
		
		List<${Model}> data = ${model}Mapper.queryForList(query);
		return new Page.PageModel<${Model}>().fill(data);
	}
	
	public List<${Model}> queryForList(${Model}Query query) {
		query.setPageNum(0);
		return ${model}Mapper.queryForList(query);
	}
}
