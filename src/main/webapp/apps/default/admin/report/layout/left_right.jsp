<#noparse><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%></#noparse>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>${definition.name}</title>

    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>  
   <script type="text/javascript" src="<#noparse>${pageContext.request.contextPath}</#noparse>/scripts/boot.js"></script>
</head>
<body>
   
    
<div class="mini-splitter" style="width:100%;height:100%;">
    <div size="${definition.searchAreaWidth}" showCollapseButton="true">
				<div id="form1"  style="padding:8px;">
					<table>	
						<!-- 			
						<tr>
							<td>关键字 ：</td>
							<td>
								<input id="reportDefinitionId" name="reportDefinitionId" class="mini-hidden" value="${definition.id}"/>
								<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
							</td>
						</tr>
						 -->
						<#-- 字段的代码生成器类型:1=选择器 2=下拉框(字典) 3=外键  4=文本框 5=整型框 6=精度型框 7=日期 8=文件  9=下拉框(常量JSON) -->
						<#list columnList as column>
						<#if (column.searchType?? && column.searchType==1)>
							<#if column.columnCodegenType??>
								
								
								<#if column.columnCodegenType == 2 >
									<tr>
										<td>${column.name}：</td>
										<td>
											<input id="${column.propertyName}" name="${column.propertyName}" class="mini-combobox" style="width:140px" valueField="${column.selectorValueCols }" textField="${column.selectorTextCols}" showNullItem="true" nullItemText="请选择..." emptyText="请选择..."  url="<#noparse>${pageContext.request.contextPath}</#noparse>/dictionary/option?code=${column.selectorDataFrom}" <#if (column.searchRequired?? && column.searchRequired ==1) >required="true"</#if> />
										</td>
									</tr>
								</#if>
								
								<#if column.columnCodegenType == 4 >
									<tr>
										<td>${column.name}：</td>
										<td>
											<input id="${column.propertyName}" name="${column.propertyName}"  style="width:140px" class="mini-textbox"  emptyText="请输入${column.name!}"  onenter="search" <#if (column.searchRequired??  && column.searchRequired ==1)>required="true"</#if> />
										</td>
									</tr>
								</#if>
								
								<#if column.columnCodegenType == 5 || column.columnCodegenType == 6>
									<tr>
										<td>${column.name}：</td>
										<td>
											 <input id="${column.propertyName}" name="${column.propertyName}" allowNull="true" value="null" style="width:140px" class="mini-spinner" minValue="-999999999999" <#if column.columnCodegenType == 6>format="n4"</#if> <#if (column.searchRequired?? && column.searchRequired ==1)>required="true"</#if> />
										</td>
									</tr>
								</#if>
								
								<#if column.columnCodegenType == 7>
									<tr>
										<td>${column.name}(开始)：</td>
										<td>
											<input id="${column.propertyName}Begin" name="${column.propertyName}Begin" class="mini-datepicker" style="width:140px;" nullValue="null" <#if column.columnCodegenFormat??> format="${column.columnCodegenFormat}" timeFormat="HH:mm:ss"</#if>  showTime="true" showOkButton="true" showClearButton="false"  emptyText="请输入" <#if (column.searchRequired?? && column.searchRequired ==1)>required="true"</#if> />
										</td>
									</tr>
									<tr>
										<td>${column.name}(结束)：</td>
										<td>
											<input id="${column.propertyName}End" name="${column.propertyName}End" class="mini-datepicker" style="width:140px;" nullValue="null" <#if column.columnCodegenFormat??> format="${column.columnCodegenFormat}" timeFormat="HH:mm:ss"</#if>  showTime="true" showOkButton="true" showClearButton="false"  emptyText="请输入" <#if (column.searchRequired??  && column.searchRequired ==1)>required="true"</#if> />
										</td>
									</tr>
								</#if>

								<#if column.columnCodegenType == 10>
									<tr>
										<td>${column.name}：</td>
										<td>
											<input id="${column.propertyName}" name="${column.propertyName}" class="mini-datepicker" style="width:140px;" nullValue="null" <#if column.columnCodegenFormat??> format="${column.columnCodegenFormat}" timeFormat="HH:mm:ss"</#if>  showTime="true" showOkButton="true" showClearButton="false"  emptyText="请输入" <#if (column.searchRequired?? && column.searchRequired ==1)>required="true"</#if> />
										</td>
									</tr>
								</#if>
								
								<#if column.columnCodegenType == 9>
									<tr>
										<td>${column.name}：</td>
										<td>
											 <input id="${column.propertyName}" name="${column.propertyName}" class="mini-combobox" style="width:140px" valueField="${column.selectorValueCols }" textField="${column.selectorTextCols}" showNullItem="true" nullItemText="请选择..." emptyText="请选择..."  <#if (column.searchRequired??  && column.searchRequired ==1 )>required="true"</#if>  />
										</td>
									</tr>
								</#if>
								
								
							</#if>
						</#if>
						</#list>

					</table>
					<div style="text-align:center;padding:10px;">
						<a class="mini-button" onclick="search()" iconCls="icon-search" style="width:60px;margin-right:20px;">查询</a>
						<a class="mini-button" onclick="clear()" iconCls="icon-cancel" style="width:60px;margin-right:20px;">清空</a>
					</div>
				</div>
    </div>
    <div showCollapseButton="true">
    	<div id="tabs1" class="mini-tabs" activeIndex="0" plain="false" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
  			
  			<div title="${definition.name}">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
								<#if buttonList??>
									<#list buttonList as btn>
										<#if btn.type?? && btn.type==1>
											<a class="mini-button" iconCls="icon-node" <#if btn.eventName??>${btn.eventName}='<#if btn.eventFunction??>${btn.eventFunction}</#if>'</#if> >${btn.name}</a>
											<#if btn.btnScript??>
											<script type="text/javascript">
												${btn.btnScript}
											</script>
											</#if>
										</#if>
									</#list>
								</#if>
								<span class="separator"></span>
								<#if (definition.canExport?? && definition.canExport == 1)>
									<a id="exportFile" class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
								</#if>
								
								<#if (definition.canImport?? && definition.canImport == 1)>
									<a id="importData" class="mini-button" iconCls="icon-upload" onclick="importData()">导入</a>
								</#if>
								<#-- 志出txt、pdf、excel等
									<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:74px" value="1" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=report_export_file_type&enable=1" />
								 -->
							</td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
					<div id="${definition.code}" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
						<#-- 常规分页显示默认算total值  -->
						<#if (definition.showPager?? && definition.showPager == 1)>
							showPager="true"  
							
							<#if definition.pageSizeList?? >
									sizeList="[${definition.pageSizeList}]" 
							<#else>
									sizeList="[20,50,100,200,500]"
							</#if>
							
							<#if definition.pageSize??>
								pageSize="${definition.pageSize}"
							<#else>
								pageSize="20"
							</#if>
						<#else>
							showPager="false"
						</#if>
						
						<#-- 非常规分页显示,不计算total值  -->
						<#if (definition.showPager?? && definition.showPager == 1) && (definition.countRequired?? && definition.countRequired == 0) >
							showPageInfo="false" showPageIndex="false" showPagerButtonText="true" sizeText="&lt;span id='pageIndexSpan'&gt;第1页&lt;/span&gt;&nbsp;"
						</#if>
						
						url="<#noparse>${pageContext.request.contextPath}</#noparse>/report/definition/search"  idField="id" >
						<div property="columns">
							<div type="checkcolumn" ></div>
							<#list columnList as column>
								<#if (column.columnCodegenType?? && column.columnCodegenType == 2)>
									<div type="comboboxcolumn" field="${column.propertyName}" width="${column.width}" headerAlign="center" visible="<#if (column.hidde?? && column.hidde == 1)>false<#else>true</#if>" align="<#if (column.alignType?? && column.alignType == 1)>left</#if><#if (column.alignType?? && column.alignType == 2)>center</#if><#if (column.alignType?? &&column.alignType == 3)>right</#if>" <#if (column.allowSort?? && column.allowSort == 1)>allowSort="true"</#if>>${column.name}
											<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="${column.selectorTextCols}" valueField="${column.selectorValueCols }" url="<#noparse>${pageContext.request.contextPath}</#noparse>/dictionary/option?code=${column.selectorDataFrom}" />
									</div>
								<#elseif (column.columnCodegenType?? && column.columnCodegenType == 9)>
									<div type="comboboxcolumn" field="${column.propertyName}" width="${column.width}" headerAlign="center" visible="<#if (column.hidde?? && column.hidde == 1)>false<#else>false</#if>" align="<#if (column.alignType?? && column.alignType == 1)>left</#if><#if (column.alignType?? && column.alignType == 2)>center</#if><#if (column.alignType?? &&column.alignType == 3)>right</#if>" <#if (column.allowSort?? && column.allowSort == 1)>allowSort="true"</#if>>${column.name}
											<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="${column.selectorTextCols}" valueField="${column.selectorValueCols }" data='${column.selectorDataFrom }' />
									</div>
								<#else>
									<div field="${column.propertyName}" width="${column.width}" headerAlign="center" visible="<#if (column.hidde?? && column.hidde == 1)>false<#else>true</#if>" <#if (column.allowSort?? && column.allowSort == 1)>allowSort="true"</#if>  align="<#if (column.alignType?? && column.alignType == 1)>left</#if><#if (column.alignType?? && column.alignType == 2)>center</#if><#if (column.alignType?? &&column.alignType == 3)>right</#if>" >${column.name}</div>
								</#if>
							</#list>
						</div>
					</div>
		        </div>
		    </div>    

        </div>
    </div>        
</div>
    
    <script type="text/javascript">
    mini.parse();
	var grid = mini.get("${definition.code}");
	var form = new mini.Form("form1");
	
<#list columnList as column>
	<#if column.columnCodegenType??>
		<#if column.columnCodegenType == 9>
		
	var ${column.propertyName} = mini.get("${column.propertyName}");
	${column.propertyName}.setData(${column.selectorDataFrom});
	
		</#if>
	</#if>
</#list>

	function ajaxGetTemplateExists(type,callback) {
		var data = {};
		data.definitionId= '${definition.id}';
		data.type = type; //100=导入模板 200=导出模板
        $.ajax({ 
            url: "<#noparse>${pageContext.request.contextPath}</#noparse>/report/export_template/list",
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
            url: "<#noparse>${pageContext.request.contextPath}</#noparse>/apps/default/admin/report/definition/upload.jsp",
            title: "选择数据文件",
            width: 650,
            height: 400,
			onload : function() {
				var iframe = this.getIFrameEl();
				var data = {
					action : "importData",
					definitionId: '${definition.id}'
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
		
    	<#list columnList as column>
    		<#if (column.searchType?? && column.searchType==1)>
		    	<#if column.columnCodegenType??>
		    		<#if column.columnCodegenType == 5 || column.columnCodegenType == 6>
			    		if(data.${column.propertyName} == null) {
							data.${column.propertyName} = "";
						}
		    		</#if>
		    		<#if column.columnCodegenType == 7>
		    			data.${column.propertyName}Begin =  mini.get('${column.propertyName}Begin').text;
		    			data.${column.propertyName}End =  mini.get('${column.propertyName}End').text;
		    		</#if>
		    	</#if>
		    </#if>
		</#list>
		
		 <#if (definition.showPager?? && definition.showPager==1)>
		 	data.pageIndex = grid.pageIndex;
		 	data.pageSize = grid.pageSize;
		 </#if>
		data.reportDefinitionId=${definition.id};
		
		download(data);

	}
	
    function download(data) {
 	   loading();
 	   var url = '<#noparse>${pageContext.request.contextPath}</#noparse>/report/definition/export?1=1';
   		
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
 	               a.download = '${definition.name}.xlsx';
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
            url: "<#noparse>${pageContext.request.contextPath}</#noparse>/apps/default/admin/sys/tenant/selector_tenant.jsp",
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
		
    	<#list columnList as column>
    		<#if (column.searchType?? && column.searchType==1)>
		    	<#if column.columnCodegenType??>
		    		<#if column.columnCodegenType == 5 || column.columnCodegenType == 6>
			    		if(data.${column.propertyName} == null) {
							data.${column.propertyName} = "";
						}
		    		</#if>
		    		<#if column.columnCodegenType == 7>
		    			data.${column.propertyName}Begin =  mini.get('${column.propertyName}Begin').text;
		    			data.${column.propertyName}End =  mini.get('${column.propertyName}End').text;
		    		</#if>
		    		<#if column.columnCodegenType == 10>
		    			data.${column.propertyName} =  mini.get('${column.propertyName}').text;
	    			</#if>
		    	</#if>
		    </#if>
    	</#list>
    	
    	data.reportDefinitionId=${definition.id};
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
    	
    	<#-- 非常规则分页，（不计算总记录数)，底部分页显示页码 -->
    	<#if (definition.showPager?? && definition.showPager==1) && (definition.countRequired?? && definition.countRequired == 0) >
	        grid.on("load", function (e) {
	        	document.getElementById("pageIndexSpan").innerHTML="<span class='mini-button-text'>第"+(e.pageIndex+1)+"页</span>";
	        });		
    	</#if>
    	
		grid.on("drawcell", function (e) {// 如果有日期类型，转换为人工可讯形式
		   var record = e.record,
		   column = e.column,
		   field = e.field,
		   value = e.value;
		   
		   if (mini.isDate(value)) {
	    		e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm:ss");
		   }
		   
		   <#--
		   if (mini.isDate(value)) {
	    		e.cellHtml = mini.formatDate(value, "${columnCodegenFormat}");
		   }
		   
	    	<#list columnList as column>
		    	<#if column.columnCodegenType?? && column.columnCodegenType == 7 >
		    		<#assign columnCodegenFormat = 'yyyy-MM-dd'/>
		    		
		    		<#if column.columnCodegenFormat??>
		    			<#assign columnCodegenFormat = column.columnCodegenFormat>
		    		</#if>
		    		
			    	
			    	<#break>
		    	</#if>
	    	</#list>
	     	-->
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
    ${definition.reportScript}
    </script>

</body>
</html>