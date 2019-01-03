<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>个人视频表</title>

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
								<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
							</td>
						</tr>
 						<tr>
							<td>昵称 ：</td>
							<td>
								<input id="nickName" name="nickName" style="width:140px" class="mini-textbox" emptyText="请输入昵称搜索" style="width: 150px;" onenter="search"/>
							</td>
						</tr>
						<tr>
							<td>姓名 ：</td>
							<td>
								<input id="realName" name="realName" style="width:140px" class="mini-textbox" emptyText="请输入姓名搜索" style="width: 150px;" onenter="search"/>
							</td>
						</tr>
						<tr>
							<td>手机号码：</td>
							<td>
								<input id="phone" name="phone" style="width:140px" class="mini-textbox" emptyText="请输入手机号码搜索" style="width: 150px;" onenter="search"/>
							</td>
						</tr>
						<tr>
							<td>上架状态：</td>
							<td>
								<input id="status" name="status" class="mini-combobox"  style="width:140px" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_video_status" />
							</td>
						</tr>
						<tr>
							<td>审核状态：</td>
							<td>
								<input id="checkStatus" name="checkStatus" class="mini-combobox"  style="width:140px" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_video_check_status" />
							</td>
						</tr>
						<tr>
							<td>备注：</td>
							<td>
								<input id="remark" name="remark" class="mini-textbox"  style="width:140px"   emptyText="请输入备注" textField="name"   />
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
  			
  			<div title="视频列表">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-edit" onclick="edit()">审核上架</a>
								<a class="mini-button" iconCls="icon-node" onclick="view()">查看</a>
								<!-- <a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a> -->
								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
							</td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
					<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" showPager="true"
						url="${pageContext.request.contextPath}/rst/personal_video_info/page"  idField="id" autoLoad="true">
						<div property="columns">
							<div type="checkcolumn" ></div>
									<div field="id" headerAlign="center" align="center" width="80">ID</div>
									<div field="code" headerAlign="center" align="center" width="150">视频编码</div>
									<div field="nickName" headerAlign="center" align="left" >昵称</div>
									<div field="realName" headerAlign="center" align="center" >姓名</div>
									<div field="phone" headerAlign="center" align="center" >手机号</div>
									
									<!-- 
									<div field="userCode" headerAlign="center" align="" >用户编码</div> 
									<div field="activityId" headerAlign="center" align="" >activity_id</div> 
									<div field="pictureUrl" headerAlign="center" align="" >picture_url</div> 
									<div field="videoUrl" headerAlign="center" align="" >video_url</div> 
									<div field="positionCode" headerAlign="center" align="" >职位编码</div>
									-->
									
									<div type="comboboxcolumn" field="status" width="120" headerAlign="center" align="center" allowSort="true">上架状态
										<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_video_status" />
									</div>
									<div type="comboboxcolumn" field="checkStatus" width="120" headerAlign="center" align="center" allowSort="true">审核状态
										<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_video_check_status" />
									</div>
									<div field="uploadTime" width="180" dateFormat="yyyy-MM-dd HH:mm:ss" align="center" headerAlign="center">上传时间</div>
									<div field="reviewTime" width="180" dateFormat="yyyy-MM-dd HH:mm:ss" align="center" headerAlign="center">审核时间</div>     
									<div field="reason" width="180" headerAlign="center" align="left" >下线原因</div>
									<div field="remark" width="120" headerAlign="center" align="left" >备注</div>
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
	
	grid.on("rowdblclick",function(sender){
		openEdit(sender.row);
		//console.log(sender.row)
	})
	
	function view() {
		var row = grid.getSelected();
		if(!row) {
			mini.alert("请选择一个视频记录");
			return ;
		}
		window.open(row.videoUrl,"_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width="+screen.width+", height="+screen.height+"");
		//window.open(row.videoUrl,"_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=600, height=500");
		
	}
	
	function remove() {
		var rows = grid.getSelecteds();
		if(rows.length == 0) {
			mini.alert("请勾选删除视频");
			return ;
		}
		
		var idList = new Array();
		for (var i=0;i<rows.length; i++) {
			idList.push(rows[i].id);
		}
		
		var data = {};
		data.ids = idList.join(",");
		
		mini.confirm("确定删除？", "确定？",
			function (action) {
				if (action == "ok") {
					$.ajax({
						url : "${pageContext.request.contextPath}/rst/personal_video_info/delete",
						dataType: 'json', type : 'post',
						data: data,
						success : function(text) {
							grid.reload();
						}
					});					
				}
		});
		
		

	}
	
 	function edit() {
 		var row = grid.getSelected();
 		if (!row) {
 			mini.alert("请选择一条记录");
 			return ;
 		}
 		openEdit(row);
 		 
 	}
 	
 	function openEdit(data) {
		mini.open({
			url : "${pageContext.request.contextPath}/apps/default/admin/rst/personal_video_info/edit.jsp",
			title : "审核",
			width : 490,
			height : 220,
			onload : function() {
				var iframe = this.getIFrameEl();
				
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