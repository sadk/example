<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>工时记录</title>
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
			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>工时信息</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td>用户姓名:</td>
								<td>
									<input id="userName" name="userName"  style="width:140px" class="mini-textbox"  emptyText="请输入"   />
								</td>
								<td>用户编码:</td>
								<td>
									<input id="userCode" name="userCode"  style="width:140px" class="mini-textbox"  emptyText="请输入"/>
								</td>
							</tr>
							<tr>
								<tr>
									<td>考勤类型:</td>
									<td>
										<input id="type" name="type" style="width:140px" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dic_kaoqin_type" />
									</td>
									
								</tr>
							</tr>
							<tr>
								<td>工时:</td>
								<td>
									<input id="workingHours" name="workingHours"  style="width:140px" class="mini-textbox"  emptyText="小时数,保留一个小数" required="true"/>
								</td>
								<td>班次类型:</td>
								<td>
									<input id="shiftType" name="shiftType" style="width:140px" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dic_shift_type_bc" />
								</td>
							</tr>
							<tr>
								<td>请假类型:</td>
								<td>
									<input id="leaveType" name="leaveType" style="width:140px" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dic_leave_type" />
								</td>
								<td>请假原因:</td>
								<td>
									<input id="remark" name="remark"  style="width:140px" class="mini-textbox"  emptyText="请输入请假原因"  />
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
				console.log(o)
				$.ajax({
					url : "${pageContext.request.contextPath}/rst/user_work_record/save_or_update",
					dataType: 'json', type : 'post',
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
				//console.log(data)
				 if(data.action == "edit") {
					$.ajax({
						url : "${pageContext.request.contextPath}/rst/user_work_record/get_by_id?id=" + data.id,
						dataType: 'json',
						success : function(text) {
							if(text) {
								var o = mini.decode(text);
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
