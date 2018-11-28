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
		<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="true"
			url="${pageContext.request.contextPath}/rst/company/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
		    <div property="columns">
		        <div type="checkcolumn"></div>
		        <div field="shortName" width="120" headerAlign="center">简称</div>
		        <div field="fullName" width="160" headerAlign="center">全称</div>
		        <div field="code" width="100" headerAlign="center">公司编码</div>
		        <div type="comboboxcolumn" field="status" width="50" headerAlign="center" align="center" allowSort="true">状态
					<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dic_active_status" />
				</div>
		        <div field="introduction" width="200" headerAlign="center" align="left" >介绍</div>
		        <!-- 
		        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
		        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
		         -->
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
	    
	    var storeCode = null;
	    var companyCode = null
	    
	    function SaveData() {
	    	var rows = grid.getSelecteds();
	    	if (rows.length == 0) {
	    		mini.alert("请至少选择厂区");
	    		return ;
	    	}  
	    	var userCodes = new Array();
	    	for(var i=0;i<rows.length;i++) {
	    		userCodes.push(rows[i].code);
	    	}
	    	
	    	var data = {};
	    	data.storeCode = storeCode;
	    	data.companyCodes = userCodes.join(",");
	    	$.ajax({
				'url': "${pageContext.request.contextPath}/rst/store_company/save_manager",
				type: 'post', dataType:'JSON',
				data : data,
				success: function (json) {
					 CloseWindow("ok");
				},
				error : function(data) {
			  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
			  		mini.alert(data.responseText);
				}
			});
	    }
		////////////////////
		//标准方法接口定义
		function SetData(data) {
			data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
			storeCode = data.storeCode;
			companyCode = data.companyCode;
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
	    	SaveData();
	    }
	    function onCancel() {
	        CloseWindow("cancel");
	    }
	
	</script>
</body>
</html>
