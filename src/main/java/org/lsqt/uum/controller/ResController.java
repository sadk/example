package org.lsqt.uum.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Cache;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.uum.model.Org;
import org.lsqt.uum.model.Res;
import org.lsqt.uum.model.ResQuery;
import org.lsqt.uum.model.Role;
import org.lsqt.uum.model.User;
import org.lsqt.uum.service.ResService;




@Controller(mapping={"/res"})
public class ResController {
	
	@Inject private ResService resService; 
	
	@Inject private Db db;
 
	@Cache(Res.class)
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public Res getById(Long id) {
		return resService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Res> queryForPage(ResQuery query) {
		return resService.queryForPage(query); //  
	}
	
	@Cache(Res.class)
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Res> getAll() {
		return resService.getAll();
	}
	
	/**
	 * 
	 * @param query 
	 * @param isEnableTreeQuery 是否开启树状查询
	 * @return
	 */
	@RequestMapping(mapping = { "/list", "/m/list" })
	public Collection<Res> queryForList(ResQuery query,Boolean isEnableTreeQuery) {
		
		List<Res> list = resService.queryForList(query);
		
		if(ArrayUtil.isNotBlank(list) && (isEnableTreeQuery!=null && isEnableTreeQuery)) {
			List<String> nodePathList = new ArrayList<>();
			for(Res e: list) {
				nodePathList.add(e.getNodePath());
			}
			if(ArrayUtil.isNotBlank(nodePathList)) {
				//ResQuery q = new ResQuery();
				query.setNodePathList(nodePathList);
				return resService.queryForList(query);
			}
		}
		return list;
	}
	
	@Cache(Res.class)
	@RequestMapping(mapping = { "/all_selector", "/m/all_selector" },text="资源选择器使用（过滤自己节点做为父节点等）")
	public Collection<Res> getAll(ResQuery query,Boolean isAllChild) {
		if(isAllChild!=null && isAllChild) {
			Long resRoot = query.getOrgId();
			query.setOrgId(null);
			List<Res> list = resService.getAllChildNodes(resRoot);
			List<Long> temp = new ArrayList<>();
			for(Res e : list){
				temp.add(e.getId());
			}
			if(!temp.isEmpty()) {
				query.setOrgIds(StringUtil.join(temp, ","));
			}
		}
		
		return db.queryForList("queryForPage", Res.class, query);
	}
	
	@Cache(value = { User.class, Org.class, Role.class, Res.class }, evict = true)
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Res saveOrUpdate(Res form) {
		return resService.saveOrUpdate(form);
	}
	
	@Cache(value = { User.class, Org.class, Role.class, Res.class }, evict = true)
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return resService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@Cache(value = Res.class, evict = true)
	@RequestMapping(mapping = { "/repair_node_path", "/m/repair_node_path" },text="修复节点路径")
	public void repairNodePath() {
		resService.repairNodePath();
	}
	
	@Cache(Res.class)
	@RequestMapping(mapping = { "/tree", "/m/tree" }, text = "权限管理")
	public Collection<SimpleNode> getTree() {
		List<SimpleNode> list = new ArrayList<>();
		
		ResQuery query2 = new ResQuery();
		query2.setType(Res.TYPE_页面元素);
		Collection<Res> buttonList = resService.queryForList(query2); // 获取所有菜单
		
		
		ResQuery query = new ResQuery();
		query.setType(Res.TYPE_菜单);
		Collection<Res> data = resService.queryForList(query); // 获取所有菜单
		
		if (ArrayUtil.isNotBlank(data)) {
			for (Res e : data) {
				SimpleNode node = new SimpleNode();
				node.id = e.getId();
				node.pid = e.getPid();
				node.name = e.getName();
				
				List<SimpleNode.Function> funs = new ArrayList<>();
				for(Res button : buttonList) {
					if(button.getPid().longValue() == e.getId()) {
						SimpleNode.Function btn = new SimpleNode.Function();
						btn.action = button.getCode();
						btn.name = button.getName();
						btn.checked = false;
						funs.add(btn);
					}
				}
				
				node.functions = funs;
				
				list.add(node);
			}
		}
		return list;
	}
	
	/**
	 * 用于权限管理
	 * @author mingmin.yuan
	 *
	 */
	public static class SimpleNode implements java.io.Serializable{
		
		private static final long serialVersionUID = -8092779930549804330L;
		
		public Long id;
		public Long pid;
		public String name;
		public List<Function> functions = new ArrayList<>();
		
		public static class Function implements java.io.Serializable{
			private static final long serialVersionUID = -7820277711652152458L;
			
			public String action ;
			public String name;
			public boolean checked ;
		}
	}
	
}
