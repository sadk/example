<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>代码生成</title>
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
			<input name="tableId" class="mini-hidden" />
			<div style="padding:4px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;" id="fd1">
		            <legend><label><input type="checkbox" checked id="checkbox1" onclick="toggleFieldSet(this, 'fd1')" hideFocus/>基本信息</label></legend>
		            <div style="padding:3px;" class="fieldset-body">
				        <table>
							<tr>
								<td style="width:90px;">模板名称：</td>
								<td style="width:200px;">
								 	<input name="name" id="name" class="mini-textbox" style="width:180px" required="true" />
								</td>
								<td style="width:90px;">模板编码：</td>
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
								<td>数据库：</td>
								<td>
									<input id="dbName" name="dbName" style="width:180px" name="pkg" required="true"  class="mini-textbox"/>
								</td>
								<td>表名：</td>
								<td>
									<input id="tableName" name="tableName" style="width:180px" id="remark" class="mini-textbox"/>
								</td>
							</tr> 
				        </table>
				    </div>
				</fieldset>
				
	
				
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;" id="fd2">
		           <legend><label><input type="checkbox" checked id="checkbox2" onclick="toggleFieldSet(this, 'fd2')" hideFocus/>【模板/代码】 内容</label></legend>
		            <div style="padding:5px;" class="fieldset-body">
		            	
				        
				        <div id="tabs1" class="mini-tabs" activeIndex="0" plain="false">
						    
						    <div title="模板占位符" >
						    	<div class="mini-toolbar" style="border-bottom:0;padding:0px;" >
						            <table style="width:100%;">
						                <tr>
						                    <td style="width:100%;">
						                    	<a class="mini-button" iconCls="icon-download" onclick="impVariable()" plain="false">导入常用占位符</a>
						                    	<span class="separator"></span>
						                        <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="false">增加</a>
						                        <a class="mini-button" iconCls="icon-remove" onclick="removeRow()" plain="false">删除</a>
						                        <span class="separator"></span>
						                        <a class="mini-button" iconCls="icon-save"  plain="false" onclick="saveOrUpdateRow()">保存</a>
						                        <a class="mini-button" iconCls="icon-reload"  plain="false" onclick="holdGrid.reload()">刷新</a>            
						                    </td>
						                </tr>
						            </table>           
						        </div>
							    <div id="holdGrid" class="mini-datagrid" style="height:380px;"  showPager="false"  allowResize="true" 
							        allowCellEdit="true" allowCellSelect="true" multiSelect="true"   editNextOnEnterKey="true"  editNextRowCell="true" >
							        <div property="columns">
							            <div type="checkcolumn"></div>
							            <div field="name" headerAlign="center" allowSort="true">名称
							            	<input property="editor" class="mini-textbox" style="width:100%;" />
							            </div>
							            <div field="code" headerAlign="center" allowSort="true" >编码
							            	<input property="editor" class="mini-textbox" style="width:100%;" />
							            </div>
							            <div type="comboboxcolumn" field="valueType" headerAlign="center" allowSort="true">值解析类型
							            	<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=variable_value_type" />
							            </div>
							            <div field="value" headerAlign="center" allowSort="true" >变量值
							            	<input property="editor" class="mini-textbox" style="width:100%;" />
							            </div>
							            <div type="comboboxcolumn" field="valueResolveType" headerAlign="center" allowSort="true">解析类型
							            	<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=value_resolve_type" />
							            </div>
							            <div field="remark" headerAlign="center" allowSort="true">备注
							            	<input property="editor" class="mini-textbox" style="width:100%;" />
							            </div>
							            <!-- 
							            <div field="useTypeDesc" headerAlign="center" allowSort="true">类型</div> -->
							        </div>
							    </div>
						    </div>
						    
						    
						    
						    
						    
						    
						    
						    <div title="模板内容" >
						    	<div class="mini-toolbar" style="border-bottom:0;padding:0px;" >
						            <table style="width:100%;">
						                <tr>
						                    <td style="width:100%;">
						                        <a class="mini-button" iconCls="icon-save"  plain="false" onclick="saveOrUpdateTmpl()">保存模板</a>
						                    </td>
						                </tr>
						            </table>           
						        </div>
						        <input id="content" name="content" class="mini-textarea" required="true" style="width:100%;height:380px;"/>
						    </div>
						    
						    
						    
						    
						    
						    <div title="代码内容">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;" >
						            <table style="width:100%;">
						                <tr>
						                    <td style="width:100%;">
						                        <a class="mini-button" iconCls="icon-split"  plain="false" onclick="doCodegen()">生成代码</a>
						                        <a class="mini-button" iconCls="icon-save"  plain="false" onclick="saveCode()">保存代码</a>
						                    </td>
						                </tr>
						            </table>           
						        </div>
						        <input id="codeContent" name="codeContent" class="mini-textarea" required="true" style="width:100%;height:380px;"/>
						    </div>
						    
						    
						    
						    
						    
						    
						    <div title="代码生成日志">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;" >
						            <table style="width:100%;">
						                <tr>
						                    <td style="width:100%;">
						                        <a class="mini-button" iconCls="icon-cancel"  plain="false" onclick="">清空日志</a>
						                    </td>
						                </tr>
						            </table>           
						        </div>
						        <input id="logContent" name="logContent" class="mini-textarea" required="true" style="width:100%;height:380px;"/>
						    </div>
						</div>
		            	
		            	

				    </div>
				</fieldset>
				
				
			</div>
			<div id="subbtn" style="text-align:center;padding:5px;">
				<a class="mini-button" onclick="onCancel" id="btnCancel" style="width:80px;"  iconCls="icon-close">关闭</a>
			</div>
		</form>
		<script type="text/javascript">
			mini.parse();

			var form = new mini.Form("edit-form1");
			var tmplType = mini.get("tmplType");
			var holdGrid = mini.get("holdGrid");
			
			var tmplContent = mini.get("content");
			var logContent = mini.get("logContent");
			var codeContent = mini.get("codeContent");
			
			var dbName = mini.get("dbName");
			var tableName = mini.get("tableName");
			
			var btnOk =  mini.get("btnOk");
			var btnCancel =  mini.get("btnCancel");
			
			function toggleFieldSet(ck, id) {
	            var dom = document.getElementById(id);
	            dom.className = !ck.checked ? "hideFieldset" : "";
	        }
			
			function saveOrUpdateTmpl() {
				var data = {};
				data.id  = form.getData().id; // 模板ID
				data.content = form.getData().content;
				$.ajax({
					url : "${pageContext.request.contextPath}/code_template/save_template_content",
					dataType: 'json', type : 'post', cache : false,
					data: data,
					success : function(text) {
						mini.alert('保存成功');
					},
					error : function(data) {
				  		mini.alert(data.responseText);
					}
				});
			}
			
			function doCodegen(){
				var data = {};
				data.tmplId  = form.getData().id; // 模板ID
				data.tableId = form.getData().tableId; // 开发者表ID
				$.ajax({
					url : "${pageContext.request.contextPath}/code_template/do_codegen",
					dataType: 'json', type : 'post', cache : false,
					data: data,
					success : function(text) {
						if(text) {
							var o = mini.decode(text);
							
							codeContent.setValue(o.content);
							logContent.setValue(o.log); 
							
							if(text.status == '1'){
								mini.alert('代码生成成功');
								return ;
							}
						}
						mini.alert('代码生成失败');
					},
					error : function(data) {
				  		//mini.alert(data.responseText);
					}
				});
			}
			
			function  impVariable() {
				var objId = form.getData().id;
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/sys/variable/selector_variable.jsp?useType=3",
					title : "常用占位符说明",
					showModal: false,
					width : 600,
					height : 500,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : "view",
							id : form.id
						};
						//iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						if(action != 'ok'){
							return ;
						}
						
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.GetData();
						var data = mini.clone(data);
						if(!data){
							return ;
						}
						
						
						var dt = {};
						dt.jsonText = mini.encode(data);
						dt.objId = form.getData().id;
						
						$.ajax({
							url : "${pageContext.request.contextPath}/variable/imp_variable",
							dataType: 'json',
							type : 'post',
							cache : false,
							data: dt,
							success : function(text) {
								holdGrid.reload();
							}
						});
					}
				});
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
	
			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~占位符导入表格 begin: ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	        function addRow() {          
	            var newRow = { name: "New Row",code : "New Row" };
	            holdGrid.addRow(newRow);

	            holdGrid.beginEditCell(newRow, "LoginName");
	        }
	        function removeRow() {
	            var rows = holdGrid.getSelecteds();
	            if (rows.length > 0) {
	            	holdGrid.removeRows(rows, true);
	            }
	        }
	        
	        holdGrid.on("celleditenter", function (e) {
	            var index = holdGrid.indexOf(e.record);
	            if (index == holdGrid.getData().length - 1) {
	                var row = {};
	                holdGrid.addRow(row);
	            }
	        });
	        
	        holdGrid.on("cellendedit", function (e) { 
	        	//mini.alert(e.rowIndex);
	        });
	        
	        function saveOrUpdateRow(){
	        	 mini.confirm("确定保存到数据库？", "确定？",
	        	            function (action) {
	        	                if (action == "ok") {
	        	                	var data = holdGrid.getData();
	        	    	        	if(!data) return ;
	        	    	        	for (var i=0;i<data.length;i++) {
	        	    	        		data[i].objId= form.getData().id;
	        	    	        		
	        	    	        		// 校验变量值类型  1=固定值 2=运行时 3=内置值
	        	    	        		if(data[i].valueType == "1") {
	        	    	        			if(typeof(data[i].value) == '' || data[i].value == null || data[i].value == '') {
	        	    	        				mini.alert("第"+(i+1)+"行，变量名称为【"+data[i].name+"】,编码为【"+data[i].code+"】的固定值不能为空!");
	        	    	        				return ;
	        	    	        			}
	        	    	        		}
	        	    	        	}
	        	    	        	var dt = {};
	        	    	        	dt.jsonText=mini.encode(data);
	        	    	        	dt.objId=form.getData().id;
	        	    	        	$.ajax({
	        	    					url : "${pageContext.request.contextPath}/variable/batch_save_or_update",
	        	    					dataType: 'json',
	        	    					type : 'post',
	        	    					cache : false,
	        	    					data: dt,
	        	    					success : function(text) {
	        	    						mini.alert("保存成功!");
	        	    						holdGrid.reload();
	        	    					}
	        	    				});
	        	                } 
	        	            }
	        	        );
	        	
	        }
	        
	     	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~占位符导入表格 end: ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	     
			
			
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
					//var o = {projectCode:data.projectCode,tableId:data.tableId}
					//form.setData(o);
					
					var url = "${pageContext.request.contextPath}/dictionary/all?parentCode=tmpl_type&values=";
					tmplType.load(url + data.tmplTypes);
					
				//}
				
				
				holdGrid.setUrl("${pageContext.request.contextPath}/variable/all?objId="+data.id);
				holdGrid.load();
				
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
							o.tableId = data.tableId;
							o.tableName = data.tableName;
							o.dbName = data.dbName;
							
							form.setData(o);
							form.setChanged(false);
							
							if (data.action == 'view') {
								form.setEnabled(false);
								tmplContent.setEnabled(true);
								logContent .setEnabled(true); 
								codeContent.setEnabled(true);  
								//btnOk.hide();
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
				CloseWindow("ok");
			}

			function onCancel(e) {
				CloseWindow("cancel");
			}
		</script>
	</body>
</html>
