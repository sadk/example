<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>审核</title>
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
			<input name="id" id="id" class="mini-hidden" />
			<input name="code" id="code" class="mini-hidden" />
			<input name="videoUrl" id="videoUrl" class="mini-hidden"/>
			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>审核</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td>视频编码：</td>
								<td>
									<input id="code" name="code"  style="width:140px" class="mini-textbox"  emptyText=""  onenter="search" />
								</td>
								<td>上架状态：</td>
								<td>
									<input id="status" name="status" class="mini-combobox"  style="width:140px" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_video_status" />
								</td>
							</tr>
							
							<tr>
								<td>审核状态：</td>
								<td>
									<input id="checkStatus" name="checkStatus" class="mini-combobox"  style="width:140px" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_video_check_status" />
								</td>
								<td>审核失败原因:</td>
								<td>
									<input id="reason" name="reason" style="width:140px" class="mini-textbox"  emptyText="请输入不通过原因"  />
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><a href="javascript:video()">点击这里查看视频</a></td>
								<td>用户备注：</td>
								<td>
									<input id="remark" name="remark" style="width:140px" class="mini-textbox"  emptyText="请输入备注"   />
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
			
			function video() {
				var videoUrl = mini.get("videoUrl").getValue();
				if(videoUrl) {
					window.open(videoUrl,"_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width="+screen.width+", height="+screen.height+"");
					return ;
				}
				mini.alert("没有找到视频");
			}
			
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				$.ajax({
					url : "${pageContext.request.contextPath}/rst/personal_video_info/save_or_update_short",
					dataType: 'json', type : 'post',
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
				//console.log(data)
			 	form.setData(data)
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
