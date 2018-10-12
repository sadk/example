<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>修改密码</title>
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
		
	<style type="text/css">
    .asLabel .mini-textbox-border,
    .asLabel .mini-textbox-input,
    .asLabel .mini-buttonedit-border,
    .asLabel .mini-buttonedit-input,
    .asLabel .mini-textboxlist-border
    {
        border:0;background:none;cursor:default;
    }
    .asLabel .mini-buttonedit-button,
    .asLabel .mini-textboxlist-close
    {
        display:none;
    }
    .asLabel .mini-textboxlist-item
    {
        padding-right:8px;
    }    
    </style>
	</head>
	<body> 
		<form id="edit-form1" method="post" style="height:97%; overflow:auto;">
			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>用户信息</legend>
		            <div style="padding:5px;">
				        <table>
				        
									<tr>
										<td>姓名：</td>
										<td>
											<input id="name" name="name"  class="mini-textbox"  emptyText="请输入姓名" enabled = "false"/>
										</td>
										<td>登陆帐号：</td>
										<td>
											<input id="loginName" name="loginName"  class="mini-textbox"  emptyText="请输入帐号" enabled = "false" />
										</td>
									</tr>
									
									<tr>
										<td>新的密码：</td>
										<td>
											<input id="loginPwd" name="loginPwd"  class="mini-password"  emptyText="请输入新的密码"  required="true"/>
										</td>
										<td>确认密码：</td>
										<td>
											<input id="loginPwdReboot" name="loginPwdReboot"  class="mini-password"  emptyText="请再输入一次密码" required="true" />
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
			
			var loginPwd = mini.get("loginPwd");
			var loginPwdReboot = mini.get("loginPwdReboot");
			
			function SaveData() {
				var o = form.getData();
				console.log(o)
				form.validate();
				if(form.isValid() == false) return;
				
				if (o.loginPwd != o.loginPwdReboot) {
					mini.alert("两次输入的密码不相同，请重新输入!");
					
					loginPwd.setValue("");
					loginPwdReboot.setValue("");
					
					return ;
				}
				
				$.ajax({
					url : "${pageContext.request.contextPath}/user/update_password",
					dataType: 'json',
					type : 'post',
					cache : false,
					data: o,
					success : function(text) {
						CloseWindow("update");
					}
				});
			}

			////////////////////
			//标准方法接口定义
			function SetData(data) {
				data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
				
				if(data.action == "updatePwd") {
					$.ajax({
						url : "${pageContext.request.contextPath}/user/get_login_user",
						dataType: 'json',
						success : function(text) {
							var o = mini.decode(text);
							if(o) {
								o.loginPwd = "";
								mini.get("loginPwd").focus();
								form.setData(o);
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
