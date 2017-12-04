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
													<input id="key" name="key"  class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
												</td>
											</tr>
								
											<tr>
												<td>姓名：</td>
												<td>
													<input id="name" name="name"   class="mini-textbox"  emptyText="请输入姓名"  onenter="search"/>
												</td>
											</tr>
											
											<tr>
												<td>帐号：</td>
												<td>
													<input id="loginName" name="loginName"   class="mini-textbox"  emptyText="请输入帐号"  onenter="search"/>
												</td>
											</tr>
											
										
											
											<tr>
												<td>状态 </td>
												<td>
													<input id="status" name="status" value="1" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=user_status" />
												</td>
											</tr>
											
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
													<input id="tel" name="tel"   class="mini-textbox"  emptyText="请输入电话"  onenter="search"/>
												</td>
											</tr>
											
											<!-- 
											
											<tr>
												<td>QQ：</td>
												<td>
													<input id="numQq" name="numQq"   class="mini-textbox"  emptyText="请输入QQ"  onenter="search"/>
												</td>
											</tr>
											
											
											<tr>
												<td>微信：</td>
												<td>
													<input id="numWx" name="numWx"   class="mini-textbox"  emptyText="请输入微信"  onenter="search"/>
												</td>
											</tr>
											
											
										
											
											<tr>
												<td>办公地点：</td>
												<td>
													<input id="addressOffice" name="addressOffice"   class="mini-textbox"  emptyText="请输入办公地点（多个地址用@@分割）"  onenter="search"/>
												</td>
											</tr>
											
											
											
											<tr>
												<td>家庭地址：</td>
												<td>
													<input id="addressHome" name="addressHome"   class="mini-textbox"  emptyText="请输入家庭地址（多个地址用@@分割）"  onenter="search"/>
												</td>
											</tr>
											 -->
											
											
											<tr>
												<td>性别</td>
												<td>
													<input id="sex" name="sex" value="1" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=sex"  />
												</td>
											</tr>
											
											
											<tr>
												<td>备注：</td>
												<td>
													<input id="remark" name="remark"   class="mini-textbox"  emptyText="请输入"  onenter="search"/>
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
						    <div title="用户列表" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="add()">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search()">上传</a>
						                    </td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
						            <div id="userGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
						                url="${pageContext.request.contextPath}/user/page" idField="id" allowResize="false" multiSelect="true" 
						                sizeList="[5,10,20,50]" pageSize="20" showEmptyText="true" emptyText="暂无待修改信息" sortMode="client" >
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
						<!--  
						<ul id="refreshTabMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
							<li onclick="reloadTab">
								刷新标签页
							</li>
						</ul>
						-->
					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							

							<div title="用户的权限资源" refreshOnClick="true" name="tabUserReses">
								 
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-reload" onclick="refreshRes()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								 
								<div class="mini-fit">
									<div id="uResGrid" class="mini-treegrid" style="width:100%;height:100%;"
										showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="true" showCheckBox="false" 
    									treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  
										url="${pageContext.request.contextPath}/user/get_permission_list" >
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







							<div title="用户的角色" refreshOnClick="true" name="tabUserRoles">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addObject('7')">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeObject('7')">删除</a>
												<span class="separator"></span> 
												<a class="mini-button" iconCls="icon-reload" onclick="refreshRole()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="uRoleGrid" class="mini-datagrid" style="width:100%;height:100%;"
										url="${pageContext.request.contextPath}/user/get_role_list"  idField="id" multiSelect="true" allowResize="false"
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
							
							
							
							<div title="用户所属部门" refreshOnClick="true" name="tabUserOrgs">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addObject('3')">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeObject('3')">删除</a>
												<span class="separator"></span> 
												<a class="mini-button" iconCls="icon-reload" onclick="refreshOrg()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="uOrgGrid" class="mini-treegrid" style="width:100%;height:100%;" 
										showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="true" showCheckBox="false" 
    									treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  
										url="${pageContext.request.contextPath}/user/get_org_list" >
										<div property="columns">
									        <div type="checkcolumn" ></div>
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
							


							
							
							
							<div title="用户所属组" refreshOnClick="true" name="tabUserGroups">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addObject('4')">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeObject('4')">删除</a>
												<span class="separator"></span> 
												<a class="mini-button" iconCls="icon-reload" onclick="refreshGroup()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="uGroupGrid" class="mini-treegrid" style="width:100%;height:100%;"  
										showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="true" showCheckBox="false" 
    									treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  
										url="${pageContext.request.contextPath}/user/get_group_list" >
										
										<div property="columns">
											<div type="checkcolumn" ></div>
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
							
							
							
							
							<div title="岗位/职称" refreshOnClick="true" name="tabUserTitle">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addObject('1,2')">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeObject('1,2')">删除</a>
												<span class="separator"></span> 
												<a class="mini-button" iconCls="icon-reload" onclick="refreshTitle()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="uTitleGrid" class="mini-treegrid" style="width:100%;height:100%;" 
										showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="true" showCheckBox="false" 
    									treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  
										url="${pageContext.request.contextPath}/user/get_title_list"   emptyText="查无数据" >
										<div property="columns">
									        <div type="checkcolumn"></div>
									        <div name="name" field="name" width="160" headerAlign="center">名称</div>
									        <div field="typeDesc" width="30" align="right" headerAlign="center">类型</div>
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
			
			
			var grid = mini.get("userGrid"); // 用户列表
			
			var uResGrid = mini.get("uResGrid");// 用户权限
			var uOrgGrid = mini.get("uOrgGrid"); // 用户所属部门
			var uGroupGrid = mini.get("uGroupGrid");// 用户所属的组
			var uRoleGrid = mini.get("uRoleGrid"); // 用户的角色
			var uTitleGrid = mini.get("uTitleGrid"); // 用户的称谓
			
			grid.load();
			//uOrgGrid.load();
			//uResGrid.load();
			//uGroupGrid.load();
			//uRoleGrid.load();
			//uTitleGrid.load();
			function refreshGrid(){
				uOrgGrid.reload();
				uResGrid.reload();
				uGroupGrid.reload();
				uRoleGrid.reload();
				uTitleGrid.reload();
			}
			
			function search() {
				var data = form.getData();
				
				//var createTimeBegin = mini.get('createTimeBegin').text;
				//var createTimeEnd = mini.get('createTimeEnd').text;
				
				//data.createTimeBegin = createTimeBegin;
				//data.createTimeEnd = createTimeEnd;
				
				var key2 = mini.get("key2").value;
				if( (data.key==null || data.key=="") && (key2!=null && key2!="")){
					data.key = key2;
				}
				
				grid.load(data);
			}
		 
			grid.on("rowclick", function(e){
				var record = e.record;
				
				uGroupGrid.load({userId:record.id}); //用户的组
				uResGrid.load({userId:record.id}); // 用户的权限
				uOrgGrid.load({userId:record.id}); // 用户的部门
				uRoleGrid.load({userId:record.id}); // 用户的角色
				uTitleGrid.load({userId:record.id});//用户的称谓
			});
			/*
			grid.on("drawcell", function (e) {
	            var record = e.record,
	        	column = e.column,
		        field = e.field,
		        value = e.value;
	 
	        });
			*/
			
	
		 
			
			
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
								'url': "${pageContext.request.contextPath}/user/delete?ids="+ids.join(","),
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
					url : "${pageContext.request.contextPath}/apps/default/admin/uum/user/edit.jsp",
					title : "添加用户",
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
				
				if (row) {
					mini.open({
						url : "${pageContext.request.contextPath}/apps/default/admin/uum/user/edit.jsp",
						title : "编辑用户信息",
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
			
			
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 用户相关联的：7=角色、3=部门、4=组、称谓(1=职称、2=岗位)~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			function addObject(objType){
				var row = grid.getSelected();
				if(!row){
					mini.alert("请选中一条用户记录");
					return ;
				}
				
				var data = {userId:row.id,objType:objType};
				
				
				var ajaxUrl = null;
				var text ="";
				if(objType == '1,2') {
					text = "岗位/职称";
					ajaxUrl = "${pageContext.request.contextPath}/apps/default/admin/uum/title/selector_title.jsp";
				}
				else if(objType == '3') {
					 text="部门";
					 ajaxUrl = "${pageContext.request.contextPath}/apps/default/admin/uum/org/selector_org.jsp";
				}
				else if(objType == '4') {
					 text="组";
					 ajaxUrl = "${pageContext.request.contextPath}/apps/default/admin/uum/group/selector_group.jsp";
				}
				else if(objType == '7') {
					text="角色";
					ajaxUrl = "${pageContext.request.contextPath}/apps/default/admin/uum/role/selector_role.jsp?isMutil=true";
				}
				
				
				mini.open({
					url : ajaxUrl,
					title : "用户的"+text+"信息",
					width : 800,
					height : 580,
					ondestroy : function(action) {
						if(action == 'ok'){
							var iframe = this.getIFrameEl();
							var dt = iframe.contentWindow.GetData();
							
							var roleIds = mini.clone(dt);
							
							var rids = new Array();
							if(roleIds) {
								for(var i=0;i<roleIds.length;i++){
									rids.push(roleIds[i].id);
								}
							}
							
							if(rids.length == 0){
								mini.alert("请选择一个或多个"+text);
								return ;
							}
							
							data.objectIds = rids.join(",");
							
							
							
							$.ajax({
								'url': "${pageContext.request.contextPath}/user/add_object_to_user",
								data: data,
								type: 'post',dataType:'JSON', cache: false, async:false,
								success: function (json) {
									if(json && json>0) {
										mini.alert("添加成功");
									}
									refreshGrid();
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
			
			function removeObject(objType){
				var row = grid.getSelected();
				var data = {userId:row.id,objType:objType};
				if(!row){
					mini.alert("请选中一条用户记录");
					return ;
				}
				
				var row2 = null;
				if(objType == '1,2') {
					row2 = uTitleGrid.getSelecteds();
				}
				else if(objType == '3') {
					row2 = uOrgGrid.getSelecteds();
				}
				else if(objType == '4') {
					row2 = uGroupGrid.getSelecteds();
				}
				else if(objType == '7') {
					row2 = uRoleGrid.getSelecteds()
				}
				
				
				if(!row2){
					mini.alert("请选择至少一个对象");
					return ;
				}
				
				
				var roleIdsArr= new Array();
				for(var i=0;i<row2.length;i++) {
					roleIdsArr.push(row2[i].id);
				}
				data.objectIds = roleIdsArr.join(",");
				
				
		        mini.confirm("确定删除记录？", "确定？",
	                function (action) {
	                    if (action == "ok") {
	                    	$.ajax({
	        					'url': "${pageContext.request.contextPath}/user/delete_object_from_user",
	        					data: data,
	        					type: 'post',dataType:'JSON', cache: false, async:false,
	        					success: function (json) {
	        						if(json && json>0) {
	        							mini.alert("删除成功");
	        						}
	        						refreshGrid();
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
		    
			function refreshTitle() {
				loading();
				uTitleGrid.reload();
			}
			function refreshGroup() {
				loading();
				uGroupGrid.reload();
			}
			function refreshRole() {
				loading();
				uRoleGrid.reload();
			}
			function refreshOrg() {
				loading();
				uOrgGrid.reload();
			}
			function refreshRes() {
				loading();
				uResGrid.reload();
			}
		</script>
	</body>
</html>