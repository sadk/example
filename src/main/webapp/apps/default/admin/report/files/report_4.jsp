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
										<td>编码：</td>
										<td>
											<input id="code" name="code"  style="width:140px" class="mini-textbox"  emptyText="请输入编码"  onenter="search"  />
										</td>
									</tr>
								
								
								
								
								
								
								
								
								
									<tr>
										<td>姓名：</td>
										<td>
											<input id="name" name="name"  style="width:140px" class="mini-textbox"  emptyText="请输入姓名"  onenter="search" required="true" />
										</td>
									</tr>
								
								
								
								
								
								
								
								
								
								
									<tr>
										<td>薪水：</td>
										<td>
											 <input id="salary" name="salary" allowNull="true" value="null" style="width:140px" class="mini-spinner" minValue="-999999999999" format="n4"  />
										</td>
									</tr>
								
								
								
								
								
								
								
									<tr>
										<td>是否启用：</td>
										<td>
											<input id="eable" name="eable" class="mini-combobox" style="width:140px" valueField="value" textField="name" showNullItem="true" nullItemText="请选择..." emptyText="请选择..."  url="${pageContext.request.contextPath}/dictionary/option?code=sex"  />
										</td>
									</tr>
								
								
								
								
								
								
								
								
								
								
									<tr>
										<td>备注：</td>
										<td>
											<input id="remark" name="remark"  style="width:140px" class="mini-textbox"  emptyText="请输入备注"  onenter="search"  />
										</td>
									</tr>
								
								
								
								
								
								
								
								
								
								
								
									<tr>
										<td>生日(开始)：</td>
										<td>
											<input id="birthdayBegin" name="birthdayBegin" class="mini-datepicker" style="width:140px;" nullValue="null"  format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss"  showTime="true" showOkButton="true" showClearButton="false"  emptyText="请输入"  />
										</td>
									</tr>
									<tr>
										<td>生日(结束)：</td>
										<td>
											<input id="birthdayEnd" name="birthdayEnd" class="mini-datepicker" style="width:140px;" nullValue="null"  format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss"  showTime="true" showOkButton="true" showClearButton="false"  emptyText="请输入"  />
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
									<a id="importData" class="mini-button" iconCls="icon-upload" onclick="importData()">导入</a>
									<a id="exportFile" class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
									<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:74px" value="1" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="/dictionary/option?code=report_export_file_type&enable=1" />
									
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
									<div field="id" width="120" headerAlign="center" visible="true"   align="center" >用户ID</div>
									<div field="sex" width="120" headerAlign="center" visible="false"   align="center" >性别</div>
									<div field="code" width="120" headerAlign="center" visible="true"   align="center" >编码</div>
									<div field="name" width="120" headerAlign="center" visible="true" allowSort="true"  align="center" >姓名</div>
									<div field="salary" width="120" headerAlign="center" visible="true"   align="center" >薪水</div>
									<div type="comboboxcolumn" field="eable" width="120" headerAlign="center" visible="true" align="center" >是否启用
											<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="/dictionary/option?code=enable_status" />
									</div>
									<div field="remark" width="120" headerAlign="center" visible="true"   align="center" >备注</div>
									<div field="appCode" width="120" headerAlign="center" visible="true"   align="center" >app_code</div>
									<div field="gid" width="120" headerAlign="center" visible="true"   align="center" >gid</div>
									<div field="createTime" width="120" headerAlign="center" visible="true"   align="center" >create_time</div>
									<div field="updateTime" width="120" headerAlign="center" visible="true"   align="center" >update_time</div>
									<div field="sn" width="120" headerAlign="center" visible="true"   align="center" >sn</div>
									<div field="id" width="120" headerAlign="center" visible="true"   align="center" >id</div>
									<div field="sex" width="120" headerAlign="center" visible="true"   align="center" >sex</div>
									<div field="code" width="120" headerAlign="center" visible="true"   align="center" >code</div>
									<div field="name" width="120" headerAlign="center" visible="true"   align="center" >name</div>
									<div field="birthday" width="120" headerAlign="center" visible="true"   align="center" >birthday</div>
									<div field="salary" width="120" headerAlign="center" visible="true"   align="center" >salary</div>
									<div field="eable" width="120" headerAlign="center" visible="true"   align="center" >eable</div>
									<div field="remark" width="120" headerAlign="center" visible="true"   align="center" >remark</div>
									<div field="appCode" width="120" headerAlign="center" visible="true"   align="center" >app_code</div>
									<div field="gid" width="120" headerAlign="center" visible="true"   align="center" >gid</div>
									<div field="createTime" width="120" headerAlign="center" visible="true"   align="center" >create_time</div>
									<div field="updateTime" width="120" headerAlign="center" visible="true"   align="center" >update_time</div>
									<div field="sn" width="120" headerAlign="center" visible="true"   align="center" >sn</div>
									<div field="birthday" width="120" headerAlign="center" visible="true"   align="center" >生日</div>
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
	

	function ajaxGetTemplateExists(type,callback) {
		var data = {};
		data.definitionId= '4';
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
					definitionId: '4'
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
		
	    		if(data.salary == null) {
					data.salary = "";
				}
    			data.birthdayBegin =  mini.get('birthdayBegin').text;
    			data.birthdayEnd =  mini.get('birthdayEnd').text;
	
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
		
		    		if(data.salary == null) {
						data.salary = "";
					}
	    			data.birthdayBegin =  mini.get('birthdayBegin').text;
	    			data.birthdayEnd =  mini.get('birthdayEnd').text;
    	
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