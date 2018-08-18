package org.lsqt.act.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.NodeButtonQuery;
import org.lsqt.act.model.NodeForm;
import org.lsqt.act.model.NodeFormQuery;
import org.lsqt.act.model.NodeQuery;
import org.lsqt.act.service.NodeButtonService;
import org.lsqt.act.service.NodeFormService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;




@Controller(mapping={"/act/node_form","/nv2/act/node_form"})
public class NodeFormController {
	
	public static class NodeFormWapper extends NodeForm{
		public String buttonListDesc ;

		public String getButtonListDesc() {
			return buttonListDesc;
		}

		public void setButtonListDesc(String buttonListDesc) {
			this.buttonListDesc = buttonListDesc;
		}
	}
	
	@Inject private NodeButtonService nodeButtonService;
	@Inject private NodeFormService nodeFormService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<NodeForm> queryForList(NodeFormQuery query)  {
		List<NodeForm> list = nodeFormService.queryForList(query);
		
		if(list!=null) { 
			loadNodeType(query, list); //节点类型
			list = loadButtons(list);// 加载节点表单的操作按钮
		}
		return list;
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<NodeForm> queryForPage(NodeFormQuery query)  {
		Page<NodeForm> page = nodeFormService.queryForPage(query);
		
		if(page!=null && page.getData()!=null) { 
			loadNodeType(query, page.getData()); //节点类型
			page.setData(loadButtons(page.getData()));// 加载节点表单的操作按钮
		}
		
		return page;
	}

	private List<NodeForm> loadButtons(Collection<NodeForm> sourceData) {
		List<NodeForm> wList = new ArrayList<>();
		for(NodeForm n: sourceData) {
			NodeButtonQuery filter = new NodeButtonQuery();
			filter.setDefinitionId(n.getDefinitionId());
			filter.setTaskKey(n.getTaskKey());
			
			StringBuilder buttonListDesc = new StringBuilder();
			List<NodeButton> tempList = nodeButtonService.queryForList(filter);
			for (NodeButton f: tempList) {
				buttonListDesc.append(f.getBtnName()+",");
			}
			
			NodeFormWapper w = new NodeFormWapper();
			BeanUtils.copyProperties(n, w);
			w.setButtonListDesc(buttonListDesc.toString());
			wList.add(w);
		}
		
		return wList;
	}

	private void loadNodeType(NodeFormQuery query, Collection<NodeForm> data) {
		//节点类型
		NodeQuery nquery = new NodeQuery();
		nquery.setDefinitionId(query.getDefinitionId());
		List<Node> nodeList = db.queryForList("queryForPage", Node.class, nquery);
		if (nodeList != null) {
			for (NodeForm nf : data) {
				for (Node e : nodeList) {
					if (e.getTaskKey().equals(nf.getTaskKey())) {
						nf.setTaskType(e.getTaskBizType());
						nf.setTaskTypeDesc(e.getTaskBizTypeDesc());
						break;
					}
				}
			}
		}
	}
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public NodeForm getById(Long id)  {
		return db.getById(NodeForm.class, id);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<NodeForm> getAll() {
		return nodeFormService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public NodeForm saveOrUpdate(NodeForm form) {
		return nodeFormService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/save_or_update_json", "/m/save_or_update_json" })
	public void saveOrUpdate(String data) {
		if(StringUtil.isNotBlank(data)) {
			List<NodeForm> list = JSON.parseArray(data, NodeForm.class);
			for(NodeForm f: list) {
				db.saveOrUpdate(f);
			}
		}
	}
	
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return nodeFormService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/import_node", "/m/import_node" },text="手工导入节点表单")
	public Collection<NodeForm> importNode(String definitionId) {
		return nodeFormService.importNode(definitionId);
	}
	
	@RequestMapping(mapping = { "/import_node_auto", "/m/import_node_auto" },text="如果没有节点表单，自动添加")
	public Collection<NodeForm> importNodeAuto(NodeFormQuery query) {
		Collection<NodeForm> rs = new ArrayList<>();
		
		if(StringUtil.isNotBlank(query.getDefinitionId())) {
			List<NodeForm> list = db.queryForList("queryForPage", NodeForm.class, query);
			if(ArrayUtil.isBlank(list)) {
				return nodeFormService.importNode(query.getDefinitionId());
			}
			return list;
		}
		
		return rs;
	}
}
