package org.lsqt.act.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.NodeButtonQuery;
import org.lsqt.act.model.NodeForm;
import org.lsqt.act.model.NodeFormQuery;
import org.lsqt.act.service.NodeButtonService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.service.DictionaryService;

import com.alibaba.fastjson.JSON;




@Controller(mapping={"/act/node_button","/nv2/act/node_button"})
public class NodeButtonController {
	
	@Inject private NodeButtonService nodeButtonService; 
	@Inject private DictionaryService dictionaryService;
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public NodeButton getById(Long id) {
		return db.getById(NodeButton.class, id);
	}
	
	@RequestMapping(mapping = { "/delete_by_ids", "/m/delete_by_ids" })
	public void deleteByIds(String ids) {
		if(StringUtil.isNotBlank(ids)) {
			List<Long> idArray = StringUtil.split(Long.class,ids, ",");
			db.deleteById(NodeButton.class, idArray.toArray());
		}
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<NodeButton> queryForPage(NodeButtonQuery query) throws IOException {
		return nodeButtonService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<NodeButton> getAll() {
		return nodeButtonService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public NodeButton saveOrUpdate(NodeButton form) {
		return nodeButtonService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/save_or_update_json", "/m/save_or_update_json" })
	public List<NodeButton> saveOrUpdateJSON(String data) {
		List<NodeButton> list = new ArrayList<>();
		if (StringUtil.isNotBlank(data)) {
			list = JSON.parseArray(data, NodeButton.class);
			if(list!=null && list.size()>0) {
				for(NodeButton e: list) {
					db.saveOrUpdate(e);
				}
			}
		}
		return list;
	}
	
	@RequestMapping(mapping = { "/update_simple", "/m/update_simple" })
	public NodeButton updateSimple(NodeButton form) {
		form.setUpdateTime(new Date());
		if(StringUtil.isBlank(form.getAfterScriptType())){
			form.setAfterScriptType(NodeButton.SCRIPT_TYPE_URL);
		}
		return db.update(form, "beforeScript","afterScript","afterScriptType","updateTime","btnName","remark","btnCode");
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return nodeButtonService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public Collection<NodeButton> list(NodeButtonQuery query) {

		List<NodeButton> dbList = db.queryForList("queryForPage", NodeButton.class, query);
		for(NodeButton n: dbList) {
			n.setTaskName(query.getTaskName());
		}
		return dbList;
	}
	
	@RequestMapping(mapping = { "/init", "/m/init" },text="初使化表单里的审批按钮")
	public Collection<NodeButton> init(NodeButtonQuery query) {
		List<NodeButton> rs = new ArrayList<>();

		List<NodeButton> dbList = db.queryForList("queryForPage", NodeButton.class, query);

		db.executeUpdate("delete from ext_node_button where definition_id=? and task_key=?", query.getDefinitionId(),query.getTaskKey());
		if (dbList == null || dbList.size() == 0) { // 如果没有，加载系统字典里定义的
			List<Dictionary> list = dictionaryService.getOptionByCode("form_node_button_type", Application.APP_CODE_DEFAULT);
			if (list != null) {
				for (Dictionary d : list) {
					NodeButton nb = new NodeButton();
					nb.setDefinitionId(query.getDefinitionId());
					nb.setTaskKey(query.getTaskKey());
					nb.setDataType(query.getDataType());
					
					nb.setBtnName(d.getName());
					nb.setBtnCode(d.getCode());
					nb.setBtnType(Integer.valueOf(d.getValue()));
					nb.setTaskName(query.getTaskName());

					nb.setRemark(d.getRemark());
					
					db.save(nb);
					rs.add(nb);
				}
			}

			return rs;
		}

		for (NodeButton n : dbList) {
			n.setTaskName(query.getTaskName());
		}
		return dbList;
	}
	
	/**
	 * 初始化所有按钮
	 * @param query
	 * @return
	 */
	@RequestMapping(mapping = { "/init_all"},text="初使化表单里所有的审批按钮")
	public Result initAll(NodeFormQuery query) {
		System.out.println(query.getDefinitionId());
		
		List<Dictionary> list = dictionaryService.getOptionByCode("form_node_button_type", Application.APP_CODE_DEFAULT);

		List<NodeForm> dbList = db.queryForList("queryForPage", NodeForm.class, query);

		db.executeUpdate("delete from ext_node_button where definition_id=? and data_type=?", query.getDefinitionId(),NodeButton.DATA_TYPE_TASK_BUTTON);
		
		List<NodeButton> models = new ArrayList<>();
		for (NodeForm nodeForm : dbList) {
			
			if (list != null) {
				for (Dictionary d : list) {
					NodeButton nb = new NodeButton();
					nb.setDefinitionId(nodeForm.getDefinitionId());
					nb.setTaskKey(nodeForm.getTaskKey());
					nb.setDataType(nodeForm.getDataType());
					nb.setBtnName(d.getName());
					nb.setBtnCode(d.getCode());
					nb.setBtnType(Integer.parseInt(d.getValue()));
					nb.setTaskName(nodeForm.getTaskName());
					nb.setRemark(d.getRemark());
					//db.save(nb);
					models.add(nb);
				}
			}
			
		}
		if(!models.isEmpty()){
			db.batchSave(models);
		}

		return Result.ok();
	}
	
	@RequestMapping(mapping = { "/get_approve_action_list", "/m/get_approve_action_list" },text="")
	public Collection<NodeButton> getApproveActionList() {
		List<NodeButton> rs = new ArrayList<>();
	 
		List<Dictionary> list = dictionaryService.getOptionByCode("form_node_button_type", Application.APP_CODE_DEFAULT);
		if (list != null) {
			for (Dictionary d : list) {
				System.out.println(d.getCode());
				
				//去除"保存表单、保存草稿、发起"审批动作
				if(("button_type_"+NodeButton.BTN_TYPE_START).equals(d.getCode())){
					continue;
				}
				if(("button_type_"+NodeButton.BTN_TYPE_SAVE_DRAFT).equals(d.getCode())){
					continue;
				}
				if(("button_type_"+NodeButton.BTN_TYPE_SAVE_FORM).equals(d.getCode())){
					continue;
				}
				
				NodeButton nb = new NodeButton();
				nb.setBtnName(d.getName());
				nb.setBtnCode(d.getCode());
				nb.setBtnType(Integer.valueOf(d.getValue()));
				nb.setRemark(d.getRemark());
				
				rs.add(nb);
			}
		}
		return rs;
	}
}
