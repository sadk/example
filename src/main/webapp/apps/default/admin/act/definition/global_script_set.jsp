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

<form id="edit-form1" method="post" style="border: 0;margin: 0;padding: 0">
<input id="id" name="id" class="mini-hidden" />
<input id="definitionId" name="definitionId" class="mini-hidden" />
<input id="definitionName" name="definitionName" class="mini-hidden" />
<input id="definitionShortName" name="definitionShortName" class="mini-hidden" />
<input id="definitionKey" name="definitionKey" class="mini-hidden" />
<input id="beforeScriptType" name="beforeScriptType" class="mini-hidden" value="1"/>
<input id="afterScriptType" name="afterScriptType" class="mini-hidden" value="1"/>

<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:530px;">
   
    <div title="全局前置脚本"  >
	    <div class="mini-toolbar">
	         <a class="mini-button" iconCls="icon-save" plain="false"  onclick="save()">保存</a>
	          <!--  
	         <span class="separator"></span>
	           -->
	    </div>
 
	    <div class="mini-fit">
       		<input id="beforeScript" name="beforeScript" class="mini-textarea" style="width:100%;height:300px;"/>
       		<div style="width: 100%;border: 0;margin: 0;padding: 0;color: green">
       			内置变量说明:
	    		 <ul style="margin: 2px;">
	    			<li>loginUser=登陆用户（常用属性:userId、loginNo、userName等）</li>
	    			<li>processInstanceId=流程实例ID</li>
	          		<li>processDefinitionId=流程定义ID</li>
	          		<li>businessKey=业务主键</li>
	          		<li>nextTaskCandidateUserIds=下一步流程处理人（多个以逗号分割）</li>
	          	 </ul>
	    	</div>
       	</div>
    </div>
    <div title="全局后置脚本">
	    <div class="mini-toolbar">
	         <a class="mini-button" iconCls="icon-save" plain="false" onclick="save()">保存</a>
	    </div>
	    <div class="mini-fit">
        	<input id="afterScript" name="afterScript" class="mini-textarea" style="width:100%;height:300px;"/>
        	<div style="width: 100%;border: 0;margin: 0;padding: 0;color: green">
       			内置变量说明:
	    		 <ul style="margin: 2px;">
	    			<li>loginUser=登陆用户（常用属性:userId、loginNo、userName等）</li>
	    			<li>processInstanceId=流程实例ID</li>
	          		<li>processDefinitionId=流程定义ID</li>
	          		<li>businessKey=业务主键</li>
	          		<li>nextTaskCandidateUserIds=下一步流程处理人（多个以逗号分割）</li>
	          	 </ul>
	    	</div>
        </div>
    </div>
</div>



         
    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
       <!--  <a class="mini-button" style="width:60px;" onclick="onOk()">确定</a> -->
       <!--  <span style="display:inline-block;width:25px;"></span> -->
        <a class="mini-button" style="width:60px;" onclick="onCancel()">关闭</a>
    </div>
    </form>
	<script type="text/javascript">
	    mini.parse();
	    var form = new mini.Form("edit-form1");
	    var idCt = mini.get("id");
	    var definitionIdCt = mini.get("definitionId");
	  	var definitionNameCt = mini.get("definitionName");
	  	var definitionShortNameCt = mini.get("definitionShortName");
	  	var definitionNameCt = mini.get("definitionName");
	  	var definitionKeyCt = mini.get("definitionKey");
	  	var beforeScriptCt = mini.get("beforeScript");
	  	var afterScriptCt = mini.get("afterScript");
	  	
		 
		function save() {
			var o = form.getData();
			form.validate();
			if(form.isValid() == false) return;
			
            $.ajax({
                url: "${pageContext.request.contextPath}/act/redefinition/save_or_update",
                data: o,  type: "post",
                success: function (text) {
                	mini.alert("保存成功");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
		}
		   
		////////////////////
		//标准方法接口定义
			function SetData(data) {
				data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
				
				definitionIdCt.setValue(data.definitionId);
				definitionNameCt.setValue(data.definitionName);
			  	definitionShortNameCt.setValue(data.definitionShortName);
			  	definitionNameCt.setValue(data.definitionName);
			  	definitionKeyCt.setValue(data.definitionKey);
			  	
				if(data.action == "edit" || data.action=='view') {
					$.ajax({
						url : "${pageContext.request.contextPath}/act/redefinition/get_by_definition_id?definitionId=" + data.definitionId,
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							//form.setData(o);
							if(o){
								idCt.setValue(o.id);
								beforeScriptCt.setValue(o.beforeScript);
								afterScriptCt.setValue(o.afterScript);
								form.setChanged(false);
							}
							
						}
					});
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
