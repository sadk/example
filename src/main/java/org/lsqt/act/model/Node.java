package org.lsqt.act.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.FormProperty;
import org.lsqt.components.util.collection.ArrayUtil;

public class Node {
	private Long id;
	private String definitionId;
	private String taskKey;
	private String taskName;

	
	@Deprecated	private Integer taskDefType; // 节点定义类型: 1=usertask
	@Deprecated public static final int TASK_DEF_TYPE_USERTASK=1;
	
	/** 节点类型: 0=拟稿节点(退回到拟稿人时有用) 1=普通任务节点 2=结束节点  3=会签结点**/
	private Integer taskBizType; 
	public static final int TASK_BIZ_TYPE_DRAFTNODE=0;
	public static final int TASK_BIZ_TYPE_UNDRAFTNODE=1;
	public static final int TASK_BIZ_TYPE_LASTNODE=2;
	public static final int TASK_BIZ_TYPE_MEETINGNODE=3;
	public String getTaskBizTypeDesc() {
		if(taskBizType!=null) {
			if(TASK_BIZ_TYPE_DRAFTNODE == taskBizType) {
				return "拟稿节点";
			}
			if(TASK_BIZ_TYPE_UNDRAFTNODE == taskBizType) {
				return "普通节点"; // 普通节点显示空串，好突显特殊节点
			}
			if(TASK_BIZ_TYPE_LASTNODE == taskBizType) {
				return "结束节点 ";
			}
			if(TASK_BIZ_TYPE_MEETINGNODE == taskBizType) {
				return "会签节点 ";
			}
		}
		return "";
	}
	
	
	private String formKey ; // 表单url
	
	private String nodeJumpType; // 1=正常跳转 2=选择路径跳转 3=自由跳转 4=发起时自动跳过 5=审批用户为空自动跳过(节点用户设置为空）   6=解析用户为空自动跳过（节点用户设置不为空，但程序解析不到用户）
	public static final String NODE_JUMP_TYPE_NORMAL = "1";
	@Deprecated public static final String NODE_JUMP_TYPE_CHOOSE_ROAD = "2";
	@Deprecated public static final String NODE_JUMP_TYPE_ANY_JUMP = "3";
	public static final String NODE_JUMP_TYPE_AUTO_JUMP = "4";
	public static final String NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP = "5";
	public static final String NODE_JUMP_TYPE_RESOLVED_USER_EMPTY_AUTO_JUMP="6";
	
	public static final String NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP_VIRTUAL_USERID="-1"; // 当设置流程节点审批用户为空时，节点自动跳过使用此虚拟用户ID
	
	/** 是否从全局变量里copy流程变量值到当前节点 0=否  1=是 2=第三方接口设置 **/
	private Integer nodeVariableCopy;
	public static final int NODE_VARIABLE_COPY_NONE=0;
	public static final int NODE_VARIABLE_COPY_FROM_GLOBAL=1;
	public static final int NODE_VARIABLE_COPY_FROM_CUSTOME=2;
	
	
	
	// ----------------------------------------------------  意见相关约束     ----------------------------------------
	private String opinionToDoType ; // 意见处理类型: 1=意见处理是否必填  2=意见处理是否隐藏 3=意见是否弹出显示 4=意见回填
	public static final String OPINION_TO_DO_TYPE_REQURIED = "1";
	public static final String OPINION_TO_DO_TYPE_HIDE ="2";
	public static final String OPINION_TO_DO_TYPE_POP ="3";
	public static final String OPINION_TO_DO_TYPE_BACK_FILL ="4";
	
	private String opinionToDoTypeValue;
	public static final String OPINION_TO_DO_TYPE_VALUE_POP_TRUE = "1"; // 意见是否弹出显示 : 是
	public static final String OPINION_TO_DO_TYPE_VALUE_POP_FALSE = "0"; // 意见是否弹出显示 : 否
	
	public String getOpinionToDoTypeDesc() {
		if (OPINION_TO_DO_TYPE_REQURIED.equals(this.opinionToDoType)) {
			return "意见必填";
		}
		if (OPINION_TO_DO_TYPE_HIDE.equals(this.opinionToDoType)) {
			return "意见隐藏";
		}
		if (OPINION_TO_DO_TYPE_POP.equals(this.opinionToDoType)) {
			if (OPINION_TO_DO_TYPE_VALUE_POP_TRUE.equals(opinionToDoTypeValue)) {
				return "是";
			}
			if (OPINION_TO_DO_TYPE_VALUE_POP_FALSE.equals(opinionToDoTypeValue)) {
				return "否";
			}
		}
		if (OPINION_TO_DO_TYPE_BACK_FILL.equals(this.opinionToDoType)) {
			return this.opinionToDoTypeValue;
		}
		return "";
	}
	
	private String opinionBackFillFiled ; // 意见回填的数据库字段
	
	public String getNodeJumpTypeDesc() {
		if (nodeJumpType == null) {
			return "正常跳转";
		}

		if (nodeJumpType.equals(NODE_JUMP_TYPE_NORMAL)) {
			return "正常跳转";
		}
		if (nodeJumpType.equals(NODE_JUMP_TYPE_CHOOSE_ROAD)) {
			return "选择路径跳转";
		}
		if (nodeJumpType.equals(NODE_JUMP_TYPE_ANY_JUMP)) {
			return "自由跳转";
		}
		if (nodeJumpType.equals(NODE_JUMP_TYPE_AUTO_JUMP)) {
			return "发起时自动跳过";
		}
		if (nodeJumpType.equals(NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP)) {
			return "审批用户为空自动跳过";
		}
		
		return "";
	}
	
	
	private List<String> candidateGroups = new ArrayList<>();
	private List<String> candidateUsers = new ArrayList<>();
	private List<FormProperty> formProperties = new ArrayList<FormProperty>();// 表单属性，用于做表单字段权限使用，暂用原生对象
	
	
	private Map<String,Object> extProperty = new HashMap<>(); // 节点的扩展属性，用于存放编程时的变量,如:startUserId
	
	public String getCandidateGroupsValue() {
		if (candidateGroups != null)
			return ArrayUtil.join(candidateGroups, ",");
		return "";
	}

	public String getCandidateUsersValue() {
		if (candidateUsers != null)
			return ArrayUtil.join(candidateUsers, ",");
		return "";
	}

	public String getFormPropertiesValue() {
		if (formProperties != null)
			return ArrayUtil.join(formProperties, ",");
		return "";
	}
	
	
	
	
	
	
	private String appCode;// 租户编码
	private Integer sn;
	private String remark;
	private String gid;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDefinitionId() {
		return definitionId;
	}
	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}
	public String getTaskKey() {
		return taskKey;
	}
	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Integer getTaskDefType() {
		return taskDefType;
	}
	public void setTaskDefType(Integer taskDefType) {
		this.taskDefType = taskDefType;
	}
	public Integer getTaskBizType() {
		return taskBizType;
	}
	public void setTaskBizType(Integer taskBizType) {
		this.taskBizType = taskBizType;
	}
	public Integer getSn() {
		return sn;
	}
	public void setSn(Integer sn) {
		this.sn = sn;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.util.Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public String getNodeJumpType() {
		return nodeJumpType;
	}

	public void setNodeJumpType(String nodeJumpType) {
		this.nodeJumpType = nodeJumpType;
	}

	public String getOpinionToDoType() {
		return opinionToDoType;
	}

	public void setOpinionToDoType(String opinionToDoType) {
		this.opinionToDoType = opinionToDoType;
	}

	public String getOpinionToDoTypeValue() {
		return opinionToDoTypeValue;
	}

	public void setOpinionToDoTypeValue(String opinionToDoTypeValue) {
		this.opinionToDoTypeValue = opinionToDoTypeValue;
	}

	public String getOpinionBackFillFiled() {
		return opinionBackFillFiled;
	}

	public void setOpinionBackFillFiled(String opinionBackFillFiled) {
		this.opinionBackFillFiled = opinionBackFillFiled;
	}

	public List<String> getCandidateGroups() {
		return candidateGroups;
	}

	public void setCandidateGroups(List<String> candidateGroups) {
		this.candidateGroups = candidateGroups;
	}

	public List<String> getCandidateUsers() {
		return candidateUsers;
	}

	public void setCandidateUsers(List<String> candidateUsers) {
		this.candidateUsers = candidateUsers;
	}

	public List<FormProperty> getFormProperties() {
		return formProperties;
	}

	public void setFormProperties(List<FormProperty> formProperties) {
		this.formProperties = formProperties;
	}

	public Integer getNodeVariableCopy() {
		return nodeVariableCopy;
	}

	public void setNodeVariableCopy(Integer nodeVariableCopy) {
		this.nodeVariableCopy = nodeVariableCopy;
	}

	public Map<String, Object> getExtProperty() {
		return extProperty;
	}

	public void setExtProperty(Map<String, Object> extProperty) {
		this.extProperty = extProperty;
	}
}
