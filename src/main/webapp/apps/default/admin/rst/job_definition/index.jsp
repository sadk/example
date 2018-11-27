<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>岗位定义</title>
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
							<td>岗位编码：</td>
							<td>
								<input id="code" name="code"  style="width:140px" class="mini-textbox"  emptyText="请输入岗位编码"  onenter="search"/>
							</td>
						</tr>
						<tr>
							<td>岗位名称：</td>
							<td>
								<input id="name" name="name"  style="width:140px" class="mini-textbox"  emptyText="请输入岗位名称"  onenter="search"/>
							</td>
						</tr>
						<tr>
							<td>是否启用：</td>
							<td>
								<input id="enable" name="enable" class="mini-combobox" style="width:140px" valueField="value" textField="name" showNullItem="true" nullItemText="请选择..." emptyText="请选择"  url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" />
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
								<a class="mini-button" iconCls="icon-add" onclick="edit('add')">新增</a>
								<a class="mini-button" iconCls="icon-edit" onclick="edit('edit')">编辑</a>
								<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
								
								<span class="separator"></span>  
								<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
								 
							</td>
						</tr>
					</table>
				</div>
				<div class="mini-fit">
					<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="true"
					url="${pageContext.request.contextPath}/rst/job_definition/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
					<div property="columns">
						<div type="checkcolumn" ></div>
						<div field="id" width="60" headerAlign="center" allowSort="true" align="center" >ID</div>
						<div field="name" width="140" headerAlign="center" allowSort="true" align="left" >名称</div>
						<div field="code" width="140" headerAlign="center" allowSort="true" align="left">编码</div>
						<div field="sn" width="80" headerAlign="center" allowSort="true" align="center">排序号</div>
						<div field="enable" width="80" headerAlign="center" allowSort="true" align="center">是否启用</div>
						
						<!-- <div field="gid" width="160" headerAlign="center" allowSort="true" align="left">全局编码</div> -->
						<div field="createTime" width="150" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
						<div field="updateTime" width="150" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
									        
					</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");
		var form = new mini.Form("#form1");
		
		
		function clear() {
			 form.clear();
		}

		function search() {
			var data = form.getData();
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
							'url': "${pageContext.request.contextPath}/rst/job_definition/delete?ids="+ids.join(","),
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

		function edit(action) {
			var row = grid.getSelected();
			if ('edit' == action) {
				if(!row) {
					mini.alert("请选择一条记录");
					return ;
				}
			}
			
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/admin/rst/job_definition/edit.jsp",
				title : "编辑岗位",
				width : 490,
				height : 220,
				onload : function() {
					var iframe = this.getIFrameEl();
					
					var data = {};
					data.action = action
					if ('edit' == action) {
						data.id = row.id
					}
					
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					grid.reload();
				}
			});
		}
		
		function exportData() {
			var exportDataType = mini.get("exportDataType").value;
			var exportFileType = mini.get("exportFileType").value;
			//<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
			//<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
			mini.confirm("确定导出记录？", "确定？",
		            function (action) {
		                if (action == "ok") {
		    				var o = form.getData();
		    				
							var url = "${pageContext.request.contextPath}/application/export?exportFileType="+exportFileType+"&exportDataType="+exportDataType;
							location.href=url;
						}
					});
			
		}
		</script>
	</body>
</html>