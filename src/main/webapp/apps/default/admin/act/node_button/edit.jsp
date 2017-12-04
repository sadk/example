<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>编辑节点某一个按钮</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>

		<style type="text/css">
			html, body {
				font-size: 12px;
				padding: 0;
				margin: 0;
				border: 0;
				height: 100%;
				overflow: hidden;
			}
		</style>
	</head>
	<body> 
		<form id="edit-form1" method="post" style="height:97%; overflow:auto;">
			<input id="id" name="id" class="mini-hidden" />
			<input id="definitionId" name="definitionId" class="mini-hidden" />
			<input id="taskName" name="taskName" class="mini-hidden" />
			<input id="taskKey" name="taskKey" class="mini-hidden" />
			<div style="padding:4px;padding-bottom:5px;">
			
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>按钮信息</legend>
		            <div style="padding:5px;">
				        <table>
							<tr id="taskInfo">
								<td style="width:150px;" >名称：</td>
								<td style="width:150px;">
								 	<input name="btnName" id="btnName" class="mini-textbox"/>
								</td>
								
								<td style="width:80;" align="right">编码：</td>
								<td style="width:150px;">
								 	<input id="btnCode" name="btnCode" class="mini-textbox"/>
								</td>
							</tr>
							<tr>
								<td style="width:150;">按钮范围：</td>
								<td style="width:150px;">
								 	<input id="dataType" name="dataType" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=node_button_type_categroy" />
								</td>
								
								<td style="width:80px;" align="right">序号：</td>
								<td style="width:150px;">
								 	<input name="sn" id="sn" class="mini-spinner" />
								</td>
							</tr>
							<tr>
								<td>按钮类型:</td>
								<td colspan="3">
									<input id="btnType" name="btnType" class="mini-combobox" onvaluechanged="onButtonChanged" style="width: 100%" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=form_node_button_type" />
								</td>
							</tr>
							<tr>
								<td>前置角本类型:</td>
								<td colspan="3">
									<input id="beforeScriptType" name="beforeScriptType" class="mini-combobox" style="width: 100%" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=node_button_script_type" />
								</td>
							</tr>
							<tr>
								<td>前置角本片断：</td>
								<td colspan="3">
								 	<input id="beforeScript" name="beforeScript"  class="mini-textarea" style="width: 100%"  />
								</td>
							</tr>
							<tr>
								<td>后置角本类型:</td>
								<td colspan="3">
									<input id="afterScriptType" name="afterScriptType" class="mini-combobox" style="width: 100%" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=node_button_script_type" />
								</td>
							</tr>
							<tr>
								<td>后置角本片断：</td>
								<td colspan="3">
								 	<input name="afterScript" id="afterScript" class="mini-textarea" style="width: 100%"/>
								</td>
							</tr>
				        </table>
				    </div>
				</fieldset>
				   
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
					 <legend>角本片断内置变量说明：</legend>
						<div style="width: 100%;border: 0;margin: 0;padding: 0;color: green">
				    		 <ul style="margin: 2px;">
				    			<li>loginUser=登陆用户（常用属性:userId、loginNo、userName等）</li>
				    			<li>processInstanceId=流程实例ID</li>
				          		<li>processDefinitionId=流程定义ID</li>
				          		<li>businessKey=业务主键</li>
				          		<li>taskId=当前任务ID</li>
				          		<li>action=审批按钮编码: [agree=同意、reject_to_starter=不同意(驳回到发起人)、disagree_continue_go=不同意((最后一个节点)流程继续往下走)、any_reback=撤回、abort=作废、 add_assign=加签、forword_read=转发、copy_send=抄送]</li>
				          	 </ul>
				    	</div>
				</fieldset>
			</div>
			<div id="subbtn" style="text-align:center;padding:10px;">
				<a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>
			</div>
		</form>
		<script type="text/javascript">
			mini.parse();
			
			var form = new mini.Form("edit-form1");
		
			var taskName = mini.get("taskName");
			var taskKey = mini.get("taskKey");
			var definitionId = mini.get("definitionId");
			var dataType = mini.get("dataType");// 节点按钮、全局按钮
			var btnType = mini.get("btnType"); //
			var btnCode = mini.get("btnCode");
			
			function onButtonChanged(e) {
			 	var cd = btnType.getValue();
			 	for(var i=0;i<btnType.getData().length;i++){
					if((cd+"") == (btnType.getData()[i].value+"")){
						 btnCode.setValue(btnType.getData()[i].code);
						 break;
					}
			 	}
			}
			
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				
				$.ajax({
					url : "${pageContext.request.contextPath}/act/node_button/save_or_update",
					dataType: 'json',
					type : 'post',
					cache : false,
					data: o,
					success : function(text) {
						CloseWindow("save");
					}
				});
			}

			////////////////////
			//标准方法接口定义
			function SetData(data) {
				data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
				
				taskName.setValue(data.taskName);
				taskKey.setValue(data.taskKey);
				definitionId.setValue(data.definitionId);
				
				if(data.action == "edit") {
					
					$.ajax({
						url : "${pageContext.request.contextPath}/act/node_button/get_by_id?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							form.setData(o);
							form.setChanged(false);
						}
					});
				}
			}
			
			function GetData() {
				var o = form.getData();
				return o;
			}

			function CloseWindow(action) {
				if(action == "close" && form.isChanged()) {
					if(confirm("数据被修改了，是否先保存？")) {
						return false;
					}
				}
				if(window.CloseOwnerWindow)
					return window.CloseOwnerWindow(action);
				else
					window.close();
			}

			function onOk(e) {
				SaveData();
			}

			function onCancel(e) {
				CloseWindow("cancel");
			}
		</script>
	</body>
</html>
