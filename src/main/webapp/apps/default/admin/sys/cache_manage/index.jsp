<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>缓存管理</title>
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
		            <span style="padding-left:5px;">名称：</span>
		            <input class="mini-textbox" width="120"/>
		            <a class="mini-button" iconCls="icon-search" plain="true">查找</a>                  
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/cache_category/all" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" expandOnLoad="true">        
		            </ul>
		        </div>
		    </div>
			<div showCollapseButton="true">
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						    <div title="缓存定义" refreshOnClick="true">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="edit('add')">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit('edit')">编辑</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-reload" onclick="refrechCache()">刷新缓存</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key" name="key" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search()">查询</a>
						                    </td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
									<div id="cacheManageGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" 
										multiSelect="true" allowResize="false" sizeList="[20,50,100,150,200]" pageSize="20"  
					 					url="${pageContext.request.contextPath}/cache_manage/page"> 
									    <div property="columns">
									        <div type="checkcolumn"></div>
									        <div field="id" width="60" headerAlign="center">ID</div>
									        <div field="name" width="80" headerAlign="center">名称</div>
									        <div field="code" width="80" headerAlign="center">编码</div>
									        <div field="categoryName" width="80" headerAlign="center">分类</div>
									        <div field="url" width="240" headerAlign="center">缓存URL</div>
									        <div field="remark" width="80" headerAlign="center">备注</div>
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
									    </div>
									</div>
						        </div>
						    </div>
						</div>
						
					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							
							<div title="缓存参数" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="editParam('add')">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editParam('edit')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeParam()">删除</a>
												<span class="separator"></span> 
												<a class="mini-button" iconCls="icon-save" onclick="saveParam()">保存</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="cacheParamGrid" class="mini-datagrid" style="width:100%;height:100%;" sortMode="client"
										url="${pageContext.request.contextPath}/cache_url_param/page"   idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据" sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" 
										 allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" >
										<div property="columns">
									        <div type="checkcolumn" ></div>
									        <div field="paramName" width="80" headerAlign="center">参数名称
									        	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									        </div>
									        <div field="paramCode" width="80" headerAlign="center">参数编码
									        	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									        </div>
									        
									        <!-- 
									        <div type="comboboxcolumn" field="paramType" width="80" headerAlign="center" allowSort="true" align="left">参数类型
												<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=user_data_config_param" />
											</div>
											 -->
											 
									        <div field="paramValue" width="80" headerAlign="center">参数值
									        	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									        </div>
									        <!-- 
									         <div field="paramValueExpress" width="120" headerAlign="center">审批对象扩展属性表达式
									         	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									         </div>
									          -->
									        <div field="remark" width="80" align="right" headerAlign="center">备注
									        	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									        </div>
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
	        
	        var grid = mini.get("cacheManageGrid");
	        var cacheParamGrid = mini.get("cacheParamGrid");
	        
	        tree.on("nodeselect", function (e) {
	        	grid.load({ categoryId: e.node.id });
	        });
			
	        grid.load();
 
	        grid.on("rowclick", function(e){
				var record = e.record;
				cacheParamGrid.load({cacheId:record.id}); 
			});

	        function refrechCache() {
	        	var rows = grid.getSelecteds();
	        	if(rows.length == 0){
	        		mini.alert("请选择一个缓存定义");
	        		return ;
	        	}
	        	var ids = new Array();
	        	for(var i=0;i<rows.length;i++){
	        		ids.push(rows[i].id);
	        	}
	        	
	            $.ajax({
	                url: "${pageContext.request.contextPath}/cache_manage/execute_refresh",
	                data: { ids: ids.join(",") },
	                type: "post",
	                success: function (text) {
	                	mini.alert(text.message)
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
	        }
	        
	        function saveParam(){
			    var data = cacheParamGrid.getChanges();
	            var json = mini.encode(data);
	            $.ajax({
	                url: "${pageContext.request.contextPath}/cache_url_param/save_or_update_json",
	                data: { data: json },
	                type: "post",
	                success: function (text) {
	                	cacheParamGrid.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
	        }
	        
			function editParam(action) {
				var data = {action : action};
				
				if(action == 'add'){
					var row = grid.getSelected();
					if(!row){
						mini.alert("请至少选择一个缓存定义");
						return ;
					}
					data.cacheId = row.id;
				}
				
				if(action == 'edit') {
					var row = cacheParamGrid.getSelected();
					if(!row){
						mini.alert("请至少选择一个参数定义");
						return ;
					}
					data.id = row.id;
				}
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/sys/cache_manage/edit_param.jsp",
					title : "添加参数",
					width : 500,
					height : 300,
					onload : function() {
						var iframe = this.getIFrameEl();
						console.log(data)
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						cacheParamGrid.reload();
					}
				});
			}
			
	        function removeParam() {
	        	var rows = cacheParamGrid.getSelecteds();
	        	if(rows.length ==0) {
	        		mini.alert("请至少选择一个参数!");
	        		return ;
	        	}
	        	
	        	mini.confirm("是否删除选定的缓存参数?", "确定？",
						function (action) {
							if (action == "ok") {
					        	var idArray = new Array();
					        	for(var i=0;i<rows.length;i++){
					        		idArray.push(rows[i].id);
					        	}
					        	  
								var data = {};
								data.ids = idArray.join(",");
								
								$.ajax({
									url : "${pageContext.request.contextPath}/cache_url_param/delete",
									type: 'post', dataType:'JSON', cache: false, async:false,
									data: data,
									success: function (json) {
										mini.alert("删除成功!");
										cacheParamGrid.reload();
									},
									error : function(data) {
								  		mini.alert(data.responseText);
									}
								});
							}
	        	});
	        }
	        
			function edit(action) {
				var node = null;
				
				if(action == 'add') {
					node = tree.getSelected();
					/*
					if(!node){
						mini.alert("请选择一个缓存分类!");
						return ;
					}*/
				}
				
				var row = grid.getSelected();
				if(action == 'edit') {
					if(!row){
						mini.alert("请选择一个缓存定义");
						return ;
					}
				}
				var url = "${pageContext.request.contextPath}/apps/default/admin/sys/cache_manage/edit.jsp";
				var data = {};
				if(action == 'add') {
					data.action = "add";
					if(node!=null) {
						data.categoryId = node.id;
						data.categoryName = node.name;
					}
				}
				
				if(action == 'edit') {
					data.action = "edit";
					data.id=row.id;
					if(node!=null) {
						data.categoryId = node.id;
						data.categoryName = node.name;
						data.id = row.id
					}
				}
				
				mini.open({
					url : url,
					title : "编辑缓存管理定义",
					width : 490,
					height : 250,
					onload : function() {
						var iframe = this.getIFrameEl();
						
						//alert(data.pid+" "+data.pName+" "+data.idNotIn);
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						grid.reload();
					}
				});
			}
			
			function remove() {
				var data = {}
				var rows = grid.getSelecteds();
				
				if(rows.length == 0) {
					mini.alert("请选择一个缓存定义!");
					return ;
				}
				
				var ids = new Array();
				for(var i=0;i<rows.length;i++) {
					ids.push(rows[i].id);
				}
				data.ids = ids.join(",");
				$.ajax({
					url : "${pageContext.request.contextPath}/cache_manage/delete",
					type: 'post', dataType:'JSON', cache: false, async:false,
					data: data,
					success: function (json) {
						mini.alert("删除成功!");
						grid.reload();
					},
					error : function(data) {
				  		mini.alert(data.responseText);
					}
				});
			}
			
	 
			
		    function search() {
		    	var key = mini.get("key")
		    	grid.load({key:key.getValue()});
		    }
		       
			
		    function loading() {
		        mini.mask({
		            el: document.body,
		            cls: 'mini-mask-loading',
		            html: '加载中...'
		        });
		        setTimeout(function () {
		            mini.unmask(document.body);
		        }, 500);
		    }
			
		   

		</script>
	</body>
</html>