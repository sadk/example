<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>报表定义管理</title>
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
			<div size="220" showCollapseButton="true">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <span style="padding-left:5px;">报表系统：</span>
		            <!--  <input class="mini-textbox" width="120"/> -->
		            
		            <input showNullItem="false" width="140" class="mini-combobox" url="${pageContext.request.contextPath}/application/all" textField="name" valueField="id" />
		            
		           <!--  <a class="mini-button" iconCls="icon-search" plain="true">查找</a>      -->             
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/report/category/all" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" expandOnLoad="false">        
		            </ul>
		        </div>
			</div>
			<div showCollapseButton="true">
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						
						
						
						    <div title="报表列表" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="add()">新增</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<a class="mini-button" iconCls="icon-save" onclick="save()">保存</a>
												
												<input class="mini-combobox" style="width: 80px;" onvaluechanged="onVersionValueChanged" id="versionCmb" showNullItem="false" nullItemText="请选择..." emptyText="显示版本" textField="name" valueField="value" data="[{name:'最新版本',value:1},{name:'所有版本',value:2}]" />
												
												
												
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-goto" onclick="copyDefinition()">报表复制</a>
												<!-- 
												<a class="mini-button" iconCls="icon-upload"   onclick="importDefinition()">导入</a>
												<a class="mini-button" iconCls="icon-download" onclick="exportDefinition()">导出</a>
												<span class="separator"></span>  
												-->
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:100px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search()">查询</a>
						                    </td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
						            <div id="definitionGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
						            	 idField="id" allowResize="false" multiSelect="true"  sizeList="[5,10,20,50]" 
						            	 pageSize="20" showEmptyText="true" emptyText="暂无查询信息" sortMode="client"
						            	  allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" 
						              	 url="${pageContext.request.contextPath}/report/definition/page" >
						                <div property="columns">
											<div type="checkcolumn" ></div>
											<div field="id" width="60" headerAlign="center" allowSort="true" align="center">ID</div>
											<div field="name" width="300" headerAlign="center" allowSort="true" align="left">报表全称</div>
											
											<div field="shortName" width="100" headerAlign="center" allowSort="true" align="left">报表简称
												<input property="editor" class="mini-textbox" style="width:100%;" />
											</div>
											
											<div type="comboboxcolumn" field="status" width="80" headerAlign="center" align="center" allowSort="true">移端启用
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" />
											</div>
											
											<div field="code" width="160" headerAlign="center" allowSort="true" align="left">编码</div>
											
											<div field="url" width="250" headerAlign="center" allowSort="true" align="left">地址
												<input property="editor" class="mini-textbox" style="width:100%;" />
											</div>
											
											<div type="comboboxcolumn" field="type" width="80" headerAlign="center" align="center" allowSort="true">生成类型
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rpt_general_type" />
											</div>
											
											<div type="comboboxcolumn" field="dbType" width="80" headerAlign="center" align="center" allowSort="true">数据库类型
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rpt_db_type" />
											</div>
											
											<div field="version" width="150" headerAlign="center" allowSort="true" align="center">版本号</div>
											
											<div field="remark" width="160" headerAlign="center" allowSort="true" align="left">备注</div>
 
					 						<div field="createTime"  dateFormat="yyyy-MM-dd HH:mm:ss" width="150" headerAlign="center" allowSort="true" align="center">创建时间</div> 
											
											<div field="updateTime"  dateFormat="yyyy-MM-dd HH:mm:ss" width="150" headerAlign="center" allowSort="true" align="center">更新时间</div> 
										</div>
						            </div>
						        </div>
						    </div>



						</div>

					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">

							<div title="字段设置" refreshOnClick="true" name="tabUserReses">
								 
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-redo" onclick="importColumn()">导入字段</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-edit" onclick="editColumn()">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeColumn()">删除</a>
												<a class="mini-button" iconCls="icon-save" onclick="saveColumn()">保存</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-reload" onclick="refreshColumn()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								 
								<div class="mini-fit">
									<div id="columnGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据"  allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true"
										sizeList="[5,10,20,50]" pageSize="20" allowAlternating="false" sortMode="client" showPager="true"
										 url="${pageContext.request.contextPath}/report/column/page" >
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="reportName" width="150" headerAlign="center" allowSort="true">报表名称</div>
											
											<div field="code" width="100" headerAlign="center" allowSort="true">DB字段
												<input property="editor" class="mini-textbox" style="width:100%;" minWidth="100" />
											</div>
											<div field="dbType" width="100" headerAlign="center" allowSort="true">DB字段类型</div>
											
											<div field="propertyName" width="100" headerAlign="center" allowSort="true">JAVA字段
												<input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
											</div>
											<div type="comboboxcolumn" field="javaType" width="130" headerAlign="center" allowSort="true">JAVA字段类型
												<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=java_type" />
											</div>
											<div field="name" width="100" headerAlign="center" allowSort="true">字段中文
												<input property="editor" class="mini-textbox" style="width:100%;" minWidth="100" />
											</div>
											<div type="comboboxcolumn" field="primaryKey" width="60" headerAlign="center" align="center" allowSort="true">主键
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=yes_or_no" />
											</div>
											<div field="comment" width="100" headerAlign="center" allowSort="true">DB字段注释
												<input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
											</div>
											
											
											<div type="comboboxcolumn" field="searchType" width="120" headerAlign="center" align="center" allowSort="true">是否作为查询条件
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=yes_or_no" />
											</div>
											
											
											<div type="comboboxcolumn" field="columnCodegenType" width="140" headerAlign="center" align="left" allowSort="true">字段控件
												<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=column_codegen_type" />
											</div>
											<div field="columnCodegenFormat" width="90" headerAlign="center" align="left" allowSort="true">字段显示格式
												<input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
											</div>
											
											<!--  <div field="fkCol" width="80" headerAlign="center" allowSort="true">外键</div> -->
											<div type="comboboxcolumn" field="oroColumnType" width="160" headerAlign="center" align="left" allowSort="true">ORO字段映射类型
												<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=oro_column_type" />
											</div>
											
											<div field="columnCodegenGroupCode" width="100" headerAlign="center" align="left" allowSort="true">字段的分组编码
												<input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
											</div>
											
											<!-- 
											<div type="comboboxcolumn" field="largeFiled" width="100" headerAlign="center" allowSort="true">是否大字段
												<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=yes_or_no" />
											</div>
											 -->
											 
											<div field="optLog" width="150" headerAlign="center" allowSort="true">操作日志</div>
											<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        				<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>
										</di
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
			var grid = mini.get("definitionGrid"); 
			var columnGrid = mini.get("columnGrid");
			
			
			var versionCmb = mini.get("versionCmb");
			grid.load();
			
	        tree.on("nodeselect", function (e) {
	        	grid.load({ categoryId: e.node.id });
	        });
	        
			grid.on("rowclick", function(e){
				var record = e.record;
				/* 
				column = e.column
		        field = e.field 
		        */
		        columnGrid.load({definitionId:record.id});
				 
			});			
 
			function add() {
				var node = tree.getSelectedNode();
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/report/definition/edit.jsp",
					title : "报表信息",
					width : 550,
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
			
			function edit() {
				var row = grid.getSelected();
				if(row) {
					mini.open({
						url : "${pageContext.request.contextPath}/apps/default/admin/report/definition/edit.jsp",
						title : "报表信息",
						width : 550,
						height : 600,
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
				}else {
					mini.alert("请选择一条记录");
				}
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
								'url': "${pageContext.request.contextPath}/report/definition/delete?ids="+ids.join(","),
								type: 'post',
								dataType:'JSON',
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
			
			
			
			function importColumn() {
				var row = grid.getSelected();
				if (!row) {
					mini.alert("请选择一个报表");
					return ;
				}
				
				$.ajax({
					'url': "${pageContext.request.contextPath}/report/definition/import_column?id="+row.id,
					type: 'post', dataType:'JSON',
					success: function (json) {
						mini.alert("导入成功");
						columnGrid.load({definitionId: row.id});
					},
					error : function(data) {
				  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
				  		mini.alert(data.responseText);
					}
				});
			}
			
			function removeColumn() {
				var row = columnGrid.getSelected();
				if (!row) {
					mini.alert("请选择一个节点设置");
					return ;
				}
		        mini.confirm("确定删除字段【"+row.name+"】？", "确定？",
	                function (action) {
	                    if (action == "ok") {
	        	            $.ajax({
	        	                url: "${pageContext.request.contextPath}/report/column/delete",
	        	                data: { ids: row.id },
	        	                type: "post",
	        	                success: function (text) {
	        	                	columnGrid.reload();
	        	                },
	        	                error: function (jqXHR, textStatus, errorThrown) {
	        	                    alert(jqXHR.responseText);
	        	                }
	        	            });
	                    }
	        		})
			}
			
			function saveColumn () {
				var data = columnGrid.getChanges();
	            var json = mini.encode(data);
	            columnGrid.loading("保存中，请稍后......");
	            $.ajax({
	                url: "${pageContext.request.contextPath}/report/column/save_or_update_json",
	                data: { data: json },
	                type: "post",
	                success: function (text) {
	                	columnGrid.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
			}
			
			function editColumn() {
				var row = columnGrid.getSelected();
				if(!row) {
					mini.alert("请选择一个字段");
					return ;
				}
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/report/column/edit.jsp",
					title : "编辑字段",
					width : 540,
					height : 620,
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
			}
			/*
			function onVersionValueChanged(e) {
				var v = versionCmb.getValue();
				var node = tree.getSelectedNode();
				definitionKey ="";
				if(typeof(node) !='undifiend') {
					category = node.code;
				}
				console.log(node)
				if(v==1) {
					grid.load({isDisplayNewest : true,category:category });
				} else {
					grid.load({category : category });
				}
			}
			*/
			function refreshColumn(){
				 columnGrid.reload();
			}
			
			function search() {
				var data = {};
		 
				var key2 = mini.get("key2").value;
				if( (data.key==null || data.key=="") && (key2!=null && key2!="")){
					data.keyWord = key2;
				}
				
				grid.load(data);
			}
		 
			
			function save() {
			    var data = grid.getChanges();
	            var json = mini.encode(data);
	            //console.log(json);
	            loading();
	           
	            $.ajax({
	                url: "${pageContext.request.contextPath}/report/definition/save_or_update_json_short_name",
	                data: { data: json },
	                type: "post",
	                success: function (text) {
	                	grid.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
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