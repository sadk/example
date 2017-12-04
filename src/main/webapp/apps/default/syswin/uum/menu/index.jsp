<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>菜单管理</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/pagertree.js" ></script>
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
			<div size="270" showCollapseButton="true">
				<div id="form1"  style="padding:8px;">
					<table>						
						<tr>
							<td>关键字 ：</td>
							<td>
								<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
							</td>
						</tr>
						<tr>
							<td>所属应用：</td>
							<td>
								<input id="appCode" name="appCode" class="mini-combobox" style="width:140px"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/application/all" />
							</td>
						</tr>
						<tr>
							<td>菜单名称：</td>
							<td>
								<input id="name" name="name"  style="width:140px" class="mini-textbox"  emptyText="请输入菜单名称"  onenter="search"/>
							</td>
						</tr>
						
						<tr>
							<td>菜单编码：</td>
							<td>
								<input id="code" name="code" class="mini-textbox" style="width:140px" emptyText="请输入编码" onenter="search"/>
							</td>
						</tr>
						<tr>
							<td>备注：</td>
							<td>
								<input id="remark" name="remark" class="mini-textbox" style="width:140px" emptyText="请输入备注" onenter="search"/>
							</td>
						</tr>
						 
						<tr>
							<td>创建时间(开始)：</td>
							<td>
								<input id="createTimeBegin" name="createTimeBegin" class="mini-datepicker" style="width:140px" emptyText="请输入"/>
							</td>
						</tr>
						<tr>
							<td>创建时间(结束)：</td>
							<td>
								<input id="createTimeEnd" name="createTimeEnd" class="mini-datepicker" style="width:140px"  emptyText="请输入"/>
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
								<!-- 
								<a class="mini-button" iconCls="icon-save" onclick="save()">保存</a>
								 -->
								<span class="separator"></span>  
								<a class="mini-button" iconCls="icon-split" onclick="repairNodePath()">修复结点路径</a>
								<!--  
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
					<div id="datagrid1" class="mini-treegrid" style="width:100%;height:100%;"
					showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="false" 
    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false" 
					url="${pageContext.request.contextPath}/syswin/menu/all" > 
					    <div property="columns">
					        <div type="checkcolumn"></div>
					        <div name="name" field="name" width="160" headerAlign="center">名称</div>
					        <div field="code" width="80" headerAlign="center">编码</div>
					        <!-- 
					        <div field="sn" width="30" align="right" headerAlign="center">序号</div>
					        <div field="appCode" width="60" align="left">所属应用</div>
					         -->
					        <div field="url" width="160" align="left">菜单URL</div>
					        <div field="nodePath" width="120" align="left">结点路径</div>
					        
					        <!-- 
					        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
					         -->
					        <div name="id" field="id" width="30" >ID</div>
					        <div name="pid" field="pid" width="30" >父ID</div>              
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
		
		function repairNodePath() {
			$.ajax({
				'url': "${pageContext.request.contextPath}/syswin/menu/repair_node_path",
				type: 'post',
				dataType:'JSON',
				cache: false,
				async:false,
				success: function (json) {
					mini.alert("修复成功");
					grid.reload();
				},
				error : function(data) {
			  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
			  		mini.alert(data.responseText);
				}
			});
			
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

		function remove() {
			var row = grid.getSelected();
			if(row) {
				mini.confirm("删除当前菜单，其子菜单也即将删除，确定删除？", "确定？",
						function (action) {
							if (action == "ok") {
								$.ajax({
									'url': "${pageContext.request.contextPath}/org/delete?ids="+row.id,
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
			} else {
				mini.alert("请选中一条以上的记录");
			}
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
				url : "${pageContext.request.contextPath}/apps/default/syswin/uum/menu/edit.jsp",
				title : "添加菜单",
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
					url : "${pageContext.request.contextPath}/apps/default/syswin/uum/menu/edit.jsp",
					title : "编辑菜单信息",
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
		
		</script>
	</body>
</html>