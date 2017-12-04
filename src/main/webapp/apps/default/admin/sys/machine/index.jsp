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
		            <span style="padding-left:5px;">名称：</span>
		            <input class="mini-textbox" width="120"/>
		            <a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查找</a>                  
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/application/all_tree" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" >        
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
												<a class="mini-button" iconCls="icon-add" onclick="editPosition('add')">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removePosition()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editPosition('edit')">编辑</a>
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
									        <div name="nodePath" field="nodePath" width="160" headerAlign="center">登陆用户名</div>
									        <div name="code" field="code" width="160" headerAlign="center">登陆密码</div>
									        <div field="orgNodeText" width="260" headerAlign="center">链接</div>
									        <div field="typeDesc" width="80" headerAlign="center" align="center">状态</div>
									        
									        <div field="pid" width="80" headerAlign="center">地址</div>
									        <div field="parentName" width="80" headerAlign="center">端口</div>
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
	        var machineGrid = mini.get("machineGrid");
	        var linkPropGrid = mini.get("linkPropGrid");
	       
	        tree.on("nodeselect", function (e) {
	        	machineGrid.load({ appCode: e.node.code });
	        });
		 
			function search() { // 查询岗位

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