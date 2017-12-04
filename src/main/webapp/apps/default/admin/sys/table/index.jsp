<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>数据表管理</title>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<style type="text/css">
			body {
				margin: 0;
				padding: 0;
				border: 0;
				width: 100%;
				height: 100%;
				overflow: hidden;
			}
		</style>
	</head>
	<body>
		<div class="mini-splitter" style="width:100%;height:100%; overflow:auto;">
			<div size="250" showCollapseButton="true">
			    <div class="mini-panel" showToolbar="true" showHeader="false" style="width:100%;height:100%;">
				   
				    <div style="padding-left:3px;padding-bottom:5px;">
				            <div style="padding:5px;" id="form1">
						        <table>
						        	<tr>
						            	<td style="width:120px;">数据源：</td>
						                <td style="width:150px;">    
						                    <input id="dataSourceCode" name="dataSourceCode" value="localhost" onvaluechanged="onDataSourceCodeValuechanged" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/datasource/all" />
						                </td>
						            </tr>
						        	<tr>
						            	<td style="width:120px;">数据库名：</td>
						                <td style="width:150px;">  
						                	 <input id="dbName" name="dbName" value="test" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="name" url="${pageContext.request.contextPath}/datasource/get_database_list" />  
						                </td>
						            </tr>
						            <tr>
						            	<td style="width:120px;">表名：</td>
						                <td style="width:150px;">    
						                    <input style="width:150px;" name="tableName" id="tableName" class="mini-textbox" emptyText="请输入表名称" />
						                </td>
						            </tr>
						        </table>
						        <div style="text-align:center;padding:10px;">
									<a class="mini-button" onclick="search()" iconCls="icon-search" style="width:60px;margin-right:20px;">查询</a>
									<a class="mini-button" onclick="clear()" iconCls="icon-cancel" style="width:60px;margin-right:20px;">清空</a>
								</div>
						    </div>
					</div>
				</div>       
			</div>
			<div showCollapseButton="true">
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						    <div title="数据库定义的表" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<!-- <a class="mini-button" onclick="edit()" iconCls="icon-edit" >查看表结构</a> -->
												<a class="mini-button" onclick="sqlView()" iconCls="icon-reload" >SQL面板</a>
												
											</td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
						            <div id="dbTableGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
						                url="${pageContext.request.contextPath}/table/page?isQueryDb=true" idField="id" allowResize="false"
						                sizeList="[5,10,20,50]" pageSize="20" showEmptyText="true" emptyText="暂无待修改信息" sortMode="client">
						                
						                <div property="columns">
											<div type="checkcolumn" ></div>
											<div field="dbName" width="80" headerAlign="center" allowSort="true">数据库</div>
											<div field="tableName" width="120" headerAlign="center" allowSort="true">表名</div>	
											<div field="comment" width="160" headerAlign="center" allowSort="true">表注释</div>										
											<!-- <div field="dataSourceCode" width="80" headerAlign="center" allowSort="true">数据源编码</div> -->
											<div field="type" width="100" headerAlign="center" allowSort="true">表类型</div>
											<div field="engine" width="100" headerAlign="center" allowSort="true">表引擎</div>
											<div field="rows" width="60" headerAlign="center" allowSort="true">表记录数</div>
											<div field="autoIncrement" width="80" headerAlign="center" allowSort="true">自动增长值</div>
											<div field="collation" width="100" headerAlign="center" allowSort="true">表编码</div>
											<div field="createOption" width="150" headerAlign="center" allowSort="true">建表项</div>
											<!-- <div field="remark" width="160" headerAlign="center" allowSort="true">用户备注</div>
											<div field="appCode" width="60" headerAlign="center" allowSort="true">应用编码</div>-->
											<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        				<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>
										</div>
										
						            </div>
						        </div>
						    </div>
						</div>
						<ul id="refreshTabMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
							<li onclick="reloadTab">
								刷新标签页
							</li>
						</ul>
					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu2" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							<div title="开发者定义的表" refreshOnClick="true">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" onclick="impTable()" iconCls="icon-redo" >从DB导入</a>
												<a class="mini-button" onclick="impSyn()" iconCls="icon-reload" >从DB同步到系统</a>
												<a class="mini-button" onclick="add()" iconCls="icon-add" >手工添加</a>
												<a class="mini-button" onclick="remove()" iconCls="icon-remove" >删除定义</a>
												<a class="mini-button" onclick="updateShort()" iconCls="icon-save" >保存</a>
												
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="devTableGrid" class="mini-datagrid" style="width:100%;height:100%;" sortMode="client"
										url="${pageContext.request.contextPath}/table/page"  idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据" sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" 
										allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true">
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="modelName" width="150" headerAlign="center" allowSort="true">模型名
												<input property="editor" class="mini-textbox" style="width:100%;" minWidth="100" />
											</div>
											<div field="version" width="150" headerAlign="center" allowSort="true">定义的版本</div>	
											<div field="dataSourceCode" width="80" headerAlign="center" allowSort="true">数据源</div>
											<div field="dbName" width="80" headerAlign="center" allowSort="true">数据库</div>
											<div field="tableName" width="120" headerAlign="center" allowSort="true">表名</div>	
											<div field="comment" width="160" headerAlign="center" allowSort="true">表注释</div>	
											<!-- <div field="dataSourceCode" width="80" headerAlign="center" allowSort="true">数据源编码</div> -->
											<div field="type" width="100" headerAlign="center" allowSort="true">表类型</div>
											<div field="engine" width="100" headerAlign="center" allowSort="true">表引擎</div>
											<div field="rows" width="60" headerAlign="center" allowSort="true">表记录数</div>
											<div field="autoIncrement" width="80" headerAlign="center" allowSort="true">自动增长值</div>
											<div field="collation" width="100" headerAlign="center" allowSort="true">表编码</div>
											<div field="createOption" width="150" headerAlign="center" allowSort="true">建表项</div>
											<div field="appCode" width="60" headerAlign="center" allowSort="true">应用编码</div>
											
											<div field="remark" width="160" headerAlign="center" allowSort="true">用户备注
												<input property="editor" class="mini-textbox" style="width:100%;" minWidth="100" />
											</div>
											
											<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        				<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<ul id="refreshTabMenu2" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
							<li onclick="reloadTab">
								刷新标签页
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			mini.parse();
			
			var form = new mini.Form("#form1");
			var grid = mini.get("dbTableGrid");
			var devTableGrid = mini.get("devTableGrid");
			var tabs = mini.get("tabs1");
			var dbName = mini.get("dbName");
			
			grid.load({isQueryDb:true,dbName:'test'});
			grid.on("rowclick", function(e){
				var record = e.record;
				devTableGrid.load({dbName:record.dbName,tableName:record.tableName});
			});
			
			function clear() {
				 form.clear();
			}
			
			function onDataSourceCodeValuechanged(e) {
				//mini.alert(e.source.value); // e.sender
				
				var data = {};
				data.dataSourceCode = e.source.value;
	            $.ajax({
	                url: "${pageContext.request.contextPath}/datasource/get_database_list",
	                data: data,
	                type: "post",
	                success: function (text) {
	                	//mini.alert(text);
	                	dbName.setData(text);
	                	
	                	var isTestExists = false;
	                	for(var i=0;i<text.length;i++) {
	                		if(text[i].name == 'test') {
	                			isTestExists = true;
	                		}
	                	}
	                	if (isTestExists) {
	                		dbName.setValue('test');
	                	}else {
	                		dbName.select(0);
	                	}
	                	
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
			}
			
			function sqlView() {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/sys/table/sql_panel.jsp",
					title : "SQL页板",
					width : 1024,
					height : 768,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							//id : row.id,
							action : 'sqlView'
						};
						//iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						//devTableGrid.reload();
					}
				});
			}
			
			function add() {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/sys/table/edit.jsp",
					title : "添加表定义",
					width : 500,
					height : 320,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							//id : row.id,
							action : 'add'
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						//grid.reload();
						devTableGrid.reload();
					}
				});
			}
			
			function updateShort() {
			    var data = devTableGrid.getChanges();
	            var json = mini.encode(data);
	           // alert(json);
	            devTableGrid.loading("保存中，请稍后......");
	           // if(true) return ;
	            $.ajax({
	                url: "${pageContext.request.contextPath}/table/update_short",
	                data: { data: json },
	                type: "post",
	                success: function (text) {
	                	devTableGrid.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
			}
			
			function impSyn() {
				var row = devTableGrid.getSelected();
				if(row) {
					mini.confirm("是否同步当前表定义信息？", "确定？",
							function (action) {
								if (action == "ok") {
									$.ajax({
										'url': "${pageContext.request.contextPath}/table/imp_syn?id="+row.id,
										type: 'post',
										dataType:'JSON',
										cache: false,
										async:false,
										success: function (json) {
											mini.alert("同步成功");
											devTableGrid.reload();
										},
										error : function(data) {
									  		mini.alert(data.responseText);
										}
									});
								}
							}
						);
				} else {
					mini.alert("请选中一个数据库定义的表");
				}
			}
			
			var currentTab = null;
			function onBeforeOpen(e) {
				currentTab = tabs.getTabByEvent(e.htmlEvent);
				if(!currentTab) {
					e.cancel = true;
				}
			}
			
			function reloadTab() {
				tabs.reloadTab(currentTab);
			}
			
			function impTable() {
				var row = grid.getSelected();
				if(row) {
					var dataSourceCode = mini.get("dataSourceCode").value; 
					mini.confirm("确定即时从数据库里导入当前表定义信息？", "确定？",
							function (action) {
								if (action == "ok") {
									$.ajax({
										'url': "${pageContext.request.contextPath}/table/imp_table?dataSourceCode="+dataSourceCode+"&dbName="+row.dbName+"&tableName="+row.tableName,
										type: 'post',
										dataType:'JSON',
										cache: false,
										async:false,
										success: function (json) {
											mini.alert("导入成功");
											devTableGrid.reload();
										},
										error : function(data) {
									  		mini.alert(data.responseText);
										}
									});
								}
							}
						);
				} else {
					mini.alert("请选中一个数据库定义的表");
				}
			}
			

			
			function search() {
				var data = form.getData();
				grid.load(data);
			}
			
			
			function reload(){
				grid.reload();
			}
			
			
			function remove() {
				var row = devTableGrid.getSelecteds();
				//alert(mini.encode(row));
				if(row) {
					var idsArray = new Array();
					for(var i=0;i<row.length;i++){
						idsArray.push(row[i].id);
					}
					//mini.alert(idsArray.join(","));
					//if(true) return ;
					mini.confirm("确认删除记录？", "确定？",
							function (action) {
								if (action == "ok") {
									$.ajax({
										'url': "${pageContext.request.contextPath}/table/delete?ids="+idsArray.join(","),
										type: 'post',
										dataType:'JSON',
										cache: false,
										async:false,
										success: function (json) {
											mini.alert("删除成功");
											devTableGrid.reload();
										},
										error : function(data) {
									  		mini.alert(data.responseText);
										}
									});
								}
							}
						);
				} else {
					mini.alert("请选中一个开发者定义的表");
				}
			}			
		</script>
	</body>
</html>