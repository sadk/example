<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>登陆</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <style type="text/css">
   	 	body {width:100%;height:100%;margin:0;overflow:hidden;}
    </style>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
    
</head>
<body >   
<div id="loginWindow" class="mini-window" title="用户登录" style="width:300px;height:165px;" showModal="true" showCloseButton="false" >

    <div id="loginForm" style="padding:15px;padding-top:10px;">
        <table >
            <tr>
                <td style="width:60px;"><label for="username$text">帐号：</label></td>
                <td>
                    <input id="username" name="username" class="mini-textbox" required="true" style="width:150px;"/>
                </td>    
            </tr>
            <tr>
                <td style="width:60px;"><label for="pwd$text">密码：</label></td>
                <td>
                    <input id="password" name="password"  class="mini-password" required="true" style="width:150px;" onenter="onLoginClick"/>
                </td>
            </tr>            
            <tr>
                <td></td>
                <td style="padding-top:5px;">
                    <a onclick="onLoginClick" class="mini-button" style="width:60px;">登录</a>
                    <a onclick="onResetClick" class="mini-button" style="width:60px;">重置</a>
                </td>
            </tr>
        </table>
    </div>

</div>


    

    
    <script type="text/javascript">
        mini.parse();

        var username = mini.get("username");
        var password = mini.get("password");
        
        var loginWindow = mini.get("loginWindow");
        loginWindow.show();

        function onLoginClick(e) {
            var form = new mini.Form("#loginWindow");

            form.validate();
            if (form.isValid() == false) return;

            loginWindow.hide();
           var messageId = mini.loading("正在登录，马上转到系统...", "登录成功");
            
            
          //  setTimeout(function () {
	            $.ajax({
	                url: "${pageContext.request.contextPath}/user/login",
	                data:  form.getData(),
	                type: "post",
	                success: function (text) {
	                	mini.hideMessageBox(messageId);
	                	if(text) {
	                		//console.log(text);
	                		if(text.status == 0) {
	                			window.location = "${pageContext.request.contextPath}/apps/default/admin/index.jsp";
	                		}else {
	                			mini.alert(text.message,"登陆提示",function(){
	                				loginWindow.show();
	                				password.focus();
	                			});
	                		}
	                	}
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    mini.alert(jqXHR.responseText);
	                }
	            });
         //   }, 1000);
        }
        function onResetClick(e) {
            var form = new mini.Form("#loginWindow");
            form.clear();
        }
    </script>

</body>
</html>