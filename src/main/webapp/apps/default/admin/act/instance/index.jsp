<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>流程实例和任务管理</title>
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
			
			a:link {
			 text-decoration: none;
			}
			a:visited {
			 text-decoration: none;
			}
			a:hover {
			 text-decoration: none;
			}
			a:active {
			 text-decoration: none;
			}
 
 
		</style>
	</head>
	<body>
		<div class="mini-splitter" style="width:100%;height:100%; overflow:auto;">
			<div size="240" showCollapseButton="true">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		           <span style="padding-left:5px;">应用名称：</span>
		           <input showNullItem="false" width="140" class="mini-combobox" url="${pageContext.request.contextPath}/application/all" textField="name" valueField="id" />
		           <!--  <a class="mini-button" iconCls="icon-search" plain="true">加载分类</a>     -->              
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/act/category/all?dataType=1" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" expandOnLoad="false">        
		            </ul>
		        </div>
			</div>
			<div showCollapseButton="true">
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						    <div title="流程定义" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-node" onclick="start()">启动流程</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-zoomout" onclick="displayNewestVersion()">最新版本</a>
												<a class="mini-button" iconCls="icon-zoomin" onclick="displayAllVersion()">所有版本</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-upload" onclick="deploy()">上传</a>
												<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
											</td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
						            <div id="definitionGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
						                url="${pageContext.request.contextPath}/act/definition/page" idField="id" allowResize="false" multiSelect="true" 
						                sizeList="[5,10,20,50]" pageSize="20" showEmptyText="true" emptyText="暂无待修改信息" sortMode="client" >
						                <div property="columns">
											<div type="checkcolumn" ></div>
											<div field="id" width="180" headerAlign="center" allowSort="true" align="left">流程定义ID</div>
											<div field="name" width="350" headerAlign="center" allowSort="true" align="left">流程名称</div>
											<div field="key" width="160" headerAlign="center" allowSort="true" align="left">流程Key</div>
											<div field="version" width="50" headerAlign="center" allowSort="true" align="center">版本号</div>
											
											<div field="deploymentId" width="80" headerAlign="center" allowSort="true" align="center">流程布署ID</div>
											<div field="resourceName" width="250" headerAlign="center" allowSort="true" align="left">流程定义XML</div>
											<div field="dgrmResourceName" width="250" headerAlign="center" allowSort="true" align="left">流程资源图</div>
											<div field="description" width="160" headerAlign="center" allowSort="true" align="left">描述</div>
											<div field="suspensionStateDesc" width="160" headerAlign="center" allowSort="true" align="center">挂起状态</div>
											<div field="hasStartFormKeyDesc" width="160" headerAlign="center" allowSort="true" align="center">是否有启动表单</div>
											<div field="tenantId" width="160" headerAlign="center" allowSort="true" align="center">租户ID</div>
											<div field="deployName" width="160" headerAlign="center" allowSort="true" align="left">流程布署名</div>
											<div field="deployTime"  dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">布署时间</div> 
										</div>
						            </div>
						        </div>
						    </div>
						</div>
					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							

							<div title="运行中的流程" refreshOnClick="true" name="tabUserReses">
								 
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-remove" onclick="removeInstance()">删除</a>
												<a class="mini-button" iconCls="icon-goto" onclick="jump()">跳转调整</a>
												<a class="mini-button" iconCls="icon-edit" onclick="opinionManage('runningGrid')">审批意见调整</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-ok" onclick="onecePass()">一键通过</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search()">查询</a>
						                    </td>
										</tr>
									</table>
								</div>
								 
								<div class="mini-fit">
									<div id="runningGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
						                idField="id" allowResize="false" multiSelect="true"  sizeList="[50,100,500,1000,2000]" 
						                pageSize="50" showEmptyText="true" emptyText="暂无查询的记录" sortMode="client" 
										url="${pageContext.request.contextPath}/act/runinstance/page_running" >
										<div property="columns">
									        <div type="checkcolumn" ></div>
									        
									        <div field="instanceId" width="80" headerAlign="center">流程实例ID</div>
									        <div field="id" width="80" headerAlign="center">执行ID</div>
									        <!-- 
									        <div field="taskKey" width="80" headerAlign="center">当前活动节点（key）</div>
									         -->
									        <div field="title" width="350" headerAlign="center">流程标题</div>
									        <div field="taskName" width="220" headerAlign="center">当前活动节点</div>
									        <div field="approveUserText" width="180" headerAlign="center" >当前节点审批用户</div>
									        <div field="concurrentDesc" width="100" headerAlign="center" align="center">是否并发执行</div>
									        <div field="suspendedDesc" width="80" headerAlign="center" align="center">是否挂起</div>
									        <div field="endStatusDesc" width="80" headerAlign="center">流程是否结束</div>
									        <div field="version" width="50" headerAlign="center" align="center">版本号</div>
									       
									        <div field="businessKey" width="80" headerAlign="center">业务主键</div>
									        <div field="businessFlowNo" width="150" headerAlign="center">业务流水</div>
									        <div field="businessStatusDesc" width="80" headerAlign="center" align="center">业务状态</div>
									        
									        <div field="startUserId" width="80" headerAlign="center">发起人ID</div>
									        <div field="startUserName" width="80" headerAlign="center">发起人姓名</div>
									        
									        <div field="createDeptId" width="80" headerAlign="center">填制人部门ID</div>
									        <div field="createDeptName" width="150" headerAlign="center">填制人部门名称</div>
									        
									        <!-- 
									        <div field="processDefinitionId" width="180" headerAlign="center">流程定义ID</div>
									        <div field="processDefinitionKey" width="160" headerAlign="center">流程定义Key</div>
									         -->
									       
									        <div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="150" width="80" headerAlign="center" align="center">创建时间</div>
									        
									       
									        <div field="appCode" width="80" headerAlign="center">租户码</div>
										</div>
									</div>
								</div>
							</div>



							<div title="已结束的流程" refreshOnClick="true" name="tabUserReses">
								 
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-goto" onclick="reStart()">重新发起流程</a>
												<a class="mini-button" iconCls="icon-edit" onclick="opinionManage('finishedGrid')">审批意见调整</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-reload" plain="true" onclick="refreshFinish()">刷新</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key3" name="key3" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search3()">查询</a>
						                    </td>
										</tr>
									</table>

								</div>
								 
								<div class="mini-fit">
									<div id="finishedGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
						                idField="id" allowResize="false" multiSelect="true"  sizeList="[5,10,20,50]" 
						                pageSize="20" showEmptyText="true" emptyText="暂无查询的记录" sortMode="client"  
										url="${pageContext.request.contextPath}/act/runtime/page_finished_detail" >
										<div property="columns">
									        <div type="checkcolumn" ></div>
									        <div field="id" width="70" headerAlign="center">流程实例ID</div>
									        <div field="title" width="400" headerAlign="center">流程标题</div>
									        <div field="businessKey" width="80" headerAlign="center">业务主键</div>
									        <div field="businessFlowNo" width="150" headerAlign="center">业务流水</div>
									        <div field="businessStatusDesc" width="80" headerAlign="center" align="center">业务状态</div>
									        
									        <div field="endStatusDesc" width="80" headerAlign="center">流程是否结束</div>
									        
									        <div field="version" width="50" headerAlign="center" align="center">版本号</div>
									        <div field="startUserId" width="80" headerAlign="center">发起人ID</div>
									        <div field="startUserName" width="80" headerAlign="center">发起人</div>
									        <div field="createDeptId" width="80" headerAlign="center">填制人部门ID</div>
									        <div field="startUserOrgText" width="150" headerAlign="center">填制人部门</div>
									         
									        <div field="startActivityId" width="80" headerAlign="center">开始活动节点key</div>
									        <div field="endAcitivityId" width="80" headerAlign="center">节束活动节点key</div>
									        <!-- 
									        <div field="taskName" width="120" headerAlign="center">结点名称</div>
									         -->
									        <div field="durationInMillis" width="120" headerAlign="center">经历时间(毫秒)</div>
									        <div field="startTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="150" headerAlign="center" allowSort="true" align="center">创建时间</div>
									        <div field="endTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="150" headerAlign="center" allowSort="true" align="center">结束时间</div>
									        <div field="tenantId" width="60" headerAlign="center">租户编码</div>
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
			var grid = mini.get("definitionGrid");
			var runningGrid = mini.get("runningGrid");
			var finishedGrid = mini.get("finishedGrid");
			
			tree.on("nodeselect", function (e) {
	        	grid.load({ deployCategory: e.node.code });
	        });
	        
			
			grid.on("rowclick", function(e){
				var record = e.record;
				runningGrid.load({processDefinitionId:record.id,isActive:true});
				finishedGrid.load({processDefinitionId:record.id});
			});
			
			runningGrid.on("drawcell", function(e){
				var record = e.record;
				var field = e.field;
				var value = e.value;
				var row = e.row;
				if(typeof(row.taskName) != 'undefined' && row && field == "taskName"){
					e.cellHtml = row.taskName +"("+row.taskKey+")" ;
				}
				
				if(typeof(row.title) != 'undefined' && row && field == "title" ){
					e.cellHtml = "<a href='javascript:viewVariable("+row.instanceId+")'>"+row.title + "</a>"  ;
				}
				
				if(typeof(row.instanceId) != 'undefined' && row && field == "instanceId" ){
					e.cellHtml = "<a href='javascript:viewVariable("+row.instanceId+")'>"+row.instanceId + "</a>"  ;
				}
			});
			
			finishedGrid.on("drawcell", function(e){
				var record = e.record;
				var field = e.field;
				var value = e.value;
				var row = e.row;
				
				if(typeof(row.id) != 'undefined' && row && field == "id"){
					e.cellHtml = "<a href='javascript:viewVariable("+row.id+")'>"+row.id + "</a>"  ;
				}
				
				if(typeof(row.title) != 'undefined' && row && field == "title" ){
					e.cellHtml = "<a href='javascript:viewVariable("+row.id+")'>"+row.title + "</a>"  ;
				}
			});
			
			//grid.load();
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
								'url': "${pageContext.request.contextPath}/act/definition/delete?cascade=true&ids="+ids.join(","),
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
			
			
			
			function onecePass() {
				var rows = runningGrid.getSelecteds();
				if(rows.length == 0) {
					mini.alert("请选择运行中的流程实例");
					return ;
				}
				var instanceIds = new Array();
				for (var i=0;i<rows.length;i++) {
					instanceIds.push(rows[i].instanceId);
				}
				/*
				mini.confirm("是否确定终止流程，并强行审批通过？", "确定？",
						function (action) {
							if (action == "ok") {
								$.ajax({
									'url': "${pageContext.request.contextPath}/act/runtime/onece_pass",
									type: 'post', dataType:'JSON', 
									data: {instanceIds:instanceIds.join(",")},
									success: function (json) {
										mini.alert("操作成功");
									},
									error : function(data) {
								  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
								  		mini.alert(data.responseText);
									}
								});
							}
				});*/
				
		        mini.showMessageBox({
		            title: "流程将终止并一键通过！注意！！注意操作！！！",
		            iconCls: "mini-messagebox-question",
		            buttons: ["ok", "no", "cancel"],
		            message: "请选择：审批通过(删除审批意见)？， 审批通过(删除审批意见)？， 取消？",
		            callback: function (action) {
						if (action == "ok" || action == 'no') {
							
							$.ajax({
								'url': "${pageContext.request.contextPath}/act/runtime/onece_pass?isDeleteOpinion="+(action == "ok" ? true:false),
								type: 'post', dataType:'JSON', 
								data: {instanceIds:instanceIds.join(",")},
								success: function (json) {
									mini.alert("操作成功");
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
			
			
			function start() {
				var t = grid.getSelected();
				if(!t) {
					mini.alert("请选择一个流程定义");
					return ;
				}
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/definition/start_json.jsp",
					title : "发起流程",
					width : 450,
					height : 350,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {definitionId : t.id}
						data.variables = mini.encode({"startUserId":1,"businessKey":5,"title":(t.name+"-流程标题"+getNowFormatDate())})
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						if(action == 'ok'){
							var iframe = this.getIFrameEl();
							var data = iframe.contentWindow.GetData();
							data = mini.clone(data);
							if(typeof data.variable != 'undefined'){
								data.variable = mini.encode(data.variable)
							}
							console.log(data)
							$.ajax({
								'url': "${pageContext.request.contextPath}/act/runtime/start_by_definition_id",
								type: 'post', dataType:'JSON', cache: false, async:false,
								data: data,
								success: function (json) {
									if(json){
										mini.alert("流程启动成功");
										grid.reload();
									}
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
			
			
			function reStart() {
				var row = finishedGrid.getSelected();
				if(!row) {
					mini.alert("请选择一个已结束的流程实例")
					return ;
				}
				
				var defRow = grid.getSelected();
				if(!defRow) {
					mini.alert("请选择一个流程定义")
					return ;
				}
				
				var data = {}
				data.definitionId = defRow.id;
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/instance/restart_variable.jsp",
					title : "流程发起变量",
					width : 580,
					height : 600,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {}
						data.instanceId = row.id;
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						var iframe = this.getIFrameEl();
						var dt = iframe.contentWindow.GetData(data);
						
						if(action == 'ok'){
							dt = mini.clone(dt);
							data.variables = dt;
							$.ajax({
								'url': "${pageContext.request.contextPath}/act/runtime/start_by_definition_id",
								type: 'post', dataType:'JSON', 
								data: data,
								success: function (json) {
									mini.alert("发起成功");
								},
								error : function(data) {
							  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
							  		mini.alert(data.responseText);
								}
							});
						}
					}
				})
			}
			
			
			function opinionManage(gridId) {
				var row = mini.get(gridId).getSelected();
				
				if(!row){
					mini.alert("请至少选择一个流程实例");
					return ;
				}
				//console.log(row)
				var data = {};
				data.title = row.title;
				data.businessKey = row.businessKey;
				data.processInstanceId=row.instanceId;
				if(gridId == 'finishedGrid') {
					data.processInstanceId = row.id;
				}
				data.definitionId=row.processDefinitionId;
				data.definitionName = row.processDefinitionName;
				data.definitionKey = row.processDefinitionKey;
				data.approveTaskId = row.approveTaskId;
				data.approveTaskName = row.taskName;
				data.approveTaskKey = row.taskKey;
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/opinion/index.jsp?processInstanceId="+data.processInstanceId,
					title : "审批意见调整",
					width : 800,
					height : 600,
					onload : function() {
						var iframe = this.getIFrameEl();
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						runningGrid.reload();
						//finishedGrid.reload();
					}
				})
			}
			
			function viewVariable(instanceId) {
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/instance/instance_variable.jsp",
					title : "流程发起变量",
					width : 580,
					height : 600,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {}
						data.instanceId = instanceId;
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						if(action == 'ok'){
							
						}
					}
				})
			}
			
			function jump(){
				var row = runningGrid.getSelected();
				if(!row){
					mini.alert("请选择一个流程实例");
					return ;
				}
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/instance/jump.jsp?definitionId="+row.processDefinitionId+"&processDefinitionKeyTarget="+row.processDefinitionKey,
					title : "流程跳转处理",
					width : 580,
					height : 600,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
								processDefinitionId : row.processDefinitionId,
								instanceId : row.instanceId,
								title : row.title,
								taskName : row.taskName ,
								taskKey : row.taskKey, 
								businessKey:row.businessKey,
								version :row.version,
								startUserId:row.startUserId,
								startUserName:row.startUserName,
								createDeptId : row.createDeptId,
								createDeptName: row.createDeptName,
								variableJSONInit : row.variableJSONInit,
								variableJSONStarting: row.variableJSONStarting
								
						}
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						if(action == 'ok'){
							var iframe = this.getIFrameEl();
							var data = iframe.contentWindow.GetData();
							
							if(data) {
								data = mini.clone(data);
								$.ajax({
									'url': "${pageContext.request.contextPath}/act/runtime/complete",
									type: 'post', dataType:'JSON', cache: false, async:false,
									data: data,
									success: function (json) {
										mini.alert("提交成功");
									},
									error : function(data) {
								  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
								  		mini.alert(data.responseText);
									}
								});
							}
						}
						grid.reload(); // 使终刷新待办列表
					}
				});
			}
			
			function search(){
				var key = mini.get("key2").value;	
				runningGrid.load({key: key,isActive:true});
			}
			
			function search3(){
			//	alert(33);
				var key = mini.get("key3").value;	
				finishedGrid.load({key: key});
			}
			
			
			function start() {
				var t = grid.getSelected();
				if(!t) {
					mini.alert("请选择一个流程定义");
					return ;
				}
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/definition/start_json.jsp",
					title : "发起流程",
					width : 450,
					height : 350,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {definitionId : t.id}
						data.variables = mini.encode({"startUserId":1,"businessKey":-1,"createDeptId":-1,"title":(t.name+"-流程标题"+getNowFormatDate())})
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						if(action == 'ok'){
							var iframe = this.getIFrameEl();
							var data = iframe.contentWindow.GetData();
							data = mini.clone(data);
							if(typeof data.variable != 'undefined'){
								data.variable = mini.encode(data.variable)
							}
							console.log(data)
							$.ajax({
								'url': "${pageContext.request.contextPath}/act/runtime/start_by_definition_id",
								type: 'post', dataType:'JSON', cache: false, async:false,
								data: data,
								success: function (json) {
									if(json){
										mini.alert("流程启动成功");
										grid.reload();
									}
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
			
			function getNowFormatDate() {
			    var date = new Date();
			    var seperator1 = "-";
			    var seperator2 = ":";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
			            + " " + date.getHours() + seperator2 + date.getMinutes()
			            + seperator2 + date.getSeconds();
			    return currentdate;
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
								'url': "${pageContext.request.contextPath}/act/definition/delete?cascade=true&ids="+ids.join(","),
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
			
			function displayNewestVersion() { // 只显示最新版本流程定义
				grid.load({isDisplayNewest : true});
			}
			
			function displayAllVersion() {
				grid.load({});
			}
			
			function refreshGrid(){
				grid.reload();
				runningGrid.clearRows();
				finishedGrid.clearRows();
			}
			
			function refreshFinish() {
				finishedGrid.reload();
			}
			
			function removeInstance() {
				var instanceIds = runningGrid.getSelecteds();
				var ids = [];
				
				for(var i=0;i<instanceIds.length;i++) {
					ids.push(instanceIds[i].instanceId);
				}
				
		        mini.confirm("确定删除记录？", "确定？",
		                function (action) {
		                    if (action == "ok") {
		        				$.ajax({
		        					'url': "${pageContext.request.contextPath}/act/runtime/instance_delete?instanceIds="+ids.join(","),
		        					type: 'post',
		        					dataType:'JSON',
		        					cache: false,
		        					async:false,
		        					success: function (json) {
		        						mini.alert("删除成功");
		        						runningGrid.reload();
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
			
			/*
			grid.on("drawcell", function (e) {
	            var record = e.record,
	        	column = e.column,
		        field = e.field,
		        value = e.value;
	 
	        });
			*/
			
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