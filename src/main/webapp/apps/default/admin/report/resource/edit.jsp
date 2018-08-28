<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>报表页面元素定义</title>
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
			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>XXX信息</legend>
		            <div style="padding:5px;">
				        <table>
				        
									<tr>
										<td>报表分类名：</td>
										<td>
											<input id="name" name="name"  style="width:140px" class="mini-textbox"  emptyText="请输入报表分类名"  onenter="search"/>
										</td>
									</tr>
									
									
									
									<tr>
										<td>定义编码：</td>
										<td>
											<input id="code" name="code"  style="width:140px" class="mini-textbox"  emptyText="请输入定义编码"  onenter="search"/>
										</td>
									</tr>
									
									
									
									<tr>
										<td>报表页面元素类型: 1=按钮 2=下拉框（单选）3=下拉框（多选）4=文本框 5=TextArae 6=File 7=日历 8=数字框 ：</td>
										<td>
											<input id="type" name="type"  style="width:140px" class="mini-textbox"  emptyText="请输入报表页面元素类型: 1=按钮 2=下拉框（单选）3=下拉框（多选）4=文本框 5=TextArae 6=File 7=日历 8=数字框 "  onenter="search"/>
										</td>
									</tr>
									
									
									
									<tr>
										<td>按钮点击前事件触发的js函数：</td>
										<td>
											<input id="btnBeforeScript" name="btnBeforeScript"  style="width:140px" class="mini-textbox"  emptyText="请输入按钮点击前事件触发的js函数"  onenter="search"/>
										</td>
									</tr>
									
									
									
									<tr>
										<td>按钮点击后事件触发js函数：</td>
										<td>
											<input id="btnAfterScript" name="btnAfterScript"  style="width:140px" class="mini-textbox"  emptyText="请输入按钮点击后事件触发js函数"  onenter="search"/>
										</td>
									</tr>
									
									
									
									<tr>
										<td>租户编码：</td>
										<td>
											<input id="appCode" name="appCode"  style="width:140px" class="mini-textbox"  emptyText="请输入租户编码"  onenter="search"/>
										</td>
									</tr>
									
									
									
									<tr>
										<td>备注：</td>
										<td>
											<input id="remark" name="remark"  style="width:140px" class="mini-textbox"  emptyText="请输入备注"  onenter="search"/>
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
				$.ajax({
					url : "${pageContext.request.contextPath}/resource/save_or_update",
					dataType: 'json',
					type : 'post',
					cache : false,
					data: form.getData(),
					success : function(text) {
						CloseWindow("save");
					}
				});
			}

			////////////////////
			//标准方法接口定义
			function SetData(data) {
				data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
				
				 if(data.action == "edit" || data.action=='view') {
					$.ajax({
						url : "${pageContext.request.contextPath}/resource/page?id=" + data.id,
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
