<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户表</title>
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
			<div size="250" showCollapseButton="true">
				<div id="form1"  style="padding:8px;margin-left: 8px;">
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
											<input id="code" name="code"   class="mini-textbox"  emptyText="请输入用户编码"  onenter="search"/>
										</td>
									</tr>
									<tr>
										<td>姓名：</td>
										<td>
											<input id="realName" name="realName"   class="mini-textbox"  emptyText="请输入姓名"  onenter="search"/>
										</td>
									</tr>
									
									<tr>
										<td>昵称：</td>
										<td>
											<input id="nickName" name="nickName"   class="mini-textbox"  emptyText="请输入昵称"  onenter="search"/>
										</td>
									</tr>
									<tr>
										<td>手机号：</td>
										<td>
											<input id="mobile" name="mobile"   class="mini-textbox"  emptyText="请输入手机号"  onenter="search"/>
										</td>
									</tr>
									<tr>
										<td>微信账号：</td>
										<td>
											<input id="wxAccount" name="wxAccount"   class="mini-textbox"  emptyText="请输入手机号"  onenter="search"/>
										</td>
									</tr>
									
									<%-- 
									<tr>
										<td>状态 </td>
										<td>
											<input id="status" name="status" value="1" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=user_status" />
										</td>
									</tr> 
									--%>
									
									<tr>
										<td>邮箱：</td>
										<td>
											<input id="email" name="email"   class="mini-textbox"  emptyText="请输入邮箱"  onenter="search"/>
										</td>
									</tr>
									
									<tr>
										<td>手机：</td>
										<td>
											<input id="mobile" name="mobile"   class="mini-textbox"  emptyText="请输入手机"  onenter="search"/>
										</td>
									</tr>
									
									
									<tr>
										<td>电话：</td>
										<td>
											<input id="tel" name="tel"   class="mini-textbox"  emptyText="请输入电话"  onenter="search"/>
										</td>
									</tr>
									
									<tr>
										<td>性别</td>
										<td>
											<input id="sex" name="sex" value="1" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=sex"  />
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
								<!-- <a class="mini-button" iconCls="icon-add" onclick="edit('add')">添加</a>
								<a class="mini-button" iconCls="icon-edit" onclick="edit('edit')">修改</a>
								<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
								<span class="separator"></span>   -->
								 <a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
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
					url="${pageContext.request.contextPath}/rst/user/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
					<div property="columns">
						<div type="checkcolumn" ></div>
						<div field="code" width="160" headerAlign="center" allowSort="true" align="center">编号</div>
						<div field="realName" width="160" headerAlign="center" allowSort="true" align="center">姓名</div>
						<div field="nickName" width="160" headerAlign="center" allowSort="true" align="center">昵称</div>
						<div field="mobile" width="160" headerAlign="center" allowSort="true" align="center">手机</div>
						<div field="wxAccount" width="160" headerAlign="center" allowSort="true" align="center">微信号</div>
						
						<div field="sex" width="160" headerAlign="center" allowSort="true" align="center">性别</div>
						<div field="birthday" dateFormat="yyyy-MM-dd" width="160" headerAlign="center" allowSort="true" align="center">生日</div>
						
						<div field="education" width="160" headerAlign="center" allowSort="true" align="center">学历</div>
						
						<div field="countryName" width="160" headerAlign="center" allowSort="true" align="center">国家</div>
						<div field="provinceName" width="160" headerAlign="center" allowSort="true" align="center">省份</div>
						<div field="cityName" width="160" headerAlign="center" allowSort="true" align="center">城市</div>
						
						<!-- 
							<div field="headImgUrl" width="160" headerAlign="center" allowSort="true" align="center">家庭地址</div> 
						-->
						
						<div field="registrationTime" dateFormat="yyyy-MM-dd" width="160" headerAlign="center" allowSort="true" align="center">注册时间</div>
						<div field="registrationSource" width="160" headerAlign="center" allowSort="true" align="center">注册来源</div>
						<div field="refereeUserCode" width="160" headerAlign="center" allowSort="true" align="center">邀请码</div>
						<div field="seatNumber" width="160" headerAlign="center" allowSort="true" align="center">坐席电话</div>
						<div field="email" width="160" headerAlign="center" allowSort="true" align="center">邮箱</div>
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
			
			//var createTimeBegin = mini.get('createTimeBegin').text;
			//var createTimeEnd = mini.get('createTimeEnd').text;
			
			//data.createTimeBegin = createTimeBegin;
			//data.createTimeEnd = createTimeEnd;
			
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
							'url': "${pageContext.request.contextPath}/user/delete?ids="+ids.join(","),
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
				url : "${pageContext.request.contextPath}/apps/default/admin/uum/user/edit.jsp",
				title : "添加用户",
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
					url : "${pageContext.request.contextPath}/apps/default/admin/uum/user/edit.jsp",
					title : "编辑用户信息",
					width : 500,
					height : 380,
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
			mini.confirm("确定导出记录？", "确定？",
		            function (action) {
		                if (action == "ok") {
		    				var o = form.getData();
		    				
							var url = "${pageContext.request.contextPath}/user/export?exportFileType="+exportFileType+"&exportDataType="+exportDataType;
							location.href=url;
						}
					});
			
		}
		</script>
	</body>
</html>