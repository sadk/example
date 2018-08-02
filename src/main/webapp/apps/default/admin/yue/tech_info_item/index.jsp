<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>技能信息</title>
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
					            	<td style="width:90px;">技能名称：</td>
					                <td style="width:150px;">    
					                    <input style="width:150px;" name="name" class="mini-textbox"  />
					                </td>
					            </tr>
					            <tr>
					            	<td style="width:90px;">编码：</td>
					                <td style="width:150px;">    
					                    <input style="width:150px;" name="code" class="mini-textbox" />
					                </td>
					            </tr>
					            <tr>
					                <td style="width:90px;">备注：</td>
					                <td style="width:150px;">    
					                    <input style="width:150px;" name="remark" class="mini-textbox" />
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
						    <div title="技能信息" refreshOnClick="true">
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
						            <div id="techGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
						                url="${pageContext.request.contextPath}/yue/tech_info/page" idField="id" allowResize="false"
						                sizeList="[5,10,20,50]" pageSize="20" showEmptyText="true" emptyText="暂无待修改信息" sortMode="client" >
						                <div property="columns">
											<div type="checkcolumn" ></div>
											<div field="defName" width="150" headerAlign="center" align="left" allowSort="true">技能定义</div>	
											<div field="name" width="160" headerAlign="center" align="center" allowSort="true">名称</div>										
											<div field="code" width="100" headerAlign="center" align="center" allowSort="true">编码</div>
											
											<div field="appCode" width="80" headerAlign="center" align="left" allowSort="true">应用系统</div>
											<div field="remark" width="300" headerAlign="center" align="left" allowSort="true">说明</div>
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
							
							<div title="技能选项" refreshOnClick="true" name="oroTmpl">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addItem()">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editItem()">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeItem()">删除</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key_oro" name="key_oro" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search()">搜索</a>
						                    </td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="infoGrid" class="mini-datagrid" style="width:100%;height:100%;"
										url="${pageContext.request.contextPath}/code_template/page"  idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据"
										sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" sortMode="client" >
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="name" width="80" headerAlign="center" allowSort="true">技能细项名</div>
											<div field="code" width="80" headerAlign="center" allowSort="true">编码</div>
											 
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
			
			var grid = mini.get("techGrid");
			var infoGrid = mini.get("infoGrid");
			
			grid.load();
 
			function onActivechanged(sender) {
				var index = sender.index;
				var name = sender.name;
			}
			
			function refreshAll() {
				var t = grid.getSelected();
				if(!t)return ;
				var projectCode = t.code;
				
				var tab = tabs2.getActiveTab();
				
			}
			
			grid.on("rowclick", function(e){
				var record = e.record;
				// record.code;
			});
			
 	
		</script>
	</body>
</html>