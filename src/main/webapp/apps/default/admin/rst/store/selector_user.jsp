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
			url="${pageContext.request.contextPath}/rst/user/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" > 
		    <div property="columns">
					<div type="checkcolumn" ></div>
					<div field="code" width="160" headerAlign="center" allowSort="true" align="center">编号</div>
					<div field="realName" width="160" headerAlign="center" allowSort="true" align="center">姓名</div>
					<div field="nickName" width="160" headerAlign="center" allowSort="true" align="center">昵称</div>
					<div field="mobile" width="160" headerAlign="center" allowSort="true" align="center">手机</div>
					<div field="wxAccount" width="160" headerAlign="center" allowSort="true" align="center">微信号</div>
					
					<div field="sex" width="160" headerAlign="center" allowSort="true" align="center">性别</div>
					<div field="birthday" dateFormat="yyyy-MM-dd" width="160" headerAlign="center" allowSort="true" align="center">生日</div>
					
					<div field="education" width="160" headerAlign="center" allowSort="true" align="center">学历</div>
					
					<div field="countryName" width="160" headerAlign="center" allowSort="true" align="center">国家</div>
					<div field="provinceName" width="160" headerAlign="center" allowSort="true" align="center">省份</div>
					<div field="cityName" width="160" headerAlign="center" allowSort="true" align="center">城市</div>
					
					<!-- 
						<div field="headImgUrl" width="160" headerAlign="center" allowSort="true" align="center">家庭地址</div> 
					-->
					
					<div field="registrationTime" dateFormat="yyyy-MM-dd" width="160" headerAlign="center" allowSort="true" align="center">注册时间</div>
					<div field="registrationSource" width="160" headerAlign="center" allowSort="true" align="center">注册来源</div>
					<div field="refereeUserCode" width="160" headerAlign="center" allowSort="true" align="center">邀请码</div>
					<div field="seatNumber" width="160" headerAlign="center" allowSort="true" align="center">坐席电话</div>
					<div field="email" width="160" headerAlign="center" allowSort="true" align="center">邮箱</div>
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
	    var userCode = null
	    
	    function SaveData() {
	    	var rows = grid.getSelecteds();
	    	if (rows.length == 0) {
	    		mini.alert("请至少选择一个用户");
	    		return ;
	    	}  
	    	var userCodes = new Array();
	    	for(var i=0;i<rows.length;i++) {
	    		userCodes.push(rows[i].code);
	    	}
	    	
	    	var data = {};
	    	data.storeCode = storeCode;
	    	data.userCodes = userCodes.join(",");
	    	$.ajax({
				'url': "${pageContext.request.contextPath}/rst/store_info/save_manager",
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
			userCode = data.userCode;
			storeCode = data.storeCode;
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
