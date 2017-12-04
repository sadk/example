<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>代码生成</title>
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
		<div class="mini-splitter" style="width:100%;height:100%; overflow:auto;">
			<div size="280" showCollapseButton="true">
			    <div class="mini-panel" showToolbar="true" showHeader="false" style="width:100%;height:100%;">
				     
				    <!--body-->
				    <div style="padding-left:3px;padding-bottom:5px;">
				    	 
						<div style="padding:5px;" id="form1">
					        <table>
					        	<tr>
					            	<td style="width:120px;">数据源：</td>
					                <td style="width:150px;">    
					                    <input id="dataSourceCode" name="dataSourceCode" onvaluechanged="onDataSourceCodeValuechanged" value="localhost" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/datasource/all" />
					                </td>
					            </tr>
					        	<tr>
					            	<td style="width:120px;">数据库名：</td>
					                <td style="width:150px;">  
					                	 <input id="dbName" name="dbName" value="test" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="name" url="${pageContext.request.contextPath}/datasource/get_database_list" />  
					                </td>
					            </tr>
					            <tr>
					            	<td style="width:120px;">表名：</td>
					                <td style="width:150px;">    
					                    <input style="width:150px;" name="tableName" id="tableName" class="mini-textbox" emptyText="请输入表名称" />
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
						    <div title="开发者定义的表" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												
												<a class="mini-button" iconCls="icon-edit" onclick="editSubTable()">主子表配置</a>
												<span class="separator"></span>
												groupId： <input id="groupId" name="groupId" class="mini-textbox" emptyText="请输入groupId" style="width:100px;"/>
												<span class="separator"></span>
												模块名：<input id="modules" name="modules" class="mini-textbox" emptyText="请输入模块名" style="width:100px;"/>
						                       	实体类名： <input id="entityName" name="entityName" class="mini-textbox" emptyText="请输入实体类名" style="width:100px;"/>
						                       	
						                       	<input id="codegenType" name="codegenType" value="1" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=codegen_type" />
						                       	<!-- 
						                        <a class="mini-button" onclick="codegen('1')" iconCls="icon-goto" >一键生成单表代码</a>
												<a class="mini-button" onclick="codegen('2')" iconCls="icon-goto" >一键生成主子表代码</a>
												<a class="mini-button" onclick="codegen('3')" iconCls="icon-goto" >一键生成自定义代码</a> --> 
												<a class="mini-button" onclick="codegenOneKey()" iconCls="icon-goto" >生成代码</a>
											</td>
											
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
						            <div id="devTableGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
						                url="${pageContext.request.contextPath}/table/page" idField="id" allowResize="false"  
						                sizeList="[5,10,20,50]" pageSize="20" showEmptyText="true" emptyText="暂无待修改信息" sortMode="client" 
						                allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" >
						               
						               
						                <div property="columns">
											<div type="checkcolumn" ></div>
											<div field="projectCode" type="comboboxcolumn" width="150" headerAlign="center" allowSort="true">工程模板
												<input property="editor" class="mini-combobox" showNullItem="false" onvaluechanged="onValueChanged" nullItemText="请选择..." emptyText="请选择"  textField="name" valueField="code" url="${pageContext.request.contextPath}/project/all" />
											</div>
											<div field="version" width="150" headerAlign="center" allowSort="true">版本</div>
											<div field="dbName" width="80" headerAlign="center" allowSort="true">数据库</div>
											<div field="tableName" width="120" headerAlign="center" allowSort="true">表名</div>	
											<div field="comment" width="160" headerAlign="center" allowSort="true">表注释</div>										
											<!-- <div field="dataSourceCode" width="80" headerAlign="center" allowSort="true">数据源编码</div> -->
											<div field="type" width="100" headerAlign="center" allowSort="true">表类型</div>
											<div field="engine" width="100" headerAlign="center" allowSort="true">表引擎</div>
											<div field="rows" width="60" headerAlign="center" allowSort="true">表记录数</div>
											<div field="autoIncrement" width="80" headerAlign="center" allowSort="true">自动增长值</div>
											<div field="collation" width="100" headerAlign="center" allowSort="true">表编码</div>
											<div field="createOption" width="150" headerAlign="center" allowSort="true">建表项</div>
											<!-- <div field="remark" width="160" headerAlign="center" allowSort="true">用户备注</div>
											<div field="appCode" width="60" headerAlign="center" allowSort="true">应用编码</div>-->
											<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        				<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>
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
						<div id="tabs2" contextMenu="#refreshTabMenu" onactivechanged="onActivechanged" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						
						
						
						
						
						
						
						
							<div title="ORO模板" refreshOnClick="true" name="oroTmpl">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-node" onclick="edit('oro')">查看</a>
												<a class="mini-button" iconCls="icon-split" onclick="codegen('oro')">生成</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key_oro" name="key_oro" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search('oro')">搜索</a>
						                    </td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="oroGrid" class="mini-datagrid" style="width:100%;height:100%;" sortMode="client"
										url="${pageContext.request.contextPath}/code_template/page"   idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据" sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" 
										allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true"
									>
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="name" width="80" headerAlign="center" allowSort="true">模板名称</div>
											<div field="code" width="80" headerAlign="center" allowSort="true">模板编号</div>
											<div field="tmplTypeDesc" width="120" headerAlign="center" allowSort="true">模板类型</div>	
											<div field="tmplResolveTypeDesc" width="120" headerAlign="center" allowSort="true">模板解析类型</div>
											<div field="content" width="120" headerAlign="center" allowSort="true">模板内容</div>
											<div field="pgk" width="160" headerAlign="center" allowSort="true">包名</div>										
											<div field="clazz" width="100" headerAlign="center" allowSort="true">类名</div>
											<div field="sn" width="60" headerAlign="center" allowSort="true">序号</div>
											<div field="remark" width="60" headerAlign="center" allowSort="true">备注</div>
											<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        				<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>
											
										</div>
									</div>
								</div>
							</div>
							

							<div title="Model模板" refreshOnClick="true" name="modelTmpl">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-node" onclick="edit('model')">查看</a>
												<a class="mini-button" iconCls="icon-split" onclick="codegen('model')">生成</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key_model" name="key_model" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search('model')">搜索</a>
						                    </td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="modelGrid" class="mini-datagrid" style="width:100%;height:100%;"
										url="${pageContext.request.contextPath}/code_template/page"  idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据"
										sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" sortMode="client"
									>
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="name" width="80" headerAlign="center" allowSort="true">模板名称</div>
											<div field="code" width="80" headerAlign="center" allowSort="true">模板编号</div>
											<div field="tmplTypeDesc" width="120" headerAlign="center" allowSort="true">模板类型</div>	
											<div field="tmplResolveTypeDesc" width="120" headerAlign="center" allowSort="true">模板解析类型</div>
											<div field="content" width="120" headerAlign="center" allowSort="true">模板内容</div>
											<div field="pgk" width="160" headerAlign="center" allowSort="true">包名</div>										
											<div field="clazz" width="100" headerAlign="center" allowSort="true">类名</div>
											<div field="sn" width="60" headerAlign="center" allowSort="true">序号</div>
											<div field="remark" width="60" headerAlign="center" allowSort="true">备注</div>
											<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        				<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>
											
										</div>
									</div>
								</div>
							</div>

							
							<div title="DAO模板" refreshOnClick="true"  name="daoTmpl">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-node" onclick="edit('dao')">查看</a>
												<a class="mini-button" iconCls="icon-split" onclick="codegen('dao')">生成</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key_dao" name="key_dao" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search('dao')">搜索</a>
						                    </td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="daoGrid" class="mini-datagrid" style="width:100%;height:100%;"
										url="${pageContext.request.contextPath}/code_template/page"  idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据"
										sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" sortMode="client"
									>
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="name" width="80" headerAlign="center" allowSort="true">模板名称</div>
											<div field="code" width="80" headerAlign="center" allowSort="true">模板编号</div>
											<div field="tmplTypeDesc" width="120" headerAlign="center" allowSort="true">模板类型</div>	
											<div field="tmplResolveTypeDesc" width="120" headerAlign="center" allowSort="true">模板解析类型</div>
											<div field="content" width="120" headerAlign="center" allowSort="true">模板内容</div>
											<div field="pgk" width="160" headerAlign="center" allowSort="true">包名</div>										
											<div field="clazz" width="100" headerAlign="center" allowSort="true">类名</div>
											<div field="sn" width="60" headerAlign="center" allowSort="true">序号</div>
											<div field="remark" width="60" headerAlign="center" allowSort="true">备注</div>
											<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        				<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>
											
										</div>
									</div>
								</div>
							</div>
							
							
							<div title="Service模板" refreshOnClick="true" name="serviceTmpl">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-node" onclick="edit('service')">查看</a>
												<a class="mini-button" iconCls="icon-split" onclick="codegen('service')">生成</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key_service" name="key_service" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search('service')">搜索</a>
						                    </td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="serviceGrid" class="mini-datagrid" style="width:100%;height:100%;"
										url="${pageContext.request.contextPath}/code_template/page"  idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据"
										sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" sortMode="client" >
										
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="name" width="80" headerAlign="center" allowSort="true">模板名称</div>
											<div field="code" width="80" headerAlign="center" allowSort="true">模板编号</div>
											<div field="tmplTypeDesc" width="120" headerAlign="center" allowSort="true">模板类型</div>	
											<div field="tmplResolveTypeDesc" width="120" headerAlign="center" allowSort="true">模板解析类型</div>
											<div field="content" width="120" headerAlign="center" allowSort="true">模板内容</div>
											<div field="pgk" width="160" headerAlign="center" allowSort="true">包名</div>										
											<div field="clazz" width="100" headerAlign="center" allowSort="true">类名</div>
											<div field="sn" width="60" headerAlign="center" allowSort="true">序号</div>
											<div field="remark" width="60" headerAlign="center" allowSort="true">备注</div>
											<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        				<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>
										</div>
									</div>
								</div>
							</div>
							
							
							
							
							<div title="Controller模板" refreshOnClick="true" name="controllerTmpl">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-node" onclick="edit('controller')">查看</a>
												<a class="mini-button" iconCls="icon-split" onclick="codegen('controller')">生成</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key_controller" name="key_controller" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search('controller')">搜索</a>
						                    </td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="controllerGrid" class="mini-datagrid" style="width:100%;height:100%;"
										url="${pageContext.request.contextPath}/code_template/page"  idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据"
										sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" sortMode="client" >
										
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="name" width="80" headerAlign="center" allowSort="true">模板名称</div>
											<div field="code" width="80" headerAlign="center" allowSort="true">模板编号</div>
											<div field="tmplTypeDesc" width="120" headerAlign="center" allowSort="true">模板类型</div>	
											<div field="tmplResolveTypeDesc" width="120" headerAlign="center" allowSort="true">模板解析类型</div>
											<div field="content" width="120" headerAlign="center" allowSort="true">模板内容</div>
											<div field="pgk" width="160" headerAlign="center" allowSort="true">包名</div>										
											<div field="clazz" width="100" headerAlign="center" allowSort="true">类名</div>
											<div field="sn" width="60" headerAlign="center" allowSort="true">序号</div>
											<div field="remark" width="60" headerAlign="center" allowSort="true">备注</div>
											<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        				<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>
											
										</div>
									</div>
								</div>
							</div>
							
							
							
							<div title="页面模板" refreshOnClick="true" name="pageTmpl">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-node" onclick="edit('page')">查看</a>
												<a class="mini-button" iconCls="icon-split" onclick="codegen('page')">生成</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key_page" name="key_page" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search('page')">搜索</a>
						                    </td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="pageGrid" class="mini-datagrid" style="width:100%;height:100%;"
										url="${pageContext.request.contextPath}/code_template/page"  idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据"
										sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" sortMode="client" >
										
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="name" width="80" headerAlign="center" allowSort="true">模板名称</div>
											<div field="code" width="80" headerAlign="center" allowSort="true">模板编号</div>
											<div field="tmplTypeDesc" width="120" headerAlign="center" allowSort="true">模板类型</div>	
											<div field="tmplResolveTypeDesc" width="120" headerAlign="center" allowSort="true">模板解析类型</div>
											<div field="content" width="120" headerAlign="center" allowSort="true">模板内容</div>
											<div field="pgk" width="160" headerAlign="center" allowSort="true">包名</div>										
											<div field="clazz" width="100" headerAlign="center" allowSort="true">类名</div>
											<div field="sn" width="60" headerAlign="center" allowSort="true">序号</div>
											<div field="remark" width="60" headerAlign="center" allowSort="true">备注</div>
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
			
			var form = new mini.Form("#form1");
			var tabs = mini.get("tabs1");
			var tabs2 = mini.get("tabs2");
			
			
			var grid = mini.get("devTableGrid");
			var oroGrid = mini.get("oroGrid");
			var daoGrid = mini.get("daoGrid");
			var serviceGrid = mini.get("serviceGrid");
			var controllerGrid = mini.get("controllerGrid");
			var modelGrid = mini.get("modelGrid");
			var pageGrid = mini.get("pageGrid");
			var isPageInit = false;
			var codegenTypeCmb = mini.get("codegenType");
			var dbName = mini.get("dbName");
			
			grid.load();
			/*
			oroGrid.load();
			daoGrid.load();
			serviceGrid.load();
			controllerGrid.load();
			pageGrid.load();
			*/
			
			function onDataSourceCodeValuechanged(e) {
				//mini.alert(e.source.value); // e.sender
				
				var data = {};
				data.dataSourceCode = e.source.value;
	            $.ajax({
	                url: "${pageContext.request.contextPath}/datasource/get_database_list",
	                data: data,
	                type: "post",
	                success: function (text) {
	                	//mini.alert(text);
	                	dbName.setData(text);
	                	
	                	var isTestExists = false;
	                	for(var i=0;i<text.length;i++) {
	                		if(text[i].name == 'test') {
	                			isTestExists = true;
	                		}
	                	}
	                	if (isTestExists) {
	                		dbName.setValue('test');
	                	}else {
	                		dbName.select(0);
	                	}
	                	
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	            });
			}
			
			function onValueChanged(e){
				var row = grid.getSelected();
				if(!row){
					mini.alert("请选择一个表定义");
					return ;
				}				
				
				var data = {};
				data.id=row.id;
				data.projectCode = e.sender.getValue();
				
				$.ajax({
					url : "${pageContext.request.contextPath}/table/update_project_code",
					data: data,
					dataType: 'json',type : 'post', cache : false,
					success : function(text) {
						grid.commitEdit();
						grid.reload();
					},
					error : function(data) {
				  		mini.alert(data.responseText);
					}
				});
			}
			
			function search() {
				var data = form.getData();
				grid.load(data);
			}
			
			function onActivechanged(sender) {
				var index = sender.index;
				var name = sender.name;
				// alert(index + "  "+name);
				
				/*
				模板类型:
					/**
					 * 
					-- 模板类型
					-- ORO         0=Mybatis3_Mapper.xml 1=Hibernate3.hbm.xml 2=Example.ftl.sql.xml 
					-- Controller  5=SpringMVC_Controller.java 6=Struts2_Action.java 20=ExampleMVC_Controller.java 
					-- Dao         3=Dao_Mybatis3.java 11= Dao_Hibernate3.java 12=Dao_SpringJDBC.java 13=Dao_DBUtil.java
					-- Service     4=Service_Example.java  14=Service_Spring.java
					-- Page        7=jsp 8=html 9=*.vm 10=*.ftl
					-- Model       15=Model.java
					
					*/
				var tmplTypes = "";
				var projectCode = '';
				var row = grid.getSelected();
				//mini.alert(1111);
				if(row) {
					projectCode = row.projectCode;
				}else {
					return ;
				}
				
				if(name == 'oroTmpl') {
					tmplTypes = "0,1,2";
					oroGrid.load({tmplTypes:tmplTypes,projectCode:projectCode});
				}
				else if(name == 'daoTmpl') {
					tmplTypes = "3,11,12,13";
					daoGrid.load({tmplTypes:tmplTypes,projectCode:projectCode});
				}
				else if(name == 'serviceTmpl') {
					tmplTypes = "4,14";
					serviceGrid.load({tmplTypes:tmplTypes,projectCode:projectCode});
				}
				else if(name == 'controllerTmpl') {
					tmplTypes = '5,6,20';
					controllerGrid.load({tmplTypes:tmplTypes,projectCode:projectCode});
				}
				else if(name == 'pageTmpl') {
					tmplTypes = '7,8,9,10';
					pageGrid.load({tmplTypes:tmplTypes,projectCode:projectCode});
				}
				else if(name = 'modelTmpl') {
					tmplTypes = '15';
					modelGrid.load({tmplTypes: tmplTypes,projectCode:projectCode});
				}
				
			}
			
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
			
			function refreshAll() {
				var t = grid.getSelected();
				if(!t)return ;
				var projectCode = t.projectCode;
				
				var tab = tabs2.getActiveTab();
				
				var name = tab.name;
				if(name == 'oroTmpl') {
					tmplTypes = "0,1,2";
					oroGrid.load({tmplTypes:tmplTypes,projectCode:projectCode});
				}
				else if(name == 'daoTmpl') {
					tmplTypes = "3,11,12,13";
					daoGrid.load({tmplTypes:tmplTypes,projectCode:projectCode});
				}
				else if(name == 'serviceTmpl') {
					tmplTypes = "4,14";
					serviceGrid.load({tmplTypes:tmplTypes,projectCode:projectCode});
				}
				else if(name == 'controllerTmpl') {
					tmplTypes = '5,6,20';
					controllerGrid.load({tmplTypes:tmplTypes,projectCode:projectCode});
				}
				else if(name == 'pageTmpl') {
					tmplTypes = '7,8,9,10';
					pageGrid.load({tmplTypes:tmplTypes,projectCode:projectCode});
				}
				else if(name = 'modelTmpl') {
					tmplTypes = '15';
					modelGrid.load({tmplTypes: tmplTypes,projectCode:projectCode});
				}
			}
			grid.on("rowclick", function(e){
				var record = e.record;
				 
				var projectCode=record.projectCode;
				//mini.alert(projectCode)
				refreshAll();
			});
			/*
			grid.on("drawcell", function (e) {
	            var record = e.record,
	        	column = e.column,
		        field = e.field,
		        value = e.value;
	 
	        });
			*/
			
			function add(type) {
				/*
				模板类型:
					ORO ---------> 0=Mybatis3-Mapper.xml   1=Hibernate3.hbm.xml    2=Example.ftl.sql.xml 
					DAO ---------> 3=Dao.java   
					Service -----> 4=Service.java
					Controller --> 5=SpringMVC-Controller.java     6=Struts2-Action.java      20=ExampleMVC-Controller.java 
					Page --------> （页面模板有：7=jsp 8=html 9=*.vm 10=*.ftl）
					*/
				var tmplTypes = "";
				if('oro' == type) {
					tmplTypes = "0,1,2";
				}
				else if('dao' == type) {
					tmplTypes = "3";
				}
				else if('service' == type) {
					tmplTypes = "4";
				}
				else if('controller' == type) {
					tmplTypes = '5,6,20';
				}
				else if("page" == type) {
					tmplTypes = '7,8,9,10';
				}
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/sys/code_template/edit.jsp",
					title : "添加ORO模板",
					width : 700,
					height : 730,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : 'add',
							id : row.id,
							projectCode: row.code//,
							//tmplTypes : tmplTypes
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						oroGrid.reload();
					}
				});
			}
			
			function codegen(type) {
				var tmplGrid = null;
				var tmplTypes = "";
				var title = '';
				if('oro' == type) {
					tmplTypes = "0,1,2";
					title = 'ORO';
					tmplGrid = oroGrid;
				}
				else if('dao' == type) {
					tmplTypes = "3,11,12,13";
					title='Dao';
					tmplGrid = daoGrid;
				}
				else if('service' == type) {
					tmplTypes = "4,14";
					title = 'Service'
					tmplGrid = serviceGrid;
				}
				else if('controller' == type) {
					tmplTypes = '5,6,20';
					title='Controller'
					tmplGrid= controllerGrid;
				}
				else if("page" == type) {
					tmplTypes = '7,8,9,10';
					title = 'Page';
					tmplGrid = pageGrid;
				}
				else if("model" == type) {
					tmplTypes = '15';
					title = 'Model'
					tmplGrid = modelGrid;
				}
				var r = grid.getSelected();
				if(!r){
					mini.alert("请选择一个开发者定义的表");
					return ;
				}
				var row = tmplGrid.getSelected();
				if(!row) {
					mini.alert("请选择一个模板");
					return ;
				}
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/sys/code_template/codegen.jsp",
					title : "正在使用【"+title+"】模板",
					width : 700,
					height : 750,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : "view",
							
							tableId: r.id,
							tableName: r.tableName,
							dbName : r.dbName,
							
							id : row.id, //模板ID
							projectCode: row.code, // 模板编码
							tmplTypes : tmplTypes,
							type : type
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						/*
						if(action == 'ok'){
							
						}
						*/
					}
				});
			}
				

			
			function edit(type) {
				var grid = null;
				var tmplTypes = "";
				var title = '';
				if('oro' == type) {
					tmplTypes = "0,1,2";
					title = 'ORO';
					grid = oroGrid;
				}
				else if('dao' == type) {
					tmplTypes = "3,11,12,13";
					title='Dao';
					grid = daoGrid;
				}
				else if('service' == type) {
					tmplTypes = "4,14";
					title = 'Service'
					grid = serviceGrid;
				}
				else if('controller' == type) {
					tmplTypes = '5,6,20';
					title='Controller'
					grid= controllerGrid;
				}
				else if("page" == type) {
					tmplTypes = '7,8,9,10';
					title = 'Page';
					grid = pageGrid;
				}
				else if("model" == type) {
					tmplTypes = '15';
					title = 'Model'
					grid = modelGrid;
				}
				var row = grid.getSelected();
				if(!row) {
					mini.alert("请选择一个模板");
					return ;
				}
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/sys/code_template/edit.jsp",
					title : "查看"+title+"模板",
					width : 700,
					height : 730,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : "view",
							id : row.id,
							projectCode: row.code,
							tmplTypes : tmplTypes,
							type : type
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						//refreshAll();
					}
				});
			}
			
			
			
			// 主子表定义
			function editSubTable() {
				var row = grid.getSelected();
				if(typeof(action) == 'undefined') {
					action = "edit";
				}
				
				if (row) {
					mini.open({
						url : "${pageContext.request.contextPath}/apps/default/admin/sys/code_template/edit_main_sub.jsp?dbName="+row.dbName,
						title : "主子表配置",
						width : 560,
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
							
							if("save" == action) {
								mini.alert("保存成功");
							}
							grid.reload();
						}
					});
				} else {
					mini.alert("请选中一条记录");
				}
			}
			
			function remove() {
				 
			}		
			
			function codegenOneKey() { // 1=一键生成单表 2=一键生成主子表代码 3=定制生成代码
				var row = grid.getSelected();
				if(row) {
					var data = {};
					var r = mini.clone(row);
					data.tableId = r.id;
					data.dbName = r.dbName;
					data.version = r.version;
					data.tableName = r.tableName;
					data.codegenType = codegenTypeCmb.getValue();
					data.groupId = mini.get("groupId").value;
					data.modules = mini.get("modules").value;
					data.entityName = mini.get("entityName").value;
					window.location = "${pageContext.request.contextPath}/code_template/codegen?tableId="+data.tableId+"&groupId="+data.groupId+"&modules="+data.modules+"&entityName="+data.entityName+"&codegenType="+data.codegenType;
					/*
					$.ajax({
						url : "${pageContext.request.contextPath}/code_template/codegen",
						dataType: 'json',
						type : 'post',
						cache : false,
						data: data,
						success : function(text) {
							//mini.alert('生成成功');
						},
						error : function(data) {
					  		mini.alert(data.responseText);
						}
					});*/
				} else {
					mini.alert("请选择一个表定义的记录");
				}
			}
			
		</script>
	</body>
</html>