<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>地址信息</title>
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
			<input name="id" name="id" class="mini-hidden" />
			<input name="code" id="code" class="mini-hidden" />
			<div style="padding-left:11px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>地址信息</legend>
		            <div style="padding:5px;">
				        <table>
									
									<tr>
										<td>公司名称：</td>
										<td>
											<input id="companyCode" name="companyCode" class="mini-hidden" />
											<input id="companyName" name="companyName" class="mini-textbox" style="width:150px"  />
										</td>
										<td>省：</td>
										<td>
											<input id="provinceName" name="provinceName" class="mini-hidden" />
											<input id="provinceCode" name="provinceCode"   onvaluechanged="onChangedProvince"  class="mini-combobox"   style="width:150px" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/rst/area/list?parentCodeIsNull=true" required="true" />
										</td>
									</tr>

									<tr>
										<td>市：</td>
										<td>
											<input id="cityName" name="cityName" class="mini-hidden" />
											<input id="cityCode" name="cityCode" onvaluechanged="onChangedCity"  class="mini-combobox"    style="width:150px"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code"  />
										    
										</td>
										<td>区：</td>
										<td>
											<input id="areaName" name="areaName" class="mini-hidden" />
											<input id="areaCode" name="areaCode" class="mini-combobox" onvaluechanged="onChangedArea"  style="width:150px"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code"/>
										</td>
									</tr>
									
									<tr>
										<td>详细地址：</td>
										<td colspan="3">
											<input id="address" name="address"  style="width:100%;height:100px;" class="mini-textarea"  emptyText="请输入详细地址" />
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
			
			var provinceCode = mini.get("provinceCode");
			var provinceName = mini.get("provinceName");
			
			var cityCode = mini.get("cityCode");
			var cityName = mini.get("cityName");
			
			var areaName = mini.get("areaName");
			var areaCode = mini.get("areaCode");
			
			function onChangedProvince(e) {
				cityCode.setValue("");
				areaCode.setValue("");
				
				cityCode.setUrl("${pageContext.request.contextPath}/rst/area/list?parentCode="+e.sender.value);
				provinceName.setValue(e.sender.text);
			}
			
			function onChangedCity(e) {
				areaCode.setValue("");
				areaCode.setUrl("${pageContext.request.contextPath}/rst/area/list?parentCode="+e.sender.value);
				cityName.setValue(e.sender.text);
				
			}

			function onChangedArea(e) {
				areaName.setValue(e.sender.text);
			}
			
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;
				
				$.ajax({
					url : "${pageContext.request.contextPath}/rst/work_address/save_or_update",
					dataType: 'json', type : 'post',
					data: form.getData(),
					success : function(text) {
						CloseWindow("save");
					}
				});
			}

			function loadCompanyName(code) {
				$.ajax({
					url : "${pageContext.request.contextPath}/rst/company/list?code="+code,
					dataType: 'json', type : 'post',
					success : function(text) {
						if(text && text.length == 1) {
							var row = text[0];
							//console.log(row)
							var companyName = mini.get("companyName")
							companyName.setValue(row.shortName);
						}
					}
				});
			}
			
			////////////////////
			//标准方法接口定义
			function SetData(data) {
				data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
				
				 if(data.action == "edit") {
					$.ajax({
						url : "${pageContext.request.contextPath}/rst/work_address/get_by_id?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							if (text) {
								var o = mini.decode(text);
								form.setData(o);
								
								//provinceCode.setUrl("${pageContext.request.contextPath}/rst/area/list?parentCodeIsNull=true");
								cityCode.setUrl("${pageContext.request.contextPath}/rst/area/list?parentCode="+o.provinceCode);
								areaCode.setUrl("${pageContext.request.contextPath}/rst/area/list?parentCode="+o.cityCode);
								
								
								cityCode.setText(o.cityName);
								areaCode.setText(o.areaName);
								
								loadCompanyName(o.companyCode);
							}
						}
					});
				}
				
				if (data.action == 'add') {
					form.setData(data);
					loadCompanyName(data.companyCode);
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
