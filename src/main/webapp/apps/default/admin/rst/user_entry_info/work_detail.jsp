<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>在职用户详情</title>
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
			<div style="padding:4px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:2px; margin-bottom:5px;">
		            <legend>基本信息</legend>
		            <div style="padding:5px;">
				        <table>
									<tr>
										<td>姓名：</td>
										<td>
											<input id="userName" name="userName"  class="mini-textbox"  emptyText="" style="width: 140px;" />
										</td>
										<td>昵称：&nbsp;&nbsp;&nbsp;&nbsp;	</td>
										<td>
											<input id="nickName" name="nickName"  class="mini-textbox"  emptyText="" style="width: 140px;" />
										</td>
									</tr>
									
									<tr>
										<td>性别：</td>
										<td>
											<input id="sex" name="sex" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_sex_required" style="width: 140px;"/>
										</td>
										<td>生日：</td>
										<td>
											<input id="birthday" name="birthday" format="yyyy-MM-dd" class="mini-datepicker" emptyText="" style="width: 140px;" />
										</td>
									</tr>
									
									<tr>
										<td>手机：</td>
										<td>
											<input id="mobile" name="mobile"  class="mini-textbox"  emptyText="" style="width: 140px;" />
										</td>
										<td>邮箱：</td>
										<td>
											<input id="email" name="email"  class="mini-textbox"  emptyText="" style="width: 140px;" />
										</td>
									</tr>
									
									<tr>
										<td>坐席电话：</td>
										<td>
											<input id="seatNumber" name="seatNumber"  class="mini-textbox"  emptyText="" style="width: 140px;" />
										</td>
										<td>学历：</td>
										<td>
											<input id="education" name="education"  class="mini-textbox"  emptyText="" style="width: 140px;" />
										</td>
									</tr>
									
									<tr>
										<td>角色 ：</td>
										<td>
											<input id="roleName" name="roleName" class="mini-buttonedit"   style="width: 140px;"/>
											<input id="roleCode" name="roleCode" class="mini-hidden" />
										</td>
									</tr>
									
				        </table>
				    </div>
				</fieldset>
				
				
				<fieldset style="border:solid 1px #aaa;padding:2px; margin-bottom:5px;">
		            <legend>账户信息</legend>
		            <div style="padding:4px;">
				        <table>
									<tr>
										<td>银行卡号：</td>
										<td>
											<input id="bankCard" name="bankCard"  class="mini-textbox"  emptyText="" style="width: 140px;" />
										</td>
										<td>开户银行：</td>
										<td>
											<input id="bankCardName" name="bankCardName"  class="mini-textbox"  emptyText="" style="width: 140px;" />
										</td>
									</tr>
									<tr>
										<td>身份证号码：</td>
										<td colspan="3">
											<input id="idCard" name="idCard"  class="mini-textbox" style="width: 100%"/>
										</td>
									</tr>
				        </table>
				    </div>
				</fieldset>
				
				<fieldset style="border:solid 1px #aaa;padding:2px; margin-bottom:5px;">
		            <legend>工作信息</legend>
		            <div style="padding:4px;">
				        <table>
							<tr>
								<td>是否在职：</td>
								<td>
									<input id="entryStatus" name="entryStatus" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_jianli_tech_entry_status_new"  style="width:140px"/>
								</td>
								<td>入驻企业：</td>
								<td>
									<input id="companyName" name="companyName"  class="mini-textbox"  emptyText="" style="width: 140px;" />
								</td>
							</tr>
							<tr>
								<td>入职日期：</td>
								<td>
									<input id="entryTime" name="entryTime"  class="mini-textbox"  emptyText=""  style="width: 140px;"/>
								</td>
								<td>离职日期：</td>
								<td>
									<input id="leaveTime" name="leaveTime"  class="mini-textbox"  emptyText=""  style="width: 140px;"/>
								</td>
							</tr>
							
							<tr>
								<td>紧急联系人：</td>
								<td>
									<input id="contactor" name="contactor"  class="mini-textbox"  emptyText="" style="width: 140px;" />
								</td>
								<td>紧急联系人电话：</td>
								<td>
									<input id="contactorPhone" name="contactorPhone"  class="mini-textbox"  emptyText="" style="width: 140px;" />
								</td>
							</tr>
				        </table>
				    </div>
				</fieldset>
				
				<!-- 
				<fieldset style="border:solid 1px #aaa;padding:2px; margin-bottom:5px;">
		            <legend>入驻工厂记录</legend>
		            <div style="padding:4px;">
				        <table>
							<tr>
								<td>工厂名称：</td>
								<td><input id="dfdsaf" name="nickNasdfsafme"  class="mini-textbox"  emptyText="请输入昵称"  style="width: 140px;"/></td>
								<td>时间段：</td>
								<td>2018-10-20至2018-10-21</td>
							</tr>
						</table>
				    </div>
				</fieldset>
				 -->
				
			</div>
			<div id="subbtn" style="text-align:center;padding:10px;">
				<!-- <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a> -->
				<a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>
			</div>
		</form>
		<script type="text/javascript">
			mini.parse();

			var form = new mini.Form("edit-form1");
  

			////////////////////
			//标准方法接口定义
			function SetData(data) {
				data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
				console.log(data)
				loadUserInfo(data.userCode,function(text){
					var o = mini.decode(text);
					form.setData(o);
					mini.get("nickName").setValue(o.nickName);
					mini.get("userName").setValue(o.realName);
					
					mini.get("roleName").setValue(o.roleName);
					mini.get("roleName").setText(o.roleName);
					
					mini.get("roleCode").setValue(o.roleCode);
				})
				
				loadUserEntryInfo(data.userCode,function(data){
					var o = mini.decode(data);
					mini.get("bankCard").setValue(o.bankCard);
					mini.get("bankCardName").setValue(o.bankCardName);
					mini.get("contactor").setValue(o.contactor);
					mini.get("contactorPhone").setValue(o.contactorPhone);
					mini.get("entryStatus").setValue(o.entryStatus);
					mini.get("entryTime").setValue(o.entryTime);
					mini.get("companyName").setValue(o.companyName);
					
				})
			}

			function loadUserInfo(userCode,callback) { // 注册表的用户信息
				$.ajax({ 
					url : "${pageContext.request.contextPath}/rst/user/get_by_code?code=" + userCode,
					dataType: 'json',
					success : function(text) {
						if (text) {
							callback(text);
						}
					}
				});
			}
			
			function loadUserEntryInfo(userCode,callback) { // 入职表的用户信息
				$.ajax({ 
					url : "${pageContext.request.contextPath}/rst/user_entry_info/get_by_user_code?userCode=" + userCode,
					dataType: 'json',
					success : function(text) {
						if (text) {
							callback(text);
						}
					}
				});
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
