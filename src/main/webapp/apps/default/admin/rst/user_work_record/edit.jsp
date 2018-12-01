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
			<input id="id" name="id" class="mini-hidden" />
			<input id="weekday" name="weekday" class="mini-hidden" />
			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>考勤日期</legend>
		            <div style="padding:5px;">
				        <table>
				        	<tr>
				        		<td>考勤日期：</td>
				        		<td>
				        			<input id="recordDate" name="recordDate" style="width:140px"  class="mini-datepicker"   format="yyyyMMdd" required="true"/>
				        		</td>
				        		<td>正常工时：</td>
				        		<td>
				        			<input id="workingHours" name="workingHours" style="width:140px" class="mini-spinner" value="0" minValue="0" maxValue="8"/>
				        		</td>
				        	</tr>
				        </table>
				    </div>
				</fieldset>
			
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>加班工时</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td>用户姓名:</td>
								<td>
									<input id="userName" name="userName" onclick="selectUser()"  style="width:140px" class="mini-textbox"  emptyText="请选择用户"/>
								</td>
								<td>用户编码:</td>
								<td>
									<input id="userCode" name="userCode"  style="width:140px" class="mini-textbox"  emptyText="请输入"  readonly="readonly" required="true"/>
								</td>
							</tr>
							
							<tr>
									<%-- 
									<td>记录类型:</td>
									<td>
										<input id="type" name="type" style="width:140px" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dic_kaoqin_type&enable=1" />
									</td>
									 --%>
									<td>加班时长(h):</td>
									<td>
										<input id="extraHours" name="extraHours"  style="width:140px" class="mini-textbox"  emptyText="小时数,保留一个小数"/>
									</td>
									<td>上班类型:</td>
									<td>
										<input id="extraShiftType" name="extraShiftType" style="width:140px" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dic_shift_type_bc" />
									</td>
							</tr>
				        </table>
				    </div>
				</fieldset>
				
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>请假工时</legend>
		            <div style="padding:5px;">
				        <table>
				        	<tr>
				        		<td>请假时长(h):</td>
								<td>
									<input id="leaveHours" name="leaveHours"  style="width:140px" class="mini-textbox"  emptyText="小时数,保留一个小数"/>
								</td>
								<td>请假类型:</td>
								<td>
									<input id="leaveType" name="leaveType" style="width:140px" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dic_leave_type" />
								</td>
				        	</tr>
							<tr>
								<td>请假班次:</td>
								<td>
									<input id="leaveShiftType" name="leaveShiftType" style="width:140px" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dic_shift_type_bc" />
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
		
			function selectUser() {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/rst/user/selector_user.jsp?multiSelect=false",
					title : "编辑",
					width : 490,
					height : 450,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {};
						
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.GetData();
						if (data) {
							data = mini.clone(data);
							mini.get("userName").setValue(data.realName);
							mini.get("userCode").setValue(data.code);
						}
					}
				});
			}
			
			function SaveData() {
				var o = form.getData();
				
				o.recordDate = mini.get("recordDate").text  ;
				mini.get("recordDate").setValue(o.recordDate+"");
				
				form.validate();
				if(form.isValid() == false) return;
				
				
				
				if (parseInt(o.extraHours) > 24) {
					mini.alert("加班时长不能超过24小时");
					return ;
				}
				if (parseInt(o.leaveHours) > 24) {
					mini.alert("请假时长不能超过24小时");
					return ;
				}
				
				
				//console.log(o)
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
								o.recoredDate = o.recordDate+"";
								form.setData(o);
								
								mini.get("recordDate").setText(o.recoredDate);
								//mini.get("recordDate").setValue(o.recordDate);
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
