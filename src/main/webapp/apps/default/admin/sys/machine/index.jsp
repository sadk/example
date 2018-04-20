<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>机器连接管理</title>
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
		            <input id="tenantCode" name="tenantCode" width="180" class="mini-buttonedit" onbuttonclick="onButtonEdit" />                 
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/application/list" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false">        
		            </ul>
		        </div>
		    </div>
			<div showCollapseButton="true">
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						    <div title="服务器列表" refreshOnClick="true">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="edit('add')">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit('edit')">编辑</a>
												<!-- 
												<a class="mini-button" iconCls="icon-node" onclick="edit('view')">查看</a>
												
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
												<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
												<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
												 -->
											</td>
											<td style="white-space:nowrap;">
						                        <input id="keyPosition" name="keyPosition" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="searchPosition()">查询</a>
						                    </td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
									<div id="machineGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" 
										multiSelect="true" allowResize="false" sizeList="[20,50,100,150,200]" pageSize="20"  
					 					url="${pageContext.request.contextPath}/machine/page"> 
									    <div property="columns">
									        <div type="checkcolumn"></div>
									        <div name="id" field="id" width="60" headerAlign="center">编码</div>
									        <div name="name" field="name" width="160" headerAlign="center">名称</div>
									        
									        <div name="userName" field="userName" width="160" headerAlign="center">登陆用户名</div>
									        <div name="userPassword" field="userPassword" width="160" headerAlign="center">登陆密码</div>
									        <div field="address" width="80" headerAlign="center">IP或域名</div>
									        <div field="port" width="80" headerAlign="center">端口</div>
									        
									        <div field="url" width="260" headerAlign="center">链接字符</div>
											
											<div type="comboboxcolumn" field="type" width="160" headerAlign="center" align="left" allowSort="true">服务器类型
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=datasource" />
											</div>
											
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
							
							<div title="连接属性" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addRole()">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeRole()">删除</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="linkPropGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" 
										multiSelect="true" allowResize="false" sizeList="[20,50,100,150,200]" pageSize="20"  
					 					url="${pageContext.request.contextPath}/syswin/role/page">
										<div property="columns">
									        <div type="checkcolumn" ></div>
									        <div name="id" field="id" width="160" headerAlign="center">角色ID</div>
									        <div name="name" field="name" width="160" headerAlign="center">角色名称</div>
									        
									        <div field="categoryName" width="80" headerAlign="center">角色分类</div>
									        <div field="typeDesc" width="80" headerAlign="center">角色类型</div>
									        
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
	        var grid = mini.get("machineGrid");
	        var linkPropGrid = mini.get("linkPropGrid");
	       
	        grid.load();
	        
	        tree.on("nodeselect", function (e) {
	        	grid.load({ appCode: e.node.code });
	        });
		 
			function edit(action) {
				var row = grid.getSelected();
				if(typeof(action) == 'undefined') {
					action = "edit";
				}
				
				if(action == 'edit' && !row) {
					mini.alert("请选择一条记录");
					return ;
				}
					mini.open({
						url : "${pageContext.request.contextPath}/apps/default/admin/sys/machine/edit.jsp",
						title : "编辑",
						width : 490,
						height : 350,
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
				 
			}	
	        
	        function onButtonEdit(e) {
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