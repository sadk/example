package org.lsqt.syswin.uum.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lsqt.act.controller.Result;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.Org;
import org.lsqt.syswin.uum.model.OrgQuery;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.PositionDataConfig;
import org.lsqt.syswin.uum.model.PositionDataConfigQuery;
import org.lsqt.syswin.uum.model.PositionQuery;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.model.UserQuery;
import org.lsqt.syswin.uum.service.PositionService;

import com.alibaba.fastjson.JSON;




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

	// ----------------------------------------岗位数据查询权限关键 操作，开始-------------------------------------
	
	@RequestMapping(mapping = { "/build_checkbox_tree", "/m/build_checkbox_tree" },text="构建岗位树")
	public List<Node> tree() throws IOException {
		List<Node> list = new ArrayList<>();
		
		List<Position> data2 = db.queryForList("queryForPage", Position.class);
		
		OrgQuery query = new OrgQuery();
		query.setSortOrder("asc");
		query.setSortField("order_no");
		
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
						n.text = p.getName();//+"(岗)";
						n.type="position";
						n.originalId=p.getId()+"";
						list.add(n);
					}
				}
			}
		}
		
		return list;
	}
	
	@RequestMapping(mapping = { "/save_position_dataquery_useness_permit", 
			"/m/save_position_dataquery_useness_permit" },text="保存一个或多个岗位的数据查询权限和使用权限")
	public void savePositionDataQueryUsenessPermit(String positionIds,String itemsJson) {
		if (StringUtil.isNotBlank(positionIds)) {
			Set<DataQueryUsernessRecord> set = new HashSet<>();
			
			List<Long> positionIdList = StringUtil.split(Long.class,positionIds, ",");
			for(Long positionId: positionIdList){
				db.executeUpdate("delete from t_power_duties_config where duties_id = ?", positionId);
				db.executeUpdate("delete from t_power_duties_data_query_permit where operation_duties_id = ?", positionId);
				
				if (StringUtil.isNotBlank(itemsJson)) {
					List<Item> items = JSON.parseArray(itemsJson, Item.class);
					
					// 保存UI钩选的权限项到配置表
					String sql = "insert into t_power_duties_config(duties_id,col_type,org_id,org_pid,org_type,org_name) values(?,?,?,?,?,?)";
					for(Item e: items){
						db.executeUpdate(sql, positionId,e.colType,e.id,e.pid,e.type,e.name);
					}
					
					
					// 解析到《岗位数据 "查询和使用" 权限表》
					for (Item e : items) {
						if (Item.COL_TYPE_MYSELF.equals(e.colType)) {
							DataQueryUsernessRecord r = new DataQueryUsernessRecord();
							r.operationPosId = positionId;
							r.orgId = e.id;
							r.positionId = positionId;
							r.type = DataQueryUsernessRecord.TYPE_QUERY;
							set.add(r);
						}
						
						else if(Item.COL_TYPE_UNDERME.equals(e.colType)) {
							PositionQuery pq = new PositionQuery();
							pq.setPid(positionId);
							List<Position> list = db.queryForList("queryForPage", Position.class, pq); // 获取当前岗位的直接下级岗(一层)

							Position temp = db.getById(Position.class, positionId);
							if(temp!=null) {
								list.add(temp);// 再添加当前岗的记录
							}
							
							if (list != null && list.size() > 0) {
								for (Position p : list) {
									DataQueryUsernessRecord r = new DataQueryUsernessRecord();
									r.operationPosId = positionId;
									r.orgId = e.id;
									r.positionId = p.getId();
									r.type = DataQueryUsernessRecord.TYPE_QUERY;
									set.add(r);
								}
							}
						}
						
						else if(Item.COL_TYPE_ALL.equals(e.colType)) {
							Position pos = db.getById(Position.class, positionId);
							if (pos!=null && StringUtil.isBlank(pos.getNodePath())) {
								throw new RuntimeException("岗位ID为%s的岗位，节点路径为空");
							}
							if (pos != null) {
								PositionQuery pq = new PositionQuery();
								pq.setNodePath(pos.getNodePath() + "%");
								List<Position> pList = db.queryForList("queryForPage", Position.class, pq);
								if (pList != null && pList.size() > 0) {
									for (Position p : pList) {
										DataQueryUsernessRecord r = new DataQueryUsernessRecord();
										r.operationPosId = positionId;
										r.orgId = e.id;
										r.positionId = p.getId();
										r.type = DataQueryUsernessRecord.TYPE_QUERY;
										set.add(r);
									}
								}
							}
						}
						
						else if(Item.COL_TYPE_USENESS.equals(e.colType)){
							DataQueryUsernessRecord r = new DataQueryUsernessRecord();
							r.operationPosId = positionId;
							r.orgId = e.id;
							r.positionId = positionId;
							r.type = DataQueryUsernessRecord.TYPE_USENESS;
							set.add(r);
						}
						
					}
				}
			}
			
			
			for(DataQueryUsernessRecord e:set){
				//db.executeUpdate("delete from t_power_duties_data_query_permit where duties_id=? and type=?", e.positionId,e.type);
				db.executeUpdate("insert into t_power_duties_data_query_permit(operation_duties_id,duties_id,org_id,type) values(?,?,?,?)",e.operationPosId, e.positionId,e.orgId,e.type);
			}
		}
	}
	
	@RequestMapping(mapping = { "/get_dataquery_and_useness_config", "/m/get_dataquery_and_useness_config" },text="获取岗位已设置的权限")
	public List<PositionDataConfig> getDataqueryAndUsenessConfig(Long positionId) {
		PositionDataConfigQuery query = new PositionDataConfigQuery();
		query.setPositionId(positionId);
		List<PositionDataConfig> list = db.queryForList("queryForPage", PositionDataConfig.class, query);
		return list;
	}
	
	@RequestMapping(mapping = { "/refer_dataquery_and_useness_config", "/m/refer_dataquery_and_useness_config" },text="引用已存在的岗位权限")
	public void referDataqueryAndUsenessConfig(String positionIds,Long targetPositionId) {
		if(StringUtil.isBlank(positionIds) || targetPositionId == null) {
			return ;
		}
		
		PositionDataConfigQuery query = new PositionDataConfigQuery();
		query.setPositionId(targetPositionId);
		List<PositionDataConfig> list = db.queryForList("queryForPage", PositionDataConfig.class, query);
		
		List<Long> ids = StringUtil.split(Long.class, positionIds, ",");
		for(Long positionId: ids){
			db.executeUpdate("delete from t_power_duties_config where duties_id =?", positionId);
			
			for(PositionDataConfig e: list) {
				e.setId(null);
				e.setPositionId(positionId);
				db.save(e);
			}
		}
	}
	
	/*
	@RequestMapping(mapping = { "/get_position_data_query",  "/m/get_position_data_query" },text="获取岗位的数据查询权限,返回组织ID")
	public void getPositionDataQuery(String positionIds) {
		Set<DataQueryUsernessRecord> set = new HashSet<>();
		
		List<Long> posIds = StringUtil.split(Long.class, positionIds, ",");
		for (Long positionId: posIds) {
			PositionDataConfigQuery query = new PositionDataConfigQuery();
			query.setPositionId(positionId);
			List<PositionDataConfig> data = db.queryForList("queryForPage", PositionDataConfig.class, query); // 找到其中一个岗位的权限配置
			
			for (PositionDataConfig e : data) {
				if (PositionDataConfig.COL_TYPE_MYSELF.equals(e.getColType())) {
					DataQueryUsernessRecord r = new DataQueryUsernessRecord();
					r.orgId = e.getOrgId();
					r.positionId = positionId;
					r.type = DataQueryUsernessRecord.TYPE_QUERY;
					set.add(r);
					
				} else if (PositionDataConfig.COL_TYPE_UNDERME.equals(e.getColType())) {
					PositionQuery pq = new PositionQuery();
					pq.setPid(positionId);
					List<Position> list = db.queryForList("queryForPage", Position.class, pq); // 获取当前岗位的直接下级岗(一层)

					list.add(db.getById(Position.class, positionId));// 再添加当前岗的记录 3328\3359

					if (list != null && list.size() > 0) {
						for (Position p : list) {
							DataQueryUsernessRecord r = new DataQueryUsernessRecord();
							//r.orgId = e.id;
							r.positionId = p.getId();
							r.type = DataQueryUsernessRecord.TYPE_QUERY;
							set.add(r);
						}
					}
				}
			}
		}
		
	}
	
	@RequestMapping(mapping = { "/get_position_data_useness",  "/m/get_position_data_useness" },text="获取岗位的数据使用权限,返回组织ID")
	public List<String> getPositionDataUseness(String positionIds) {
		List<String> list = new ArrayList<>();
		return list;
	}
	*/
	
	public static class DataQueryUsernessRecord{
		public Long operationPosId; // 操作的岗位ID
		
		public Long positionId;
		public Long orgId;
		public Integer type;//1=数据查询权限 2=使用权限
		public static final int TYPE_QUERY=1;
		public static final int TYPE_USENESS=2;
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
			result = prime * result + ((positionId == null) ? 0 : positionId.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DataQueryUsernessRecord other = (DataQueryUsernessRecord) obj;
			if (orgId == null) {
				if (other.orgId != null)
					return false;
			} else if (!orgId.equals(other.orgId))
				return false;
			if (positionId == null) {
				if (other.positionId != null)
					return false;
			} else if (!positionId.equals(other.positionId))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}
	}
	
	/**
	 * 用户钩选的权限项checkbox封装
	 * @author admin
	 *
	 */
	public static class Item {
		public Long id;
		public Long pid;
		public String type; // 组织类型
		public String name;
		
		public String colType; //授权类型:mySelf=本人 underMe=直属下级 all=全部
		
		public static final String COL_TYPE_MYSELF="mySelf";
		public static final String COL_TYPE_UNDERME="underMe";
		public static final String COL_TYPE_ALL="all";
		public static final String COL_TYPE_USENESS="useness";

	}
	
	/**
	 * 岗位树节点对象
	 * @author admin
	 *
	 */
	public static class Node {
		public String id;
		public String pid;
		public String text;
		public Boolean checked= false;
		public String type; // 节点类型 type：组织="org" 岗位="position"
		public String originalId; // 原始数据ID
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
	// ----------------------------------------岗位数据查询权限关键 操作，结束-------------------------------------------
	
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
}
