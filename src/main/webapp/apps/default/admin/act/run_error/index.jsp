<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>流程异常记录</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
		<style>
			html, body {
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
		<div class="mini-splitter" style="width:100%;height:100%;">
			<div size="290" showCollapseButton="true">
				<div id="form1"  style="padding:8px;">
					<table>						
							<tr>
								<td>关键字 ：</td>
								<td>
									<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
								</td>
							</tr>
						
									
									<tr>
										<td>流程实例ID：</td>
										<td>
											<input name="instanceId" id="instanceId" class="mini-spinner" class="mini-datepicker" minValue="0" maxValue="999999999"  />
										</td>
									</tr>
									
									
									<tr>
										<td>节点名称：</td>
										<td>
											<input id="inputTaskName" name="inputTaskName"  style="width:140px" class="mini-textbox"  emptyText="请输入节点名称"  onenter="search"/>
										</td>
									</tr>
									
									
									
									<tr>
										<td>节点key：</td>
										<td>
											<input id="inputTaskKey" name="inputTaskKey"  style="width:140px" class="mini-textbox"  emptyText="请输入节点key"  onenter="search"/>
										</td>
									</tr>
									
									
									
									<tr>
										<td>流程标题：</td>
										<td>
											<input id="title" name="title"  style="width:140px" class="mini-textbox"  emptyText="请输入流程标题"  onenter="search"/>
										</td>
									</tr>
									
									
									
									<tr>
										<td>流程定义key：</td>
										<td>
											<input id="processDefinitionKey" name="processDefinitionKey"  style="width:140px" class="mini-textbox"  emptyText="请输入流程定义key"  onenter="search"/>
										</td>
									</tr>
									
									
									
									<tr>
										<td>流程发起人账号：</td>
										<td>
											<input id="startLoginNo" name="startLoginNo"  style="width:140px" class="mini-textbox"  emptyText="请输入流程发起人账号"  onenter="search"/>
										</td>
									</tr>
									
									
									
									<tr>
										<td>单据号：</td>
										<td>
											<input id="businessFlowNo" name="businessFlowNo"  style="width:140px" class="mini-textbox"  emptyText="请输入单据号"  onenter="search"/>
										</td>
									</tr>
									
									
									
									<tr>
										<td>业务状态：</td>
										<td>
											<input id="businessStatus" name="businessStatus"  style="width:140px" class="mini-textbox"  emptyText="请输入业务状态"  onenter="search"/>
										</td>
									</tr>
									
									
									
									<tr>
										<td>操作用户：</td>
										<td>
											<input id="operatorUserName" name="operatorUserName"  style="width:140px" class="mini-textbox"  emptyText="请输入操作用户"  onenter="search"/>
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
				<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-add" onclick="add()">添加</a>
								<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
								<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
								<a class="mini-button" iconCls="icon-node" onclick="edit('view')">查看</a>
								<span class="separator"></span>  
								<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
								<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
								<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
								
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
					url="${pageContext.request.contextPath}/act/run_error/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
					<div property="columns">
						<div type="checkcolumn" ></div>
						<div field="instanceId"  width="160" headerAlign="center" allowSort="true" align="center">流程实例ID</div>
						<div field="inputTaskId"  width="160" headerAlign="center" allowSort="true" align="center">任务ID</div>
						<div field="inputTaskName"  width="160" headerAlign="center" allowSort="true" align="center">节点名称</div>
						<div field="inputTaskKey"  width="160" headerAlign="center" allowSort="true" align="center">节点key</div>
						<div field="inputVariableJson"  width="160" headerAlign="center" allowSort="true" align="center">每次提审传入的业务变量</div>
						<div field="completeJson"  width="160" headerAlign="center" allowSort="true" align="center">每次提审聚合变量</div>
						<div field="errorMsg"  width="160" headerAlign="center" allowSort="true" align="center">错误信息</div>
						<div field="title"  width="160" headerAlign="center" allowSort="true" align="center">流程标题</div>
						<div field="processDefinitionId"  width="160" headerAlign="center" allowSort="true" align="center">流程定义ID</div>
						<div field="processDefinitionKey"  width="160" headerAlign="center" allowSort="true" align="center">流程定义key</div>
						<div field="processDefinitionName"  width="160" headerAlign="center" allowSort="true" align="center">流程定义名称</div>
						<div field="createDeptId"  width="160" headerAlign="center" allowSort="true" align="center">填制人部门</div>
						<div field="createDeptText"  width="160" headerAlign="center" allowSort="true" align="center">填制人部门或填制人部门节点路径</div>
						<div field="startUserId"  width="160" headerAlign="center" allowSort="true" align="center">流程发起人ID</div>
						<div field="startUserName"  width="160" headerAlign="center" allowSort="true" align="center">流程发起人名称</div>
						<div field="startLoginNo"  width="160" headerAlign="center" allowSort="true" align="center">流程发起人账号</div>
						<div field="startUserOrgText"  width="160" headerAlign="center" allowSort="true" align="center">流程发起人(主)组织</div>
						<div field="startUserPositionText"  width="160" headerAlign="center" allowSort="true" align="center">流程发起人(主)岗位</div>
						<div field="businessKey"  width="160" headerAlign="center" allowSort="true" align="center">业务数据主键ID</div>
						<div field="businessFlowNo"  width="160" headerAlign="center" allowSort="true" align="center">单据号</div>
						<div field="businessStatus"  width="160" headerAlign="center" allowSort="true" align="center">业务状态</div>
						<div field="businessStatusDesc"  width="160" headerAlign="center" allowSort="true" align="center">业务状态中文表示</div>
						<div field="businessCategory"  width="160" headerAlign="center" allowSort="true" align="center">业务自定义的分类</div>
						<div field="endStatus"  width="160" headerAlign="center" allowSort="true" align="center">4=已完成 3=未结束</div>
						<div field="appCode"  width="160" headerAlign="center" allowSort="true" align="center">租户编码</div>
						<div field="sn"  width="160" headerAlign="center" allowSort="true" align="center">排序</div>
						<div field="remark"  width="160" headerAlign="center" allowSort="true" align="center">备注</div>
						<div field="gid"  width="160" headerAlign="center" allowSort="true" align="center"></div>
						<div field="operatorUserId"  width="160" headerAlign="center" allowSort="true" align="center">操作用户</div>
						<div field="operatorUserName"  width="160" headerAlign="center" allowSort="true" align="center">操作用户</div>
						<div field="operatorAction"  width="160" headerAlign="center" allowSort="true" align="center">操作用户动作</div>
						<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
						<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center"></div>
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
							'url': "${pageContext.request.contextPath}/runError/delete?ids="+ids.join(","),
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
				url : "${pageContext.request.contextPath}/apps/default/admin/sys/runError/edit.jsp",
				title : "添加系统",
				width : 480,
				height : 220,
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
					url : "${pageContext.request.contextPath}/apps/default/admin/sys/application/edit.jsp",
					title : "编辑系统信息",
					width : 480,
					height : 220,
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
		
		function exportData() {
			var exportDataType = mini.get("exportDataType").value;
			var exportFileType = mini.get("exportFileType").value;
			mini.confirm("确定导出记录？", "确定？",
		            function (action) {
		                if (action == "ok") {
		    				var o = form.getData();
		    				
							var url = "${pageContext.request.contextPath}/runError/export?exportFileType="+exportFileType+"&exportDataType="+exportDataType;
							location.href=url;
						}
					});
			
		}
		</script>
	</body>
</html>