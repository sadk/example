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
										<td>姓名：</td>
										<td>
											<input id="name" name="name"   class="mini-textbox"  emptyText="请输入姓名"  onenter="search"/>
										</td>
									</tr>
									
									<tr>
										<td>帐号：</td>
										<td>
											<input id="loginName" name="loginName"   class="mini-textbox"  emptyText="请输入帐号"  onenter="search"/>
										</td>
									</tr>
									
								
									
									<tr>
										<td>状态 </td>
										<td>
											<input id="status" name="status" value="1" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=user_status" />
										</td>
									</tr>
									
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
									
									<!-- 
									
									<tr>
										<td>QQ：</td>
										<td>
											<input id="numQq" name="numQq"   class="mini-textbox"  emptyText="请输入QQ"  onenter="search"/>
										</td>
									</tr>
									
									
									<tr>
										<td>微信：</td>
										<td>
											<input id="numWx" name="numWx"   class="mini-textbox"  emptyText="请输入微信"  onenter="search"/>
										</td>
									</tr>
									
									
								
									
									<tr>
										<td>办公地点：</td>
										<td>
											<input id="addressOffice" name="addressOffice"   class="mini-textbox"  emptyText="请输入办公地点（多个地址用@@分割）"  onenter="search"/>
										</td>
									</tr>
									
									
									
									<tr>
										<td>家庭地址：</td>
										<td>
											<input id="addressHome" name="addressHome"   class="mini-textbox"  emptyText="请输入家庭地址（多个地址用@@分割）"  onenter="search"/>
										</td>
									</tr>
									 -->
									
									
									<tr>
										<td>性别</td>
										<td>
											<input id="sex" name="sex" value="1" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=sex"  />
										</td>
									</tr>
									
									
									<tr>
										<td>备注：</td>
										<td>
											<input id="remark" name="remark"   class="mini-textbox"  emptyText="请输入"  onenter="search"/>
										</td>
									</tr>
									
									<!-- <tr>
										<td>创建日期(开始)：</td>
										<td>
											<input id="createTimeBegin" name="createTimeBegin" format="yyyy-MM-dd" class="mini-datepicker"  emptyText="请输入创建日期(开始)" />
										</td>
									</tr>
									<tr>
										<td>创建日期(结束)：</td>
										<td>
											<input id="createTimeEnd" name="createTimeEnd" format="yyyy-MM-dd" class="mini-datepicker"  emptyText="请输入创建日期(结束)" />
										</td>
									</tr> -->
									
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
					url="${pageContext.request.contextPath}/user/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
					<div property="columns">
						<div type="checkcolumn" ></div>
						<div field="name" width="160" headerAlign="center" allowSort="true" align="center">姓名</div>
						<div field="loginName" width="160" headerAlign="center" allowSort="true" align="center">帐号</div>
						<!-- <div field="loginPwd" width="160" headerAlign="center" allowSort="true" align="center">密码</div> -->
						<div field="statusDesc" width="160" headerAlign="center" allowSort="true" align="center">状态</div>
						<div field="email" width="160" headerAlign="center" allowSort="true" align="center">邮箱</div>
						<div field="mobile" width="160" headerAlign="center" allowSort="true" align="center">手机</div>
						<div field="tel" width="160" headerAlign="center" allowSort="true" align="center">电话</div>
						<div field="numQq" width="160" headerAlign="center" allowSort="true" align="center">QQ</div>
						<div field="numWx" width="160" headerAlign="center" allowSort="true" align="center">微信</div>
						<div field="birthday" width="160" headerAlign="center" allowSort="true" align="center">生日</div>
						<div field="addressOffice" width="160" headerAlign="center" allowSort="true" align="center">办公地点</div>
						<div field="addressHome" width="160" headerAlign="center" allowSort="true" align="center">家庭地址</div>
						<div field="sexDesc" width="160" headerAlign="center" allowSort="true" align="center">性别</div>
						<div field="sn" width="160" headerAlign="center" allowSort="true" align="center">序号</div>
						<div field="remark" width="160" headerAlign="center" allowSort="true" align="center">备注</div>
						<div field="appCode" width="160" headerAlign="center" allowSort="true" align="center">系统编码</div>
						<div field="gid" width="160" headerAlign="center" allowSort="true" align="center">全局编码</div>
						<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
						<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">更新日期</div>
					
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