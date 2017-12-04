<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>新闻管理</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
		<!-- -->
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
			
			fieldset {
				border: solid 1px #aaa;
			}
			
			.hideFieldset {
				border-left: 0;
				border-right: 0;
				border-bottom: 0;
			}
			
			.hideFieldset .fieldset-body {
				display: none;
			}
</style>
	</head>
	<body> 
		<form id="edit-form1" method="post" style="height:97%; overflow:auto;">
			<input name="id" class="mini-hidden" />
			<div style="padding:4px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;" id="fd1">
		            <legend><label><input type="checkbox" checked id="checkbox1" onclick="toggleFieldSet(this, 'fd1')" hideFocus/>基本信息</label></legend>
		            <div style="padding:5px;" class="fieldset-body">
				        <table>
							<tr>
								<td style="width:120px;">名称：</td>
								<td style="width:150px;">
								 	<input name="name" id="name" class="mini-textbox"  />
								</td>
								<td style="width:100px;">编码：</td>
								<td style="width:150px;">
									<input name="code" id="code" class="mini-textbox" />
								</td>
								<td style="width:100px;">标题：</td>
								<td style="width:150px;">
								 	<input name="title" id="title" class="mini-textbox"  />
								</td>
							</tr>
							<tr>
								<td>摘要：</td>
								<td>
									<input name="summary" id="summary" class="mini-textbox" />
								</td>
								<td>作者：</td>
								<td>
									<input name="author" id="author" class="mini-textbox" />
								</td>
								<td>标签：</td>
								<td>
								 	<input name="tags" id="tags" class="mini-textbox"  />
								</td>
							</tr>
							<tr>
								<td>发布日期：</td>
								<td>
									<input id="publishDate" name="publishDate" class="mini-datepicker"  emptyText="请输入"/>
								</td>
								<td>是否启用：</td>
								<td>
								 	<input id="enable" name="enable" class="mini-combobox"  url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" textField="name" valueField="value" showNullItem="true" nullItemText="请选择..." emptyText="请选择" />
								</td>
								<td>审核状态：</td>
								<td>
								 	<input name="statusAuth" id="statusAuth" class="mini-combobox"  url="${pageContext.request.contextPath}/dictionary/option?code=auth_status" textField="name" valueField="value"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" />
								</td>
							</tr>
							
							<tr>
								<td>置顶天数：</td>
								<td>
								 	<input name="topDays" id="topDays" class="mini-spinner" minValue="0" maxValue="100"/>
								</td>
								<td>回复数：</td>
								<td>
								 	<input name="cntReplay" id="cntReplay" class="mini-spinner" value="0" minValue="0" maxValue="999999999"  />
								</td>
								<td>阅读数：</td>
								<td>
								 	<input id="cntView" name="cntView"  class="mini-spinner" value="0" minValue="0" maxValue="999999999"  />
								</td>
							</tr>
							<tr>
								<td>时间：</td>
								<td>
								 	<input name="time" id="time" class="mini-datepicker"  />
								</td>
								<td>地点：</td>
								<td>
								 	<input id="address" name="address" class="mini-textbox"/>
								</td>
								<td>人物：</td>
								<td>
								 	<input name="person" id="person" class="mini-textbox"  />
								</td>
							</tr>
							<tr>
								<td>来源：</td>
								<td>
								 	<input id="source" name="source" class="mini-textbox"/>
								</td>
								<td>原链接：</td>
								<td>
								 	<input name="person" id="person" class="mini-textbox" />
								</td>
								<td>生成类型：</td>
								<td>
								 	<input id="generateType" name="generateType" class="mini-combobox"  url="${pageContext.request.contextPath}/dictionary/option?code=generate_type" textField="name" valueField="value" value="0"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" />
								</td>
							</tr>
							<tr>
								<td>生成静态文件：</td>
								<td>
								 	<input name="staticFileFlag" id="person" class="mini-textbox"   />
								</td>
								<td>静态文件地圵：</td>
								<td>
								 	<input id="staticFilePath" name="generateType" class="mini-textbox"/>
								</td>
								<td>序号：</td>
								<td>
								 	<input name="sn" id="sn" class="mini-spinner" value="0" minValue="0" maxValue="999999999"  />
								</td>
							</tr>
							<tr>
								<td>备注：</td>
								<td colspan="5">
								 	<input id="remark" name="remark" class="mini-textbox"/>
								</td>
							</tr>
				        </table>
				    </div>
				</fieldset>
				
				<fieldset style="border:solid 1px #aaa;padding:5px; margin-bottom:5px;" id="fd2">
		            <legend><label><input type="checkbox" checked id="checkbox2" onclick="toggleFieldSet(this, 'fd2')" hideFocus/>资讯内容</label></legend>
		            <div style="padding:5px;" class="fieldset-body">
						 
				   		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/ueditor/ueditor.config.js"></script>
					    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/ueditor/ueditor.all.min.js"> </script>
					    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/ueditor/lang/zh-cn/zh-cn.js"></script>
				   
					    <!-- 加载编辑器的容器 -->
					    <script id="content" name="content" type="text/plain" style="width:738px;"></script>
					    <!-- 实例化编辑器 -->
					    <script type="text/javascript">
					        var ue = UE.getEditor('content');
					    </script>
		            </div>
		        </fieldset>
		        
		        <fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend >自定义资讯图片或附件信息</legend>
		            <div style="padding:5px;">
				        <table>
				         	<tr>
				                <td>附件别名:</td>
				            	<td colspan="3">
				            		<input name="protocolName" id="protocolName" class="mini-textbox" width="320px" emptyText="请输入产品协议名称" />
				            	</td>
				            </tr>
				            <tr>
				            	<td>附件路径:</td>
				            	<td colspan="3">
				            		<input class="mini-textbox" width="270px" readonly="readonly" name="protocolUrl" id="protocolUrl"/>
				            		<a class="mini-button" onclick="clearFile">清空</a>
				            	</td>
				            </tr>
				            <tr>
				            	<td>附件上传:</td>
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
			<div id="subbtn" style="text-align:center;padding:10px;">
				<a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>
			</div>
		</form>
		<script type="text/javascript">
			mini.parse();

			var form = new mini.Form("edit-form1");
			
			function toggleFieldSet(ck, id) {
	            var dom = document.getElementById(id);
	            dom.className = !ck.checked ? "hideFieldset" : "";
	        }
			
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				$.ajax({
					url : "${pageContext.request.contextPath}/news/save_or_update",
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
						url : "${pageContext.request.contextPath}/news/page?id=" + data.id,
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
