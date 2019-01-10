<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户入职信息</title>
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
											<input id="phone" name="phone"   class="mini-textbox"  emptyText="请输入手机号"  onenter="search" style="width:140px"/>
										</td>
									</tr>
									<tr>
										<td>企业名称：</td>
										<td>
											<input id="companyName" name="companyName"   class="mini-textbox"  emptyText="请输入用户编码"  onenter="search" style="width:140px"/>
										</td>
									</tr>
									
									<tr>
										<td>门店名称：</td>
										<td>
											<input id="storeName" name="storeName"   class="mini-textbox"  emptyText="请输入用户编码"  onenter="search" style="width:140px"/>
										</td>
									</tr>

									<tr>
										<td>是否入职：</td>
										<td>
											<input id="entryStatus" name="entryStatus" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_jianli_tech_entry_status_new"  style="width:140px"/>
										</td>
									</tr>
									
									<tr>
										<td>入职日期(开始)：</td>
										<td>
											<input id="entryTimeBegin" name="entryTimeBegin" class="mini-datepicker" style="width:140px" emptyText="请输入" style="width:140px"/>
										</td>
									</tr>
									<tr>
										<td>入职日期(结束)：</td>
										<td>
											<input id="entryTimeEnd" name="entryTimeEnd" class="mini-datepicker" style="width:140px"  emptyText="请输入" style="width:140px"/>
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
								<a class="mini-button" iconCls="icon-node" onclick="entry()">批量入(离)职处理</a>
								<a class="mini-button" iconCls="icon-user" onclick="viewFile()">影像资料审核</a>
								<a class="mini-button" iconCls="icon-expand"  onclick="detail()">详情</a>

								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
								<!-- <a class="mini-button" iconCls="icon-download" onclick="export()">导出</a> -->
							</td>
						</tr>
					</table>
				</div>
				<div class="mini-fit">
					<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="false"
						url="${pageContext.request.contextPath}/rst/user_entry_info/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
						<div property="columns">
							<div type="checkcolumn" ></div>
							<div field="userCode" width="120" headerAlign="center" allowSort="true" align="left">员工编码</div>
							<div field="userName" width="80" headerAlign="center" allowSort="true" align="left">员工姓名</div>
							<div field="phone" width="100" headerAlign="center" allowSort="true" align="center">手机号</div>
							
							<div type="comboboxcolumn" field="sex" width="60" headerAlign="center" align="center" allowSort="true">性别
								<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_sex_required" />
							</div>
							<div field="idCard" width="150" headerAlign="center" allowSort="true" align="center">身份证</div>
							
							<div field="faceDetect" width="100" headerAlign="center" allowSort="true" align="center">认证结果</div>
							
							<div type="comboboxcolumn" field="entryStatus" width="160" headerAlign="center" align="center" allowSort="true">入职状态
								<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_jianli_tech_entry_status_new" />
							</div>
							
							<div field="companyName" width="160" headerAlign="center" allowSort="true" align="left">入职公司</div>
							<div field="entryTime" dateFormat="yyyy-MM-dd" width="120" headerAlign="center" allowSort="true" align="center">入职日期</div>
							<div field="leaveTime" dateFormat="yyyy-MM-dd" width="120" headerAlign="center" allowSort="true" align="center">离职日期</div>
							<div field="storeName" width="160" headerAlign="center" allowSort="true" align="center">所属门店</div>
							
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
		
		function detail( ) {
			var row = grid.getSelected();

			if(!row) {
				mini.alert("请选中一条记录");
				return ;
			}

			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/admin/rst/user_entry_info/work_detail.jsp",
				title : "详情", width : 520, height : 520,
				onload : function() {
					var iframe = this.getIFrameEl();
				 
					iframe.contentWindow.SetData(row);
				},
				ondestroy : function(action) {
					grid.reload();
				}
			});
		}
		
		function viewFile() { //查看影像资料
			var row = grid.getSelected();
			if(!row) {
				mini.alert("请至少选择一个员工");
				return ;
			}
			
			if ((row.entryStatus+"") !='300') { //待人工审核
				mini.alert("非人工审核阶段不允许审核影像资料");	
				return;
			}
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/admin/rst/user_entry_info/view_file.jsp",
				title : "查看",
				width : screen.width-150,
				height : screen.height - 200,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {};
					iframe.contentWindow.SetData(row);
				},
				ondestroy : function(action) {
					if(action == 'save') {
						grid.reload();
					}
				}
			});
		}
		
		function entry() { // 入职处理
			var rows = grid.getSelecteds();
			if(rows.length == 0) {
				mini.alert("请至少选择一个要入职员工");
				return ;
			}
			var userIds = [];
			var entryUserNameList = [];
			for (var i=0;i<rows.length; i++) {
				userIds.push(rows[i].userCode);
				entryUserNameList.push(rows[i].userName);
				
			}
			console.log(entryUserNameList)
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/admin/rst/user_entry_info/entry.jsp",
				title : "入职/离职处理",
				width : 520,
				height : 350,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {};
					data.userIds = userIds.join(",")
					data.entryUserNames = entryUserNameList.join(",");
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
			
			var entryTimeBegin = mini.get('entryTimeBegin').text;
			var entryTimeEnd = mini.get('entryTimeEnd').text;
			
			data.entryTimeBegin = entryTimeBegin;
			data.entryTimeEnd = entryTimeEnd;
			
			grid.load(data);
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