<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>报表字段管理</title>
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
		            <legend>DB字段</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td style="width:120px;">表名：</td>
								<td style="width:150px;">
								 	<input name="tableName" id="tableName" class="mini-textbox"  />
								</td>
								
								<td style="width:120px;">字段：</td>
								<td style="width:150px;">
								 	<input id="name" name="name" class="mini-textbox" onvaluechanged="onNameValueChanged"/>
								</td>
							</tr>
							<tr>
								<td>是否是键：</td>
								<td>
								 	<input id="primaryKey" name="primaryKey"   onvaluechanged="onPrimaryKeyChanged" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/dictionary/option?code=yes_or_no" />
								</td>
								<td>字段注释：</td>
								<td>
								 	<input name="comment" id="comment" class="mini-textbox" />
								</td>
							</tr> 
							<tr>
								<td>字段类型：</td>
								<td>
								 	<input id="dbType" name="dbType"  class="mini-textbox"  />
								</td>
								<td>版本号：</td>
								<td>
								 	<input id="version" name="version"  class="mini-textbox"  />
								</td>
							</tr> 
				        </table>
				    </div>
				</fieldset>
				
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>SQL映射</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td style="width:120px;">列名：</td>
								<td style="width:150px;">
								 	<input name="name2" id="name2" class="mini-textbox" readonly="readonly" />
								</td>
								
								<td style="width:120px;">实体属性名：</td>
								<td style="width:150px;">
								 	<input id="propertyName" name="propertyName" class="mini-textbox"/>
								</td>
							</tr>
							<tr>
								<td>是否主键：</td>
								<td>
								 	<input id="primaryKey2" name="primaryKey2"  onvaluechanged="onPrimaryKey2Changed" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/dictionary/option?code=yes_or_no" />
								</td>
								<td>映射类型：</td>
								<td>
								 	<input name="oroColumnType" id="oroColumnType" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/dictionary/option?code=oro_column_type"/>
								</td>
							</tr> 
							
				        </table>
				    </div>
				</fieldset>
				
				
				
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>代码生成</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td style="width:120px;">JAVA字段名：</td>
								<td style="width:150px;">
								 	<input name="propertyName2" id="propertyName2" class="mini-textbox"  />
								</td>
								
								<td style="width:120px;">数据类型：</td>
								<td style="width:150px;">
								 	<input name="javaType" id="javaType"   class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/dictionary/option?code=java_type" />
								</td>
							</tr>
							
							<tr>
								<td>是否查询条件：</td>
								<td>
								 	<input id="searchType" name="searchType" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/dictionary/option?code=yes_or_no" />
								</td>
								
								<td>表单控件：</td>
								<td>
								 	<input id="columnCodegenType" name="columnCodegenType" onvaluechanged="onColumnCodegenTypeChanged" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=column_codegen_type" />
								</td>
							</tr> 
							
							
							
							
							
							<tr>
								<td>显示格式：</td>
								<td>
								 	<input id="columnCodegenFormat" name="columnCodegenFormat" class="mini-textbox" />
								</td>
								<td>表单分组：</td>
								<td>
								 	<input id="columnCodegenGroupCode" name="columnCodegenGroupCode"  class="mini-textbox" />
								</td>
							</tr> 
							<tr>
								<td>序号:</td>
								<td><input name="sn" id="sn" class="mini-spinner" /></td>
								<td>备注：</td>
								<td colspan="3">
								 	<input name="remark" id="remark" class="mini-textbox" />
								</td>
							</tr>
							
				        </table>
				    </div>
				</fieldset>
				
				
				
				
				
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;" id="controlConfig">
		            <legend>控件配置</legend>
		            <div style="padding:5px;">
				        <table>
				        	<tr id="dsTR">
								<td style="width:120px;">数据来源类型:</td>
								<td style="width:150px;"><input name="selectorDataFromType" id="selectorDataFromType" onvaluechanged="onSelectorDataFromTypeChanged" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=selector_data_form_type" /></td>
								
								<td style="width:120px;"><span class="selectorMultilSelect">是否可多选:</span></td>
								<td style="width:150px;"><span class="selectorMultilSelect"><input id="selectorMultilSelect" name="selectorMultilSelect" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=yes_or_no" /></span></td>
								
							</tr>
							<tr id="dbTR">
								<td>数据源:</td>
								<td><input id="dataSourceCode" name="dataSourceCode" value="localhost" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/datasource/all" /></td>
								<td>数据库:</td>
								<td><input id="dbName" name="dbName" value="test" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="name" url="${pageContext.request.contextPath}/datasource/get_database_list" /></td>
							</tr>
							<tr id="displayColumnTR">
								<td>显示文本列:</td>
								<td>
									<input id="selectorTextCols" name="selectorTextCols" class="mini-buttonedit" onbuttonclick="onSelectorTextColsButtonEdit"/>
								</td>
								<td>显示的值列:</td>
								<td>
									<input id="selectorValueCols" name="selectorValueCols" class="mini-buttonedit" onbuttonclick="onSelectorValueColsButtonEdit"/>    
								</td>
								
							</tr>
							
							<tr id="selectorDataFromLableTR">
								<td valign="top"><span id="selectorDataFromLable">数据来源：</span></td>
								<td colspan="3"><input id="selectorDataFrom" name="selectorDataFrom" class="mini-textarea" style="width:100%"/></td>
							</tr>
							
				        </table>
				    </div>
				</fieldset>
				
				
			</div>
			<div id="subbtn" style="text-align:center;padding:10px;">
				<a class="mini-button" onclick="onCheckSQL"   iconCls="icon-split">检测SQL</a>
				<a class="mini-button" onclick="onCheckJSON"   iconCls="icon-split">检测JSON</a>
				<a class="mini-button" onclick="onCheckJavaScript"   iconCls="icon-split">检测JavaScript</a>
				
				<a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>
			</div>
		</form>
		<script type="text/javascript">
			mini.parse();

			var form = new mini.Form("edit-form1");
			var columnCodegenFormat = mini.get("columnCodegenFormat"); // 显示格式
			var columnCodegenGroupCode = mini.get("columnCodegenGroupCode");//表单分组 
			var columnCodegenType = mini.get("columnCodegenType");// 表单控件 
			
			// 控件配置
			var selectorDataFromType = mini.get("selectorDataFromType");//数据来源类型
			var selectorMultilSelect = mini.get("selectorMultilSelect");//是否可多选
			
			var dataSourceCode = mini.get("dataSourceCode"); //数据源
			var dbName = mini.get("dbName"); // 数据库
			
			var selectorTextCols = mini.get("selectorTextCols");//显示文本列
			var selectorValueCols = mini.get("selectorValueCols"); // 显示的值列
			var selectorDataFrom = mini.get("selectorDataFrom"); //数据来源
			
			function onNameValueChanged(e) {
				mini.get("name2").setValue(mini.get("name").getValue());
			}
			
			// 修正数据，并做交互检查,检查不通过返回null  !!!
			function prepareData(data) {
				var columnCodegenTypeValue = data.columnCodegenType;
				
				var selectorDataFromTypeValue = data.selectorDataFromType; // 数据来源类型
				var selectorMultilSelectValue = data.selectorMultilSelect; // 是否可多选
				
				var dataSourceCodeValue = data.dataSourceCode;
				var dbNameValue = data.dbName;
				
				var selectorTextColsValue = data.selectorTextCols;
				var selectorValueColsValue = data.selectorValueCols;
				var selectorDataFromValue = data.selectorDataFrom;
				
				if(isNotInvalid(columnCodegenTypeValue)) {
					 if(columnCodegenTypeValue == '2'){ // 下拉框(字典)
						 if(isInvalid(selectorTextColsValue)){
								mini.alert("【显示文本列】不能为空");
								return null;
							}
							if(isInvalid(selectorValueColsValue)){
								mini.alert("【显示的值列】不能为空");
								return null;
							}
							if(isInvalid(selectorDataFromValue)){
								mini.alert("【字典编码】不能为空");
								return null;
							}
						 return data;
					 }
					
					 if(columnCodegenTypeValue == '1'){ // 选择器
						if(isInvalid(selectorDataFromTypeValue)){
								mini.alert("数据来源类型不能为空");
								return null;
						}
					 	
					 	// 1=URL(返回JSON) 2=URL(返回XML) 3=代码片断(JavaScript)数组
						if(selectorDataFromTypeValue == '1' || selectorDataFromTypeValue == '2' || selectorDataFromTypeValue == '3') {
							if(isInvalid(selectorMultilSelectValue)){
								mini.alert("【是否可多选】不能为空");
								return null;
							}
							if(isInvalid(selectorTextColsValue)){
								mini.alert("【显示文本列】不能为空");
								return null;
							}
							if(isInvalid(selectorValueColsValue)){
								mini.alert("【显示的值列】不能为空");
								return null;
							}
							if(isInvalid(selectorDataFromValue)){
								if(selectorDataFromTypeValue == '1'){
									mini.alert("【URL(返回JSON)地址】不能为空");
									return null;
								}
								else if(selectorDataFromTypeValue == '2'){
									mini.alert("【URL(返回XML)地址】不能为空");
									return null;
								}
								else if(selectorDataFromTypeValue == '3'){
									mini.alert("【代码片断或数组(JavaScript)】不能为空");
									return null;
								}
							}
							
							data.dataSourceCode=null;
							data.dbName = null;
						}
						else if(selectorDataFromTypeValue == '0') {
							if(isInvalid(selectorDataFromValue)) {
								mini.alert("【Url页面地址】不能为空");
								return null;
							}
							data.selectorMultilSelect = null;
							data.dataSourceCode  = null;
							data.dbName  = null;
							data.selectorTextCols  = null;
							data.selectorValueCols  = null;
						}
						else if(selectorDataFromTypeValue == '4') {
							if(isInvalid(selectorMultilSelectValue)){
								mini.alert("【是否可多选】不能为空");
								return null;
							}
							if(isInvalid(dataSourceCodeValue)){
								mini.alert("【数据源】不能为空");
								return null;
							}
							if(isInvalid(dbNameValue)){
								mini.alert("【数据库】不能为空");
								return null;
							}
							if(isInvalid(selectorTextColsValue)){
								mini.alert("【显示文本列】不能为空");
								return null;
							}
							if(isInvalid(selectorValueColsValue)){
								mini.alert("【显示的值列】不能为空");
								return null;
							}
							if(isInvalid(selectorDataFromValue)){
								mini.alert("【SQL语句】不能为空");
								return null;
							}
						}
					 	
					 }
				}
				// alert(mini.encode(data));
				return data;
			}
			
			function onColumnCodegenTypeChanged(e) {
				// 1=选择器 2=下拉框(字典) 3=外键    4=文本框 5=整型框  6=精度型框 7=日期 8=文件  9=下拉框(常量JSON)
				var value = mini.get("columnCodegenType").getValue();
				//alert(value);
				if("1"==value) { 
			
					selectorDataFromType.setValue('');
					
					$(".selectorMultilSelect").show();
					$("#dsTR").show();
					
					$("#dbTR").hide();
					$("#displayColumnTR").hide();
					$("#selectorDataFromLableTR").hide();
					
					$("#controlConfig").show();
					
					
				}
				else if("2" == value){
					$("#displayColumnTR").show();
					
					$("#dsTR").hide();
					$("#dbTR").hide();
					$("#selectorDataFromLableTR").show();
					$("#controlConfig").show();
					$("#selectorDataFromLable").html("字典编码：");
				}
				else if('4' == value || '5' == value || '6' == value || '7' == value || '8' == value) {
					$("#controlConfig").hide();
				}
				else if('9' == value) {
					$("#dsTR").hide();
					$("#dbTR").hide();
					$("#selectorDataFromLableTR").show();
					$("#controlConfig").show();
					$("#selectorDataFromLable").html("下拉框(常量JSON)：");
				}
				else {
					$("#controlConfig").hide();
				}
				
			}
			
			function onSelectorDataFromTypeChanged(e) {	//数据来源类型:0=URL(页面) 1=URL(返回JSON) 2=URL(返回XML) 3=代码片断(JavaScript)数组  4=SQL
				//var obj = e.sender;
				var v = selectorDataFromType.getValue();;
				if(v == '0'){
					$("#selectorDataFromLable").html("Url页面地址");
					
					$(".selectorMultilSelect").hide();
					$("#dbTR").hide();
					$("#displayColumnTR").hide();
					$("#selectorDataFromLableTR").show();
					
					selectorDataFrom.focus();
				}
				else if (v == '1') {
					$("#selectorDataFromLable").html("URL(返回JSON)地址");
					
					
					$(".selectorMultilSelect").show();
					$("#displayColumnTR").show();
					$("#selectorDataFromLableTR").show();
					
					$("#dbTR").hide();
					selectorDataFrom.focus();
				}
				else if (v == '2') {
					$("#selectorDataFromLable").html("URL(返回XML)地址");
					
					$(".selectorMultilSelect").show();
					$("#displayColumnTR").show();
					$("#selectorDataFromLableTR").show();
					
					$("#dbTR").hide();
					selectorDataFrom.focus();
				}
				else if (v == '3') {
					$("#selectorDataFromLable").html("代码片断或数组(JavaScript)");
					
					$(".selectorMultilSelect").show();
					$("#displayColumnTR").show();
					$("#selectorDataFromLableTR").show();
					
					$("#dbTR").hide();
					selectorDataFrom.focus();
					
				}
				else if (v == '4') {
					$("#selectorDataFromLable").html("SQL语句");
					
					$(".selectorMultilSelect").show();
					$("#displayColumnTR").show();
					$("#dbTR").show();
					$("#selectorDataFromLableTR").show();
					
					selectorDataFrom.focus();
				}
				
			}
			
			
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
			
			function setComboBoxText(id,codeValue){ // 下拉列表加载 
				var comboBox = mini.get(id);
				var data = comboBox.getData();
				//alert(codeValue);
				//alert(mini.encode(data));
				for(var n=0;n<data.length;n++) {
					
					if( (""+codeValue) == (""+data[n].value) ){
						comboBox.setText(data[n].name);
						break;
					}
				}
			}

			function onPrimaryKeyChanged(e){
				var primaryKeyCmb = mini.get("primaryKey");
				var primaryKeyCmb2 = mini.get("primaryKey2");
				
				var value = primaryKeyCmb.getValue();
				var text =  primaryKeyCmb.getText();
				
				primaryKeyCmb2.setValue(value);
				primaryKeyCmb2.setText(text);
				
			}
			
			function onPrimaryKey2Changed(e){
				var primaryKeyCmb = mini.get("primaryKey2");
				var primaryKeyCmb2 = mini.get("primaryKey");
				
				var value = primaryKeyCmb.getValue();
				var text =  primaryKeyCmb.getText();
				
				primaryKeyCmb2.setValue(value);
				primaryKeyCmb2.setText(text);
				
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
