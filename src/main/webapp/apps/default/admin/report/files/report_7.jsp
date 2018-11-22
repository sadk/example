<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>代扣导入报表</title>

    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>  
   <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
</head>
<body>
   
    
<div class="mini-splitter" style="width:100%;height:100%;">
    <div size="250" showCollapseButton="true">
				<div id="form1"  style="padding:8px;">
					<table>	
						<!-- 			
						<tr>
							<td>关键字 ：</td>
							<td>
								<input id="reportDefinitionId" name="reportDefinitionId" class="mini-hidden" value="7"/>
								<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
							</td>
						</tr>
						 -->
								
								
								
									<tr>
										<td>合同号：</td>
										<td>
											<input id="contractNo" name="contractNo"  style="width:140px" class="mini-textbox"  emptyText="请输入合同号"  onenter="search"  />
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
  			
  			<div title="代扣导入报表">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
											<a class="mini-button" iconCls="icon-node" onclick='startDaiKou' >发起贷扣</a>
											<script type="text/javascript">
												function startDaiKou () {
	mini.open({
		url : "${pageContext.request.contextPath}/apps/default/admin/biz/csp/edit.jsp",
		title : "发起代扣",
		width : 500,
		height : 400,
		onload : function() {
			var iframe = this.getIFrameEl();
			var data = {
				action : "edit"
			};
			iframe.contentWindow.SetData(data);
		},
		ondestroy : function(action) {
			if(action == 'ok') {
				mini.alert("发起成功!");
			} else if(action == 'cancel' ) {
				
			} else {
				mini.alert(action);
			}
		}
	});
}
											</script>
								<span class="separator"></span>
								
									<a id="importData" class="mini-button" iconCls="icon-upload" onclick="importData()">导入</a>
							</td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
					<div id="dkimport_report_excel" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
							showPager="true"  
							
									sizeList="[20,50,100,500]" 
							
								pageSize="20"
						
						
						url="${pageContext.request.contextPath}/report/definition/search"  idField="id" >
						<div property="columns">
							<div type="checkcolumn" ></div>
									<div field="id" width="120" headerAlign="center" visible="true"   align="center" >id</div>
									<div field="createTime" width="120" headerAlign="center" visible="true"   align="center" >create_time</div>
									<div field="updateTime" width="120" headerAlign="center" visible="true"   align="center" >update_time</div>
									<div field="contractNo" width="120" headerAlign="center" visible="true"   align="center" >合同号</div>
									<div field="amt" width="120" headerAlign="center" visible="true"   align="center" >amt</div>
									<div field="payAcctNo" width="120" headerAlign="center" visible="true"   align="center" >pay_acct_no</div>
									<div field="payAcctName" width="120" headerAlign="center" visible="true"   align="center" >pay_acct_name</div>
									<div field="payAcctBankCode" width="120" headerAlign="center" visible="true"   align="center" >pay_acct_bank_code</div>
									<div field="payAcctBankName" width="120" headerAlign="center" visible="true"   align="center" >pay_acct_bank_name</div>
									<div field="acctNo" width="120" headerAlign="center" visible="true"   align="center" >acct_no</div>
									<div field="acctName" width="120" headerAlign="center" visible="true"   align="center" >acct_name</div>
									<div field="acctBankCode" width="120" headerAlign="center" visible="true"   align="center" >acct_bank_code</div>
									<div field="acctBankName" width="120" headerAlign="center" visible="true"   align="center" >acct_bank_name</div>
									<div field="entityId" width="120" headerAlign="center" visible="true"   align="center" >entity_id</div>
									<div field="telNo" width="120" headerAlign="center" visible="true"   align="center" >tel_no</div>
									<div field="bankCode" width="120" headerAlign="center" visible="true"   align="center" >bank_code</div>
									<div field="areaCode" width="120" headerAlign="center" visible="true"   align="center" >area_code</div>
									<div field="customerId" width="120" headerAlign="center" visible="true"   align="center" >customer_id</div>
						</div>
					</div>
		        </div>
		    </div>    

        </div>
    </div>        
</div>
    
    <script type="text/javascript">
    mini.parse();
	var grid = mini.get("dkimport_report_excel");
	var form = new mini.Form("form1");
	

	function ajaxGetTemplateExists(type,callback) {
		var data = {};
		data.definitionId= '7';
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
					definitionId: '7'
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
		
		
		 	data.pageIndex = grid.pageIndex;
		 	data.pageSize = grid.pageSize;
		data.reportDefinitionId=7;
		
		download(data);

	}
	
    function download(data) {
 	   loading();
 	   var url = '${pageContext.request.contextPath}/report/definition/export?1=1';
   		
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
 	               a.download = '代扣导入报表.xlsx';
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
		
    	
    	data.reportDefinitionId=7;
    	grid.load(data,function(e){
    		if(e && e.text) {
    			var data = mini.decode(e.text).hook;
    			if (data) {
	    	        mini.showTips({
	    	            content: data,
	    	            state: "danger",
	    	            x: "center",
	    	            y: "bottom",
	    	            timeout: 25000
	    	        });
    			}
    		}
    	});
    	
    	
		grid.on("drawcell", function (e) {// 如果有日期类型，转换为人工可讯形式
		   var record = e.record,
		   column = e.column,
		   field = e.field,
		   value = e.value;
		   
		   if (mini.isDate(value)) {
	    		e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm:ss");
		   }
		   
		})
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
    
    // -------------------------------- 报表自定义函数 -------------------------------------
    
    </script>

</body>
</html>