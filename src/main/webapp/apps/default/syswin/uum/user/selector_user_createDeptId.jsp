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
    <div class="mini-toolbar" style="text-align:center;line-height:30px;text-align: left;" borderStyle="border:0;">
          <label>单据填制人：</label>
          <input id="key" class="mini-textbox" style="width:150px;" onenter="onKeyEnter"/>
          <a class="mini-button" style="width:60px;" onclick="search()">查询</a>
          <span class="separator"></span>
          
          <label>填制人部门：</label>
          <input id="createDeptId" name="createDeptId" class="mini-buttonedit" onbuttonclick="onButtonEdit" emptyText="填制人部门ID"/>  
    </div>
    <div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
			url="${pageContext.request.contextPath}/syswin/user/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" > 
		    <div property="columns">
				<div type="checkcolumn" ></div>
				<div field="userId" width="80" headerAlign="center" allowSort="true" align="center">ID</div>
				<div field="userName" width="160" headerAlign="center" allowSort="true" align="center">姓名</div>
				<div field="loginNo" width="160" headerAlign="center" allowSort="true" align="center">帐号</div>
				<div field="workNo" width="160" headerAlign="center" allowSort="true" align="center">工号</div>
				<div field="statusDesc" width="160" headerAlign="center" allowSort="true" align="center">状态</div>
				<div field="email" width="160" headerAlign="center" allowSort="true" align="center">邮箱</div>
				<div field="mobile" width="160" headerAlign="center" allowSort="true" align="center">手机</div>
				<div field="telephone" width="160" headerAlign="center" allowSort="true" align="center">电话</div>
				<div field="sexDesc" width="80" headerAlign="center" allowSort="true" align="center">性别</div>
				<!-- <div field="appCode" width="160" headerAlign="center" allowSort="true" align="center">系统编码</div>
				<div field="gid" width="160" headerAlign="center" allowSort="true" align="center">全局编码</div> -->
				<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
				<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">更新日期</div>
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
	    
	    
	    
        function onButtonEdit(e) {
            var btnEdit = this;
           
            mini.open({
                url: "${pageContext.request.contextPath}/apps/default/syswin/uum/org/selector_org.jsp",
                title: "选择列表",
                width: 650,
                height: 380,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);    //必须
                        if (data) {
                            btnEdit.setValue(data.id);
                            btnEdit.setText(data.name);
                        }
                    }
                }
            });
        }
        
	    function GetData() {
	    	var isMutil = '${param.isMutil}';
	    	var row = {};
	    	if(isMutil == 'true') {
	    		row = grid.getSelecteds();
		       
	    	}else {
	        	row = grid.getSelected();
	        	//console.log(row);
	        	if(row==null) {
	        		row = {};
	        	}
	        	row.createDeptId =  mini.get("createDeptId").value;
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
