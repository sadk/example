<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>岗位属性管理</title>
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
								<td style="width:100px;">名称：</td>
								<td style="width:150px;">
								 	<input name="name" id="name" class="mini-textbox" required="true" />
								</td>
								<td style="width:100px;">编码：</td>
								<td style="width:150px;">
									<input name="code" id="code" class="mini-textbox" />
								</td>
							</tr>
 
							<tr>
								<td>编码值：</td>
								<td>
								 	<input id="value" name="value" class="mini-textbox" />    
								</td>
								<td>是否启用：</td>
								<td>
								 	<input id="enable" name="enable" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" required="true"/>
								</td>
							</tr>
							<tr>
								<td>所属分类：</td>
								<td>
									<input id="categoryId" name="categoryId" class="mini-buttonedit" onbuttonclick="onButtonEditCategory"/>
								</td>
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
		
	      	function onButtonEditCategory(e) {
	      		var edit = this;
	            mini.open({
	    			url : "${pageContext.request.contextPath}/apps/default/admin/rst/job_category/seletor_category.jsp?showCheckBox=false",
	    			title : "选择分类",
	    			width : 500,
	    			height : 420,
	    			onload : function() {
	    				var iframe = this.getIFrameEl();
	    				iframe.contentWindow.SetData(data);
	    			},
	    			ondestroy : function(action) {
	    				var iframe = this.getIFrameEl();
	    				var row = iframe.contentWindow.GetData();
	    				
	    				if(row) {
	    					row = mini.clone(row);
		    				edit.setText(row.name);
		    				edit.setValue(row.id);
	    				}
	    			}
	    		});
	      	}
			function SaveData() {
				var data = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				 
				$.ajax({
					url : "${pageContext.request.contextPath}/rst/job_category_attr/save_or_update",
					dataType: 'json', type : 'post',
					data: data,
					success : function(text) {
						CloseWindow("save");
					},error : function(data) {
				  		mini.alert(data.responseText);
					}
				});
			}

			function loadCategoryName(id){
				$.ajax({
					url : "${pageContext.request.contextPath}/rst/job_category/get_by_id?id="+id,
					dataType: 'json',
					cache : false,
					success : function(text) {
						if(text) {
							var o = mini.decode(text);
							mini.get("categoryId").setText(o.name);
						}
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
					 
				 if(data.action == "edit") {
					$.ajax({
						url : "${pageContext.request.contextPath}/rst/job_category_attr/get_by_id?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							if(!text){
								return;
							}
							var o = mini.decode(text);
							
							form.setData(o);
							form.setChanged(false);
							
							loadCategoryName(o.categoryId);
							 
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
