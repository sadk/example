<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户权限综合管理</title>
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
			<div size="280" showCollapseButton="true">
			    <div class="mini-panel" showToolbar="true" showHeader="false" style="width:100%;height:100%;">
				     
				    <!--body-->
				    <div style="padding-left:3px;padding-bottom:5px;">
			            <div id="form1" style="padding:5px;">
							<table>						
								<tr>
									<td>关键字 ：</td>
									<td>
										<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
									</td>
								</tr>
								<tr>
									<td>所属应用：</td>
									<td>
										<input id="appCode" name="appCode" class="mini-combobox" style="width:140px"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/application/all" />
									</td>
								</tr>
								<tr>
									<td>机构名称：</td>
									<td>
										<input id="name" name="name"  style="width:140px" class="mini-textbox"  emptyText="请输入机构名称"  onenter="search"/>
									</td>
								</tr>
								
								<tr>
									<td>机构编码：</td>
									<td>
										<input id="code" name="code" class="mini-textbox" style="width:140px" emptyText="请输入编码" onenter="search"/>
									</td>
								</tr>
								<tr>
									<td>备注：</td>
									<td>
										<input id="remark" name="remark" class="mini-textbox" style="width:140px" emptyText="请输入备注" onenter="search"/>
									</td>
								</tr>
								 
								<tr>
									<td>创建时间(开始)：</td>
									<td>
										<input id="createTimeBegin" name="createTimeBegin" class="mini-datepicker" style="width:140px" emptyText="请输入"/>
									</td>
								</tr>
								<tr>
									<td>创建时间(结束)：</td>
									<td>
										<input id="createTimeEnd" name="createTimeEnd" class="mini-datepicker" style="width:140px"  emptyText="请输入"/>
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
						    <div title="组织机构" refreshOnClick="true">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="add()">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
												<!-- 
												<a class="mini-button" iconCls="icon-node" onclick="edit('view')">查看</a>
												 -->
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
												<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
												<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
												
											</td>
											
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
									<div id="datagrid1" class="mini-treegrid"" style="width:100%;height:100%;"
									showTreeIcon="true" allowResize="true" expandOnLoad="true"
				    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false" 
									url="${pageContext.request.contextPath}/org/all_selector" > 
									    <div property="columns">
									        <div type="checkcolumn"></div>
									        <div name="name" field="name" width="160" headerAlign="center">名称</div>
									        <div field="code" width="80" headerAlign="center">编码</div>
									        <div field="sn" width="30" align="right" headerAlign="center">序号</div>
									        <div field="appCode" width="60" align="left">所属应用</div>
									        <div field="nodePath" width="60" align="left">结点路径</div>
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
									        <div name="id" field="id" width="30" >ID</div>
									        <div name="pid" field="pid" width="30" >父ID</div>              
									    </div>
									</div>
						        </div>
						    </div>
						    
						</div>
						<ul class="mini-contextmenu" onbeforeopen="onBeforeOpen">
							<li onclick="reloadTab">
								刷新标签页
							</li>
						</ul>
					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							

							<div title="机构权限" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<input id="isAllChildForRes" name="isAllChildForRes" oncheckedchanged="oncheckedchanged" class="mini-checkbox" text="含下级部门" />
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-add" onclick="refreshRes()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="resGrid" class="mini-treegrid"" style="width:100%;height:100%;" allowAlternating="false" 
										showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="true" 
					    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="false"  showCheckBox="false" 
										url="${pageContext.request.contextPath}/res/all_selector" >
										<div property="columns">
									        <div type="checkcolumn" ></div>
									        <div name="name" field="name" width="160" headerAlign="center">名称</div>
									        <div field="code" width="80" headerAlign="center">编码</div>
									        <div field="typeDesc" width="80" headerAlign="center">类型</div>
									        <div field="sn" width="30" align="right" headerAlign="center">序号</div>
									        <div field="appCode" width="60" align="left">所属应用</div>
									        <div field="nodePath" width="60" align="left">结点路径</div>
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
									        <div name="id" field="id" width="30" >ID</div>
									        <div name="pid" field="pid" width="30" >父ID</div>   
										</div>
									</div>
								</div>
							</div>







							<div title="机构的角色" refreshOnClick="true" name="tabUserRoles">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<input id="isAllChildForRole" name="isAllChildForRole"  oncheckedchanged="oncheckedchanged"  class="mini-checkbox" text="含下级部门"/>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-add" onclick="addObject('7')">添加</a>
												<a id="btnDeleteForRole" class="mini-button" iconCls="icon-remove" onclick="removeObject('7')">删除</a>
												
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="roleGrid" class="mini-datagrid" style="width:100%;height:100%;"
										url="${pageContext.request.contextPath}/role/page"  idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据"
										sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" sortMode="client" >
										
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="name" width="160" headerAlign="center" allowSort="true" align="center">名称</div>
											<div field="nameShort" width="160" headerAlign="center" allowSort="true" align="center">简称</div>
											<div field="code" width="160" headerAlign="center" allowSort="true" align="center">编码</div>
											<div field="statusDesc" width="160" headerAlign="center" allowSort="true" align="center">状态</div>
											
											<div field="sn" width="160" headerAlign="center" allowSort="true" align="center">序号</div>
											<div field="remark" width="160" headerAlign="center" allowSort="true" align="center">备注</div>
											<div field="appCode" width="160" headerAlign="center" allowSort="true" align="center">系统编码</div>
											<div field="gid" width="160" headerAlign="center" allowSort="true" align="center">全局编码</div>
											<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
											<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">更新日期</div>
											
										</div>
									</div>
								</div>
							</div>
							
							
							
							<div title="机构的用户" refreshOnClick="true" name="tabUserOrgs">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<input id="isAllChildForUser" name="isAllChildForUser" oncheckedchanged="oncheckedchanged" class="mini-checkbox" text="含下级部门"  />
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-add" onclick="addObject('5')">添加</a>
												<a id="btnDeleteForUser" class="mini-button" iconCls="icon-remove" onclick="removeObject('5')">删除</a>
												
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="userGrid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" allowAlternating="true" 
										url="${pageContext.request.contextPath}/user/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
											<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="name" width="160" headerAlign="center" allowSort="true" align="center">姓名</div>
											<div field="loginName" width="160" headerAlign="center" allowSort="true" align="center">帐号</div>
											<div field="loginPwd" width="160" headerAlign="center" allowSort="true" align="center">密码</div>
											<div field="statusDesc" width="160" headerAlign="center" allowSort="true" align="center">状态</div>
											<div field="email" width="160" headerAlign="center" allowSort="true" align="center">邮箱</div>
											<div field="mobile" width="160" headerAlign="center" allowSort="true" align="center">手机</div>
											<div field="tel" width="160" headerAlign="center" allowSort="true" align="center">电话</div>
											<div field="numQq" width="160" headerAlign="center" allowSort="true" align="center">QQ</div>
											<div field="numWx" width="160" headerAlign="center" allowSort="true" align="center">微信</div>
											<div field="birthday" width="160" headerAlign="center" allowSort="true" align="center">生日</div>
											<div field="addressOffice" width="160" headerAlign="center" allowSort="true" align="center">办公地点</div>
											<div field="addressHome" width="160" headerAlign="center" allowSort="true" align="center">家庭地址</div>
											<div field="sexDesc" width="160" headerAlign="center" allowSort="true" align="center">性别</div>
											<div field="sn" width="160" headerAlign="center" allowSort="true" align="center">序号</div>
											<div field="remark" width="160" headerAlign="center" allowSort="true" align="center">备注</div>
											<div field="appCode" width="160" headerAlign="center" allowSort="true" align="center">系统编码</div>
											<div field="gid" width="160" headerAlign="center" allowSort="true" align="center">全局编码</div>
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
			
			var grid = mini.get("datagrid1"); // 组织机构列表
			
			var resGrid = mini.get("resGrid");
			resGrid.load({id: -1});
			
			var roleGrid = mini.get("roleGrid"); 
			var userGrid = mini.get("userGrid");
			
			var resCheck = mini.get("isAllChildForRes");
			var roleCheck = mini.get("isAllChildForRole");
			var userCheck = mini.get("isAllChildForUser");
			
			var deleteForRoleBtn = mini.get("btnDeleteForRole");
			var deleteForUserBtn = mini.get("btnDeleteForUser");
			
			function oncheckedchanged(e){
				var row = grid.getSelected();
				if(!row) {
					mini.alert("请选择一个部门");
					return ;
				}
				
				var obj = e.sender;
				var isAllChild = obj.getValue() == 'true' ? true: false;
				
				if(obj.id == 'isAllChildForRes') {
					resGrid.load({orgId:row.id,isAllChild: isAllChild});
					
				}
				else if(obj.id == 'isAllChildForRole') {
					
					roleGrid.load({orgId:row.id,isAllChild });
					 
					deleteForRoleBtn.setVisible(!isAllChild);
				}
				else if(obj.id == 'isAllChildForUser') {
					userGrid.load({orgId:row.id,isAllChild: obj.getValue()});
					
					deleteForUserBtn.setVisible(!isAllChild);
				}
			}
			
			grid.on("rowclick", function(e){
				var record = e.record;
				
				resGrid.load({orgId:record.id,isAllChild: resCheck.getValue()}); 
				roleGrid.load({orgId:record.id,isAllChild: roleCheck.getValue()}); 
				userGrid.load({orgId:record.id,isAllChild : userCheck.getValue()});
			});

			
			function search() {
				var data = form.getData();
				
				var createTimeBegin = mini.get('createTimeBegin').text;
				var createTimeEnd = mini.get('createTimeEnd').text;
				
				data.createTimeBegin = createTimeBegin;
				data.createTimeEnd = createTimeEnd;
				
				grid.load(data);
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
				resGrid.reload();
			}
		    
			function refreshAllGrid() {
				loading();
				grid.reload();
				resGrid.reload();
				roleGrid.reload();
				userGrid.reload();
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
					url : "${pageContext.request.contextPath}/apps/default/admin/uum/org/edit.jsp",
					title : "添加机构",
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
						url : "${pageContext.request.contextPath}/apps/default/admin/uum/org/edit.jsp",
						title : "编辑机构信息",
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
			
			function addObject(objType){
				var row = grid.getSelected();
				if(!row) {
					mini.alert("请选择一个组织机构");
					return ;
				}
				
				var url = "";
				if(objType == '7') {
					url = "${pageContext.request.contextPath}/apps/default/admin/uum/role/selector_role.jsp?isMutil=true";
				}
				else if(objType == '5') {
					url = "${pageContext.request.contextPath}/apps/default/admin/uum/user/selector_user.jsp?isMutil=true";
				}
				else {
					mini.alert("没有找到ajax请求的url");
					return ;
				}
				mini.open({
					url : url,
					title : "编辑机构信息",
					width : 700,
					height : 500,
					ondestroy : function(action) {
						if (action == 'ok') {
							var iframe = this.getIFrameEl();
							var rows = mini.clone(iframe.contentWindow.GetData());
							if(!rows) {
								mini.alert("请选择至少一条记录");
								return 
							}
							
							var objIds = new Array();
							for(var i=0;i<rows.length;i++){
								objIds.push(rows[i].id);
							}
							var data = {};
							data.orgId = row.id;
							
							if(objType == "7"){
								data.roleIds = objIds.join(",");
								addRoleToOrg(data); 
							}
							
							else if(objType == "5") {
								data.userIds =  objIds.join(",");
								addUserToOrg(data);
							}
						}
					}
				});
			}
			
			function removeObject(objType){
				var row = grid.getSelected();
				if(!row) {
					mini.alert("请选择一个组织机构");
					return ;
				}
				
				var data = {};
				data.orgId = row.id;
				
				var tGrid = null;
				if(objType == '7'){
					tGrid = roleGrid;
				}else if(objType == '5'){
					tGrid = userGrid;
				}
				
				var tRows = tGrid.getSelecteds();
				if(!tRows) {
					mini.alert("请选择至少一个记录");
					return ;
				}
				var tArr = new Array();
				for(var i=0;i<tRows.length;i++){
					tArr.push(tRows[i].id);
				}
				
				if(objType == "7"){ //移除机构的角色
					
					data.roleIds = tArr.join(",");
					removeRoleFromOrg(data);
				}
				else if(objType == "5"){ // 移除机构的用户
					data.userIds = tArr.join(",");
					removeUserFromOrg(data);
				}
			}
			
			function removeRoleFromOrg(data){
				$.ajax({
					url : "${pageContext.request.contextPath}/org/remove_role_from_org",
					type: 'post', dataType:'JSON', cache: false, async:false,
					data: data,
					success: function (json) {
						mini.alert("删除成功");
						roleGrid.reload();
					},
					error : function(data) {
				  		mini.alert(data.responseText);
					}
				});
			}
			function removeUserFromOrg(data) {
				$.ajax({
					url : "${pageContext.request.contextPath}/org/remove_user_from_org",
					type: 'post', dataType:'JSON', cache: false, async:false,
					data: data,
					success: function (json) {
						mini.alert("删除成功");
						userGrid.reload();
					},
					error : function(data) {
				  		mini.alert(data.responseText);
					}
				});
			}
			function addRoleToOrg(data){
				$.ajax({
					url : "${pageContext.request.contextPath}/org/add_role_to_org",
					type: 'post', dataType:'JSON', cache: false, async:false,
					data: data,
					success: function (json) {
						mini.alert("添加成功");
						roleGrid.reload();
					},
					error : function(data) {
				  		mini.alert(data.responseText);
					}
				});
			}
			
			function addUserToOrg(data) {
				$.ajax({
					url : "${pageContext.request.contextPath}/org/add_user_to_org",
					type: 'post', dataType:'JSON', cache: false, async:false,
					data: data,
					success: function (json) {
						mini.alert("添加成功");
						userGrid.reload();
					},
					error : function(data) {
				  		mini.alert(data.responseText);
					}
				});
			}
		</script>
	</body>
</html>