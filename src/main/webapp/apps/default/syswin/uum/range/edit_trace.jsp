<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>数据预览</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
    </style>
</head>
<body>


<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:530px;">
    <div title="详情" >
	    <div class="mini-toolbar">
			  <!-- 
	          <a class="mini-button" iconCls="icon-node" plain="false" onclick="initButton()">初使化所有按钮</a>    
	          <span class="separator"></span>
	          <a class="mini-button" iconCls="icon-add" plain="false" onclick="add()">添加</a> 
	          
	          <a class="mini-button" iconCls="icon-remove" plain="false" onclick="remove()">清空</a>
	          -->
	          <a class="mini-button" iconCls="icon-node" onclick="viewSql()">预览</a>
	    </div>
	    <div class="mini-fit">
				<!-- 
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>范围内置变量</legend>
		            <div style="padding:5px;">
				       <p> 1.登陆用户变量: ${loginUser} </p>
				       <p> 2.属性说明</p>
				            
				    </div>
				</fieldset>
				 -->
				
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>数据范围定义</legend>
		            <div style="padding:5px;" id="edit-form1">
				        <table>
							<tr>
								<td style="width:100px;">名称：</td>
								<td style="width:150px;">
									<input name="id" id="id" class="mini-hidden" />
								 	<input name="name" id="name" class="mini-textbox"  emptyText="请输入名称"/>
								</td>
								
								<td style="width:100px;">编码：</td>
								<td style="width:150px;">
									<input name="code" id="code" class="mini-textbox" emptyText="请输入编码"/>
								</td>
							</tr>
							<tr>
								<td>范围描述：</td>
								<td colspan="3">
									<input name="description" id="description" class="mini-textbox" style="width: 100%"></input>
								</td>
							</tr>
							<tr >
								<td>范围值：</td>
								<td colspan="3">
									<input name="value" id="value" class="mini-textarea" style="width: 100%;height: 75px"></input>
								</td>
								
							</tr>
							<tr>
								<td style="color: green;">范围值说明:</td>
								<td colspan="3" style="color: green;">
									内置变量--登陆用户 :\${loginUser} <a href="javascript:selectUser()">  点击这里选择登陆用户预览</a>
									<span id="selectedUser" style="color: red"></span> 
								</td>
							</tr>

				        </table>
				    </div>
				</fieldset>
				
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>数据范围SQL值预览结果</legend>
		            <div style="padding:5px;">
				    	<input id="sqlViewResult" class="mini-textarea"  style="width: 520px;height: 170px" />
				    </div>
				</fieldset>
	    </div>  
    </div>
</div>



         
    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
       <a class="mini-button" style="width:60px;" onclick="onOk()">确定</a>
        <a class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>
    
	<script type="text/javascript">
	mini.parse();
	var form = new mini.Form("edit-form1");
	
	var sqlViewResult = mini.get("sqlViewResult");
	
	
	var selectUserId = null;
	
	function viewSql() {
		var data = form.getData();
		if(data && (data.value==null || data.value=='')){
			mini.alert("请输入范围值!!!");
			return;
		}
		
		var dt = {};
		dt.userId = selectUserId;
		dt.value = data.value;
		$.ajax({
			url : "${pageContext.request.contextPath}/syswin/range_value/view_sql",
			dataType: 'json',  type : 'post', cache : false, data: dt,
			success : function(text) {
				sqlViewResult.setValue(text.data);
			},error : function(data) {
				sqlViewResult.setValue(data.responseText);
			}
		});
	}
	
	function selectUser() {
		mini.open({
			url : "${pageContext.request.contextPath}/apps/default/syswin/uum/user/selector_user.jsp",
			title : "请选择登陆用户",
			width : 800, height : 600,
			ondestroy : function(action) {
				if(action == 'ok') {
					var iframe = this.getIFrameEl();
					var data = iframe.contentWindow.GetData();
					if(!data){
						mini.alert("您没有选择用户进行下一步的sql预览!");
						return ;
					}
					
					data = mini.clone(data);
					
					selectUserId = data.userId;
					$("#selectedUser").html("【"+data.userName+"】");
				}
			}
		});
		
	}
	
	function SaveData() {
		var o = form.getData();
		form.validate();
		if(form.isValid() == false) return;
		//mini.alert(mini.encode(form.getData()));
		//if(true) return ;
		$.ajax({
			url : "${pageContext.request.contextPath}/syswin/range/save_or_update",
			dataType: 'json',
			type : 'post',
			cache : false,
			data: form.getData(),
			success : function(text) {
				CloseWindow("save");
			},error : function(data) {
		  		mini.alert(data.responseText);
			}
		});
	}

	////////////////////
	//标准方法接口定义
	function SetData(data) {
		data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用

		 if(data.action == "edit") {
			$.ajax({
				url : "${pageContext.request.contextPath}/syswin/range/page?id=" + data.id,
				dataType: 'json',
				cache : false,
				success : function(text) {
					var o = mini.decode(text);
					if(o!=null && o.data!=null && o.data.length>0) {
						o = o.data[0];
					}
					form.setData(o);
					form.setChanged(false);
					
					/*
					if (data.action == 'view') {
						form.setEnabled(false);
					}
					*/
				}
			});
		}
	}
	
	
	function GetData() {
		var o = form.getData();
		return o;
	}

	function CloseWindow(action) {
		if(action == "close" && form.isChanged()) {
			if(confirm("数据被修改了，是否先保存？")) {
				return false;
			}
		}
		if(window.CloseOwnerWindow)
			return window.CloseOwnerWindow(action);
		else
			window.close();
	}

	function onOk(e) {
		SaveData();
	}

	function onCancel(e) {
		CloseWindow("cancel");
	}
	</script>
</body>
</html>
