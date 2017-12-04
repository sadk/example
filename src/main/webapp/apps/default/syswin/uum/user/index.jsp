<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户管理</title>
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
									<td>姓名：</td>
									<td>
										<input id="userName" name="userName"   class="mini-textbox"  emptyText="请输入姓名"  onenter="search"/>
									</td>
								</tr>
								
								<tr>
									<td>帐号：</td>
									<td>
										<input id="loginNo" name="loginNo"   class="mini-textbox"  emptyText="请输入帐号"  onenter="search"/>
									</td>
								</tr>
								<tr>
									<td>工号：</td>
									<td>
										<input id="workNo" name="workNo"   class="mini-textbox"  emptyText="请输入工号"  onenter="search"/>
									</td>
								</tr>
							
								<!-- 
								<tr>
									<td>状态 </td>
									<td>
										<input id="status" name="status" value="1" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=user_status" />
									</td>
								</tr>
								 -->
								<tr>
									<td>邮箱：</td>
									<td>
										<input id="email" name="email"   class="mini-textbox"  emptyText="请输入邮箱"  onenter="search"/>
									</td>
								</tr>
								
								<tr>
									<td>手机：</td>
									<td>
										<input id="mobile" name="mobile"   class="mini-textbox"  emptyText="请输入手机"  onenter="search"/>
									</td>
								</tr>
								
								<tr>
									<td>电话：</td>
									<td>
										<input id="telephone" name="telephone"   class="mini-textbox"  emptyText="请输入电话"  onenter="search"/>
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
						    <div title="用户列表" refreshOnClick="true">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="editUser('add')">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeUser()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editUser('edit')">编辑</a>
												<!-- 
												<a class="mini-button" iconCls="icon-node" onclick="edit('view')">查看</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
												<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
												<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
												 -->
											</td>
											
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
									<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
									url="${pageContext.request.contextPath}/syswin/user/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="userId" width="80" headerAlign="center" allowSort="true" align="center">ID</div>
											<div field="userName" width="160" headerAlign="center" allowSort="true" align="center">姓名</div>
											<div field="loginNo" width="160" headerAlign="center" allowSort="true" align="center">帐号</div>
											<div field="workNo" width="160" headerAlign="center" allowSort="true" align="center">工号</div>
											<div field="statusDesc" width="160" headerAlign="center" allowSort="true" align="center">状态</div>
											<div field="email" width="160" headerAlign="center" allowSort="true" align="center">邮箱</div>
											<div field="mobile" width="160" headerAlign="center" allowSort="true" align="center">手机</div>
											<div field="telephone" width="160" headerAlign="center" allowSort="true" align="center">电话</div>
											<div field="sexDesc" width="80" headerAlign="center" allowSort="true" align="center">性别</div>
											<!-- <div field="appCode" width="160" headerAlign="center" allowSort="true" align="center">系统编码</div>
											<div field="gid" width="160" headerAlign="center" allowSort="true" align="center">全局编码</div> -->
											<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
											<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">更新日期</div>
										</div>
									</div>
						        </div>
						    </div>
						    
						</div>
					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							
							<div title="用户的岗位" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addPosition()">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removePosition()">删除</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-edit" onclick="setMainPosition()">设为主岗</a>
												<a class="mini-button" iconCls="icon-edit" onclick="fixOneMainPosition()">处理用户多个主岗</a>
												<a class="mini-button" iconCls="icon-reload" onclick="reloadUserPosition()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="positionGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" 
										multiSelect="true" allowResize="false" sizeList="[20,50,100,150,200]" pageSize="50"  
					 					url="${pageContext.request.contextPath}/syswin/position/page">
										<div property="columns">
									        <div type="checkcolumn" ></div>
									        <div field="id" width="60" headerAlign="center" align="center">岗位ID</div>
									        <div field="name" width="160" headerAlign="center">岗位名称</div>
									        <div field="mainPositionDesc" width="60" headerAlign="center" align="center">主岗</div>
									        <div field="directId" width="80" headerAlign="center">直属上级ID</div>
									        <div field="directName" width="80" headerAlign="center">直属上级名称</div>
									        <div field="typeDesc" width="80" headerAlign="center">岗位级别</div>
									        
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
											
										</div>
									</div>
								</div>
							</div>
							
							
							
							<div title="用户的组织" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-reload" onclick="reloadUserOrgs()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="userOrgsGrid" class="mini-treegrid" style="width:100%;height:100%;"
										showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="false" 
					    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false" 
										url="${pageContext.request.contextPath}/syswin/org/list" >
										<div property="columns">
									        <div type="indexcolumn"></div>
									        <div name="name" field="name" width="160" headerAlign="center">名称</div>
									        <div field="typeDesc" width="80" headerAlign="center" align="center">类型</div>
									        <div field="sn" width="30" align="right" headerAlign="center">序号</div>
									        <div field="nodePath" width="100" align="left">结点路径</div>
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
									        <div name="id" field="id" width="30" >ID</div>
									        <div name="pid" field="pid" width="30" >父ID</div> 
										</div>
									</div>
								</div>
							</div>
							


							<div title="用户的角色" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-reload" onclick="reloadUserRoles()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="userRolesGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" 
										multiSelect="true" allowResize="false" sizeList="[20,50,100,150,200]" pageSize="50"  
					 					url="${pageContext.request.contextPath}/syswin/role/page">
										<div property="columns">
									        <div type="checkcolumn" ></div>
											<div field="id" width="60" headerAlign="center" allowSort="true" align="center">角色ID</div>
											<div field="name" width="120" headerAlign="center" allowSort="true" align="left">名称</div>
											<div field="code" width="80" headerAlign="center" allowSort="true" align="center">编码</div>
											<div field="categoryId" width="60" headerAlign="center" allowSort="true" align="center">分类ID</div>
											<div field="categoryName" width="80" headerAlign="center" allowSort="true" align="left">分类名称</div>
											
											<div field="typeDesc" width="60" headerAlign="center" allowSort="true" align="center">角色类型</div>
											<div field="description" width="160" headerAlign="center" allowSort="true" align="center">系统编码</div>
											
											<div field="createTime" dateFormat="yyyy-MM-dd" width="120" headerAlign="center" allowSort="true" align="center">创建日期</div>
											<div field="updateTime" dateFormat="yyyy-MM-dd" width="120" headerAlign="center" allowSort="true" align="center">更新日期</div>
											
										</div>
									</div>
								</div>
							</div>


							<div title="用户的菜单和功能权限" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-reload">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="userMenuFunctionAuthorityGrid" class="mini-treegrid" style="width:100%;height:100%;"
									showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="false" 
				    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="false"  showCheckBox="false" 
									url="${pageContext.request.contextPath}/syswin/function/get_resource_tree_for_user" >
										<div property="columns">
									        <div type="checkcolumn" ></div>
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



							<div title="用户的数据查询权限" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-reload">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="userRangeValueGrid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
									url="${pageContext.request.contextPath}/syswin/range_value/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
										<div property="columns">
									        <div type="checkcolumn" ></div>
											<div field="id" width="60" headerAlign="center" allowSort="true" align="center">ID</div>
											<div field="code" width="80" headerAlign="center" allowSort="true" align="center">范围值编码</div>
											<div field="rangeValue" width="250" headerAlign="center" allowSort="true" align="center">范围值</div>
											<div field="description" width="160" headerAlign="center" allowSort="true" align="center">描述</div>
											
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
			
			var form = new mini.Form("#form1");
			function clear() {
				 form.clear();
			}
			
			var grid = mini.get("datagrid1");  // 用户表格
			var positionGrid = mini.get("positionGrid"); //用户的岗位    
			var userOrgsGrid = mini.get("userOrgsGrid"); // 用户的部门
			var userRolesGrid = mini.get("userRolesGrid"); //用户的角色
			var userMenuFunctionAuthorityGrid = mini.get("userMenuFunctionAuthorityGrid"); //用户的权限
			var userRangeValueGrid = mini.get("userRangeValueGrid"); // 用户的数据查询范围权限
			
			
			
			grid.load();
			positionGrid.load({id: -1});
			//userRangeValueGrid.load({userId:-1})
			
			grid.on("rowclick", function(e){
				var record = e.record;
				//console.log(record);
				var data ={userId:record.userId};
				
				positionGrid.load(data); 
				userOrgsGrid.load(data);
				userRolesGrid.load(data);
				userMenuFunctionAuthorityGrid.load(data);
				userRangeValueGrid.load(data);
			});
			
			function reloadUserPosition(){
				positionGrid.reload(); 
			}
			
			function fixOneMainPosition() {
				  mini.confirm("确定处理数据：所有用户只能有一个主岗？", "确定？",
				      function (action) {
						  $.ajax({'url': "${pageContext.request.contextPath}/syswin/position/fix_one_main_position",
			   					type: 'post', dataType:'JSON', cache: false, async:false,
			   				//	data: data,
			   					success: function (json) {
			   						mini.alert("设置用户主岗位成功!");
			   						positionGrid.reload();
			   					},
			   					error : function(data) {
			   				  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
			   				  		mini.alert(data.responseText);
			   					}
							});
				  });
			}
			
			function setMainPosition() {
				var userRow= grid.getSelected();
 				if(!userRow){
 					mini.alert("请选择一个用户");
 					return ;
 				}
 				var poses = positionGrid.getSelected();
 				if(!poses){
 					mini.alert("请至少选择一个岗位作为主岗位");
 					return ;
 				}
				var data = {}
				data.userId=userRow.userId;
				data.positionId = poses.id;
		        mini.confirm("确定设置主岗位？", "确定？",
		            function (action) {
		                if (action == "ok") {
		    				$.ajax({'url': "${pageContext.request.contextPath}/syswin/position/set_user_main_positions",
			   					type: 'post', dataType:'JSON', cache: false, async:false,
			   					data: data,
			   					success: function (json) {
			   						mini.alert("设置用户主岗位成功!");
			   						positionGrid.reload();
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
			
 			function removePosition() { // 删除用户的岗位
 				var user= grid.getSelected();
 				if(!user){
 					mini.alert("请选择一个用户");
 					return ;
 				}
 				
 				var poses = positionGrid.getSelecteds();
 				if(poses.length == 0){
 					mini.alert("请至少选择一个岗位");
 					return ;
 				}
 				var positionIds = new Array();
 				var data = {}
 				for(var i=0;i<poses.length;i++){
 					positionIds.push(poses[i].id);
 				}
 				data.userId = user.userId;
 				data.positionIds = positionIds.join(",");
				$.ajax({
					'url': "${pageContext.request.contextPath}/syswin/user/remove_user_positions",
					type: 'post', dataType:'JSON', cache: false, async:false,
					data: data,
					success: function (json) {
						mini.alert("删除用户岗位成功!");
						positionGrid.reload();
					},
					error : function(data) {
				  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
				  		mini.alert(data.responseText);
					}
				});
 			}
 			
			function addPosition() { // 给用户添加岗位
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/syswin/uum/position/selector_postion.jsp",
					title : "添加用户的岗位",
					width : 800,
					height : 600,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							id : row.userId
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						if(action != 'ok'){
							return ;
						}
						
						var userRow = grid.getSelected();
						if(!userRow){
							mini.alert("请选择一个用户!");
							return ;
						}
						
						var iframe = this.getIFrameEl();
						var positionRows = iframe.contentWindow.GetDatas();
						if(positionRows && positionRows.length==0) {
							mini.alert("您没有选择岗位!");
							return ;
						}
						
						positionRows = mini.clone(positionRows);
						
						var data = {};
						var positionIds = [];
						for(var i=0;i<positionRows.length;i++) {
							positionIds.push(positionRows[i].id);
						}
						data.positionIds = positionIds.join(",");
						data.userId = userRow.userId;
						
						$.ajax({
							'url': "${pageContext.request.contextPath}/syswin/user/add_user_positions",
							type: 'post', dataType:'JSON', cache: false, async:false,
							data: data,
							success: function (json) {
								mini.alert("添加成功!");
								positionGrid.reload();
							},
							error : function(data) {
						  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
						  		mini.alert(data.responseText);
							}
						});
						
					}
				});
			} 
			
			function search() { // 查询用户
				var data = form.getData();
				/*
				var createTimeBegin = mini.get('createTimeBegin').text;
				var createTimeEnd = mini.get('createTimeEnd').text;
				
				data.createTimeBegin = createTimeBegin;
				data.createTimeEnd = createTimeEnd;
				*/
				grid.load(data)
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
			
		    function refreshRes(){
				loading();
				positionGrid.reload();
			}
		    
			function refreshAllGrid() {
				loading();
				grid.reload();
				positionGrid.reload();
			}
			
			function removeUser() {
				var row = grid.getSelecteds();
				if (!row) {
					mini.alert("请选中一条以上的记录");
				}
				var ids = [];
				for(var i=0;i<row.length;i++) {
					ids.push(row[i].userId);
				}
				console.log(ids);
				mini.confirm("确定删除？", "确定？",
					function (action) {
						if (action == "ok") {
							$.ajax({
								'url': "${pageContext.request.contextPath}/syswin/user/delete?ids="+ids.join(","),
								type: 'post',
								dataType:'JSON',
								cache: false,
								async:false,
								success: function (json) {
									mini.alert("删除成功");
									refreshAllGrid();
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

			
			function editUser(action) {
				var row = grid.getSelected();
				if(typeof(action) == 'undefined') {
					action = "edit";
				}
				
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/syswin/uum/user/edit.jsp",
					title : "编辑用户信息",
					width : 490,
					height : 400,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : action,
							id : row.userId
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						grid.reload();
					}
				});
				
			}
		   
		</script>
	</body>
</html>