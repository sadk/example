<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>数据源管理</title>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
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
		<div class="mini-splitter" style="width:100%;height:100%;">
			<div size="270" showCollapseButton="true">
			    <div class="mini-panel" showToolbar="true" showHeader="false" style="width:100%;height:100%;">
				    <div style="padding-left:3px;padding-bottom:5px;">
				            <div style="padding:0px;" id="form1">
						        <table>
						            <tr>
						            	<td style="width:120px;">关键字：</td>
						                <td style="width:150px;">
						                	<input name="key" id="key" class="mini-textbox" emptyText="请输入关键字搜索"  onenter="search"/>
						                </td>
						            </tr>
						            <tr>
						            	<td style="width:70px;">所属应用：</td>
						                <td style="width:150px;">
						                	<input id="appCode" name="appCode" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/application/all" />
						                </td>
						            </tr>
						            <tr>
						            	<td>名称：</td>
						                <td>    
						                    <input name="name" id="name" class="mini-textbox" emptyText="请输入名称"/>
						                </td>
						            </tr>
						            <tr>
						                <td>登陆名：</td>
						                <td>    
						                    <input name="loginName" id="loginName" class="mini-textbox" emptyText="请输入登陆名"/>
						                </td>
						            </tr>
						            <tr>
						            	<td>地址：</td>
						                <td>    
						                    <input name="address" id="address" class="mini-textbox" emptyText="请输入地址或域名"/>
						                </td>
						            </tr>
						            <tr>
						            	<td>备注：</td>
						                <td>    
						                    <input name="remark" id="remark" class="mini-textbox" emptyText="请输入备注"/>
						                </td>
						            </tr>
									<tr>
										<td>创建时间(开始)：</td>
										<td>
											<input id="createTimeBegin" name="createTimeBegin" class="mini-datepicker" emptyText="请输入"/>
										</td>
									</tr>
									<tr>
										<td>创建时间(结束)：</td>
										<td>
											<input id="createTimeEnd" name="createTimeEnd" class="mini-datepicker"  emptyText="请输入"/>
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
					        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
								<table style="width:100%;">
									<tr>
										<td style="width:100%;">
											<a class="mini-button" iconCls="icon-add" onclick="add()">添加</a>
											<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
											<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
											<a class="mini-button" iconCls="icon-node" onclick="edit('view')">查看</a>
											<a class="mini-button" iconCls="icon-split" onclick="testConnection()">测试连接</a>
											<!-- 
											<span class="separator"></span>  
											<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
											<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
											<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
											 -->
										</td>
										<td style="white-space:nowrap;">
					                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
					                        <a class="mini-button" onclick="search()">查询</a>
					                    </td>
									</tr>
								</table>
							</div>
						
					        <div class="mini-fit">
					            <div id="dataSourceGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
					                url="${pageContext.request.contextPath}/datasource/page" idField="id" allowResize="false"
					                sizeList="[5,10,20,50]" pageSize="20" showEmptyText="true" emptyText="暂无数据"  allowAlternating="true" sortMode="client">
					                <div property="columns">
										<div type="checkcolumn"></div>
										<div field="name" width="120" headerAlign="center" align="left">数据源名称</div>
										<div field="code" width="120" headerAlign="center" align="left">数据源编码</div>	
										
										<div field="url" width="260" headerAlign="center" allowSort="true"  align="left">数据链接地址</div>	
										<div field="loginName" width="80" headerAlign="center" allowSort="true">登陆名</div>
										<!-- 
										<div field="loginPassword" width="80" headerAlign="center" allowSort="true">登陆密码</div>
										 -->
										<div field="driverClassName" width="160" headerAlign="center" allowSort="true">数据库动</div>	
																					
										
										<div field="address" width="150" headerAlign="center" allowSort="true"  align="right">数据库域名或地址</div>
										<div field="port" width="100" headerAlign="center" allowSort="true" align="center">数据库端口</div>
										<div type="comboboxcolumn" field="status" width="80" headerAlign="center" align="left" allowSort="true">状态
											<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=datasource" />
										</div>
										
										<div field="sn" width="80" align="center" headerAlign="center" allowSort="true">序号</div>
										
										<div field="remark" width="100" headerAlign="center" allowSort="true">备注</div>											
										<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center">创建日期</div>
					        			<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center">更新日期</div>  
									</div>
					    		</div>
							</div>
					</div>
					<div showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							
							<div title="数据源属性" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="editProp('add')">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeProp()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editProp('edit')">编辑</a>
												<a class="mini-button" iconCls="icon-save" onclick="saveShortProp()">保存</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-edit" onclick="copyProp()">复制</a>
											</td>
											
										</tr>
									</table>
								</div>
								
								
								
								<div class="mini-fit">
									<div id="propertyGrid" class="mini-datagrid" style="width:100%;height:100%;"
										url="${pageContext.request.contextPath}/property/page" idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据"
										allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" 
										sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" sortMode="client">
										<div property="columns">
											<div type="checkcolumn"></div>
											<div field="parentCode" width="80" headerAlign="center" allowSort="true">数据源编码</div>											
											
											<div field="name" width="170"  align="left" headerAlign="center" allowSort="true">属性名
												<input property="editor" class="mini-textbox" style="width:100%;"   />
											</div>
											<div field="value"width="60" headerAlign="center" allowSort="true">属性值
												<input property="editor" class="mini-textbox" style="width:100%;"   />
											</div>
																		
											<div field="remark" align="left" width="350" headerAlign="center" allowSort="true">备注
												<input property="editor" class="mini-textbox" style="width:100%;"   />
											</div>
											
											<div field="appCode"  headerAlign="center" align="right" width="60" allowSort="true">应用编码</div>	
											<div field="sn" headerAlign="center" align="right" width="50" allowSort="true">序号
												<input property="editor"  class="mini-spinner" value="0" minValue="0" maxValue="999999999"  style="width:100%;" />
											</div>
											
											<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center">创建日期</div>
					        				<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center">更新日期</div>  
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
				</div>
			</div>
		</div>
		<script type="text/javascript">
			mini.parse();
			
			var form = new mini.Form("#form1");
			function clear() {
				 form.clear();
			}
			
			
			var tabs = mini.get("tabs1");
			var currentTab = null;
			function onBeforeOpen(e) {
				currentTab = tabs.getTabByEvent(e.htmlEvent);
				if(!currentTab) {
					e.cancel = true;
				}
			}
			
			function reloadTab() {
				tabs.reloadTab(currentTab);
			}
			
			var propertyGrid = mini.get("propertyGrid");
			var grid = mini.get("dataSourceGrid");
			grid.on("rowclick", function(e){
				var record = e.record;
				//propertyGrid.setUrl("${pageContext.request.contextPath}/property/page?parentCode="+record.code);
				propertyGrid.load({parentCode:record.code,type:'datasource'});
			});
			
			/*
			grid.on("drawcell", function (e) {
	            var record = e.record,
	        	column = e.column,
		        field = e.field,
		        value = e.value;
	            
	        });*/
			grid.load();
			
			function reload(){
				grid.reload();
			}
			
 
			function search() {
				var data = form.getData();
				
				var createTimeBegin = mini.get('createTimeBegin').text;
				var createTimeEnd = mini.get('createTimeEnd').text;
				
				data.createTimeBegin = createTimeBegin;
				data.createTimeEnd = createTimeEnd;
				
				var key2 = mini.get("key2").value;
				if( (data.key==null || data.key=="") && (key2!=null && key2!="")){
					data.key = key2;
				}
				
				grid.load(data);
			}

			function remove() {
				var row = grid.getSelected();
				if(row) {
					mini.confirm("确定删除？", "确定？",
							function (action) {
								if (action == "ok") {
									$.ajax({
										'url': "${pageContext.request.contextPath}/datasource/delete?ids="+row.id,
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
				} else {
					mini.alert("请选中一条以上的记录");
				}
			}
			
			function add() {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/sys/datasource/edit.jsp",
					title : "添加数据源",
					width : 490,
					height : 400,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : "add",
							pid : row.id
							
						};
						console.log(data)
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
						url : "${pageContext.request.contextPath}/apps/default/admin/sys/datasource/edit.jsp",
						title : "编辑数据源信息",
						width : 490,
						height : 400,
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
			
			function testConnection() {
				var row = grid.getSelected();
				if(row) {
					$.ajax({
						url : "${pageContext.request.contextPath}/datasource/test/connection/id?id="+row.id,
						type : 'post',
						cache : false,
						data: form.getData(),
						success : function(text) {
							if(text) {
								if(text.isOk) {
									mini.alert("连接成功!");
								}else {
									var options ={
									    content: text.message,    
									    state: "danger",      //default|success|info|warning|danger
									    x: "center",          //left|center|right
									    y: "center",          //top|center|bottom
									    timeout: 4000     //自动消失间隔时间。默认2000（2秒）。
									}
									mini.showTips(options)
								}
							}
						},
						error : function(data) {
					  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
					  		mini.alert("连接失败!"+data.statusText);
						}
					});
				} else {
					mini.alert("请选中一条记录");
				}
			}
			
			
// ------------------------------------------------ 属性操作 --------------------------------------------
			function removeProp() {
				var rows = propertyGrid.getSelecteds();
				if(rows.length == 0) {
					mini.alert("请至少选择一个属性");
					return ;
				}
				var data = {};
				var array = new Array();
				for(var i=0;i<rows.length;i++) {
					array.push(rows[i].id)
				}
				data.ids = array.join(",")
				$.ajax({
					url : "${pageContext.request.contextPath}/property/delete",
					type : 'post', cache : false,
					data: data,
					success : function(text) {
						mini.alert("删除成功!");
						propertyGrid.reload();
					},
					error : function(data) {
				  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
				  		mini.alert("连接失败!"+data.statusText);
					}
				});
			}
			
			
			function editProp(action){
				var row = grid.getSelected();
				if(action == 'add') {
					if(!row) {
						mini.alert("请选择一个数据源");
						return ;
					}
				}
				var propRow = propertyGrid.getSelected();
				
				if (row) {
					mini.open({
						url : "${pageContext.request.contextPath}/apps/default/admin/sys/datasource/edit_prop.jsp",
						title : "编辑边接属性",
						width : 500,
						height : 260,
						onload : function() {
							var iframe = this.getIFrameEl();
							var data = {
								action : action
							};
							if('add' == action) {
								data.parentCode = row.code
								data.appCode = row.appCode
							}						
							if('edit' == action) {
								data.id = propRow.id
								data.parentCode = propRow.parentCode
								data.appCode = propRow.appCode
							}
							data.type ="datasource";
							iframe.contentWindow.SetData(data);
						},
						ondestroy : function(action) {
							propertyGrid.reload();
						}
					});
				} else {
					mini.alert("请选中一条记录");
				}
			}

			function saveShortProp() {
			    var data = propertyGrid.getChanges();
	            var json = mini.encode(data);
	           // alert(json);
	            propertyGrid.loading("保存中，请稍后......");
	           // if(true) return ;
	            $.ajax({
	                url: "${pageContext.request.contextPath}/property/save_or_update_short",
	                data: { data: json },
	                type: "post",
	                success: function (text) {
	                	propertyGrid.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
			}
			
			function copyProp() {
				var row = grid.getSelected();
				if (!row) {
					mini.alert("请选择一个数据源"); return 
				}

				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/sys/datasource/seletor_datasource.jsp?idNotId="+row.id,
					title : "请选择需要拷贝的数据源",
					width : 500, height : 460,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : 'copy'
						};
						data.type ="datasource";
						//data.idNotIn = row.id;
						//iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						if("ok" == action) {
							var iframe = this.getIFrameEl();
							var ds = iframe.contentWindow.GetData();
							ds = mini.clone(ds);
							var data ={};
							data.sourceDatasourceId = ds.id;
							data.targetDataSourceId = row.id;
							data.type = "datasource";
							
					        mini.confirm("确定复制属性? 将会删除已存在的属性配置.", "确定？",
				                function (action) {
				                    if (action == "ok") {
							            $.ajax({
							                url: "${pageContext.request.contextPath}/property/copy_properties_by_datasource_id",
							                data: data ,  type: "post",
							                success: function (text) {
							                	mini.alert("复制成功");
							                	propertyGrid.reload();
							                },
							                error: function (jqXHR, textStatus, errorThrown) {
							                    alert(jqXHR.responseText);
							                }
							            });
				                    }
				                }
					        );
						}
					}
				});
		     
				
			}
		</script>
	</body>
</html>