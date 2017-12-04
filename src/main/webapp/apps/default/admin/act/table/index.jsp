<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>流程表字段管理</title>
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
			<div size="270" showCollapseButton="true">
			    <div class="mini-panel" showToolbar="true" showHeader="false" style="width:100%;height:100%;">
				   
				    <div style="padding-left:3px;padding-bottom:5px;">
				            <div style="padding:5px;" id="form1">
						        <table>
						        	<tr>
						            	<td style="width:120px;">数据源：</td>
						                <td style="width:150px;">    
						                    <input id="dataSourceCode" name="dataSourceCode" value="localhost" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/datasource/all" />
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
						            <tr>
						            	<td style="width:120px;">模型名：</td>
						                <td style="width:150px;">    
						                    <input style="width:150px;" name="modelName" id="modelName" class="mini-textbox" emptyText="请输入模型名称" />
						                </td>
						            </tr>
						            
						            <!-- 
						            <tr>
						            	<td style="width:120px;">数据源名称：</td>
						                <td style="width:150px;">    
						                   <input id="dataSourceCode" name="dataSourceCode" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择数据源" textField="name" valueField="code" url="${pageContext.request.contextPath}/datasource/all" />
						                </td>
						            </tr> -->
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
						    <div title="开发者定义的表" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" onclick="viewTable()" iconCls="icon-edit">即时查看表结构</a>
												<a class="mini-button" onclick="exeSQL()" iconCls="icon-reload" >SQL面板</a>
												<span class="separator"></span>  
												<a class="mini-button" onclick="removeTable()" iconCls="icon-remove" >删除表定义</a>
											</td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
						            <div id="tableGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="false"
						                url="${pageContext.request.contextPath}/table/page" idField="id" allowResize="false"
						                sizeList="[5,10,20,50]" pageSize="20" showEmptyText="true" emptyText="暂无数据" sortMode="client">
						                
						                <div property="columns">
											<div type="checkcolumn" ></div>
											<div field="modelName" width="150" headerAlign="center" allowSort="true">模型名称</div>
											<div field="version" width="150" headerAlign="center" allowSort="true">版本</div>
											<div field="dbName" width="80" headerAlign="center" allowSort="true">数据库</div>
											<div field="tableName" width="120" headerAlign="center" allowSort="true">表名</div>	
											<div field="comment" width="160" headerAlign="center" allowSort="true">表注释</div>										
											<div field="type" width="100" headerAlign="center" allowSort="true">表类型</div>
											<div field="engine" width="100" headerAlign="center" allowSort="true">表引擎</div>
											<div field="rows" width="60" headerAlign="center" allowSort="true">表记录数</div>
											<div field="autoIncrement" width="80" headerAlign="center" allowSort="true">自动增长值</div>
											<div field="collation" width="100" headerAlign="center" allowSort="true">表编码</div>
											<div field="createOption" width="150" headerAlign="center" allowSort="true">建表项</div>
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
							<div title="开发者定义的表字段" refreshOnClick="true">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" onclick="impColumsByTable()" iconCls="icon-redo" >从DB导入</a>
												<a class="mini-button" onclick="reload()" iconCls="icon-reload" >从DB同步到系统</a>
												<span class="separator"></span>
												<a class="mini-button" onclick="addColumn()" iconCls="icon-add" >手工添加</a>  
												<a class="mini-button" onclick="editColumn()" iconCls="icon-edit" >编辑</a>
												<a class="mini-button" onclick="saveColumn()" iconCls="icon-save" >保存</a>
												<a class="mini-button" onclick="removeColumn()" iconCls="icon-remove" >删除字段定义</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="columnGrid" class="mini-datagrid" style="width:100%;height:100%;" sortMode="client"
										url="${pageContext.request.contextPath}/column/page"  idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据" sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" 
										 allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" >
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="version" width="150" headerAlign="center" allowSort="true">版本</div>
											<div field="tableName" width="120" headerAlign="center" allowSort="true">DB表名</div>	
											<div field="name" width="100" headerAlign="center" allowSort="true">DB字段名
												<input property="editor" class="mini-textbox" style="width:100%;" minWidth="100" />
											</div>
											<div type="comboboxcolumn" field="primaryKey" width="50" headerAlign="center" align="center" allowSort="true">主键
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=yes_or_no" />
											</div>
											<div field="comment" width="100" headerAlign="center" allowSort="true">DB字段注释
												<input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
											</div>
											<div field="dbType" width="100" headerAlign="center" allowSort="true">DB字段类型</div>
											
											<div field="propertyName" width="100" headerAlign="center" allowSort="true">JAVA字段名
												<input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
											</div>
											
											<div type="comboboxcolumn" field="searchType" width="160" headerAlign="center" align="left" allowSort="true">是否作为查询条件
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=yes_or_no" />
											</div>
											
											<div type="comboboxcolumn" field="javaType" width="130" headerAlign="center" allowSort="true">JAVA字段类型
												<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=java_type" />
											</div>
											<div type="comboboxcolumn" field="columnCodegenType" width="140" headerAlign="center" align="left" allowSort="true">JAVA字段的代码生成类型
												<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=column_codegen_type" />
											</div>
											<div field="columnCodegenFormat" width="90" headerAlign="center" align="left" allowSort="true">字段的显示格式
												<input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
											</div>
											
											<!--  <div field="fkCol" width="80" headerAlign="center" allowSort="true">外键</div> -->
											<div type="comboboxcolumn" field="oroColumnType" width="160" headerAlign="center" align="left" allowSort="true">ORMapping映射的字段类别
												<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=oro_column_type" />
											</div>
											
											<div field="columnCodegenGroupCode" width="100" headerAlign="center" align="left" allowSort="true">字段的分组编码
												<input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
											</div>
											
											<!-- 
											<div type="comboboxcolumn" field="largeFiled" width="100" headerAlign="center" allowSort="true">是否大字段
												<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=yes_or_no" />
											</div>
											 -->
											 
											<div field="optLog" width="100" headerAlign="center" allowSort="true">操作日志</div>
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
			var grid = mini.get("tableGrid");
			var columnGrid = mini.get("columnGrid");
			
			
			
			var tabs = mini.get("tabs1");
			
			grid.load();
			
			function clear() {
				 form.clear();
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
			
			
			grid.on("rowclick", function(e){
				var record = e.record;
				columnGrid.load({dbName:record.dbName,tableName:record.tableName,version:record.version});
			});
			
			function search() {
				var data = form.getData();
				grid.load(data);
			}
			
			function removeTable() {
				// 删除表定义
				var row = grid.getSelected();
				if(row) {
					mini.confirm("确定删除选中的表定义?", "确定？",
							function (action) {
								if (action == "ok") {
									$.ajax({
										'url': "${pageContext.request.contextPath}/table/delete?ids="+row.id,
										type: 'post',
										dataType:'JSON',
										cache: false,
										async:false,
										success: function (json) {
											mini.alert("删除成功");
											grid.reload();
											columnGrid.reload();
										},
										error : function(data) {
									  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
									  		mini.alert(data.responseText);
										}
									});
								}
							}
						);
				} else {
					mini.alert("请选中一条以上的记录");
				}
			}
			
			function removeColumn() {
				// 删除字段定义
				var row = columnGrid.getSelecteds();
				if(row) {
					var idArr = new Array();
					for(var i=0;i<row.length;i++) {
						idArr.push(row[i].id);
					}
					mini.confirm("确定删除选中的字段定义?", "确定？",
							function (action) {
								if (action == "ok") {
									$.ajax({
										'url': "${pageContext.request.contextPath}/column/delete?ids="+idArr.join(","),
										type: 'post',
										dataType:'JSON',
										cache: false,
										async:false,
										success: function (json) {
											mini.alert("删除成功");
											columnGrid.reload();
										},
										error : function(data) {
									  		mini.alert(data.responseText);
										}
									});
								}
							}
						);
				} else {
					mini.alert("请选中一条以上的记录");
				}
			}
			
		 
			
			function editColumn() {
				var row = columnGrid.getSelected();
				if (row) {
					mini.open({
						url : "${pageContext.request.contextPath}/apps/default/admin/sys/column/edit.jsp",
						title : "编辑字段信息",
						width : 540,
						height : 690,
						onload : function() {
							var iframe = this.getIFrameEl();
							var data = {
								action : 'edit',
								id : row.id
							};
							iframe.contentWindow.SetData(data);
						},
						ondestroy : function(action) {
							columnGrid.reload();
						}
					});
				} else {
					mini.alert("请选中一条字段记录");
				}
			}
			
			function saveColumn(){ // 快速保存
			    var data = columnGrid.getChanges();
	            var json = mini.encode(data);
	           // alert(json);
	            columnGrid.loading("保存中，请稍后......");
	           // if(true) return ;
	            $.ajax({
	                url: "${pageContext.request.contextPath}/column/update_short",
	                data: { data: json },
	                type: "post",
	                success: function (text) {
	                	columnGrid.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
			}  
			
			function impColumsByTable() {
				var row = grid.getSelected();
				var dbName = mini.get("dbName").value;
				if(row) {
					mini.confirm("选中的版本字段信息将会重新删除后导入,是否继续导入？", "确定？",
							function (action) {
								if (action == "ok") {
									$.ajax({
										'url': "${pageContext.request.contextPath}/column/imp_columns_by_table?tableId="+row.id+"&dbName="+dbName+"&tableName="+row.tableName+"&version="+row.version,
										type: 'post',
										dataType:'JSON',
										cache: false,
										async:false,
										success: function (json) {
											mini.alert("导入成功");
											columnGrid.reload();
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
			
			

			 
		</script>
	</body>
</html>