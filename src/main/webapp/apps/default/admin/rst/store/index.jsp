<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>门店管理</title>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/pagertree.js" ></script>
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
			            <div id="form1" style="padding:5px;">
							<table>						
									<tr>
										<td>关键字 ：</td>
										<td>
											<input id="key" name="key"  class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
										</td>
									</tr>
						
									<tr>
										<td>门店编码：</td>
										<td>
											<input id="code" name="code"   class="mini-textbox"  emptyText="请输入门店编码"  onenter="search"/>
										</td>
									</tr>
								  
									<tr>
										<td>门店名称：</td>
										<td>
											<input id="name" name="name"   class="mini-textbox"  emptyText="请输入门店名称"  onenter="search"/>
										</td>
									</tr>
									
									<tr>
										<td>区域：</td>
										<td>
											<input id="belongRegion" name="belongRegion"   class="mini-textbox"  emptyText="请输入区域"  onenter="search"/>
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
						<div id="tabs1" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						    <div title="门店信息" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="edit('add')">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit('edit')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
											</td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
									<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="true"
										url="${pageContext.request.contextPath}/rst/store_info/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
									    <div property="columns">
									        <div type="checkcolumn"></div>
									        <div field="code" width="120" headerAlign="center">门店编码</div>
									        <div field="name" width="160" headerAlign="center">门店名称</div>
									        <div field="belongRegion" width="100" headerAlign="center">区域</div>
									         
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
									         
									    </div>
									</div>
						        </div>
						    </div>
						</div>
					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							
							<div title="门店管理员" refreshOnClick="true" name="tabReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="editManager('add')">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeManager()">删除</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-reload" onclick="refreshManager()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="datagrid2" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="false"
										url="${pageContext.request.contextPath}/rst/store_manager/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
										<div property="columns">
											<div type="checkcolumn"></div>
									        <div field="managerCode" width="120" headerAlign="center">用户编码</div>
									        <div field="managerName" width="120" headerAlign="center">用户名称</div>
									        
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
										</div>
									</div>
								</div>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			mini.parse();
			
			var form = new mini.Form("#form1");
			var grid = mini.get("datagrid1"); 
			var managerGrid = mini.get("datagrid2");
		 
			grid.on("rowclick", function(e){
				var record = e.record;
				managerGrid.load({storeCode:record.code});
			});
	
			function refreshManager() {
				managerGrid.reload();
			}
			
			function editManager() {
				var row = grid.getSelected();
				if (!row) {
					mini.alert("请选择一个门店");
					return ;
				}
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/rst/store/selector_user.jsp?multiSelect=false",
					title : "添加管理员",
					width : 680,
					height : 400,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {};
						data.storeCode = row.code;
						
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						if ('ok' == action) {
							managerGrid.reload();
						}
					}
				});
			}
			
			function removeManager() {
				var row = grid.getSelected();
				var row2 = managerGrid.getSelecteds();
				
				if(!row) {
					mini.alert("请选择门店");
					return ;
				}
		    	 
		    	if (row2.length == 0) {
		    		mini.alert("请至少选择一个管理员用户");
		    		return ;
		    	}
		    	
		    	var userCodes = new Array();
		    	for(var i=0;i<row2.length;i++) {
		    		userCodes.push(row2[i].managerCode);
		    	}
		    	
		    	var data = {};
		    	data.storeCode = row.code;
		    	data.userCodes = userCodes.join(",");

				mini.confirm("确定删除？", "确定？",
					function (action) {
						if (action == "ok") {
							deleteManager();
						}
					});
				
				var deleteManager = function() {
			    	$.ajax({
						'url': "${pageContext.request.contextPath}/rst/store_info/delete_manager",
						type: 'post', dataType:'JSON',
						data : data,
						success: function (json) {
							managerGrid.reload();
						},
						error : function(data) {
					  		mini.alert(data.responseText);
						}
					});
				}
			}
			
			function edit(action) {
				var row = grid.getSelected();
				if('edit' == action) {
					if(!row) {
						mini.alert("请至少选择一条记录");
						return ;
					}
				}
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/rst/store/edit.jsp",
					title : "编辑",
					width : 480,
					height : 300,
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
						grid.reload();
					}
				});
			 
			}
			
			function remove() {
				var row = grid.getSelecteds();
				if (row.length == 0) {
					mini.alert("请选中一条以上的记录");
					return;
				}
				
				var ids = [];
				for(var i=0;i<row.length;i++) {
					ids.push(row[i].id);
				}
				mini.confirm("确定删除？", "确定？",
					function (action) {
						if (action == "ok") {
							$.ajax({
								'url': "${pageContext.request.contextPath}/rst/store_info/delete?ids="+ids.join(","),
								type: 'post', dataType:'JSON',
								success: function (json) {
									mini.alert("删除成功");
									grid.reload();
								},
								error : function(data) {
							  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
							  		mini.alert(data.responseText);
								}
							});
						}
					}
				);
			}

			
			
			function clear() {
				 form.clear();
			}
		 
			function search() {
				var data = form.getData();
				grid.load(data);
			}
		</script>
	</body>
</html>