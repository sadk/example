package org.lsqt.uum.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.uum.model.Org;
import org.lsqt.uum.model.OrgQuery;
import org.lsqt.uum.model.Res;
import org.lsqt.uum.model.ResQuery;
import org.lsqt.uum.service.OrgService;
import org.lsqt.uum.service.ResService;




@Controller(mapping={"/res"})
public class ResController {
	
	@Inject private ResService resService; 
	
	@Inject private Db db;
	@Inject private OrgService orgService;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Res> queryForPage(ResQuery query) {
		return resService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Res> getAll() {
		return resService.getAll();
	}
	
	@RequestMapping(mapping = { "/all_selector", "/m/all_selector" },text="组织机构选择器用（过滤自己节点做为父节点等）")
	public Collection<Res> getAll(ResQuery query,Boolean isAllChild) {
		if(isAllChild!=null && isAllChild) { // 获取部门的权限(含下级部门)
			Long orgRoot = query.getOrgId();
			query.setOrgId(null);
			List<Org> list = orgService.getAllChildNodes(orgRoot);
			List<Long> temp = new ArrayList<>();
			for(Org e : list){
				temp.add(e.getId());
			}
			if(!temp.isEmpty()) {
				query.setOrgIds(StringUtil.join(temp, ","));
			}
		}
		
		return db.queryForList("queryForPage", Res.class, query);
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Res saveOrUpdate(Res form) {
		return resService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return resService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
