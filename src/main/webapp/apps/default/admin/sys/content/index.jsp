<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>资讯管理</title>
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
			<div size="280" showCollapseButton="true">
				<div style="padding:15px;">
					<table>						
						<tr>
							<td>关键字：</td>
							<td>
								<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="输入关键字搜索" style="width: 150px;" onenter="onKeyEnter" />
							</td>
						</tr>
						<tr>
							<td>标题：</td>
							<td>
								<input id="title" name=""title"" style="width:140px" class="mini-textbox" emptyText="输入标题"  />
							</td>
						</tr>
						<tr>
							<td>资讯类别：</td>
							<td>
								<input id="type" name="type" showNullItem="true"  nullItemText="请选择..." emptyText="请选择" class="mini-combobox" style="width:140px" data='[{id:"HG",text:"活动回顾"},{id:"NK",text:"知认库"},{id:"HN",text:"热点新闻"},{id:"OTHER",text:"其它"}]' />
							</td>
						</tr>
						<tr>
							<td>是否隐藏：</td>
							<td>
								<input id="showFlag" name="showFlag" class="mini-combobox" style="width:140px" showNullItem="true" nullItemText="请选择..." emptyText="请选择"   data='[{id:"HD",text:"显示"},{id:"DIS",text:"隐藏"}]'	/>
							</td>
						</tr>
						<tr>
							<td>是否推荐：</td>
							<td>
								<input class="mini-combobox" showNullItem="true"  nullItemText="请选择..." emptyText="请选择" style="width:140px" id="prdType" data='[{id:"REC",text:"推荐"},{id:"NREC",text:"不推荐"}]' />
							</td>
						</tr>
						<!-- 
						<tr>
							<td>创建日期(结束)：</td>
							<td>
								<input class="mini-datepicker" style="width:140px" id="endDate" emptyText="请输入"/>
							</td>
						</tr>
						 -->
					</table>
					<div style="text-align:center;padding:10px;">
						<a class="mini-button" onclick="search()" iconCls="icon-search" style="width:60px;margin-right:20px;">查询</a>
					</div>
				</div>
			</div>
			<div showCollapseButton="true">
				<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-add" onclick="add()">添加</a>
								<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
								<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
							</td>
						</tr>
					</table>
				</div>
				<div class="mini-fit">
					<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
					url="${pageContext.request.contextPath}/action/admin/v3/NewsController/query?sortOrder=desc&sortField=id"  idField="id" 
				  	   sizeList="[20,50,100,150,200]" pageSize="50" >
					<div property="columns">
						<div type="checkcolumn" ></div>
						<div field="id" width="40" headerAlign="center" allowSort="true" align="center">ID</div>
						<div field="title" width="280" headerAlign="center" allowSort="true" align="left" >标题</div>
						<div field="typeDesc" width="80" headerAlign="center" allowSort="true" align="center">资讯类型</div>
						<!-- 
						<div field="content" width="140" headerAlign="center" allowSort="true" align="center">内容</div>
						 -->
						<div field="picUrl" width="350" headerAlign="center" allowSort="true" align="left">资讯图路径</div>
						<div field="picTile" width="120" headerAlign="center" allowSort="true" align="left">资讯图标题</div>
						<div field="author" width="80" headerAlign="center" allowSort="true" align="left">作者</div>
						<div field="suggest" width="280" headerAlign="center" allowSort="true" align="left">资讯摘要</div>
						<div field="actDateDesc" width="80" headerAlign="center" allowSort="true" align="center">资讯时间</div>
						<div field="actAddress" width="120" headerAlign="center" allowSort="true" align="left">资讯地点</div>
						<div field="viewCount" width="80" headerAlign="center" allowSort="true" align="center">阅读量</div>
						
						<div field="showFlagDesc" width="80" headerAlign="center" allowSort="true" align="center" >是否显示</div>
						<div field="recFlagDesc" width="120" headerAlign="center" allowSort="true" align="center" >是否推荐</div>
						<div field="createDateDesc" width="120" headerAlign="center" allowSort="true" align="center" >创建日期</div>
						<div field="order" width="80" headerAlign="center" allowSort="true" align="center" >排序号</div>
					</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		mini.parse();


		var grid = mini.get("datagrid1");
		grid.load();
		grid.on("drawcell", function(e){
			var record = e.record;
			var field = e.field;
			var value = e.value;
			var row = e.row;
			if(row && field == "title" && row.id){
				e.cellHtml = '<a href="javascript:void(0)" onclick="view('+row.id+')">' + row.title + '</a>';
			}
			if(row && field == "picUrl" && row.picUrl){ 
				e.cellHtml = '<a href="javascript:void(0)" onclick="viewImage('+row.picUrl+')">' + row.picUrl + '</a>';
			}
		});
		
		function search() {
			var key = mini.get('key').value;
			var status = mini.get('status').value;
			var orderSource = mini.get('orderSource').value;
			var prdType = mini.get('prdType').value;
			var prdId = mini.get('prdId').value;
			var beginDate = mini.get('beginDate').text;
			var endDate = mini.get('endDate').text;
			grid.load({
				key : key,
				status : status,
				orderSource : orderSource,
				prdType : prdType,
				begin : beginDate,
				end : endDate,
				prdId : prdId,
				isConsole: true
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
							'url': "${pageContext.request.contextPath}/action/admin/v3/NewsController/deleteByIds?ids="+ids.join(","),
							type: 'post',
							dataType:'JSON',
							cache: false,
							async:false,
							error : function(xhr){
								console.error(xhr);
							},
							success: function (json) {
								if(json.status==1){
									grid.reload();
									mini.alert("删除成功");
								}else{
									mini.alert(json.msg);
								}
							}
						});
					}
				}
			);
		}
		
		function add() {
			mini.open({
				url : "${pageContext.request.contextPath}/admin/v3/news/edit_news.jsp",
				title : "添加资讯",
				width : 880,
				height : 760,
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

		function edit() {
			var row = grid.getSelected();
			if (row) {
				mini.open({
					url : "${pageContext.request.contextPath}/admin/v3/news/edit_news.jsp",
					title : "添加资讯",
					width : 880,
					height : 760,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : "edit",
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