<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>累计实还表</title>

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
								<input id="reportDefinitionId" name="reportDefinitionId" class="mini-hidden" value="1006"/>
								<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
							</td>
						</tr>
						 -->
								
								
								
								
								

									<tr>
										<td>实还月：</td>
										<td>
											<input id="PAYDATE" name="PAYDATE" class="mini-datepicker" style="width:140px;" nullValue="null"  format="yyyy/MM/dd" timeFormat="HH:mm:ss"  showTime="true" showOkButton="true" showClearButton="false"  emptyText="请输入" required="true" />
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
  			
  			<div title="累计实还表">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
								<span class="separator"></span>
									<a id="exportFile" class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
								
							</td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
					<div id="bi_report_ljshb" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
							showPager="true"  
							
									sizeList="[20,50,100,500]" 
							
								pageSize="20"
						
						
						url="${pageContext.request.contextPath}/report/definition/search"  idField="id" >
						<div property="columns">
							<div type="checkcolumn" ></div>
									<div field="PUTOUTNO" width="120" headerAlign="center" visible="true"   align="center" >合同号</div>
									<div field="HXRQ" width="120" headerAlign="center" visible="true"   align="center" >核销日期</div>
									<div field="JZRQ" width="120" headerAlign="center" visible="true"   align="center" >记账日期</div>
									<div field="TJY" width="120" headerAlign="center" visible="true"   align="center" >统计月</div>
									<div field="KHQD" width="120" headerAlign="center" visible="true"   align="center" >客户渠道</div>
									<div field="YWMS" width="120" headerAlign="center" visible="true"   align="center" >业务模式</div>
									<div field="CPZLX" width="120" headerAlign="center" visible="true"   align="center" >产品子类型</div>
									<div field="CPMC" width="120" headerAlign="center" visible="true"   align="center" >产品名称</div>
									<div field="SF" width="120" headerAlign="center" visible="true"   align="center" >省</div>
									<div field="CS" width="120" headerAlign="center" visible="true"   align="center" >市</div>
									<div field="CSBM" width="120" headerAlign="center" visible="true"   align="center" >城市编码</div>
									<div field="HKLX" width="120" headerAlign="center" visible="true"   align="center" >还款类型</div>
									<div field="SFQXFQ" width="120" headerAlign="center" visible="true"   align="center" >是否取消分期</div>
									<div field="SSJFL" width="120" headerAlign="center" visible="true"   align="center" >十四级分类</div>
									<div field="DDF" width="120" headerAlign="center" visible="true"   align="center" >代垫方</div>
									<div field="ZCSSF" width="120" headerAlign="center" visible="true"   align="center" >资产所属方</div>
									<div field="BZF" width="120" headerAlign="center" visible="true"   align="center" >保证方</div>
									<div field="BDBF" width="120" headerAlign="center" visible="true"   align="center" >被担保方</div>
									<div field="DBFS" width="120" headerAlign="center" visible="true"   align="center" >担保方式</div>
									<div field="SFDQDQ" width="120" headerAlign="center" visible="true"   align="center" >是否当期到期</div>
									<div field="PAYDATE" width="120" headerAlign="center" visible="true"   align="center" >实还月</div>
									<div field="YQTS" width="120" headerAlign="center" visible="true"   align="center" >逾期天数</div>
									<div field="SHBJ" width="120" headerAlign="center" visible="true"   align="center" >实还本金</div>
									<div field="SHLX" width="120" headerAlign="center" visible="true"   align="center" >实还利息</div>
									<div field="SHCWGLF" width="120" headerAlign="center" visible="true"   align="center" >实还财务管理费</div>
									<div field="SHZZFWF" width="120" headerAlign="center" visible="true"   align="center" >实还增值服务费</div>
									<div field="SHTQHKSXF" width="120" headerAlign="center" visible="true"   align="center" >实还提前还款手续费</div>
									<div field="SHZNJ" width="120" headerAlign="center" visible="true"   align="center" >实还滞纳金</div>
									<div field="SHYHS" width="120" headerAlign="center" visible="true"   align="center" >实还印花税</div>
									<div field="SHCSF" width="120" headerAlign="center" visible="true"   align="center" >实还催收费</div>
									<div field="SHTQCSF" width="120" headerAlign="center" visible="true"   align="center" >实还提前催收费</div>
									<div field="HJ" width="120" headerAlign="center" visible="true"   align="center" >合计</div>
						</div>
					</div>
		        </div>
		    </div>    

        </div>
    </div>        
</div>
    
    <script type="text/javascript">
    mini.parse();
	var grid = mini.get("bi_report_ljshb");
	var form = new mini.Form("form1");
	

	function ajaxGetTemplateExists(type,callback) {
		var data = {};
		data.definitionId= '1006';
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
					definitionId: '1006'
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
		data.reportDefinitionId=1006;
		
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
 	               a.download = '累计实还表.xlsx';
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
		
		    			data.PAYDATE =  mini.get('PAYDATE').text;
    	
    	data.reportDefinitionId=1006;
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