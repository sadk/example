<#noparse><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%></#noparse>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>上下布局报表模板</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="<#noparse>${pageContext.request.contextPath}</#noparse>/scripts/boot.js"></script>
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
		<div class="mini-splitter" style="width:100%;height:100%;" vertical="true">
			<div size="30%" showCollapseButton="true">
				<div id="form1"  style="padding:8px;">
					<table>						
						<tr>
							<td>关键字 ：</td>
							<td>
								<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
							</td>
							<td>名称：</td>
							<td>
								<input id="name" name="name"  style="width:140px" class="mini-textbox"  emptyText="请输入系统名称"  onenter="search"/>
							</td>
							<td>是否启用：</td>
							<td>
								<input id="enable" name="enable" class="mini-combobox" style="width:140px" valueField="value" textField="name" showNullItem="true" nullItemText="请选择..." emptyText="请选择"  url="<#noparse>${pageContext.request.contextPath}</#noparse>/dictionary/option?code=yes_or_no" />
							</td>
							<td>备注：</td>
							<td>
								<input id="remark" name="remark" class="mini-textbox" style="width:140px" emptyText="请输入备注" onenter="search"/>
							</td>
							<td>创建时间(开始)：</td>
							<td>
								<input id="createTimeBegin" name="createTimeBegin" class="mini-datepicker" style="width:140px" emptyText="请输入"/>
							</td>
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
					url="<#noparse>${pageContext.request.contextPath}</#noparse>/application/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
					<div property="columns">
						<div type="checkcolumn" ></div>
						
						<div field="tenantName" width="80" headerAlign="center" allowSort="true" align="left" >租户</div>
						<div field="name" width="160" headerAlign="center" allowSort="true" align="left" >系统名称</div>
						<div field="code" width="80" headerAlign="center" allowSort="true" align="left">系统编码</div>
						<div field="sn" width="60" headerAlign="center" allowSort="true" align="right">排序号</div>
						<div field="remark" width="160" headerAlign="center" allowSort="true" align="left">备注</div>
						<div field="enableDesc" width="60" headerAlign="center" allowSort="true" align="center">是否启用</div>
						
						<div field="gid" width="160" headerAlign="center" allowSort="true" align="left">全局编码</div>
						<div field="createTimeDesc" width="100" headerAlign="center" allowSort="true" align="center" >创建时间</div>
						<div field="updateTimeDesc" width="100" headerAlign="center" allowSort="true" align="center">更新时间</div>
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
				e.cellHtml = '<a href="javascript:void(0)" onclick="view('+row.id+')">' + row.name + '</a>'; // style="text-decoration:none;"
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
							'url': "<#noparse>${pageContext.request.contextPath}</#noparse>/application/delete?ids="+ids.join(","),
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
				url : "<#noparse>${pageContext.request.contextPath}</#noparse>/apps/default/admin/sys/application/edit.jsp",
				title : "添加系统",
				width : 490,
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
					url : "<#noparse>${pageContext.request.contextPath}</#noparse>/apps/default/admin/sys/application/edit.jsp",
					title : "编辑系统信息",
					width : 490,
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
			//<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
			//<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
			mini.confirm("确定导出记录？", "确定？",
		            function (action) {
		                if (action == "ok") {
		    				var o = form.getData();
		    				
							var url = "<#noparse>${pageContext.request.contextPath}</#noparse>/application/export?exportFileType="+exportFileType+"&exportDataType="+exportDataType;
							location.href=url;
						}
					});
			
		}
		</script>
	</body>
</html>