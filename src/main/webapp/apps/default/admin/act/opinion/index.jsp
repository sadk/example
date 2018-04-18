<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>审批意见选择器</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
    </style>
</head>
<body>    
<form id="edit-form1" method="post" style="display: none;">
	<input id="businessKey" name="businessKey" class="mini-hidden" />
	<input id="processInstanceId" name="processInstanceId" class="mini-hidden" />
	<input id="definitionId" name="definitionId" class="mini-hidden" />
	<input id="definitionName" name="definitionName" class="mini-hidden" />
	<input id="definitionKey" name="definitionKey" class="mini-hidden" />
	<input id="approveTaskId" name="approveTaskId" class="mini-hidden" />
	<input id="approveTaskName" name="approveTaskName" class="mini-hidden" />
	<input id="approveTaskKey" name="approveTaskKey" class="mini-hidden" />
</form>

    <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">   
		<table style="width:100%;">
			<tr>
				<td style="width:100%;">
					<a class="mini-button" iconCls="icon-add" onclick="edit('add')">添加</a>
					<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
					<a class="mini-button" iconCls="icon-edit" onclick="edit('edit')">编辑</a>
					<span class="separator"></span>
					<a class="mini-button" iconCls="icon-save" onclick="save">保存</a>
				</td>
				<td style="white-space:nowrap;">
                     <input id="key1" name="key1" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
                     <a class="mini-button" onclick="search()" onenter="onKeyEnter">查询</a>
	            </td>
			</tr>
		</table>
	</div>

    <div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
			url="${pageContext.request.contextPath}/act/approve_opinion/page?processInstanceId=${param.processInstanceId}"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" > 
		    <div property="columns">
				<div type="checkcolumn" ></div>
				
				<div field="id" width="70" headerAlign="center" allowSort="true" align="center">审批意见ID</div>
				
				
				<div field="processInstanceId" width="80" headerAlign="center" allowSort="true" align="center">流程实例ID</div>
				<div field="approveUserName" width="80" headerAlign="center" allowSort="true" align="center">审批用户</div> 
				<div field="approveResult" width="120" headerAlign="center" allowSort="true" align="left">审批动作</div> 
				<div field="approveOpinion" width="260" headerAlign="center" allowSort="true" align="left">审批意见</div>
				<div field="approveTaskName" width="160" headerAlign="center" allowSort="true" align="left">审批节点</div>
				<div field="approveAction" width="220" headerAlign="center" allowSort="true" align="left">审批动作key</div>
				<div field="approveTaskKey" width="100" headerAlign="center" allowSort="true" align="center">审批节点Key</div>
				
				
				
				<div field="businessKey" width="70" headerAlign="center" allowSort="true" align="center">业务主键</div>
				
				<div field="definitionId" width="220" headerAlign="center" allowSort="true" align="center">流程定义ID</div>
				<div field="definitionKey" width="160" headerAlign="center" allowSort="true" align="left">流程定义Key</div>
				<div field="definitionName" width="160" headerAlign="center" allowSort="true" align="left">流程定义名称</div>
				
				<div field="approveTaskId" width="160" headerAlign="center" allowSort="true" align="center">审批任务ID</div>
				
				
				
				<div field="rejectToChooseNodeTaskKey" width="160" headerAlign="center" allowSort="true" align="center">驳回到选择节点</div>
				
				
				<div field="approveUserId" width="160" headerAlign="center" allowSort="true" align="center">审批用户ID</div> 
				
				<div field="approveUserPositionText" width="160" headerAlign="center" allowSort="true" align="left">审批用户岗位</div> 
				<div field="approveUserOrgText" width="160" headerAlign="center" allowSort="true" align="left">审批用户组织</div> 
				
				
				<div field="assignForwardCcUserIds" width="160" headerAlign="center" allowSort="true" align="center">加签/转发/抄送用户ID</div> 
				
				
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
	    
	    var form = new mini.Form("edit-form1");
	    var grid = mini.get("datagrid1");
	    grid.load();
	    
	    function edit(action) {
	    	var data = form.getData();
	    	var row = grid.getSelected();
	    	var approveTaskKey = "";
	    	var processInstanceId = data.processInstanceId;
	    	
	    	if(action == 'edit') {
	    		data.id = row.id;
	    		data.approveUserId = row.approveUserId;
	    		data.approveUserName = row.approveUserName;
	    		data.approveOpinion = row.approveOpinion;
	    		data.approveTaskId = row.approveTaskId;
	    		
	    		if(!row){
	    			mini.alert("请选择一条记录");
	    			return ;
	    		}
	    		
	    		approveTaskKey = row.approveTaskKey;	    		
	    	}

			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/admin/act/opinion/edit.jsp?definitionId="+data.definitionId+"&approveTaskKey="+approveTaskKey+"&processInstanceId="+processInstanceId,
				title : "审批意见调整",
				width : 580,
				height : 495,
				onload : function() {
					var iframe = this.getIFrameEl();
					data.action = action;
					//console.log(data)
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					grid.reload();
				}
			})
	    }
	    
	    function remove(){
	    	var rows = grid.getSelecteds();
	    	if(rows.length == 0){
	    		mini.alert("请至少选择一条记录");
	    		return ;
	    	}
	    	var ids = new Array();
	    	for(var i=0;i<rows.length;i++){
	    		ids.push(rows[i].id);
	    	}
	    	
	    	var data = {};
	    	data.ids = ids.join(",");
			mini.confirm("将删除所选择审批意见，是否继续？", "确定？",
	                function (action) {
	                    if (action == "ok") {
	            			$.ajax({
	                            url: "${pageContext.request.contextPath}/act/approve_opinion/delete",
	                            type: "post", dataType: 'json',
	                            data : data,
	                            success: function (data) {
	                            	mini.alert("删除成功");
	                            	 grid.reload()
	                            },
	                            error: function (jqXHR, textStatus, errorThrown) {
	                                alert(jqXHR.responseText);
	                            }
	                   		}); 
	                    } 
	                }
	        );
	    }
	    
	    
	    function search() {
	        var key = mini.get("key").getValue();
	        grid.load({ key: key });
	    }
	    
		////////////////////
		//标准方法接口定义
		function SetData(data) {
			var data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
			//console.log(data);
			if(data) {
				//console.log(data);
				form.setData(data);
			}
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
