<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>接口分类管理</title>
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
			<input name="pid" id="pid" class="mini-hidden" />
			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>类别信息</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td style="width:100px;">分类名称：</td>
								<td style="width:150px;">
								 	<input name="name" id="name" class="mini-textbox"  />
								</td>
								<td style="width:100px;">分类值：</td>
								<td style="width:150px;">
									<input name="value" id="value" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>编码：</td>
								<td>
								 	<input name="code" id="code" class="mini-textbox" />
								</td>
								<td>序号：</td>
								<td>
								 	<input name="sn" id="sn" class="mini-spinner" value="0" minValue="0" maxValue="999999999"  />
								</td>
							</tr>
							<tr>
								<td>分类维度：</td>
								<td>
								 	<input id="categoryCode" name="categoryCode" class="mini-buttonedit" onbuttonclick="onButtonEdit" />    
									<input name="categoryName" id="categoryName" class="mini-hidden" />
								</td>
								<td>所属系统：</td>
								<td>
								 	<input id="appCode" name="appCode" class="mini-combobox" style="width:150px"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/application/all" />
								</td>
							</tr> 
							<tr>
								<!-- 
								<td>数据类型码：</td>
								<td> 
								 	<input id="dataType" name="dataType" class="mini-combobox" style="width:150px"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" data="[{name:'流程定义分类',code:'1'},{name:'用户规则分类',code:'2'}]" />
								</td>
								 -->
								<td>备注：</td>
								<td colspan="3"> 
								 	<input id="remark" name="remark" class="mini-textbox" width="100%"/>
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
	                            mini.get("categoryName").setValue(data.name);
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
					url : "${pageContext.request.contextPath}/api/category/save_or_update",
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
					 
				 if(data.action == "edit") {
					$.ajax({
						url : "${pageContext.request.contextPath}/api/category/get_by_id?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							if(o!=null) {
								form.setData(o);
								mini.get("categoryCode").setText(o.categoryName);
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
