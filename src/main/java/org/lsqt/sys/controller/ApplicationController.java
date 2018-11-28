package org.lsqt.sys.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.ApplicationQuery;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.service.ApplicationService;


@Controller(mapping={"/application"})
public class ApplicationController {
	
	@Inject private ApplicationService applicationService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" }, view = View.JSON)
	public Page<Application> queryForPage(ApplicationQuery query) throws IOException {
		// HttpServletResponse res = ContextUtil.getResponse();
		// ContextUtil.mvc.getResponse();
		// ContextUtil.db.getDb();
		// ContextUtil.user.getUserId(); 
		// res.sendRedirect("http://sohu.com"); 
		return applicationService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" },view = View.JSON)
	public Collection<Application> getAll() {
		return applicationService.getAll();
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" },view = View.JSON)
	public Collection<Application> queryForList(ApplicationQuery query) {
		return db.queryForList("queryForPage", Application.class, query);
	}
	
	public static class Node {
		public Long id;
		public Long pid;
		public String name;
		public String code;
		
	}
	@RequestMapping(mapping = { "/all_tree", "/m/all_tree" },view = View.JSON)
	public Collection<Node> getAllTree() {
		Collection<Application> list = applicationService.getAll();
		Node root = new Node();
		root.id = 0L;
		root.name = "应用系统";
		root.pid = -1L;
		root.code ="-1";
		
		Collection<Node> rs = new ArrayList<>();
		rs.add(root);
		
		for(Application e: list) {
			Node child = new Node();
			child.id = e.getId();
			child.name = e.getName();
			child.pid = 0L;
			child.code = e.getCode();
			rs.add(child);
		}
		return rs;
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" }, view = View.JSON)
	public Application saveOrUpdate(Application form) {
		if(StringUtil.isBlank(form.getCode())) {
			form.setCode("code:"+System.currentTimeMillis());
		}
		return applicationService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" },view = View.JSON)
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return applicationService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
