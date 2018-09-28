package ${pkg}.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.lsqt.components.util.lang.StringUtil;
import ${pkg}.model.${Model};
import ${pkg}.model.${Model}Query;
import ${pkg}.service.${Model}Service;

/**
 * ${comment}
 */
@Controller(mapping={"/${module}/${model}"})
public class ${Model}Controller {
	
	@Resource private ${Model}Service ${model}Service; 
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	@RequestBody
	public List<${Model}> queryForList(${Model}Query query) {
		return ${model}Service.queryForList(query);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	@RequestBody
	public Page<${Model}> queryForPage(${Model}Query query) {
		return ${model}Service.queryForPage(query); //<#--  首字母小写: ${Model?uncap_first}  大写:${Model?cap_first} --> 
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	@RequestBody
	public List<${Model}> getAll() {
		return ${model}Service.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	@RequestBody
	public ${Model} saveOrUpdate(${Model} form) {
		return ${model}Service.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	@RequestBody
	public ${Model} getById(Long id) {
		return ${model}Service.getById(id);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	@RequestBody
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return ${model}Service.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
