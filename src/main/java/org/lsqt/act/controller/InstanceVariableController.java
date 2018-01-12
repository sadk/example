package org.lsqt.act.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lsqt.act.model.InstanceVariable;
import org.lsqt.act.model.InstanceVariableQuery;
import org.lsqt.act.service.InstanceVariableService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;

import com.alibaba.fastjson.JSON;




@Controller(mapping={"/instance_variable","/nv2/instance_variable"})
public class InstanceVariableController {
	
	@Inject private InstanceVariableService instanceVariableService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<InstanceVariable> queryForPage(InstanceVariableQuery query) throws IOException {
		return instanceVariableService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<InstanceVariable> getAll() {
		return instanceVariableService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public InstanceVariable saveOrUpdate(InstanceVariable form) {
		return instanceVariableService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return instanceVariableService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/view_variable", "/m/view_variable" },text="查看流程图发起变量和聚合变量")
	public InstanceVariable viewVariable(String instanceId) {
		if (StringUtil.isNotBlank(instanceId)) {
			InstanceVariableQuery query = new InstanceVariableQuery();
			query.setInstanceId(instanceId);
			InstanceVariable model = db.queryForObject("queryForPage", InstanceVariable.class, query);
			if (model != null) {
				if(StringUtil.isNotBlank(model.getVariableJSONStart())) {
					model.setVariableJSONStart(formatJson(model.getVariableJSONStart()));
					model.setVariableJson(formatJson(model.getVariableJson()));
				}
			}
			return model;
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	private static final String formatJson(String json) {
		if(StringUtil.isNotBlank(json)) {
			Map map = JSON.parseObject(json, Map.class);
			return JSON.toJSONString(map, true);
		}
		return json;
	}
}
