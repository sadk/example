<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>图片上传</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<%-- <script src="${pageContext.request.contextPath}/scripts/upload/swfupload.js" type="text/javascript"></script> --%>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
	</style>
</head>
<body>
			   <div class="mini-fit">
					<div style="padding-left:5px;padding-bottom:5px;">
					
							    <form id="edit-form1" action="${pageContext.request.contextPath}/rst/position_definition/upload" method="post" enctype="multipart/form-data">
							    	<table>
						         	<tr>
						            	<td>视频上传:</td>
						            	<td colspan="3">
						            		<input class="mini-hidden" name="positionCode" id="positionCode" value="${positionCode}"/>
						            		<input class="mini-htmlfile" name="dataFile" limitType="*.m4v,*.mp4" style="width:224px;" />
											<a class="mini-button" onclick="startUpload()">上传</a>
						            	</td>
						            </tr>
						       		<tr>
						            	<td>文件路径:</td>
						            	<td colspan="3">
						            		<input class="mini-textbox" width="224px" readonly="readonly" name="serverPath" id="serverPath" value="${serverPath}"/>
						            		<a class="mini-button" onclick="clearFileImport">清空</a>
						            	</td>
						            </tr>
						            </table>
							    </form>
					</div>
					<div id="videoDIV">

					</div>
				</div>
		    

		
		    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:1;">
			   	<a class="mini-button" onclick="onOk" style="width:80px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="onCancel" style="width:80px;">取消</a>
			</div>
	<script type="text/javascript">
	    mini.parse();
	    var form = new mini.Form("edit-form1");
	   	var grid = mini.get("grid");
	   	var serverPath = mini.get("serverPath");
	   	var remark = mini.get("remark");
	   	 
 
		
		function clearFileImport() {
			//form.reset();
			serverPath.setValue("");
		}
		
	    function startUpload(id) {
	    	loading();
	    	
	        $("#edit-form1").submit();
	    }
	    
	    function onUploadSuccessImport(e) {
	    	//console.log(e.serverData);
	    	//mini.alert(typeof e.serverData);
	        mini.alert("上传成功!");
	        mini.get("serverPath").setValue(mini.decode(e.serverData));
	    }
	    
	    function onUploadError(e) {
	    	mini.alert("上传失败：" + e.serverData);
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
	    

		function SaveData() {
			var data = form.getData();
			form.validate();
			if(form.isValid() == false) return;
			
			$.ajax({
				url : "${pageContext.request.contextPath}/rst/position_definition/save_position_video",
				dataType: 'json', type : 'post',
				data: data,
				success : function(text) {
					CloseWindow("ok");
				},
				error: function (jqXHR, textStatus, errorThrown) {
	                mini.showTips({
	                    content: jqXHR.responseText,
	                    state: 'danger',  x: "right",  y: "bottom",
	                    timeout: 10000
	                });
	            }
			});
			
		}

		////////////////////
		//标准方法接口定义
		function SetData(data) {
			data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
			
			form.setData(data);
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

		
		// -------------------------------------
	    function loading(){
	        mini.mask({
	            el: document.body,
	            cls: 'mini-mask-loading',
	            html: '正在传输文件，请稍后 ...'
	        });
		}
		
	    function loadingAutoClose(timeout) {
	        mini.mask({
	            el: document.body,
	            cls: 'mini-mask-loading',
	            html: '正在传输文件，请稍后 ...'
	        });
	        
	        setTimeout(function () {
	            mini.unmask(document.body);
	        }, timeout);
	    }
	    
	    function loadingClose(){
	    	 mini.unmask(document.body);
	    }
	</script>
</body>
</html>
