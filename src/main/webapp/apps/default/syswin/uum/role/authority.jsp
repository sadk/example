<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>角色授权管理</title>
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
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <span style="padding-left:5px;">分类名：</span>
		            <input class="mini-textbox" width="100px"/>
		            <a class="mini-button" iconCls="icon-search" plain="true">查找</a>                  
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/syswin/role_category/all" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" >        
		            </ul>
		        </div>
			</div>
			<div showCollapseButton="true">
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						    <div title="角色列表" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="add()">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
												<span class="separator"></span>
												<!-- <a class="mini-button" iconCls="icon-node" onclick="manageRoleCategory()">管理角色分类</a>
												
												
												<a class="mini-button" iconCls="icon-node" onclick="edit('view')">查看</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
												<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
												<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
												 -->
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search()">查询</a>
						                    </td>
										</tr>
									</table>
								</div>
						
								<div class="mini-fit">
									<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
									url="${pageContext.request.contextPath}/syswin/role/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
									<div property="columns">
										<div type="checkcolumn" ></div>
										<div field="id" width="60" headerAlign="center" allowSort="true" align="center">角色ID</div>
										<div field="name" width="120" headerAlign="center" allowSort="true" align="left">名称</div>
										<div field="code" width="80" headerAlign="center" allowSort="true" align="center">编码</div>
										<div field="categoryId" width="60" headerAlign="center" allowSort="true" align="center">分类ID</div>
										<div field="categoryName" width="80" headerAlign="center" allowSort="true" align="left">分类名称</div>
										
										<div field="typeDesc" width="60" headerAlign="center" allowSort="true" align="center">角色类型</div>
										<div field="description" width="160" headerAlign="center" allowSort="true" align="center">描述</div>
										
										<div field="createTime" dateFormat="yyyy-MM-dd" width="100" headerAlign="center" allowSort="true" align="center">创建日期</div>
										<div field="updateTime" dateFormat="yyyy-MM-dd" width="100" headerAlign="center" allowSort="true" align="center">更新日期</div>
										
									</div>
									</div>
								</div>
						    </div>
						</div>
						
					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							

							<div title="菜单权限" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addMenu()">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeMenu()">删除</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="menuGrid" class="mini-treegrid" style="width:100%;height:100%;"
									showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="false" 
				    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="false"  showCheckBox="true" 
									url="${pageContext.request.contextPath}/syswin/menu/all_selector" > 
									    <div property="columns">
									    	<!-- 
									        <div type="checkcolumn"></div>
									         -->
									        <div type="indexcolumn"></div>
									        <div name="name" field="name" width="160" headerAlign="center">名称</div>
									        <div field="code" width="80" headerAlign="center">编码</div>
									        <!-- 
									        <div field="sn" width="30" align="right" headerAlign="center">序号</div>
									        <div field="appCode" width="60" align="left">所属应用</div>
									         -->
									        <div field="url" width="160" align="left">菜单URL</div>
									        <div field="nodePath" width="120" align="left">结点路径</div>
									        
									        <!-- 
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
									         -->
									        <div name="id" field="id" width="30" >ID</div>
									        <div name="pid" field="pid" width="30" >父ID</div>              
									    </div>
									</div>
								</div>
							</div>


							<div title="功能权限" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-save" onclick="saveAuthority()">保存权限</a>
											</td>
											 
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="functionGrid" class="mini-treegrid" style="width:100%;height:100%;"
									showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="false" 
				    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="false"  showCheckBox="true" 
									url="${pageContext.request.contextPath}/syswin/function/get_resource_tree" > 
									    <div property="columns">
									    	<!-- 
									        <div type="checkcolumn"></div>
									         -->
									        <div type="indexcolumn"></div>
									        <div name="name" field="name" width="160" headerAlign="center">名称</div>
									        <div field="code" width="80" headerAlign="center">编码</div>
									        <!-- 
									        <div field="sn" width="30" align="right" headerAlign="center">序号</div>
									        <div field="appCode" width="60" align="left">所属应用</div>
									         -->
									        <div field="url" width="160" align="left">菜单URL</div>
									        <div field="nodePath" width="120" align="left">结点路径</div>
									        
									        <!-- 
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
									         -->
									        <div name="id" field="id" width="60" >ID</div>
									        <div name="pid" field="pid" width="60" >父ID</div>              
									    </div>
									</div>
								</div>
							</div>


							<div title="数据查询权限" refreshOnClick="true" name="tabReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addRangeValue()">添加查询权限</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeRangeValue()">删除查询权限</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-save" onclick="saveRangeValue()">保存</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key_oro" name="key_oro" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="searchRangeValue()">搜索</a>
						                    </td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="rangeValueGrid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
									url="${pageContext.request.contextPath}/syswin/range_value/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" 
									 allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true">
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="id" width="60" headerAlign="center" allowSort="true" align="center">ID</div>
											<div field="code" width="160" headerAlign="center" allowSort="true" align="center">范围值编码
												<input property="editor" class="mini-textbox" style="width:100%;" minWidth="100" />
											</div>
											<div type="comboboxcolumn" field="rangeType" width="250" headerAlign="center" allowSort="true" align="center">范围值类型
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" data="[{name:'静态值',value:'1'},{name:'动态值',value:'2'}]" />
											</div>
											<div  field="rangeValue" width="250" headerAlign="center" allowSort="true" align="center">范围值
												<input property="editor" class="mini-textarea" style="width:100%;" minWidth="100" />
											</div>
											<div field="description" width="80" headerAlign="center" allowSort="true" align="center">描述
												<input property="editor" class="mini-textbox" style="width:100%;" minWidth="100" />
											</div>
											
											<div field="rangeId" width="60" headerAlign="center" allowSort="true" align="center">范围定义ID</div>
											<div field="createTime" dateFormat="yyyy-MM-dd" width="80" headerAlign="center" allowSort="true" align="center">创建日期</div>
											<div field="updateTime" dateFormat="yyyy-MM-dd" width="80" headerAlign="center" allowSort="true" align="center">更新日期</div>
										
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
			
			var tree = mini.get("tree1");
			var grid = mini.get("datagrid1"); // 角色列表
			var menuGrid = mini.get("menuGrid");// 角色下的用户
			var functionGrid = mini.get("functionGrid"); //  角色下的资源
			var rangeValueGrid = mini.get("rangeValueGrid")// 角色下的数据查询权限
			
			grid.load();
			
	        tree.on("nodeselect", function (e) {
	        	grid.load({ categoryId: e.node.categoryId });
	        	
	        });

	        grid.on("rowclick", function(e){
				var record = e.record;
				loading();
				menuGrid.load({roleId:record.id});
				rangeValueGrid.load({roleId:record.id});
				functionGrid.load({roleId:record.id},function(){
					//functionGrid.setValue("101,107");
					doTreeSelect(record.id);
				});
				
			});
	        
	        function saveRangeValue() {
			    var data = rangeValueGrid.getChanges();
	            var json = mini.encode(data);
	          
	            rangeValueGrid.loading("保存中，请稍后......");
	           // if(true) return ;
	            $.ajax({
	                url: "${pageContext.request.contextPath}/syswin/range_value/save_or_update_short",
	                data: { data: json },
	                type: "post",
	                success: function (text) {
	                	rangeValueGrid.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
	        }
	        
	        function removeRangeValue() { // 添加查询权限
	        	var rangeValueIds = new Array();
	        	var rows= rangeValueGrid.getSelecteds();
	        	if(rows.length == 0){
	        		mini.alert('请选择要删除的查询权限');
	        		return ;
	        	}
	        	for(var i=0;i<rows.length;i++){
	        		rangeValueIds.push(rows[i].id);
	        	}
	        	$.ajax({
					'url': "${pageContext.request.contextPath}/syswin/range_value/delete?ids="+rangeValueIds.join(","),
					type: 'post', dataType:'JSON', cache: false, async:false,
					success: function (json) {
						rangeValueGrid.reload();
					},
					error : function(data) {
				  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
				  		mini.alert(data.responseText);
					}
				});
	        	
	        }
	        
	        function addRangeValue() { // 添加查询权限
	        	var roleRow = grid.getSelected();
	        	if(!roleRow) {
	        		mini.alert("请选择一个角色!");
	        		return ;
	        	}
	        	
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/syswin/uum/range/selector_range.jsp",
					title : "添加查询权限",
					width : 650,
					height : 500,
					ondestroy : function(action) {
						if (action == "ok") {
							var iframe = this.getIFrameEl();
							var rows = iframe.contentWindow.GetDatas();
							if(rows && rows.length==0){
								mini.alert("您没有选择任务记录!");
								return ;
							}
							
							var rangeIds = new Array();
							for(var i=0;i<rows.length;i++){
								rangeIds.push(rows[i].id);
								
							}
							var data = {}
				        	data.roleId=roleRow.id;
							data.rangeIds = rangeIds.join(",");
							$.ajax({
								'url': "${pageContext.request.contextPath}/syswin/role/save_role_range_values",
								type: 'post', dataType:'JSON', cache: false, async:false,data: data,
								success: function (json) {
									rangeValueGrid.reload();
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
	        
	        function searchRangeValue () {

	        	
	        }
	        
	        function saveAuthority(){
	        	var roleRow = grid.getSelected();
	        	if(!roleRow) {
	        		mini.alert("请选择一个角色!");
	        		return ;
	        	}
	        	
	        	var data={};
	        	data.roleId = roleRow.id;
	        	data.nodeIds = functionGrid.getValue(true);
	        	
	        	$.ajax({
					'url': "${pageContext.request.contextPath}/syswin/function/save_function_and_menu_authority",
					type: 'post', dataType:'JSON', cache: false, async:false,data: data,
					success: function (json) {
						if(json && json.length>0) {
							var treeSelectIds = new Array();
							for(var i=0;i<json.length;i++){
								treeSelectIds.push(json[i].id);
							}
							functionGrid.setValue(treeSelectIds.join(","));
							mini.alert("保存成功!");
						}
					},
					error : function(data) {
				  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
				  		mini.alert(data.responseText);
					}
				});
	        }
	        
	        function doTreeSelect(roleId){
	        	$.ajax({
					'url': "${pageContext.request.contextPath}/syswin/function/get_resource_tree_selected?roleId="+roleId,
					type: 'post', dataType:'JSON', cache: false, async:false,
					success: function (json) {
						if(json && json.length>0) {
							var treeSelectIds = new Array();
							for(var i=0;i<json.length;i++){
								treeSelectIds.push(json[i].id);
							}
							functionGrid.setValue(treeSelectIds.join(","));
						}
					},
					error : function(data) {
				  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
				  		mini.alert(data.responseText);
					}
				});
	        }
	        
	        function addFunction() {
	        	var roleRow = grid.getSelected();
	        	if(!roleRow) {
	        		mini.alert("请选择一个角色!");
	        		return ;
	        	}
	        	var roleId = roleRow.id;
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/syswin/uum/button/selector_function.jsp?roleId="+roleRow.id,
					title : "添加功能",
					width : 750,
					height : 500,
					ondestroy : function(action) {
						if (action == "ok") {
							var iframe = this.getIFrameEl();
							var rows = iframe.contentWindow.GetData();
							if(rows && rows.length==0){
								mini.alert("您没有选择任务功能!");
								return ;
							}
							rows = mini.clone(rows);
							var menuFuncIds = new Array();
							for(var i=0;i<rows.length;i++) {
								menuFuncIds.push(rows[i].id);
							}
							//console.log(menuFuncIds);
							
							var data = {};
							data.menuFuncIds = menuFuncIds.join(",");
							data.roleId = roleRow.id;
							
							$.ajax({
								'url': "${pageContext.request.contextPath}/syswin/role/save_role_functions",
								type: 'post', dataType:'JSON', cache: false, async:false,
								data : data,
								success: function (json) {
									mini.alert("添加成功!");
									menuGrid.reload();
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
	        
	        // ---------------------------------  
	        function addMenu(){
	        	var roleRow = grid.getSelected();
	        	if(!roleRow) {
	        		mini.alert("请选择一个角色!");
	        		return ;
	        	}
	        	var roleId = roleRow.id;
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/syswin/uum/menu/selector_menu_mutil.jsp",
					title : "添加菜单",
					width : 750,
					height : 500,
					onload : function() {
						/*
						var iframe = this.getIFrameEl();
						var data = {
							action : "add"
						};
						iframe.contentWindow.SetData(data);
						*/
					},
					ondestroy : function(action) {
						if (action == "ok") {
							var iframe = this.getIFrameEl();
							var rows = iframe.contentWindow.GetData();
							if(rows && rows.length==0){
								mini.alert("您没有选择菜单!");
								return ;
							}
							rows = mini.clone(rows);
							var menuIds = new Array();
							for(var i=0;i<rows.length;i++) {
								menuIds.push(rows[i].id);
							}
							//console.log(menuIds);
							
							var data = {};
							data.menuIds = menuIds.join(",");
							data.roleId = roleRow.id;
							
							$.ajax({
								'url': "${pageContext.request.contextPath}/syswin/role/save_role_menus",
								type: 'post', dataType:'JSON', cache: false, async:false,
								data : data,
								success: function (json) {
									mini.alert("添加成功!");
									menuGrid.reload();
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
	        
	        function removeMenu() {
	        	var roleRow = grid.getSelected();
	        	if(!roleRow){
	        		mini.alert("请选择一个角色!");
	        		return ;
	        	}
	        	
	        	var menuIds = menuGrid.getValue();
	        	
	        	if(menuIds == null || menuIds == '') {
	        		mini.alert("请选择要删除的菜单!");
	        		return ;
	        	}
	        	 
	        	var data = {};
	        	data.roleId = roleRow.id;
	        	data.menuIds = menuIds;
				$.ajax({
					'url': "${pageContext.request.contextPath}/syswin/role/delete_role_menus",
					type: 'post', dataType:'JSON', cache: false, async:false,
					data: data,
					success: function (json) {
						mini.alert("删除成功!");
						menuGrid.reload();
					},
					error : function(data) {
				  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
				  		mini.alert(data.responseText);
					}
				});
	        }
	        
			function search() {
				var data = {};
				var key2 = mini.get("key2").value;
				if( (data.key==null || data.key=="") && (key2!=null && key2!="")){
					data.key = key2;
				}
				grid.load(data);
			}
			

			
			function remove() {
				var row = grid.getSelecteds();
				if (!row) {
					mini.alert("请选中一条以上的记录");
				}
				var ids = [];
				for(var i=0;i<row.length;i++) {
					ids.push(row[i].id);
				}
				mini.confirm("确定删除？", "确定？",
					function (action) {
						if (action == "ok") {
							$.ajax({
								'url': "${pageContext.request.contextPath}/role/delete?ids="+ids.join(","),
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
			}
			
			function add() {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/uum/role/edit.jsp",
					title : "添加角色",
					width : 500,
					height : 380,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : "add"
						};
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
				//alert(row.id);
				if (row) {
					mini.open({
						url : "${pageContext.request.contextPath}/apps/default/admin/uum/role/edit.jsp",
						title : "编辑角色信息",
						width : 500,
						height : 380,
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
			
			
			
			
	        function addUser() { //添加角色下的用户     
	        	var row = grid.getSelected();
		        if(!row){
		        	mini.alert("请选择一个角色");
		        	return ;
		        }
		        
    			mini.open({
    				url : "${pageContext.request.contextPath}/apps/default/admin/uum/user/selector_user.jsp",
    				title : "添加角色下的用户",
    				width : 600,
    				height : 500,
    				ondestroy : function(action) {
    					if (action == "ok") {
    			            var iframe = this.getIFrameEl();
    			            var datas = iframe.contentWindow.GetDatas();
    			            datas = mini.clone(datas);    //必须。克隆数据。
    			            //alert(mini.encode(datas));
    			            if(!datas)return;
    			            
    			            var uids = new Array();
    			            for(var i=0;i<datas.length;i++){
    			            	uids.push(datas[i].id);
    			            }
    			            
    			            var data = {userIds:uids.join(","),roleId:row.id};
    			            $.ajax({
    							url : "${pageContext.request.contextPath}/role/add_user_to_role",
    							dataType: 'json',
    							type : 'post',
    							cache : false,
    							data: data,
    							success : function(text) {
    								//grid.reload();
    								userGrid.reload();
    							}
    						});
    			        }  
    					
    				}
    			});          
	        }
			
	        
			function removeUser() { // 删除角色下的用户
				var row = grid.getSelected();
				if (!row) {
					mini.alert("请选中一个角色记录");
					return ;
				}
				var urow = userGrid.getSelecteds();
				if(!urow) {
					mini.alert("请选择至少一个用户");
					return ;
				}
				
				var uids = new Array();
				for(var i=0;i<urow.length;i++) {
					uids.push(urow[i].id);
				}
				var data = {roleId: row.id,userIds:uids.join(",")};
				
				mini.confirm("确定删除？", "确定？",
					function (action) {
						if (action == "ok") {
							$.ajax({
								'url': "${pageContext.request.contextPath}/role/remove_user_from_role",
								type: 'post',
								dataType:'JSON',
								data : data,
								cache: false,
								async:false,
								success: function (json) {
									mini.alert("删除成功");
									userGrid.reload();
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
			
			
	        function addRes() { //添加角色下的资源     
	        	var row = grid.getSelected();
		        if(!row){
		        	mini.alert("请选择一个角色");
		        	return ;
		        }
		        
    			mini.open({
    				url : "${pageContext.request.contextPath}/apps/default/admin/uum/res/selector_res.jsp",
    				title : "添加角色下的资源",
    				width : 600,
    				height : 500,
    				ondestroy : function(action) {
    					if (action == "ok") {
    			            var iframe = this.getIFrameEl();
    			            var datas = iframe.contentWindow.GetData();
    			            datas = mini.clone(datas);    //必须。克隆数据。
    			            //alert(mini.encode(datas));
    			            if(!datas)return;
    			            
    			            var uids = new Array();
    			            for(var i=0;i<datas.length;i++){
    			            	uids.push(datas[i].id);
    			            }
    			            
    			            var data = {resIds:uids.join(","),roleId:row.id};
    			            $.ajax({
    							url : "${pageContext.request.contextPath}/role/add_res_to_role",
    							dataType: 'json',
    							type : 'post',
    							cache : false,
    							data: data,
    							success : function(text) {
    								resGrid.reload();
    							}
    						});
    			        }  
    					
    				}
    			});          
	        }
	        
	        
	        
			function removeRes() { // 删除角色下的资源
				var row = grid.getSelected();
				if (!row) {
					mini.alert("请选中一个角色记录");
					return ;
				}
				var urow = resGrid.getSelecteds();
				if(!urow) {
					mini.alert("请选择至少一个资源");
					return ;
				}
				
				var uids = new Array();
				for(var i=0;i<urow.length;i++) {
					uids.push(urow[i].id);
				}
				var data = {roleId: row.id,resIds:uids.join(",")};
				
				mini.confirm("确定删除？", "确定？",
					function (action) {
						if (action == "ok") {
							$.ajax({
								'url': "${pageContext.request.contextPath}/role/delete_res_from_role",
								type: 'post',
								dataType:'JSON',
								data : data,
								cache: false,
								async:false,
								success: function (json) {
									mini.alert("删除成功");
									resGrid.reload();
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
			
		    function loading() {
		        mini.mask({
		            el: document.body,
		            cls: 'mini-mask-loading',
		            html: '加载中...'
		        });
		        setTimeout(function () {
		            mini.unmask(document.body);
		        }, 500);
		    }
		</script>
	</body>
</html>