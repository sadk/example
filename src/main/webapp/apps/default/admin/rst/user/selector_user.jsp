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
    <div class="mini-toolbar" style="text-align:center;line-height:30px;" borderStyle="border:0;">
          <label >关键字：</label>
          <input id="key" class="mini-textbox" style="width:150px;" onenter="onKeyEnter"/>
          <a class="mini-button" style="width:60px;" onclick="search()">查询</a>
    </div>
    <div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="${param.multiSelect}" autoLoad="true"
			url="${pageContext.request.contextPath}/rst/user/page?entryStatus=${param.entryStatus}"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" > 
		    <div property="columns">
					<div type="checkcolumn" ></div>
						<div field="code" width="160" headerAlign="center" allowSort="true" align="left">编号</div>
						<div field="realName" width="160" headerAlign="center" allowSort="true" align="left">姓名</div>
						<div field="nickName" width="160" headerAlign="center" allowSort="true" align="left">昵称</div>
						<div field="mobile" width="160" headerAlign="center" allowSort="true" align="center">手机</div>
						<div type="comboboxcolumn" field="entryStatus" width="80" headerAlign="center" align="center" allowSort="true">在职状态
							<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_jianli_tech_entry_status" />
						</div>
						<div field="dependCompanyName" width="180" headerAlign="center" allowSort="true" align="left">入职工厂</div>
						
						
						<div type="comboboxcolumn" field="sex" width="80" headerAlign="center" align="center" allowSort="true">性别
							<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=sex" />
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
