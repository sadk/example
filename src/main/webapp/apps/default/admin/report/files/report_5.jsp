<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>法务报表</title>

    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>  
   <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
</head>
<body>
   
    
<div class="mini-splitter" style="width:100%;height:100%;">
    <div size="280" showCollapseButton="true">
				<div id="form1"  style="padding:8px;">
					<table>	
						<!-- 			
						<tr>
							<td>关键字 ：</td>
							<td>
								<input id="reportDefinitionId" name="reportDefinitionId" class="mini-hidden" value="5"/>
								<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
							</td>
						</tr>
						 -->
								
								
								
									<tr>
										<td>合同编号：</td>
										<td>
											<input id="contractNo" name="contractNo"  style="width:140px" class="mini-textbox"  emptyText="请输入合同编号"  onenter="search" required="true" />
										</td>
									</tr>
								
								
								
								
								
								
								
								
								
									<tr>
										<td>借款人姓名：</td>
										<td>
											<input id="customername" name="customername"  style="width:140px" class="mini-textbox"  emptyText="请输入借款人姓名"  onenter="search"  />
										</td>
									</tr>
								
								
								
								
								
								
								
								
								
									<tr>
										<td>借款人电子邮箱：</td>
										<td>
											<input id="emailadd" name="emailadd"  style="width:140px" class="mini-textbox"  emptyText="请输入借款人电子邮箱"  onenter="search"  />
										</td>
									</tr>
								
								
								
								
								
								
								
								
								
									<tr>
										<td>借款人手机号：</td>
										<td>
											<input id="personMobil" name="personMobil"  style="width:140px" class="mini-textbox"  emptyText="请输入借款人手机号"  onenter="search"  />
										</td>
									</tr>
								
								
								
								
								
								
								
								
								
									<tr>
										<td>借款人身份证号码：</td>
										<td>
											<input id="certid" name="certid"  style="width:140px" class="mini-textbox"  emptyText="请输入借款人身份证号码"  onenter="search"  />
										</td>
									</tr>
								
								
								
								
								
								

					</table>
					<div style="text-align:center;padding:10px;">
						<a class="mini-button" onclick="search()" iconCls="icon-search" style="width:60px;margin-right:20px;">查询</a>
						<a class="mini-button" onclick="clear()" iconCls="icon-cancel" style="width:60px;margin-right:20px;">清空</a>
					</div>
				</div>
    </div>
    <div showCollapseButton="true">
    	<div id="tabs1" class="mini-tabs" activeIndex="0" plain="false" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
  			
  			<div title="法务报表">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
							</td>
							<!-- 
							<td style="white-space:nowrap;">
		                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="search()">查询</a>
		                    </td>
		                     -->
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
					<div id="report_fwbb" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" showPager="true"  sizeList="[20,50,100,500]"  pageSize="20" 
						url="${pageContext.request.contextPath}/report/definition/search"  idField="id" >
						<div property="columns">
							<div type="checkcolumn" ></div>
									<div field="id" width="120" headerAlign="center" visible="true"   align="center" >id</div>
									<div field="contractNo" width="120" headerAlign="center" visible="true"   align="center" >合同编号</div>
									<div field="customername" width="120" headerAlign="center" visible="true"   align="center" >借款人姓名</div>
									<div field="emailadd" width="120" headerAlign="center" visible="true"   align="center" >借款人电子邮箱</div>
									<div field="personMobil" width="120" headerAlign="center" visible="true"   align="center" >借款人手机号</div>
									<div field="personSex" width="120" headerAlign="center" visible="true"   align="center" >性别</div>
									<div field="certid" width="120" headerAlign="center" visible="true"   align="center" >借款人身份证号码</div>
									<div field="currentAddress" width="120" headerAlign="center" visible="true"   align="center" >借款人居住地地址</div>
									<div field="registedAddress" width="120" headerAlign="center" visible="true"   align="center" >借款人户籍地址</div>
									<div field="registedProvince" width="120" headerAlign="center" visible="true"   align="center" >户籍所在省</div>
									<div field="registedCity" width="120" headerAlign="center" visible="true"   align="center" >户籍所在市</div>
									<div field="city" width="120" headerAlign="center" visible="true"   align="center" >借款城市</div>
									<div field="province" width="120" headerAlign="center" visible="true"   align="center" >借款省份</div>
									<div field="putoutdate" width="120" headerAlign="center" visible="true"   align="center" >借款起算时间</div>
									<div field="totalprice" width="120" headerAlign="center" visible="true"   align="center" >商品价格</div>
									<div field="totalsum" width="120" headerAlign="center" visible="true"   align="center" >首付金额</div>
									<div field="businesssum" width="120" headerAlign="center" visible="true"   align="center" >贷款本金</div>
									<div field="payprincipalamt" width="120" headerAlign="center" visible="true"   align="center" >应还本金</div>
									<div field="actualpayprincipalamt" width="120" headerAlign="center" visible="true"   align="center" >已还贷款本金</div>
									<div field="unpayActualpayprincipalamt" width="120" headerAlign="center" visible="true"   align="center" >未还本金</div>
									<div field="payinteamt" width="120" headerAlign="center" visible="true"   align="center" >应还贷款利息</div>
									<div field="actualpayinteamt" width="120" headerAlign="center" visible="true"   align="center" >已还贷款利息</div>
									<div field="unpayPayinteamt" width="120" headerAlign="center" visible="true"   align="center" >未还贷款利息</div>
									<div field="baserate" width="120" headerAlign="center" visible="true"   align="center" >贷款利息利率</div>
									<div field="a2Amt" width="120" headerAlign="center" visible="true"   align="center" >应还客户服务费</div>
									<div field="unpayA2Amt" width="120" headerAlign="center" visible="true"   align="center" >未还客户服务费</div>
									<div field="a7Amt" width="120" headerAlign="center" visible="true"   align="center" >应还财务顾问费</div>
									<div field="unpayA7Amt" width="120" headerAlign="center" visible="true"   align="center" >未还财务顾问费</div>
									<div field="a9Amt" width="120" headerAlign="center" visible="true"   align="center" >应还提前还款手续费</div>
									<div field="unpayA9Amt" width="120" headerAlign="center" visible="true"   align="center" >未还提前还款手续费</div>
									<div field="a10Amt" width="120" headerAlign="center" visible="true"   align="center" >应还滞纳金</div>
									<div field="unpayA10Amt" width="120" headerAlign="center" visible="true"   align="center" >未还滞纳金</div>
									<div field="a12Amt" width="120" headerAlign="center" visible="true"   align="center" >应还增值服务费</div>
									<div field="unpayA12Amt" width="120" headerAlign="center" visible="true"   align="center" >未还增值服务费</div>
									<div field="a17Amt" width="120" headerAlign="center" visible="true"   align="center" >应还委外催收费</div>
									<div field="unpayA17Amt" width="120" headerAlign="center" visible="true"   align="center" >未还委外催收费</div>
									<div field="a18Amt" width="120" headerAlign="center" visible="true"   align="center" >应还随心还服务费</div>
									<div field="unpayA18Amt" width="120" headerAlign="center" visible="true"   align="center" >未还随心还服务费</div>
									<div field="a19Amt" width="120" headerAlign="center" visible="true"   align="center" >应还提前委外催收费</div>
									<div field="unpayA19Amt" width="120" headerAlign="center" visible="true"   align="center" >未还提前委外催收费</div>
									<div field="a20Amt" width="120" headerAlign="center" visible="true"   align="center" >应还催收费</div>
									<div field="unpayA20Amt" width="120" headerAlign="center" visible="true"   align="center" >未还催收费</div>
									<div field="a22Amt" width="120" headerAlign="center" visible="true"   align="center" >应还佰保袋费用</div>
									<div field="unpayA22Amt" width="120" headerAlign="center" visible="true"   align="center" >未还佰保袋费用</div>
									<div field="a25Amt" width="120" headerAlign="center" visible="true"   align="center" >应还还款宝服务费</div>
									<div field="unpayA25Amt" width="120" headerAlign="center" visible="true"   align="center" >未还还款宝服务费</div>
									<div field="a31Amt" width="120" headerAlign="center" visible="true"   align="center" >应还渠道管理费</div>
									<div field="unpayA31Amt" width="120" headerAlign="center" visible="true"   align="center" >未还渠道管理费</div>
									<div field="a50Amt" width="120" headerAlign="center" visible="true"   align="center" >应还居间服务费</div>
									<div field="unpayA50Amt" width="120" headerAlign="center" visible="true"   align="center" >未还居间服务费</div>
									<div field="a52Amt" width="120" headerAlign="center" visible="true"   align="center" >应还信息服务费</div>
									<div field="unpayA52Amt" width="120" headerAlign="center" visible="true"   align="center" >未还信息服务费</div>
									<div field="a55Amt" width="120" headerAlign="center" visible="true"   align="center" >应还借意险</div>
									<div field="unpayA55Amt" width="120" headerAlign="center" visible="true"   align="center" >未还借意险</div>
									<div field="cnt" width="120" headerAlign="center" visible="true"   align="center" >合同数量</div>
									<div field="repaymentno" width="120" headerAlign="center" visible="true"   align="center" >指定还款账号</div>
									<div field="repaymentname" width="120" headerAlign="center" visible="true"   align="center" >指定还款户名</div>
									<div field="repaymentbank" width="120" headerAlign="center" visible="true"   align="center" >指定还款开户行</div>
									<div field="replaceaccount" width="120" headerAlign="center" visible="true"   align="center" >代扣账号</div>
									<div field="replacename" width="120" headerAlign="center" visible="true"   align="center" >代扣账号户名</div>
									<div field="openbank" width="120" headerAlign="center" visible="true"   align="center" >代扣账户开户行</div>
									<div field="familyTel" width="120" headerAlign="center" visible="true"   align="center" >客户住宅电话</div>
									<div field="inputdate" width="120" headerAlign="center" visible="true"   align="center" >合同录入日期</div>
									<div field="registrationdate" width="120" headerAlign="center" visible="true"   align="center" >合同注册日期</div>
									<div field="contractstatus" width="120" headerAlign="center" visible="true"   align="center" >合同状态</div>
									<div field="dealdata" width="120" headerAlign="center" visible="true"   align="center" >赎回日期</div>
									<div field="normalbalance" width="120" headerAlign="center" visible="true"   align="center" >赎回正常本金</div>
									<div field="overduebalance" width="120" headerAlign="center" visible="true"   align="center" >赎回逾期本金</div>
									<div field="odintebalance" width="120" headerAlign="center" visible="true"   align="center" >赎回逾期利息</div>
									<div field="pureoverflowsum" width="120" headerAlign="center" visible="true"   align="center" >纯溢价</div>
									<div field="isResell" width="120" headerAlign="center" visible="true"   align="center" >赎回后是否转给其他公司</div>
									<div field="dcGuarantor" width="120" headerAlign="center" visible="true"   align="center" >赎回合同的代偿主体</div>
									<div field="shGuarantor" width="120" headerAlign="center" visible="true"   align="center" >赎回主体</div>
									<div field="dcAmt" width="120" headerAlign="center" visible="true"   align="center" >实际代偿本金</div>
									<div field="dcInt" width="120" headerAlign="center" visible="true"   align="center" >实际代偿利息</div>
									<div field="maturitydate" width="120" headerAlign="center" visible="true"   align="center" >借款正常终止时间</div>
									<div field="monthrepayment" width="120" headerAlign="center" visible="true"   align="center" >每期还款金额</div>
									<div field="periods" width="120" headerAlign="center" visible="true"   align="center" >约定还款期数</div>
									<div field="payPeriods" width="120" headerAlign="center" visible="true"   align="center" >已还期数</div>
									<div field="unpayPeriods" width="120" headerAlign="center" visible="true"   align="center" >未还期数</div>
									<div field="maxActualpaydate" width="120" headerAlign="center" visible="true"   align="center" >最后还款时间</div>
									<div field="cpddays" width="120" headerAlign="center" visible="true"   align="center" >逾期天数</div>
									<div field="cancelInst" width="120" headerAlign="center" visible="true"   align="center" >提前终止时间</div>
									<div field="isDianzi" width="120" headerAlign="center" visible="true"   align="center" >是否属于电子签署</div>
									<div field="productname" width="120" headerAlign="center" visible="true"   align="center" >贷款类型（个人消费分期或渠道消费分期）</div>
									<div field="creditperson" width="120" headerAlign="center" visible="true"   align="center" >所属资方</div>
									<div field="suretype" width="120" headerAlign="center" visible="true"   align="center" >业务来源</div>
									<div field="spousename" width="120" headerAlign="center" visible="true"   align="center" >配偶姓名</div>
									<div field="spousetel" width="120" headerAlign="center" visible="true"   align="center" >配偶移动电话</div>
									<div field="spouseworkcorp" width="120" headerAlign="center" visible="true"   align="center" >配偶单位名称</div>
									<div field="spouseworktel" width="120" headerAlign="center" visible="true"   align="center" >配偶单位电话</div>
									<div field="kinshipname" width="120" headerAlign="center" visible="true"   align="center" >家庭成员名称</div>
									<div field="relativetype" width="120" headerAlign="center" visible="true"   align="center" >家庭成员类</div>
									<div field="kinshiptel" width="120" headerAlign="center" visible="true"   align="center" >家庭成员电话</div>
									<div field="kinshipadd" width="120" headerAlign="center" visible="true"   align="center" >家庭成员联系地址</div>
									<div field="producttype" width="120" headerAlign="center" visible="true"   align="center" >子产品类型</div>
									<div field="posId" width="120" headerAlign="center" visible="true"   align="center" >门店编码</div>
									<div field="posName" width="120" headerAlign="center" visible="true"   align="center" >门店名称</div>
									<div field="rno" width="120" headerAlign="center" visible="true"   align="center" >商户编号</div>
									<div field="rname" width="120" headerAlign="center" visible="true"   align="center" >商户名称</div>
									<div field="rAccountname" width="120" headerAlign="center" visible="true"   align="center" >商户户名</div>
									<div field="rBankname" width="120" headerAlign="center" visible="true"   align="center" >商户开户行</div>
									<div field="rBranchbankName" width="120" headerAlign="center" visible="true"   align="center" >商户开户支行</div>
									<div field="rAccount" width="120" headerAlign="center" visible="true"   align="center" >商户账号</div>
									<div field="rAccountProcince" width="120" headerAlign="center" visible="true"   align="center" >商户开户行所在省份</div>
									<div field="rAccountCity" width="120" headerAlign="center" visible="true"   align="center" >商户开户行所在城市</div>
									<div field="typename" width="120" headerAlign="center" visible="true"   align="center" >产品名称</div>
									<div field="mufacturer1" width="120" headerAlign="center" visible="true"   align="center" >商品名称</div>
									<div field="status" width="120" headerAlign="center" visible="true"   align="center" >赎回跟转让的状态</div>
									<div field="pDay" width="120" headerAlign="center" visible="false"   align="center" >p_day</div>
						</div>
					</div>
		        </div>
		    </div>    

        </div>
    </div>        
</div>
    
    <script type="text/javascript">
    mini.parse();
	var grid = mini.get("report_fwbb");
	var form = new mini.Form("form1");
	

	function ajaxGetTemplateExists(type,callback) {
		var data = {};
		data.definitionId= '5';
		data.type = type; //100=导入模板 200=导出模板
        $.ajax({ 
            url: "${pageContext.request.contextPath}/report/export_template/list",
            data: data,
            type: "post",
            success: function (text) {
             	 callback(text);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                mini.alert("请求错误："+jqXHR.responseText);
            }
        }); 
	}
	
	function importData() {
        mini.open({
            url: "${pageContext.request.contextPath}/apps/default/admin/report/definition/upload.jsp",
            title: "选择数据文件",
            width: 650,
            height: 400,
			onload : function() {
				var iframe = this.getIFrameEl();
				var data = {
					action : "importData",
					definitionId: '5'
				};
				iframe.contentWindow.SetData(data);
			},
            ondestroy: function (action) {
                if (action == "ok") {
                    var iframe = this.getIFrameEl();
                    var data = iframe.contentWindow.GetData();
                    data = mini.clone(data);
                    if (data) {
                        
                    }  
                }
            }
        });
	}
	
	function checkCanBeExportFile() { //检查是否有上传导出模板,如果有，则显示导出按钮
		var callback = function (text) {
			if(typeof(text) == 'undefined' || text == null || text.length == 0) {
        		 $("#exportFile").hide();
        		 $("#exportFileType").hide();
        	 } else if(text.length ==1 ){ 
        		 $("#exportFile").show();
        		 $("#exportFileType").show();
        	 } else {
        		 $("#exportFile").hide();
        		 $("#exportFileType").hide();
        	 }
		}
		ajaxGetTemplateExists(200,callback); //100=导入模板 200=导出模板
	}
	
	function checkCanBeImportFile() { //检查是否有上传导入模板,如果有，则显示导入按钮
		var callback = function (text) {
			if(typeof(text) == 'undefined' || text == null || text.length == 0) {
        		 $("#importData").hide();
        	 } else if(text.length ==1 ){ 
        		 $("#importData").show();
        	 } else {
        		 $("#importData").hide();
        	 }
		}
		ajaxGetTemplateExists(100,callback); //100=导入模板 200=导出模板
	}
	
	$(function(){
		checkCanBeExportFile();
		checkCanBeImportFile();
	})

	
	function refresh() {
		var messageid = mini.loading("Loading, Please wait ...", "Loading");
        setTimeout(function () {
            mini.hideMessageBox(messageid);
        }, 1000);
        grid.reload();
	}
	
	function exportData() {
    	var data = form.getData();
    	form.validate();
		if(form.isValid() == false) return;
		
	
		data.reportDefinitionId=5;
		
		download(data);
		/*
        $.ajax({ 
            url: "/report/definition/export",
            data: data,
            type: "post",
           // dataType: "blob",
            success: function (text) {
            	console.log(text)
            	 
             	 
            },
            error: function (jqXHR, textStatus, errorThrown) {
                mini.showTips({
                    content: jqXHR.responseText,
                    state: 'danger',  x: "right",  y: "bottom",
                    timeout: 10000
                });
            }
        });*/
	}
	
    function download(data) {
 	   loading();
 	   var url = '/report/definition/export?1=1';
   		
 	   var params = new Array();
 	    for (var i in data) {	// 获取对象属性
 	         if (data.hasOwnProperty(i) && typeof data[i] != "function") {
 	        	params.push("&"+i+"="+data[i])
 	          }
 	     }
 	   url = url + params.join("");
 	   var xhr = new XMLHttpRequest();
 	   xhr.open('POST', url, true);        // 也可以使用POST方式，根据接口
 	   xhr.responseType = "blob";    // 返回类型blob
 	   // 定义请求完成的处理函数，请求前也可以增加加载框/禁用下载按钮逻辑
 	   xhr.onload = function () {
 	       // 请求完成
 	       if (this.status === 200) {
 	    	   
 	    	   loadingClose()
 	    	   
 	           // 返回200
 	           var blob = this.response;
 	           var reader = new FileReader();
 	           reader.readAsDataURL(blob);    // 转换为base64，可以直接放入a表情href
 	           reader.onload = function (e) {
 	        	   
 	               // 转换完成，创建一个a标签用于下载
 	               var a = document.createElement('a');
 	               a.download = '法务报表.xlsx';
 	               a.href = e.target.result;
 	               $("body").append(a);    // 修复firefox中无法触发click
 	               a.click();
 	               $(a).remove();
 	           }
 	       }
 	   };
 	   // 发送ajax请求
 	   xhr.send()
 	}
	
	/*
    function onButtonEdit(e) {
        var btnEdit = this;
        mini.open({
            url: "${pageContext.request.contextPath}/apps/default/admin/sys/tenant/selector_tenant.jsp",
            title: "选择租户",
            width: 650,
            height: 380,
            ondestroy: function (action) {
                
                if (action == "ok") {
                    var iframe = this.getIFrameEl();
                    var data = iframe.contentWindow.GetData();
                    data = mini.clone(data);    //必须
                    if (data) {
                        btnEdit.setValue(data.code);
                        btnEdit.setText(data.name);
                        
                       //	console.log(data.code)
                        folderTree.load({tenantCode:data.code})
                    } else {
                    	btnEdit.setValue(null);
                        btnEdit.setText(null);
                        folderTree.load()
                    }
                }

            }
        });
    }
     */
    function search() {
    	var data = form.getData();
    	form.validate();
		if(form.isValid() == false) return;
		
    	
    	data.reportDefinitionId=5;
    	grid.load(data)
    	/*
        $.ajax({ 
            url: "/report/definition/search",
            data: data,
            type: "post",
            success: function (text) {
            	
					if(text) {
						grid.setData(text.data)
	            	}
            	
            },
            error: function (jqXHR, textStatus, errorThrown) {
                mini.showTips({
                    content: jqXHR.responseText,
                    state: 'danger',  x: "right",  y: "bottom",
                    timeout: 10000
                });
            }
        });*/
    }
    
    function clear() {
    	form.clear();
    }
    
    // ----------------------------
    function loading(){
        mini.mask({
            el: document.body,
            cls: 'mini-mask-loading',
            html: '正在批量处理数据，请稍后 ...'
        });
	}
	
    function loadingAutoClose(timeout) {
        mini.mask({
            el: document.body,
            cls: 'mini-mask-loading',
            html: '正在批量处理数据，请稍后 ...'
        });
        
        setTimeout(function () {
            mini.unmask(document.body);
        }, timeout);
    }
    
    function loadingClose(){
    	 mini.unmask(document.body);
    }
    </script>

</body>
</html>