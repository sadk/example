package org.lsqt.act.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.UserRuleMatrixFlow;
import org.lsqt.act.model.UserRuleMatrixFlowQuery;
import org.lsqt.act.service.UserRuleMatrixFlowService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;




@Controller(mapping={"/act/user_rule_matrix_flow"})  
public class UserRuleMatrixFlowController {
	
	@Inject private UserRuleMatrixFlowService userRuleMatrixFlowService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<UserRuleMatrixFlow> queryForPage(UserRuleMatrixFlowQuery query) throws IOException {
		return userRuleMatrixFlowService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<UserRuleMatrixFlow> getAll() {
		return userRuleMatrixFlowService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public UserRuleMatrixFlow saveOrUpdate(UserRuleMatrixFlow form) {
		return userRuleMatrixFlowService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return userRuleMatrixFlowService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	
	@RequestMapping(mapping = { "/save_matrix_flow_setting", "/m/save_matrix_flow_setting"},text="设置流程节点审批用户与矩阵用户规则")
	public void saveMatrixFlowSetting(UserRuleMatrixFlow form) throws IOException {
		if(form.getUserRuleId()!=null && StringUtil.isNotBlank(form.getDefinitionId(),form.getTaskKey())) {
			db.executeUpdate("delete from ext_user_rule_matrix_flow where user_rule_id=? and definition_id=? and task_key=?", form.getUserRuleId(),form.getDefinitionId(),form.getTaskKey());
			db.save(form);
		}
	}
	
}
