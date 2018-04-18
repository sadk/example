<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>我的已办（详细版）</title>
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
										<td>用户ID ：</td>
										<td>
											<input id="userId" name="userId"   class="mini-textbox"  emptyText="请输入用户名ID"  onenter="search"/>
										</td>
									</tr>
									<tr>
										<td>用户名 ：</td>
										<td>
											<input id="userName" name="userName"   class="mini-textbox"  emptyText="请输入用户名"  onenter="search"/>
										</td>
									</tr>
									<tr>
										<td>用户账号：</td>
										<td>
											<input id="loginNo" name="loginNo"   class="mini-textbox"  emptyText="请输入用户账号"  onenter="search"/>
										</td>
									</tr>
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
										<td>办理日期(开始)：</td>
										<td>
											<input id="createTimeBegin" name="createTimeBegin" format="yyyy-MM-dd" class="mini-datepicker"  emptyText="请输入创建日期(开始)" />
										</td>
									</tr>
									<tr>
										<td>办理日期(结束)：</td>
										<td>
											<input id="createTimeEnd" name="createTimeEnd" format="yyyy-MM-dd" class="mini-datepicker"  emptyText="请输入创建日期(结束)" />
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
						    <div title="我的已办" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-node" onclick="edit('view')">查看</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search()">查询</a>
						                    </td>
										</tr>
									</table>
								</div>
								
						        <div class="mini-fit">
									<div id="myDoneGrid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
									url="${pageContext.request.contextPath}/act/task/list_my_started_and_done"  idField="id" sizeList="[5,10,20,50,100]" pageSize="20" >
									<div property="columns">
										<div type="checkcolumn" ></div>
										<div field="processInstanceId" width="80" headerAlign="center" allowSort="true" align="left">流程实例ID</div>
										<div field="closeStatusDesc" width="100" headerAlign="center" allowSort="true" align="center">流程是否结束</div>
										<div field="id" width="80" headerAlign="center" allowSort="true" align="center">任务ID</div>
										<div field="name" width="100" headerAlign="center" allowSort="true" align="left">任务名称</div>
										
										<div field="title" width="350" headerAlign="center" allowSort="true" align="left">流程标题</div>
										
										<div field="businessKey" width="80" headerAlign="center" allowSort="true" align="left">业务主键ID</div>
										
										
										<div field="startUserId" width="80" headerAlign="center" allowSort="true" align="left">发起人ID</div>
										<div field="startUserName" width="80" headerAlign="center" allowSort="true" align="left">发起人名称</div>
										<div field="startUserOrgText" width="80" headerAlign="center" allowSort="true" align="left">发起人组织</div>
										<div field="startUserPositionText" width="80" headerAlign="center" allowSort="true" align="left">发起人岗位</div>
											
										
										
										<div field="processDefinitionId" width="240" headerAlign="center" allowSort="true" align="left">流程定义ID</div>
										<div field="processDefinitionName" width="360" headerAlign="center" allowSort="true" align="left">流程定义名称</div>
										<div field="processDefinitionKey" width="240" headerAlign="center" allowSort="true" align="left">流程定义Key</div>
									
									
										
										<div field="parentTaskId" width="80" headerAlign="center" allowSort="true" align="center">父任务ID</div>
										<div field="tenantId" width="80" headerAlign="center" allowSort="true" align="center">租户ID</div>
										<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">办理日期</div>
										<div field="instanceCreateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">流程创建日期</div>
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
												<a class="mini-button" iconCls="icon-add" onclick="editOpinion('add')">新增</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editOpinion('edit')">编辑</a>
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
			
			var approveOpinionGrid = mini.get("approveOpinionGrid");
			var grid = mini.get("myDoneGrid")
			
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
			
			grid.on("rowclick", function(e){
				var record = e.record;
				approveOpinionGrid.load({processInstanceId: record.processInstanceId});
			});	
			
			grid.on("drawcell", function(e){
				var record = e.record;
				var field = e.field;
				var value = e.value;
				var row = e.row;
				if(typeof(row.name) != 'undefined' && row && field == "name" && row.id){
					e.cellHtml = '<a href="javascript:void(0)" onclick="view('+row.id+')">' + row.name + '</a>';
				}
			});
 
			//grid.load();
			
			function search() {
				var data = form.getData();
				
				var createTimeBegin = mini.get('createTimeBegin').text;
				var createTimeEnd = mini.get('createTimeEnd').text;
				
				
				var instanceCreateTimeBegin = mini.get('instanceCreateTimeBegin').text;
				var instanceCreateTimeEnd = mini.get('instanceCreateTimeEnd').text;
				
				
				data.createTimeBegin = createTimeBegin;
				data.createTimeEnd = createTimeEnd;
				
				data.instanceCreateTimeBegin = instanceCreateTimeBegin;
				data.instanceCreateTimeEnd = instanceCreateTimeEnd;
				var key2 = mini.get("key2").value;
				if( (data.key==null || data.key=="") && (key2!=null && key2!="")){
					data.key = key2;
				}
				
				grid.load(data);
			}

		</script>
	</body>
</html>