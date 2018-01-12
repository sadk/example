package org.lsqt.syswin.authority.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.IllegalFormatFlagsException;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.DbException;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.Plan;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.Function;
import org.lsqt.syswin.authority.model.FunctionQuery;
import org.lsqt.syswin.authority.model.Menu;
import org.lsqt.syswin.authority.model.MenuQuery;
import org.lsqt.syswin.authority.model.Node;
import org.lsqt.syswin.authority.model.PositionModuleConfig;
import org.lsqt.syswin.authority.model.PositionPermitResult;
import org.lsqt.syswin.authority.model.RoleModuleConfig;
import org.lsqt.syswin.authority.service.FunctionService;
import org.lsqt.syswin.authority.service.MenuService;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.PositionQuery;
import org.lsqt.syswin.uum.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller(mapping={"/syswin/function","/nv2/syswin/function"})
public class FunctionController {
	private static final Logger log = LoggerFactory.getLogger(FunctionController.class);
	@Inject private FunctionService functionService; 
	@Inject private MenuService menuService;
	@Inject private PositionService positionService;
	
	@Inject private PlatformDb db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Function> queryForPage(FunctionQuery query) throws IOException {
		return functionService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Function> getAll() {
		return functionService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Function saveOrUpdate(Function form) {
		return functionService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return functionService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	// ----------------------------------------------------------
	
	@RequestMapping(mapping = { "/add_menu_buttons", "/m/add_menu_buttons" },text="添加菜单下的按钮")
	public void addMenuButtons(Long menuId, String functionIds) {
		if(menuId == null || StringUtil.isBlank(functionIds)) {
			return ;
		}
		List<Long> list = StringUtil.split(Long.class, functionIds, ",");
		db.executePlan(new Plan() {
			@Override
			public void doExecutePlan() throws DbException {
				String sql = "delete from t_power_menu_func where menu_id=? and func_id in (%s)";
				sql = String.format(sql, StringUtil.join(list, ","));
				db.executeUpdate(sql, menuId);
				
				sql = "insert into t_power_menu_func (menu_id,func_id,create_date) values(?,?,?)";
				for(Long funcId : list) {
					db.executeUpdate(sql, menuId,funcId,new Date());
				}
			}
		});
	}
	
	@RequestMapping(mapping = { "/delete_menu_buttons", "/m/delete_menu_buttons" },text="删除菜单下的按钮")
	public void deleteMenuButtons(Long menuId, String functionIds) {
		if(menuId == null || StringUtil.isBlank(functionIds)) {
			return ;
		}
		
		List<Long> list = StringUtil.split(Long.class, functionIds, ",");

		String sql = "delete from t_power_menu_func where menu_id=? and func_id in (%s)";
		sql = String.format(sql, StringUtil.join(list, ","));
		db.executeUpdate(sql, menuId);

	}
	/*
	@RequestMapping(mapping = { "/get_function_list_by_role_id", "/m/get_function_list_by_role_id" },text="获取角色下的功能按钮")
	public List<Function> deleteMenuButtons(Long roleId) {
		if(roleId == null ) {
			return new ArrayList<>();
		}
		return functionService.getFunctionListByRoleID(roleId);
	}
	*/
	
	// ---------------------------------  以下是功能授权与展示的关键方法!!!!!!!! Begin: -------------------------------------
	@RequestMapping(mapping = { "/get_resource_tree", "/m/get_resource_tree" },text="获取系统资源树：菜单+功能按钮,不带权限")
	public List<Node> getResourceTree() {
		 List<Node> tree = new ArrayList<>();
		 // 获取所有菜单
		 Collection<Menu> menus = menuService.getAll();
		 for (Menu m: menus) {
			 Node node = new Node();
			 node.setDataType(Node.DATA_TYPE_MENU);
			 node.setId(m.getId().toString());
			 node.setPid(m.getPid()+"");
			 node.setName(m.getName());
			 node.setNodePath(m.getNodePath());
			 node.setUrl(m.getUrl());
			 node.setCode(m.getCode());
			 tree.add(node);
		 }
		 
		 //获取所有功能按钮定义
		 String sql = "select menu_func_id menuFuncId,A.func_id functionId,menu_id menuId ,B.func_name funcName ,B.func_code funcCode from t_power_menu_func A left join  t_power_res_func_info B on A.func_id=B.func_id";
		 List<Map<String,Object>> funcList = db.executeQuery(sql);
		 if(funcList!=null && !funcList.isEmpty()) {
			 List<Node> temp = new ArrayList<>();
			 for(Node n: tree) {
				 
				 for(Map<String,Object> row: funcList) {
					 if(n.getId().equals(row.get("menuId")+"")) {
						 Node nd = new Node();
						 nd.setDataType(Node.DATA_TPYE_FUNCTION);
						 nd.setPid(n.getId());
						 nd.setName(row.get("funcName")+"");
						 nd.setId("func_"+row.get("menuFuncId")); // 防止菜单ID和功能ID重合，加个"func_"前缀
						// nd.setExtProperty(row.get("menuFuncId")+"");
						 nd.setCode(row.get("funcCode")+"");
						 temp.add(nd);
						 
					 }
				 }
				 
				 // 添加数据查询权限固定结点：下级，所有
				 Node underMe = new Node();
				 underMe.setDataType(Node.DATA_TPYE_DATA_QUERY);
				 underMe.setPid(n.getId());
				 underMe.setName("直属下级");
				 underMe.setId("dataQueryUnderMe"+"_@@_"+n.getCode()); 
				 underMe.setCode("underMe");
				 temp.add(underMe);
				 
				 /*
				 Node all = new Node();
				 all.setDataType(Node.DATA_TPYE_DATA_QUERY);
				 all.setPid(n.getId());
				 all.setName("所有下级");
				 all.setId("dataQueryAll"+"_@@_"+n.getCode()); 
				 all.setCode("all");
				 temp.add(all);
				 */
				 
				 Node all1 = new Node();
				 all1.setDataType(Node.DATA_TPYE_DATA_QUERY);
				 all1.setPid(n.getId());
				 all1.setName("全部");
				 all1.setId("dataQueryRealAll"+"_@@_"+n.getCode()); 
				 all1.setCode("realAll");
				 temp.add(all1);
			 }
			tree.addAll(temp);
		 }
		 return tree;
	}
	
	@RequestMapping(mapping = { "/get_resource_tree_selected", "/m/get_resource_tree_selected" },text="获取角色拥有的系统权限树：菜单+功能按钮,带权限!!!")
	public List<Node> getResourceTreeSelected(Long roleId) {
		List<Node> tree = new ArrayList<>();
		// 获取角色下的菜单
		MenuQuery query = new MenuQuery();
		query.setRoleId(roleId);
		List<Menu> list = menuService.queryForList(query);
		if(list!=null && list.size()>0) {
			for(Menu m: list) {
				Node node = new Node();
				 node.setDataType(Node.DATA_TYPE_MENU);
				 node.setId(m.getId().toString());
				 node.setPid(m.getPid()+"");
				 node.setName(m.getName());
				 node.setNodePath(m.getNodePath());
				 node.setUrl(m.getUrl());
				 node.setCode(m.getCode());
				 tree.add(node);
			}
		}
		
		// 获取角色下的功能按钮 （“menuFuncId”）
		List<Function> funcList = functionService.getFunctionListByRoleID(roleId);
		if (funcList != null && funcList.size() > 0) {
			for (Function f : funcList) {
				Node node = new Node();
				node.setId("func_"+f.getId());
				node.setName(f.getName());
				node.setPid(f.getMenuId()+"");
				tree.add(node);
			}
		}
		
		// 获取角色模块权配置
		RoleModuleConfig filter = new RoleModuleConfig();
		filter.setRoleId(roleId);
		List<RoleModuleConfig> data = db.queryForList("queryForPage", RoleModuleConfig.class, filter);
		for (RoleModuleConfig n: data) {
			Node node = new Node();
			if(PositionPermitResult.lEVEL_NEXT == n.getLevel()) {
				node.setId("dataQueryUnderMe_@@_"+n.getModuleCode());
			}else if(PositionPermitResult.LEVEL_CHILD == n.getLevel()) {
				node.setId("dataQueryAll_@@_"+n.getModuleCode());
			}else if(PositionPermitResult.LEVEL_ALL == n.getLevel()) {
				node.setId("dataQueryRealAll_@@_"+n.getModuleCode());
			}
			node.setName("数据查询");
			node.setDataType(Node.DATA_TPYE_DATA_QUERY);
			for (Node e: tree) {
				if(Node.DATA_TYPE_MENU == e.getDataType() && e.getCode().equals(n.getModuleCode())) {
					node.setPid(n.getId()+"");
					break;
				}
			}
			tree.add(node);
		}
		return tree;
	}
	
	
	@RequestMapping(mapping = { "/save_function_and_menu_authority", "/m/save_function_and_menu_authority" },text="保存菜单和功能权限到角色!!!")
	public void saveFunctionAndMenuAuthority(Long roleId,String nodeIds) {
		if(roleId == null || StringUtil.isBlank(nodeIds)) {
			return ;
		}

		// 1.删除角色与菜单关系，重新添加
		String sql = "delete from t_power_role_menu_operation where role_id=?";
		db.executeUpdate(sql, roleId);
		
		// 2.删除角色与功能关系，重新添加
		String sql2="delete from t_power_role_menu_func_operation where role_id=?";
		db.executeUpdate(sql2,roleId);
		
		// 3.删除角色模块配置关系，重新添加
		String sql5="delete from t_power_role_module_config where role_id=?";
		db.executeUpdate(sql5,roleId);
		
		
		List<Object> funcArgs = new ArrayList<>(); // 功能权限批量插入参数
		List<String> items = new ArrayList<>(); // UI上的数据查询权限配置项
		List<String> ids= StringUtil.split(nodeIds, ",");
		//List<String> moduleIds = new ArrayList<>();
		
		List<Object> roleMenuArgs = new ArrayList<>();
		for(String id: ids) {
			if (id.startsWith("func_")) {
				
				funcArgs.add(roleId);
				funcArgs.add(id.replace("func_", ""));
				
			} else if (id.startsWith("dataQuery")) { // id = "数据查询标识_菜单编码"
				items.add(id);
				
			} else {
				
				roleMenuArgs.add(roleId);
				roleMenuArgs.add(id);
				
				//moduleIds.add(id);
			}
		}
		
		// 添加角色功能数据
		String batchSql01 = "insert into t_power_role_menu_func_operation (role_id,menu_func_id) values(?,?)";
		db.batchUpdate(batchSql01, funcArgs.toArray(new Object[funcArgs.size()]));
		
		
		// 添加角色模块配置表数据
		String batchSql = "insert into t_power_role_module_config (role_id,module_code,level) values(?,?,?)";
		List<Object> argsList = new ArrayList<>();
		for (String e : items) {
			String[] text = e.split("_@@_");
			if (text == null || text.length != 2) {
				throw new IllegalFormatFlagsException("不支持的ID格式：" + e);
			}
			
			if ("dataQueryUnderMe".equals(text[0])) {
				argsList.add(roleId);
				argsList.add(text[1]);
				argsList.add(PositionPermitResult.lEVEL_NEXT);
				 
			} else if ("dataQueryAll".equals(text[0])) {
				argsList.add(roleId);
				argsList.add(text[1]);
				argsList.add(PositionPermitResult.LEVEL_CHILD);
				
			} else if("dataQueryRealAll".equals(text[0])){
				argsList.add(roleId);
				argsList.add(text[1]);
				argsList.add(PositionPermitResult.LEVEL_ALL);
				
			} else {
				throw new IllegalFormatFlagsException("不支持的ID格式：" + e);
			}
		}
		db.batchUpdate(batchSql, argsList.toArray(new Object[argsList.size()]));
		
		

		
		
		// 添加【菜单权限】
		String batchSql03 = "insert into t_power_role_menu_operation (role_id,menu_id) values(?,?)";
		db.batchUpdate(batchSql03, roleMenuArgs.toArray(new Object[roleMenuArgs.size()]));
		
		
		
		
		// 保存岗位模块配置表
		PositionQuery query = new PositionQuery();
		query.setRoleId(roleId);
		List<Position> list = db.queryForList("queryForPage", Position.class, query);
		if(list!=null) {
			List<Long> idList = positionService.getPostionIds(list);
			db.executeUpdate(String.format("delete from t_power_duties_module_config where duties_id in (%s)",StringUtil.join(idList,",")));
		}
		
		List<PositionModuleConfig> pmcList = new ArrayList<>();
		for (Position p : list) {
			for (String e : items) {
				PositionModuleConfig model = new PositionModuleConfig();
				model.setPositionId(p.getId());
				String[] text = e.split("_@@_");
				if (text == null || text.length != 2) {
					throw new IllegalFormatFlagsException("不支持的ID格式：" + e);
				}

				if ("dataQueryUnderMe".equals(text[0])) {
					model.setLevel(PositionPermitResult.lEVEL_NEXT);
				} else if ("dataQueryAll".equals(text[0])) {
					model.setLevel(PositionPermitResult.LEVEL_CHILD);
				} else if("dataQueryRealAll".equals(text[0])){
					model.setLevel(PositionPermitResult.LEVEL_ALL);
				} else {
					throw new IllegalFormatFlagsException("不支持的ID格式：" + e);
				}
				model.setModuleCode(text[1]);

				/*
				MenuQuery q = new MenuQuery();
				q.setCode(model.getModuleCode());
				Menu m = db.queryForObject("queryForPage", Menu.class, q);
				if (m != null) {
					model.setModuleName(m.getName());
				}*/

				//db.save(model);
				pmcList.add(model);
			}
		}
		
		db.batchSave(pmcList);
		
		
		// 解析权限数据到结果表
		/*
		for (Position p : list) {
			positionService.saveResolveResult(p.getId());
		}

		// 最后，保存角色模块配置，只用于UI加载，不做任何业务意义
		saveRoleModuleConfigData(roleId,items);
		*/
	}


	private void saveRoleModuleConfigData(Long roleId,List<String> items){
		db.executeUpdate("delete from t_power_role_module_config where role_id=?", roleId);
		
		List<RoleModuleConfig> data = new ArrayList<>();
		for (String e:items) {
			RoleModuleConfig model = new RoleModuleConfig();
			model.setRoleId(roleId);
			
			String [] text = e.split("_@@_");
			if(text == null || text.length!=2) {
				throw new IllegalFormatFlagsException("不支持的ID格式："+e);
			}
			
			if ("dataQueryUnderMe".equals(text[0])) {
				model.setLevel(PositionPermitResult.lEVEL_NEXT );
			} else if("dataQueryAll".equals(text[0])) {
				model.setLevel(PositionPermitResult.LEVEL_CHILD);
			} else if("dataQueryRealAll".equals(text[0])) {
				model.setLevel(PositionPermitResult.LEVEL_ALL);
			}
			else {
				throw new IllegalFormatFlagsException("不支持的ID格式："+e);
			}
			
			model.setModuleCode(text[1]);
			
			data.add(model);
		}
		
		/*
		for(RoleModuleConfig e:data) {
			db.save(e);
		}*/
		db.batchSave(data);
	}
	 
	
	// ---------------------------------  以上是功能授权与展示的关键方法!!!!!!!! End: -------------------------------------
	
	
	
	@RequestMapping(mapping = { "/get_resource_tree_for_user", "/m/get_resource_tree_for_user" },text="获取用户拥有的系统权限树：菜单+功能按钮")
	public List<Node> getResourceTreeForUser(Long userId) {
		List<Node> tree = new ArrayList<>();
		//1.获取用户的菜单权限
		MenuQuery query = new MenuQuery();
		query.setUserId(userId);
		List<Menu> menus = db.queryForList("queryForPage",Menu.class,query);
		
		//2.获取用户的按钮功能权限
		List<Function> functions = functionService.getFunctionListByUserID(userId);
		
		if(menus!=null && menus.size()>0) {
			for(Menu m: menus) {
				Node node = new Node();
				 node.setDataType(Node.DATA_TYPE_MENU);
				 node.setId(m.getId().toString());
				 node.setPid(m.getPid()+"");
				 node.setName(m.getName());
				 node.setNodePath(m.getNodePath());
				 node.setUrl(m.getUrl());
				 node.setCode(m.getCode());
				 tree.add(node);
			}
		}
		
		// 3.将功能按钮“挂”到菜单上
		if (functions != null && functions.size() > 0) {
			for (Function f : functions) {
				Node node = new Node();
				node.setId("func_"+f.getId());
				node.setName(f.getName());
				node.setPid(f.getMenuId()+"");
				tree.add(node);
			}
		}
		return tree;
	}
	
}
