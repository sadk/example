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
			<input id="id" name="id" class="mini-hidden" />
			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>用户信息</legend>
		            <div style="padding:5px;">
				        <table>
				        
									<tr>
										<td>姓名：</td>
										<td>
											<input id="userId" name="userId"  class="mini-hidden"  onenter="search"/>
											<input id="userName" name="userName"  class="mini-textbox"  emptyText="请输入姓名"  onenter="search"/>
										</td>
										<td>性别：</td>
										<td>
											<input id="sex" name="sex" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" data="[{name:'男',value:1}]" />
										</td>
									</tr>
									
									
									
									<tr>
										<td>帐号：</td>
										<td>
											<input id="loginName" name="loginName"  class="mini-textbox"  emptyText="请输入帐号"  onenter="search"/>
										</td>
										<td>密码：</td>
										<td>
											<input id="loginPwd" name="loginPwd"  class="mini-textbox"  emptyText="请输入密码"  onenter="search"/>
										</td>
									</tr>
									
									
									
									 <tr>
										<td>生日：</td>
										<td>
											<input id="birthday" name="birthday" format="yyyy-MM-dd" class="mini-datepicker" emptyText="请输入生日" />
										</td>
										<td>电话：</td>
										<td>
											<input id="tel" name="tel"  class="mini-textbox"  emptyText="请输入电话"  onenter="search"/>
										</td>
									</tr>
									
									
									<tr>
										<td>手机：</td>
										<td>
											<input id="mobile" name="mobile"  class="mini-textbox"  emptyText="请输入手机"  onenter="search"/>
										</td>
										<td>邮箱：</td>
										<td>
											<input id="email" name="email"  class="mini-textbox"  emptyText="请输入邮箱"  onenter="search"/>
										</td>
									</tr>
									
									
									
								
									
									
									
									
									
									
									
								 
									
									
									
									
									
									
									<tr>
										<td>状态 ：</td>
										<td>
											<input id="status" name="status" value="1" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=user_status" />
										</td>
										<td>序号：</td>
										<td>
											<input name="sn" id="sn" class="mini-spinner" class="mini-datepicker" minValue="0" maxValue="999999999"  />
										</td>
									</tr>
									
									
									<tr>
										<td>序号:</td>
										<td>
											<input name="sn" id="sn" class="mini-spinner" value="0" minValue="0" maxValue="999999999"  />
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
				
				o.birthday = mini.get("birthday").text;
				
				//alert(o.birthday);
				//alert(o.loginPwd);
				$.ajax({
					url : "${pageContext.request.contextPath}/user/save_or_update",
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
				
				 if(data.action == "edit" || data.action=='view') {
					$.ajax({
						url : "${pageContext.request.contextPath}/user/page?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							if(o!=null && o.data!=null && o.data.length>0) {
								o = o.data[0];
							}
							form.setData(o);
							form.setChanged(false);
							//alert(o.birthday);
							if (data.action == 'view') {
								//form.setEnabled(false);
							}
							
							if(data.action == 'view') {
								var fields = form.getFields();                
					            for (var i = 0, l = fields.length; i < l; i++) {
					                var c = fields[i];
					                if (c.setReadOnly) c.setReadOnly(true);     //只读
					                if (c.setIsValid) c.setIsValid(true);      //去除错误提示
					                if (c.addCls) c.addCls("asLabel");          //增加asLabel外观
					            }
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
