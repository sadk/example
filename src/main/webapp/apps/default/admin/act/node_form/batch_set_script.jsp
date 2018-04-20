<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>批量设置前后置脚本</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
		<style>
			html, body {
				margin: 0;
				padding: 0;
				border: 0;
				width: 100%;
				height: 100%;
				overflow: hidden;
			}
		</style>
	</head>
	<body>
		<div class="mini-splitter" style="width:100%;height:100%;">
			<div size="220" showCollapseButton="true">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <span style="padding-left:5px;">节点类别：</span>
		            <!--  <input class="mini-textbox" width="120"/> -->
		            
		            <input showNullItem="false" width="140" id="taskType" class="mini-combobox" onvaluechanged="onTaskTypeChanged"  url="${pageContext.request.contextPath}/dictionary/option?code=node_task_biz_type" textField="name" valueField="value" />
		            
		           <!--  <a class="mini-button" iconCls="icon-search" plain="true">查找</a>      -->             
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/act/node_button/get_tree_node?definitionId=${param.definitionId}&taskType=" style="width:100%;height: 100%"
		                 showCheckBox="true" checkRecursive="true"  allowSelect="false" enableHotTrack="false" showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" expandOnLoad="0">        
		            </ul>
		        </div>
			</div>
			<div showCollapseButton="true">
				 
				<div class="mini-fit">

					<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:530px;">
					    <div title="前置脚本"  >
						    <div class="mini-toolbar">
						         <a class="mini-button" iconCls="icon-save" plain="false"  onclick="save()">保存前置脚本</a>
						    </div>
						    <div class="mini-fit">
					       		<input id="beforeScript" name="beforeScript" class="mini-textarea" style="width:100%;height:275px;"/>
					        	<div style="width: 100%;border: 0;margin: 0;padding: 0;color: green">
					       			内置变量说明:
						    		 <ul style="margin: 2px;">
						    			<li>loginUser=登陆用户（常用属性:userId、loginNo、userName等）</li>
						    			<li>processInstanceId=流程实例ID，processDefinitionId=流程定义ID</li>
						          		<li>businessKey=业务主键，taskId=当前任务ID</li>
						          		<li>action=审批按钮编码（agree=同意、reject_to_starter=不同意（退回到发起人)、disagree_continue_go（最后一个节点流程继续往下走） 、any_reback=撤回、abort=作废、add_assign=加签、forword_read=转发、copy_send=抄送）</li>
						          	 </ul>
						    	</div>
					       	</div>
					    </div>
					    <div title="后置脚本">
						    <div class="mini-toolbar">
						         <a class="mini-button" iconCls="icon-save" plain="false" onclick="save()">保存后置脚本</a>     
						    </div>
						    <div class="mini-fit">
					        	<input id="afterScript" name="afterScript" class="mini-textarea" style="width:100%;height:275px;"/>
					        	<div style="width: 100%;border: 0;margin: 0;padding: 0;color: green">
					       			内置变量说明:
						    		 <ul style="margin: 2px;">
						    			<li>loginUser=登陆用户（常用属性:userId、loginNo、userName等）</li>
						    			<li>processInstanceId=流程实例ID，processDefinitionId=流程定义ID</li>
						          		<li>businessKey=业务主键，taskId=当前任务ID</li>
						          		<li>action=审批按钮编码（agree=同意、reject_to_starter=不同意（退回到发起人)、disagree_continue_go（最后一个节点流程继续往下走） 、any_reback=撤回、abort=作废、add_assign=加签、forword_read=转发、copy_send=抄送）</li>
						          	 </ul>
						    	</div>
					        </div>
					    </div>
					</div>


				</div>
				
				
			
    <div class="mini-toolbar" style="text-align:center;padding-top:6px;padding-bottom:6px;" borderStyle="border:0;">
    	 <a class="mini-button" iconCls="icon-save" style="width:60px;" onclick="onOk()">保存</a>
        <a class="mini-button" iconCls="icon-remove" style="width:60px;" onclick="onCancel()">关闭</a>
    </div>
			</div>
		</div>
		

		<script type="text/javascript">
		mini.parse();
		
		var taskType = mini.get("taskType");
		var tree = mini.get("tree1");
		
		
		function onTaskTypeChanged (e) {
			var value = taskType.getValue();
 			var url = "${pageContext.request.contextPath}/act/node_button/get_tree_node?definitionId=${param.definitionId}&taskType=" + value
 			//taskType.setUrl(url);
 			tree.load(url);
 			//tree.expand(0)
 			//taskType.select(0);
		}
		function GetData() {
			
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