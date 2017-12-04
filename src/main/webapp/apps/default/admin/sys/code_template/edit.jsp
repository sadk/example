<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>代码模板信息管理</title>
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
			<div style="padding:4px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;" id="fd1">
		            <legend><label><input type="checkbox" checked id="checkbox1" onclick="toggleFieldSet(this, 'fd1')" hideFocus/>基本信息</label></legend>
		            <div style="padding:3px;" class="fieldset-body">
				        <table>
							<tr>
								<td style="width:90px;">名称：</td>
								<td style="width:200px;">
								 	<input name="name" id="name" class="mini-textbox" style="width:180px" required="true" />
								</td>
								<td style="width:90px;">编码：</td>
								<td style="width:200px;">
									<input name="code" id="code" class="mini-textbox" style="width:180px" required="true"/>
								</td>
							</tr>
							<tr>
								<td>所属工程类：</td>
								<td>
									<input id="projectCode" name="projectCode" style="width:180px" required="true" class="mini-combobox" readonly="readonly" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/project/all"/>
								</td>
								<td>模板类型：</td>
								<td>
									<input id="tmplType" name="tmplType" style="width:180px" required="true" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" />
								</td>
							</tr>
							<tr>
								<td>模板解析方式：</td>
								<td>
									<input id="tmplResolveType" style="width:180px" name="tmplResolveType" required="true" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=tmpl_resolve_type"/>
								</td>
								<td>备注：</td>
								<td>
									<input name="remark" style="width:180px" id="remark" class="mini-textbox"/>
								</td>
							</tr>
							<tr>
								<td>包名：</td>
								<td>
									<input id="pkg" name="pkg" style="width:180px" name="pkg" required="true"  class="mini-textbox"/>
								</td>
								<td>类名：</td>
								<td>
									<input id="clazz" name="clazz" style="width:180px" id="remark" class="mini-textbox"/>
								</td>
							</tr>
				        </table>
				    </div>
				</fieldset>
				
				
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;" id="fd2">
		           <legend><label><input type="checkbox" checked id="checkbox2" onclick="toggleFieldSet(this, 'fd2')" hideFocus/>模板内容</label></legend>
		            <div style="padding:5px;" class="fieldset-body">
		            	<input id="content" name="content" class="mini-textarea" required="true" style="width:100%;height:420px;"/>
		            	<!-- 
				        <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/ueditor/ueditor.config.js"></script>
					    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/ueditor/ueditor.all.min.js"> </script>
					    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/ueditor/lang/zh-cn/zh-cn.js"></script>
				   
					    <!-- 加载编辑器的容器 
					    <script id="content" name="content" type="text/plain" style="width:100%;height:320px;"></script>
					    <!-- 实例化编辑器 
					    <script type="text/javascript">
					        var ue = UE.getEditor('content');
					    </script>
					     -->
				    </div>
				</fieldset>
				
				
			</div>
			<div id="subbtn" style="text-align:center;padding:10px;">
				<a class="mini-button" onclick="onOk" id="btnOk" style="width:80px;margin-right:20px;"  iconCls="icon-ok" >确定</a>
				<a class="mini-button" onclick="onCancel" id="btnCancel" style="width:80px;"  iconCls="icon-cancel">取消</a>
			</div>
		</form>
		<script type="text/javascript">
			mini.parse();

			var form = new mini.Form("edit-form1");
			var tmplType = mini.get("tmplType");
			 
			
			var btnTest = mini.get("btnTest");
			var btnOk =  mini.get("btnOk");
			var btnCancel =  mini.get("btnCancel");
			
			function toggleFieldSet(ck, id) {
	            var dom = document.getElementById(id);
	            dom.className = !ck.checked ? "hideFieldset" : "";
	        }
			
			function onProjectChanged(e) {
				var dt = projectCodeCmbo.getData();
				for(var i=0;i<dt.length;i++) {
					//alert(e.value+"  "+dt[i].code +"   "+ ((""+e.value) == (""+dt[i].code)));
					if((""+e.value) == (""+dt[i].code)){
						projectCommentTxt.setValue(dt[i].remark);
						break;
					}
				}
			}
	
	     	
			function SaveData() {
				var o = form.getData();
				form.validate();
				/*
				var text = ue.getContentTxt();
				if(text ==''){
					mini.alert("模板内容不能为空");
					return ;
				}*/
				if(form.isValid() == false) return;
	
				var data = form.getData();
				//data.content = text;
				
				$.ajax({
					url : "${pageContext.request.contextPath}/code_template/save_or_update",
					dataType: 'json',
					type : 'post',
					cache : false,
					data: data,
					success : function(text) {
						CloseWindow("save");
					}
				});
			}
			
			
			////////////////////
			//标准方法接口定义
			function SetData(data) {
				data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
				
				//if(data.action == 'add') {
					/*
					-- ORO         0=Mybatis3_Mapper.xml 1=Hibernate3.hbm.xml 2=Example.ftl.sql.xml 
					-- Controller  5=SpringMVC_Controller.java 6=Struts2_Action.java 20=ExampleMVC_Controller.java 
					-- Dao         3=Dao_Mybatis3.java 11= Dao_Hibernate3.java 12=Dao_SpringJDBC.java 13=Dao_DBUtil.java
					-- Service     4=Service_Example.java  14=Service_Spring.java
					-- Model       15= Model.java
					-- Page        7=jsp 8=html 9=*.vm 10=*.ftl
					*/					
					var o = {projectCode:data.projectCode}
					form.setData(o);
					
					var url = "${pageContext.request.contextPath}/dictionary/all?parentCode=tmpl_type&values=";
					tmplType.load(url + data.tmplTypes);
					
				//}
				 if(data.action == "edit" || data.action=='view') {
					$.ajax({
						url : "${pageContext.request.contextPath}/code_template/page?id=" + data.id,
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
							
								btnOk.hide();
								//btnCancel.hide();
								btnCancel.setText("关闭");
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
