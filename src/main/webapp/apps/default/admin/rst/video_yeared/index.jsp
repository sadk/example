<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>年会视频管理</title>
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
			<div size="250" showCollapseButton="true">
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
											<input id="id" name="id"   class="mini-spinner"  emptyText="请输入编码"  onenter="search" value="" />
										</td>
									</tr>
									<tr>
										<td>上传用户：</td>
										<td>
											<input id="name" name="name"   class="mini-textbox"  emptyText="请输入上传用户"  onenter="search"/>
										</td>
									</tr>
									
									<tr>
										<td>上传部门：</td>
										<td>
											<input id="departmentName" name="departmentName"   class="mini-textbox"  emptyText="请输入上传部门"  onenter="search"/>
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
						    <div title="年会视频列表" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-upload" onclick="edit('add')">上传</a>
												<!-- <a class="mini-button" iconCls="icon-edit" onclick="edit('edit')">编辑</a> -->
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
											</td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
									<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="true"
										url="${pageContext.request.contextPath}/rst/video_year/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
									    <div property="columns">
									        <div type="checkcolumn"></div>
									        <div field="id" width="80" headerAlign="center">编码</div>
									        <div field="name" width="80" headerAlign="center">上传用户</div>
									        <div field="departmentName" width="100" headerAlign="center">上传部门</div>
									        <div field="coverUrl" width="200" headerAlign="center" align="left" >视频封面</div>
									        <div field="videoUrl" width="200" headerAlign="center" align="left" >视频地址</div>
									    </div>
									</div>
						        </div>
						    </div>
						</div>
					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							
							<div title="年会节目单投票" refreshOnClick="true" name="tabReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<!-- 
												<a class="mini-button" iconCls="icon-add" onclick="editAddress('add')">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editAddress('edit')">修改</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeAddress()">删除</a>
												<span class="separator"></span>  
												 -->
												<a class="mini-button" iconCls="icon-reload" onclick="refreshVote()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="datagrid2" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="false"
										url="${pageContext.request.contextPath}/rst/vedio_vote_yeared/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
										<div property="columns">
											<div type="checkcolumn"></div>
											<!-- <div field="id" width="60" headerAlign="center" align="center">ID</div> -->
									        <div field="userId" width="100" headerAlign="center">用户编码</div>
									        <div field="userName" width="150" headerAlign="center">投票用户名</div>
											<div type="comboboxcolumn" field="status" width="50" headerAlign="center" align="center" allowSort="true">投票状态
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_vote_year_status" />
											</div>
									        <div field="voteTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="180" headerAlign="center" allowSort="true" align="center">投票时间</div>
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
			
			var grid = mini.get("datagrid1");  
			var voteGrid = mini.get("datagrid2");// 投票列表
			 

			
			function openUrl(url) {
				var url= "${pageContext.request.contextPath}/apps/default/admin/rst/company/img_show.jsp?url="+url ;
	        	window.open(url,"_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width="+screen.width+", height="+screen.height+"");
			}
			
			function viewPicture() {
				var row = pictureGrid.getSelected();
				if (!row) {
					mini.alert("请选择一个企业图片");
					return ;
				}
				openUrl(row.url);
			}
			
			function  refreshPicture() {
				pictureGrid.reload();
			}
			
		 
			 
			
			function search() {
				var data = form.getData();
				if((data.id+"") == "0") {
					data.id = "";
				}
				
				grid.load(data);
			}
			
			grid.on("rowclick", function(e){
				var record = e.record;
				voteGrid.load({videoId:record.id}); 
				 
			});
	
			grid.on("drawcell", function(e){
				var record = e.record;
				var field = e.field;
				var value = e.value;
				var row = e.row;
				if(typeof(row.coverUrl) != 'undefined' && row && field == "coverUrl"){
					e.cellHtml = "<a href='javascript:openUrl(\""+row.coverUrl+"\")'>"+row.coverUrl +"</a>" ;
				}
				console.log(field);
				
				if(typeof(row.videoUrl) != 'undefined' && row && field == "videoUrl"){
					e.cellHtml = "<a href='javascript:openVideo(\""+row.videoUrl+"\")'>"+row.videoUrl +"</a>" ;
				}
			})
			
			function openVideo(videoUrl) {
				window.open(videoUrl,"_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width="+screen.width+", height="+screen.height+"");
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
					url : "${pageContext.request.contextPath}/apps/default/admin/rst/video_yeared/upload_video_htmlfile.jsp",
					title : "编辑信息",
					width : 520,
					height : 500,
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
								'url': "${pageContext.request.contextPath}/rst/video_year/delete?ids="+ids.join(","),
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