<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>入职处理</title>
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
			<div style="padding:4px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>入职办理</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td style="width:100px;">入职企业：</td>
								<td style="width:150px;">
								 	<input name="companyName" id="companyName" class="mini-textbox" onclick = "onClickCompanyName()" emptyText="请选择入职企业" />
								 	<input name="companyCode" id="companyCode" class="mini-hidden"/>
								 	<input name="userIds"     id="userIds" class="mini-hidden"/>
								</td>
								<td style="width:100px;">入(离)职日期：</td>
								<td style="width:150px;">
									<input id="entryTime" name="entryTime" class="mini-datepicker" emptyText="请选择入职日期"  />
								</td>
							</tr>
							
							<tr>
								<td>入职状态：</td>
								<td>
								 	<input id="entryStatus" name="entryStatus" required="true"   class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_jianli_tech_entry_status_new" />
								</td>
								<td>入职备注：</td>
								<td>
									<input id="remark" name="remark" class="mini-textbox" emptyText="请输入备注"  />
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
			
	      	function onClickCompanyName() {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/rst/company/selector_company.jsp?multiSelect=false",
					title : "企业选择",
					width : 550,
					height : 480,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.GetData();
						if(data) {
							data = mini.clone(data);
							mini.get("companyCode").setValue(data.code);
							mini.get("companyName").setValue(data.fullName);
							
						}
					}
				});	
	      	}
	      	
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				
				o.entryTime = mini.get("entryTime").text;
				
				$.ajax({
					url : "${pageContext.request.contextPath}/rst/user_entry_info/batch_short_update",
					dataType: 'json', data: o,type : 'post',
					success : function(text) {
						CloseWindow("save");
					}
				});
			}

			////////////////////
			//标准方法接口定义
			function SetData(data) {
				data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
				console.log(data)
				form.setData(data);
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
