<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户数据适配管理</title>
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
			<div size="270" showCollapseButton="true">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <span style="padding-left:5px;">系统名称：</span>
		            <input class="mini-textbox" width="120"/>
		            <a class="mini-button" iconCls="icon-search" plain="true">查找</a>                  
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/application/all_tree" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" expandOnLoad="true"  >        
		            </ul>
		        </div>
			</div>
			<div showCollapseButton="true">
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						
						    <div title="映射接口配置" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-undo" onclick="copyInto()">copy导入到其它系统</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-add" onclick="configEdit('add')">新增</a>
												<a class="mini-button" iconCls="icon-edit" onclick="configEdit('edit')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="configRemove()">删除</a>
												<a class="mini-button" iconCls="icon-save" onclick="saveDataConfigGrid()">保存</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-node" onclick="viewData()">数据预览</a>
												<a class="mini-button" iconCls="icon-node" onclick="viewDataDeep()">深度预览</a>
												<a class="mini-button" iconCls="icon-reload" onclick="dataRefresh()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								
						        <div class="mini-fit">
									<div id="userDataConfig" class="mini-datagrid" style="width:100%;height:100%;" sortMode="client"  
										url="${pageContext.request.contextPath}/act/user_data_config/page"  idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据" sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" 
										 allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" >
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="id" width="50" headerAlign="center" allowSort="true" align="center">ID</div>
											
											<div field="configName" width="100" headerAlign="center" allowSort="true">配置名称
												<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
											</div>
											
											<div field="url" width="280" headerAlign="center" allowSort="true" align="left">接口地址
												<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
											</div>
											
											<div field="dataProp" width="80" headerAlign="center" allowSort="true" align="left">返回值数据属性
												<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
											</div>
											<div field="configCode" width="120" headerAlign="center" allowSort="true" align="left">编码
												<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
											</div>
											
											<div field="appCode" width="120" headerAlign="center" allowSort="true" align="left">系统编码</div>
											
											<div field="appName" width="120" headerAlign="center" allowSort="true" align="left">系统名称 </div>
											
											<div type="comboboxcolumn" field="configType" width="80" headerAlign="center" allowSort="true" align="left">映射类型
												<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=user_data_config_config_type" />
											</div>
											
											<div type="comboboxcolumn" field="entityType" width="80" headerAlign="center" allowSort="true" align="left">映射实体
												<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=object_mapping_type" />
											</div>
											
											<div field="remark" width="80" headerAlign="center" allowSort="true" align="left">备注
												<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
											</div>
											
											<div field="configKey" width="100" headerAlign="center" allowSort="true" align="left">配置键
												<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
											</div>
											<!-- <div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div> -->
										</div>
									</div>
						        </div>
						    </div>
						</div>

					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">

							<div title="接口参数配置" refreshOnClick="true" name="tabUserResesxxx">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="paramEdit('add')">新增</a>
												<a class="mini-button" iconCls="icon-edit" onclick="paramEdit('edit')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="paramDelete()">删除</a>
												<a class="mini-button" iconCls="icon-save" onclick="paramSave()">保存</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-reload" onclick="subRefresh()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								 
								<div class="mini-fit">
									<div id="userDataConfigParam" class="mini-datagrid" style="width:100%;height:100%;" sortMode="client"
										url="${pageContext.request.contextPath}/act/user_data_config_param/page"   idField="id" multiSelect="true" allowResize="false"
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
									        
									        <div type="comboboxcolumn" field="paramType" width="80" headerAlign="center" allowSort="true" align="left">参数类型
												<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=user_data_config_param" />
											</div>
											
									        <div field="paramValue" width="80" headerAlign="center">参数值
									        	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									        </div>
									        
									         <div field="paramValueExpress" width="120" headerAlign="center">审批对象扩展属性表达式
									         	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									         </div>
									         
									        <div field="remark" width="80" align="right" headerAlign="center">备注
									        	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									        </div>
										</div>
									</div>
								</div>
							</div>


							<div title="结果集映射配置" refreshOnClick="true" name="tabUserSetting">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="dataMappingEdit('add')">新增</a>
												<a class="mini-button" iconCls="icon-edit" onclick="dataMappingEdit('edit')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="dataMappingDelete()">删除</a>
												<a class="mini-button" iconCls="icon-save" onclick="dataMappingSave()">保存</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-reload" onclick="subRefresh()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								 
								<div class="mini-fit">
									<div id="userDataMapping" class="mini-datagrid" style="width:100%;height:100%;" sortMode="client"
										url="${pageContext.request.contextPath}/act/user_data_mapping/page"   idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据" sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" 
										 allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" >
										<div property="columns">
									        <div type="checkcolumn" ></div>
									        <div field="localField" width="120" headerAlign="center">本地对象字段
									        	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									        </div>
									        <div field="remoteField" width="80" headerAlign="center">远程对象字段
									        	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									        </div>
									        <div field="remark" width="80" headerAlign="center">备注
									        	<input property="editor" class="mini-textbox" style="width:100%;" maxWidth="100" />
									        </div>
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					       					<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
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
			var grid = mini.get("userDataConfig"); 
			var userDataConfigParam = mini.get("userDataConfigParam");
			var userDataMapping = mini.get("userDataMapping");
			grid.load();
			grid.on("rowclick", function(e){
				var record = e.record;
				userDataConfigParam.load({configId:record.id});
				userDataMapping.load({configId:record.id});
			});
			
			tree.on("nodeclick",function(e){
				var node = e.node;
				
				//mini.alert(node.code);
				
				if(node.code!="-1") {
					grid.load({appCode: node.code});
					return ;
				}
				
				grid.load({});
				//subRefresh();
			})
			
			function copyInto() {
				var configRows = grid.getSelecteds();
				if(configRows.length == 0) {
					mini.alert("请选择配置接口");
					return ;
				}
				
				var configIds = [];
				for(var i=0;i<configRows.length;i++) {
					configIds.push(configRows[i].id);
				}
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/sys/application/selector_application.jsp?isMutil=false",
					title : "系统选择", width : 540,height : 350,
					ondestroy : function(action) {
    					if (action == "ok") {
    						
    			            var iframe = this.getIFrameEl();
    			            var row = iframe.contentWindow.GetData();
    			            row = mini.clone(row);    //必须。克隆数据。
    			            if(!row)return;
    			            
    			           
    			             
    			            var data = {appCode:row.code, ids : configIds.join(",")};
    			            $.ajax({
    							url : "${pageContext.request.contextPath}/act/user_data_config/copy_into",
    							dataType: 'json', type : 'post', cache : false,
    							data: data,
    							success : function(text) {
    								grid.reload();
    							}
    						});
    			        }  
					}
				});
			}
			
			function subRefresh(){
				userDataConfigParam.reload();
				userDataMapping.reload();
			}
	
			function dataMappingDelete () {
				var row = userDataMapping.getSelecteds();
				if(!row) {
					mini.alert("请选择一个参数");
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
								'url': "${pageContext.request.contextPath}/act/user_data_mapping/delete?ids="+ids.join(","),
								type: 'post',
								dataType:'JSON',
								cache: false,
								async:false,
								success: function (json) {
									mini.alert("删除成功");
									userDataMapping.reload();
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
			
			function dataMappingSave() {
			    var data = userDataMapping.getChanges();
	            var json = mini.encode(data);
	            userDataMapping.loading("保存中，请稍后......");
	            $.ajax({
	                url: "${pageContext.request.contextPath}/act/user_data_mapping/save_or_update",
	                data: { data: json },
	                type: "post",
	                success: function (text) {
	                	userDataMapping.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
			}
			
			function dataMappingEdit (action) {
				var row = grid.getSelected();
				if (!row){
					mini.alert("请选择一个配置接口");
					return ;
				}
				
				if(typeof action == 'undefined' || action == null || action == '') {
					mini.alert("操作参数不能为空");
					return ;
				}
				
				var dataMappedId = null;
				if(action == 'edit') {
					var r = userDataMapping.getSelected();
					if(!r) {
						mini.alert("请选择一个映射记录");
						return ;
					}
					dataMappedId = r.id;
				}
				
				//mini.alert(row.id);
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/user_data/edit_data_mapping.jsp",
					title : "新增映射",
					width : 540,
					height : 350,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : action,
							configId : row.id,
							id : dataMappedId
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						userDataMapping.reload();
					}
				});
			}
			
			
			function paramSave() {
			    var data = userDataConfigParam.getChanges();
	            var json = mini.encode(data);
	            userDataConfigParam.loading("保存中，请稍后......");
	            $.ajax({
	                url: "${pageContext.request.contextPath}/act/user_data_config_param/save_or_update",
	                data: { data: json },
	                type: "post",
	                success: function (text) {
	                	userDataConfigParam.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
			}
			
			function paramDelete () {
				var row = userDataConfigParam.getSelecteds();
				if(!row) {
					mini.alert("请选择一个参数");
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
								'url': "${pageContext.request.contextPath}/act/user_data_config_param/delete?ids="+ids.join(","),
								type: 'post',
								dataType:'JSON',
								cache: false,
								async:false,
								success: function (json) {
									mini.alert("删除成功");

									//grid.reload();
									userDataConfigParam.reload();
									//userDataMapping.reload();
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
			
			function paramEdit (action) {
				var row = grid.getSelected();
				if (!row){
					mini.alert("请选择一个配置接口");
					return ;
				}
				
				if(typeof action == 'undefined' || action == null || action == '') {
					mini.alert("操作参数不能为空");
					return ;
				}
				
				
				var paramId = null;
				if(action == 'edit') {
					var r = userDataConfigParam.getSelected();
					if(!r) {
						mini.alert("请选择一个参数记录");
						return ;
					}
					paramId = r.id;
				}
				
				//mini.alert(row.id);
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/user_data/edit_param.jsp",
					title : "新增参数",
					width : 540,
					height : 350,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : action,
							configId : row.id,
							id : paramId
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						//grid.reload();
						userDataConfigParam.reload();
					}
				});
			}
			
			function configEdit(action) {
				if(typeof action == 'undifined' || action == null || action == ''){
					mini.alert("action 参数不能为空");
					return ;
				}
				
				var id = null;
				var title = "";
				if(action == 'add') {
					title = "添加接口信息";
				}
				
				if(action == 'edit') {
					title = "编辑接口信息";
					var row = grid.getSelected();
					
					if (!row) {
						mini.alert("请选中一个配置记录");
						return ;
					}
					
					id = row.id;
				}
				
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/act/user_data/edit.jsp",
					title : title,
					width : 540,
					height : 350,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : action,
							id : id
						};
						//console.log(data);
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						grid.reload();
					}
				});
				
			}
			
			function viewData () {
				var row = grid.getSelected();
				//alert(row.id);
				if(row) {
					mini.open({
						url : "${pageContext.request.contextPath}/apps/default/admin/act/user_data/user_data_view.jsp",
						title : "数据预览",
						width : 800,
						height : 600,
						onload : function() {
							var iframe = this.getIFrameEl();
							var data = {configId : row.id}
							iframe.contentWindow.SetData(data);
						},
						ondestroy : function(action) {
							grid.reload();
						}
					});
					return ;
				}
				mini.alert("请选择一个接口");
			}
			
			function viewDataDeep () {
				var row = grid.getSelected();
				//alert(row.id);
				if(row) {
					mini.open({
						url : "${pageContext.request.contextPath}/apps/default/admin/act/user_data/user_data_view_deep.jsp",
						title : "数据预览",
						width : 800,
						height : 600,
						onload : function() {
							var iframe = this.getIFrameEl();
							var data = {configId : row.id}
							iframe.contentWindow.SetData(data);
						},
						ondestroy : function(action) {
							grid.reload();
						}
					});
					return ;
				}
				mini.alert("请选择一个接口");
			}
			
			function saveDataConfigGrid() {
			    var data = grid.getChanges();
	            var json = mini.encode(data);
	            grid.loading("保存中，请稍后......");
	            $.ajax({
	                url: "${pageContext.request.contextPath}/act/user_data_config/save_or_update",
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
 
			function dataRefresh(){
				 var node = tree.getSelectedNode();
		            if (node && node.id!=-1) {
		                grid.load({appId: node.id})
		            }else {
		            	mini.alert("请选择一个系统");
		            }
			}
	
			
			function configRemove() {
				var row = grid.getSelecteds();
				if (!row) {
					mini.alert("请选中一条以上的记录");
				}
				var ids = [];
				for(var i=0;i<row.length;i++) {
					ids.push(row[i].id);
				}
				mini.confirm("删除当前记录，对应的参数和结果集映射配置也将删除，是否确定？", "确定？",
					function (action) {
						if (action == "ok") {
							$.ajax({
								'url': "${pageContext.request.contextPath}/act/user_data_config/delete?ids="+ids.join(","),
								type: 'post',
								dataType:'JSON',
								cache: false,
								async:false,
								success: function (json) {
									mini.alert("删除成功");

									grid.reload();
									userDataConfigParam.reload();
									userDataMapping.reload();
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