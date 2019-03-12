<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>跳转调整</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/upload/swfupload.js" type="text/javascript"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
	</style>
</head>
<body>

<form id="edit-form1" method="post">

<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:530px;">
    
    <div title="基本信息"  >
	    <div class="mini-fit">
			<div style="padding-left:5px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>资讯要素</legend>
		            <div style="padding:5px;">
				        <table border="0px">
				        	<tr>
				        		<td style="width:120px;">标题:</td>
				        		<td style="width: 650px;" colspan="5">
				        			<input name="id" id="id" class="mini-hidden" />
				        			<input name="title" id="title" class="mini-textbox" style="width: 100%;" required="true" />
				        		</td>
				        	</tr>
				        	<tr>
								<td style="width:120px;">作者：</td>
								<td style="width:150px;">
									<input name="author" id="author" class="mini-textbox" required="true"/>
								</td>
								<td style="width:100px;">标签：</td>
								<td style="width:150px;">
								 	<input name="tags" id="tags" class="mini-textbox"  emptyText="多个以逗号分割" />
								</td>
								<td style="width:100px;">来源：</td>
								<td style="width:150px;">
								 	<input id="source" name="source" class="mini-textbox"/>
								</td>
							</tr>
							<tr>
								<td>时间：</td>
								<td>
								 	<input name="time" id="time" class="mini-datepicker" format="yyyy-MM-dd" required="true" />
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
								<td>名称：</td>
								<td>
								 	<input name="name" id="name" class="mini-textbox"  />
								</td>
								<td>编码：</td>
								<td>
									<input name="code" id="code" class="mini-textbox" required="true"/>
								</td>
								<td>所属栏目：</td>
								<td>
								 	<input id="channelIds" name="channelIds" class="mini-buttonedit" required="true"  onbuttonclick="onButtonEdit" />
								 	
								</td>
							</tr>
							<tr>
								<td>序号：</td>
								<td>
								 	<input name="sn" id="sn" class="mini-spinner" value="0" minValue="0" maxValue="999999999"  />
								</td>
								<td>原链接：</td>
								<td>
								 	<input name="sourceUrl" id="sourceUrl" class="mini-textbox" />
								</td>
								<td>生成类型：</td>
								<td>
								 	<input id="generateType" name="generateType" class="mini-combobox"  url="${pageContext.request.contextPath}/dictionary/option?code=generate_type" textField="name" valueField="value" value="0"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" />
								</td>
							</tr>
							<tr>
								<td>是否静态化：</td>
								<td>
								 	<input name="staticFileFlag" id="staticFileFlag" class="mini-combobox" style="width: 100%;" url="${pageContext.request.contextPath}/dictionary/option?code=yes_or_no" />
								</td>
								<td>静态地圵：</td>
								<td colspan="3">
								 	<input id="staticFilePath" name="staticFilePath" class="mini-textbox" style="width: 100%;"/>
								</td>
							</tr>
				        </table>
				    </div>
				</fieldset>

				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>管理信息</legend>
		            <div style="padding:5px;">
		            	 <table border="0px">
				        	<tr>
								<td style="width:120px;">发布日期：</td>
								<td style="width:150px;">
									<input id="publishDate" name="publishDate" class="mini-datepicker" format="yyyy-MM-dd"  emptyText="请输入"/>
								</td>
								<td style="width:100px;">是否启用：</td>
								<td style="width:150px;">
								 	<input id="enable" name="enable" class="mini-combobox"  url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" textField="name" valueField="value" showNullItem="true" nullItemText="请选择..." emptyText="请选择" />
								</td>
								<td style="width:100px;">审核状态：</td>
								<td style="width:150px;">
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
						</table>
		            </div>
		        </fieldset>
				
				
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>摘要</legend>
		            <div style="padding:5px;">
		            	<input id="summary" name="summary" width="100%"  class="mini-textarea" emptyText="请输入摘要" style="height: 120px;"/>
		            </div>
		        </fieldset>
			</div>
	    </div>
    </div>
    
    <div title="资讯内容"  >
	   <div class="mini-fit">
			<div style="padding-left:5px;padding-bottom:5px;">
				<fieldset style="border:solid 0px #aaa;padding:0px; margin-bottom:5px;" id="fd2">
		            <legend></legend>
		            <div style="padding:2px;" class="fieldset-body">
						 
				   		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/ueditor/ueditor.config.js"></script>
					    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/ueditor/ueditor.all.js"> </script>
					    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/ueditor/lang/zh-cn/zh-cn.js"></script>
				   
					    <!-- 加载编辑器的容器 -->
					    <script id="content" name="content" type="text/plain" style="width:738px;height:160px;"></script>
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
		</div>
    </div>
    
</div>
</form>



<div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
   	<a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
	<a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>
</div>
    
	<script type="text/javascript">
	    mini.parse();
	    var form = new mini.Form("edit-form1");
	    
	    var channelIds = mini.get("channelIds");
	    var channelNames = mini.get("channelNames");
	    
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
                        var data = iframe.contentWindow.GetDatas();
                        data = mini.clone(data);    //必须
                        if (data && data.length>0) {
                        	var channelIds = new Array();
                        	var channelNames = new Array();
                        	for(var i=0;i<data.length;i++) {
                        		channelIds.push(data[i].id);
                        		channelNames.push(data[i].name);
                        	}
                            btnEdit.setValue(channelIds.join(","));
                            btnEdit.setText(channelNames.join(","));
                        } else {
                        	mini.alert("请至少选择一个所属栏目");
                        }
                    }
                }
            });

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
			
			data.time = mini.get("time").text;
			data.publishDate = mini.get("publishDate").text;
			data.content = ue.getContent();
			$.ajax({
				url : "${pageContext.request.contextPath}/news/save_or_update",
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
						
						loadContent(data.id)
						loadChannels(data.id)
					}
				});
			}
		}

		function loadContent(newsId){ // 加载新闻内容
			$.ajax({
				url : "${pageContext.request.contextPath}/news/get_content_by_news_id?id=" + newsId,
				dataType: 'json',
				cache : false,
				success : function(text) {
					var o = mini.decode(text);
					if(o!=null) {
						ue.setContent(o.content);
					}
				}
			});
		}
		
		function loadChannels(newsId){ // 加载新闻所属的栏目
			$.ajax({
				url : "${pageContext.request.contextPath}/news/get_channels_by_id?id=" + newsId,
				dataType: 'json',
				cache : false,
				success : function(text) {
					var o = mini.decode(text);
					if(o!=null && o.channelIds) {
						channelIds.setText(o.channelNames);
						channelIds.setValue(o.channelIds);
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
