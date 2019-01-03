<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>职位定义</title>
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
			<div style="padding:4px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>职位信息</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td style="width:100px;">职位名称：</td>
								<td style="width:150px;">
									<input id="name" name="name" class="mini-buttonedit" onbuttonclick="onButtonEditPosition" required="true"/>
								</td>
								<td class="code" style="width:100px;">职位编码：</td>
								<td class="code" style="width:150px;">
									<input name="code" id="code" class="mini-textbox" readonly="readonly"/>
								</td>
							</tr>
							
							<tr>
								<td>综合工资：</td>
								<td>
									<input name="comprehensiveSalary" id="comprehensiveSalary" class="mini-textbox" />
								</td>
								<td>作息时间：</td>
								<td>
									<input name="workTime" id="workTime" class="mini-textbox" />
								</td>
							</tr>
							<tr>
								<td>公司简称：</td>
								<td>
									<input name="companyShortName" id="companyShortName" class="mini-hidden" />
									<input id="companyCode" name="companyCode"  onvaluechanged="onChangedCompany"  class="mini-combobox"  style="width:150px" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="shortName" valueField="code" url="${pageContext.request.contextPath}/rst/company/list"/>
								</td>
							</tr>
							
							<tr>
								<td>面试地址：</td>
								<td colspan="3">	
									<!-- 从公司地址加载，并且用户可录入 -->
									<input name="interviewAddress" id="interviewAddress" class="mini-textbox" /><a href="javascript:selectAddr()" > 选择地址</a>
								</td>
							</tr>
							
							<!-- <tr>
								<td>工作地址：</td>
								<td colspan="3">	
									从公司地址加载，
									<input id="positionAddress" name="positionAddress" class="mini-combobox" style="width:100%" textField="addressDesc" valueField="code" emptyText="请选择..."  allowInput="true" showNullItem="true" nullItemText="请选择..." multiSelect="true" />
								</td>
							</tr> -->
							
							<tr>
								<td>中介姓名：</td>
								<td>
									<input name="publishUserCode" id="publishUserCode" class="mini-hidden" />
									<input id="intermediaryName" name="intermediaryName" class="mini-buttonedit" onbuttonclick="onButtonEdit" />
								</td>
								<td>中介手机号：</td>
								<td>
									<input name="intermediaryPhone" id="intermediaryPhone" class="mini-textbox" />
								</td>
							</tr>
							
							<tr>
								<td>岗位职责：</td>
								<td colspan="3">
									<input id="responsibility" name="responsibility"  class="mini-textarea"  style="width:415px"  required="true"/>
								</td>
							</tr>
							<tr>
								<td>是否热招：</td>
								<td>
									<input id="urgent" name="urgent" class="mini-combobox"  style="width:150px" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=yes_or_no"/>
								</td>
								<td> 工资详情 ：</td>
								<td>
									<input id="salaryDetails" name="salaryDetails"  class="mini-textbox"  style="width:150px"  />
								</td>
							</tr>
							
							<tr>
								<td>岗位福利：</td>
								<td>
									<input name="welfareItemNos" id="welfareItemNos" class="mini-hidden" />
									<input id="welfare" name="welfare" class="mini-buttonedit" onbuttonclick="onButtonEditJobWelfares" style="width:150px"	/>
								</td>
								<td>推荐奖利金额：</td>
								<td>
									<input id="redAmount" name="redAmount"  class="mini-spinner"  style="width:150px" />
								</td>
							</tr>
							
							<tr>
								<td>推荐奖利规则 ：</td>
								<td colspan="3">
									<input id="amountRule" name="amountRule"  class="mini-textarea"  style="width:415px"  />
								</td>
							</tr>
				        </table>
				    </div>
				</fieldset>
				
				
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>职位要求</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td style="width:100px;">性别要求：</td>
								<td style="width:150px;">
									<input id="requiredSex" name="requiredSex" class="mini-combobox"  style="width:150px" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_sex_required"/>
								</td>
								<td style="width:100px;">年龄要求：</td>
								<td style="width:150px;">
									<input name="requiredAge" id="requiredAge" class="mini-textbox" minValue="0" maxValue="150"  />
								</td>
							</tr>
							<tr>
								<td>学历要求：</td>
								<td>
									<input id="requiredEducation" name="requiredEducation" class="mini-combobox"  style="width:150px" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_edu_required"/>
								</td>
								<td >工作年限：</td>
								<td>
									<input name="requiredWorkYears" id="requiredWorkYears" class="mini-textbox" minValue="0" maxValue="150"  />
								</td>
							</tr>
							<tr>
								<td>是否需要简历：</td>
								<td>
									<input id="requiredResume" name="requiredResume" class="mini-combobox"  style="width:150px" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=yes_or_no" required="true"/>
								</td>
							</tr>
						</table>
					</div>
				</fieldset>
				
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;">
		            <legend>发布信息</legend>
		            <div style="padding:5px;">
				        <table>
							<tr>
								<td style="width:100px;">发布平台：</td>
								<td style="width:150px;">
									<input id="publishPlatfrom" name="publishPlatfrom" class="mini-combobox"  style="width:150px" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_publish_platform" required="true"/>
								</td>
								<td style="width:100px;">是否置顶：</td>
								<td style="width:150px;">
									<input id="top" name="top" class="mini-combobox"  style="width:150px" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_top_sfzd" required="true"/>
								</td>
							</tr>
							<tr>
								<td>发布状态：</td>
								<td>
									<input id="status" name="status" class="mini-combobox"  style="width:150px" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_publish_status" required="true"/>
								</td>
								<td>所属门店：</td>
								<td>
									<input id="storeName" name="storeName" onclick="selectStore()" class="mini-textbox"  style="width:150px"/>
									<input id="storeCode" name="storeCode"   class="mini-hidden" />
								</td>
							</tr>
							<tr>
								<td>排序号：</td>
								<td>
									<input id="sn" name="sn" class="mini-spinner" minValue="0" maxValue="9999999" />
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
			var companyCode = mini.get("companyCode");
			var companyShortName = mini.get("companyShortName");
			
			var publishUserCode = mini.get("publishUserCode");
			var intermediaryName = mini.get("intermediaryName");
			var intermediaryPhone = mini.get("intermediaryPhone");
			var welfareItemNos = mini.get("welfareItemNos");
			
			var interviewAddress = mini.get("interviewAddress"); //面试地址
			
			function selectStore() { //职位所属的门店
	            mini.open({
	                url: "${pageContext.request.contextPath}/apps/default/admin/rst/store/selector_store.jsp?multiSelect=false",
	                title: "选择所属门店",
	                width: 500,  height: 400,
	            	onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							 
						};
						
						iframe.contentWindow.SetData(data);
					},
	                ondestroy: function (action) {
	                    if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var data = iframe.contentWindow.GetData();
	                        data = mini.clone(data);
	                        
	                        if (data) {
	                        	mini.get("storeName").setValue(data.name);
	                        	mini.get("storeCode").setValue(data.code);
	                        } 
	                    }
	                }
	            });
			}
			
			function selectAddr() { //选择面试地址 
	            mini.open({
	                url: "${pageContext.request.contextPath}/apps/default/admin/rst/position_definition/selector_address.jsp",
	                title: "选择企业地址",
	                width: 500,  height: 400,
	            	onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							companyCode: companyCode.getValue()
						};
						
						iframe.contentWindow.SetData(data);
					},
	                ondestroy: function (action) {
	                    if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var data = iframe.contentWindow.GetData();
	                        data = mini.clone(data);
	                        
	                        if (data) {
	                        	interviewAddress.setValue(data.addressDesc);
	                        } 
	                    }
	                }
	            });
			}
			
			function loadWelfareItemNos(positionCode) { //加载职位福利隐藏域 
				$.ajax({
					url : "${pageContext.request.contextPath}/rst/position_definition/get_welfare_item_nos?positionCode=" + positionCode,
					dataType: 'json',
					success : function(text) {
						if (text && text.length >0 ) {
							var itemNoArr = new Array();
							 for (var i=0;i<text.length; i++) {
								 itemNoArr.push(text[i].welfare_id);
							 }
							 welfareItemNos.setValue(itemNoArr.join(","));
						}
					}
				});
			}
			
			function onButtonEditJobWelfares() {
				var btnEdit = this;
	            mini.open({
	                url: "${pageContext.request.contextPath}/apps/default/admin/rst/code_library/selector_code_lib.jsp?codeNo=job_welfare",
	                title: "选择福利",
	                width: 500,  height: 400,
	                ondestroy: function (action) {
	                    if (action == "ok") {
	                        
	                        var iframe = this.getIFrameEl();
	                        var data = iframe.contentWindow.GetDatas();
	                        data = mini.clone(data);    //必须
	                        
	                        if (data && data.length>0) {
	                        	var jobNameList = new Array();
	                        	var itemNoList = new Array();
	                        	for (var i=0;i<data.length;i++) {
	                        		jobNameList.push(data[i].itemName);
	                        		itemNoList.push(data[i].itemNo);
	                        	}
	                        	
	                            btnEdit.setValue(jobNameList.join(","));
	                            btnEdit.setText(jobNameList.join(","));
	                            
	                            welfareItemNos.setValue(itemNoList.join(","));
	                        } else {
		                        btnEdit.setValue("");
		                        btnEdit.setText("");
		                        
		                        welfareItemNos.setValue("");
	                        }
	                        
	                    }
	                }
	            });
			}
			
			function onButtonEdit() {
				var btnEdit = this;
	            mini.open({
	                url: "${pageContext.request.contextPath}/apps/default/admin/rst/user/selector_user.jsp?multiSelect=false",
	                title: "选择中介",  
	                width: 500,  height: 400,
	                ondestroy: function (action) {
	                    if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var data = iframe.contentWindow.GetData();
	                        data = mini.clone(data);    //必须
	                        
	                        if (data) {
	                            btnEdit.setValue(data.realName);
	                            btnEdit.setText(data.realName);
	                            
	                            publishUserCode.setValue(data.code);
	                           // intermediaryName.setValue(data.realName);
	                            intermediaryPhone.setValue(data.mobile);
	                            
	                        } else {
		                        btnEdit.setValue("");
		                        btnEdit.setText("");
	                        }
	                    }
	                }
	            });
			}
			
			function onButtonEditPosition() {
	            var btnEdit = this;
	            mini.open({
	                url: "${pageContext.request.contextPath}/apps/default/admin/rst/job_definition/selector_job_definition.jsp",
	                title: "选择列表",
	                width: 500,
	                height: 400,
	                ondestroy: function (action) {
	                    //if (action == "close") return false;
	                    if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var data = iframe.contentWindow.GetDatas();
	                        data = mini.clone(data);    //必须
	                        
	                        if (data && data.length>0) {
	                        	var jobNameList = new Array();
	                        	for (var i=0;i<data.length;i++) {
	                        		jobNameList.push(data[i].name)
	                        	}
	                        	
	                            btnEdit.setValue(jobNameList.join(","));
	                            btnEdit.setText(jobNameList.join(","));
	                        } else {
		                        btnEdit.setValue("");
		                        btnEdit.setText("");
	                        }
	                    }

	                }
	            });
			}
			
 			function onChangedCompany(e) {
 				var source = e.sender;
 				//console.log(source)
 				//companyCode.setValue(source.value);
 				companyShortName.setValue(source.text);
 				//console.log(source.code)
 				
 				//interviewAddress.setUrl("${pageContext.request.contextPath}/rst/work_address/list?companyCode="+e.value);
 			}
 			
			function SaveData() {
				var o = form.getData();
				form.validate();
				
				//o.interviewAddress = interviewAddress.getValue();
				//console.log(o)
				
				if(form.isValid() == false) return;
				$.ajax({
					url : "${pageContext.request.contextPath}/rst/position_definition/save_or_update",
					dataType: 'json', type : 'post',
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
				
				 if(data.action == "edit" ) {
					$.ajax({
						url : "${pageContext.request.contextPath}/rst/position_definition/get_by_id?id=" + data.id,
						dataType: 'json',
						success : function(text) {
							if (text) {
								var o = mini.decode(text);
								form.setData(o);
								//console.log(o)
								mini.get("name").setText(o.name);
								mini.get("intermediaryName").setText( o.intermediaryName);
								mini.get("welfare").setText(o.welfare);
								
								
								
								loadWelfareItemNos(o.code); // 加载职位福利数据
								
								//var addrUrl = "${pageContext.request.contextPath}/rst/work_address/list?companyCode="+o.companyCode;
								//interviewAddress.setUrl(addrUrl);
								console.log(o)
								//interviewAddress.setText(o.interviewAddress);
								
								//positionAddress.setUrl(addrUrl);
							}
						}
					});
				}
				
				if (data.action == 'add') {
					$(".code").hide();
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
