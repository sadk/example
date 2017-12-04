<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>流程表字段管理</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>

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
			<input id="id" name="id" class="mini-hidden" />
			<div style="padding:4px;padding-bottom:5px;">
		        <fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend >流程定义文件</legend>
		            <div style="padding:5px;">
				        <table>
				         	<tr>
				                <td>文件别名:</td>
				            	<td colspan="3">
				            		<input name="protocolName" id="protocolName" class="mini-textbox" width="320px" emptyText="请输入产品协议名称" />
				            	</td>
				            </tr>
				            <tr>
				            	<td>文件路径:</td>
				            	<td colspan="3">
				            		<input class="mini-textbox" width="270px" readonly="readonly" name="protocolUrl" id="protocolUrl"/>
				            		<a class="mini-button" onclick="clearFile">清空</a>
				            	</td>
				            </tr>
				            <tr>
				            	<td>文件上传:</td>
				            	<td colspan="3">
				            		<input id="protocolUrlFile" class="mini-fileupload" style="width:270px;" name="upfile" limitType="*.*" 
									    flashUrl="${pageContext.request.contextPath}/scripts/upload/swfupload.swf"
									    uploadUrl="${pageContext.request.contextPath}/attachment/upload"
									    onuploadsuccess="onUploadSuccess" onuploaderror="onUploadError" />
									 <a class="mini-button" onclick="startUpload">上传</a>
				            	</td>
				            </tr>
				        </table>
				    </div>
				</fieldset>
			</div>

		</form>
		<script type="text/javascript">
			mini.parse();

			var form = new mini.Form("edit-form1");
			 
			
			
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				
				o = prepareData(o);
				//if(true)return ;
				
				$.ajax({
					url : "${pageContext.request.contextPath}/column/save_or_update",
					dataType: 'json',
					type : 'post',
					cache : false,
					data: o,
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
						url : "${pageContext.request.contextPath}/column/page?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							if(o!=null && o.data!=null && o.data.length>0) {
								o = o.data[0];
							}
							form.setData(o);
							form.setChanged(false);
							
							if (data.action == 'view') {
								form.setEnabled(false);
							}
							
							mini.get("name2").setValue(o.name);
							mini.get("propertyName2").setValue(o.propertyName);
							
							setComboBoxText("primaryKey",o.primaryKey);
							setComboBoxText("primaryKey2",o.primaryKey);
							setComboBoxText("oroColumnType",o.oroColumnType);
							setComboBoxText("searchType",o.searchType);
							setComboBoxText("columnCodegenType",o.columnCodegenType);
							setComboBoxText("javaType",o.javaType);
							
							onColumnCodegenTypeChanged();
							onSelectorDataFromTypeChanged();
							
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
