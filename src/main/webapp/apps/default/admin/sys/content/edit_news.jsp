<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>资讯管理</title>
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
		<form id="edit_form" method="post" style="padding:8px;" action="${pageContext.request.contextPath}/action/admin/v3/NewsController/saveOrUpdate" enctype="multipart/form-data">
			<input name="id" class="mini-hidden" />
			<div style="padding-left:11px;padding-bottom:3px;">
				<fieldset style="border:solid 1px #aaa;padding:1px; margin-bottom:3px;">
		            <legend >资讯基本信息</legend>
		            <div style="padding:5px;">
				        <table>
				        	<tr>
				            	<td>标题：</td>
				                <td>
				                	  <input name="title" required="true" width="140px" class="mini-textbox"  emptyText="请输入标题" />
				                </td>
				                <td>摘要：</td>
				            	<td>
				                    <input name="suggest"  width="140px" class="mini-textbox" emptyText="请输入摘要"  />
				                </td>
				                 <td>作者：</td>
				                <td colspan="3">
				                    <input name="author"  width="140px" class="mini-textbox"  emptyText="请输入作者"/>
				                </td>
				            </tr>
							 
				            <tr>
				            	<td>时间：</td>
				                <td>
				                	<input name="actDate"  width="140px" class="mini-datepicker"   emptyText="请输入时间"/>
				                </td>
				            	<td>地点：</td>
				            	<td >
				                    <input name="actAddress"  width="140px" class="mini-textbox" emptyText="请输入地点"/>
				                </td>
				                <td>排序：</td>
				                <td colspan="3">
				                	 <input name="order"  width="140px" class="mini-spinner" value="0" minValue="0" maxValue="999999999" emptyText="请输入排序号"/>
				                </td>
				            </tr>
				           	<tr>
				           		<td>是否显示：</td>
				                <td>
				                    <input name="showFlag" required="true"    width="140px" class="mini-combobox" required="true"  nullItemText="请选择..." emptyText="请选择..." data='[{id:"HD",text:"隐藏"},{id:"DIS",text:"显示"}]' />
				                </td>
				                <td>是否推荐：</td>
				            	<td>
				                    <input name="recFlag" required="true"   width="140px" class="mini-combobox"   nullItemText="请选择..." emptyText="请选择" style="width:140px" data='[{id:"REC",text:"推荐"},{id:"NREC",text:"不推荐"}]' />
				                </td>
				            	<td>资讯类型：</td>
				            	<td>
				                    <input id="type" name="type" required="true"  style="width:140px"  emptyText="请选择" class="mini-combobox"  data='[{id:"HG",text:"活动回顾"},{id:"NK",text:"知认库"},{id:"HN",text:"热点新闻"},{id:"OTHER",text:"其它"}]' />
				                </td>
				                <td>备注：</td>
				                <td>
				                	<input name="remark" style="width:140px" class="mini-textbox" emptyText="请输入备注"/>
				                </td>
				            </tr>
				            <tr>
								<td>图片标题：</td>
								<td><input name="picTitle" class="mini-textbox" style="width:140px"  emptyText="请输图片标题"/></td>
								<td>图片备注：</td>
								<td><input name="picRemark" class="mini-textbox" style="width:140px"   emptyText="请输入图片备注"/></td>
								<td>图片路径：</td>
								<td colspan="3">
									<input name="picUrl" class="mini-htmlfile" style="width: 100%;" limitType="*.jpg;*.png;*.gif;*.jpeg" emptyText="请输选择需要上传的资讯图片"/>
								</td>
							</tr>
				        </table>
				    </div>
				</fieldset>
				
				<fieldset style="border:solid 1px #aaa;padding:5px; margin-bottom:5px;">
		            <legend>资讯内容</legend>
		            <div style="padding:5px;">
	            		
					    <!-- 配置文件 -->
					    <script type="text/javascript" src="${pageContext.request.contextPath}/admin/public/ueditor/ueditor.config.js"></script>
					    
					    <!-- 编辑器源码文件 -->
					    <script type="text/javascript" src="${pageContext.request.contextPath}/admin/public/ueditor/ueditor.all.min.js"></script>
					   
					    <!-- 加载编辑器的容器 -->
					    <script id="container" name="content" type="text/plain"></script>
					    <!-- 实例化编辑器 -->
					    <script type="text/javascript">
					        var ue = UE.getEditor('container',{
					        	imageUrl : "${pageContext.request.contextPath}/图片上传提交地址",
					        	imagePath : "${pageContext.request.contextPath}",
					        	imageFieldName : "file",
					        	fileUrl : "附件上传提交地址"
					        });
					        
					    </script>
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

			var form = new mini.Form("edit_form");

			function SaveData() {
				var o = form.getData();

				form.validate();
				if(form.isValid() == false) return;
				
				$.ajax({
					  url : "${pageContext.request.contextPath}/action/admin/v3/NewsController/saveOrUpdate",
					  type : "POST",
					  data : $('#edit_form').serialize(),
					  success : function(data) {
						  if(msg.status == 1){
								CloseWindow("save");
							}else{
								mini.alert(data.msg);
							}
					  },
					  error : function(data) {
				  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
				  		mini.alert(data.responseText);
					  }
					});
				
				/*
				$("#edit_form").ajaxForm({
					success:function(msg){
						if(msg.status){
							CloseWindow("save");
						}else{
							mini.alert(msg.msg);
						}
					},
					dataType:"json"
				}).submit();*/
			}

			////////////////////
			//标准方法接口定义
			function SetData(dt) {
				if(dt.action == "edit") {
					//跨页面传递的数据对象，克隆后才可以安全使用
					data = mini.clone(dt);
					$.ajax({
						url : "${pageContext.request.contextPath}/action/admin/v3/NewsController/query?id=" + dt.id,
						dataType: 'json',
						success : function(text) {
							var o = mini.decode(text);
							if(o.data && o.data.length==1){
								form.setData(o.data[0]);
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