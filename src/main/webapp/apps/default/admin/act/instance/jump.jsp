<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>跳转调整</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/upload/swfupload.js" type="text/javascript"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
    </style>
</head>
<body>

<form id="edit-form1" method="post">
<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:530px;">
    
    <div title="流程调整"  >
    	<!-- 
	    <div class="mini-toolbar" >
	        <a class="mini-button" plain="false" iconCls="icon-user" onclick="selectUser()">选择登陆用户</a>
	    </div>
	     -->
	    <div class="mini-fit">
			<input id="instanceId" name="instanceId" class="mini-hidden" />
			<input id="processDefinitionId" name="processDefinitionId" class="mini-hidden" />
			
			<div style="padding-left:5px;padding-bottom:5px;">

				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>当前流程实例信息</legend>
		            <div style="padding:5px;">
				        <table>
					        	<tr>
				        			<td>流程标题:</td>
				        			<td colspan="3">
				        				<input id="title" name="title" required="true"  class="mini-textbox"  readonly="readonly" style="width: 100%"/>
				        			</td>
				        		</tr>
					        	<tr>
				        			<td>当前节点:</td>
				        			<td>
				        				<input id="taskName" name="taskName" required="true"  class="mini-textbox"  style="width: 100%"/>
				        			</td>
				        			<td>当前节点key:</td>
				        			<td>
				        				<input id="taskKey" name="taskKey" required="true" class="mini-textbox"  readonly="readonly" style="width: 100%"/>
				        			</td>
				        		</tr>
				        		<tr>
				        			<td>业务主键:</td>
				        			<td>
				        				<input id="businessKey" name="businessKey" required="true" class="mini-textbox"  readonly="readonly" style="width: 100%"/>
				        			</td>
				        			<td>流程版本号:</td>
				        			<td>
				        				<input id="version" name="version" required="true" class="mini-textbox"  readonly="readonly" style="width: 100%"/>
				        			</td>
				        		</tr>
				        		<tr>
				        			<td>填制人:</td>
				        			<td>
				        				<input id="startUserId" name="startUserId" required="true" class="mini-buttonedit" onbuttonclick="selectUser" style="width: 100%"/>
				        			</td>
				        			<td>填制人部门:</td>
				        			<td>
				        				<input id="createDeptId" name="createDeptId" required="true" class="mini-buttonedit" onbuttonclick="selectOrg" style="width: 100%"/>
				        			</td>
				        		</tr>
				        </table>
				    </div>
				</fieldset>
					
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>跳转指令</legend>
		            <div style="padding:5px;">
				        <table>
								<tr>
									<td>跳转的版本：</td>
									<td>
										<input id="processDefinitionIdTarget" name="processDefinitionIdTarget"  onvaluechanged="onProcessDefinitionIdTargetChanged" width="100%" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="versionDesc" valueField="id" url="${pageContext.request.contextPath}/act/definition/list?key=${param.processDefinitionKeyTarget}" />
									</td>
									
									<td>跳转到节点：</td>
									<td>
										<input id="taskKeyTarget" name="taskKeyTarget" width="100%" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="taskNameDesc" valueField="taskKey" url="${pageContext.request.contextPath}/act/definition/get_node_list?definitionId=${param.definitionId}" />
									</td>
								</tr>
								<tr>
									<td>跳转动作：</td>
									<td>
										<input id="approveAction" name="approveAction" width="100%" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="btnName" valueField="btnCode" url="${pageContext.request.contextPath}/act/node_button/get_approve_action_list" style="width: 100%"/>
									</td>
									<td>候选用户：</td>
									<td>
										<input id="taskkeyTargetCandiateUserIds" name="taskkeyTargetCandiateUserIds" class="mini-buttonedit" onbuttonclick="selectUsers" style="width: 100%"/>
									</td>
								</tr>
				        </table>
				    </div>
				</fieldset>
				
 
				
 
				
				<!-- 
		        <fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>意见相关</legend>
		            <div style="padding:5px;">
				        <table width="100%">
								<tr>
									<td>审批用户：</td>
									<td colspan="3">
										<input id="approveUserId" name="approveUserId" class="mini-buttonedit" onbuttonclick="selectUser" style="width: 100%"/>
									</td>
								</tr>
				        	<tr>
								<td valign="top">审批意见：</td>
								<td colspan="3">
									<input id="approveOpinion" name="approveOpinion" width="100%"  class="mini-textarea" emptyText="请输入意见" />
								</td>
							</tr>
				         	<tr>
				                <td>附件名:</td>
				            	<td colspan="3">
				            		<input name="originalName" id="originalName" class="mini-textbox" width="100%" />
				            	</td>
				            </tr>
				            <tr>
				            	<td>附件上传:</td>
				            	<td colspan="3">
				            		<input id="protocolUrlFile" class="mini-fileupload" style="width:340px;" name="upfile" limitType="*.*" 
									    flashUrl="${pageContext.request.contextPath}/scripts/upload/swfupload.swf"
									    uploadUrl="${pageContext.request.contextPath}/attachment/upload"
									    onuploadsuccess="onUploadSuccess" onuploaderror="onUploadError" />
									 <a class="mini-button" onclick="startUpload">上传</a>
				            	</td>
				            </tr>
				            <tr>
				            	<td>附件路径:</td>
				            	<td colspan="3">
				            		<input class="mini-textbox" width="340px" readonly="readonly" name="path" id="path"/>
				            		<a class="mini-button" onclick="clearFile">清空</a>
				            	</td>
				            </tr>
				        </table>
				    </div>
				</fieldset>
				  -->
			</div>
		
	    </div>
    </div>
    
    <div title="管理员录入的变量 >"  >
	    <div class="mini-toolbar">
	         <a class="mini-button" plain="false" iconCls="icon-collapse" >格式化</a>
	    </div>
	    <div class="mini-fit">
       		<input id="variableJSON4Jump" name="variableJSON4Jump" class="mini-textarea" style="width:100%;height:100%;"/>
       	</div>
    </div>
    
    <div title="流程启动聚合变量 >">
	    <div class="mini-toolbar">
	         <a class="mini-button" iconCls="icon-collapse" plain="false" >格式化</a>     
	    </div>
	    <div class="mini-fit">
        	<input id="variableJSONStarting" name="variableJSONStarting" class="mini-textarea" style="width:100%;height:100%;"/>
        </div>
    </div>
    
    <div title="流程发起初始变量 >"  >
	    <div class="mini-toolbar">
	         <a class="mini-button" plain="false" iconCls="icon-collapse" >格式化</a>
	    </div>
	    <div class="mini-fit">
       		<input id="variableJSONInit" name="variableJSONInit" class="mini-textarea" style="width:100%;height:100%;"/>
       	</div>
    </div>
    

</div>
</form>



<div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
   <a class="mini-button" style="width:100px;" iconCls="icon-download" onclick="jumpNext()" id="btnJumpNext">下一步流转</a>
   <a class="mini-button" style="width:60px;" iconCls="icon-close" onclick="onCancel()">关闭</a>
</div>
    
	<script type="text/javascript">
	    mini.parse();
	    
	    var form = new mini.Form("edit-form1");
	    
	    var loginUserId = mini.get("loginUserId");
	    var loginUserName = mini.get("loginUserName");
	    
	    var startUserId = mini.get("startUserId");
	    var createDeptId = mini.get("createDeptId");
	    var taskKey = mini.get("taskKey");
	    var processDefinitionId = mini.get("processDefinitionId");
	    
	    var processDefinitionIdTarget = mini.get("processDefinitionIdTarget"); // 跳转的流程（版本）
	    var taskKeyTarget = mini.get("taskKeyTarget");
	    
		function jumpNext(){
			form.validate();
			if(form.isValid() == false) return;
			
			var data = form.getData();
			if(data.variableJSONInit == ''){
				mini.alert("流程发起初使变量不能为空,请切换Tab页【流程发起初始变量】录入JSON数据");
				return ;
			}
			//console.log(data)
			var defId = processDefinitionId.getValue();
			var jumpDefId = processDefinitionIdTarget.getValue();
			
			if (defId != jumpDefId) {
		        mini.confirm("当前流程实例将会删除，并会新启指定版本流程到指定的节点，是否继续跳转？", "确定？",
	                function (action) {
	                    if (action == "ok") {
	                    	loading();
	                    	doJump(data);
	                    } 
	                }
		        );
		        return ;
			}
			
			mini.confirm("当前流程实例将从【"+data.taskName+"】跳转到【"+taskKeyTarget.getText()+"】节点，是否继续？", "确定？",
	                function (action) {
	                    if (action == "ok") {
	                    	loading();
	                    	doJump(data);
	                    } 
	                }
	        );
			
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
		
		function doJump(data){
			$.ajax({
                url: "${pageContext.request.contextPath}/act/runtime/jump_next",
                type: "post", dataType: 'json',
                data : data,
                success: function (data) {
                	loadingClose();
                	mini.alert("下一步流转成功");
                	
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
       		}); 
		}
		
	    function onProcessDefinitionIdTargetChanged(e) {
    	 	var defId = processDefinitionIdTarget.getValue();
    	 	
    	 	taskKeyTarget.setValue("");
            
            var url = "${pageContext.request.contextPath}/act/definition/get_node_list?definitionId="+defId;
            taskKeyTarget.setUrl(url);
           
          	var data = taskKeyTarget.getData();
          	if(data){
          		for(var i=0;i<data.length;i++){
          			if(taskKey.getValue() == data[i].taskKey){
          				 taskKeyTarget.select(i);
          				 break;
          			}
          		}
          	}
          //	console.log(data)
	    }
	    
	    
	    function selectUser(e){
	    	var btnEdit = this;
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/syswin/uum/user/selector_user.jsp",
				title : "选择登陆用户",
				width : 600,
				height : 600,
				ondestroy : function(action) {
					if(action == 'ok'){
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.GetData();
						if(data){
							var row = mini.clone(data);
							btnEdit.setValue(row.userId);
							btnEdit.setText(row.userName);
						}
					}
				}
			});	
	    }
	    
	    function selectUsers(e){
	    	var btnEdit = this;
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/syswin/uum/user/selector_user.jsp?isMutil=true",
				title : "选择登陆用户",
				width : 600,
				height : 600,
				ondestroy : function(action) {
					if(action == 'ok'){
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.GetData();
						if(data && data.length>0){
							var ids =new Array();
							var names=new Array();
							var rows = mini.clone(data);
							for(var i=0;i<rows.length;i++){
								ids.push(rows[i].userId);
								names.push(rows[i].userName);
							}
							
							btnEdit.setValue(ids.join(","));
							btnEdit.setText(names.join(","));
						}else {
							btnEdit.setValue("");
							btnEdit.setText("");
						}
					}
				}
			});	
	    }
	    
	    function selectOrg(e) {
	    	var btnEdit = this;
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/syswin/uum/org/selector_org.jsp",
				title : "选择填制人部门",
				width : 600,
				height : 600,
				ondestroy : function(action) {
					console.log(action)
					if(action == 'ok'){
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.GetData();
						console.log(data)
						if(data){
							var row = mini.clone(data);
							btnEdit.setValue(row.id);
							btnEdit.setText(row.name);
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
				
				startUserId.setValue(data.startUserId);
				startUserId.setText(data.startUserName);
				
				createDeptId.setValue(data.createDeptId);
				createDeptId.setText(data.createDeptName);
				
				processDefinitionIdTarget.setValue(data.processDefinitionId);
				
				
				var data = taskKeyTarget.getData();
	          	if(data){
	          		for(var i=0;i<data.length;i++){
	          			if(taskKey.getValue() == data[i].taskKey){
	          				 taskKeyTarget.select(i);
	          				 break;
	          			}
	          		}
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
	
	
	</script>
</body>
</html>
