package org.lsqt.act.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.NodeButtonQuery;
import org.lsqt.act.model.NodeForm;
import org.lsqt.act.model.NodeFormQuery;
import org.lsqt.act.service.NodeButtonService;
import org.lsqt.act.service.NodeFormService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
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
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<NodeForm> queryForPage(NodeFormQuery query) throws IOException {
		Page<NodeForm> page = nodeFormService.queryForPage(query);
		
		// 加载节点表单的操作按钮
		if(page!=null && page.getData()!=null) {
			List<NodeForm> wList = new ArrayList<>();
			for(NodeForm n: page.getData()) {
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
			
			page.setData(wList);
		}
		return page;
	}
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public NodeForm getById(Long id) throws IOException {
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
	
	@RequestMapping(mapping = { "/import_node", "/m/import_node" })
	public Collection<NodeForm> importNode(String definitionId) {
		return nodeFormService.importNode(definitionId);
	}
}
