<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>数据源选择器</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
    </style>
</head>
<body>
    <div class="mini-toolbar" style="text-align:center;line-height:30px;" borderStyle="border:0;">
          <label >名称：</label>
          <input id="key" class="mini-textbox" style="width:150px;" onenter="onKeyEnter"/>
          <a class="mini-button" style="width:60px;" onclick="search()">查询</a>
    </div>
    <div class="mini-fit">
		<div id="datagrid1" class="mini-treegrid"" style="width:100%;height:100%;"
		showTreeIcon="true" allowResize="true" expandOnLoad="true"
 		treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false" 
		url="${pageContext.request.contextPath}/datasource/all" > 
		    <div property="columns">
		        <div type="indexcolumn"></div>
		        <div name="name" field="name" width="160" >数据源名称</div>
		        <div name="code" field="code" width="80">编码</div>
		        <div name="loginName" field="loginName" width="80">登陆名</div>
		        <div name="status" field="status" width="80" >状态</div>
		        <div name="remark" field="remark" width="120" >备注</div>
		        
		         
		        <div name="sn" field="sn" width="30" align="right">序号</div>
		        <div name="appCode" field="appCode" width="60" align="right">所属应用</div>
		        <div name="createTime" field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center">开始日期</div>
		        <div name="updateTime" field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center">完成日期</div>     
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
	    //动态设置URL
	  // grid.setUrl("${pageContext.request.contextPath}/category/all");
	  //  grid.load();
	
	    function GetData() {
	        var row = grid.getSelected();
	        return row;
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
