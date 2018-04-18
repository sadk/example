<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户规则定义</title>
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
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/act/category/all?dataType=2" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" expandOnLoad="true">        
		            </ul>
		        </div>
			</div>
			<div showCollapseButton="true">
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						
						
						
						    <div title="用户规则定义" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="edit('add')">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit('edit')">编辑</a>
												<a class="mini-button" iconCls="icon-save" onclick="saveSimple()">保存</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-node" onclick="viewUser()">预览用户</a>
											</td>
											<!-- 
											<td style="white-space:nowrap;">
						                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search()">查询</a>
						                    </td>
						                     -->
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
						            <div id="userRuleGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
						            	 idField="id" allowResize="false" multiSelect="true"  sizeList="[5,10,20,50]" 
						            	 pageSize="20" showEmptyText="true" emptyText="暂无查询信息" sortMode="client"
						            	  
						            	 allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" 
						            	  
						              	 url="${pageContext.request.contextPath}/act/user_rule/page" >
						                <div property="columns">
											<div type="checkcolumn" ></div>
											<div field="id" width="60" headerAlign="center" allowSort="true" align="left">ID</div>
											<div field="name" width="80" headerAlign="center" allowSort="true" align="left">名称</div>
											<div field="code" width="80" headerAlign="center" allowSort="true" align="left">编码</div>
											<div type="comboboxcolumn" field="enable" width="80" headerAlign="center" align="center" allowSort="true">启用规则矩阵
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" />
											</div>
											<div field="categoryName" width="60" headerAlign="center" allowSort="true" align="left">分类名称</div>
											<div field="resolveTypeDesc" width="80" headerAlign="center" allowSort="true" align="center">解析引擎</div>
											<div field="content" width="180" headerAlign="center" allowSort="true" align="left">内容</div>
										</div>
						            </div>
						        </div>
						    </div>



						</div>

					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">

							<div title="用户预览结果" refreshOnClick="true" name="tabUserReses">
								<!--  --> 
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-cancel" onclick="clearResult()">清空结果集</a>
												
											</td>
										</tr>
									</table>
								</div>
								
								<div class="mini-fit">
									<div id="userGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="暂无预览后的用户规则数据"  allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true"
										sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" sortMode="client" showPager="false"
										 url="${pageContext.request.contextPath}/act/user_rule/view_user" >
										<div property="columns">
											<div field="userId" width="80" headerAlign="center" allowSort="true" align="center">ID</div>
											<div field="userName" width="160" headerAlign="center" allowSort="true" align="center">姓名</div>
											<div field="loginNo" width="160" headerAlign="center" allowSort="true" align="center">帐号</div>
											<div field="workNo" width="160" headerAlign="center" allowSort="true" align="center">工号</div>
											<div field="statusDesc" width="160" headerAlign="center" allowSort="true" align="center">状态</div>
											<div field="email" width="160" headerAlign="center" allowSort="true" align="center">邮箱</div>
											<div field="mobile" width="160" headerAlign="center" allowSort="true" align="center">手机</div>
											<div field="telephone" width="160" headerAlign="center" allowSort="true" align="center">电话</div>
											<div field="sexDesc" width="80" headerAlign="center" allowSort="true" align="center">性别</div>
											<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
											<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">更新日期</div>
											
										</div>
									</div>
								</div>
							</div>




							<div title="预览日志" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-cancel" onclick="clearResult()">清空日志</a>
											</td>
										</tr>
									</table>
								</div>
								
								<div class="mini-fit">
									  <input id="message" name="message"  class="mini-textarea" style="width: 100%;height: 100%;background-color: black;"/>
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
			var grid = mini.get("userRuleGrid");
			var userGird = mini.get("userGrid");
			var message = mini.get("message");
			grid.load();
			
	        tree.on("nodeselect", function (e) {
	        	//alert(e.node.id);
	        	grid.load({ categoryId: e.node.id });
	        });

	        function saveSimple() {
	        	var rows = grid.getChanges(); 
	         	
	        	var data={};
	         	data.userRulesJson = mini.encode(rows);
	         	
	        	if (rows.length!=0) {
					$.ajax({
					    'url': "${pageContext.request.contextPath}/act/user_rule/save_or_update_simple",
						type: 'post', dataType:'JSON', data:data ,
						success: function (json) {
							mini.alert("保存成功");
							grid.reload();
						},
						error : function(data) {
					  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
					  		mini.alert(data.responseText);
						}
					});
	        	}
	        }
	        
	        function viewUser(){
	        	var row = grid.getSelected();
        		if(!row){
        			mini.alert("请选择一个规则定义");
        			return ;
        		}
        		var data = {};
        		
        		data.ruleId = row.id;
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/syswin/uum/user/selector_user_createDeptId.jsp",
					title : "选择登陆用户上下文",
					width : 600,
					height : 400,
					
					ondestroy : function(action) {
						if(action == 'ok'){
							var iframe = this.getIFrameEl();
							var temp = iframe.contentWindow.GetData();
							console.log(temp)
							
							if(!temp && temp.userId == null){
								mini.alert("您没有选择任何登陆用户上下文");
								return ;
							}
							
							
							temp = mini.clone(temp);
							data.userId = temp.userId;
							data.createDeptId = temp.createDeptId;
							console.log(temp);
							$.ajax({
								'url': "${pageContext.request.contextPath}/act/user_rule/view_user",
								type: 'post', dataType:'JSON', cache: false, async:false,
								data: data,
								success: function (json) {
									
									message.setValue(json.message);
									
									if(json.status==0){
										mini.alert("预览成功！");
										userGird.load(data);
									}else {
										mini.alert("预览失败,请查看预览日志！");
										userGird.clearRows();
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
	        
	        function clearResult () {
	        	userGird.setData([]);
	        }
	        
	        function edit(action){
	        	var row = null;
	        	if('edit' == action) {
	        		row = grid.getSelected();
	        		if(!row){
	        			mini.alert("请选择一个规则定义");
	        			return ;
	        		}
	        	}
	        	
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/user_rule/edit.jsp",
					title : "编辑用户规则",
					width : 600,
					height : 440,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action: action
						}
						if(action == 'edit'){
							data.id=row.id
						}
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						grid.reload();
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
								'url': "${pageContext.request.contextPath}/act/user_rule/delete?ids="+ids.join(","),
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
			
		 
			
			function search() {
				var data = form.getData();
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

		</script>
	</body>
</html>