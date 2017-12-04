<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>代码生成管理</title>
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
			            <div style="padding:5px;">
					        <table>
					            <tr>
					            	<td style="width:90px;">工程模板名：</td>
					                <td style="width:150px;">    
					                    <input style="width:150px;" name="customer.custNo" class="mini-textbox"  />
					                </td>
					            </tr>
					            <tr>
					            	<td style="width:90px;">文件名：</td>
					                <td style="width:150px;">    
					                    <input style="width:150px;" name="customer.custName" class="mini-textbox" />
					                </td>
					            </tr>
					            <tr>
					                <td style="width:90px;">备注：</td>
					                <td style="width:150px;">    
					                    <input style="width:150px;" name="account.accNo" class="mini-textbox" />
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
						    <div title="工程模板" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="add()">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="eidtProject()">编辑</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search()">上传</a>
						                    </td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
						            <div id="projectGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
						                url="${pageContext.request.contextPath}/project/page" idField="id" allowResize="false"
						                sizeList="[5,10,20,50]" pageSize="20" showEmptyText="true" emptyText="暂无待修改信息" sortMode="client" >
						                <div property="columns">
											<div type="checkcolumn" ></div>
											<div field="eclipse" width="80" headerAlign="center" align="center" allowSort="true">Eclipse工程</div>
											<div field="structure" width="80" headerAlign="center" align="center" allowSort="true">Maven结构</div>
											<div field="code" width="80" headerAlign="center" align="center" allowSort="true">工程编码</div>
											<div field="name" width="150" headerAlign="center" align="left" allowSort="true">工程标识</div>	
											<div field="remark" width="300" headerAlign="center" align="left" allowSort="true">工程说明</div>
											<div field="levelDb" width="160" headerAlign="center" align="center" allowSort="true">DB层</div>										
											<div field="levelWeb" width="100" headerAlign="center" align="center" allowSort="true">WEB层</div>
											<div field="levelView" width="100" headerAlign="center" align="center" allowSort="true">View层</div>
											<div field="fileName" width="100" headerAlign="center" align="left" allowSort="true">文件名</div>
											<div field="filePath" width="250" headerAlign="center" align="left" allowSort="true">文件路径</div>
											<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        				<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>
										</div>
						            </div>
						        </div>
						    </div>
						    <!-- 
						    <div title="工程其它" refreshOnClick="true" url="${base_url}/custody/fund/voucher/review"></div>
						     -->
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
												<a class="mini-button" iconCls="icon-add" onclick="add('oro')">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit('oro')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove('oro')">删除</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key_oro" name="key_oro" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search('oro')">搜索</a>
						                    </td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="oroGrid" class="mini-datagrid" style="width:100%;height:100%;"
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
							

							<div title="Model模板" refreshOnClick="true" name="modelTmpl">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="add('model')">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit('model')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove('model')">删除</a>
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
												<a class="mini-button" iconCls="icon-add" onclick="add('dao')">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit('dao')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove('dao')">删除</a>
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
												<a class="mini-button" iconCls="icon-add" onclick="add('service')">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit('service')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove('service')">删除</a>
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
												<a class="mini-button" iconCls="icon-add" onclick="add('controller')">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit('controller')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove('controller')">删除</a>
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
												<a class="mini-button" iconCls="icon-add" onclick="add('page')">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit('page')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove('page')">删除</a>
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
			var tabs = mini.get("tabs1");
			var tabs2 = mini.get("tabs2");
			
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
			
			var grid = mini.get("projectGrid");
			var oroGrid = mini.get("oroGrid");
			var daoGrid = mini.get("daoGrid");
			var serviceGrid = mini.get("serviceGrid");
			var controllerGrid = mini.get("controllerGrid");
			var modelGrid = mini.get("modelGrid");
			var pageGrid = mini.get("pageGrid");
			
			
			grid.load();
			/*oroGrid.load();
			daoGrid.load();
			serviceGrid.load();
			controllerGrid.load();
			pageGrid.load();
			*/
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
				if(row) {
					projectCode = row.code;
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
			

			
			function refreshAll() {
				var t = grid.getSelected();
				if(!t)return ;
				var projectCode = t.code;
				
				var tab = tabs2.getActiveTab();
				//mini.alert(tab);
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
				 
				var projectCode=record.code;
				
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
				var row = grid.getSelected();
				if(!row) {
					mini.alert("请选择一个工程模板");
					return ;
				}
				/*
				模板类型:
					-- ORO         0=Mybatis3_Mapper.xml 1=Hibernate3.hbm.xml 2=Example.ftl.sql.xml 
					-- Controller  5=SpringMVC_Controller.java 6=Struts2_Action.java 20=ExampleMVC_Controller.java 
					-- Dao         3=Dao_Mybatis3.java 11= Dao_Hibernate3.java 12=Dao_SpringJDBC.java 13=Dao_DBUtil.java
					-- Service     4=Service_Example.java  14=Service_Spring.java
					-- Model       15= Model.java
					-- Page        7=jsp 8=html 9=*.vm 10=*.ftl
					*/
				var tmplTypes = "";
				var title = '';
				if('oro' == type) {
					tmplTypes = "0,1,2";
					title = 'ORO';
				}
				else if('dao' == type) {
					tmplTypes = "3,11,12,13";
					title='Dao';
				}
				else if('service' == type) {
					tmplTypes = "4,14";
					title = 'Service'
				}
				else if('controller' == type) {
					tmplTypes = '5,6,20';
					title='Controller'
				}
				else if("page" == type) {
					tmplTypes = '7,8,9,10';
					title = 'Page';
				}
				else if("model" == type) {
					tmplTypes = '15';
					title = 'Model'
				}
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/sys/code_template/edit.jsp",
					title : "添加"+title+"模板",
					width : 700,
					height : 730,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : "add",
							id : row.id,
							projectCode: row.code,
							tmplTypes : tmplTypes,
							type : type
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						refreshAll();
					}
				});
			}
			
			function eidtProject(){
				var row = grid.getSelected();
				if(row){
					mini.open({
						url : "${pageContext.request.contextPath}/apps/default/admin/sys/project/edit.jsp",
						title : "编辑工程模板",
						width : 500,
						height : 450,
						onload : function() {
							var iframe = this.getIFrameEl();
							var data = {
								action : "edit",
								id : row.id
							};
							iframe.contentWindow.SetData(data);
						},
						ondestroy : function(action) {
							refreshAll();
						}
					});
				}else {
					mini.alert("请选择一个工程模板");
				}
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
					title : "编辑"+title+"模板",
					width : 700,
					height : 730,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : "edit",
							id : row.id,
							projectCode: row.code,
							tmplTypes : tmplTypes,
							type : type
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						refreshAll();
					}
				});
			}
			
			function remove(type) {
				var grid = null;
				if('oro' == type) {
					grid = oroGrid;
				}
				else if('dao' == type) {
					grid = daoGrid;
				}
				else if('service' == type) {
					grid = serviceGrid;
				}
				else if('controller' == type) {
					grid = controllerGrid;
				}
				else if("page" == type) {
					grid = pageGrid;
				}
				else if("model" == type) {
					grid = modelGrid;
				}else {
					mini.alert("没有找到对应的模板删除类型");
					return ;
				}
				
				var arrayIds = new Array();
				var rows = grid.getSelecteds();
				for(var i=0;i<rows.length;i++) {
					 arrayIds.push(rows[i].id);
				}
				if(arrayIds == null || arrayIds.length == 0) {
					mini.alert("请选择一个模板记录");
					return ;
				}
		        mini.confirm("确定删除记录？", "确定？",
		                function (action) {
		                    if (action == "ok") {
		                    	var ids = arrayIds.join(",");
		        				$.ajax({
		        					url : "${pageContext.request.contextPath}/code_template/delete?ids="+ids,
		        					dataType: 'json',
		        					type : 'get',
		        					cache : false,
		        					success : function(text) {
		        						refreshAll();
		        					}
		        				});  
		                    } 
		                }
		        );
			}			
		</script>
	</body>
</html>