<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>节点用户</title>

    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>  
    
   <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
</head>
<body>
<div class="mini-fit">
<div class="mini-splitter" style="width:100%;height:100%;">
    <div size="240" showCollapseButton="true">
        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
            <span style="padding-left:5px;">分类：</span>
            <input class="mini-textbox" width="120"/>
            <a class="mini-button" iconCls="icon-search" plain="true">查找</a>                  
        </div>
        <div class="mini-fit">
            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/act/category/all?dataType=1" style="width:100%;"
                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" expandOnLoad="true">        
            </ul>
        </div>
    </div>
    <div showCollapseButton="true">
    	<div id="tabs1" class="mini-tabs" activeIndex="0" plain="false" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
  			<div title="流程定义" >
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
							</td>
							<td style="white-space:nowrap;">
		                        <input id="key" name="key" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="search()">查询</a>
		                    </td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
			         <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
			         	 idField="id" allowResize="false" multiSelect="true"  sizeList="[5,10,15,20,50]" 
			         	 pageSize="15" showEmptyText="true" emptyText="暂无查询信息" sortMode="client"
			         	  allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" 
			           	 url="${pageContext.request.contextPath}/act/definition/page" >
			             <div property="columns">
							<div type="checkcolumn" ></div>
							<div field="id" width="180" headerAlign="center" allowSort="true" align="left">流程定义ID</div>
							<div field="version" width="50" headerAlign="center" allowSort="true" align="center">版本号</div>
							<div field="name" width="300" headerAlign="center" allowSort="true" align="left">流程全称</div>
							<!-- 
							<div field="shortName" width="100" headerAlign="center" allowSort="true" align="left">流程简称
								<input property="editor" class="mini-textbox" style="width:100%;" />
							</div>
							 -->
							 
							<!-- 
							<div type="comboboxcolumn" field="enableMobile" width="80" headerAlign="center" align="center" allowSort="true">移端启用
								<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" />
							</div>
							 -->
							 
							<div field="key" width="160" headerAlign="center" allowSort="true" align="left">流程Key</div>
							
							<!-- 
							<div field="deploymentId" width="80" headerAlign="center" allowSort="true" align="center">流程布署ID</div>
							
							<div field="resourceName" width="250" headerAlign="center" allowSort="true" align="left">流程定义XML</div>
							<div field="dgrmResourceName" width="250" headerAlign="center" allowSort="true" align="left">流程资源图</div>
							<div field="description" width="160" headerAlign="center" allowSort="true" align="left">描述</div>
							<div field="suspensionStateDesc" width="160" headerAlign="center" allowSort="true" align="center">挂起状态</div>
							<div field="hasStartFormKeyDesc" width="160" headerAlign="center" allowSort="true" align="center">是否有启动表单</div>
							
							<div field="deployName" width="160" headerAlign="center" allowSort="true" align="left">流程布署名</div>
							 -->
							<div field="tenantId" width="80" headerAlign="center" allowSort="true" align="center">租户ID</div>
							<div field="deployTime"  dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">布署时间</div> 
						</div>
			         </div>
		        </div>
		    </div>
        </div>
    </div> 
</div>
</div>
    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
        <a class="mini-button" style="width:60px;" onclick="onOk()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>
    <script type="text/javascript">
        mini.parse();

        var grid = mini.get("datagrid1");
        var tree = mini.get("tree1");
		
        tree.on("nodeclick",function(e){
        	grid.load({ deployCategory: e.node.code });
		})
		
      	function refresh() {
        	grid.load();
        }
        refresh();
        
		function search(){
			var key = mini.get("key").value;
			grid.load({keyWord: key});
		}


		function GetData() {
			return grid.getSelected();
		}
  
        
        //////////////////////////////////
        function SetData(data) {
			data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
        }
        
	    function CloseWindow(action) {
	        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	        else window.close();
	    }
	
	    function onOk() {
	    	 CloseWindow("ok");
	    }
	    
	    function onCancel() {
	        CloseWindow("cancel");
	    }
    </script>

</body>
</html>