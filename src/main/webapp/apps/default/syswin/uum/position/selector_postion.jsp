<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>组织机构-通讯录</title>

    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>  
    
   <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
</head>
<body>
<div style="height:100%; overflow:auto;">
<div class="mini-splitter" style="width:100%;height:90%;">
    <div size="240" showCollapseButton="true">
        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
            <span style="padding-left:5px;">名称：</span>
            <input class="mini-textbox" width="120"/>
            <a class="mini-button" iconCls="icon-search" plain="true">查找</a>                  
        </div>
        <div class="mini-fit">
            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/syswin/org/all" style="width:100%;"
                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false">        
            </ul>
        </div>
    </div>
    <div showCollapseButton="true">
    	<div id="tabs1" class="mini-tabs" activeIndex="0" plain="false" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
  			<div title="部门下的岗位列表" >
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-reload" plain="false">刷新</a>
								<!-- 
								<span class="separator"></span>  
								<a class="mini-button" iconCls="icon-add" plain="false" onclick="add()">分配部门用户</a>
								<a class="mini-button" iconCls="icon-downgrade" plain="false" onclick="remove()">移出部门用户</a>
								 -->
							</td>
							<td style="white-space:nowrap;">
		                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="search()">查询</a>
		                    </td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
		            <div id="grid1" class="mini-datagrid" style="width:100%;height:100%;" multiSelect="true" 
		                borderStyle="border:0;"  url="${pageContext.request.contextPath}/syswin/position/page"
		                showFilterRow="false" allowCellSelect="true" allowCellEdit="true">
		                <div property="columns">
		                	<div type="checkcolumn"></div>
		                	<div name="id" field="id" width="60" headerAlign="center">岗位ID</div>
					        <div name="name" field="name" width="160" headerAlign="center">岗位名称</div>
					        <div field="directId" width="80" headerAlign="center">直属上级ID</div>
					        <div field="directName" width="80" headerAlign="center">直属上级名称</div>
					        <div field="typeDesc" width="80" headerAlign="center">岗位级别</div>
					        
					        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
		                </div>
		            </div>  
		        </div>
		    </div>
        
        </div>
    </div>     
       
</div>

			<div id="subbtn" style="text-align:center;padding:10px;">
				<a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>
			</div>
</div>  
    <script type="text/javascript">
        mini.parse();

        var tree = mini.get("tree1");
        var grid = mini.get("grid1");

        tree.on("nodeselect", function (e) {
        	 grid.load({ orgId: e.node.id });
        });
       
        function search() {
        	var key = mini.get("key2").getValue();
        	grid.load({key: key});
        }
        
        
		function GetData() {
			var o = grid.getSelected();
			return o;
		}
        
		function GetDatas() {
			var o = grid.getSelecteds();
			return o;
		}
		
		function CloseWindow(action) {
			if(action == "close" && form.isChanged()) {
				if(confirm("数据被修改了，是否先保存？")) {
					return false;
				}
			}
			if(window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}
		
		function onOk(e) {
			 CloseWindow("ok");
		}

		function onCancel(e) {
			CloseWindow("cancel");
		}
    </script>

</body>
</html>