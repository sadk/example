<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>站点资源</title>
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
