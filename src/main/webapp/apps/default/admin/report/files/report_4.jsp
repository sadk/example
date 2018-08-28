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
    <div size="240" showCollapseButton="true">
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
										<td>姓名：</td>
										<td>
											<input id="name" name="name"  style="width:140px" class="mini-textbox"  emptyText="请输入姓名"  onenter="search"  />
										</td>
									</tr>
								
								
								
								
								
								
								
								
								
								
								
								
								
									<tr>
										<td>性别：</td>
										<td>
											 <input id="sex" name="sex" class="mini-combobox" style="width:140px" valueField="id" textField="name" showNullItem="true" nullItemText="请选择..." emptyText="请选择..."    />
										</td>
									</tr>
								
								
								
								
								
									<tr>
										<td>编码：</td>
										<td>
											<input id="code" name="code"  style="width:140px" class="mini-textbox"  emptyText="请输入编码"  onenter="search" required="true" />
										</td>
									</tr>
								
								
								
								
								
								
								
								
									<tr>
										<td>是否启用：</td>
										<td>
											<input id="eable" name="eable" class="mini-combobox" style="width:140px" valueField="value" textField="name" showNullItem="true" nullItemText="请选择..." emptyText="请选择..."  url="${pageContext.request.contextPath}/dictionary/option?code=enable_status"  />
										</td>
									</tr>
								
								
								
								
								
								
								
								
								
								
								
									<tr>
										<td>薪水：</td>
										<td>
											 <input id="salary" name="salary" allowNull="true" value="null" style="width:140px" class="mini-spinner" minValue="-999999999999" format="n4"  />
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
							</td>
							<td style="white-space:nowrap;">
		                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="search()">查询</a>
		                    </td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
					<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
						url="${pageContext.request.contextPath}/application/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
						<div property="columns">
							<div type="checkcolumn" ></div>
								<div field="appCode" width="" headerAlign="center"   align="" >租户码</div>
								<div field="gid" width="" headerAlign="center"   align="" >全局码</div>
								<div field="createTime" width="" headerAlign="center"   align="" >创建时间</div>
								<div field="updateTime" width="" headerAlign="center"   align="" >更新时间</div>
								<div field="sn" width="" headerAlign="center"   align="" >排序号</div>
								<div field="id" width="120" headerAlign="center"   align="center" >用户ID</div>
								<div field="name" width="120" headerAlign="center"   align="left" >姓名</div>
								<div field="sex" width="120" headerAlign="center" allowSort="true"  align="center" >性别</div>
								<div field="code" width="" headerAlign="center"   align="" >编码</div>
								<div field="eable" width="120" headerAlign="center" allowSort="true"  align="center" >是否启用</div>
								<div field="salary" width="" headerAlign="center"   align="" >薪水</div>
								<div field="remark" width="" headerAlign="center"   align="" >备注</div>
								<div field="birthday" width="120" headerAlign="center" allowSort="true"  align="center" >生日</div>
							<!-- 
							<div field="tenantName" width="80" headerAlign="center" allowSort="true" align="left" >租户</div>
							<div field="name" width="160" headerAlign="center" allowSort="true" align="left" >系统名称</div>
							<div field="code" width="80" headerAlign="center" allowSort="true" align="left">系统编码</div>
							<div field="sn" width="60" headerAlign="center" allowSort="true" align="right">排序号</div>
							<div field="remark" width="160" headerAlign="center" allowSort="true" align="left">备注</div>
							<div field="enableDesc" width="60" headerAlign="center" allowSort="true" align="center">是否启用</div>
							
							<div field="gid" width="160" headerAlign="center" allowSort="true" align="left">全局编码</div>
							<div field="createTimeDesc" width="100" headerAlign="center" allowSort="true" align="center" >创建时间</div>
							<div field="updateTimeDesc" width="100" headerAlign="center" allowSort="true" align="center">更新时间</div>
							 -->
						</div>
					</div>
		        </div>
		    </div>    

        </div>
    </div>        
</div>
    
    <script type="text/javascript">
    mini.parse();
	var grid = mini.get("datagrid1");
	var form = new mini.Form("form1");
	
	var sex = mini.get("sex");
	sex.setData([{id: 1,"name":"男"},{id: 0, "name":"女"}]);
	
	
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
     
    function search() {
    	var data = form.getData();
    	form.validate();
		if(form.isValid() == false) return;
		
	    			data.birthdayBegin =  mini.get('birthdayBegin').text;
	    			data.birthdayEnd =  mini.get('birthdayEnd').text;
    	
        $.ajax({
            url: "/report/definition/search",
            data: data,
            type: "post",
            success: function (text) {
            	 
            	
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
            }
        });
    }
    
    function clear() {
    	form.clear();
    }
    </script>

</body>
</html>