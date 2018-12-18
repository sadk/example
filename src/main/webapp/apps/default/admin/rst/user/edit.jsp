<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>注册用户</title>
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
			<input id="code" name="code" class="mini-hidden" />
			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>用户信息</legend>
		            <div style="padding:5px;">
				        <table>
				        
									<tr>
										<td>姓名：</td>
										<td>
											<input id="realName" name="realName"  class="mini-textbox"  emptyText="请输入真实姓名"  />
										</td>
										<td>昵称：</td>
										<td>
											<input id="nickName" name="nickName"  class="mini-textbox"  emptyText="请输入昵称"  />
										</td>
									</tr>
									
									<tr>
										<td>性别：</td>
										<td>
											<input id="sex" name="sex" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_sex_required" />
										</td>
										<td>生日：</td>
										<td>
											<input id="birthday" name="birthday" format="yyyy-MM-dd" class="mini-datepicker" emptyText="请输入生日" />
										</td>
									</tr>
									
									<tr>
										<td>手机：</td>
										<td>
											<input id="mobile" name="mobile"  class="mini-textbox"  emptyText="请输入手机"  />
										</td>
										<td>邮箱：</td>
										<td>
											<input id="email" name="email"  class="mini-textbox"  emptyText="请输入邮箱"  />
										</td>
									</tr>
								
									<tr>
										<td>坐席电话：</td>
										<td>
											<input id="seatNumber" name="seatNumber"  class="mini-textbox"  emptyText="请输入备注"  />
										</td>
										<td>学历：</td>
										<td>
											<input id="education" name="education"  class="mini-textbox"  emptyText="请输入学历"  />
										</td>
									</tr>
									
									<tr>
										<td>角色 ：</td>
										<td>
											<input id="roleName" name="roleName" class="mini-buttonedit" onbuttonclick="onButtonEditRoleName"/>
											<input id="roleCode" name="roleCode" class="mini-hidden" />
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
			
			function onValueChangedEntryStatus(e) {
				var obj = e.sender;
				var value = (obj.getValue());
				if("L1" == value) {
					mini.get("dependCompanyCode").setValue("");
					mini.get("dependCompanyName").setValue("");
				}
			}
			
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
							mini.get("dependCompanyCode").setValue(data.code);
							mini.get("dependCompanyName").setValue(data.fullName);
							
						}
					}
				});	
	      	}
	      	
			function onButtonEditRoleName() {
				var btnEdit = this;
	            mini.open({
	                url: "${pageContext.request.contextPath}/apps/default/admin/rst/role/selector_role.jsp",
	                title: "选择角色",
	                width: 500,  height: 400,
	                ondestroy: function (action) {
	                    if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var data = iframe.contentWindow.GetData();
	                        if(data) {
	                        	data = mini.clone(data);
		                        btnEdit.setValue(data.name);
		                        btnEdit.setText(data.name);
		                        
		                        mini.get("roleCode").setValue(data.code);
	                        }
	                    }
	                }
	            });
			}
			
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				
				o.birthday = mini.get("birthday").text;
				console.log(o.dependCompanyCode)
				if(o.entryStatus == 'P5' && o.dependCompanyCode == '') {
					mini.alert("请选择入职的企业");
					return ;
				}
				
				$.ajax({
					url : "${pageContext.request.contextPath}/rst/user/save_or_update_short",
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
						url : "${pageContext.request.contextPath}/rst/user/get_by_id?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							if (text) {
								var o = mini.decode(text);
								form.setData(o);
								
								mini.get("roleName").setValue(o.roleName);
								mini.get("roleName").setText(o.roleName);
								
								mini.get("roleCode").setValue(o.roleCode);
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
