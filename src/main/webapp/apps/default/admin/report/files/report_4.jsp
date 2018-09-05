<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>用户薪资表</title>

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
						<tr>
							<td>关键字 ：</td>
							<td>
								<input id="reportDefinitionId" name="reportDefinitionId" class="mini-hidden" value="4"/>
								<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
							</td>
						</tr>
								
								
									<tr>
										<td>sex：</td>
										<td>
											<input id="sex" name="sex" class="mini-combobox" style="width:140px" valueField="value" textField="name" showNullItem="true" nullItemText="请选择..." emptyText="请选择..."  url="${pageContext.request.contextPath}/dictionary/option?code=sex"  />
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
  			
  			<div title="用户薪资表">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-download" onclick="exportData()" id="exportFile">导出</a>
								<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:74px" value="1" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="/dictionary/option?code=report_export_file_type&enable=1" />
								<!-- 
								<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
								 -->
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
					<div id="hr_user_salary" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" showPager="true"  sizeList="[20,30,500]"  pageSize="20" 
						url="${pageContext.request.contextPath}/report/definition/search"  idField="id" >
						<div property="columns">
							<div type="checkcolumn" ></div>
									<div field="id" width="" headerAlign="center" visible="true"   align="" >id</div>
									<div field="code" width="" headerAlign="center" visible="true"   align="" >code</div>
									<div field="name" width="" headerAlign="center" visible="true"   align="" >name</div>
									<div field="birthday" width="" headerAlign="center" visible="true"   align="" >birthday</div>
									<div field="salary" width="" headerAlign="center" visible="true"   align="" >salary</div>
									<div field="eable" width="" headerAlign="center" visible="true"   align="" >eable</div>
									<div field="remark" width="" headerAlign="center" visible="true"   align="" >remark</div>
									<div field="appCode" width="" headerAlign="center" visible="true"   align="" >app_code</div>
									<div field="gid" width="" headerAlign="center" visible="true"   align="" >gid</div>
									<div field="createTime" width="" headerAlign="center" visible="true"   align="" >create_time</div>
									<div field="updateTime" width="" headerAlign="center" visible="true"   align="" >update_time</div>
									<div field="sn" width="" headerAlign="center" visible="true"   align="" >sn</div>
									<div type="comboboxcolumn" field="sex" width="120" headerAlign="center" visible="false" align="center" >sex
											<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="/dictionary/option?code=enable_status" />
									</div>
									<div field="222" width="120" headerAlign="center" visible="true"   align="" >111</div>
						</div>
					</div>
		        </div>
		    </div>    

        </div>
    </div>        
</div>
    
    <script type="text/javascript">
    mini.parse();
	var grid = mini.get("hr_user_salary");
	var form = new mini.Form("form1");
	
	
	function checkCanBeExportFile() { //检查是否有上传导出模板,如果有，则显示导出按钮
		var data = {};
		data.definitionId= '4';
		data.type = 200; //100=导入模板 200=导出模板
        $.ajax({ 
            url: "/report/export_template/list",
            data: data,
            type: "post",
            success: function (text) {
             	 
            },
            error: function (jqXHR, textStatus, errorThrown) {
                mini.alert("请求错误："+jqXHR.responseText);
            }
        }); 
	}
	
	function checkCanBeImportFile() { //检查是否有上传导入模板,如果有，则显示导入按钮
		
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
		
	
		data.reportDefinitionId=4;
		
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
 	               a.download = '用户薪资表.xlsx';
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
		
    	
    	data.reportDefinitionId=4;
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