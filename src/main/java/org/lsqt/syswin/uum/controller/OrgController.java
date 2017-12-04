package org.lsqt.syswin.uum.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.uum.model.Org;
import org.lsqt.syswin.uum.model.OrgQuery;
import org.lsqt.syswin.uum.service.OrgService;




@Controller(mapping={"/syswin/org"})
public class OrgController {
	
	@Inject private OrgService orgService; 
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Org> queryForPage(OrgQuery query) throws IOException {
		return orgService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<Org> queryForList(OrgQuery query) throws IOException {
		return orgService.queryForList(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Org> getAll() {
		return orgService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Org saveOrUpdate(Org form) {
		return orgService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return orgService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/repair_node_path", "/m/repair_node_path" },text="修复节点路径")
	public void repairNodePath() {
		orgService.repairNodePath();
	}
	
	@RequestMapping(mapping = { "/fill_node_path_text", "/m/fill_node_path_text" },text="填充节点路径文本")
	public void fillNodePath() {
		orgService.fillNodePath();
	}
}
