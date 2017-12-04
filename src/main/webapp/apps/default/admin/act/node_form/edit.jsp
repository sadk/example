<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>流程表字段管理</title>
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
			<div style="padding:4px;padding-bottom:5px;">
			
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>表单配置</legend>
		            <div style="padding:5px;">
				        <table>
							<tr id="taskInfo">
								<td style="width:120px;">节点名称：</td>
								<td style="width:150px;">
								 	<input name="taskName" id="taskName" class="mini-textbox"  readonly="readonly"/>
								</td>
								
								<td style="width:120px;">节点key：</td>
								<td style="width:150px;">
								 	<input id="taskKey" name="taskKey" class="mini-textbox" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<td style="width:120px;">定义类别：</td>
								<td style="width:150px;">
								 	<input id="dataType" name="dataType" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=form_definition_type" />
								</td>
								
								<td style="width:120px;">表单类型：</td>
								<td style="width:150px;">
								 	<input id="formType" name="formType" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=act_form_type" />
								</td>
							</tr>
							<tr>
								<td>编辑URL：</td>
								<td colspan="3">
								 	<input id="formUrl" name="formUrl"  class="mini-textarea" style="width: 100%"  />
								</td>
							</tr>
							<tr>
								<td>明细URL：</td>
								<td colspan="3">
								 	<input name="formDetailUrl" id="formDetailUrl" class="mini-textarea" style="width: 100%"/>
								</td>
							</tr>
							<tr>
								<td>自定义URL：</td>
								<td colspan="3">
								 	<input name="customUrl" id="customUrl" class="mini-textarea" style="width: 100%"/>
								</td>
							</tr>
				        </table>
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
			var dataType = mini.get("dataType");
			var formType = mini.get("formType");
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				
				$.ajax({
					url : "${pageContext.request.contextPath}/act/node_form/save_or_update",
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
				//console.log(data)
				taskName.setValue(data.taskName);
				taskKey.setValue(data.taskKey);
				definitionId.setValue(data.definitionId);
				
				if(data.action == "edit" || data.action=='view') {
					
					$.ajax({
						url : "${pageContext.request.contextPath}/act/node_form/get_by_id?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							form.setData(o);
							form.setChanged(false);
						}
					});
				}
				
				
				// 设置全局表单
				if(data.action == 'editGlobal') {
					$.ajax({
						url : "${pageContext.request.contextPath}/act/node_form/page?definitionId=" + data.definitionId+"&dataType=2",
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							if(o.data && o.data.length>0){
								form.setData(o.data[0]);
								form.setChanged(false);
							}
							
							taskName.setValue("全局表单");
							taskKey.setValue("全局表单");
							
							formType.setValue(1);
							dataType.setValue(2);
							dataType.setEnabled(false);
							
							$("#taskInfo").hide();
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
