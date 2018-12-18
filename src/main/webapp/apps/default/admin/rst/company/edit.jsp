<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>公司信息</title>
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
			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>企业信息</legend>
		            <div style="padding:5px;">
				        <table>
									
									<tr>
										<td>企业简称：</td>
										<td>
											<input id="shortName" name="shortName"  style="width:140px" class="mini-textbox"  emptyText="请输入企业简称"  required="true"/>
										</td>
										<td>企业全称：</td>
										<td>
											<input id="fullName" name="fullName"  style="width:140px" class="mini-textbox"  emptyText="请输入企业名称"  required="true"/>
										</td>
									</tr>
									
									
									<tr>
										<!-- <td>企业编号：</td>
										<td>
											<input id="code" name="code"  style="width:140px" class="mini-textbox"  emptyText="请输入企业编号"  />
										</td> -->
										<td>有效状态：</td>
										<td>
											<input id="status" name="status" class="mini-combobox"  style="width:140px" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dic_active_status" required="true" />
										</td>
									</tr>
									
									
									
									<tr>
										<td>企业介绍：</td>
										<td colspan="3">
											<input id="introduction" name="introduction"  style="width:100%;height:100px;" class="mini-textarea"  emptyText="请输入企业介绍" />
										</td>
									</tr>
				        </table>
				    </div>
				</fieldset>


			<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
	            <legend>认证设置</legend>
	            <div style="padding:5px;">
			        <table>
						<tr>
							<td>人脸识别：</td>
							<td>
								<input id="enableFaceDetect" name="enableFaceDetect" class="mini-combobox"  style="width:140px" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status"/>
							</td>
							<%-- 
							<td>OCR认别:</td>
							<td>
								<input id="enableOcr" name="enableOcr" class="mini-combobox" onvaluechanged="onValueChangedOcr"  style="width:140px" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status"/>
							</td>
							 --%>
						</tr>
						<tr class="ocrTR">
							<td>身份证认证:</td>
							<td>
								<input id="enableOcrIdCard" name="enableOcrIdCard" class="mini-combobox"  style="width:140px" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status"/>
							</td>
							<td>银行卡认证:</td>
							<td>
								<input id="enableOcrBankCard" name="enableOcrBankCard" class="mini-combobox"  style="width:140px" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status"/>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>


				
			<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
	            <legend>考勤设置</legend>
	            <div style="padding:5px;">
			        <table>
								
								<tr>
									<td>考勤月切天数：</td>
									<td>
										<input id="attendanceDay" name="attendanceDay"  style="width:140px" class="mini-spinner" maxValue="1000"  emptyText="5天后不能修改考勤数据"  required="true"/>
									</td>
									<td style="color: green;">例如：5天后不能修改考勤数据</td>
									<td></td>
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
			var enableOcrIdCard = mini.get("enableOcrIdCard");
			var enableOcrBankCard = mini.get("enableOcrBankCard");
			/* 
			$(function(){
				$(".ocrTR").hide();
			})
			
			function onValueChangedOcr(e) {
				var obj = e.sender;
				if("1" != obj.getValue()) {
					$(".ocrTR").hide();
					enableOcrIdCard.setValue("0");
					enableOcrBankCard.setValue("0");
				}else {
					$(".ocrTR").show();
				}
				
			}
			 */
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				$.ajax({
					url : "${pageContext.request.contextPath}/rst/company/save_or_update",
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
						url : "${pageContext.request.contextPath}/rst/company/get_by_id?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							form.setData(o);
							/* 
							if(o.enableOcrIdCard == 1 || o.enableOcrBankCard == 1) {
								mini.get("enableOcr").setValue("1");
								$(".ocrTR").show();
							}
							 */
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
