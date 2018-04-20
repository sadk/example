<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>模板内容</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/upload/swfupload.js" type="text/javascript"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
	</style>
</head>
<body>

<form id="edit-form1" method="post">
<input name="id" id="id" class="mini-hidden"/>
<input name="type" id="type" class="mini-hidden"/>
<input name="title" id="title" class="mini-hidden"/>
<input name="objectId" id="objectId" class="mini-hidden"/>

<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:530px;">
    <div title="模板内容"  >
	   <div class="mini-fit">
			<div>
				<fieldset style="border:solid 0px #aaa;padding:2px; margin-bottom:2px;" id="fd2">
		            <legend></legend>
		            <div class="fieldset-body">
						 
				   		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/ueditor/ueditor.config.js"></script>
					    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/ueditor/ueditor.all.min.js"> </script>
					    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/ueditor/lang/zh-cn/zh-cn.js"></script>
				   
					    <!-- 加载编辑器的容器 -->
					    <script id="content" name="content" type="text/plain" style="width:100%;height:350px;"></script>
					    <!-- 实例化编辑器 -->
					    <script type="text/javascript">
					        var ue = UE.getEditor('content');
					    </script>
		            </div>
		        </fieldset>
			</div>
		</div>
    </div>
</div>

<div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
   	<a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
	<a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>
</div>
</form>
	<script type="text/javascript">
	    mini.parse();
	    var form = new mini.Form("edit-form1");
	    
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
	    


		function SaveData() {
			var data = form.getData();
			form.validate();
			if(form.isValid() == false) return;
			
			//data.time = mini.get("time").text;
			//data.publishDate = mini.get("publishDate").text;
			data.content = ue.getContent();
			$.ajax({
				url : "${pageContext.request.contextPath}/content/save_or_update",
				dataType: 'json',
				type : 'post',
				cache : false,
				data: data,
				success : function(text) {
					CloseWindow("ok");
				}
			});
		}

		////////////////////
		//标准方法接口定义
		function SetData(data) {
			data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
			//console.log(data)
			if(data.action == "edit" || data.action=='view') {
				loadContent(data.id);
				return ;
			}
			
			if(data.action == "add") {
				form.setData(data);
				ue.setContent(data.content);
				return ;
			}
		}

		function loadContent(id){ // 加载HTML内容模板
			$.ajax({
				url : "${pageContext.request.contextPath}/content/page?id=" + id, //类型: 
				dataType: 'json',
				cache : false,
				success : function(text) {
					var o = mini.decode(text);
					
					if(o!=null && o.data!=null && o.data.length>0) {
						o = o.data[0];
						form.setData(o);
						ue.setContent(o.content);
					}
				}
			});
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
