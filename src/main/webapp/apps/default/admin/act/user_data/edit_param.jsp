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
			<input id="configId" name="configId" class="mini-hidden" />
			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>接口参数信息</legend>
		            <div style="padding:5px;">
				        <table>
									<tr>
										<td>参数名称：</td>
										<td>
											<input id="paramName" name="paramName"  class="mini-textbox"  emptyText="请输入参数名称"  />
										</td>
										<td>编码：</td>
										<td>
											<input id="paramCode" name="paramCode"  class="mini-textbox"  emptyText="请输入参数编码"  />
										</td>
									</tr>
									
							 
									<tr>
										<td>参数类型：</td>
										<td>
											<input id="paramType" name="paramType" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=user_data_config_param" />
										</td>
										<td>参数(或预览)值：</td>
										<td>
											<input id="paramValue" name="paramValue"  class="mini-textbox"  emptyText="请输入参数值 "  />
										</td>
									</tr>
									
									
									<tr>
										<td>参数值表达式：</td>
										<td>
											<input id="paramValueExpress" name="paramValueExpress" class="mini-textbox" emptyText="请输入参数表达式" />
										</td>
										<td>序号：</td>
										<td>
											<input name="sn" id="sn" class="mini-spinner" class="mini-datepicker" minValue="0" maxValue="999999999"  />
										</td>
									</tr>
									
									
									<tr>
										<td>备注：</td>
										<td colspan="3">
											<input id="remark" name="remark"  class="mini-textarea"  emptyText="请输入备注"  style="width:100%"/>
										</td>
									</tr>
				        </table>
				    </div>
				</fieldset>
			</div>
			<div id="subbtn" style="text-align:center;padding:10px;">
				<a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
				 <a class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
			</div>
		</form>
		<script type="text/javascript">
			mini.parse();

			var form = new mini.Form("edit-form1");
			var configIdHid = mini.get("configId");
			var configId = null;
			
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				
				//o.birthday = mini.get("birthday").text; // 日期类型要转取text
				
				$.ajax({
					url : "${pageContext.request.contextPath}/act/user_data_config_param/save_or_update_signle",
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
				
				// mini.alert(data.configId);
				configIdHid.setValue(data.configId);
				
				 if(data.action == "edit") {
					$.ajax({
						url : "${pageContext.request.contextPath}/act/user_data_config_param/get_by_id?id=" + data.id,
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
