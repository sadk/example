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
import org.lsqt.yue.model.TechInfoItem;
import org.lsqt.yue.model.TechInfoItemQuery;
import org.lsqt.yue.service.TechInfoItemService;

/**
 * 技能信息选项
 * @author mmyuan
 *
 */
@Controller(mapping={"/yue/tech_info_item"})
public class TechInfoItemController {
	
	@Inject private TechInfoItemService techInfoItemService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<TechInfoItem> queryForPage(TechInfoItemQuery query) throws IOException {
		return techInfoItemService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<TechInfoItem> getAll() {
		return techInfoItemService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public TechInfoItem saveOrUpdate(TechInfoItem form) {
		return techInfoItemService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return techInfoItemService.deleteById(list.toArray(new Long[list.size()]));
	}

}
