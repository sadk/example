<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>下一步处理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/upload/swfupload.js" type="text/javascript"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
    </style>
</head>
<body>

<form id="edit-form1" method="post">
<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:530px;">
    
    <div title="录入审批意见"  >
	    <div class="mini-toolbar">
	         <a class="mini-button" plain="false" iconCls="icon-user" onclick="selectUser()">选择录入时的用户</a>
	    </div>
	    <div class="mini-fit">
			<input id="taskId" name="taskId" class="mini-hidden" />
			<input id="loginUserId" name="loginUserId" class="mini-hidden" />
			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>意见内容</legend>
		            <div style="padding:5px;">
				        <table>
								<tr>
									<td>登陆用户ID：</td>
									<td>
										<input id="approveUserId" name="approveUserId" required="true"  class="mini-textbox" readonly="readonly" emptyText="请选择录入用户"/>
									</td>
									<td>登陆用户名：</td>
									<td>
										<input id="approveUserName" name="approveUserName" required="true" class="mini-textbox" readonly="readonly" emptyText="请选择录入用户"/>
									</td>
								</tr>
								 <tr>
									<td>意见：</td>
									<td colspan="3">
										<input id="approveOpinion" name="approveOpinion" width="100%"  class="mini-textarea" emptyText="请输入意见" />
									</td>
									 
								</tr>
								<tr>
									<td>审批动作：</td>
									<td colspan="3">
										<input id="approveAction" name="approveAction" onvaluechanged="approveActionChanged" width="100%" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="btnName" valueField="btnCode" url="${pageContext.request.contextPath}/act/node_button/get_approve_action_list" />
									</td>
								</tr>
								<tr>
									<td>选择节点：</td>
									<td colspan="3">
										<input id="rejectToChooseNodeTaskKey" name="rejectToChooseNodeTaskKey" width="100%" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="taskName" valueField="taskKey" url="${pageContext.request.contextPath}/act/definition/get_node_list?definitionId=${param.definitionId}" />
									</td>
								</tr>
								<tr>
									<td>加签用户：</td>
									<td colspan="3">
										<input id="assignForwardCcUserIdsText" name="assignForwardCcUserIdsText" onclick="selectAssignForwardCcUserIds()" class="mini-textbox" width="100%"   emptyText="请选择用户"/>
										<input id="assignForwardCcUserIds" name="assignForwardCcUserIds" class="mini-hidden" width="100%"/>
										 
									</td>
								</tr>
								<!-- 
								<tr>
									<td>序号:</td>
									<td>
										<input name="sn" id="sn" class="mini-spinner" value="0" minValue="0" maxValue="999999999"  />
									</td>
									<td>备注：</td>
									<td>
										<input id="remark" name="remark"  class="mini-textbox"  emptyText="请输入备注"  onenter="search"/>
									</td>
								</tr>
								 -->
				        </table>
				    </div>
				</fieldset>
				
		        <fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>意见附件</legend>
		            <div style="padding:5px;">
				        <table>
				         	<tr>
				                <td>附件名:</td>
				            	<td colspan="3">
				            		<input name="originalName" id="originalName" class="mini-textbox" width="320px" />
				            	</td>
				            </tr>
				            <tr>
				            	<td>附件路径:</td>
				            	<td colspan="3">
				            		<input class="mini-textbox" width="270px" readonly="readonly" name="path" id="path"/>
				            		<a class="mini-button" onclick="clearFile">清空</a>
				            	</td>
				            </tr>
				            <tr>
				            	<td>附件上传:</td>
				            	<td colspan="3">
				            		<input id="protocolUrlFile" class="mini-fileupload" style="width:270px;" name="upfile" limitType="*.*" 
									    flashUrl="${pageContext.request.contextPath}/scripts/upload/swfupload.swf"
									    uploadUrl="${pageContext.request.contextPath}/attachment/upload"
									    onuploadsuccess="onUploadSuccess" onuploaderror="onUploadError" />
									 <a class="mini-button" onclick="startUpload">上传</a>
				            	</td>
				            </tr>
				        </table>
				    </div>
				</fieldset>
			</div>
		
	    </div>
    </div>
    
    <div title="录入流程变量"  >
	    <div class="mini-toolbar">
	         <a class="mini-button" plain="false" iconCls="icon-collapse" >流程变量JSON格式化</a>
	    </div>
	    <div class="mini-fit">
       		<input id="variables" name="variables" class="mini-textarea" style="width:100%;height:100%;"/>
       	</div>
    </div>
    
    <div title="输出流程日志">
	    <div class="mini-toolbar">
	         <a class="mini-button" iconCls="icon-node" plain="false" >导出日志</a>     
	    </div>
	    <div class="mini-fit">
        	<input id="completeLog" name="completeLog" class="mini-textarea" style="width:100%;height:100%;"/>
        </div>
    </div>
</div>
</form>



<div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
   <a class="mini-button" style="width:100px;" iconCls="icon-download" onclick="complete()">下一步流转</a>
   <a class="mini-button" style="width:60px;" iconCls="icon-close" onclick="onCancel()">关闭</a>
</div>
    
	<script type="text/javascript">
	    mini.parse();
	    
	    var form = new mini.Form("edit-form1");
	    
	    var loginUserId = mini.get("loginUserId");
	    var approveUserId = mini.get("approveUserId");
	    var approveUserName = mini.get("approveUserName");
	    var approveAction = mini.get("approveAction");
	    
	    var assignForwardCcUserIds = mini.get("assignForwardCcUserIds");
		var assignForwardCcUserIdsText = mini.get("assignForwardCcUserIdsText");
		
	    var rejectToChooseNodeTaskKey= mini.get("rejectToChooseNodeTaskKey");
	    
	    var taskId = mini.get("taskId");
	    
		function complete(){
			form.validate();
			if(form.isValid() == false) return;
			
			var data = form.getData();
			console.log(data)
			$.ajax({
	                url: "${pageContext.request.contextPath}/act/runtime/complete",
	                type: "post", dataType: 'json',
	                data : data,
	                success: function (data) {
	                	mini.alert("下一步流转成功");
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                }
	        }); 
		}
		
		function approveActionChanged(e){
           var action = approveAction.getValue();
           if(action == 'button_type_reject_to_choose_node' || action=='button_type_reject_to_choose_node_for_mutil_back') { //单级退回或多级退回
        	   rejectToChooseNodeTaskKey.enable();
           }else{
        	   rejectToChooseNodeTaskKey.disable();
           }
		}
		
		function selectAssignForwardCcUserIds(){
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/syswin/uum/user/selector_user.jsp",
				title : "选择录入的用户",
				width : 600,
				height : 600,
				ondestroy : function(action) {
					if(action == 'ok'){
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.GetDatas();
						if(data && data.length>0){
							var rows = mini.clone(data);
							
							var userNames= new Array();
							var userIds = new Array();
							for (var i=0;i<rows.length;i++) {
								userIds.push(rows[i].userId);
								userNames.push(rows[i].userName);
							}
							
							assignForwardCcUserIds.setValue(userIds.join(","));
							assignForwardCcUserIdsText.setValue(userNames.join(","));
						}
					}
				}
			});	
		}
		
	    function selectUser(){
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/syswin/uum/user/selector_user.jsp",
				title : "选择录入的用户",
				width : 600,
				height : 600,
				ondestroy : function(action) {
					if(action == 'ok'){
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.GetData();
						if(data){
							var row = mini.clone(data);
							approveUserName.setValue(row.userName);
							approveUserId.setValue(row.userId);
							loginUserId.setValue(row.userId);
						}
					}
				}
			});	
	    }
	    
		////////////////////
		//标准方法接口定义
		function SetData(data) {
			var data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
			console.log(data);
			if(data) {
				 taskId.setValue(data.taskId);
			}
		}
		
	    function GetData() {
	        var row = grid.getSelected();
	        return row;
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
