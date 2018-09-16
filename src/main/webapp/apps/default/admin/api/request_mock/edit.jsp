<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>请求模拟</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
    </style>
</head>
<body>

<form id="edit-form1" method="post" style="border: 0;margin: 0;padding: 0">
<input id="id" name="id" class="mini-hidden" />
<input id="definitionId" name="definitionId" class="mini-hidden" />

<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:530px;">
   
    <div title="接口模拟"  >
	    <div class="mini-toolbar">
	    	<input id="method" name="method" class="mini-combobox" showNullItem="true" nullItemText="请求方式..." emptyText="请求方式" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=http_request_method" style="width: 100px"/>
	        <input id="url" name="url"  class="mini-textbox"  emptyText="请输入URL... " style="width:400px;" />
	        <a class="mini-button" iconCls="icon-ok" plain="false"  onclick="save()">Send</a>
	    </div>
 		
 		<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>请求报文</legend>
		            <div style="padding:5px;">
				    <div class="mini-fit">
			       		<input id="contentRequest" name="contentRequest" class="mini-textarea" style="width:100%;height:150px;border: 0"/>
			       	</div>
		            </div>
		</fieldset>
		            
 		<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>请求报文</legend>
		            <div style="padding:5px;">
				    <div class="mini-fit">
			       		<input id="contentResponse" name="contentResponse" class="mini-textarea" style="width:100%;height:200px;border: 0"/>
			       	</div>
		            </div>
		</fieldset>
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
                	console.log(text);
                	idCt.setValue(text.id);
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
