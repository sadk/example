<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>流程发起初使变量查看</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/upload/swfupload.js" type="text/javascript"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
    </style>
</head>
<body>

<form id="edit-form1" method="post">
<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:530px;">

    <div title="流程发起初始变量 >"  >
	    <div class="mini-toolbar">
	         <a class="mini-button" plain="false" iconCls="icon-collapse" >格式化</a>
	    </div>
	    <div class="mini-fit">
       		<input id="variableJSONInit" name="variableJSONInit" class="mini-textarea" style="width:100%;height:100%;"/>
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
</div>
</form>



<div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
  
   <a class="mini-button" style="width:60px;" iconCls="icon-close" onclick="onCancel()">关闭</a>
</div>
    
	<script type="text/javascript">
	    mini.parse();
	    var form = new mini.Form("edit-form1");
		
	    
		////////////////////
		//标准方法接口定义
		function SetData(data) {
			var data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
			
			if(data && typeof(data.instanceId)!='undefined') {
				$.ajax({
					'url': "${pageContext.request.contextPath}/instance_variable/view_variable?instanceId="+data.instanceId,
					type: 'get',
					dataType:'JSON',
					cache: false,
					async:false,
					success: function (json) {
						if(json){
							mini.get("variableJSONInit").setValue(json.variableJSONStart);
							mini.get("variableJSONStarting").setValue(json.variableJson);
						}
					},
					error : function(data) {
				  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
				  		mini.alert(data.responseText);
					}
				});
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
