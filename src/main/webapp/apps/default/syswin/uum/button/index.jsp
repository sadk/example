<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>菜单按钮</title>
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
			<div size="240" showCollapseButton="true">
			    <div class="mini-panel" showToolbar="true" showHeader="false" style="width:100%;height:100%;">
				     
				    <!--body-->
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
												<td>名称：</td>
												<td>
													<input id="name" name="name"   class="mini-textbox"  emptyText="请输入角色名称"  onenter="search"/>
												</td>
											</tr>
										  
											<tr>
												<td>备注：</td>
												<td>
													<input id="remark" name="remark"   class="mini-textbox"  emptyText="请输入备注"  onenter="search"/>
												</td>
											</tr>
											
											<!-- <tr>
												<td>创建日期(开始)：</td>
												<td>
													<input id="createTimeBegin" name="createTimeBegin" format="yyyy-MM-dd" class="mini-datepicker"  emptyText="请输入创建日期(开始)" />
												</td>
											</tr>
											<tr>
												<td>创建日期(结束)：</td>
												<td>
													<input id="createTimeEnd" name="createTimeEnd" format="yyyy-MM-dd" class="mini-datepicker"  emptyText="请输入创建日期(结束)" />
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
						    <div title="菜单定义" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="add()">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-split" onclick="repairNodePath()">修复结点路径</a>
												<a class="mini-button" iconCls="icon-reload" onclick="menuDataReload()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
									<div id="datagrid1" class="mini-treegrid" style="width:100%;height:100%;"
									showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="false" 
				    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false" 
									url="${pageContext.request.contextPath}/syswin/menu/all" > 
									    <div property="columns">
									        <div type="checkcolumn"></div>
									        <div name="name" field="name" width="160" headerAlign="center">名称</div>
									        <div field="code" width="80" headerAlign="center">编码</div>
									        <div field="url" width="160" align="left">菜单URL</div>
									        <div field="nodePath" width="120" align="left">结点路径</div>
									        
									        <div name="id" field="id" width="30" >ID</div>
									        <div name="pid" field="pid" width="30" >父ID</div>              
									    </div>
									</div>
						        </div>
						    </div>
						    
						    
						    








						    <div title="功能（按钮）定义" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="editButtonData('add')">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editButtonData('edit')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="deleteButtonData()">删除</a>
												<!-- 
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-save" onclick="save()">保存</a>
												 -->
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="searchButtonData()">查询</a>
						                    </td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
									<div id="datagrid2" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
									idField="id" sizeList="[20,50,100,150,200]" pageSize="50" url="${pageContext.request.contextPath}/syswin/function/page" > 
									    <div property="columns">
									        <div type="checkcolumn"></div>
									        <div name="name" field="name" width="80" headerAlign="center" align="center">名称</div>
									        <div field="code" width="80" headerAlign="center">编码</div>
									        <div field="description" width="160" headerAlign="center" align="left">描述</div>
									        <div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
											<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">更新日期</div>
									    
									    </div>
									</div>
						        </div>
						    </div>
						    
						    



							<div title="数据查询权限定义" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="editRange('add')">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editRange('edit')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="deleteRange()">删除</a>
												<!-- 
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-save" onclick="save()">保存</a>
												 -->
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="searchRangeData()">查询</a>
						                    </td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
									<div id="rangeGrid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
									idField="id" sizeList="[20,50,100,150,200]" pageSize="50" url="${pageContext.request.contextPath}/syswin/range/page" > 
									    <div property="columns">
									        <div type="checkcolumn"></div>
									        <div name="name" field="name" width="80" headerAlign="center" align="center">名称</div>
									        <div field="code" width="80" headerAlign="center">编码</div>
									        <div field="value" width="180" headerAlign="center">范围值</div>
									        <div field="description" width="160" headerAlign="center" align="left">描述</div>
									        <div field="createTime" dateFormat="yyyy-MM-dd" width="80" headerAlign="center" allowSort="true" align="center">创建日期</div>
											<div field="updateTime" dateFormat="yyyy-MM-dd" width="80" headerAlign="center" allowSort="true" align="center">更新日期</div>
									    
									    </div>
									</div>
						        </div>
						    </div>


						    
						</div>
					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							<div title="菜单下的按钮定义" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addButton()">添加按钮</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeButton()">删除按钮</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="menuButtonsGrid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" multiSelect="true" 
										url="${pageContext.request.contextPath}/syswin/function/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
										<div property="columns">
											<div type="checkcolumn" ></div>
									        <div name="name" field="name" width="80" headerAlign="center" align="center">名称</div>
									        <div field="code" width="80" headerAlign="center">编码</div>
									        <div field="description" width="160" align="left">描述</div>
									        <div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
											<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">更新日期</div>
									    
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
		function clear() {
			 form.clear();
		}
		 
		var grid = mini.get("datagrid1");
		var buttonGrid = mini.get("datagrid2");
		var menuButtonsGrid = mini.get("menuButtonsGrid");
		var rangeGrid = mini.get("rangeGrid");
		
		grid.on("rowclick",function(e){
			var record = e.record;
			menuButtonsGrid.load({menuId: record.id}); 
		})
		
		buttonGrid.load();
		rangeGrid.load();
		
		function deleteRange() {
			var rows = rangeGrid.getSelecteds();
			if(rows.length == 0){
				mini.alert("请选择要删除的权限范围定义");
				return ;
			}
			var ids = new Array();
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].id);
			}
			mini.confirm("确定删除当前定义? 其关联角色数据查询权限也将删除!", "确定",
			function (action) {
				if (action == "ok") {
						$.ajax({
							'url': "${pageContext.request.contextPath}/syswin/range/delete?ids="+ids.join(","),
							type: 'post', dataType:'JSON', cache: false, async:false,
							success: function (json) {
								mini.alert("删除成功");
								rangeGrid.reload();
							},
							error : function(data) {
						  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
						  		mini.alert(data.responseText);
							}
						});						
				}
			});

		}
		
		function editRange(action){
			var rangeRow = rangeGrid.getSelected();
			
			
			if(action == 'edit') {
				if(!rangeRow){
					mini.alert("请选择一个数据范围定义!");
					return ;
				}
			}
			
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/syswin/uum/range/edit_trace.jsp",
				title : "编辑数据范围定义",
				width : 560, height : 600,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {
						action : action,
						id : rangeRow.id
					};
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					rangeGrid.reload();
				}
			});
			
		}
		
		function searchButtonData() {
			 
		}
		
		function searchRangeData() {
			
		}
		
		function menuDataReload() {
			grid.reload();
		}
		function deleteButtonData() {
			var rows = buttonGrid.getSelecteds();
			if(rows && rows.length==0){
				mini.alert("请至少选择一个按钮!");
				return ;
			}
			var ids = new Array();
			for(var i=0;i<rows.length;i++) {
				ids.push(rows[i].id);
			}
			
			$.ajax({
				'url': "${pageContext.request.contextPath}/syswin/function/delete?ids="+ids.join(","),
				type: 'post', dataType:'JSON', cache: false, async:false,
				success: function (json) {
					mini.alert("删除功能按钮成功");
					menuButtonsGrid.reload();
				},
				error : function(data) {
			  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
			  		mini.alert(data.responseText);
				}
			});
		}
		 
		function editButtonData(action) {
			if('edit' == action) {
				var row= buttonGrid.getSelected();
				if(!row){
					mini.alert("请选择一个按钮定义");
					return ;
				}
			}
			
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/syswin/uum/button/edit.jsp",
				title : "编辑功能按钮信息",
				width : 450, height : 220,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {
						action : action,
						id : row.id
					};
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					buttonGrid.reload();
				}
			});
		}
		 

		
		// -------------------------------------- 菜单按钮关系  ---------------------
		function removeButton() {
			var data = {};
			var row = grid.getSelected();
			if(!row) {
				mini.alert("请选择一个菜单定义!");
				return ;
			}
			
			var btnRows = menuButtonsGrid.getSelecteds();
			if(btnRows && btnRows.length ==0){
				mini.alert("请选择至少一个按钮");
				return ;
			}
			
			var functionIds = new Array();
			for(var i=0;i<btnRows.length;i++){
				functionIds.push(btnRows[i].id);
			}
			data.menuId= row.id;
			data.functionIds = functionIds.join(",");
			
			$.ajax({
				'url': "${pageContext.request.contextPath}/syswin/function/delete_menu_buttons",
				type: 'post', dataType:'JSON', cache: false, async:false,
				data: data,
				success: function (json) {
					mini.alert("删除功能按钮成功");
					menuButtonsGrid.reload();
				},
				error : function(data) {
			  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
			  		mini.alert(data.responseText);
				}
			});
					 
		}
		
		function addButton() {
			var data = {};
			var row = grid.getSelected();
			if(!row) {
				mini.alert("请选择一个菜单定义!");
				return ;
			}
			
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/syswin/uum/button/selector_button.jsp",
				title : "请选择菜单按钮",
				width : 690, height : 450,
				ondestroy : function(action) {
					if(action == 'ok') {
						var iframe = this.getIFrameEl();
						var dt = iframe.contentWindow.GetDatas();
						if(dt && dt.length==0){
							mini.alert("您没有选择任何功能按钮!");
							return ;
						}
						
						dt=mini.clone(dt);
						var functionIds = new Array();
						for(var i=0;i<dt.length;i++){
							functionIds.push(dt[i].id);
						}
						data.menuId= row.id;
						data.functionIds = functionIds.join(",");
						
						$.ajax({
							'url': "${pageContext.request.contextPath}/syswin/function/add_menu_buttons",
							type: 'post', dataType:'JSON', cache: false, async:false,
							data: data,
							success: function (json) {
								mini.alert("添加成功");
								menuButtonsGrid.reload();
							},
							error : function(data) {
						  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
						  		mini.alert(data.responseText);
							}
						});
					}
				}
			});
		}
		
		// ------------------------------------ 菜单相关 ----------------------
		function repairNodePath() {
			$.ajax({
				'url': "${pageContext.request.contextPath}/syswin/menu/repair_node_path",
				type: 'post',
				dataType:'JSON',
				cache: false,
				async:false,
				success: function (json) {
					mini.alert("修复成功");
					grid.reload();
				},
				error : function(data) {
			  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
			  		mini.alert(data.responseText);
				}
			});
			
		}
		
		function search() {
			var data = form.getData();
			
			var createTimeBegin = mini.get('createTimeBegin').text;
			var createTimeEnd = mini.get('createTimeEnd').text;
			
			data.createTimeBegin = createTimeBegin;
			data.createTimeEnd = createTimeEnd;
			
			var key2 = mini.get("key2").value;
			if( (data.key==null || data.key=="") && (key2!=null && key2!="")){
				data.key = key2;
			}
			
			grid.load(data);
		}

		function remove() {
			var row = grid.getSelected();
			if(row) {
				mini.confirm("删除当前菜单，其子菜单也即将删除，确定删除？", "确定？",
						function (action) {
							if (action == "ok") {
								$.ajax({
									'url': "${pageContext.request.contextPath}/org/delete?ids="+row.id,
									type: 'post',
									dataType:'JSON',
									cache: false,
									async:false,
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
			} else {
				mini.alert("请选中一条以上的记录");
			}
		}
		
		function add() {
			var row = grid.getSelected();
			var idNotIn = null;
			var pid = null;
			var pName = null;
			if(row){
				idNotIn = row.id;
				pid = row.id;
				pName = row.name;
			}else{
				idNotIn = -1;
				pid = -1;
			}
			
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/syswin/uum/menu/edit.jsp",
				title : "添加菜单",
				width : 490,
				height : 250,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {
						action : "add",
						pid : pid ,
						pName　:　pName,
						idNotIn : idNotIn
						
					};
					//alert(data.pid+" "+data.pName+" "+data.idNotIn);
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					grid.reload();
				}
			});
				
		}
		

		function edit(action) {
			var row = grid.getSelected();
			if(typeof(action) == 'undefined') {
				action = "edit";
			}
			
			if (row) {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/syswin/uum/menu/edit.jsp",
					title : "编辑菜单信息",
					width : 490,
					height : 250,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : action,
							id : row.id
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						grid.reload();
					}
				});
			} else {
				mini.alert("请选中一条记录");
			}
		}
		
		</script>
	</body>
</html>