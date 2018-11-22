<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>新闻评论管理</title>
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
			<div size="240" showCollapseButton="true">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		           <span style="padding-left:5px;">租户：</span>
		           <!-- 
		           <input showNullItem="false" width="140" class="mini-combobox" url="${pageContext.request.contextPath}/application/all" textField="name" valueField="id" />
					-->
					 <input id="tenantCode" name="tenantCode" width="180" class="mini-buttonedit" onbuttonclick="onButtonEdit" />  
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/apps/default/resume/company/json.txt" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" >        
		            </ul>
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
									url="${pageContext.request.contextPath}/news/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
									<div property="columns">
										<div type="checkcolumn" ></div>
										<div field="name" width="100" headerAlign="center" allowSort="true" align="left" >名称</div>
										<div field="code" width="80" headerAlign="center" allowSort="true" align="left">编码</div>
										<div field="title" width="160" headerAlign="center" allowSort="true" align="left" >标题</div>
										<div field="summary" width="160" headerAlign="center" allowSort="true" align="left" >摘要</div>
										<div field="tags" width="160" headerAlign="center" allowSort="true" align="left" >标签</div>
										<div field="author" width="160" headerAlign="center" allowSort="true" align="left" >作者</div>
										
										<div field="publishDate" width="80" dateFormat="yyyy-MM-dd" headerAlign="center" align="center">发布日期</div>
										<div field="enableDesc" width="60" headerAlign="center" allowSort="true" align="center">是否启用</div>
										<div field="statusAuthDesc" width="60" headerAlign="center" allowSort="true" align="center">审核状态</div>
										
										<div field="topDays" width="80" headerAlign="center" allowSort="true" align="left">置顶天数</div>
										<div field="cntReplay" width="80" headerAlign="center" allowSort="true" align="left">评论数</div>
										<div field="cntView" width="80" headerAlign="center" allowSort="true" align="left">浏览数</div>
										<div field="sn" width="60" headerAlign="center" allowSort="true" align="right">序号</div>
										<div field="appCode" width="80" headerAlign="center" allowSort="true" align="left">应用编码</div>
										<div field="remark" width="160" headerAlign="center" allowSort="true" align="left">备注</div>
										
										
										<div field="gid" width="160" headerAlign="center" allowSort="true" align="left">全局编码</div>
										<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center">创建日期</div>     
										<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center">更新日期</div>     
									</div>
									</div>
						        </div>
						    </div>
						</div>
					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							

							<div title="职位列表 " refreshOnClick="true" name="tabUserReses">
								 
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="editComment('add')">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeComment()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editComment('edit')">编辑</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key3" name="key3" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="searchComment()">查询</a>
						                    </td>
										</tr>
									</table>
								</div>
								 
								<div class="mini-fit">
									<div id="commentGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
						                idField="id" allowResize="false" multiSelect="true"  sizeList="[5,10,20,50]" 
						                pageSize="20" showEmptyText="true" emptyText="暂无查询的记录" sortMode="client" 
										url="${pageContext.request.contextPath}/news_comment/page" >
										<div property="columns">
									        <div type="checkcolumn" ></div>
									        
									        <div field="id" width="40" headerAlign="center">ID</div>
									        <div field="commentUserName" width="80" headerAlign="center" align="left">评论用户</div>
									        <div field="content" width="400" headerAlign="center" align="left">评论内容</div>
									        
									        <div field="appCode" width="80" headerAlign="center">租户码</div>
											<div field="gid" width="180" headerAlign="center" allowSort="true" align="left">全局编码</div>
											<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center">创建日期</div>     
											<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center">更新日期</div>    
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
		
			var tree = mini.get("tree1");
			var grid = mini.get("datagrid1");
			var commentGrid = mini.get("commentGrid");
			
			
			tree.on("nodeselect", function (e) {
	        	grid.load({ deployCategory: e.node.code });
	        });
	        
			
			grid.on("rowclick", function(e){
				var record = e.record;
				commentGrid.load({newsId:record.id});
			});
			
			
			grid.load();

	        function onButtonEdit(e) {
	        	var node = tree.getSelectedNode();
	        	console.log(node)
	        	if(node) {
	        		
	        		return ;
	        	}
	            var btnEdit = this;
	            mini.open({
	                url: "${pageContext.request.contextPath}/apps/default/admin/sys/tenant/selector_tenant.jsp",
	                title: "选择租户",
	                width: 650,
	                height: 380,
	                ondestroy: function (action) {
	                    
	                    if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var data = iframe.contentWindow.GetData();
	                        data = mini.clone(data);    //必须
	                        if (data) {
	                            btnEdit.setValue(data.code);
	                            btnEdit.setText(data.name);
	                            
	                           
	                            tree.load({tenantCode:data.code})
	                        } else {
	                        	btnEdit.setValue(null);
	                            btnEdit.setText(null);
	                        	tree.load()
	                        }
	                    }

	                }
	            });
	        }
			
			
			function removeComment() {
				var rows = commentGrid.getSelecteds();
				if (rows.length == 0) {
					mini.alert("请选择要删除的评论");
					return ;
				}
				var ids = new Array();
				for(var i=0;i<rows.length;i++) {
					ids.push(rows[i].id);
				}
		        mini.confirm("确定删除记录？", "确定？",
	                function (action) {
	                    if (action == "ok") {
	        				$.ajax({
	        					url : "${pageContext.request.contextPath}/news_comment/delete?ids=" + ids.join(","),
	        					dataType: 'json',
	        					cache : false,
	        					success : function(text) {
	        						commentGrid.reload();
	        					}
	        				});
	                    } 
	                }
		        );
			}
			
			function editComment(action) {
				var row = null; // 新闻行
				var r = null;  // 评论行
				
				if(action == 'add') {
					row = grid.getSelected();
					if (!row) {
						mini.alert("请选中一条资讯记录");
						return ;
					}
				}
				
				if(action == 'edit') {
					r = commentGrid.getSelected();
					if (!r) {
						mini.alert("请选择一个评论");
						return ;
					}
				}
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/cms/comment/edit.jsp",
					title : "添加评论",
					width : 350,
					height : 250,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : action
						};
						if(action == 'add') {
							data.newsId = row.id;
						}
						if(action == 'edit') {
							data.id = r.id;
						}
						console.log(data)
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						if(action == 'save') {
							commentGrid.reload();
						}
					}
				});
			}
			
			function remove() {
				var row = grid.getSelecteds();
				if (row.length==0) {
					mini.alert("请选中一条以上的记录");
					return ;
				}
				
				var ids = [];
				for(var i=0;i<row.length;i++) {
					ids.push(row[i].id);
				}
				mini.confirm("确定删除？", "确定？",
					function (action) {
						if (action == "ok") {
							$.ajax({
								'url': "${pageContext.request.contextPath}/news/delete?ids="+ids.join(","),
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
					url : "${pageContext.request.contextPath}/apps/default/admin/cms/news/edit.jsp",
					title : "添加资讯",
					width : 790,
					height : 600,
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
						url : "${pageContext.request.contextPath}/apps/default/admin/cms/news/edit.jsp",
						title : "编辑资讯",
						width : 790,
						height : 600,
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