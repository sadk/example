package ${pkg}.service.impl;

<#list columnList as column>
	<#if column.primaryKey == 1>
		<#assign pkColumn = column />
	</#if>
</#list>

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import ${pkg}.model.${Model};
import ${pkg}.model.${Model}Query;
import ${pkg}.service.${Model}Service;

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
		return ${model}Mapper.queryForPage(query);
	}
	
	public Page<${Model}> queryForList(${Model}Query query) {
		return ${model}Mapper.queryForList(query);
	}
}
