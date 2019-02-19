<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>字典管理</title>
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
			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>类别信息</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td style="width:100px;">字典名称：</td>
								<td style="width:150px;">
								 	<input name="name" id="name" class="mini-textbox"  />
								</td>
								<td style="width:100px;">字典值：</td>
								<td style="width:150px;">
									<input name="value" id="value" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>字典编码：</td>
								<td>
								 	<input name="code" id="code" class="mini-textbox" />
								</td>
								<td>序号：</td>
								<td>
								 	<input name="sn" id="sn" class="mini-spinner" value="0" minValue="0" maxValue="999999999"  />
								</td>
							</tr>
							<tr>
								<td>所属类别：</td>
								<td>
								 	<input id="categoryCode" name="categoryCode" class="mini-buttonedit" onbuttonclick="onButtonEdit" />    
								</td>
								<td>所属系统：</td>
								<td>
								 	<input id="appCode" name="appCode" class="mini-combobox" style="width:150px"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/application/all" />
								</td>
							</tr> 
							<tr>
								<td>启用/禁用：</td>
								<td>
								 	<input id="enable" name="enable" class="mini-combobox" style="width:150px"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" />
								</td>
								<td>上级：</td>
								<td>
								 	<input id="pid" name="pid" class="mini-buttonedit" onbuttonclick="onButtonEditParent" />    
								</td>
							</tr>
							<tr>
								<td>备注：</td>
								<td> 
								 	<input id="remark" name="remark" class="mini-textbox"/>
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
	                url: "${pageContext.request.contextPath}/apps/default/admin/sys/category/seletor_category.jsp",
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
	        
			function onButtonEditParent() { //字典上级选择器
				var btnEdit = this;
	            mini.open({
	                url: "${pageContext.request.contextPath}/apps/default/admin/sys/dictionary/seletor_dictionary.jsp",
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
	                        	
	                        	/*上级不能是自己,会造成死循环
	                        	var idCtl = mini.get("id");
	                        	if(data.id == idCtl.value) {
	                        		mini.alert("上级ID不能是自己");
	                        		return ;
	                        	}*/
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
				$.ajax({
					url : "${pageContext.request.contextPath}/dictionary/save_or_update",
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
				 if(data.action == "add" ) {
					mini.get("pid").setValue(data.pid);
					return ; 
				 } 
					 
				 if(data.action == "edit" || data.action=='view') {
					$.ajax({
						url : "${pageContext.request.contextPath}/dictionary/page?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							if(o!=null && o.data!=null && o.data.length>0) {
								o = o.data[0];
							}
							form.setData(o);
							form.setChanged(false);
							
							if (data.action == 'view') {
								form.setEnabled(false);
							}
							
							loadCategroyName(o.categoryCode);
							
							loadParentName(o.pid);
						}
					});
				}
			}
			
			function loadCategroyName(code){
				$.ajax({
					url : "${pageContext.request.contextPath}/category/page?code=" + code,
					dataType: 'json',
					cache : false,
					success : function(text) {
						var o = mini.decode(text);
						if(o!=null && o.data!=null && o.data.length>0) {
							o = o.data[0];
							mini.get("categoryCode").setText(o.name);
						}
					}
				});
			}
			
			function loadParentName(id){
				if(typeof(id) == 'undefined' || id == null || id == '') {
					return ;
				}
				$.ajax({
					url : "${pageContext.request.contextPath}/dictionary/get_by_id?id="+id,
					dataType: 'json',
					cache : false,
					success : function(text) {
						if(text) {
							var o = mini.decode(text);
							mini.get("pid").setText(o.name);
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
