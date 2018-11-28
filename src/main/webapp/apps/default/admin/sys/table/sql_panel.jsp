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
			    	<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>数据源相关</legend>
				    <div style="padding-left:10px;padding-bottom:5px;">
				            <div id="form1">
						        <table>
						        	<tr>
						            	<td style="width:120px;">数据源：</td>
						                <td style="width:150px;">    
						                    <input id="dataSourceCode" name="dataSourceCode" value="localhost" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/datasource/all" />
						                </td>
						            </tr>
						        </table>
						    </div>
					</div>
					</fieldset>
					
					<ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/datasource/get_database_list_by_ds_code?dataSourceCode=localhost"  style="width:100%;padding:5px;" 
			         showTreeIcon="true" textField="text" idField="id" parentField="pid" resultAsTree="false" showArrow="true" expandOnNodeClick="true" expandOnLoad="true" onnodeclick="onNodeClick">        
			    	</ul> 
				</div>   
			</div>
			<div showCollapseButton="true">
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						    <div title="查询" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" onclick="executeSql()" iconCls="icon-ok" >执行SQL</a>
												<a class="mini-button" onclick="executeSql()" iconCls="icon-node" >格式化SQL</a>
												<a class="mini-button" onclick="clear()" iconCls="icon-remove" >清空SQL</a>
												<span class="separator"></span>
												<a class="mini-button" onclick="closePage()" iconCls="icon-close" >关闭SQL面板</a>
											</td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
						            <input class="mini-textarea" id="sqlStmt" style="width:100%;height:100%;" />
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
							<div title="结果集" refreshOnClick="true">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" onclick="exportSql()" iconCls="icon-download" >导出SQL</a>
												<a class="mini-button" onclick="exportExcel()" iconCls="icon-download" >导出Excel</a>
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
			
			var devTableGrid = mini.get("devTableGrid");
			var tabs = mini.get("tabs1");
			
			//devTableGrid.load({isQueryDb:true,dbName:'test'});
			
			function onNodeClick(e){
				var sender = e.sender ;
				var node = e.node
				//alert(mini.encode(node));
				//alert(node.text);
				
				var sqlContent = mini.get("sqlStmt").getValue();
				if(sqlContent == ''){
					mini.get("sqlStmt").setValue("use "+node.text+";\r");
					return ;
				}
				
				 mini.confirm("确定切换数据库，当前SQL将清空？", "确定？",
		            function (action) {
					 	
		                if (action == "ok") {
		                	$.ajax({
								url : "${pageContext.request.contextPath}/datasource/get_prety_sql",
								dataType: 'json',
								type: 'Post',
								cache : false,
								data : {sql:sqlContent},
								success : function(text) {
									mini.alert(text);
									mini.get("sqlStmt").setValue(text);
								}
							});
		                } else {
		                    
		                }
		            }
		        );
			}
			
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
			 
			
			function search() {
				var data = form.getData();
				grid.load(data);
			}
			
			
			function reload(){
				grid.reload();
			}
			
					
		</script>
	</body>
</html>