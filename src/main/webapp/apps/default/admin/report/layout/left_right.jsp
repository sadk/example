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
						<tr>
							<td>关键字 ：</td>
							<td>
								<input id="reportDefinitionId" name="reportDefinitionId" class="mini-hidden" value="${definition.id}"/>
								<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
							</td>
						</tr>
						<#-- 字段的代码生成器类型:1=选择器 2=下拉框(字典) 3=外键  4=文本框 5=整型框 6=精度型框 7=日期 8=文件  9=下拉框(常量JSON) -->
						<#list columnList as column>
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
								
								
								<#if column.columnCodegenType == 9>
									<tr>
										<td>${column.name}：</td>
										<td>
											 <input id="${column.propertyName}" name="${column.propertyName}" class="mini-combobox" style="width:140px" valueField="${column.selectorValueCols }" textField="${column.selectorTextCols}" showNullItem="true" nullItemText="请选择..." emptyText="请选择..."  <#if (column.searchRequired??  && column.searchRequired ==1 )>required="true"</#if>  />
										</td>
									</tr>
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
								<#if btnList??>
									<#list btnList as btn>
										<#if btn.type?? && btn.type==1>
											<a class="mini-button" iconCls="icon-node" <#if btn.eventName??>${btn.eventName}='<#if btn.eventFunction??>${btn.eventFunction}</#if>'</#if> >${btn.name}</a>
										</#if>
									</#list>
								</#if>
								<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
								<#if (column.canExport?? && column.canExport == 1)>
								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
								<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
								<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
								</#if>
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
					<div id="${definition.code}" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" <#if (definition.showPager?? && definition.showPager==1)>showPager="true"  <#if definition.pageSizeList?? >sizeList="[${definition.pageSizeList}]" <#else>sizeList="[20,50,100,200,500]"</#if> <#if definition.pageSize??>pageSize="${definition.pageSize}"<#else>pageSize="20"</#if> <#else>showPager="false"</#if>
						url="<#noparse>${pageContext.request.contextPath}</#noparse>/report/definition/search"  idField="id" >
						<div property="columns">
							<div type="checkcolumn" ></div>
							<#list columnList as column>
								<#if (column.columnCodegenType?? && column.columnCodegenType == 2)>
									<div type="comboboxcolumn" field="${column.propertyName}" width="${column.width}" headerAlign="center" visible="<#if (column.hidde?? && column.hidde == 0)>false<#else>true</#if>" align="<#if (column.alignType?? && column.alignType == 1)>left</#if><#if (column.alignType?? && column.alignType == 2)>center</#if><#if (column.alignType?? &&column.alignType == 3)>right</#if>" <#if (column.allowSort?? && column.allowSort == 1)>allowSort="true"</#if>>${column.name}
											<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="${column.selectorTextCols}" valueField="${column.selectorValueCols }" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" />
									</div>
								<#elseif (column.columnCodegenType?? && column.columnCodegenType == 9)>
									<div type="comboboxcolumn" field="${column.propertyName}" width="${column.width}" headerAlign="center" visible="<#if (column.hidde?? && column.hidde == 0)>false<#else>true</#if>" align="<#if (column.alignType?? && column.alignType == 1)>left</#if><#if (column.alignType?? && column.alignType == 2)>center</#if><#if (column.alignType?? &&column.alignType == 3)>right</#if>" <#if (column.allowSort?? && column.allowSort == 1)>allowSort="true"</#if>>${column.name}
											<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="${column.selectorTextCols}" valueField="${column.selectorValueCols }" data='${column.selectorDataFrom }' />
									</div>
								<#else>
									<div field="${column.propertyName}" width="${column.width}" headerAlign="center" visible="<#if (column.hidde?? && column.hidde == 0)>false<#else>true</#if>" <#if (column.allowSort?? && column.allowSort == 1)>allowSort="true"</#if>  align="<#if (column.alignType?? && column.alignType == 1)>left</#if><#if (column.alignType?? && column.alignType == 2)>center</#if><#if (column.alignType?? &&column.alignType == 3)>right</#if>" >${column.name}</div>
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
	
	function refresh() {
		var messageid = mini.loading("Loading, Please wait ...", "Loading");
        setTimeout(function () {
            mini.hideMessageBox(messageid);
        }, 1000);
        grid.reload();
	}
	
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
     
    function search() {
    	var data = form.getData();
    	form.validate();
		if(form.isValid() == false) return;
		
    	<#list columnList as column>
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
    	</#list>
    	
    	data.reportDefinitionId=${definition.id};
    	grid.load(data)
    	/*
        $.ajax({ 
            url: "${pageContext.request.contextPath}/report/definition/search",
            data: data,
            type: "post",
            success: function (text) {
            	
            	<#if (definition.showPager?? && definition.showPager == 1) >
					if(text) {
						grid.setData(text.data)
	            	}
            	</#if>
            	
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
    </script>

</body>
</html>