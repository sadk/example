<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>接口定义编辑</title>
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
		<form id="edit-form1" method="post" style="height:98%; overflow:auto;">
			<input name="id" class="mini-hidden" />
			<div style="padding-left:2px;padding-bottom:2px;">
				<fieldset style="border:solid 1px #aaa;padding:4px; margin-bottom:2px;">
		            <legend>缓存管理信息</legend>
		            <div style="padding:4px;">
				        <table>
							<tr>
								<td style="width:140px;">名称：</td>
								<td style="width:150px;">
								 	<input name="name" id="name" class="mini-textbox"  />
								</td>
								<td style="width:100px;">API类型：</td>
								<td style="width:150px;">
								 	<input id="type" name="type" class="mini-combobox" style="width:150px"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=api_system_api_type" />
								</td>
							</tr>
							<tr>
								<td>编码：</td>
								<td>
								 	<input name="code" id="code" class="mini-textbox" />
								</td>
								<td>编码值：</td>
								<td>
								 	<input name="value" id="value" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>URL：</td>
								<td>
								 	<input name="url" id="url" class="mini-textbox" />
								</td>
								<td>Http方法：</td>
								<td>
								 	<input id="method" name="method" class="mini-combobox" showNullItem="true" nullItemText="请求方式..." emptyText="请求方式" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=http_request_method"  />
								</td>
							</tr>
							<tr>
								<td>发送类型：</td>
								<td>
								 	<input id="sendType" name="sendType" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=api_message_send_type" />
								</td>
								<td>数据字段：</td>
								<td>
								 	<input name="dataProp" id="dataProp" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>请求实体：</td>
								<td>
								 	<input name="entityClassRequest" id="entityClassRequest" class="mini-textbox" />
								</td>
								<td>响应实体：</td>
								<td>
								 	<input name="entityClassResponse" id="entityClassResponse" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>超时时间(ms)：</td>
								<td>
								 	<input id="timeout" name="timeout"   class="mini-spinner"/>
								</td>
								
								<td>分类：</td>
								<td>
								 	<input id="categoryId" name="categoryId" class="mini-buttonedit" onbuttonclick="onButtonEdit" />    
									<input name="categoryName" id="categoryName" class="mini-hidden" />
								</td>
							</tr> 
							<tr>
								<td>所属系统：</td>
								<td>
								 	<input id="appCode" name="appCode" class="mini-combobox" style="width:150px"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/application/all" />
								</td>
								<td>排序号：</td>
								<td> 
								 	<input name="sn" id="sn" class="mini-spinner" value="0" minValue="0" maxValue="999999999"  />
								</td>
							</tr>
							<tr>
								<td>备注：</td>
								<td colspan="3"> 
								 	<input id="remark" name="remark" class="mini-textarea" style="width: 100%"/>
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
			var categoryName = mini.get("categoryName");
			
	        function onButtonEdit(e) {
	            var btnEdit = this;
	            mini.open({
	                url: "${pageContext.request.contextPath}/apps/default/admin/api/category/seletor_category.jsp",
	                title: "选择列表",
	                width: 650,
	                height: 380,
	                ondestroy: function (action) {
	                    //if (action == "close") return false;
	                    if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var data = iframe.contentWindow.GetData();
	                        data = mini.clone(data);    //必须
	                        //console.log(data)
	                        if (data) {
	                            btnEdit.setValue(data.id);
	                            btnEdit.setText(data.name);
	                            
	                            categoryName.setValue(data.name);
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
				$.ajax({
					url : "${pageContext.request.contextPath}/api/definition/save_or_update",
					dataType: 'json',
					type : 'post',
					cache : false,
					data: form.getData(),
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
				
					 
				 if(data.action == "edit") {
					$.ajax({
						url : "${pageContext.request.contextPath}/api/definition/get_by_id?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							if(typeof(text) != 'undifinied' && text && text!=null) {
								var o = mini.decode(text);
									form.setData(o);
									form.setChanged(false);
									mini.get("categoryId").setValue(o.categoryId);
									mini.get("categoryId").setText(o.categoryName);
									
									
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
