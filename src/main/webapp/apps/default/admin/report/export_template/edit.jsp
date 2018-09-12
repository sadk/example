<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>报表Excel数据模板文件上传</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/upload/swfupload.js" type="text/javascript"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
	</style>
</head>
<body>
<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:330px;">
    <div title="模板信息"  >
	   <div class="mini-fit">
			<div style="padding-left:5px;padding-bottom:5px;">
		        <fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend >数据导入模板</legend>
		            <div style="padding:5px;">
		            	<form id="edit-form1" method="post">
					        <table>
					         	<tr>
					                <td>名称:</td>
					            	<td colspan="3">
					            		<input id="id" name="id" class="mini-hidden" />
					            		<input id="type" name="type" class="mini-hidden" value="100"/>
					            		<input name="name" id="name" class="mini-textbox" width="320px" emptyText="请输入模板名称" />
					            	</td>
					            </tr>
					       		<tr>
					            	<td>模板路径:</td>
					            	<td colspan="3">
					            		<input class="mini-textbox" width="224px" readonly="readonly" name="path" id="path"/>
					            		<a class="mini-button" onclick="clearFileImport">清空</a>
					            		<a class="mini-button" onclick="download('path','name')">下载</a>
					            	</td>
					            </tr>
					         	<tr>
					            	<td>模板上传:</td>
					            	<td colspan="3">
					            		<input id="importTemplateFile" class="mini-fileupload" style="width:270px;" name="upfile" limitType="*.xlsx;*.xls" 
										    flashUrl="${pageContext.request.contextPath}/scripts/upload/swfupload.swf"
										    uploadUrl="${pageContext.request.contextPath}/report/export_template/upload"
										    onuploadsuccess="onUploadSuccessImport" onuploaderror="onUploadError" />
										 <a class="mini-button" onclick="startUpload('importTemplateFile')">上传</a>
										
					            	</td>
					            </tr>
					        </table>
				        </form>
				    </div>
				</fieldset>
				
				
		        <fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend >数据导出模板</legend>
		            <div style="padding:5px;">
		            	<form id="edit-form2" method="post">
					        <table>
					         	<tr>
					                <td>名称:</td>
					            	<td colspan="3">
					            		<input name="id" id="id1" class="mini-hidden" />
					            		<input name="type" id="type1" class="mini-hidden" value="200"/>
					            		<input name="name" id="name1" class="mini-textbox" width="320px" emptyText="请输入模板名称" />
					            	</td>
					            </tr>
					            <tr>
					            	<td>模板路径:</td>
					            	<td colspan="3">
					            		<input class="mini-textbox" width="224px" readonly="readonly" name="path" id="path1"/>
					            		<a class="mini-button" onclick="clearFileExport">清空</a>
					            		<a class="mini-button" onclick="download('path1','name1')">下载</a>
					            	</td>
					            </tr>
					            <tr>
					            	<td>模板上传:</td>
					            	<td colspan="3">
					            		<input id="exportTemplateFile" class="mini-fileupload" style="width:270px;" name="upfile" limitType="*.xlsx;*.xls" 
										    flashUrl="${pageContext.request.contextPath}/scripts/upload/swfupload.swf"
										    uploadUrl="${pageContext.request.contextPath}/report/export_template/upload"
										    onuploadsuccess="onUploadSuccessExport" onuploaderror="onUploadError" />
										 <a class="mini-button" onclick="startUpload('exportTemplateFile')">上传</a>
					            	</td>
					            </tr>

					        </table>
				        </form>
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
    
	<script type="text/javascript">
	    mini.parse();
	    var form = new mini.Form("edit-form1");
	    var form2 = new mini.Form("edit-form2");
		var definitionId = null;
		
		function download(path,name) { // 服务器端路径，中文文件名 
			var path = mini.get(path);
			var name = mini.get(name);
			if (path.value != '') {
				console.log(path.value)
				window.location = '${pageContext.request.contextPath}/report/export_template/download?path='+path.value+"&name="+name.value;
			}
		}
		
		function clearFileImport() {
			//mini.get("importTemplateFile").clear();
			//mini.get("path").setValue(null);
			form.reset();
		}
		
		function clearFileExport() {
			form2.reset();
		}
		
	    function startUpload(id) {
	        var fileupload = mini.get(id);
	        
	        var paramObject = {};
	        if('importTemplateFile' == id) { //导入=100 导出=200
	        	paramObject.type= 100;
	        } else {
	        	paramObject.type= 200;
	        }
	        fileupload.setPostParam (paramObject);
	        fileupload.startUpload();
	    }
	    
	    function onUploadSuccessImport(e) {
	    	console.log(e.serverData);
	    	//mini.alert(typeof e.serverData);
	        mini.alert("上传成功!");
	        mini.get("path").setValue(mini.decode(e.serverData));
	    }
	    
	    function onUploadSuccessExport(e) {
	    	console.log(e.serverData);
	    	//mini.alert(typeof e.serverData);
	        mini.alert("上传成功!");
	        mini.get("path1").setValue(mini.decode(e.serverData));
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
			
			var data2 = form2.getData();
			form2.validate();
			if(form2.isValid() == false) return;
			
			console.log(definitionId)
			data.definitionId = definitionId;
			data2.definitionId = definitionId;
			
			var arr= new Array();
			arr.push(data);
			arr.push(data2);
			
			var postData = {};
			postData.data = mini.encode(arr);
			$.ajax({
				url : "${pageContext.request.contextPath}/report/export_template/save_or_update_json_4import_or_export",
				dataType: 'json', type : 'post',
				data: postData,
				success : function(text) {
					CloseWindow(data.name+","+data2.name);
				}
			});
		}
		
		////////////////////
		//标准方法接口定义
		function SetData(data) {
			data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
			definitionId = data.definitionId;
			//console.log(data)
			 if(data.action == "edit" || data.action=='view') {
				 /**/
				$.ajax({
					url : "${pageContext.request.contextPath}/report/export_template/list?definitionId=" + definitionId,
					dataType: 'json',
					cache : false,
					success : function(text) {
						var o = mini.decode(text);
						if(o && o!=null && o.length == 2 ) {
							form.setData(o[0]);
							form2.setData(o[1]);
							
							form.setChanged(false);
							form2.setChanged(false);
							if (data.action == 'view') {
								form.setEnabled(false);
								form1.setEnabled(false);
							}
							
						}
					}
				});
			}
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
