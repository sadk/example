<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>岗位角色</title>
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
		            <span style="padding-left:5px;">名称：</span>
		            <input class="mini-textbox" width="120"/>
		            <a class="mini-button" iconCls="icon-search" plain="true">查找</a>                  
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/syswin/org/all" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" >        
		            </ul>
		        </div>
		    </div>
			<div showCollapseButton="true">
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						    <div title="组织下的岗位" refreshOnClick="true">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="editPosition('add')">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removePosition()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editPosition('edit')">编辑</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-date" onclick="repairNodePath('all')">修复所有节点路径</a>
												<a class="mini-button" iconCls="icon-date" onclick="repairNodePath('emptyPath')">修复节点路径为空的岗位</a>
												<!-- 
												<a class="mini-button" iconCls="icon-node" onclick="edit('view')">查看</a>
												
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
												<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
												<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
												 -->
											</td>
											<td style="white-space:nowrap;">
						                        <input id="keyPosition" name="keyPosition" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="searchPosition()">查询</a>
						                    </td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
									<div id="positionGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" 
										multiSelect="true" allowResize="false" sizeList="[20,50,100,150,200]" pageSize="20"  
					 					url="${pageContext.request.contextPath}/syswin/position/page"> 
									    <div property="columns">
									        <div type="checkcolumn"></div>
									        <div name="id" field="id" width="60" headerAlign="center">岗位ID</div>
									        <div name="name" field="name" width="160" headerAlign="center">岗位名称</div>
									        <div name="nodePath" field="nodePath" width="160" headerAlign="center">(岗位上下级)节点路径</div>
									        <div name="code" field="code" width="160" headerAlign="center">编码</div>
									        <div field="orgNodeText" width="460" headerAlign="center">组织节点</div>
									        <div field="typeDesc" width="80" headerAlign="center" align="center">岗位级别</div>
									        
									        <div field="pid" width="80" headerAlign="center">直属上级ID</div>
									        <div field="parentName" width="80" headerAlign="center">直属上级名称</div>
									        <div field="uuidHR" width="220" headerAlign="center">HR映射ID</div>
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
											              
									    </div>
									</div>
						        </div>
						    </div>
						    
						    
						    
						    
						    <div title="岗位上下级视图" refreshOnClick="true">
						    	
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												
												<a class="mini-button" iconCls="icon-reload" onclick="refreshView">刷新</a>
												<!-- <a class="mini-button" iconCls="icon-remove" onclick="removePosition()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editPosition('edit')">编辑</a> -->
												<span class="separator"></span>
												
												
											</td>
											<!-- 
											<td style="white-space:nowrap;">
						                        <input id="keyPosition2" name="keyPosition2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="searchPosition2()">查询</a>
						                    </td>
						                     -->
										</tr>
									</table>
								</div>
						 		
						        <div class="mini-fit">
 									<div id="positionTreeGrid" class="mini-treegrid" style="width:100%;height:100%;"
										showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="false" 
					    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false" 
										url="${pageContext.request.contextPath}/syswin/position/all_pid_not_null"> 
									    <div property="columns">
									        <div type="checkcolumn"></div>
									        <div name="id" field="id" width="60" headerAlign="center">ID</div>
									        
									         
									        <div name="name" field="name" width="220" headerAlign="center">岗位名称</div>
									        <div field="nodePath" width="180" headerAlign="center">(岗位上下级)节点路径</div>
									        
									        <div field="orgNodeText" width="460" headerAlign="center">组织节点</div>
									        <div field="typeDesc" width="80" headerAlign="center">岗位级别</div>
									        
									       
									        <div field="parentName" width="80" headerAlign="center">直属上级名称</div>
									        
									        
									        
									        <!-- 
									         <div name="code" field="code" width="160" headerAlign="center">编码</div>
									        <div field="uuidHR" width="80" headerAlign="center">HR映射ID</div>
									         -->
									        <div field="pid" width="80" headerAlign="center">PID</div>
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
							
							<div title="岗位下的角色" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addRole()">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeRole()">删除</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="roleGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" 
										multiSelect="true" allowResize="false" sizeList="[20,50,100,150,200]" pageSize="20"  
					 					url="${pageContext.request.contextPath}/syswin/role/page">
										<div property="columns">
									        <div type="checkcolumn" ></div>
									        <div name="id" field="id" width="160" headerAlign="center">角色ID</div>
									        <div name="name" field="name" width="160" headerAlign="center">角色名称</div>
									        
									        <div field="categoryName" width="80" headerAlign="center">角色分类</div>
									        <div field="typeDesc" width="80" headerAlign="center">角色类型</div>
									        
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
										</div>
									</div>
								</div>
							</div>
							
							<div title="岗位下的用户" refreshOnClick="true" name="tabUser">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addUser()">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeUser()">删除</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="userGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" 
										multiSelect="true" allowResize="false" sizeList="[20,50,100,150,200]" pageSize="20"  
					 					url="${pageContext.request.contextPath}/syswin/user/page">
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="userId" width="80" headerAlign="center" allowSort="true" align="center">用户ID</div>
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
				</div>
			</div>
		</div>
		<script type="text/javascript">
			mini.parse();
			
	        var tree = mini.get("tree1");
	        var positionGrid = mini.get("positionGrid");
	        var roleGrid = mini.get("roleGrid");
	        var userGrid = mini.get("userGrid");
	       	var positionTreeGrid = mini.get("positionTreeGrid");
	       
	        tree.on("nodeselect", function (e) {
	        	positionGrid.load({ orgId: e.node.id });
	        });
			
	        positionGrid.load();
	        roleGrid.load({id: -1});
 
	        positionGrid.on("rowclick", function(e){
				var record = e.record;
				roleGrid.load({positionId:record.id}); 
				userGrid.load({positionIds : record.id});
			});
	        
			function repairNodePath(type) {
				/*
				mini.mask({
			            el: document.body,
			            cls: 'mini-mask-loading',
			            html: '数据处理中...'
			    });
				*/
				positionGrid.loading("数据处理中，请稍后......");
				$.ajax({
					'url': "${pageContext.request.contextPath}/syswin/position/repair_node_path?type="+type,
					type: 'post',
					dataType:'JSON',
					cache: false,
					async:false,
					success: function (json) {
						positionGrid.unmask();
						mini.alert("修复成功");
						positionGrid.reload();
					},
					error : function(data) {
				  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
				  		mini.alert(data.responseText);
					}
				});
			}

	        function removeUser() {
	        	var positRow = positionGrid.getSelected();
	        	if(!positRow){
	        		mini.alert("请选择一个岗位");
	        		return ;
	        	}
	        	
	        	var rows = userGrid.getSelecteds();
	        	if(rows.length==0){
	        		mini.alert("请选择至少一个用户");
	        		return ;
	        	}
	        	
	        	var userIds = new Array();
				for(var i=0;i<rows.length;i++){
					userIds.push(rows[i].userId);
				}
				
	        	$.ajax({
					url : "${pageContext.request.contextPath}/syswin/position/remove_position_users?positionId="+positRow.id+"&userIds="+userIds.join(","),
					dataType: 'json', type : 'post', cache : false, //data: o,
					success : function(text) {
						userGrid.reload();
					}
				});
	        }
	        
	        function addUser() {
	        	
	        	var positRow = positionGrid.getSelected();
	        	if(!positRow){
	        		mini.alert("请选择一个岗位");
	        		return ;
	        	}
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/syswin/uum/user/selector_user.jsp",
					title : "选择用户",
					width : 600,
					height : 600,
					ondestroy : function(action) {
						if(action == 'ok'){
							var iframe = this.getIFrameEl();
							var data = iframe.contentWindow.GetDatas();
							if(data.length>0){
								var rows = mini.clone(data);
								var userIds = new Array();
								for(var i=0;i<rows.length;i++){
									userIds.push(rows[i].userId);
								}
								
								//uploadUserId.setValue(row.userId);
								//uploadUserName.setValue(row.userName);
								$.ajax({
									url : "${pageContext.request.contextPath}/syswin/position/add_position_users?positionId="+positRow.id+"&userIds="+userIds.join(","),
									dataType: 'json',
									type : 'post',
									cache : false,
									//data: o,
									success : function(text) {
										userGrid.reload();
									}
								});
							} else {
								mini.alert("请选择一个用户");
							}
							
						}
					}
				});
	        }
	        
 

		    function searchPosition() {
		    	var key = mini.get("keyPosition")
		    	positionGrid.load({key:key.getValue()});
		    }
		    
		    function searchPosition2() {
		    	var key = mini.get("keyPosition2")
		    	positionTreeGrid.load({key:key.getValue()});
		    }
		    
		    
		    
	        function addRole() {
	        	var positionRow = positionGrid.getSelected();
	        	if(!positionRow){
	        		mini.alert("请选择一个岗位");
	        		return ;
	        	}
	        	
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/syswin/uum/role/selector_role.jsp",
					title : "添加角色",
					width : 800,
					height : 580,
					onload : function() {
						var iframe = this.getIFrameEl();
						//alert(data.pid+" "+data.pName+" "+data.idNotIn);
						//iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						var iframe = this.getIFrameEl();
						var rows = iframe.contentWindow.GetDatas();
						rows = mini.clone(rows);
						//console.log(rows)
						
						if(rows && rows.length>0) {
							var roleIds = new Array();
							for(var i=0;i<rows.length;i++) {
								roleIds.push(rows[i].id);
							}
							var data = {};
							data.positionId = positionRow.id;
							data.roleIds = roleIds.join(",");
							if(action == 'ok'){
								$.ajax({
									url : "${pageContext.request.contextPath}/syswin/role/save_position_roles",
									type: 'post', dataType:'JSON', cache: false, async:false,
									data: data,
									success: function (json) {
										mini.alert("添加成功!");
										roleGrid.reload();
									},
									error : function(data) {
								  		mini.alert(data.responseText);
									}
								});
							}
						}
						
					}
				});
	        }
	        
	        function removeRole() {
	        	var positionRow = positionGrid.getSelected();
	        	if(!positionRow){
	        		mini.alert("请选择一个岗位!");
	        		return ;
	        	}
	        	var rows = roleGrid.getSelecteds();
	        	if(rows && rows.length ==0) {
	        		mini.alert("请选择至少一个角色!");return ;
	        	}
	        	
	        	var roleIds = new Array();
				for(var i=0;i<rows.length;i++) {
					roleIds.push(rows[i].id);
				}
				
				var data = {};
				data.roleIds = roleIds.join(",");
				data.positionId = positionRow.id;
				
		        mini.confirm("确定删除记录？", "确定？",
		                function (action) {
		                    if (action == "ok") {
		        				$.ajax({
		        					url : "${pageContext.request.contextPath}/syswin/role/delete_position_roles",
		        					type: 'post', dataType:'JSON', cache: false, async:false,
		        					data: data,
		        					success: function (json) {
		        						mini.alert("删除成功!");
		        						roleGrid.reload();
		        					},
		        					error : function(data) {
		        				  		mini.alert(data.responseText);
		        					}
		        				});
		                    } 
		                }
		            );
		        

	        }
	        
			function editPosition(action) {
				var orgRow = tree.getSelected();
				if(!orgRow){
					mini.alert("请选择一个组织!");
					return ;
				}
				
				var positionRow = positionGrid.getSelected();
				var url = "${pageContext.request.contextPath}/apps/default/syswin/uum/position/edit.jsp";
				var data = {};
				if(action == 'add') {
					data.action = "add";
					//data.id=positionRow.id;
					data.orgId = orgRow.id;
				}
				
				if(action == 'edit') {
					data.action = "edit";
					data.id=positionRow.id;
					data.orgId = orgRow.id;
				}
				
				mini.open({
					url : url,
					title : "编辑岗位",
					width : 490,
					height : 250,
					onload : function() {
						var iframe = this.getIFrameEl();
						
						//alert(data.pid+" "+data.pName+" "+data.idNotIn);
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						positionGrid.reload();
						userGrid.reload({positionId: -1 });
						roleGrid.reload({positionId: -1 });
					}
				});
			}
			
			function search() { // 查询岗位
				var data = form.getData();
				
				var createTimeBegin = mini.get('createTimeBegin').text;
				var createTimeEnd = mini.get('createTimeEnd').text;
				
				data.createTimeBegin = createTimeBegin;
				data.createTimeEnd = createTimeEnd;
				
				
				positionGrid.load(data);
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
		   
			function removePosition() {
				var data = {}
				var orgRow = tree.getSelected() ;
				var positionRow = positionGrid.getSelected();
				
				if(!orgRow){
					mini.alert("请选择一个组织机构!");
					return ;
				}
				
				if(!positionRow) {
					mini.alert("请选择一个岗位!");
					return ;
				}
				
				data.orgId = orgRow.id;
				data.position = positionRow.id;
				
				$.ajax({
					url : "${pageContext.request.contextPath}/syswin/org/remove_user_from_org",
					type: 'post', dataType:'JSON', cache: false, async:false,
					data: data,
					success: function (json) {
						mini.alert("删除成功!");
						positionGrid.reload();
					},
					error : function(data) {
				  		mini.alert(data.responseText);
					}
				});
			}
		</script>
	</body>
</html>