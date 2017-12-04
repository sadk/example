<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单表</title>
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
			<div size="270" showCollapseButton="true">
			    <div class="mini-panel" showToolbar="true" showHeader="false" style="width:100%;height:100%;">
				   
				    <div style="padding-left:3px;padding-bottom:5px;">
				            <div style="padding:5px;" id="form1">
						        <table>
						        
						
										<tr>
											<td>订单编号-对外显示：</td>
											<td>
												<input id="orderNo" name="orderNo"  style="width:140px" class="mini-textbox"  emptyText="请输入订单编号-对外显示"  onenter="search"/>
											</td>
										</tr>
										
										
										
										<tr>
											<td>订单编码：</td>
											<td>
												<input id="code" name="code"  style="width:140px" class="mini-textbox"  emptyText="请输入订单编码"  onenter="search"/>
											</td>
										</tr>
										
										
										
										<tr>
											<td>下单用户ID：</td>
											<td>
												<input id="userId" name="userId"  style="width:140px" class="mini-textbox"  emptyText="请输入下单用户ID"  onenter="search"/>
											</td>
										</tr>
										
										
										
										<tr>
											<td>下单用户名：</td>
											<td>
												<input id="userName" name="userName"  style="width:140px" class="mini-textbox"  emptyText="请输入下单用户名"  onenter="search"/>
											</td>
										</tr>
										
										
										
										
										<tr>
											<td>订单的状态: 0=下单成功 1=支付成功 2=支付失败 ：</td>
											<td>
												<input name="status" id="status" class="mini-spinner" class="mini-datepicker" minValue="0" maxValue="999999999"  />
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
						    <div title="主表-订单表" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" onclick="add()" iconCls="icon-add">添加</a>
												<a class="mini-button" onclick="remove()" iconCls="icon-remove" >删除</a>
												<a class="mini-button" onclick="edit()" iconCls="icon-edit" >编辑</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
												<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
												<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
												
											</td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
						            <div id="tableGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="false"
						                url="${pageContext.request.contextPath}/order/page" idField="id" allowResize="false"
						                sizeList="[5,10,20,50]" pageSize="20" showEmptyText="true" emptyText="暂无数据" sortMode="client">
						                
						                <div property="columns">
											<div type="checkcolumn" ></div>
											<div field="orderNo"  width="160" headerAlign="center" allowSort="true" align="center">订单编号-对外显示</div>
											<div field="code"  width="160" headerAlign="center" allowSort="true" align="center">订单编码</div>
											<div field="total"  width="160" headerAlign="center" allowSort="true" align="center">总价</div>
											<div field="userId"  width="160" headerAlign="center" allowSort="true" align="center">下单用户ID</div>
											<div field="userName"  width="160" headerAlign="center" allowSort="true" align="center">下单用户名</div>
											<div field="status"  width="160" headerAlign="center" allowSort="true" align="center">订单的状态: 0=下单成功 1=支付成功 2=支付失败 </div>
											<div field="gid"  width="160" headerAlign="center" allowSort="true" align="center"></div>
											<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
											<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center"></div>
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
						<div id="tabs2" contextMenu="#refreshTabMenu2" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							
							<div title="234234" refreshOnClick="true">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="add()">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key_0" name="key_0" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search()">搜索</a>
						                    </td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="columnGrid" class="mini-datagrid" style="width:100%;height:100%;" sortMode="client"
										url="${pageContext.request.contextPath}/shop_order_item/page"  idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据" sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" 
										 allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" >
										<div property="columns">
											<div type="checkcolumn" ></div>
											
											<div field="orderNo"  width="160" headerAlign="center" allowSort="true" align="center">订单编号-对外显示</div>
											<div field="code"  width="160" headerAlign="center" allowSort="true" align="center">订单编码</div>
											<div field="total"  width="160" headerAlign="center" allowSort="true" align="center">总价</div>
											<div field="userId"  width="160" headerAlign="center" allowSort="true" align="center">下单用户ID</div>
											<div field="userName"  width="160" headerAlign="center" allowSort="true" align="center">下单用户名</div>
											<div field="status"  width="160" headerAlign="center" allowSort="true" align="center">订单的状态: 0=下单成功 1=支付成功 2=支付失败 </div>
											<div field="gid"  width="160" headerAlign="center" allowSort="true" align="center"></div>
											<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
											<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center"></div>
											
										</div>
									</div>
								</div>
							</div>
							
							
						</div>
						<!-- 
						<ul id="refreshTabMenu2" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
							<li onclick="reloadTab">
								刷新标签页
							</li>
						</ul>
						 -->
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			mini.parse();
			
			var form = new mini.Form("#form1");
			var grid = mini.get("tableGrid");
			var columnGrid = mini.get("columnGrid");
			
			columnGrid.load();
			
			var tabs = mini.get("tabs1");
			
			grid.load();
			
			function clear() {
				 form.clear();
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
			
			
			grid.on("rowclick", function(e){
				var record = e.record;
				columnGrid.load({dbName:record.dbName,tableName:record.tableName,version:record.version});
			});
			
			function search() {
				var data = form.getData();
				grid.load(data);
			}
			
			
			function add() {
				
			}  
			
			function edit(){
				
			}
			
			function remove() {
				// 删除字段定义
				var row = columnGrid.getSelecteds();
				if(row) {
					var idArr = new Array();
					for(var i=0;i<row.length;i++) {
						idArr.push(row[i].id);
					}
					mini.confirm("确定删除选中的字段定义?", "确定？",
							function (action) {
								if (action == "ok") {
									$.ajax({
										'url': "${pageContext.request.contextPath}/column/delete?ids="+idArr.join(","),
										type: 'post',
										dataType:'JSON',
										cache: false,
										async:false,
										success: function (json) {
											mini.alert("删除成功");
											columnGrid.reload();
										},
										error : function(data) {
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
			
		</script>
	</body>
</html>