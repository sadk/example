<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户表</title>
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
			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>数据接口信息</legend>
		            <div style="padding:5px;">
				        <table>
									<tr>
										<td>配置名称：</td>
										<td>
											<input id="configName" name="configName"  class="mini-textbox"  emptyText="请输入姓名"  onenter="search"/>
										</td>
										<td>编码：</td>
										<td>
											<input id="configCode" name="configCode"  class="mini-textbox"  emptyText="请输入编码"  onenter="search"/>
										</td>
									</tr>
									
									<tr>
										<td>接口地址：</td>
										<td colspan="3">
											<input id="url" name="url"  class="mini-textbox" style="width:100%" emptyText="请输入接口地址"  onenter="search"/>
										</td>
									</tr>
									
									<tr>
										<td>返回数据属性：</td>
										<td>
											<input id="dataProp" name="dataProp"  class="mini-textbox"  emptyText="请输入数据属性"  onenter="search"/>
										</td>
										<td>系统类型：</td>
										<td>
											<input id="configType" name="configType" value="1" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=user_data_config_config_type" />
										</td>
									</tr>
									
									
									<tr>
										<td>租户编码：</td>
										<td>
											<input id="appCode" name="appCode" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/application/all" />
										</td>
										<td>配置键：</td>
										<td>
											<input id="configKey" name="configKey"  class="mini-textbox"  emptyText="请输入配置键"  onenter="search"/>
										</td>
									</tr>
									
									
									<tr>
										<td>映射实体：</td>
										<td>
											<input id="entityType" name="entityType" value="1" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=object_mapping_type" />
										</td>
										<td>备注：</td>
										<td>
											<input id="remark" name="remark"  class="mini-textbox"  emptyText="请输入备注"  onenter="search"/>
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
		
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				
				//o.birthday = mini.get("birthday").text; // 日期类型要转取text
				
				$.ajax({
					url : "${pageContext.request.contextPath}/act/user_data_config/save_or_update_signle",
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
				
				 if(data.action == "edit") {
					$.ajax({
						url : "${pageContext.request.contextPath}/act/user_data_config/get_by_id?id=" + data.id,
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
