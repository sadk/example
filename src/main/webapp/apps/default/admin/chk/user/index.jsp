<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户核查</title>
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
				     
				    <!--body-->
				    <div style="padding-left:3px;padding-bottom:5px;">
			            <div id="form1" style="padding:5px;">
							<table>						
								<tr>
									<td>关键字 ：</td>
									<td>
										<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
									</td>
								</tr>
								<tr>
									<td>导入的批次号：</td>
									<td>
										<input id="batchNo" name="batchNo"  style="width:140px" class="mini-textbox"  emptyText="请输入批次号"  onenter="search"/>
									</td>
								</tr>
								<tr>
									<td>用户姓名：</td>
									<td>
										<input id="name" name="name"  style="width:140px" class="mini-textbox"  emptyText="请输入用户名称"  onenter="search"/>
									</td>
								</tr>
								<tr>
									<td>身份证号：</td>
									<td>
										<input id="idcard" name="idcard"  style="width:140px" class="mini-textbox"  emptyText="请输入身份证号码"  onenter="search"/>
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
					<div size="70%" showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						    <div title="用户信息" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="edit('add')">新增</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit('edit')">修改</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-goto" onclick="check()">提交核查</a>
												<a class="mini-button" iconCls="icon-upload" onclick="upload()">导入用户</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
									<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="true"
									url="${pageContext.request.contextPath}/chk/user_crime/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" sortMode="client">
									    <div property="columns">
									        <div type="checkcolumn"></div>
											<div field="id" width="50" headerAlign="center" align="center" >ID</div>
											<div field="batchNo" width="150" headerAlign="center" align="center" >导入批次号</div>
											
											<div field="code"  headerAlign="center" visible="false"   align="center" >用户编码</div>
											<div field="userId"  headerAlign="center" visible="false"   align="center" >系统用户ID</div>
											
											<div field="name"  headerAlign="center" align="center" >用户姓名</div>
											<div field="idcard" width="150" headerAlign="center" align="center" >身份证号</div>
											<div field="sex"   width="40" headerAlign="center" align="center" >性别</div>
											<!-- <div field="photo"  headerAlign="center" align="center" >头像</div> -->
											<div field="policeAddress"  width="200"  headerAlign="center" align="left" >所属公安局</div>
											
											<div field="matchBizDesc"  width="200" headerAlign="center" align="left">身份证匹配说明</div>
											<!-- <div field="resCode"  headerAlign="center" align="center" visible="false">系统响应</div> -->
											<div field="resMsg"  width="200" headerAlign="center" align="left">犯罪记录核查说明 </div>
											
											<!-- <div field="statusCode"  headerAlign="center" align="center" >业务状态码</div> -->
											
											
											<div field="sn"  headerAlign="center" align="center" visible="false">排序号</div>
											<div field="remark" width="250" headerAlign="center" align="left" visible="true">导入日志</div>
											<div field="appCode"  headerAlign="center" align="center" visible="false">系统编码</div>
											<div field="gid"  headerAlign="center" align="center" visible="false">全局码</div>
											
											<div field="createTime"  dateFormat="yyyy-MM-dd HH:mm:ss" width="150" headerAlign="center" allowSort="true" align="center">创建时间</div> 
											<div field="updateTime"  dateFormat="yyyy-MM-dd HH:mm:ss" width="150" headerAlign="center" allowSort="true" align="center">更新时间</div>              
									    </div>
									</div>
						        </div>
						    </div>
						</div>
						<ul id="refreshTabMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
							<li onclick="reloadTab">
								刷新标签页
							</li>
						</ul>
					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">

							<div title="案件明细" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-reload" onclick="refreshDetail()">刷新</a>
												
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="crimeGrid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" multiSelect="true" 
										url="${pageContext.request.contextPath}/chk/crime_detail/list" autoLoad="false" idField="id" sizeList="[20,50,100,150,200]" pageSize="50" 
										showEmptyText="true" emptyText="暂无数据" showPager="false" sortMode="client">
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="crimeType" width="160" headerAlign="center" allowSort="true" align="center">涉案类型</div>
											<div field="count" width="160" headerAlign="center" allowSort="true" align="center">案件数量</div>
											<div field="caseType" width="160" headerAlign="center" allowSort="true" align="center">案件类型</div>
											<div field="caseSource" width="160" headerAlign="center" allowSort="true" align="center">案件来源</div>
											<div field="casePeriod" width="160" headerAlign="center" allowSort="true" align="center">案件时间段</div>
											<div field="caseLevel" width="160" headerAlign="center" allowSort="true" align="center">案件级别</div>
											
											<!-- 
											<div field="sn" width="160" headerAlign="center" allowSort="true" align="center">序号</div>
											<div field="remark" width="160" headerAlign="center" allowSort="true" align="center">备注</div>
											<div field="appCode" width="160" headerAlign="center" allowSort="true" align="center">系统编码</div>
											<div field="gid" width="160" headerAlign="center" allowSort="true" align="center">全局编码</div>
											<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
											<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">更新日期</div>
											 -->
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
			var grid = mini.get("datagrid1"); // 用户列表
			var crimeGrid = mini.get("crimeGrid");// 用户列表
			
			grid.on("rowclick", function(e){
				var record = e.record;
				crimeGrid.load({ucId:record.id}); //用户的犯罪记录
			});
			
			function check() {
				var rows = grid.getSelecteds();
				if (rows.length ==0) {
					mini.alert("请至少选择一个用户");
					return ;
				}
				
				var ids = new Array();
				for (var i=0;i<rows.length;i++) {
					ids.push(rows[i].id);
				}
				
				$.ajax({
					url : "${pageContext.request.contextPath}/chk/user_crime/start_check?ids="+ ids.join(","),
					dataType: 'json', type : 'post',
					success : function(text) {
						mini.alert("提交成功");
						crimeGrid.load({ucId:ids[0]});
					},error : function(data) {
				  		mini.alert(data.responseText);
					}
				});
			}
			
			function upload() {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/chk/user/upload_user_htmlfile.jsp",
					title : "导入数据",
					width : 500,
					height : 400,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : "edit"
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						if(action == 'ok') {
							mini.alert("导入数据发起核查,结果将会有延迟几秒，请手动刷新数据 !");
						}
					}
				});
			}

			function edit(action) {
				var row = grid.getSelected();
				if(typeof(action) == 'undefined') {
					action = "edit";
				}
				if ('edit' == action) {
					if (!row) {
						mini.alert("请至少选择一条记录");
						return ;
					}
				}
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/chk/user/edit.jsp",
					title : "编辑信息",
					width : 490,
					height : 220,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : action
						};
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
				var rows = grid.getSelecteds();
				if (rows.length == 0) {
					mini.alert("请选中至少一条数据");
					return ;
				}
				
				var ids = [];
				for(var i=0;i<rows.length;i++) {
					ids.push(rows[i].id);
				}
				mini.confirm("确定删除？", "确定？",
					function (action) {
						if (action == "ok") {
							$.ajax({
								'url': "${pageContext.request.contextPath}/chk/user_crime/delete?ids="+ids.join(","),
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
			
			function search() {
				var data = form.getData();
				grid.load(data);
			}

	
			function clear() {
				 form.clear();
			}
			
			function refresh() {
				grid.reload();
			}
		</script>
	</body>
</html>