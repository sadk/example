package org.lsqt.sys.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.Default;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.model.DictionaryQuery;
import org.lsqt.sys.service.DictionaryService;

@Controller(mapping={"/dictionary"})
public class DictionaryController {
	@Inject private DictionaryService dictionaryService; 
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Dictionary> queryForPage(DictionaryQuery query) throws IOException {
		return dictionaryService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Dictionary> all(DictionaryQuery query) {
		  return dictionaryService.queryForList(query);
	}
	
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Dictionary saveOrUpdate(Dictionary form) {
		return dictionaryService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return dictionaryService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/option", "/m/option" })
	public List<Dictionary> option(@Default("1000") String  appCode,String code,Integer enable) {
		if(StringUtil.isBlank(appCode)) {
			appCode = Application.APP_CODE_DEFAULT;
		}
		
		return dictionaryService.getOptionByCode(code, appCode,enable);
	}
	
}