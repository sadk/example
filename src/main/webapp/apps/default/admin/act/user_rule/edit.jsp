<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户规则编辑</title>
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
			<input name="id" id="id" class="mini-hidden" />

			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>规则信息</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td style="width:100px;">名称：</td>
								<td style="width:150px;">
								 	<input name="name" id="name" class="mini-textbox"  emptyText="请输入名称" required="true"/>
								</td>
								<td style="width:100px;">编码：</td>
								<td style="width:150px;">
									<input name="code" id="code" class="mini-textbox"  emptyText="请输入名称"/>
								</td>
							</tr>
							<tr>
								<td>解析类型：</td>
								<td>
								 	<input name="resolveType" id="resolveType" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" data="[{name:'Freemark',value:'1'},{name:'velocity',value:'2'},{name:'javascript',value:'3'},{name:'groovy',value:'4'}]" required="true"/>   
								</td>
								<td>分类:</td>
								<td>
								 	<input id="categoryId" name="categoryId" class="mini-buttonedit" onbuttonclick="onButtonEdit" emptyText="上级部门可以为空" required="true"/>    
								</td>
							</tr>
							<tr>
								<td>备注：</td>
								<td> 
								 	<input id="remark" name="remark" class="mini-textbox" emptyText="请输入备注"/>
								</td>
								<td>内容类型:</td>
								<td>
									<input name="contentType" id="contentType" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" data="[{name:'静态文本',value:'0'},{name:'SQL语句',value:'1'},{name:'JavaScript片断',value:'2'},{name:'Groovy片断',value:'3'}]" required="true"/>   
								</td>
							</tr>
							<!-- <tr><td colspan="4">&nbsp;</td></tr> -->
							<tr>
								<td>规则内容:<br>(返回用户ID集)</br></td>
								<td colspan="3">
									 <input id="content" name="content"  style="width: 100%;height: 150px;" class="mini-textarea" required="true"/>
								</td>
							</tr>
							<tr>
								<td>规则内置变量:</td>
								<td colspan="3" style="color: blue;">登陆用户上下文 \${loginUser}、填制人部门\${createDeptId}、 工具类\${StringUtil}、\${ArrayUtil}</td>
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
	                url: "${pageContext.request.contextPath}/apps/default/admin/act/category/seletor_category.jsp?dataType=2",
	                title: "选择分类",
	                width: 800,
	                height: 500,
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
				 
				$.ajax({
					url : "${pageContext.request.contextPath}/act/user_rule/save_or_update",
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
						url : "${pageContext.request.contextPath}/act/user_rule/page?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							if(o!=null && o.data!=null && o.data.length>0) {
								o = o.data[0];
							}
							form.setData(o);
							form.setChanged(false);
							
						 
							
							mini.get("categoryId").setValue(o.categoryId);
							if(typeof(o.categoryName) != 'undefined') {
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
