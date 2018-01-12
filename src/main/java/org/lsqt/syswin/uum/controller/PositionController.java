package org.lsqt.syswin.uum.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.lsqt.act.controller.Result;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.Org;
import org.lsqt.syswin.uum.model.OrgQuery;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.PositionQuery;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.service.PositionService;


@Controller(mapping={"/syswin/position"})
public class PositionController {
	
	@Inject private PositionService positionService; 
	@Inject private PlatformDb db;
	
	@RequestMapping(mapping = { "/repair_node_path", "/m/repair_node_path" },text="修复节点路径")
	public void repairNodePath(String type) {
		if("all".equals(type)){
			positionService.repairNodePath();
		}
		
		if("emptyPath".equals(type)) {
			positionService.repairNodePathForEmptyPath();
		}
		
		if("emptyPid".equals(type)) {
			positionService.repairNodePathForEmptyPid();
		}
	}
	
	/**
	 * 岗位树节点对象
	 * @author admin
	 */
	public static class Node {
		public String id;
		public String pid;
		public String text;
		public Boolean checked= false;
		public String type; // 节点类型 type：组织="org" 岗位="position"
		public String originalId; // 原始数据ID
		
		public String img;// 节点图标
		
		public String getTypeDesc(){
			if("org".equals(type)){
				return "组织";
			}
			if("position".equals(type)) {
				return "岗位";
			}
			return "";
		}
	}
	public static final String POSITION_IMG = "/scripts/miniui/res/images/user_b.png"; // 岗位树的图标,用人头显示
	
	@RequestMapping(mapping = { "/build_checkbox_tree", "/m/build_checkbox_tree" },text="构建岗位树")
	public List<Node> tree() throws IOException {
		List<Node> list = new ArrayList<>();
		
		List<Position> data2 = db.queryForList("queryForPage", Position.class);
		
		OrgQuery query = new OrgQuery();
		query.setSortOrder("asc");
		query.setSortField("order_no");
		
		HttpServletRequest request = ContextUtil.getRequest();
		List<Org> data = db.queryForList("queryForPage", Org.class);
		if (data != null && data.size() > 0) {
			for (Org o: data) {
				Node node = new Node();
				node.id = "org_"+o.getId();
				node.pid = "org_"+o.getPid();
				node.text = o.getName();//+"(部)";
				node.type="org";
				node.originalId=o.getId()+"";
				list.add(node);
				
				// 挂载岗位到组织结点下
				for (Position p : data2) {
					if (p.getOrgId()!=null && o.getId().longValue() == p.getOrgId()) {
						Node n = new Node();
						n.id = "pos_"+p.getId();
						n.pid="org_"+p.getOrgId();
						n.text = p.getName()+"("+p.getId()+")" ;//+"(岗)"; icon-user
						n.type="position";
						n.originalId=p.getId()+"";
						n.img =  request.getContextPath() + POSITION_IMG;
						list.add(n);
					}
				}
			}
		}
		
		return list;
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Position> queryForPage(PositionQuery query) throws IOException {
		Page<Position> page = positionService.queryForPage(query);
		processOrgNodeText(page.getData());
		processMainPositionDescByUserId(page.getData(),query.getUserId());
		return page;
	}
	
	private void processMainPositionDescByUserId(Collection<Position> data, Long userId) {
		if(data == null || data.isEmpty()) return ;
		if(userId == null) return ;
		
		PositionQuery query=new PositionQuery();
		query.setUserId(userId);
		List<Position> list = db.queryForList("getMainPositionDescByUserId", Position.class, query);
		if(list == null || list.isEmpty()) return ;
		
		for(Position p: data) {
			for(Position e: list) {
				if(p.getId().longValue() == e.getId()) {
					p.setMainPositionDesc(e.getMainPositionDesc());
					break;
				}
			}
		}
	}
	
	void processOrgNodeText(Collection<Position> data) {
		if (data != null) {

			for (Position p : data) {
				
				if (p.getOrgId() != null) {
					StringBuilder orgNodeText = new StringBuilder();

					Org org = db.getById(Org.class, p.getOrgId());
					if(org == null) {
						continue;
					}
					
					OrgQuery q = new OrgQuery();
					q.setIds(StringUtil.join( StringUtil.split(org.getNodePath(),","),","));
					q.setSortOrder("asc");
					q.setSortField("org_node_path");
					
					List<Org> temp = db.queryForList("queryForPage", Org.class, q);
					
					if (temp != null) {
						for (int i = 0; i < temp.size(); i++) {
							orgNodeText.append(temp.get(i).getName());
							if (i != temp.size() - 1) {
								orgNodeText.append("--");
							}
						}
					}
					p.setOrgNodeText(orgNodeText.toString());
				}
			}

		}
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Position> getAll() {
		return positionService.getAll();
	}
	
	@RequestMapping(mapping = { "/all_pid_not_null", "/m/all_pid_not_null" },text="获取岗位上下级关系视图数据,没有上下级的岗位将不显示")
	public List<Position> getAllPidNotNull() {
		PositionQuery query = new PositionQuery();
		query.setPidNotNull(true);
		
		List<Position> data= db.queryForList("queryForPage", Position.class, query);
		processOrgNodeText(data);
		return data;
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public Collection<Position> getForList(PositionQuery query) {
		Collection<Position> data = positionService.queryForList(query);
		processOrgNodeText(data);
		return data;
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Position saveOrUpdate(Position form) {
		return positionService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public Integer delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return positionService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/add_position_users", "/m/add_position_users" },text="给某个岗位添加用户")
	public Integer addUsers(Long positionId,String userIds) {
		Integer rs = 0;
		if (positionId != null && StringUtil.isNotBlank(userIds)) {
			
			List<String> list = StringUtil.split(userIds, ",");
			for (String userId : list) {
				User user = db.getById(User.class, userId);
				Long cnt = db.executeQueryForObject("select count(1) from t_user_duties where duties_id=? and user_id=?",Long.class, positionId, Long.valueOf(userId));
				if(cnt==0) {
					db.executeUpdate("insert into t_user_duties (duties_id,user_id,user_name) values(?,?,?)", positionId,userId,(user!=null ? user.getUserName():"") );
					rs ++ ;
				}
			}
		}

		return rs;
	}
	
	@RequestMapping(mapping = { "/remove_position_users", "/m/remove_position_users" },text="删除某个岗位下的用户")
	public Integer removeUsers(Long positionId,String userIds) {
		Integer rs = 0;
		if (positionId != null && StringUtil.isNotBlank(userIds)) {
			
			List<String> list = StringUtil.split(userIds, ",");
			for (String userId : list) {
				db.executeUpdate("delete from t_user_duties where duties_id=? and user_id=?", positionId, Long.valueOf(userId));
				rs++ ;
			}
		}

		return rs;
	}
	
	@RequestMapping(mapping = { "/set_user_main_positions", "/m/set_user_main_positions" },text="设定用户的某个岗位为主岗")
	public void setUserMainPositions(Long userId,Long positionId) {
		User user= db.getById(User.class, userId);
		if(user!=null) {
			db.executeUpdate("update t_user_duties set duties_type=?,user_name=? where user_id=?", Position.POSTION_SECOND,user.getUserName(),userId);
			db.executeUpdate("update t_user_duties set duties_type=?,user_name=? where user_id=? and duties_id=?", Position.POSITION_MAIN,user.getUserName(),userId,positionId);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/fix_one_main_position", "/m/fix_one_main_position" },text="处理所有用户只能有一个主岗")
	public Result fixOneMainPosition() {
		List<Map> list = db.queryForList(Position.class.getName(), "getTooManyMainPositionUsersForMap", Map.class);
		for (Map e : list) {
			db.executeUpdate("update t_user_duties set duties_type=? where user_id = ? ", Position.POSTION_SECOND,e.get("user_id"));
			db.executeUpdate("update t_user_duties set duties_type=? where pk = ? ", Position.POSITION_MAIN,e.get("pk"));
		}
		return Result.ok("修复成功!");

	}
	
	@RequestMapping(mapping = { "/position_node_list", "/m/position_node_list" },text="岗位授权-->引用岗位用展示")
	public List<Node> queryForNodePage(PositionQuery query) throws IOException {
		List<Position> list = positionService.queryForList(query);
		//processOrgNodeText(list);
		//processMainPositionDescByUserId(list,query.getUserId());
		
		HttpServletRequest request = ContextUtil.getRequest();
		
		List<Node> rs = new ArrayList<>();
		for(Position p: list) {
			Node n = new Node();
			n.id = "pos_"+p.getId();
			n.pid="org_"+ (StringUtil.isNotBlank(query.getKey()) ? "0": p.getName()) ;//+ p.getOrgId();
			n.text = p.getName();//+"(岗)"; icon-user
			n.type="position";
			n.originalId=p.getId()+"";
			n.img =  request.getContextPath() + POSITION_IMG;
			rs.add(n);
		}
		 
		return rs;
	}
	
}
 