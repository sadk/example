<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>工程管理</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
		<script src="${pageContext.request.contextPath}/scripts/upload/swfupload.js" type="text/javascript"></script>

		<style type="text/css">
			html, body {
				font-size: 12px;
				padding: 0;
				margin: 0;
				border: 0;
				height: 100%;
				overflow: hidden;
			}
		</style>
	</head>
	<body> 
		<form id="edit-form1" method="post" style="height:97%; overflow:auto;">
			<input name="id" class="mini-hidden" />
			<div style="padding:4px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>系统信息</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td style="width:110px;">名称：</td>
								<td style="width:150px;">
								 	<input name="name" id="name" class="mini-textbox"  />
								</td>
								<td style="width:100px;">编码：</td>
								<td style="width:150px;">
									<input name="code" id="code" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>工程结构：</td>
								<td>
								 	<input name="structure" id="structure" class="mini-textbox"  />
								</td>
								<td>工程类型：</td>
								<td>
									<input name="eclipse" id="eclipse" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>DB层：</td>
								<td>
								 	<input name="levelDb" id="levelDb" class="mini-textbox"/>
								</td>
								<td>Web层：</td>
								<td>
									<input name="levelWeb" id="levelWeb" class="mini-textbox"/>
									<!-- 
								 	<input id="status" name="status" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=datasource" value="2"/>
								 	 -->
								</td>
							</tr> 
							<tr>
								<td>视图层：</td>
								<td>
								 	<input name="levelView" id="levelView" class="mini-textbox" />
								</td>
								<td>排序号：</td>
								<td>
								 	<input name="sn" id="sn" class="mini-textbox"  />
								</td>
							</tr> 
							<tr>
								
								<td>备注：</td>
								<td colspan="3">
								 	<input id="remark" name="remark" class="mini-textbox"/>
								</td>
							</tr>
				        </table>
				    </div>
				</fieldset>
				
				
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>模板文件</legend>
		            <div style="padding:5px;">
				        <table>
				         	<tr>
				                <td>工程模板名称:</td>
				            	<td>
				            		<input name="fileNameShow" id="fileNameShow" class="mini-textbox" readonly="readonly" width="320px" emptyText="上传后模板文件名称显示" requried="true"/>
				            		<input name="fileName" id="fileName" class="mini-hidden" width="320px"  />
				            	</td>
				            </tr>
				            <tr>
				            	<td>工程模板路径:</td>
				            	<td>
				            		<input name="filePath" id="filePath" class="mini-textbox" width="270px" readonly="readonly" emptyText="上传后模板文件路径显示" requried="true"/>
				            		<a class="mini-button" onclick="clearFile">清空</a>
				            	</td>
				            </tr>
				            <tr>
				            	<td>文件上传:</td>
				            	<td>
				            		<input id="upfile" class="mini-fileupload" style="width:270px;" name="upfile" limitType="*.*" 
									    flashUrl="${pageContext.request.contextPath}/scripts/upload/swfupload.swf"
									    uploadUrl="${pageContext.request.contextPath}/project/upload"
									    onuploadsuccess="onUploadSuccess" onuploaderror="onUploadError" onuploadcomplete="onUploadcomplete" emptyText="请在这里上传模板文件" />
									 <a class="mini-button" onclick="startUpload">上传</a>
				            	</td>
				            </tr>
				        </table>
				    </div>
				</fieldset>
				
				
			</div>
			<div id="subbtn" style="text-align:center;padding:10px;">
				<a class="mini-button" onclick="onOk" id="btnOk" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="onCancel" id="btnCancel" style="width:60px;">取消</a>
			</div>
		</form>
		<script type="text/javascript">
			mini.parse();

			var form = new mini.Form("edit-form1");
			
			var filePath = mini.get("filePath");
			var fileName = mini.get("fileName");
			var fileNameShow = mini.get("fileNameShow");
			
			var btnTest = mini.get("btnTest");
			var btnOk =  mini.get("btnOk");
			var btnCancel =  mini.get("btnCancel");
			
			function clearFile(){
				filePath.setValue("");
				fileName.setValue("");
				fileNameShow.setValue("");
			}
			
			function onUploadSuccess(e) {
		        mini.alert("上传成功!");
		        var data = mini.decode(e.serverData);
		        
		        if(data && data.length>0) {
		        	for(var i=0;i<data.length;i++){
		        		
			       		fileName.setValue(data[i].originalFileName);
			       		fileNameShow.setValue(data[i].originalFileName);
			       		filePath.setValue(data[i].filePath);
			       		break;
		       		}
		        }
		        this.setText("");
		    }
		    /*
		    function onUploadcomplete(e){
		    	 mini.alert(e);
		    }*/
		    function onUploadError(e) {
		    	mini.alert("上传失败:"+e);    
		    }
		
		    function startUpload() {
		        var fileupload = mini.get("upfile");
		
		        fileupload.startUpload();
		    }
		    
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				$.ajax({
					url : "${pageContext.request.contextPath}/project/save_or_update",
					dataType: 'json',
					type : 'post',
					cache : false,
					data: form.getData(),
					success : function(text) {
						CloseWindow("save");
					}
				});
			}
			

			////////////////////
			//标准方法接口定义
			function SetData(data) {
				data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
				
				 if(data.action == "edit" || data.action=='view') {
					$.ajax({
						url : "${pageContext.request.contextPath}/project/page?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							if(o!=null && o.data!=null && o.data.length>0) {
								o = o.data[0];
							}
							form.setData(o);
							form.setChanged(false);
							fileNameShow.setValue(o.fileName);
							
							if (data.action == 'view') {
								form.setEnabled(false);
								
								
								btnTest.hide();
								btnOk.hide();
								//btnCancel.hide();
								btnCancel.setText("关闭");
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
