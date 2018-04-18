<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>流程设计管理</title>
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
			<div size="220" showCollapseButton="true">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <span style="padding-left:5px;">流程分类：</span>
		            <!--  <input class="mini-textbox" width="120"/> -->
		            
		            <input showNullItem="false" width="140" class="mini-combobox" url="${pageContext.request.contextPath}/application/all" textField="name" valueField="id" />
		            
		           <!--  <a class="mini-button" iconCls="icon-search" plain="true">查找</a>      -->             
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
						
						
						
						    <div title="流程定义列表" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												
												<a class="mini-button" iconCls="icon-node" onclick="start()">启动流程</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<a class="mini-button" iconCls="icon-save" onclick="save()">保存</a>
												
												<input class="mini-combobox" style="width: 80px;" onvaluechanged="onVersionValueChanged" id="versionCmb" showNullItem="false" nullItemText="请选择..." emptyText="版本选择" textField="name" valueField="value" data="[{name:'最新版本',value:1},{name:'所有版本',value:2}]" />
												
												
												<!-- 
												<a class="mini-button" iconCls="icon-zoomout" onclick="displayNewestVersion()">最新版本</a>
												<a class="mini-button" iconCls="icon-zoomin" onclick="displayAllVersion()">所有版本</a>
												 -->
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-goto" onclick="copyDefinition()">流程复制</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editNodeForm('editGlobal')">设置全局表单</a>
												<a class="mini-button" iconCls="icon-edit" onclick="setGlobalScript()">设置全局回调</a>
												
												<a class="mini-button" iconCls="icon-upload"   onclick="importDefinition()">导入</a>
												<a class="mini-button" iconCls="icon-download" onclick="exportDefinition()">导出</a>
												<!-- <span class="separator"></span>  
												<a class="mini-button" iconCls="icon-add" onclick="deployPay()">付款审批发布</a> -->
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:100px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search()">查询</a>
						                    </td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
						            <div id="definitionGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
						            	 idField="id" allowResize="false" multiSelect="true"  sizeList="[5,10,20,50]" 
						            	 pageSize="20" showEmptyText="true" emptyText="暂无查询信息" sortMode="client"
						            	  allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" 
						              	 url="${pageContext.request.contextPath}/act/definition/page" >
						                <div property="columns">
											<div type="checkcolumn" ></div>
											<div field="id" width="180" headerAlign="center" allowSort="true" align="left">流程定义ID</div>
											<div field="name" width="300" headerAlign="center" allowSort="true" align="left">流程全称</div>
											
											<div field="shortName" width="100" headerAlign="center" allowSort="true" align="left">流程简称
												<input property="editor" class="mini-textbox" style="width:100%;" />
											</div>
											
											<div type="comboboxcolumn" field="enableMobile" width="80" headerAlign="center" align="center" allowSort="true">移端启用
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" />
											</div>
											
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

							<div title="节点设置" refreshOnClick="true" name="tabUserReses">
								 
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-redo" onclick="importNode()">导入节点</a>
												<!-- 
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-user" onclick="refreshRes()">设置跳转规则</a>
												<a class="mini-button" iconCls="icon-split" onclick="refreshRes()">前置后置事件设置</a>
												<a class="mini-button" iconCls="icon-wait" onclick="refreshRes()">任务催办设置</a> -->
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-remove" onclick="removeNode()">删除</a>
												<a class="mini-button" iconCls="icon-save" onclick="saveNode()">保存</a>
												<a class="mini-button" iconCls="icon-edit" onclick="nodeCCSetting()">节点抄送设置</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-reload" onclick="refreshNode()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								 
								<div class="mini-fit">
									<div id="nodeGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据"  allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true"
										sizeList="[5,10,20,50]" pageSize="20" allowAlternating="false" sortMode="client" showPager="fales"
										 url="${pageContext.request.contextPath}/act/node/list" >
										<div property="columns">
									        <div type="indexcolumn" ></div>
									        <div field="taskName" width="120" headerAlign="center">节点名称</div>
									        <div field="taskKey" width="80" headerAlign="center">节点Key</div>
									       
									        <div type="comboboxcolumn"  field="taskBizType" width="120" headerAlign="center">节点类型
									        	<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=node_task_biz_type" />
									        </div>
									        
									        <div type="comboboxcolumn"  field="nodeVariableCopy" width="80" headerAlign="center">节点变量来源
									        	<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=node_variable_copy" />
									        </div>
									        
									         
									        <div type="comboboxcolumn"  field="nodeJumpType" width="140" headerAlign="center">跳转类型
									        	<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=node_jump_type&enable=1" />
									        </div>
									        <!--  
									        <div field="formKey" width="100" headerAlign="center">formKey(也就是外置表单URL)</div>
									        <div field="opinionToDoTypeDesc" width="80" align="right" headerAlign="center">意见处理类型</div>
									        -->
									         <div field="remark" width="80" headerAlign="center">备注</div>
										</div>
									</div>
								</div>
							</div>



							<div title="审批人设置" refreshOnClick="true" name="tabUserSetting">
								 
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<!-- 
												<a class="mini-button" iconCls="icon-redo" onclick="importNode()">导入节点</a>
												<span class="separator"></span>
												 -->
												<a class="mini-button" iconCls="icon-user" onclick="userSetting()">设置审批人</a>
												<a class="mini-button" iconCls="icon-edit" onclick="viewUser()">预览审批人</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-add" onclick="initNodeUser()">初使化审批人</a>
												<a class="mini-button" iconCls="icon-reload" onclick="refreshNodeUser()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								 
								<div class="mini-fit">
									<div id="userSettingGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
						            	 idField="id" allowResize="false" multiSelect="true"  sizeList="[5,10,20,50]" showPager="fales"
						            	 pageSize="20" showEmptyText="true" emptyText="暂无待修改信息" sortMode="client" 
										url="${pageContext.request.contextPath}/act/definition/get_node_list" >
										<div property="columns">
									        <div type="checkcolumn" ></div>
									        <div field="taskName" width="150" headerAlign="center">节点名称</div>
									        <div field="taskKey" width="80" headerAlign="center">流程节点Key</div>
									        <div field="taskBizTypeDesc" width="80" headerAlign="center" align="center">节点类型</div>
									        
									        <div field="approveObjectIds" width="150" headerAlign="center">审批对象ID</div>
									        <div field="approveObjectNames" width="250" headerAlign="center">审批对象名称</div>
									        <div field="approveObjectTypes" width="80" headerAlign="center">审批对象类型</div>
									        
									        <!-- 
									        <div field="formKey" width="80" headerAlign="center">formKey(也就是外置表单URL)</div>
									        <div field="approveObject" width="80" headerAlign="center">审批人</div>
									        <div field="opinionToDoTypeDesc" width="30" align="right" headerAlign="center">意见处理类型</div>
									        <div field="nodeJumpTypeDesc" width="30" align="right" headerAlign="center">跳转类型</div>
									         -->
									          <div field="remark" width="180" headerAlign="center">备注</div>
										</div>
									</div>
								</div>
							</div>



							<div title="表单设置" refreshOnClick="true" name="tabFormSetting">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-redo" onclick="importNodeForm()">导入节点</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-edit" onclick="editNodeForm('edit')">编辑</a>
												<a class="mini-button" iconCls="icon-save" onclick="saveNodeForm()">保存</a>
												<a class="mini-button" iconCls="icon-remove" onclick="deleteNodeForm()">删除</a>
												
												<!-- <a class="mini-button" iconCls="icon-edit" onclick="refreshRes()">设置全局手机表单</a> -->
												<!-- <a class="mini-button" iconCls="icon-edit" onclick="refreshRes()">设置动态表单</a> -->
												<!-- <a class="mini-button" iconCls="icon-add" onclick="refreshRes()">设置URL表单</a> -->
												
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-node" onclick="initButton()">初使化操作按钮</a>
												<a class="mini-button" iconCls="icon-tip" onclick="setButton()">设置操作按钮</a>
												<a class="mini-button" iconCls="icon-edit" onclick="batchSetScript()">批量设置按钮脚本</a>
												
											</td>
											<td style="white-space:nowrap;">
						                        <input id="keyUserTaskyKey" name="keyUserTaskyKey" class="mini-textbox" emptyText="请输入关键字" style="width:100px;" onenter="search"/>   
						                        <a class="mini-button" onclick="searchNodeForm()">查询</a>
						                    </td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="formSettingGrid" class="mini-datagrid" style="width:100%;height:100%;"
										url="${pageContext.request.contextPath}/act/node_form/page"  idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据"  allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true"
										sizeList="[20,50,200,500,1000]" pageSize="20" allowAlternating="true" sortMode="client" showPager="true">
										
										<div property="columns">
											<div type="checkcolumn" ></div>
									        <div field="taskName" width="150" headerAlign="center">节点名称</div>
									        <div field="taskTypeDesc" width="80" headerAlign="center" align="center">节点类型</div>
									        <div field="taskKey" width="80" headerAlign="center">节点Key</div>
									        
									        <div type="comboboxcolumn"  field="dataType" width="60" headerAlign="center" align="left">定义类别
									        	<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=form_definition_type" />
									        </div>
											<div type="comboboxcolumn"  field="formType" width="60" headerAlign="center" align="center">表单类型
												<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=act_form_type" />
											</div>
																				         
									        <div field="formUrl" width="80" headerAlign="center">表单URL
									        	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									        </div>
									        <div field="formDetailUrl" width="80" headerAlign="center">明细URL
									        	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									        </div>
									        
									        <div name="buttonListDesc" field="buttonListDesc" width="250" headerAlign="center">操作按钮</div>
											
											<!-- 
											<div field="remark" width="80" headerAlign="center" allowSort="true" align="center">备注</div>
											<div field="remark" width="160" headerAlign="center" allowSort="true" align="center">备注</div>
											<div field="appCode" width="160" headerAlign="center" allowSort="true" align="center">系统编码</div>
											<div field="gid" width="160" headerAlign="center" allowSort="true" align="center">全局编码</div>
											<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
											<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">更新日期</div>
											 -->
											 <div field="remark" width="80" headerAlign="center">备注</div>
										</div>
									</div>
								</div>
							</div>
							
							
							

							
							
							<!-- 
							<div title="节点消息模板" refreshOnClick="true" name="tabNodeMessageTemplate">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-redo" onclick="importNodeMessageTemplate()">导入节点</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-add" onclick="addObject('4')">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeObject('4')">删除</a>
												<span class="separator"></span> 
												<a class="mini-button" iconCls="icon-reload" onclick="refreshGroup()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="nodeMessageTemplateGrid" class="mini-treegrid" style="width:100%;height:100%;"  showPager="fales"
										showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="true" showCheckBox="false" 
    									treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  
										url="${pageContext.request.contextPath}/user/get_group_list" >
										
										<div property="columns">
											<div type="checkcolumn" ></div>
									        <div name="name" field="name" width="160" headerAlign="center">模板名称</div>
									        <div field="code" width="80" headerAlign="center">编码</div>
									        <div field="sn" width="30" align="right" headerAlign="center">序号</div>
									        <div field="appCode" width="60" align="left">所属应用</div>
									        <div field="nodePath" width="60" align="left">效果图</div>
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
									        <div name="id" field="id" width="30" >ID</div>
									        <div name="pid" field="pid" width="30" >父ID</div> 
										</div>
									</div>
								</div>
							</div>
							
							 
							
							
							
							
							
							
							
							
							

							
							
							<div title="其它参数" refreshOnClick="true" name="tabUserOrgs">
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
									<div id="otherParamGrid" class="mini-treegrid" style="width:100%;height:100%;" 
										showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="true" showCheckBox="false" 
    									treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  
										url="${pageContext.request.contextPath}/user/get_org_list" >
										<div property="columns">
									        <div type="checkcolumn" ></div>
									        <div name="name" field="name" width="160" headerAlign="center">名称</div>
									        <div field="code" width="80" headerAlign="center">编码</div>
									        <div field="sn" width="30" align="right" headerAlign="center">序号</div>
									        <div field="appCode" width="60" align="left">所属应用</div>
									        <div field="nodePath" width="60" align="left">节点路径</div>
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
									        <div name="id" field="id" width="30" >ID</div>
									        <div name="pid" field="pid" width="30" >父ID</div>  
										</div>
									</div>
								</div>
							</div>
							
							-->
							
							
							
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			mini.parse();
			
			var loginUserId = "${param.userId}";
			
			var tree = mini.get("tree1");
			var grid = mini.get("definitionGrid"); 
			var nodeGrid = mini.get("nodeGrid");
			var userSettingGrid = mini.get("userSettingGrid");
			var formSettingGrid = mini.get("formSettingGrid");
			var nodeMessageTemplateGrid = mini.get("nodeMessageTemplateGrid");
			
			var versionCmb = mini.get("versionCmb");
			grid.load();
			
	        tree.on("nodeselect", function (e) {
	        	 
	        	grid.load({ deployCategory: e.node.code });
	        });
	        
			grid.on("rowclick", function(e){
				var record = e.record;
				/* 
				column = e.column
		        field = e.field 
		        */
				nodeGrid.load({definitionId:record.id});
				userSettingGrid.load({definitionId:record.id});
				formSettingGrid.load({definitionId:record.id,dataType: 1}); // 1=节点表单 2=全局表单 
				//nodeMessageTemplateGrid.load({definitionId:record.id});
			});			

			function batchSetScript () {
				var row = grid.getSelected();
				if(!row){
					mini.alert("请选择一个流程定义");
					return ;
				}
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/node_form/batch_set_script.jsp?definitionId="+row.id,
					title : "批量设置按钮脚本",
					width : 700,
					height : 600,
					ondestroy : function(action) {
						/*
						var iframe = this.getIFrameEl();
						var users = iframe.contentWindow.GetData();
						if(users.length==0) {
							mini.alert("你没有选择用户");
							return ;
						}
						
						var userIds = new Array();
						for (var i=0;i<users.length;i++) {
							userIds.push(users[i].userId)
						}
						var data = {};
						data.userIds= userIds.join(",");
						data.definitionId = row.id;
        	            $.ajax({
        	                url: "${pageContext.request.contextPath}/act/node_user/init_node_user",
        	                data: data ,
        	                type: "post",
        	                success: function (text) {
        	                	userSettingGrid.reload();
        	                },
        	                error: function (jqXHR, textStatus, errorThrown) {
        	                    alert(jqXHR.responseText);
        	                }
        	            });*/
					}
				})
			}
			
			function searchNodeForm() {
				var row = grid.getSelected();
				if(!row){
					mini.alert("请选择一个流程定义");
					return ;
				}
				var keyUserTaskyKey = mini.get("keyUserTaskyKey").value;
				formSettingGrid.load({definitionId:row.id,taskKey: keyUserTaskyKey,dataType: 1}); // 1=节点表单 2=全局表单 
			}
			
			
			function initNodeUser () { // 初使化审批人
				var row = grid.getSelected();
				if(!row) {
					mini.alert("请选择一个流程定义");
					return ;
				}
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/syswin/uum/user/selector_user.jsp",
					title : "请选择初使化的节点审批【测试】用户",
					width : 700,
					height : 600,
					ondestroy : function(action) {
						var iframe = this.getIFrameEl();
						var users = iframe.contentWindow.GetDatas();
						if(users.length==0) {
							mini.alert("你没有选择用户");
							return ;
						}
						
						if(action == 'ok') {
							var userIds = new Array();
							for (var i=0;i<users.length;i++) {
								userIds.push(users[i].userId)
							}
							var data = {};
							data.userIds= userIds.join(",");
							data.definitionId = row.id;
	        	            $.ajax({
	        	                url: "${pageContext.request.contextPath}/act/node_user/init_node_user",
	        	                data: data ,
	        	                type: "post",
	        	                success: function (text) {
	        	                	userSettingGrid.reload();
	        	                },
	        	                error: function (jqXHR, textStatus, errorThrown) {
	        	                    alert(jqXHR.responseText);
	        	                }
	        	            });
						}
        	            
					}
				})
			}
			
			function removeNode() {
				var row = nodeGrid.getSelected();
				if (!row) {
					mini.alert("请选择一个节点设置");
					return ;
				}
		        mini.confirm("确定删除节点【"+row.taskName+"】设置？", "确定？",
	                function (action) {
	                    if (action == "ok") {
	        	            $.ajax({
	        	                url: "${pageContext.request.contextPath}/act/node/delete_by_ids",
	        	                data: { ids: row.id },
	        	                type: "post",
	        	                success: function (text) {
	        	                	nodeGrid.reload();
	        	                },
	        	                error: function (jqXHR, textStatus, errorThrown) {
	        	                    alert(jqXHR.responseText);
	        	                }
	        	            });
	                    }
	        		})
			}
			
			function onVersionValueChanged(e) {
				var v = versionCmb.getValue();
				var node = tree.getSelectedNode();
				definitionKey ="";
				if(typeof(node) !='undifiend') {
					category = node.code;
				}
				console.log(node)
				if(v==1) {
					grid.load({isDisplayNewest : true,category:category });
				} else {
					grid.load({category : category });
				}
			}
			
			function importDefinition() {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/definition/upload.jsp",
					title : "导入流程定义文件",
					width : 500,
					height : 300,
					ondestroy : function(action) {
						
						//var iframe = this.getIFrameEl();
						
						grid.reload();
					}
				})
			}
			
			function copyDefinition() {
				var row = grid.getSelected();
				if(!row){
					mini.alert("请选择要拷贝流程定义");
					return ;
				}
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/definition/upload_copy_settings.jsp",
					title : "导入新流程图，并复流程配置",
					width : 500,
					height : 400,
					ondestroy : function(action) {
						var iframe = this.getIFrameEl();
						grid.reload();
					}
				})
			}
			
			function exportDefinition() {
				
			}
			
			function refreshNode () {
				nodeGrid.reload();
			}
			
			function saveNode () {
				var data = nodeGrid.getChanges();
	            var json = mini.encode(data);
	            nodeGrid.loading("保存中，请稍后......");
	            $.ajax({
	                url: "${pageContext.request.contextPath}/act/node/save_or_update_json",
	                data: { data: json },
	                type: "post",
	                success: function (text) {
	                	nodeGrid.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
			}
			
			
			function editNodeForm(action) { //编辑节点表单、全局表单设置使用该方法
				var t = grid.getSelected();
				
				var row = {id:null,taskKey:null,taskName:null};
				if(action == 'edit') {
					row = formSettingGrid.getSelected();
					if(!row) {
						mini.alert("请选择一个任务节点");
						return ;
					}
				}
				
				if(action == 'editGlobal') {
					if(!t){
						mini.alert("请选择一个流程定义");
						return ;
					}
				}
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/node_form/edit.jsp",
					title : "节点表单设置",
					width : 500,
					height : 350,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {definitionId : t.id, id : row.id,taskKey : row.taskKey,taskName: row.taskName,action : action}
						//console.log(data);
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						formSettingGrid.reload();
					}
				});
				return ;
				
			}
			
			function saveNodeForm () {
			    var data = formSettingGrid.getChanges();
	            var json = mini.encode(data);
	            formSettingGrid.loading("保存中，请稍后......");
	            $.ajax({
	                url: "${pageContext.request.contextPath}/act/node_form/save_or_update_json",
	                data: { data: json },
	                type: "post",
	                success: function (text) {
	                	formSettingGrid.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
			}
			
			function deleteNodeForm () {
			    var ids = [];
			    var rows = formSettingGrid.getSelecteds();
			    if(rows.length ==0) {
			    	mini.alert("请至少选择一个表单节点记录");
			    	return ;
			    }
			    for(var i=0;i<rows.length;i++) {
			    	ids.push(rows[i].id);
			    }
		        mini.confirm("确定删除记录？", "确定？",
		                function (action) {
		                    if (action == "ok") {
		        	            $.ajax({
		        	                url: "${pageContext.request.contextPath}/act/node_form/delete?ids="+ids.join(","),
		        	                type: "post",
		        	                success: function (text) {
		        	                	formSettingGrid.reload();
		        	                },
		        	                error: function (jqXHR, textStatus, errorThrown) {
		        	                    alert(jqXHR.responseText);
		        	                }
		        	            });
		                    }
		                }
		            );

			}
			
			
			function importNodeForm () {
				var t = grid.getSelected();
				if(!t) {
					mini.alert("请选择一个流程定义");
					return ;
				}
		        mini.confirm("确定导入节点表单？（已配置的节点表单将会清除）", "确定？",
		                function (action) {
		                    if (action == "ok") {
		        				$.ajax({
		        					'url': "${pageContext.request.contextPath}/act/node_form/import_node?definitionId="+t.id,
		        					type: 'post',
		        					dataType:'JSON',
		        					cache: false,
		        					async:false,
		        					success: function (json) {
		        						if(json){
		        							mini.alert("导入成功");
		        							formSettingGrid.reload();
		        						}
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
			
			function importNode() {
				var t = grid.getSelected();
				if(!t) {
					mini.alert("请选择一个流程定义");
					return ;
				}
				mini.confirm("确定导入节点设置(已配置的记录将会被清除)？", "确定？",
						function (action) {
							if (action == "ok") {
								$.ajax({
									'url': "${pageContext.request.contextPath}/act/node/import_node?definitionId="+t.id,
									type: 'post',
									dataType:'JSON',
									cache: false,
									async:false,
									success: function (json) {
										if(json){
											mini.alert("导入成功");
											nodeGrid.reload();
										}
									},
									error : function(data) {
								  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
								  		mini.alert(data.responseText);
									}
								});
							}
				})
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
			
			function setGlobalScript() { //设置合局前置、后置脚本（一般为http的回调)
				var t = grid.getSelected();
				if(!t) {
					mini.alert("请选择一个流程定义");
					return ;
				}
				
				if(t) {
					mini.open({
						url : "${pageContext.request.contextPath}/apps/default/admin/act/definition/global_script_set.jsp",
						title : "【"+t.name+"】全局（回调）脚本设置",
						width : 700,
						height : 600,
						onload : function() {
							var iframe = this.getIFrameEl();
							var data = {definitionId : t.id , definitionName: t.name,definitionShortName:t.shortName,definitionKey:t.key, action: "edit"}
							console.log(data);
							iframe.contentWindow.SetData(data);
						},
						ondestroy : function(action) {
							formSettingGrid.reload();
						}
					});
					return ;
				}
			}
			
			function initButton() {
				var t = grid.getSelected();
				if(!t) {
					mini.alert("请选择一个流程定义");
					return ;
				}
				var data={}
				data.definitionId=t.id
				mini.confirm("确定初使化所有节点的按钮设置(已配置的按钮和后置脚本将会被清除)？", "确定？",
						function (action) {
							if (action == "ok") {
								$.ajax({
									'url': "${pageContext.request.contextPath}/act/node_button/init_all",
									type: 'post', dataType:'JSON', data: data,
									success: function (json) {
										if(json){
											mini.alert("初使化成功");
											formSettingGrid.reload();
										}
									},
									error : function(data) {
								  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
								  		mini.alert(data.responseText);
									}
								});
							}
				})
			}
			
			function setButton() {
				var t = grid.getSelected();
				if(!t) {
					mini.alert("请选择一个流程定义");
					return ;
				}
				
				var row = formSettingGrid.getSelected();
				if(!row) {
					mini.alert("请选择一个任务节点");
					return ;
				}
				
				
				if(row) {
					mini.open({
						url : "${pageContext.request.contextPath}/apps/default/admin/act/definition/node_button.jsp",
						title : "按钮设置",
						width : 800,
						height : 600,
						onload : function() {
							var iframe = this.getIFrameEl();
							var data = {definitionId : t.id,taskKey : row.taskKey,taskName: row.taskName, dataType : row.dataType}
							console.log(data);
							iframe.contentWindow.SetData(data);
						},
						ondestroy : function(action) {
							formSettingGrid.reload();
						}
					});
					return ;
				}
			}
			
			// 预览审批人
			function viewUser() {
				var t = grid.getSelected();
				if(!t){
					mini.alert("请选择一个流程定义");
					return ;
				}
				
				var row = userSettingGrid.getSelected();
				if(!row) {
					mini.alert("请选择一个任务节点");
					return ;
				}
				
				var openUserView = function(loginUserId,createDeptId) {
					if(typeof loginUserId == 'undefined' || loginUserId == null) {
						loginUserId = "";
					}
					
					if(typeof createDeptId == 'undefined' || createDeptId == null) {
						createDeptId = "";
					}
					console.log(loginUserId+" === "+createDeptId);
					
					mini.open({
						url : "${pageContext.request.contextPath}/apps/default/admin/act/definition/xincheng/node_user_result.jsp",
						title : "数据预览",
						width : 800,
						height : 600,
						onload : function() {
							var iframe = this.getIFrameEl();
							var data = {definitionId : t.id,taskKey : row.taskKey,loginUserId:loginUserId , createDeptId: createDeptId}
							iframe.contentWindow.SetData(data);
						},
						ondestroy : function(action) {
							grid.reload();
							
						}
					});
				}
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/syswin/uum/user/selector_user_createDeptId.jsp",
					title : "选择登陆用户",
					width : 800,
					height : 600,
					ondestroy : function(action) {
						if('ok' == action) {
							var iframe = this.getIFrameEl();
							var data = iframe.contentWindow.GetData();
							/*
							if(!data){
								mini.alert("请选择一个登陆用户!");
								return ;
							}*/
							if(data == null) {
								data = {};
							}
							data = mini.clone(data);
							openUserView(data.userId,data.createDeptId);
						}
						
					}
				});
			}
			
	 
			/*
			function displayNewestVersion() { // 只显示最新版本流程定义
				grid.load({isDisplayNewest : true});
			}
		 
			function displayAllVersion() {
				grid.load({});
			}*/
			function deploy () {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/definition/upload.jsp",
					title : "添加流程定义文件",
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
						displayAllVersion();
						//grid.load({});
						
					}
				});
			}
			
			function userSetting () {
				
				var taskKey = null;
				var definitionId = null;
				var definitionName = null;
				
				var row2 = grid.getSelected();
				if(row2){
					definitionId = row2.id;
					definitionName = row2.name;
				}else{
					mini.alert("请选择一个流程定义");
					return 
				}
				
				var row = userSettingGrid.getSelected();
				if(row){
					taskKey = row.taskKey;
				}else{
					 mini.alert("请选择一个任务节点");
					 return
				}
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/definition/xincheng/node_user.jsp",
					title : "【"+row.taskName+"】审批人设置",
					width : 850,
					height : 580,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							taskKey : taskKey,
							definitionId : definitionId,
							definitionName : definitionName
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						userSettingGrid.reload();
					}
				});
			}

			function refreshGrid(){
				 grid.reload();
				 nodeGrid.reload();

			}
			
			function search() {
				var data = {};
		 
				var key2 = mini.get("key2").value;
				if( (data.key==null || data.key=="") && (key2!=null && key2!="")){
					data.keyWord = key2;
				}
				
				grid.load(data);
			}
		 

			
			function save() {
			    var data = grid.getChanges();
	            var json = mini.encode(data);
	            //console.log(json);
	            loading();
	           
	            $.ajax({
	                url: "${pageContext.request.contextPath}/act/definition/save_or_update_json_short_name",
	                data: { data: json },
	                type: "post",
	                success: function (text) {
	                	grid.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
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