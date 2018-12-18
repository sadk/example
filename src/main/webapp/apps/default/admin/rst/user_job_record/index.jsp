<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>职位投递记录</title>
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
				<div id="form1"  style="padding:4px;margin-left: 4px;">
					<table>			
									<tr>
										<td>姓名：</td>
										<td>
											<input id="userName" name="userName"   class="mini-textbox"  emptyText="请输入姓名"  onenter="search" style="width:140px"/>
										</td>
									</tr>
									<tr>
										<td>手机号：</td>
										<td>
											<input id="userMobile" name="userMobile"   class="mini-textbox"  emptyText="请输入手机号"  onenter="search" style="width:140px"/>
										</td>
									</tr>
									<tr>
										<td>招聘中介：</td>
										<td>
											<input id="interviewContactName" name="interviewContactName"   class="mini-textbox"  emptyText="请输入用户编码"  onenter="search" style="width:140px"/>
										</td>
									</tr>

									
									<tr>
										<td>面试结果：</td>
										<td>
											<input id="statusInterview" name="statusInterview" style="width:140px" class="mini-combobox"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_face_status" />
										</td>
									</tr>

									<tr>
										<td>是否入职：</td>
										<td>
											<input id="statusWorkOn" name="statusWorkOn" style="width:140px" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_jianli_tech_entry_status"  />
										</td>
									</tr>
									
									<tr>
										<td>投递日期(开始)：</td>
										<td>
											<input id="interviewTimeBegin" name="interviewTimeBegin" class="mini-datepicker" style="width:140px" emptyText="请输入" style="width:140px"/>
										</td>
									</tr>
									<tr>
										<td>投递日期(结束)：</td>
										<td>
											<input id="interviewTimeEnd" name="interviewTimeEnd" class="mini-datepicker" style="width:140px"  emptyText="请输入" style="width:140px"/>
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
								   <a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
								    <span class="separator"></span>
								   <a class="mini-button" iconCls="icon-download" onclick="export()">导出</a>
							</td>
						</tr>
					</table>
				</div>
				<div class="mini-fit">
					<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="false"
						url="${pageContext.request.contextPath}/rst/user_job_record/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
						<div property="columns">
							<div type="checkcolumn" ></div>
							<div field="userName" width="80" headerAlign="center" allowSort="true" align="left">求职者姓名</div>
							<div field="userMobile" width="100" headerAlign="center" allowSort="true" align="center">求职者手机号</div>
							<div field="createTime" dateFormat="yyyy-MM-dd" width="100" headerAlign="center" allowSort="true" align="center">投递时间</div>
							<div field="companyName" width="160" headerAlign="center" allowSort="true" align="center">投递公司</div>
							<div field="positionName" width="160" headerAlign="center" allowSort="true" align="left">投递岗位</div>
							<div field="salary" width="160" headerAlign="center" allowSort="true" align="center">工资</div>
							<div field="welfare" width="250" headerAlign="center" allowSort="true" align="left">基本福利</div>
							<div field="interviewPlace" width="250" headerAlign="center" allowSort="true" align="left">面试地点</div>
							<div field="interviewTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">面试时间</div>
							<div field="interviewContactName" width="140" headerAlign="center" allowSort="true" align="center">面试联系人</div>
							<div field="interviewContactMobile" width="140" headerAlign="center" allowSort="true" align="center">面试联系人手机号</div>
							
							<!-- <div field="userWechat" width="160" headerAlign="center" allowSort="true" align="center">求职者微信号</div> -->
							
							<div type="comboboxcolumn" field="status" width="160" headerAlign="center" align="left" allowSort="true">面试状态
								<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_face_status" />
							</div>
							
							<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">更新日期</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		mini.parse();

		var form = new mini.Form("#form1");

		
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
		
		function entry() { // 入职处理
			var rows = grid.getSelecteds();
			if(rows.length == 0) {
				mini.alert("请至少选择一个要入职用户");
				return ;
			}
			var userIds = new Array();
			for (var i=0;i<rows.length; i++) {
				userIds.add(rows[i].id);
			}
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/admin/rst/user/entry.jsp",
				title : "入职/离职处理",
				width : 500,
				height : 180,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {};
					data.userIds = userIds.join(",")
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					if(action == 'save') {
					 	grid.reload();
					}
				}
			});
		}
		
		function search() {
			var data = form.getData();
			
			var interviewTimeBegin = mini.get('interviewTimeBegin').text;
			var interviewTimeEnd = mini.get('interviewTimeEnd').text;
			
			data.interviewTimeBegin = interviewTimeBegin;
			data.interviewTimeEnd = interviewTimeEnd;
			
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
		
	 
		

		function edit(action) {
			var row = grid.getSelected();
			if(typeof(action) == 'undefined') {
				action = "edit";
			}
			
			if (row) {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/rst/user/edit.jsp",
					title : "编辑",
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
		 
		// ----------------
		function refresh() {
			grid.reload();
		}
		function clear() {
			 form.clear();
		}
		</script>
	</body>
</html>