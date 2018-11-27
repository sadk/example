<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>职位信息</title>
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
			<div style="padding:4px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>职位信息</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td style="width:100px;">发布平台：</td>
								<td style="width:150px;">
								 	<input name="publishPlatfrom" id="publishPlatfrom" class="mini-textbox"  />
								</td>
								<td style="width:100px;">公司简称：</td>
								<td style="width:150px;">
									<input name="companyShortName" id="companyShortName" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>中介姓名：</td>
								<td>
									<input name="intermediaryName" id="intermediaryName" class="mini-textbox" />
								</td>
								<td>中介手机号：</td>
								<td>
									<input name="intermediaryPhone" id="intermediaryPhone" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>综合工资：</td>
								<td>
									<input name="comprehensiveSalary" id="comprehensiveSalary" class="mini-textbox" />
								</td>
								<td>作息时间：</td>
								<td>
									<input name="workTime" id="workTime" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>岗位名称：</td>
								<td>
									<input name="name" id="name" class="mini-textbox" />
								</td>
								<td>面试地址：</td>
								<td>
									<input name="interviewAddress" id="interviewAddress" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>发布状态：</td>
								<td>
								 	<input id="status" name="status" class="mini-combobox"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" data='[{id:"1",text:"启用"},{id:"0",text:"禁用"}]' />
								</td>
								<td>排序号：</td>
								<td>
								 	<input name="sn" id="sn" class="mini-spinner" value="0" minValue="0" maxValue="999999999"  />
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
			
	        function onButtonEdit(e) {
	            var btnEdit = this;
	            mini.open({
	                url: "${pageContext.request.contextPath}/apps/default/admin/sys/tenant/selector_tenant.jsp",
	                title: "选择列表",
	                width: 650,
	                height: 380,
	                ondestroy: function (action) {
	                    //if (action == "close") return false;
	                    if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var data = iframe.contentWindow.GetData();
	                        data = mini.clone(data);    //必须
	                        if (data) {
	                            btnEdit.setValue(data.code);
	                            btnEdit.setText(data.name);
	                        }
	                    }

	                }
	            });
	        }
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				$.ajax({
					url : "${pageContext.request.contextPath}/application/save_or_update",
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
						url : "${pageContext.request.contextPath}/application/page?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							if(o!=null && o.data!=null && o.data.length>0) {
								o = o.data[0];
							}
							form.setData(o);
							form.setChanged(false);
							
							loadTenantName(o.tenantCode);
							
							if (data.action == 'view') {
								form.setEnabled(false);
							}
						}
					});
				}
			}

			function loadTenantName(code){
				$.ajax({
					url : "${pageContext.request.contextPath}/tenant/page?code=" + code,
					dataType: 'json',
					cache : false,
					success : function(text) {
						var o = mini.decode(text);
						if(o!=null && o.data!=null && o.data.length>0) {
							o = o.data[0];
							mini.get("tenantCode").setText(o.name);
							mini.get("tenantName").setValue(o.name);
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
