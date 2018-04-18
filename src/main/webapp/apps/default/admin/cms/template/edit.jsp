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

<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:410px;">
    
    <div title="基本信息"  >
	    <div class="mini-fit">
			<div style="padding-left:5px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:1px; margin-bottom:2px;">
		            <legend>基本要素</legend>
		            <div style="padding:5px;">
				        <table border="0px">
				        	<tr>
				        		<td >标题:</td>
				        		<td style="width: 400px;" colspan="3">
				        			<input name="id" id="id" class="mini-hidden" />
				        			<input name="title" id="title" class="mini-textbox" style="width: 374px;" required="true" />
				        		</td>
				        	</tr>
							 
							<tr>
								<td>名称：</td>
								<td>
								 	<input name="name" id="name" class="mini-textbox" required="true"/>
								</td>
								<td>编码：</td>
								<td>
									<input name="code" id="code" class="mini-textbox" required="true"/>
								</td>
							</tr>
							<tr>
				        		<td>所属栏目:</td>
				        		<td>
				        			<input id="channelId" name="channelId" class="mini-buttonedit" required="true"  onbuttonclick="onButtonEdit" />    
				        		</td>
				        		<td>
									是否启用：
								</td>
								<td>
									<input id="enable" name="enable" class="mini-combobox" required="true"  url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" textField="name" valueField="value" value="0"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" />
								</td>
				        	</tr>
				        </table>
				    </div>
				</fieldset>
				
		        <fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend >模板缩略图</legend>
		            <div style="padding:5px;">
				        <table>
				         	<tr>
				                <td>缩略图名:</td>
				            	<td colspan="3">
				            		<input name="thumbnailName" id="thumbnailName" class="mini-textbox" width="320px" emptyText="请输入缩略图名称" />
				            		<input name="pathThumbnailLarge" id="pathThumbnailLarge" class="mini-hidden"/>
				            		<input name="pathThumbnailMiddle" id="pathThumbnailMiddle" class="mini-hidden"/>
				            		<input name="pathThumbnailSmall" id="pathThumbnailSmall" class="mini-hidden"/>
				            	</td>
				            </tr>
				            <tr>
				            	<td>图片路径:</td>
				            	<td colspan="3">
				            		<input class="mini-textbox" width="270px" readonly="readonly" name="pathThumbnail" id="pathThumbnail" required="true"/>
				            		<a class="mini-button" onclick="clearFile">清空</a>
				            	</td>
				            </tr>
				            <tr>
				            	<td>图片上传:</td>
				            	<td colspan="3">
				            		<input id="fileupload1" class="mini-fileupload" style="width:270px;" name="upfile" limitType="*.*" 
									    flashUrl="${pageContext.request.contextPath}/scripts/upload/swfupload.swf"
									    uploadUrl="${pageContext.request.contextPath}/template/upload"
									    onuploadsuccess="onUploadSuccess" onuploaderror="onUploadError" />
									 <a class="mini-button" onclick="startUpload">上传</a>
				            	</td>
				            </tr>
				        </table>
				    </div>
				</fieldset>
			</div>
	    </div>
    </div>
    
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
					    <script id="content" name="content" type="text/plain" style="width:722px;height:230px;"></script>
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
	     
		function clearFile() {
			//form.reset();
			mini.get("pathThumbnail").setValue("");
		}
	    function startUpload() {
	        var fileupload = mini.get("fileupload1");
	        fileupload.startUpload();
	    }
	    
	    function onUploadSuccess(e) {
	        mini.alert("上传成功!");
	        mini.get("pathThumbnail").setValue(mini.decode(e.serverData).pathThumbnail);
	        
	        mini.get("pathThumbnailLarge").setValue(mini.decode(e.serverData).pathThumbnailLarge);
	        mini.get("pathThumbnailMiddle").setValue(mini.decode(e.serverData).pathThumbnailMiddle);
	        mini.get("pathThumbnailSmall").setValue(mini.decode(e.serverData).pathThumbnailSmall);
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
	    
        function onButtonEdit(e) {
            var btnEdit = this;
            mini.open({
                url: "${pageContext.request.contextPath}/apps/default/admin/cms/channel/selector_channel.jsp",
                title: "选择列表",
                width: 900,
                height: 600,
                ondestroy: function (action) {
                   
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);    //必须
                        if (data) {
                            btnEdit.setValue(data.id);
                            btnEdit.setText(data.name);
                        }
                    }

                }
            });

        }

		function SaveData() {
			var data = form.getData();
			form.validate();
			if(form.isValid() == false) return;
			
			//data.time = mini.get("time").text;
			//data.publishDate = mini.get("publishDate").text;
			data.content = ue.getContent();
			$.ajax({
				url : "${pageContext.request.contextPath}/template/save_or_update",
				dataType: 'json',
				type : 'post',
				cache : false,
				data: data,
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
					url : "${pageContext.request.contextPath}/template/page?id=" + data.id,
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
						mini.get("channelId").setValue(o.channelId);
						mini.get("channelId").setText(o.channelName);
						
						ue.setContent(o.content);
						//loadContent(data.id)
					}
				});
			}
		}

		function loadContent(objectId){ // 加载HTML模板内容
			$.ajax({
				url : "${pageContext.request.contextPath}/content/page?objectId=" + objectId+"&type=303&enable=1", //类型:300=新闻 301=博客 302=贴子 303=HTML内容模板 
				dataType: 'json',
				cache : false,
				success : function(text) {
					var o = mini.decode(text);
					
					if(o!=null && o.data!=null && o.data.length>0) {
						o = o.data[0];
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
