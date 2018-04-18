<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>流程定义上传</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/upload/swfupload.js"></script>
		
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
			<input name="pid" id="pid" class="mini-hidden" />
			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>流程文件</legend>
		            <div style="padding:5px;">
				        <table>
				        	<tr>
 								<td>流程名称:</td>
 								<td colspan="3"><input  width="314px" class="mini-textbox"  name="definitionName" id="definitionName" required="true"/></td>
 							</tr>
							<tr>
								<td style="width:100px;">流程分类：</td>
								<td style="width:400px;" colspan="3">
								 	<input width="314px" id="categoryCode" name="categoryCode" class="mini-buttonedit" onbuttonclick="onButtonEdit" required="true"/>    
								</td>
							</tr>
				            <tr>
				            	<td>附件上传:</td>
				            	<td colspan="3">
				            		<input id="fileupload1" class="mini-fileupload" style="width:270px;" name="upfile" limitType="*.*"  
									    flashUrl="${pageContext.request.contextPath}/scripts/upload/swfupload.swf"
									    uploadUrl="${pageContext.request.contextPath}/act/definition/upload"
									    onuploadsuccess="onUploadSuccess" onuploaderror="onUploadError" required="true"/>
									 <a class="mini-button" onclick="startUpload">上传</a>
				            	</td>
				            </tr>
				            
				            <tr>
				            	<td>附件路径:</td>
				            	<td colspan="3">
				            		<input class="mini-textbox" width="270px" readonly="readonly" name="serverPath" id="serverPath" required="true"/>
				            		<a class="mini-button" onclick="clearFile">清空</a>
				            	</td>
				            </tr>

							
				        </table>
				    </div>
				</fieldset>
			</div>
			<div id="subbtn" style="text-align:center;padding:10px;">
				<a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>
			</div>
		</form>
		<script type="text/javascript">
			mini.parse();

			var form = new mini.Form("edit-form1");
		
			function clearFile() {
				form.reset();
			}
		    function startUpload() {
		        var fileupload = mini.get("fileupload1");
		        fileupload.startUpload();
		    }
		    
		    function onUploadSuccess(e) {
		    	//console.log(e.serverData);
		    	//mini.alert(typeof e.serverData);
		        mini.alert("上传成功!");
		        mini.get("serverPath").setValue(mini.decode(e.serverData).serverPath);
  
		    }
		    
		    function onUploadError(e) {
		    	mini.alert("上传失败：" + e.serverData);
		    }
		    
	        function onButtonEdit(e) {
	            var btnEdit = this;
	            mini.open({
	                url: "${pageContext.request.contextPath}/apps/default/admin/act/category/seletor_category.jsp?dataType=1",
	                title: "选择列表",
	                width: 650,
	                height: 380,
	                ondestroy: function (action) {
	                    //if (action == "close") return false;
	                    if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var data = iframe.contentWindow.GetData();
	                        data = mini.clone(data);    //必须
	                        if (data) {
	                            btnEdit.setValue(data.code);
	                            btnEdit.setText(data.name);
	                        }
	                    }

	                }
	            });
	        }
			
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				//mini.alert(mini.encode(form.getData()));
				//if(true) return ;
				$.ajax({
					url : "${pageContext.request.contextPath}/act/definition/deploy",
					dataType: 'json',
					type : 'post',
					cache : false,
					data: form.getData(),
					success : function(text) {
						CloseWindow("save");
					},error : function(data) {
				  		mini.alert(data.responseText);
					}
				});
			}

			////////////////////
			//标准方法接口定义
			function SetData(data) {
				data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
				 if(data.action == "add" ) {
					mini.get("pid").setValue(data.pid);
					return ; 
				 } 
					 
				 if(data.action == "edit" || data.action=='view') {
					$.ajax({
						url : "${pageContext.request.contextPath}/act/category/page?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							if(o!=null && o.data!=null && o.data.length>0) {
								o = o.data[0];
							}
							form.setData(o);
							form.setChanged(false);
							mini.get("categoryCode").setText(o.categoryName);
						 
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
