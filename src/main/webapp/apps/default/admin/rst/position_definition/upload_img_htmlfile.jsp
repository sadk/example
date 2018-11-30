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
					
							    <form id="edit-form1" action="${pageContext.request.contextPath}/rst/position_definition/upload_logo" method="post" enctype="multipart/form-data">
							    	<table>
						         	<tr>
						            	<td>图片上传:</td>
						            	<td colspan="3">
						            		<input class="mini-hidden" name="type" id="type" value="${type}"/>
						            		<input class="mini-hidden" name="positionCode" id="positionCode" value="${positionCode}"/>
						            		<input class="mini-htmlfile" name="dataFile" limitType="*.png,*.jpg,*.gif,*.jpeg" style="width:224px;" />
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
					
					<img src="${serverPath }" alt="" width="350" height="300" onclick="openOriginal('${serverPath }')"/>
					
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
	   	 
	   	function openOriginal(url) {//全屏打开原图
        	var url= "${pageContext.request.contextPath}/apps/default/admin/rst/company/img_show.jsp?url="+url ;
        	window.open(url,"_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width="+screen.width+", height="+screen.height+"");
        }
		
		function clearFileImport() {
			serverPath.setValue("");
		}
		
	    function startUpload(id) {
	    	loading();
	        $("#edit-form1").submit();
	    }
	    
	    function onUploadSuccessImport(e) {
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
                html: '文件正在传输，请稍后...'
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
				url : "${pageContext.request.contextPath}/rst/position_definition/update_position_img",
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
	
	</script>
</body>
</html>
