package ${groupId}.modules.${realModules};

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

import com.syswin.common.util.lang.StringUtil;
import com.syswin.common.web.Result;
import com.syswin.platform.org.model.Dictionary;
import com.syswin.common.page.Page;

import ${pkg}.model.${Model};
import ${pkg}.model.${Model}Query;
import ${pkg}.service.${Model}Service;

/**
 * ${comment}
 */
@Controller
@RequestMapping("<#noparse>${adminPath}</#noparse>/${model}")
public class ${Model}Controller {
	
	@Resource private ${Model}Service ${model}Service; 
	
	@RequestMapping("/page")
	@ResponseBody
	public Result queryForPage(${Model}Query query) {
		Page<${Model}> page = new Page.PageModel<${Model}>().emptyPage();
		try{
			page = ${model}Service.queryForPage(query); // <#--  首字母小写: ${Model?uncap_first}  大写:${Model?cap_first} -->
		}catch(Exception ex) {
			return Result.fail(ex);
		}
		return Result.ok(page);
	}
	
	@RequestMapping("/all")
	@ResponseBody
	public Result getAll() {
		try{
			List<${Model}> list = ${model}Service.getAll();
			return Result.ok(list);
		}catch(Exception ex) {
			return Result.fail(ex);
		}
	}
	
	@RequestMapping("/save_or_update")
	@ResponseBody
	public Result saveOrUpdate(${Model} form) {
		${Model} model = null;
		try{
			model = ${model}Service.saveOrUpdate(form);
		}catch(Exception ex) {
			return Result.fail(ex);
		}
		return Result.ok(model);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(String ids) {
		try{
			List<Long> list = StringUtil.split(Long.class, ids, ",");
			${model}Service.deleteById(list.toArray(new Long[list.size()]));
		}catch(Exception ex) {
			return Result.fail(ex);
		}
		
		return Result.ok(ids);
	}
	
}
