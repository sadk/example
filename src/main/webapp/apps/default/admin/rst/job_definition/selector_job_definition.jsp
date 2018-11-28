<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>用户选择器</title>
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
		<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="true"
		url="${pageContext.request.contextPath}/rst/job_definition/list"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" showPager="false" >
			<div property="columns">
				<div type="checkcolumn" ></div>
				<div field="id" width="60" headerAlign="center" allowSort="true" align="center" >ID</div>
				<div field="name" width="140" headerAlign="center" allowSort="true" align="left" >名称</div>
				<div field="code" width="140" headerAlign="center" allowSort="true" align="left">编码</div>
				<div field="sn" width="80" headerAlign="center" allowSort="true" align="center">排序号</div>
				<div field="enable" width="80" headerAlign="center" allowSort="true" align="center">是否启用</div>
				
				<!-- <div field="gid" width="160" headerAlign="center" allowSort="true" align="left">全局编码</div> -->
				<div field="createTime" width="150" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
				<div field="updateTime" width="150" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
		
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
	        grid.load({ key: key });
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
