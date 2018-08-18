package org.lsqt.act.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.act.ActUtil;
import org.lsqt.act.model.FormBaoXiao;
import org.lsqt.act.model.FormBaoXiaoQuery;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.service.FormBaoXiaoService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Controller(mapping={"/form_bao_xiao"})
public class FormBaoXiaoController {
	private static final Logger log = LoggerFactory.getLogger(FormBaoXiaoController.class);
	
	@Inject private FormBaoXiaoService formBaoXiaoService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<FormBaoXiao> queryForPage(FormBaoXiaoQuery query) throws IOException {
		return formBaoXiaoService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<FormBaoXiao> getAll() {
		return formBaoXiaoService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public FormBaoXiao saveOrUpdate(FormBaoXiao form) {
		return formBaoXiaoService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return formBaoXiaoService.deleteById(list.toArray(new Long[list.size()]));
	}
	 
	/**
	 * 
	 * @param token 安合token 
	 * @param businessKey 业务主键
	 * @param nextTaskCandidateUserIds 流程下一步审批人
	 * @param action 审批动作编码
	 * @param actionRejectType 审批动作编码类型
	 * @param isInstanceEnded 流程是否已通过（结束）
	 * @return
	 */
	@RequestMapping(mapping = { "/update_ext_status", "/m/update_ext_status" }, text = "更改业务表业务状态")
	public FormBaoXiao updateExtStatus(String token, String businessKey, String nextTaskCandidateUserIds,
			String action, String actionRejectType,Boolean isInstanceEnded) {
		
		FormBaoXiao form = db.getById(FormBaoXiao.class, businessKey);
		if (form == null) {
			log.error(String.format("没有找到ID=%s的对象", businessKey));
			return null;
		}

		if (NodeButton.BTN_TYPE_AGREE.equals(action) || NodeButton.BTN_TYPE_RESUBMIT.equals(action)) { // 同意或（打回到拟稿人）再重新提交
			
			form.setStatus(ActUtil.BUSINESS_STATUS_审批中);
			form.setStatusDesc("审批中");
			
			if(isInstanceEnded!=null && isInstanceEnded) {
				form.setStatus(ActUtil.BUSINESS_STATUS_已通过);
				form.setStatusDesc("已通过");
			}

			form.setCurrApproveUser(nextTaskCandidateUserIds);
			db.update(form, "status", "statusDesc", "currApproveUser");
			return form;
		}
		
		if (NodeButton.BTN_TYPE_REJECT_TO_STARTER.equals(action)) { // 不同意
			form.setStatus(ActUtil.BUSINESS_STATUS_已退回);
			form.setStatusDesc("已退回");
			form.setCurrApproveUser(nextTaskCandidateUserIds);
			db.update(form, "status", "statusDesc", "currApproveUser");
			return form;
		}
		
		if (NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(action) 
				&& String.valueOf(Node.TASK_BIZ_TYPE_DRAFTNODE).equals(actionRejectType)) { // 驳回到拟稿节点
			form.setStatus(ActUtil.BUSINESS_STATUS_已退回);
			form.setStatusDesc("已退回");
			form.setCurrApproveUser(nextTaskCandidateUserIds);
			db.update(form, "status", "statusDesc", "currApproveUser");
			return form;
		}
		
		
		if (NodeButton.BTN_TYPE_ABORT.equals(action)) { // 拟稿人主动作废
			form.setStatus(ActUtil.BUSINESS_STATUS_已作废);
			form.setStatusDesc("已作废");
			form.setCurrApproveUser(nextTaskCandidateUserIds);
			db.update(form, "status", "statusDesc", "currApproveUser");
			return form;
		}
		
		
		if (NodeButton.BTN_TYPE_START_USER_REBACK.equals(action)) { // 拟稿人主动撤回
			form.setStatus(ActUtil.BUSINESS_STATUS_已撤回);
			form.setStatusDesc("已撤回");
			form.setCurrApproveUser(nextTaskCandidateUserIds);
			db.update(form, "status", "statusDesc", "currApproveUser");
			return form;
		}
		
		return null;
	}
	
	@RequestMapping(mapping = { "/over_close", "/m/over_close" }, text = "流程节束更改业务表业务状态")
	public FormBaoXiao overClose(String token, String businessKey, String action) {
		FormBaoXiao form = db.getById(FormBaoXiao.class, businessKey);
		if (form == null) {
			log.error(String.format("没有找到ID=%s的对象", businessKey));
			return null;
		}
		
		if (NodeButton.BTN_TYPE_AGREE.equals(action)) { // 同意
			form.setStatus(ActUtil.BUSINESS_STATUS_已通过);
			form.setStatusDesc("已通过");
			form.setCurrApproveUser(null);
			db.update(form, "status", "statusDesc", "currApproveUser");
			return form;
		}
		
		return null;
	}
}
