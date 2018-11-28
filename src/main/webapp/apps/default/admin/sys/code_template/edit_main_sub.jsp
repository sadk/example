<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>主子表配置管理</title>
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
			<input id="subTableJson" name="subTableJson" class="mini-hidden" />
			<div style="padding:4px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;" id="fd1">
		            <legend><label><input type="checkbox" checked id="checkbox1" onclick="toggleFieldSet(this, 'fd1')" hideFocus/>基本信息</label></legend>
		            <div style="padding:5px;" class="fieldset-body">
				        <table>
							<tr>
								<td style="width:80px;">数据库：</td>
								<td style="width:150px;">
								 	<input name="dbName" id="dbName" class="mini-textbox"  />
								</td>
								<td style="width:80px;">(主)表名：</td>
								<td style="width:150px;">
									<input name="tableName" id="tableName" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>表注释：</td>
								<td>
								 	<input name="comment" id="comment" class="mini-textbox"  readonly="readonly" />
								</td>
								<td>表类型：</td>
								<td>
									<input name="type" id="type" class="mini-textbox" readonly="readonly"/>
								</td>
							</tr>
				        </table>
				    </div>
				</fieldset>
				
				
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;" id="fd2">
		           <legend><label><input type="checkbox" checked id="checkbox2" onclick="toggleFieldSet(this, 'fd2')" hideFocus/>主子表信息</label></legend>
		            <div style="padding:5px;" class="fieldset-body">
				        <div class="mini-toolbar" style="border-bottom:0;padding:0px;" >
				            <table style="width:100%;">
				                <tr>
				                    <td style="width:100%;">
				                    	  <a class="mini-button" iconCls="icon-add" onclick="impRow()" plain="false" tooltip="导入...">导入</a>
				                     	<span class="separator"></span>
				                        <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="false" tooltip="新加...">新增</a>
				                        <a class="mini-button" iconCls="icon-remove" onclick="removeRow()" plain="false">删除</a>
				                       
				                        <a class="mini-button" iconCls="icon-save"  plain="false">保存</a>            
				                    </td>
				                    
				                </tr>
				            </table>           
				        </div>
					    <div id="subTableGrid" class="mini-datagrid" style="height:140px;"  showPager="false"  allowResize="true" 
					        allowCellEdit="true" allowCellSelect="true" multiSelect="true"   editNextOnEnterKey="true"  editNextRowCell="true" >
					        <div property="columns">
					           
					            <div type="checkcolumn"></div>
					            <div name="dbName"  field="dbName" headerAlign="center" allowSort="true" width="150" >数据库
					            	<input property="editor" id="dbName" name="dbName" value="test" class="mini-combobox" allowInput="true" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="name" url="${pageContext.request.contextPath}/datasource/get_database_list" />
					            </div>
					              
					            <div field="tableName" width="120" headerAlign="center" allowSort="true">子表名
					                <input property="editor" class="mini-combobox" style="width:100%;" allowInput="true" nullItemText="请选择..." emptyText="请选择" textField="tableName" valueField="tableName" url="${pageContext.request.contextPath}/table/all_from_db?dbName=${param.dbName}&isQueryDb=true" />
					            </div>
					           
					           	<div field="remark" width="120" headerAlign="center" allowSort="true">子表备注
					                <input property="editor" class="mini-textarea" style="width:200px;" minWidth="200" minHeight="50"/>
					            </div>
					            
					        </div>
					    </div>
				    </div>
				</fieldset>
				
				<!-- -->
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;" id="fd3">
		           <legend><label><input type="checkbox" checked id="checkbox3" onclick="toggleFieldSet(this, 'fd3')" hideFocus/>技术框架</label></legend>
		            <div style="padding:5px;" class="fieldset-body">
				        <table>
							<tr>
								<td style="width:80px;">框架名：</td>
								<td style="width:400px;">
								 	<input id="projectCode" name="projectCode" style="width:400px;" required="true"  onvaluechanged="onProjectChanged" class="mini-combobox" allowInput="false" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/project/all" />
								</td>
							</tr>
							<tr>
								<td style="width:100px;">框架说明：</td>
								<td style="width:400px;">
									<input name="projectComment" id="projectComment" style="width:400px;" class="mini-textbox" readonly="readonly"/>
								</td>
							</tr>
				        </table>
				    </div>
				</fieldset>
				 
				
				
				<!-- 代码生成器 - 模板配置开始! 
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;" id="fd4">
		           <legend><label><input type="checkbox" checked id="checkbox4" onclick="toggleFieldSet(this, 'fd4')" hideFocus/>代码模板</label></legend>
		            <div style="padding:5px;" class="fieldset-body">
				        <table>
							<tr>
								<td style="width:80px;">框架名：</td>
								<td style="width:400px;">
								 	<input name="subTable" style="width:400px;" id="subTable" class="mini-textbox"  />
								</td>
							</tr>
				        </table>
				    </div>
				</fieldset>
				<!-- 代码生成器 - 模板配置结束! -->
				
				
				
			</div>
			<div id="subbtn" style="text-align:center;padding:10px;">
				<!-- 
				<a class="mini-button" onclick="" id="btnTest" style="width:120px;margin-right:20px;"  iconCls="icon-split" >生成代码检测</a> -->
				<a class="mini-button" onclick="onOk" id="btnOk" style="width:80px;margin-right:20px;"  iconCls="icon-ok" >确定</a>
				<a class="mini-button" onclick="onCancel" id="btnCancel" style="width:80px;"  iconCls="icon-cancel">取消</a>
			</div>
		</form>
		<script type="text/javascript">
			mini.parse();

			var form = new mini.Form("edit-form1");
			var subTableGrid = mini.get("subTableGrid");
			
			var projectCodeCmbo = mini.get("projectCode");
			var projectCommentTxt = mini.get("projectComment");
			
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
			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~主子表配置 begin: ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	        function addRow() {          
	            var newRow = { name: "New Row" };
	            subTableGrid.addRow(newRow);

	            subTableGrid.beginEditCell(newRow, "LoginName");
	        }
	        function removeRow() {
	            var rows = subTableGrid.getSelecteds();
	            if (rows.length > 0) {
	            	subTableGrid.removeRows(rows, true);
	            }
	        }
	        
	        subTableGrid.on("celleditenter", function (e) {
	            var index = subTableGrid.indexOf(e.record);
	            if (index == subTableGrid.getData().length - 1) {
	                var row = {};
	                subTableGrid.addRow(row);
	            }
	        });
	        
	        subTableGrid.on("cellendedit", function (e) { 
	        	//mini.alert("编辑结束");
	        });
	     	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~主子表配置 end: ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	     	
			function SaveData() {
	     		// 主表数据
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				
				// 子表数据
				 var data = subTableGrid.getChanges();
		         var json = mini.encode(data);
		         subTableGrid.loading("保存中，请稍后......");
		         mini.get("subTableJson").setValue(json);
				$.ajax({
					url : "${pageContext.request.contextPath}/code_template/save_or_update_main_sub",
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
						url : "${pageContext.request.contextPath}/table/page?id=" + data.id,
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
								btnTest.hide();
								btnOk.hide();
								//btnCancel.hide();
								btnCancel.setText("关闭");
							}
						}
					});
					
					// 加载子表信息和框架工程信息
					$.ajax({
						url : "${pageContext.request.contextPath}/table_sub/page?mainTableId=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							if(o!=null && o.data!=null && o.data.length>0) {
								var list = o.data;
								for(var i=0;i<list.length;i++){
									var newRow = { dbName: list[i].dbName , tableName : list[i].tableName , remark: list[i].remark };
							        subTableGrid.addRow(newRow);
									subTableGrid.beginEditCell(newRow);
									
									// 下拉
									var dt = projectCodeCmbo.getData();
									//alert(mini.encode(dt));
									for(var n=0;n<dt.length;n++) {
										//alert(list[i].projectCode + "  "+ dt[n].code);
										if( (""+list[i].projectCode) == (""+dt[n].code) ){
											projectCommentTxt.setValue(dt[n].remark);
											
											projectCodeCmbo.setValue(dt[n].code);
											//projectCodeCmbo.setText(dt[n].code);
											break;
										}
									}
								}
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
