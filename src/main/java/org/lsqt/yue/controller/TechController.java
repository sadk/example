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
import org.lsqt.yue.model.Tech;
import org.lsqt.yue.model.TechQuery;
import org.lsqt.yue.service.TechService;


/**
 * 技能定义
 * @author mmyuan
 *
 */
@Controller(mapping={"/yue/tech"})
public class TechController {
	
	@Inject private TechService techService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Tech> queryForPage(TechQuery query) throws IOException {
		return techService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Tech> getAll() {
		return techService.getAll();
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" },text="选择器用（过滤自己节点做为父节点等）")
	public Collection<Tech> getAll(TechQuery query) {
		return db.queryForList("queryForPage", Tech.class, query);
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Tech saveOrUpdate(Tech form) {
		return techService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return techService.deleteById(list.toArray(new Long[list.size()]));
	}
}
