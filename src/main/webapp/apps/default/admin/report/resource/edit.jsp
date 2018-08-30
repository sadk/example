<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>编辑报表的某一个按钮</title>
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
		            <legend>报表头控件信息</legend>
		            <div style="padding:5px;">
				        <table>
							<tr id="taskInfo">
								<td style="width:80px;" >控件名称：</td>
								<td style="width:150px;">
								 	<input name="name" id="name" class="mini-textbox" required="true"/>
								</td>
								
								<td style="width:80;" align="right">编码：</td>
								<td style="width:150px;">
								 	<input id="code" name="code" class="mini-textbox" required="true"/>
								</td>
							</tr>
							<tr>
								<td>控件类型：</td>
								<td>
								 	<input id="type" name="type" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=report_biz_controll_type" required="true"/>
								</td>
								<td>事件：</td>
								<td>
								 	<input name="btnScript" id="btnScript" class="mini-textbox" emptyText="如：onclick" />
								</td>
							</tr>
 							<tr>
 								<td>序号：</td>
								<td>
								 	<input name="sn" id="sn" class="mini-spinner" />
								</td>
 							</tr>
							<!-- 
							<tr>
								<td>前置脚本类型:</td>
								<td colspan="3">
									<input id="beforeScriptType" name="beforeScriptType" class="mini-combobox" style="width: 100%" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=node_button_script_type" />
								</td>
							</tr>
							<tr>
								<td>前置脚本片断：</td>
								<td colspan="3">
								 	<input id="beforeScript" name="beforeScript"  class="mini-textarea" style="width: 100%"  />
								</td>
							</tr>
							 
							<tr>
								<td>后置脚本类型:</td>
								<td colspan="3">
									<input id="afterScriptType" name="afterScriptType" class="mini-combobox" style="width: 100%" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=node_button_script_type" />
								</td>
							</tr>
							-->
							<tr>
								<td>脚本片断：</td>
								<td colspan="3">
								 	<input name="btnScript" id="btnScript" class="mini-textarea"  style="width: 100%;height: 150px;"/>
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
 
			var definitionId = null;
 
			
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
				console.log("defId:"+definitionId)
				console.log(o)
				
				o.definitionId = definitionId;
				$.ajax({
					url : "${pageContext.request.contextPath}/report/resource/save_or_update",
					dataType: 'json', type : 'post',
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
				definitionId = data.definitionId;
				//console.log(data);
				
				
				if(data.action == "edit") {
					
					$.ajax({
						url : "${pageContext.request.contextPath}/report/resource/get_by_id?id=" + data.id,
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
