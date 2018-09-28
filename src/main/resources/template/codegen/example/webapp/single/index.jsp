<#noparse><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%></#noparse>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${comment}</title>
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
						
						<#list columnList as column>
							<#if column.oroColumnType == 4 || column.oroColumnType == 2>
								<#if column.searchType == 1 >
									<#-- 字符型 -->							
									<#if column.javaType == 0>
									<tr>
										<td>${column.comment}：</td>
										<td>
											<input id="${column.propertyName}" name="${column.propertyName}"  style="width:140px" class="mini-textbox"  emptyText="请输入${column.comment}"  onenter="search"/>
										</td>
									</tr>
									</#if>
									
									<#-- 数字型 -->
									<#if column.javaType == 2 || column.javaType == 3 || column.javaType == 4 || column.javaType == 5 || column.javaType == 6 || column.javaType == 7 || column.javaType == 10 || column.javaType == 11>
									<tr>
										<td>${column.comment}：</td>
										<td>
											<input name="${column.propertyName}" id="${column.propertyName}" class="mini-spinner" class="mini-datepicker" minValue="0" maxValue="999999999"  />
										</td>
									</tr>
									</#if>
									
									<#-- 日期型 -->
									<#if column.javaType == 9 || column.javaType == 12 || column.javaType == 13 || column.javaType == 14>
									<tr>
										<td>${column.comment}(开始)：</td>
										<td>
											<input id="${column.propertyName}Begin" name="${column.propertyName}Begin" format="yyyy-MM-dd" class="mini-datepicker" style="width:140px" emptyText="请输入${column.comment}" />
										</td>
									</tr>
									<tr>
										<td>${column.comment}(结束)：</td>
										<td>
											<input id="${column.propertyName}End" name="${column.propertyName}End" format="yyyy-MM-dd" class="mini-datepicker" style="width:140px"  emptyText="请输入${column.comment}"/>
										</td>
									</tr>
									</#if>
									
								</#if>
							</#if>
						</#list>
						
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
					url="<#noparse>${pageContext.request.contextPath}</#noparse>/${module}/${model}/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
					<div property="columns">
						<div type="checkcolumn" ></div>
						<#list columnList as column>
						<#if column.primaryKey == 0 >
						<div field="${column.propertyName}" <#if column.javaType == 9 || column.javaType == 12 || column.javaType == 13 || column.javaType == 14>dateFormat=<#if column.columnCodegenFormat??>"${column.columnCodegenFormat}"<#else>"yyyy-MM-dd"</#if></#if> width="160" headerAlign="center" allowSort="true" align="center">${column.comment}</div>
						</#if>
						</#list>
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
							'url': "<#noparse>${pageContext.request.contextPath}</#noparse>/${module}/${model}/delete?ids="+ids.join(","),
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
				url : "<#noparse>${pageContext.request.contextPath}</#noparse>/apps/default/admin/${module}/${model}/edit.jsp",
				title : "添加",
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
					url : "<#noparse>${pageContext.request.contextPath}</#noparse>/apps/default/admin/${module}/${model}/edit.jsp",
					title : "编辑",
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
		
		</script>
	</body>
</html>