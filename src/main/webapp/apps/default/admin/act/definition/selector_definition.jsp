<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>流程定义选择器</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
    </style>
</head>
<body>
    <div class="mini-toolbar" style="text-align:center;line-height:30px;" borderStyle="border:0;">
          <label >关键字：</label>
          <input id="key" class="mini-textbox" style="width:150px;" onenter="onKeyEnter"/>
          <a class="mini-button" style="width:60px;" onclick="search()">查询</a>
    </div>
     <div class="mini-fit">
         <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
         	 idField="id" allowResize="false" multiSelect="true"  sizeList="[5,10,20,50]" 
         	 pageSize="20" showEmptyText="true" emptyText="暂无查询信息" sortMode="client"
         	  allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" 
           	 url="${pageContext.request.contextPath}/act/definition/page" >
             <div property="columns">
				<div type="checkcolumn" ></div>
				<div field="id" width="180" headerAlign="center" allowSort="true" align="left">流程定义ID</div>
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
				<div field="version" width="50" headerAlign="center" allowSort="true" align="center">版本号</div>
				
				<div field="deploymentId" width="80" headerAlign="center" allowSort="true" align="center">流程布署ID</div>
				<!-- 
				<div field="resourceName" width="250" headerAlign="center" allowSort="true" align="left">流程定义XML</div>
				<div field="dgrmResourceName" width="250" headerAlign="center" allowSort="true" align="left">流程资源图</div>
				<div field="description" width="160" headerAlign="center" allowSort="true" align="left">描述</div>
				<div field="suspensionStateDesc" width="160" headerAlign="center" allowSort="true" align="center">挂起状态</div>
				<div field="hasStartFormKeyDesc" width="160" headerAlign="center" allowSort="true" align="center">是否有启动表单</div>
				 -->
				<div field="deployName" width="160" headerAlign="center" allowSort="true" align="left">流程布署名</div>
				<div field="deployTime"  dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">布署时间</div> 
				<div field="tenantId" width="80" headerAlign="center" allowSort="true" align="center">租户ID</div>
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
	    grid.load();
	    
	    function GetData() {
	    	var isMutil = '${param.isMutil}';
	    	var row = {};
	    	if(isMutil == 'true') {
	    		row = grid.getSelecteds();
		       
	    	}else {
	        	row = grid.getSelected();
	    	}
	        return row;
	    }
	    
	    function GetDatas() {
	        var rows = grid.getSelecteds();
	        return rows;
	    }
	    
	    function search() {
	        var key = mini.get("key").getValue();
	        grid.load({ keyWord: key });
	    }
	    
	    function onKeyEnter(e) {
	        search();
	    }
	    function onRowDblClick(e) {
	        onOk();
	    }
	    //////////////////////////////////
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
