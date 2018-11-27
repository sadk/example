<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>企业信息管理</title>
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
			    <div class="mini-panel" showToolbar="true" showHeader="false" style="width:100%;height:100%;">
				     
				    <div style="padding-left:3px;padding-bottom:5px;">
			            <div id="form1" style="padding:5px;">
							<table>						
									<tr>
										<td>关键字 ：</td>
										<td>
											<input id="key" name="key"  class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
										</td>
									</tr>
						
									<tr>
										<td>编码：</td>
										<td>
											<input id="code" name="code"   class="mini-textbox"  emptyText="请输入组名称"  onenter="search"/>
										</td>
									</tr>
								  
									<tr>
										<td>简称：</td>
										<td>
											<input id="shortName" name="shortName"   class="mini-textbox"  emptyText="请输入备注"  onenter="search"/>
										</td>
									</tr>
									
									<tr>
										<td>全称：</td>
										<td>
											<input id="fullName" name="fullName"   class="mini-textbox"  emptyText="请输入备注"  onenter="search"/>
										</td>
									</tr>
							</table>
					        <div style="text-align:center;padding:10px;">
								<a class="mini-button" onclick="search()" iconCls="icon-search" style="width:60px;margin-right:20px;">查询</a>
								<a class="mini-button" onclick="clear()" iconCls="icon-cancel" style="width:60px;margin-right:20px;">清空</a>
							</div>
					    </div>
					</div>
					
					
				</div>       
			</div>
			<div showCollapseButton="true">
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						    <div title="企业信息" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="edit('add')">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit('edit')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
											</td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
									<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="true"
										url="${pageContext.request.contextPath}/rst/company/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
									    <div property="columns">
									        <div type="checkcolumn"></div>
									        <div field="shortName" width="120" headerAlign="center">简称</div>
									        <div field="fullName" width="160" headerAlign="center">全称</div>
									        <div field="code" width="100" headerAlign="center">公司编码</div>
									        <div type="comboboxcolumn" field="status" width="50" headerAlign="center" align="center" allowSort="true">状态
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dic_active_status" />
											</div>
									        <div field="introduction" width="200" headerAlign="center" align="left" >介绍</div>
									        <!-- 
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
									         -->
									    </div>
									</div>
						        </div>
						    </div>
						</div>
					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							
							<div title="公司地址" refreshOnClick="true" name="tabReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="editAddress('add')">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editAddress('edit')">修改</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeAddress()">删除</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-reload" onclick="refreshAddress()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="datagrid2" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="false"
										url="${pageContext.request.contextPath}/rst/work_address/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
										<div property="columns">
											<div type="checkcolumn"></div>
											<!-- <div field="id" width="60" headerAlign="center">ID</div> -->
									        <div field="code" width="120" headerAlign="center">地址编码</div>
									        <div field="provinceName" width="80" align="center" headerAlign="center">省份</div>
									        <div field="cityName" width="80" align="center" headerAlign="center">城市</div>
									        <div field="areaName" width="80" align="center" headerAlign="center">区域</div>
									        <div field="address" width="250" align="left" headerAlign="center">详细地址</div>
										</div>
									</div>
								</div>
							</div>
							




							<div title="企业图片" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="editPicture('add')">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editPicture('edit')">修改</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removePicture()">删除</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-reload" onclick="refreshPicture()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="dataGrid3" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" multiSelect="true" 
										url="${pageContext.request.contextPath}/rst/company_picture/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="id" width="40" headerAlign="center" allowSort="true" align="center">ID</div>
											<div field="url" width="660" headerAlign="center" allowSort="true" align="left">图片地址</div>
											 
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
			
			
			var grid = mini.get("datagrid1"); // 称谓列表
			var addressGrid = mini.get("datagrid2");// 用户列表
			var pictureGrid = mini.get("dataGrid3");
			
		 
			function search() {
				var data = form.getData();
				grid.load(data);
			}
			
			grid.on("rowclick", function(e){
				var record = e.record;
				addressGrid.load({companyCode:record.code}); 
				pictureGrid.load({companyCode:record.code});
			});
	
			function removeAddress() {
				var row = addressGrid.getSelecteds();
				if (!row) {
					mini.alert("请选至少勾选一个地址记录");
				}
				var ids = [];
				for(var i=0;i<row.length;i++) {
					ids.push(row[i].id);
				}
				mini.confirm("确定删除？", "确定？",
					function (action) {
						if (action == "ok") {
							$.ajax({
								'url': "${pageContext.request.contextPath}/rst/work_address/delete?ids="+ids.join(","),
								type: 'post', dataType:'JSON',
								success: function (json) {
									mini.alert("删除成功");
									addressGrid.reload();
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
			
			function editAddress(action) {
				var row = grid.getSelected();
				if('add' == action) {
					if(!row) {
						mini.alert("请选择一个企业");
						return ;
					}
				}
				
				var rowAddr = addressGrid.getSelected();
				if('edit' == action) {
					if(!rowAddr) {
						mini.alert("请选择一个地址信息");
						return ;
					}
				}
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/rst/company/edit_address.jsp",
					title : "编辑地址",
					width : 480,
					height : 300,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {};
						data.action = action;
						
						if('edit' == action) {
							data.id = row.id;
						}
						
						if('add' == action) {
							data.companyCode = row.code;
						}
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						addressGrid.reload();
					}
				});
			 
			}
			
			function edit(action) {
				var row = grid.getSelected();
				if('edit' == action) {
					if(!row) {
						mini.alert("请至少选择一条记录");
						return ;
					}
				}
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/rst/company/edit.jsp",
					title : "编辑信息",
					width : 480,
					height : 300,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {};
						data.action = action;
						
						if('edit' == action) {
							data.id = row.id;
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
								'url': "${pageContext.request.contextPath}/rst/company/delete?ids="+ids.join(","),
								type: 'post', dataType:'JSON',
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
			
		</script>
	</body>
</html>