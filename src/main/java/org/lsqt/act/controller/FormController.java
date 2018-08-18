package org.lsqt.act.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.lsqt.act.controller.DefinitionController.SimpleNode;
import org.lsqt.act.model.Category;
import org.lsqt.act.model.CategoryQuery;
import org.lsqt.act.model.Definition;
import org.lsqt.act.model.Form;
import org.lsqt.act.model.FormQuery;
import org.lsqt.act.service.CategoryService;
import org.lsqt.act.service.FormService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;




@Controller(mapping={"/act/form"})
public class FormController {
	
	@Inject private FormService formService; 
	@Inject private CategoryService categroyService; 
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Form> queryForPage(FormQuery query) throws IOException {
		return formService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Form> getAll() {
		return formService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Form saveOrUpdate(Form form) {
		return formService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return formService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/form_tree", "/m/form_tree" },text="流程设计器，条件选择器用，构建表单树")
	public List<SimpleNode> formTree() {
		List<SimpleNode> list = new ArrayList<>();
		
		CategoryQuery query = new CategoryQuery();
		query.setDataType(Category.DATA_TYPE_FLOW);
		List<Category> data = categroyService.queryForList(query);
		if(ArrayUtil.isNotBlank(data)) {
			for(Category e: data) {
				SimpleNode node = new SimpleNode();
				node.id = e.getId()+"";
				node.pid = e.getPid()+"";
				node.name = e.getName();
				node.code = e.getCode();
				node.dataType = "1";
				list.add(node);
			}
		}
		
		
		List<Form> formList = db.queryForList("getAll", Form.class);
		if (ArrayUtil.isNotBlank(formList)) {
			List<SimpleNode> formNodeList = new ArrayList<>();
			
			for (SimpleNode e : list) {
				for(Form form: formList) {
					if(e.code.equals(form.getCategory())) {
						SimpleNode subNode = new SimpleNode();
						subNode.code = "form_code_"+form.getCode();
						subNode.id = "form_id_"+form.getId();
						subNode.pid = e.id;
						subNode.dataType = "3";
						subNode.name = "表单 - " + form.getName();
						subNode.shortName = form.getName();
						formNodeList.add(subNode);
					}
				}
			}
			list.addAll(formNodeList);
		}
		return list;
	}
	
}
