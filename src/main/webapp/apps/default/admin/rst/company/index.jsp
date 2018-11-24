<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>企业信息管理</title>
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
												<td>名称：</td>
												<td>
													<input id="name" name="name"   class="mini-textbox"  emptyText="请输入组名称"  onenter="search"/>
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
						    <div title="企业信息" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="add()">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
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
									<div id="datagrid1" class="mini-treegrid"" style="width:100%;height:100%;"
									showTreeIcon="true" allowResize="true" expandOnLoad="true"
				    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false" 
									url="${pageContext.request.contextPath}/title/all" > 
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
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							
							<div title="公司地址" refreshOnClick="true" name="tabReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
												
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key_oro" name="key_oro" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search('oro')">搜索</a>
						                    </td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="resGrid" class="mini-treegrid" style="width:100%;height:100%;"
										showTreeIcon="true" allowResize="true" expandOnLoad="true"
							    		treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  
							    		checkRecursive="true"  showCheckBox="false"  multiSelect="true" 
										url="${pageContext.request.contextPath}/res/all_selector"  >
										<div property="columns">
											<div type="checkcolumn"></div>
									        <div name="name" field="name" width="160" headerAlign="center">名称</div>
									        <div field="code" width="80" headerAlign="center">编码</div>
									        <div field="typeDesc" width="80" headerAlign="center">资源类型</div>
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
							




							<div title="企业图片" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addUserToTitle()">添加用户</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeUserFromTitle()">移出用户</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key_oro" name="key_oro" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search('oro')">搜索</a>
						                    </td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="userGrid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" multiSelect="true" 
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
			
			
			var grid = mini.get("datagrid1"); // 称谓列表
			var userGrid = mini.get("userGrid");// 用户列表
			var roleGrid = mini.get("roleGrid");
			var resGrid = mini.get("resGrid");
			
			//grid.load();
			userGrid.load({id:-1});
			roleGrid.load({id:-1});
			resGrid.load({id:-1});
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
				userGrid.load({titleId:record.id}); //称谓下的用户
				roleGrid.load({titleIds:record.id}); //称谓的角色
				resGrid.load({titleIds:record.id});
			});
	
			
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
								'url': "${pageContext.request.contextPath}/group/delete?ids="+ids.join(","),
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
					url : "${pageContext.request.contextPath}/apps/default/admin/uum/title/edit.jsp",
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
						url : "${pageContext.request.contextPath}/apps/default/admin/uum/title/edit.jsp",
						title : "编辑称谓信息",
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
			
			
			
	        function addUserToTitle() { //添加用户到一个称谓下
	        	var row = grid.getSelected();
		        if(!row){
		        	mini.alert("请选择一个组");
		        	return ;
		        }
		        
    			mini.open({
    				url : "${pageContext.request.contextPath}/apps/default/admin/uum/user/selector_user.jsp",
    				title : "添加称谓下的用户",
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
    			            
    			            var data = {userIds:uids.join(","),titleId:row.id,type: row.type};
    			            $.ajax({
    							url : "${pageContext.request.contextPath}/title/add_user_to_title",
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
			
	        
			function removeUserFromTitle() { // 删除称谓下的用户
				var row = grid.getSelected();
				if (!row) {
					mini.alert("请选中一个称谓");
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
				var data = {titleId: row.id,type: row.type,userIds:uids.join(",")};
				
				mini.confirm("确定删除？", "确定？",
					function (action) {
						if (action == "ok") {
							$.ajax({
								'url': "${pageContext.request.contextPath}/title/remove_user_from_title",
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
			
			function  addRoleToTitle () {
	        	var row = grid.getSelected();
		        if(!row){
		        	mini.alert("请选择一个称谓");
		        	return ;
		        }
		        
    			mini.open({
    				url : "${pageContext.request.contextPath}/apps/default/admin/uum/role/selector_role.jsp?isMutil=true",
    				title : "添加称谓的角色",
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
    			            
    			            var data = {roleIds:uids.join(","),titleId:row.id};
    			            $.ajax({
    							url : "${pageContext.request.contextPath}/title/add_role_to_title",
    							dataType: 'json',
    							type : 'post',
    							cache : false,
    							data: data,
    							success : function(text) {
    								roleGrid.reload({roleIds:row.id});
    								mini.alert("添加成功");
    							}
    						});
    			        }  
    					
    				}
    			});
			}
			
			
			function removeRoleFromTitle() { 
				var row = grid.getSelected();
				if (!row) {
					mini.alert("请选中一个称谓");
					return ;
				}
				var urow = roleGrid.getSelecteds();
				if(!urow) {
					mini.alert("请选择至少一个角色");
					return ;
				}
				
				var uids = new Array();
				for(var i=0;i<urow.length;i++) {
					uids.push(urow[i].id);
				}
				var data = {titleId: row.id,roleIds:uids.join(",")};
				
				mini.confirm("确定删除？", "确定？",
					function (action) {
						if (action == "ok") {
							$.ajax({
								'url': "${pageContext.request.contextPath}/title/remove_role_from_title",
								type: 'post',
								dataType:'JSON',
								data : data,
								cache: false,
								async:false,
								success: function (json) {
									mini.alert("删除成功");
									roleGrid.reload();
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
		</script>
	</body>
</html>