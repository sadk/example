package org.lsqt.yue.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.yue.model.TechInfo;
import org.lsqt.yue.model.TechInfoQuery;
import org.lsqt.yue.service.TechInfoService;



/**
 * 技能信息
 * @author admin
 *
 */
@Controller(mapping={"/yue/tech_info"})
public class TechInfoController {
	
	@Inject private TechInfoService techInfoService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<TechInfo> queryForPage(TechInfoQuery query) throws IOException {
		return techInfoService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<TechInfo> getAll() {
		return techInfoService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public TechInfo saveOrUpdate(TechInfo form) {
		return techInfoService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return techInfoService.deleteById(list.toArray(new Long[list.size()]));
	}

}
