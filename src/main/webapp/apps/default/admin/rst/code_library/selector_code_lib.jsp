<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>简历科技-用户选择器</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
    </style>
</head>
<body>
   <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
    
			<table style="width:100%;">
				<tr>
					<td style="width:100%;">
						<a class="mini-button" iconCls="icon-add" onclick="add()">新增福利</a>
						<!-- <a class="mini-button" iconCls="icon-remove" onclick="remove()">删除福利</a> -->
					</td>
					<td style="white-space:nowrap;">
                        <input id="key" name="key" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
                        <a class="mini-button" style="width:60px;" onclick="search()">查询</a>
                    </td>
				</tr>
			</table>
    </div>
    <div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="true" showPager="false"
			url="${pageContext.request.contextPath}/rst/code_library/list?codeNo=${param.codeNo}"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" > 
		    <div property="columns">
					<div type="checkcolumn" ></div>
					<!-- <div field="code" width="160" headerAlign="center" allowSort="true" align="center">编码</div> -->
					<div field="itemName" width="160" headerAlign="center" allowSort="true" align="left">名称</div>
					<div field="itemNo" width="160" headerAlign="center" allowSort="true" align="center">字典值</div>
					
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
	    
	    function add() {
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/admin/rst/code_library/edit.jsp",
				title : "新增福利",
				width : 490,
				height : 220,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {};
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					grid.reload();
				}
			});
	    }
	    
	    function GetData() {
	    	var row = grid.getSelected();
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
