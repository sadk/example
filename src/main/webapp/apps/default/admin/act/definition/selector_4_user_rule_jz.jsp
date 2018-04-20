<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>流程定义选择器</title>
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
<div style="height:100%; overflow:auto;">
		<div class="mini-splitter" style="width:100%;height:92%; overflow:auto;">
			<div size="220" showCollapseButton="true">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <span style="padding-left:5px;">流程分类：</span>
		            <!--  <input class="mini-textbox" width="120"/> -->
		            
		            <input showNullItem="false" width="140" class="mini-combobox" url="${pageContext.request.contextPath}/application/all" textField="name" valueField="id" />
		            
		           <!--  <a class="mini-button" iconCls="icon-search" plain="true">查找</a>      -->             
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/act/category/all?dataType=1" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" expandOnLoad="true">        
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
						            	 idField="id" allowResize="false" multiSelect="false"  sizeList="[5,10,20,50]" 
						            	 pageSize="20" showEmptyText="true" emptyText="暂无查询信息" sortMode="client"
						            	  allowCellEdit="false" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" 
						              	 url="${pageContext.request.contextPath}/act/definition/page" >
						                <div property="columns">
											<div type="checkcolumn" ></div>
											<div field="id" width="180" headerAlign="center" allowSort="true" align="left">流程定义ID</div>
											<!-- 
											<div field="shortName" width="100" headerAlign="center" allowSort="true" align="left">流程简称
												<input property="editor" class="mini-textbox" style="width:100%;" />
											</div>
											 -->
											<div field="version" width="80" headerAlign="center" allowSort="true" align="center">流程版本号</div>
											
											<div field="name" width="300" headerAlign="center" allowSort="true" align="left">流程全称</div>
											<div type="comboboxcolumn" field="enableMobile" width="80" headerAlign="center" align="center" allowSort="true">移动端启用
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" />
											</div>
											
											<div field="key" width="160" headerAlign="center" allowSort="true" align="left">流程Key</div>
											
											
											<div field="deploymentId" width="80" headerAlign="center" allowSort="true" align="center">流程布署ID</div>
											<!--
											<div field="resourceName" width="250" headerAlign="center" allowSort="true" align="left">流程定义XML</div>
											<div field="dgrmResourceName" width="250" headerAlign="center" allowSort="true" align="left">流程资源图</div>
											
											<div field="description" width="160" headerAlign="center" allowSort="true" align="left">描述</div>
											  -->
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

							<div title="节点启用" refreshOnClick="true" name="tabUserReses">
								 
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<!-- 
												<a class="mini-button" iconCls="icon-edit" onclick="eanbleFlowRule()">启用</a>
												<span class="separator"></span>
												 -->
												
												<a class="mini-button" iconCls="icon-reload" onclick="refreshNode()" plain="true">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								 
								<div class="mini-fit">
									<div id="nodeGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" multiSelect="false" allowResize="false"
										showEmptyText="true" emptyText="查无数据"  allowCellEdit="false" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true"
										sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" sortMode="client" showPager="fales"
										 url="${pageContext.request.contextPath}/act/node/list" >
										<div property="columns">
									        <div type="checkcolumn" ></div>
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
									        
										</div>
									</div>
								</div>
							</div>

							
						</div>
					</div>
				</div>
			</div>
		</div>
			<div id="subbtn" style="text-align:center;padding:10px;">
				<a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>
			</div>
</div>
		<script type="text/javascript">
			mini.parse();
			
			var loginUserId = "${param.userId}";
			
			var tree = mini.get("tree1");
			var grid = mini.get("definitionGrid"); 
			var nodeGrid = mini.get("nodeGrid");
			
			grid.load();
			
	        tree.on("nodeselect", function (e) {
	        	grid.load({ deployCategory: e.node.code });
	        });
	        
	        function GetData(){
	        	var data = {}
	        	data.definitionId = grid.getSelected().id;
	        	data.definitionName =  grid.getSelected().name;
	        	data.version =  grid.getSelected().version;
	        	
	        	data.taskKey = nodeGrid.getSelected().taskKey;
	        	data.taskName = nodeGrid.getSelected().taskName;
	        	return data;
	        }
	        
			grid.on("rowclick", function(e){
				var record = e.record;
				/* 
				column = e.column
		        field = e.field 
		        */
				nodeGrid.load({definitionId:record.id});
				 
			});
			
			function refreshNode () {
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
		 

			
  
			function CloseWindow(action) {
				if(action == "close" && form.isChanged()) {
					if(confirm("数据被修改了，是否先保存？")) {
						return false;
					}
				}
				if(window.CloseOwnerWindow)
					return window.CloseOwnerWindow(action);
				else
					window.close();
			}
			
			function onOk(e) {
				var row = grid.getSelected();
				if(!row){
					mini.alert("请选择一个流程定义");
					return ;
				}
				
	        	var row2 = nodeGrid.getSelected();
				if(!row2) {
					mini.alert("请选择启用的一个审批节点");
					return ;
				}
				
	        	CloseWindow("ok");
			}

			function onCancel(e) {
				CloseWindow("cancel");
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