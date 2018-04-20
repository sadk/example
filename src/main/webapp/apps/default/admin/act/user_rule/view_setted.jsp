<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>查看矩阵规则已设置的流程</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
    </style>
</head>
<body>
   <div class="mini-toolbar" style="padding:1px;border-bottom:0;">
        <table style="width:100%;">
            <tr>
            <td style="width:100%;">
                <a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
                <span class="separator"></span>
                <a class="mini-button" iconCls="icon-reload" plain="true" onclick="refresh()">刷新</a>
            </td>
            <td style="white-space:nowrap;"><label style="font-family:Verdana;">关键字: </label>
                <input class="mini-textbox" name="key" id="key"/>
                <a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查询</a>
                </td>
            </tr>
        </table>
    </div>
    
 
    
    <div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
			url="${pageContext.request.contextPath}/act/user_rule_matrix_flow/page?userRuleId=${param.userRuleId}"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" > 
		    <div property="columns">
				<div type="checkcolumn" ></div>
				<div field="userRuleId" width="60" headerAlign="center" allowSort="true" align="center">规则ID</div>
				<div field="userRuleName" width="160" headerAlign="center" allowSort="true" align="left">规则名称</div>
				<div field="definitionId" width="250" headerAlign="center" allowSort="true" align="left">流程定义ID</div>
				<div field="version" width="60" headerAlign="center" allowSort="true" align="center">流程版本</div>
				<div field="definitionName" width="160" headerAlign="center" allowSort="true" align="left">流程名称</div>
				<div field="taskKey" width="160" headerAlign="center" allowSort="true" align="center">节点Key</div>
				<div field="taskName" width="160" headerAlign="center" allowSort="true" align="left">节点名称</div>
				
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
	    var key = mini.get("key");
	    grid.load();
	    
	    function remove() {
	    	var row = grid.getSelected();
	    	if (!row) {
	    		mini.alert("请选择一条记录");
	    		return ;
	    	}
	    	
			$.ajax({
				url : "${pageContext.request.contextPath}/act/user_rule_matrix_flow/delete",
				type : 'post',data: {ids:row.id},
				success : function(text) {
					grid.reload();
					mini.alert("删除成功");
				},error : function(data) {
			  		mini.alert(data.responseText);
				}
			});
	    }
	    
	    function refresh() {
	    	 grid.reload();
	    }
	    
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
