<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>资源管理</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>

		<style type="text/css">
			html, body,form {
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
		<form id="edit-form1" method="post" style="height:98%; overflow:auto;">
			<input name="id" class="mini-hidden" />
			<input name="idNotIn" id="idNotIn" class="mini-hidden" />
			
			<div style="padding:4px;padding-bottom:4px;">
				<fieldset style="border:solid 1px #aaa;padding:2px; margin-bottom:4px;">
		            <legend>资源信息</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td style="width:150px;">名称：</td>
								<td style="width:150px;">
								 	<input name="name" id="name" class="mini-textbox"  emptyText="请输入名称" required="true"/>
								</td>
								<td style="width:100px;">类型：</td>
								<td style="width:150px;">
									<input id="type" name="type"  class="mini-combobox" required="true" onvaluechanged="onChangedType" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=res_type"  />
								</td>
							</tr>
							
							<tr>
								<td>状态 </td>
								<td>
									<input id="status" name="status" required="true" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" />
								</td>
								<td>上级：</td>
								<td>
								 	<input id="pid" name="pid" class="mini-buttonedit" onbuttonclick="onButtonEdit" emptyText="上级可以为空"/>    
								</td>
							</tr>
							
							
							<tr>
								<td >编码：</td>
								<td > 
								 	<input id="code" name="code" class="mini-textbox" required="true" emptyText="请输入编码"/>
								</td>
								<td class="dataQueryPermission">编码值：</td>
								<td class="dataQueryPermission">
									<input name="value" id="value" class="mini-textbox" emptyText="请输入编码值"/>
								</td>
							</tr>
							
							<tr>

								<td class="dataQueryPermission">图标：</td>
								<td class="dataQueryPermission"> 
								 	<input name="icon" id="icon" class="mini-textbox"/>
								</td>
								<td>序号：</td>
								<td>
								 	<input name="sn" id="sn" class="mini-spinner" value="0" minValue="0" maxValue="999999999"  />
								</td>
							</tr>
							<tr>
								<td>URL地址：</td>
								<td colspan="3">
								 	<input name="url" id="url" class="mini-textbox" style="width:100%"  emptyText="请输入URL地址"/>
								</td>
							</tr>
							
							<tr class = "dataQueryPermissionValue">
								<td>上下文数据：</td>
								<td colspan="3"> 
								 	<input id="types" name="types" readonly="readonly"  multiSelect="true"  showClose="true" oncloseclick="onCloseClick" style="width:100%"  class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=res_def_context_data"  />
								</td>
							</tr>
							
							<tr class = "dataQueryPermissionValue">
								<td>权限值：</td>
								<td colspan="3"> 
								 	<input id="dataQueryPermissionValue" name="dataQueryPermissionValue" class="mini-textarea" emptyText="请输入权限值" style="width:100%;height: 100px;"/>
								</td>
							</tr>
							
							<tr>
								<td>备注：</td>
								<td colspan="3"> 
								 	<input id="remark" name="remark" class="mini-textarea" emptyText="请输入备注" style="width:100%"/>
								</td>
							</tr>
				        </table>
				    </div>
				</fieldset>
			</div>
			<div id="subbtn" style="text-align:center;padding:5px;">
				<a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>
			</div>
		</form>
		<script type="text/javascript">
			mini.parse();

			var form = new mini.Form("edit-form1");
		
			function onChangedType(e) {
				var sender = e.sender;
				var value = sender.getValue();
				if(value == 300) {
					$(".dataQueryPermission").hide();
					$(".dataQueryPermissionValue").show();
					mini.get("types").setValue("0,1");
				}else {
					$(".dataQueryPermission").show();
					$(".dataQueryPermissionValue").hide();
					
				}
			}
	        
			function onCloseClick(e) {
	            var obj = e.sender;
	            obj.setText("");
	            obj.setValue("");
	        }
	        
	        function onButtonEdit(e) {
	            var btnEdit = this;
	            var idNotIn = mini.get("idNotIn").getValue();
	           // alert(idNotIn);
	            mini.open({
	                url: "${pageContext.request.contextPath}/apps/default/admin/uum/res/selector_res.jsp?idNotIn="+idNotIn,
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
	                            btnEdit.setValue(data.id);
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
				//mini.alert(mini.encode(form.getData()));
				//if(true) return ;
				
				if (o.type == 300) { //菜单 =100 页面元素 =200  数据查询条件=300 页面=400 链接=401
					o.value = mini.get("dataQueryPermissionValue").getValue();	
				}
				
				$.ajax({
					url : "${pageContext.request.contextPath}/res/save_or_update",
					dataType: 'json', type : 'post',
					data: o,
					success : function(text) {
						CloseWindow("save");
					},error : function(data) {
				  		mini.alert(data.responseText);
					}
				});
			}

			////////////////////
			//标准方法接口定义
			function SetData(data) {
				data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
				 if(data.action == "add" ) {
					 //alert(data.idNotIn);
					mini.get("idNotIn").setValue(data.idNotIn);
					mini.get("pid").setValue(data.pid);
					mini.get("pid").setText(data.pName);
					//alert(data.pid);
					return ; 
				 } 
				
				 if(data.action == "edit" || data.action=='view') {
					$.ajax({
						url : "${pageContext.request.contextPath}/res/get_by_id?id=" + data.id,
						dataType: 'json',
						success : function(text) {
							if(text) {
								var o = mini.decode(text);
								form.setData(o);
								
								mini.get("pid").setValue(o.pid);
								if(typeof(o.pName) != 'undefined') {
									mini.get("pid").setText(o.pName);
								}
								
								mini.get("idNotIn").setValue(o.id);
								
								if("300" == (o.type+"")) { 
									$(".dataQueryPermission").hide();
									$(".dataQueryPermissionValue").show();
									
									mini.get("dataQueryPermissionValue").setValue(o.value);
									mini.get("value").setValue("");
								}else {
									$(".dataQueryPermissionValue").hide();
								}
								
								mini.get("types").setValue("0,1"); // 
							}

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
