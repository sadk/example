<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>合同模板管理</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
		<!-- -->
		<script src="${pageContext.request.contextPath}/scripts/upload/swfupload.js" type="text/javascript"></script>
		
		
		<style type="text/css">
			html, body {
				font-size: 12px;
				padding: 0;
				margin: 0;
				border: 0;
				height: 100%;
				overflow: hidden;
			}
			
			fieldset {
				border: solid 1px #aaa;
			}
			
			.hideFieldset {
				border-left: 0;
				border-right: 0;
				border-bottom: 0;
			}
			
			.hideFieldset .fieldset-body {
				display: none;
			}
</style>
	</head>
	<body> 
		<form id="edit-form1" method="post" style="height:97%; overflow:auto;">
			<input name="id" class="mini-hidden" />
			<input name="code" class="mini-hidden" />
			<div style="padding:4px;padding-bottom:4px;">
				<fieldset style="border:solid 1px #aaa;padding:2px; margin-bottom:4px;" id="fd1">
		            <legend><label><input type="checkbox" checked id="checkbox1" onclick="toggleFieldSet(this, 'fd1')" hideFocus/>基本信息</label></legend>
		            <div style="padding:4px;" class="fieldset-body">
				        <table>
							<tr>
								<td style="width:100px;">名称：</td>
								<td style="width:150px;">
								 	<input name="name" id="name" class="mini-textbox"  required="true" emptyText="请录入合同名称"/>
								</td>
								<td style="width:100px;">是否启用：</td>
								<td style="width:150px;">
									<input id="enable" name="enable" class="mini-combobox"  url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" textField="name" valueField="value" showNullItem="true" nullItemText="请选择..." emptyText="请选择" required="true"/>
								</td>
								<td style="width:100px;">所属企业：</td>
								<td style="width:150px;">
								 	<input name="companyName" id="companyName" class="mini-textbox" onclick="selectCompany()" emptyText="请选择公司..."  />
								 	<input name="companyId" id="companyId" class="mini-hidden"  />
								</td>
							</tr>
							<!-- 
							<tr>
								<td>备注 ：</td>
								<td colspan="5">
									<input name="remark" id="remark" class="mini-textarea" style="width: 100%" />
								</td>
							</tr>
							 -->
				        </table>
				    </div>
				</fieldset>
				
				<fieldset style="border:solid 1px #aaa;padding:4px; margin-bottom:2px;" id="fd2">
		            <legend><label><input type="checkbox" checked id="checkbox2" onclick="toggleFieldSet(this, 'fd2')" hideFocus/>模板内容</label></legend>
		            <div style="padding:4px;" class="fieldset-body">
						 
				   		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/ueditor/ueditor.config.js"></script>
					    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/ueditor/ueditor.all.min.js"> </script>
					    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/ueditor/lang/zh-cn/zh-cn.js"></script>
				   
					    <!-- 加载编辑器的容器 -->
					    <script id="content" name="content" type="text/plain" style="width:764px;height:300px"></script>
					    <!-- 实例化编辑器 -->
					    <script type="text/javascript">
					        var ue = UE.getEditor('content');
					    </script>
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
			
			var companyId = mini.get("companyId");
			var companyName = mini.get("companyName");
			
			function toggleFieldSet(ck, id) {
	            var dom = document.getElementById(id);
	            dom.className = !ck.checked ? "hideFieldset" : "";
	        }
			
			function selectCompany() {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/rst/company/selector_company.jsp",
					title : "企业选择",
					width : 800,
					height : 640,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {};
						data.action = action;
						
						if('edit' == action) {
							data.id = row.id;
						}
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						if(action == 'ok') {
							var iframe = this.getIFrameEl();
							var data = iframe.contentWindow.GetData();
							if(data) {
								companyName.setValue(data.shortName);
								companyId.setValue(data.code);
							}
						}
					}
				});
			}
			
			function SaveData() {
				var data = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				
				data.content = ue.getContent();
				
				$.ajax({
					url : "${pageContext.request.contextPath}/rst/contract_template/save_or_update",
					dataType: 'json', type : 'post',
					data: data,
					success : function(text) {
						CloseWindow("save");
					}
				});
			}

			function loadCompanyByCode(code,callback) {
				$.ajax({
					url : "${pageContext.request.contextPath}/rst/company/get_by_code?code=" + code,
					dataType: 'json',
					cache : false,
					success : function(text) {
						if(text) {
							var o = mini.decode(text);
							callback(o);
						}
					}
				});
			}
			////////////////////
			//标准方法接口定义
			function SetData(data) {
				data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
				
				 if(data.action == "edit") {
					$.ajax({
						url : "${pageContext.request.contextPath}/rst/contract_template/get_by_id?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							if(text) {
								var o = mini.decode(text);
								form.setData(o);
								
								loadCompanyByCode(o.companyId,function(data){
									companyName.setValue(data.shortName);
								});
								
								ue.setContent(o.content)
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
