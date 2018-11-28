<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>工时记录</title>

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
								<input id="reportDefinitionId" name="reportDefinitionId" class="mini-hidden" value=""/>
								<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
							</td>
						</tr>
						<tr>
							<td>用户编码：</td>
							<td>
								<input id="userCode" name="userCode"  style="width:140px" class="mini-textbox"  emptyText="请输入编码"  onenter="search"  />
							</td>
						</tr>
						<tr>
							<td>用户名称：</td>
							<td>
								<input id="userName" name="userName"  style="width:140px" class="mini-textbox"  emptyText="请输入姓名"  onenter="search"  />
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
  			
  			<div title="工时记录">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-add" onclick="edit('add')">新增</a> 
								<a class="mini-button" iconCls="icon-edit" onclick="edit('edit')">修改</a>
								<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
							</td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
					<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" showPager="false"
						url="${pageContext.request.contextPath}/rst/user_work_record/page"  idField="id" autoLoad="true">
						<div property="columns">
							<div type="checkcolumn" ></div>
							<!-- <div field="id" width="60" headerAlign="center">ID</div> -->
							
							<div field="userCode"  headerAlign="center">用户编码</div>
							<div field="userName"  headerAlign="center">用户姓名</div>
							
							<!--
							<div type="comboboxcolumn" field="type" width="80" headerAlign="center" align="center" allowSort="true">考勤类型
								<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dic_kaoqin_type" />
							</div>
							
							 <div field="workingHours" headerAlign="center">工时（小时）</div> -->
							
							<div field="extraHours" headerAlign="center">加班时长</div>
							<div type="comboboxcolumn" field="extraShiftType" width="80" headerAlign="center" align="center" allowSort="true">加班班班次
								<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dic_shift_type_bc" />
							</div>
							
							<div field="leaveHours" headerAlign="center">请假时长</div>
							<div type="comboboxcolumn" field="leaveShiftType" width="80" headerAlign="center" align="center" allowSort="true">请假班次
								<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dic_shift_type_bc" />
							</div>
							<div type="comboboxcolumn" field="leaveType" width="80" headerAlign="center" align="center" allowSort="true">请假类型
								<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dic_leave_type" />
							</div>
							
							
							<div field="remark"  headerAlign="center">请假原因</div>
							<div field="createTime" width="150" dateFormat="yyyy-MM-dd HH:m:ss" align="center" headerAlign="center">创建日期</div>
							<div field="updateTime" width="150" dateFormat="yyyy-MM-dd HH:m:ss" align="center" headerAlign="center">更新日期</div>  
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
	
	function remove() {
		var row = grid.getSelecteds();
		if (!row) {
			mini.alert("请选中一条以上的记录");
		}
		var ids = [];
		for(var i=0;i<row.length;i++) {
			ids.push(row[i].id);
		}
		mini.confirm("确定删除？", "确定？",
			function (action) {
				if (action == "ok") {
					$.ajax({
						'url': "${pageContext.request.contextPath}/rst/user_work_record/delete?ids="+ids.join(","),
						type: 'post', dataType:'JSON',
						success: function (json) {
							mini.alert("删除成功");
							grid.reload();
						},
						error : function(data) {
					  		//mini.alert(data.status + " : " + data.statusText + " : " + data.responseText);
					  		mini.alert(data.responseText);
						}
					});
				}
			}
		);
	}
	
	function edit(action) {
		var row = grid.getSelected();
		if ('edit' == action ) {
			if (!row) {
				mini.alert("请选择一条记录");
				return ;
			}
		}	
		
		mini.open({
			url : "${pageContext.request.contextPath}/apps/default/admin/rst/user_work_record/edit.jsp",
			title : "编辑",
			width : 490,
			height : 450,
			onload : function() {
				var iframe = this.getIFrameEl();
				var data = {
					action : action
				};
				if ('edit' == action) {
					data.id = row.id 
				}
				iframe.contentWindow.SetData(data);
			},
			ondestroy : function(action) {
				grid.reload();
			}
		});
	}
 
	
	function refresh() {
        grid.reload();
	}
	
  
    function search() {
    	var data = form.getData();
    	grid.load(data);
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