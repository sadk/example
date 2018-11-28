<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>角色表</title>
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
		    <div size="240" showCollapseButton="true">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <span style="padding-left:5px;">分类名：</span>
		            <input class="mini-textbox" width="100"/>
		            <a class="mini-button" iconCls="icon-search" plain="true">查找</a>                  
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/syswin/role_category/all" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" >        
		            </ul>
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
								
								<!-- 
								<a class="mini-button" iconCls="icon-node" onclick="edit('view')">查看</a>
								<span class="separator"></span>  
								<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
								<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
								<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
								 -->
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
					 url="${pageContext.request.contextPath}/syswin/role/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50">
						<div property="columns">
							<div type="checkcolumn" ></div>
							<div field="id" width="60" headerAlign="center" allowSort="true" align="center">角色ID</div>
							<div field="name" width="120" headerAlign="center" allowSort="true" align="left">名称</div>
							<div field="code" width="80" headerAlign="center" allowSort="true" align="center">编码</div>
							<div field="categoryId" width="60" headerAlign="center" allowSort="true" align="center">分类ID</div>
							<div field="categoryName" width="80" headerAlign="center" allowSort="true" align="left">分类名称</div>
							
							<div field="typeDesc" width="60" headerAlign="center" allowSort="true" align="center">角色类型</div>
							<div field="description" width="160" headerAlign="center" allowSort="true" align="center">系统编码</div>
							
							<div field="createTime" dateFormat="yyyy-MM-dd" width="120" headerAlign="center" allowSort="true" align="center">创建日期</div>
							<div field="updateTime" dateFormat="yyyy-MM-dd" width="120" headerAlign="center" allowSort="true" align="center">更新日期</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		mini.parse();
		
		var tree = mini.get("tree1");
		var grid = mini.get("datagrid1");
		
        tree.on("nodeselect", function (e) {
        	//console.log(e.node.id )
        	grid.load({ categoryId: e.node.id });
        });
        
		grid.load();

		
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
							'url': "${pageContext.request.contextPath}/syswin/role/delete?ids="+ids.join(","),
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
			var categoryRow = tree.getSelected();
			//console.log(categoryRow);
			
			if(!categoryRow){
				mini.alert("请在左侧树选择一个角色分类!");
				return ;
			}
			
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/syswin/uum/role/edit.jsp",
				title : "添加角色",
				width : 500,
				height : 200,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {
						action : "add",
						categoryId : categoryRow.id,
						categoryName: categoryRow.name
					};
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					grid.reload();
				}
			});
		}
		

		function edit(action) {
			var categoryRow = tree.getSelected();
			if(!categoryRow){
				mini.alert("请在左侧树选择一个角色分类!");
				return ;
			}
			
			var row = grid.getSelected();
			if(typeof(action) == 'undefined') {
				action = "edit";
			}
			//alert(row.id);
			if (row) {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/syswin/uum/role/edit.jsp",
					title : "编辑角色信息",
					width : 500,
					height : 200,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : action,
							id : row.id,
							categoryId : categoryRow.categoryId,
							categoryName: categoryRow.categoryName
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
		
		</script>
	</body>
</html>