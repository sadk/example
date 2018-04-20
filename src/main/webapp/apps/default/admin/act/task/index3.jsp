<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>我的待办(极速版)</title>
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
				<div id="form1"  style="padding:8px;margin-left: 8px;">
					<table>						
									<tr>
										<td>关键字 ：</td>
										<td>
											<input id="key" name="key"  class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
										</td>
									</tr>
									<tr>
										<td>流程单据标题 ：</td>
										<td>
											<input id="title" name="title"   class="mini-textbox"  emptyText="请输入流程单据标题"  onenter="search"/>
										</td>
									</tr>
									<tr>
										<td>待办用户ID ：</td>
										<td>
											<input id="userId" name="userId"   class="mini-textbox"  emptyText="请输入待办用户名ID"  onenter="search"/>
										</td>
									</tr>
									<tr>
										<td>待办用户名 ：</td>
										<td>
											<input id="userName" name="userName"   class="mini-textbox"  emptyText="请输入待办用户名"  onenter="search"/>
										</td>
									</tr>
									<tr>
										<td>待办用户账号：</td>
										<td>
											<input id="loginNo" name="loginNo"   class="mini-textbox"  emptyText="请输入待办用户账号"  onenter="search"/>
										</td>
									</tr>
									<!--  -->
									<tr>
										<td>流程发起人：</td>
										<td>
											<input id="startUserName" name="startUserName"   class="mini-textbox"  emptyText="请输入流程发起人"  onenter="search"/>
										</td>
									</tr>
									<tr>
										<td>单据主键：</td>
										<td>
											<input id="businessKey" name="businessKey"   class="mini-textbox"  emptyText="请输入单据主键"  onenter="search"/>
										</td>
									</tr>
     								
									<tr>
										<td>待办日期(开始)：</td>
										<td>
											<input id="createTimeBegin" name="createTimeBegin" format="yyyy-MM-dd" class="mini-datepicker"  emptyText="请输入待办创建日期(开始)" />
										</td>
									</tr>
									<tr>
										<td>待办日期(结束)：</td>
										<td>
											<input id="createTimeEnd" name="createTimeEnd" format="yyyy-MM-dd" class="mini-datepicker"  emptyText="请输入待办创建日期(结束)" />
										</td>
									</tr>
									
									<tr>
										<td>流程日期(开始)：</td>
										<td>
											<input id="instanceCreateTimeBegin" name="instanceCreateTimeBegin" format="yyyy-MM-dd" class="mini-datepicker"  emptyText="请输入流程创建日期(开始)" />
										</td>
									</tr>
									<tr>
										<td>流程日期(结束)：</td>
										<td>
											<input id="instanceCreateTimeEnd" name="instanceCreateTimeEnd" format="yyyy-MM-dd" class="mini-datepicker"  emptyText="请输入流程创建日期(结束)" />
										</td>
									</tr>
					</table>
					<div style="text-align:center;padding:10px;">
						<a class="mini-button" onclick="search()" iconCls="icon-search" style="width:60px;margin-right:20px;">查询</a>
						<a class="mini-button" onclick="clear()" iconCls="icon-cancel" style="width:60px;margin-right:20px;">清空</a>
					</div>
				</div>
			</div>
			<div showCollapseButton="true">
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						
						    <div title="我的待办" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<!-- 
													<a class="mini-button" iconCls="icon-add" onclick="add()">添加</a> 
													<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
													<span class="separator"></span>  
													<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
													<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
													<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
												-->
												
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<a class="mini-button" iconCls="icon-node" onclick="edit('view')">查看</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-goto" onclick="complete()">下一步处理</a>
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
									url="${pageContext.request.contextPath}/act/task/page_fast"  idField="id" sizeList="[5,10,20,50,100]" pageSize="20" >
									<div property="columns">
										<div type="checkcolumn" ></div>
										
										<div field="processInstanceId" width="80" headerAlign="center" allowSort="true" align="left">流程实例ID</div>			
										<div field="id" width="70" headerAlign="center" allowSort="true" align="left">任务ID</div>
										<div field="closeStatusDesc" width="100" headerAlign="center" allowSort="true" align="center">流程是否结束</div>
										<div field="name" width="100" headerAlign="center" allowSort="true" align="left">任务名称</div>
										<div field="taskDefinitionKey" width="80" headerAlign="center" allowSort="true" align="left">任务Key</div>
										<div field="candidateUserNames" width="150" headerAlign="center" allowSort="true" align="left">任务审批人</div>
										<div field="businessFlowNo" width="80" headerAlign="center" allowSort="true" align="left">业务流水号</div>	
										<div field="businessKey" width="80" headerAlign="center" allowSort="true" align="left">业务主键ID</div>
										<div field="title" width="350" headerAlign="center" allowSort="true" align="left">流程单据标题</div>
										
										
										
										<div field="startUserId" width="80" headerAlign="center" allowSort="true" align="left">发起人ID</div>
										<div field="startUserName" width="80" headerAlign="center" allowSort="true" align="left">发起人名称</div>
										<div field="startUserOrgText" width="80" headerAlign="center" allowSort="true" align="left">发起人组织</div>
										<div field="startUserPositionText" width="80" headerAlign="center" allowSort="true" align="left">发起人岗位</div>
											
										
										
										<div field="processDefinitionId" width="240" headerAlign="center" allowSort="true" align="left">流程定义ID</div>
										<div field="processDefinitionName" width="360" headerAlign="center" allowSort="true" align="left">流程定义名称</div>
										<div field="processDefinitionKey" width="240" headerAlign="center" allowSort="true" align="left">流程定义Key</div>
									
									
										<!-- 
										<div field="parentTaskId" width="80" headerAlign="center" allowSort="true" align="center">父任务ID</div>
										 -->
										<div field="tenantId" width="80" headerAlign="center" allowSort="true" align="center">租户ID</div>
										<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">任务生成日期</div>
										<div field="instanceCreateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">流程发起日期</div>
										
									</div>
									</div>
						        </div>
						    </div>
						</div>
					</div>
					
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">

							<div title="历史审批意见" refreshOnClick="true" name="tabUserResesxxx">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addOpinion()">新增</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editOpinion()">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="deleteOpinion()">删除</a>
												<a class="mini-button" iconCls="icon-save" onclick="saveOpinion()">保存</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-reload" onclick="refreshOpinion()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								 
								<div class="mini-fit">
									<div id="approveOpinionGrid" class="mini-datagrid" style="width:100%;height:100%;" sortMode="client"
										url="${pageContext.request.contextPath}/act/approve_opinion/page"   idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据" sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" 
										 allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" >
										<div property="columns">
									        <div type="checkcolumn" ></div>
									        <div field="businessKey" width="80" headerAlign="center" allowSort="true" align="center">业务主键ID</div>
									        <div field="approveTaskName" width="120" headerAlign="center" allowSort="true" align="left">审批节点名称</div>
									        <div field="processInstanceId" width="80" headerAlign="center" allowSort="true" align="left">流程实例</div>
									        <div field="definitionId" width="240" headerAlign="center" allowSort="true" align="left">流程定义ID</div>
									        <div field="definitionName" width="180" headerAlign="center" allowSort="true" align="left">流程定义名称</div>
									        <div field="definitionKey" width="220" headerAlign="center" allowSort="true" align="left">流程定义Key</div>
									        
									        <div field="approveTaskId" width="80" headerAlign="center" allowSort="true" align="center">审批任务ID</div>
									        
									        <div field="approveTaskKey" width="220" headerAlign="center" allowSort="true" align="left">审批任务Key</div>
									        <div field="approveAction" width="80" headerAlign="center" allowSort="true" align="left">审批动作</div>
									        
									        <div field="approveResult" width="80" headerAlign="center">动作说明
									        	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									        </div>
									        
									        <div field="approveOpinion" width="180" headerAlign="center">审批意见
									        	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									        </div>
									        
									        <div field="approveUserId" width="80" headerAlign="center" allowSort="true" align="left">审批用户ID</div>
									        <div field="approveUserName" width="80" headerAlign="center" allowSort="true" align="left">审批用户名称</div>
									        <!-- 
									       		<div field="lastApproveTaskKey" width="180" headerAlign="center" allowSort="true" align="left">上一步审批任务Key</div>
									         -->
									        <div field="businessType" width="180" headerAlign="center">(自定义)业务类型
									        	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									        </div>
									        
									        <div field="remark" width="160" align="right" headerAlign="center">备注
									        	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									        </div>
									        <div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
											<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">更新日期</div>
										
										
										</div>
									</div>
								</div>
							</div>
							
							
							
							
							
							<div title="可审批的用户" refreshOnClick="true" name="tabUserResesxxx">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<!-- <span class="separator"></span> 
												<a class="mini-button" iconCls="icon-reload" onclick="refreshOpinion()">刷新</a>-->
												<a class="mini-button" iconCls="icon-edit" onclick="selectLoginUser()">选择登陆用户</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key3" name="key3" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="searchApproveUser"/>   
						                        <a class="mini-button" onclick="searchApproveUser()">查询</a>
						                    </td>
										</tr>
									</table>
								</div>
								 
								<div class="mini-fit">
									<div id="approveOpinionGrid2" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
									url="${pageContext.request.contextPath}/act/node_user/get_node_user"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
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
			var approveOpinionGrid = mini.get("approveOpinionGrid");
			var nodeUserGrid = mini.get("approveOpinionGrid2");
			/*
			var myDoneGrid = mini.get("myDoneGrid")
			var myStartedGrid = mini.get("myStartedGrid")
			var ccMyGrid = mini.get("ccMyGrid")
			*/
			
			function deleteOpinion(){
				var list = approveOpinionGrid.getSelecteds();
				if(list.length==0){
					mini.alert("请至少选择一个历史审批意见记录");
					return ;
				}
				var idsArr=new Array();
				for(var i=0;i<list.length;i++){
					idsArr.push(list[i].id);
				}
				
				data = {};
				data.ids= idsArr.join(",");
				
		        mini.confirm("确定删除记录？", "确定？",
		                function (action) {
		                    if (action == "ok") {
		        				$.ajax({
		        	                url: "${pageContext.request.contextPath}/act/approve_opinion/delete",
		        	                type: "post", dataType: 'json',
		        	                data : data,
		        	                success: function (data) {
		        	                	mini.alert("删除成功");
		        	                	approveOpinionGrid.reload();
		        	                },
		        	                error: function (jqXHR, textStatus, errorThrown) {
		        	                    alert(jqXHR.responseText);
		        	                }
		        	       	 	});
		                    } 
		                }
		            );
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
			

			grid.load();
			grid.on("drawcell", function(e){
				var record = e.record;
				var field = e.field;
				var value = e.value;
				var row = e.row;
				if(typeof(row.name) != 'undefined' && row && field == "name" && row.id){
					e.cellHtml = '<a href="javascript:void(0)" onclick="view('+row.id+')">' + row.name + '</a>';
				}
			});
			
			grid.on("rowclick", function(e){
				var record = e.record;
				/* 
				column = e.column
		        field = e.field 
		        */
				approveOpinionGrid.load({processInstanceId: record.processInstanceId});
				nodeUserGrid.load({loginUserId:loginUserId,definitionId:definitionId,taskDefKey:taskDefKey});
			});	
			
				
			
			function complete(){
				var row=grid.getSelected();
				if(!row){
					mini.alert("请选择一个待办任务");
					return ;
				}
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/task/complete_json.jsp?definitionId="+row.processDefinitionId,
					title : "流程下一步处理",
					width : 510,
					height : 600,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {taskId : row.id}
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
								'url': "${pageContext.request.contextPath}/act/task/delete?ids="+ids.join(","),
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
		</script>
	</body>
</html>