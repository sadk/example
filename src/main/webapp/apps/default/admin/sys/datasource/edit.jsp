<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>数据源管理</title>
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
			<input name="id" class="mini-hidden" />
			<div style="padding:4px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>系统信息</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td style="width:110px;">名称：</td>
								<td style="width:150px;">
								 	<input name="name" id="name" class="mini-textbox"  />
								</td>
								<td style="width:100px;">编码：</td>
								<td style="width:150px;">
									<input name="code" id="code" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>链接地址：</td>
								<td>
								 	<input name="url" id="url" class="mini-textbox"  />
								</td>
								<td>驱动：</td>
								<td>
									<input name="driverClassName" id="driverClassName" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>登陆名：</td>
								<td>
								 	<input name="loginName" id="loginName" class="mini-textbox"  />
								</td>
								<td>登陆密码：</td>
								<td>
									<input name="loginPassword" id="loginPassword" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>登陆默认库：</td>
								<td>
								 	<input name="loginDefaultDb" id="loginDefaultDb" class="mini-textbox"/>
								</td>
								<td>状态：</td>
								<td>
								 	<input id="status" name="status" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=datasource" value="2"/>
								</td>
							</tr> 
							<tr>
								<td>IP或域名：</td>
								<td>
								 	<input name="address" id="address" class="mini-textbox" />
								</td>
								<td>端口：</td>
								<td>
								 	<input id="port" name="port" class="mini-textbox" />
								</td>
							</tr> 
							<tr>
								<td>排序号：</td>
								<td>
								 	<input name="sn" id="sn" class="mini-spinner" value="0" minValue="0" maxValue="999999999"  />
								</td>
								<td>备注：</td>
								<td>
								 	<input id="remark" name="remark" class="mini-textbox"/>
								</td>
							</tr>
				        </table>
				    </div>
				</fieldset>
			</div>
			<div id="subbtn" style="text-align:center;padding:10px;">
				<a class="mini-button" onclick="testConnection" id="btnTest" style="width:80px;margin-right:20px;">测试连接</a>
				<a class="mini-button" onclick="onOk" id="btnOk" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="onCancel" id="btnCancel" style="width:60px;">取消</a>
			</div>
		</form>
		<script type="text/javascript">
			mini.parse();

			var form = new mini.Form("edit-form1");
			
			var btnTest = mini.get("btnTest");
			var btnOk =  mini.get("btnOk");
			var btnCancel =  mini.get("btnCancel");
			
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				$.ajax({
					url : "${pageContext.request.contextPath}/datasource/save_or_update",
					dataType: 'json',
					type : 'post',
					cache : false,
					data: form.getData(),
					success : function(text) {
						CloseWindow("save");
					}
				});
			}
			
			function testConnection() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				$.ajax({
					url : "${pageContext.request.contextPath}/datasource/test/connection",
					dataType: 'json',
					type : 'post',
					cache : false,
					data: form.getData(),
					success : function(text) {
						mini.alert("连接成功!");
					},
					error : function(data) {
				  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
				  		mini.alert("连接失败!;"+data.statusText);
					}
				});
			}
			////////////////////
			//标准方法接口定义
			function SetData(data) {
				data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
				
				 if(data.action == "edit" || data.action=='view') {
					$.ajax({
						url : "${pageContext.request.contextPath}/datasource/page?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							if(o!=null && o.data!=null && o.data.length>0) {
								o = o.data[0];
							}
							form.setData(o);
							form.setChanged(false);
							
							if (data.action == 'view') {
								form.setEnabled(false);
								btnTest.hide();
								btnOk.hide();
								//btnCancel.hide();
								btnCancel.setText("关闭");
							}
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
