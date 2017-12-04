package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.NodeUser;
import org.lsqt.act.model.NodeUserQuery;
import org.lsqt.components.db.Page;

public interface NodeUserService {
	
	Page<NodeUser> queryForPage(NodeUserQuery query);
	
	List<NodeUser> queryForList(NodeUserQuery query);

	NodeUser saveOrUpdate(NodeUser model);

	void saveOrUpdate(String definitionId,String taskKey,List<NodeUser> models);
	
	int deleteById(Long... ids);
	
	Collection<NodeUser> getAll();
	

	
	/**
	 * 获取节点的审批对象
	 * @param loginUserId 
	 * @param definitionId
	 * @param taskDefKey
	 * @param variables （流程或扩展的）变量数据
	 * @return
	 */
	List<ApproveObject> getNodeUsers(Long loginUserId,String definitionId,String taskDefKey,Map<String,Object> variables);
	
	/**
	 * 获取每个节点的审批用户
	 * @param loginUserId
	 * @param definitionId
	 * @param variables （流程或扩展的）变量数据
	 * @return Map key=流程节点定义,value=审批用户
	 */ 
	Map<String, List<ApproveObject>> getNodeUsers(Long loginUserId, String definitionId,Map<String,Object> variables);
	
}
