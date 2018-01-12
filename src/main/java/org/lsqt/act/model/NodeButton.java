package org.lsqt.act.model;


/**
 * 节点按钮设置
 */
public class NodeButton {

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	/***/
	private Long id;

	/** 流程定义ID */
	private String definitionId;

	/** 节点定义Key */
	private String taskKey;
	private String taskName; // 辅助属性

	/**按钮定义类别: 1=节点按钮 2=全局按钮**/
	private Integer dataType;
	public static int DATA_TYPE_TASK_BUTTON=1;
	public static int DATA_TYPE_GLOBAL_BUTTON=2;
	
	public String getDataTypeDesc() {
		if(dataType==null) return "";
		if(DATA_TYPE_GLOBAL_BUTTON == dataType) {
			return "全局按钮";
		}
		if(DATA_TYPE_TASK_BUTTON == dataType) {
			return "节点按钮";
		}
		return "";
	}
	
	/** 按钮名称 */
	private String btnName;

	/** 按钮编码 */
	private String btnCode;

	public static String getApproveActionDesc(String code, String ... appendMessage){
		String message = "";
		
		if(appendMessage !=null && appendMessage.length>0){
			message = ","+appendMessage[0];
		}
		
		if(NodeButton.BTN_TYPE_AGREE.equals(code)) return ("同意"+message);
		if(NodeButton.BTN_TYPE_REJECT.equals(code)) return ("驳回(驳回到上一步) "+message);
		if(NodeButton.BTN_TYPE_REJECT_TO_STARTER.equals(code)) return "驳回到发起人(指的是驳回到拟稿节点) "; // 新城的“不同意”
		if(NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(code)) return ("驳回到选择节点"+message);
		
		
		if(NodeButton.BTN_TYPE_PROXY_DO.equals(code)) return ("转交代办"+message);
		if(NodeButton.BTN_TYPE_COMMUNICATE.equals(code)) return ("沟通"+message);
		if(NodeButton.BTN_TYPE_SAVE_FORM.equals(code)) return ("保存表单"+message);
		if(NodeButton.BTN_TYPE_SAVE_DRAFT.equals(code)) return "保存草稿"+message;
		
		
		if(NodeButton.BTN_TYPE_ABORT.equals(code)) return "作废(删除当前的流程实例)"+message;
		if(NodeButton.BTN_TYPE_ACCEPT.equals(code)) return "授理"+message;
		if(NodeButton.BTN_TYPE_START_USER_REBACK.equals(code)) return "（发起人）撤回"+message;
		if(NodeButton.BTN_TYPE_DISAGREE_CONTINUE_GO.equals(code)) return "不同意(流程继续往下走)"+message;
		if(NodeButton.BTN_TYPE_START.equals(code)) return "发起"+message;
		
		if(NodeButton.BTN_TYPE_ADD_ASSIGN.equals(code)) return "加签"+message;
		if(NodeButton.BTN_TYPE_ADD_ASSIGN_AGREE.equals(code)) return "加签同意"+message;
		if(NodeButton.BTN_TYPE_ADD_ASSIGN_DISAGREE.equals(code)) return "加签不同意"+message;
		
		if(NodeButton.BTN_TYPE_FORWORD_READ.equals(code)) return "转发"+message;
		if(NodeButton.BTN_TYPE_COPY_SEND.equals(code)) return "抄送"+message;
		
		//if(NodeButton.BTN_TYPE_DISAGREE_TO_CLOSE.equals(code)) return "不同意(流程结束)"+message;
		if(NodeButton.BTN_TYPE_DISAGREE_TO_CLEAR.equals(code)) return "不同意(流程实例级联清除)"+message;
		if(NodeButton.BTN_TYPE_ANY_REBACK.equals(code)) return "(任意)撤回"+message;
		return "";
	}
	
	public static String getApproveActionShortDesc(String code, String ... appendMessage){
		String message = "";
		
		if(appendMessage !=null && appendMessage.length>0){
			message = ","+appendMessage[0];
		}
		
		if(NodeButton.BTN_TYPE_AGREE.equals(code)) return ("同意"+message);
		if(NodeButton.BTN_TYPE_REJECT.equals(code)) return ("驳回"+message);
		if(NodeButton.BTN_TYPE_REJECT_TO_STARTER.equals(code)) return "驳回到发起人";
		if(NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(code)) return ("驳回到选择节点"+message);
		
		
		if(NodeButton.BTN_TYPE_PROXY_DO.equals(code)) return ("转交代办"+message);
		if(NodeButton.BTN_TYPE_COMMUNICATE.equals(code)) return ("沟通"+message);
		if(NodeButton.BTN_TYPE_SAVE_FORM.equals(code)) return ("保存表单"+message);
		if(NodeButton.BTN_TYPE_SAVE_DRAFT.equals(code)) return "保存草稿"+message;
		
		
		if(NodeButton.BTN_TYPE_ABORT.equals(code)) return "作废"+message;
		if(NodeButton.BTN_TYPE_ACCEPT.equals(code)) return "授理"+message;
		if(NodeButton.BTN_TYPE_START_USER_REBACK.equals(code)) return "撤回"+message;
		if(NodeButton.BTN_TYPE_DISAGREE_CONTINUE_GO.equals(code)) return "不同意"+message;
		if(NodeButton.BTN_TYPE_START.equals(code)) return "发起"+message;
		
		if(NodeButton.BTN_TYPE_ADD_ASSIGN.equals(code)) return "加签"+message;
		if(NodeButton.BTN_TYPE_ADD_ASSIGN_AGREE.equals(code)) return "加签同意"+message;
		if(NodeButton.BTN_TYPE_ADD_ASSIGN_DISAGREE.equals(code)) return "加签不同意"+message;
		
		if(NodeButton.BTN_TYPE_FORWORD_READ.equals(code)) return "转发"+message;
		if(NodeButton.BTN_TYPE_COPY_SEND.equals(code)) return "抄送"+message;
		
		//if(NodeButton.BTN_TYPE_DISAGREE_TO_CLOSE.equals(code)) return "不同意(流程结束)"+message;
		if(NodeButton.BTN_TYPE_DISAGREE_TO_CLEAR.equals(code)) return "不同意"+message;
		if(NodeButton.BTN_TYPE_ANY_REBACK.equals(code)) return "撤回"+message;
		return "";
	}
	
	/** 
	 * 按钮类型:
	 *  
	 * 1=同意(agree) 
	 * 
	 * 2=驳回(reject,指驳回到上一步) 
	 * 3=驳回到发起人(reject_to_starter,指的是驳回到拟稿节点) 
	 * 4=驳回到选择节点(reject_to_choose_node) 
	 * 
	 * 5=转交代办(proxy_do)  
	 * 6=沟通(communicate ： 把人员加进来可查看单据，给出意见，不能提交下一步流程审批）
	 * 7=保存表单(save_form) 
	 * 8=保存草稿(save_draft)  
	 * 
	 * 9=作废 (abort) 
	 * 10=授理(accept)  
	 * 11=（发起人）撤回(start_user_reback)
	 * 12=不同意(disagree_continue_go)，流程继续往下走
	 * 13=发起(start)
	 * 
	 * 14=加签(产生待办) ，add_assign
	 * 15=转发(不产生真实引擎待办，可查看单据)，forword_read
	 * 16=抄送(不产生真实引擎待办，可查看单据)，copy_send
	 * 
	 * 17=不同意(流程结束)，disagree_to_close  ---------------待删除！！
	 * 18=不同意(流程实例级联清除)，disagree_to_clear
	 * 19=(任意)撤回， any_reback
	 * 
	 * 20=退回到选择节点，多级退回！(reject_to_choose_node_for_mutil_back)
	 * */
	private Integer btnType;
	
	/**重提交按钮**/
	public static final String BTN_TYPE_RESUBMIT="resubmit";
	
	/**同意**/
	public static final String BTN_TYPE_AGREE="agree";
	/**驳回**/
	public static final String BTN_TYPE_REJECT="reject";
	/**驳回到发起人**/
	public static final String BTN_TYPE_REJECT_TO_STARTER="reject_to_starter";
	/**驳回到选择节点**/
	public static final String BTN_TYPE_REJECT_TO_CHOOSE_NODE="reject_to_choose_node";
	/**转交代办**/
	public static final String BTN_TYPE_PROXY_DO="proxy_do";
	/**沟通**/
	public static final String BTN_TYPE_COMMUNICATE="communicate";
	/**保存表单**/
	public static final String BTN_TYPE_SAVE_FORM="save_form";
	/**保存草稿**/
	public static final String BTN_TYPE_SAVE_DRAFT="save_draft";
	/**作废**/
	public static final String BTN_TYPE_ABORT = "abort";
	/**授理**/
	public static final String BTN_TYPE_ACCEPT="accept";
	/**（发起人）撤回**/
	public static final String BTN_TYPE_START_USER_REBACK="start_user_reback";
	/**不同意(流程继续往下走)**/
	public static final String BTN_TYPE_DISAGREE_CONTINUE_GO="disagree_continue_go";
	/**流程发起**/
	public static final String BTN_TYPE_START="start";
	
	/**加签(产生待办)**/
	public static final String BTN_TYPE_ADD_ASSIGN="add_assign";
	public static final String BTN_TYPE_ADD_ASSIGN_AGREE="add_assign_agree";
	public static final String BTN_TYPE_ADD_ASSIGN_DISAGREE="add_assign_disagree";
	
	/**转发(不产生真实引擎待办，可查看单据)**/
	public static final String  BTN_TYPE_FORWORD_READ ="forword_read";
	/**抄送(不产生真实引擎待办，可查看单据)**/
	public static final String  BTN_TYPE_COPY_SEND ="copy_send";
	
	
	/**不同意(流程结束)**/
	public static final String  BTN_TYPE_DISAGREE_TO_CLOSE ="disagree_to_close";
	/**不同意(流程实例级联清除)**/
	public static final String  BTN_TYPE_DISAGREE_TO_CLEAR ="disagree_to_clear";
	/**(任意)撤回**/
	public static final String  BTN_TYPE_ANY_REBACK ="any_reback";
	
	public static final String BTN_TYPE_REJECT_TO_CHOOSE_NODE_FOR_MUTIL_BACK="reject_to_choose_node_for_mutil_back";
	
	public String getBtnTypeDesc() {
		if(btnType!=null ){
			if(1 == btnType) return "同意";
			if(2 == btnType) return "驳回";
			if(3 == btnType) return "驳回到发起人";
			if(4 == btnType) return "驳回到选择节点";
			if(5 == btnType) return "转交代办";
			if(6 == btnType) return "沟通";
			if(7 == btnType) return "保存表单";
			if(8 == btnType) return "保存草稿";
			if(9 == btnType) return "作废";
			if(10 == btnType) return "授理";
			if(11 == btnType) return "(发起人)撤回";
			if(12 == btnType) return "不同意(流程继续往下走)";
			if(13 == btnType) return "发起";
			
			if(14 == btnType) return "加签(产生待办)";
			if(15 == btnType) return "转发(不产生待办)";
			if(16 == btnType) return "抄送(不产生待办)";
			if(17 == btnType) return "不同意(流程结束)";
			if(18 == btnType) return "不同意(流程实例级联清除)";
			if(19 == btnType) return "(任意)撤回";
		}
		return "";
	}

	/** 前置角本 、类型（1=url 2=javascript_code 3=javacode 4=groovy 5=sql）*/
	private String beforeScript;
	private String beforeScriptType;
	
	/** 后置角本、类型（1=url 2=javascript_code 3=javacode 5=groovy 5=sql） */
	private String afterScript;
	private String afterScriptType;

	public static final String SCRIPT_TYPE_URL="1";
	public static final String SCRIPT_TYPE_JAVASCRIPT_CODE="2";
	public static final String SCRIPT_TYPE_JAVA_CODE="3";
	public static final String SCRIPT_TYPE_GROOVY= "4";
	public static final String SCRIPT_TYPE_SQL= "5";
	
	/** 租户编码 */
	private String appCode;

	/** 排序 */

	private Integer sn;

	/** 备注 */
	private String remark;

	/***/
	private String gid;

	/** 创建日期 */

	private java.util.Date createTime;

	/***/

	private java.util.Date updateTime;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getDefinitionId() {
		return this.definitionId;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getTaskKey() {
		return this.taskKey;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getBtnName() {
		return this.btnName;
	}

	public void setBtnCode(String btnCode) {
		this.btnCode = btnCode;
	}

	public String getBtnCode() {
		return this.btnCode;
	}

	public void setBtnType(Integer btnType) {
		this.btnType = btnType;
	}

	public Integer getBtnType() {
		return this.btnType;
	}

	public void setBeforeScript(String beforeScript) {
		this.beforeScript = beforeScript;
	}

	public String getBeforeScript() {
		return this.beforeScript;
	}

	public void setAfterScript(String afterScript) {
		this.afterScript = afterScript;
	}

	public String getAfterScript() {
		return this.afterScript;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Integer getSn() {
		return this.sn;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGid() {
		return this.gid;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getBeforeScriptType() {
		return beforeScriptType;
	}

	public void setBeforeScriptType(String beforeScriptType) {
		this.beforeScriptType = beforeScriptType;
	}

	public String getAfterScriptType() {
		return afterScriptType;
	}

	public void setAfterScriptType(String afterScriptType) {
		this.afterScriptType = afterScriptType;
	}

}
