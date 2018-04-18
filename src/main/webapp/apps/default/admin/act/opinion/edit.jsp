<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>审批意见</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/upload/swfupload.js" type="text/javascript"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
    </style>
</head>
<body>

<form id="edit-form1" method="post">
<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height: 425px;">
    
    
    <div title="审批意见"  >
	    <div class="mini-fit">
			<div style="padding-left:5px;padding-bottom:5px;">
		        <fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>意见内容</legend>
		            <div style="padding:5px;">
				        <table width="100%">
							<tr>
								<td>审批用户：</td>
								<td colspan="3">
									<input id="id" name="id" class="mini-hidden"/>
									<input id="approveUserId" name="approveUserId" class="mini-buttonedit" onbuttonclick="selectUser" width="100%" />
									<input id="approveUserName" name="approveUserName" class="mini-hidden" />
									
									<input id="uploadUserId" name="uploadUserId" class="mini-hidden"/>
									<input id="uploadUserName" name="uploadUserName" class="mini-hidden" />
									
									<input id="businessKey" name="businessKey" class="mini-hidden" />
									<input id="processInstanceId" name="processInstanceId" class="mini-hidden" />	
									<input id="definitionId" name="definitionId" class="mini-hidden" />
									<input id="definitionName" name="definitionName" class="mini-hidden" />
									<input id="definitionKey" name="definitionKey" class="mini-hidden" />
									<input id="opinionId" name="opinionId" class="mini-hidden" />
								</td>
							</tr>
							<tr>
								<td>当前审批节点：</td>
								<td>
									<input id="approveTaskKey" name="approveTaskKey" width="100%" class="mini-combobox"  onvaluechanged="onValueChanged" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="taskNameDesc" valueField="taskKey" url="${pageContext.request.contextPath}/act/definition/get_node_list?definitionId=${param.definitionId}" />
									<input id="approveTaskName" name="approveTaskName" class="mini-hidden" />
								</td>
								<td>审批动作:</td>
								<td>
									<input id="approveAction" name="approveAction" required="true"  onvaluechanged="onActionValueChanged" width="100%" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="btnName" valueField="btnCode" url="${pageContext.request.contextPath}/act/node_button/get_approve_action_list" style="width: 100%"/>
									<input id="approveActionDesc" name="approveActionDesc" class="mini-hidden" />
								</td>
							</tr>
							<tr>
								<td valign="top">审批意见：</td>
								<td colspan="3">
									<input id="approveOpinion" name="approveOpinion" width="100%"  class="mini-textarea" emptyText="请输入意见" />
								</td>
							</tr>
				        </table>
				    </div>
				</fieldset>
				

			</div>
	    </div>
    </div>
    
    <div title="意见附件列表"  id="approveFileList">
	    <div class="mini-toolbar">
	         <a class="mini-button" plain="false" iconCls="icon-remove" onclick="deleteFile()">删除</a>
	    </div>
	    <div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid" style="width:100%;height:50%;" allowResize="false" multiSelect="true" showPager="false"
			url="${pageContext.request.contextPath}/act/approve_opinion_file/all?approveProcessInstanceId=${param.processInstanceId}&approveTaskKey=${param.approveTaskKey}"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" > 
		    <div property="columns">
				<div type="checkcolumn" ></div>
				
				<div field="id" width="60" headerAlign="center" allowSort="true" align="left">ID</div>
				<div field="originalName" width="220" headerAlign="center" allowSort="true" align="left">文件名</div>
				<div field="path" width="160" headerAlign="center" allowSort="true" align="left">FastDFS文件标识</div>
				<div field="uploadUserName" width="160" headerAlign="center" allowSort="true" align="center">上传用户</div>
				
				<div field="businessKey" width="80" headerAlign="center" allowSort="true" align="center">业务主键</div>
				<div field="approveProcessInstanceId" width="120" headerAlign="center" allowSort="true" align="center">流程实例ID</div>
				
				<div field="definitionId" width="220" headerAlign="center" allowSort="true" align="center">流程定义ID</div>
				<div field="definitionKey" width="160" headerAlign="center" allowSort="true" align="left">流程定义Key</div>
				<div field="definitionName" width="160" headerAlign="center" allowSort="true" align="left">流程定义名称</div>
				
				<div field="approveTaskId" width="160" headerAlign="center" allowSort="true" align="center">审批任务ID</div>
				<div field="approveTaskKey" width="160" headerAlign="center" allowSort="true" align="center">审批节点Key</div>
				<div field="approveTaskName" width="160" headerAlign="center" allowSort="true" align="left">审批节点名称</div>
				
				<div field="rejectToChooseNodeTaskKey" width="160" headerAlign="center" allowSort="true" align="center">驳回到选择节点</div>
				<div field="approveResult" width="160" headerAlign="center" allowSort="true" align="center">审批结果</div> 
				<div field="approveOpinion" width="160" headerAlign="center" allowSort="true" align="center">审批意见</div> 
				<div field="approveUserId" width="160" headerAlign="center" allowSort="true" align="center">审批用户ID</div> 
				<div field="approveUserName" width="160" headerAlign="center" allowSort="true" align="center">审批用户名称</div> 
				<div field="approveUserPositionText" width="160" headerAlign="center" allowSort="true" align="left">审批用户岗位</div> 
				<div field="approveUserOrgText" width="160" headerAlign="center" allowSort="true" align="left">审批用户组织</div> 
				
				<div field="approveAction" width="80" headerAlign="center" allowSort="true" align="center">审批动作</div>
				<!-- 
				<div field="assignForwardCcUserIds" width="160" headerAlign="center" allowSort="true" align="center">加签/转发/抄送用户ID</div> 
				 -->
				
				<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
				<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">更新日期</div>
		    </div>
		</div>
		        <fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;" id="fileUploadSet">
		            <legend>意见附件上传</legend>
		            <div style="padding:5px;">
				        <table width="100%">
				        	<!-- 
				         	<tr>
				                <td>附件名:</td>
				            	<td colspan="3">
				            		<input name="originalName" id="originalName" readonly="readonly" class="mini-textbox" width="340px"/>
				            	</td>
				            </tr>
				             -->
				            <tr>
				            	<td>附件上传:</td>
				            	<td colspan="3">
				            		<input id="fileupload1" class="mini-fileupload" style="width:340px;" name="upfile" limitType="*.*" 
									    flashUrl="${pageContext.request.contextPath}/scripts/upload/swfupload.swf"
									    uploadUrl="${pageContext.request.contextPath}/act/approve_opinion/upload"
									    onuploadsuccess="onUploadSuccess" onuploaderror="onUploadError" />
									 <a class="mini-button" onclick="startUpload">上传</a>
				            	</td>
				            </tr>
				            <tr>
				            	<td>附件路径:</td>
				            	<td colspan="3">
				            		<input class="mini-textbox" width="340px" readonly="readonly" name="serverPath" id="serverPath"/>
				            		<a class="mini-button" onclick="clearFile">清空</a>
				            	</td>
				            </tr>
				        </table>
				    </div>
				</fieldset>
       	</div>
    </div>
    
    
 
    

</div>
</form>

<div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
   <a class="mini-button"  iconCls="icon-save" onclick="SaveData()" id="btnSave">保存</a>
   <a class="mini-button" iconCls="icon-close" onclick="onCancel()">关闭</a>
</div>
    
	<script type="text/javascript">
	    mini.parse();
	    var form = new mini.Form("edit-form1");
	    var uploadUserId = mini.get("uploadUserId");
	    var uploadUserName = mini.get("uploadUserName");
	    
	    var approveUserId = mini.get("approveUserId");
	    var approveUserName = mini.get("approveUserName");
	    
	    
	    var fileupload = mini.get("fileupload1");
	    var serverPath = mini.get("serverPath");
	    var originalName = mini.get("originalName");
	    
	    var grid = mini.get("datagrid1");
	    var approveTaskKey = mini.get("approveTaskKey");
	    
	    grid.load();
	    
	    var tab = mini.get("tabs1");
	    
	    function onValueChanged(e) {
	    	//console.log(e)
	    	var taskKey = approveTaskKey.getValue();
	    	//console.log(approveTaskKey.data);
	    	for(var i=0;i<approveTaskKey.data.length;i++) {
	    		if(taskKey == approveTaskKey.data[i].taskKey) {
	    			mini.get("approveTaskName").setValue(approveTaskKey.data[i].taskName);
	    			break;
	    		}
	    	}
	    }
	    
	    function onActionValueChanged(e){
	    	//console.log(e)
	    	var taskKey = approveTaskKey.getValue();
	    	console.log(taskKey)
	    }
	    function deleteFile() {
	    	var rows = grid.getSelecteds();
	    	if(rows.length == 0){
	    		mini.alert("请至少选择一个文件");
	    		return ;
	    	}
	    	var ids = new Array();
	    	for(var i=0;i<rows.length;i++){
	    		ids.push(rows[i].id);
	    	}
	    	
			$.ajax({
				url : "${pageContext.request.contextPath}/act/approve_opinion_file/delete?ids="+ids.join(","),
				dataType: 'json', type : 'post', cache : false,
				success : function(text) {
					grid.reload();
				}
			});
	    }
	    
	    function clearFile() {
	    	serverPath.setValue(null);
	    	fileupload.clear();
	    }
	    
	    function startUpload() {
	    	var data = mini.clone(form.getData());
	    	//data.approveProcessInstanceId = data.processInstanceId;
	    	console.log(data)
	    	fileupload.setPostParam(data);
	        fileupload.startUpload();
	    }
	    
	    function onUploadSuccess(e) {
	    	//console.log(e.serverData);
	    	//mini.alert(typeof e.serverData);
	        mini.alert("上传成功!");
	        serverPath.setValue(mini.decode(e.serverData).serverPath);
	        //originalName.setValue(e.serverData.originalName);
	        
	        grid.load();
	    }
	    
	    function onUploadError(e) {
	    	mini.alert("上传失败：" + e.serverData);
	    }
	    
	    
		function SaveData() {
			var o = form.getData();
			form.validate();
			if(form.isValid() == false) return;
			
			$.ajax({
				url : "${pageContext.request.contextPath}/act/approve_opinion/save_or_update_short",
				dataType: 'json',
				type : 'post',
				cache : false,
				data: o,
				success : function(text) {
					CloseWindow("save");
				}
			});
		}
		
	    function selectUser(){
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/syswin/uum/user/selector_user.jsp",
				title : "选择审批的用户",
				width : 600,
				height : 600,
				ondestroy : function(action) {
					if(action == 'ok'){
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.GetData();
						if(data){
							var row = mini.clone(data);
							
							uploadUserId.setValue(row.userId);
							uploadUserName.setValue(row.userName);
							
							approveUserId.setValue(row.userId);
							approveUserId.setText(row.userName);
							approveUserName.setValue(row.userName);
						}
					}
				}
			});
	    }
	    
		////////////////////
		//标准方法接口定义
		function SetData(data) {
			var data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
			//console.log(data);
			if(data) {
				form.setData(data);
				
				//uploadUserId.setText(data.approveUserName);
				uploadUserId.setValue(data.approveUserId);
				uploadUserName.setValue(data.approveUserName);
				
				
				approveUserId.setValue(data.approveUserId);
				approveUserId.setText(data.approveUserName);
				approveUserName.setValue(data.approveUserName);
				
				if("add" == data.action){
					$("#fileUploadSet").hide();
					//$("#approveFileList").hide();
					tab.removeTab(1);
				}else if("edit" == data.action) {
					$("#fileUploadSet").show();
					//$("#approveFileList").show();
				}
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
	
		function loading(){
			mini.mask({
                el: document.body,
                cls: 'mini-mask-loading',
                html: '加载中...'
            });
		}
		
		function loadingClose(){
			 mini.unmask(document.body);
		}
	</script>
</body>
</html>
