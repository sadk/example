<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>职位管理</title>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/pagertree.js" ></script>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<style type="text/css">
			body {
				margin: 0;
				padding: 0;
				border: 0;
				width: 100%;
				height: 100%;
				overflow: hidden;
			}
		</style>
	</head>
	<body>
		<div class="mini-splitter" style="width:100%;height:100%; overflow:auto;">
			<div size="250" showCollapseButton="true">
			    <div class="mini-panel" showToolbar="true" showHeader="false" style="width:100%;height:100%;">
				     
				    <!--body-->
				    <div style="padding-left:3px;padding-bottom:5px;">
			            <div id="form1" style="padding:5px;">
							<table>						
											<tr>
												<td>关键字 ：</td>
												<td>
													<input id="key" name="key"  class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
												</td>
											</tr>
								
											<tr>
												<td>职位名称：</td>
												<td>
													<input id="name" name="name"   class="mini-textbox"  emptyText="请输入职位名称"  onenter="search"/>
												</td>
											</tr>
											
											<tr>
												<td>发布状态：</td>
												<td>
													<input id="status" name="status" class="mini-combobox" valueField="value" textField="name" showNullItem="true" nullItemText="请选择..." emptyText="请选择"  url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_publish_status" />
												</td>
											</tr>
											
											<tr>
												<td>发布平台：</td>
												<td>
													<input id="publishPlatfrom" name="publishPlatfrom" class="mini-combobox"   valueField="value" textField="name" showNullItem="true" nullItemText="请选择..." emptyText="请选择"  url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_publish_platform" />
												</td>
											</tr>
											<tr>
												<td>公司简称：</td>
												<td>
													<input id="companyShortName" name="companyShortName"   class="mini-textbox"  emptyText="请输入公司简称"  onenter="search"/>
												</td>
											</tr>
							</table>
					        <div style="text-align:center;padding:10px;">
								<a class="mini-button" onclick="search()" iconCls="icon-search" style="width:60px;margin-right:20px;">查询</a>
								<a class="mini-button" onclick="clear()" iconCls="icon-cancel" style="width:60px;margin-right:20px;">清空</a>
							</div>
					    </div>
					</div>
					
					
				</div>       
			</div>
			<div showCollapseButton="true">
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						    <div title="职位列表" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="edit('add')">新增</a>
												<a class="mini-button" iconCls="icon-edit" onclick="edit('edit')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
									<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="true"
										url="${pageContext.request.contextPath}/rst/position_definition/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
									    <div property="columns">
									        <div type="checkcolumn"></div>
									        <div field="companyShortName" width="120" headerAlign="center">企业名称</div>
									        <div field="name" width="250" headerAlign="center">职位名称</div>
									        <div field="code" width="120" headerAlign="center">职位编码</div>
									        <div field="intermediaryName" width="80" align="center" headerAlign="center">中介姓名</div>
									        <div field="intermediaryPhone" width="100" align="center" headerAlign="center">中介手机</div>
									        <div field="comprehensiveSalary" width="120" align="left" headerAlign="center">薪水</div>
									        <div field="workTime" width="80" align="left" headerAlign="center">作息时间</div>
									        
									        <!-- <div field="requiredSex" width="80" align="right" headerAlign="center">性别要求</div> -->
									        <div type="comboboxcolumn" field="requiredSex" width="80" headerAlign="center" align="center" allowSort="true">性别要求
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_sex_required" />
											</div>
											
									        
									        <div field="requiredAge" width="80" align="right" headerAlign="center">年龄要求</div>
									        
									        <!-- <div field="requiredEducation" width="80" align="right" headerAlign="center">学历要求</div> -->
									        
									        <div type="comboboxcolumn" field="requiredEducation" width="80" headerAlign="center" align="center" allowSort="true">学历要求
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_edu_required" />
											</div>
									        
									        <div field="requiredWorkYears" width="80" align="right" headerAlign="center">工作年限要求</div>
									        <div field="welfare" width="280" align="left" headerAlign="center">福利</div>
									        
									       <!--  <div field="status" width="80" align="center" headerAlign="center">发布状态</div> -->
									        <div type="comboboxcolumn" field="status" width="80" headerAlign="center" align="center" allowSort="true">发布状态
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_publish_status" />
											</div>
									        
									        <!-- <div field="requiredResume" width="80" align="center" headerAlign="center">是否需要简历</div> -->
									        <div type="comboboxcolumn" field="requiredResume" width="80" headerAlign="center" align="center" allowSort="true">是否需要简历
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_required" />
											</div>
											
											
									       <!--  <div field="publishPlatfrom" width="60" align="center" headerAlign="center">发布平台</div> -->
									        <div type="comboboxcolumn" field="publishPlatfrom" width="60" headerAlign="center" align="center" allowSort="true">发布平台
												<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rst_dict_publish_platform" />
											</div>
									        
									        <div field="interviewAddress" width="280" align="left" headerAlign="center">面试地址</div>
									        
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
									                  
									    </div>
									</div>
						        </div>
						    </div>
						</div>
					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							

							<div title="工作地址" refreshOnClick="true" name="tabReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addAddress()">添加</a>
												 
												<a class="mini-button" iconCls="icon-remove" onclick="removeAddress()">删除</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-reload" onclick="refreshAddress()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="datagrid2" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="false"
										url="${pageContext.request.contextPath}/rst/work_address/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
										<div property="columns">
											<div type="checkcolumn"></div>
									       <!--  <div field="code" width="120" headerAlign="center">地址编码</div> -->
									        <div field="provinceName" width="80" align="center" headerAlign="center">省份</div>
									        <div field="cityName" width="80" align="left" headerAlign="center">城市</div>
									        <div field="areaName" width="120" align="left" headerAlign="center">区域</div>
									        <div field="address" width="250" align="left" headerAlign="center">详细地址</div>
										</div>
									</div>
								</div>
							</div>
							

							<div title="职位视频" refreshOnClick="true" name="tabUserRoles">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-upload" onclick="addVideo()">上传</a>
												<!-- <a class="mini-button" iconCls="icon-edit" onclick="editVideo('edit')">编辑</a> -->
												<a class="mini-button" iconCls="icon-remove" onclick="removeVideo()">删除</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-reload" onclick="refreshVideo()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="datagrid4" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="false"
										url="${pageContext.request.contextPath}/rst/video/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
										<div property="columns">
											<div type="checkcolumn" ></div>
											 
											<div field="id" width="160" headerAlign="center" allowSort="true" align="center">编码</div>
											<div field="url" width="660" headerAlign="center" allowSort="true" align="left">视频地址</div>
											
											<div field="coverUrl" width="260" headerAlign="center" allowSort="true" align="left">视频封面</div>
											<div field="createDate" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
											<div field="updateDate" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">更新日期</div>
											
										</div>
									</div>
								</div>
							</div>


							<div title="企业Logo" refreshOnClick="true" name="tabUserRoles">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-upload" onclick="addImg('logo')">上传</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeImg('logo')">删除</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-reload" onclick="refreshImg('logo')">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="logoGrid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="false"
										  url="${pageContext.request.contextPath}/rst/position_definition/get_position_img" idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
										<div property="columns">
											<div type="checkcolumn" ></div>
											 
											<div field="code" width="160" headerAlign="center" allowSort="true" align="center">编码</div>
											<div field="urlCompanyLogo" width="660" headerAlign="center" allowSort="true" align="left">企业logo</div>
										
										</div>
									</div>
								</div>
							</div>
							
							
							<div title="职位封面" refreshOnClick="true" name="tabUserRoles">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-upload" onclick="addImg('cover')">上传</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeImg('cover')">删除</a>
												<span class="separator"></span>
												<a class="mini-button" iconCls="icon-reload" onclick="refreshImg('cover')">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="coverGrid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="false"
										url="${pageContext.request.contextPath}/rst/position_definition/get_position_img"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
										<div property="columns">
											<div type="checkcolumn" ></div>
											 
											<div field="code" width="160" headerAlign="center" allowSort="true" align="center">编码</div>
											<div field="urlPositionCover" width="660" headerAlign="center" allowSort="true" align="left">职位封面</div>
										
										</div>
									</div>
								</div>
							</div>
							
							<div title="职位投递记录" refreshOnClick="true" name="tabUserRoles">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<!-- <span class="separator"></span> -->
												<a class="mini-button" iconCls="icon-reload" onclick="refreshUserJobRecord()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="datagrid5" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="false"
										url="${pageContext.request.contextPath}/rst/user_job_record/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="userName" width="160" headerAlign="center" allowSort="true" align="center">求职者名称</div>
											<div field="userMobile" width="160" headerAlign="center" allowSort="true" align="center">求职者手机号</div>
											<div field="userWechat" width="160" headerAlign="center" allowSort="true" align="center">求职者微信号</div>
											<div field="positionName" width="160" headerAlign="center" allowSort="true" align="center">职位名称</div>
											
											<div field="salary" width="160" headerAlign="center" allowSort="true" align="center">工资</div>
											<div field="welfare" width="250" headerAlign="center" allowSort="true" align="center">基本福利</div>
											<div field="companyName" width="160" headerAlign="center" allowSort="true" align="center">公司名称</div>
											<div field="interviewPlace" width="250" headerAlign="center" allowSort="true" align="center">面试地点</div>
											<div field="interviewTime" width="160" headerAlign="center" allowSort="true" align="center">面试时间</div>
											<div field="interviewContactName" width="140" headerAlign="center" allowSort="true" align="center">面试联系人名称</div>
											
											<div field="interviewContactMobile" width="140" headerAlign="center" allowSort="true" align="center">面试联系人手机号</div>
											<div field="status" width="80" headerAlign="center" allowSort="true" align="center">面试状态</div>
											
											<div field="platfrom" width="160" headerAlign="center" allowSort="true" align="center">投递平台</div>
											
											<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
											<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">更新日期</div>
											
										</div>
									</div>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			mini.parse();
			
			var form = new mini.Form("#form1");
			function clear() {
				 form.clear();
			}
			
			var grid = mini.get("datagrid1"); 
			var gridAdress = mini.get("datagrid2"); 
			var gridVideo = mini.get("datagrid4"); 
			var gridRecord = mini.get("datagrid5");
			
			var logoGrid = mini.get("logoGrid")
			var coverGrid = mini.get("coverGrid");
			
			gridVideo.on("drawcell", function(e){
				var field = e.field;
				var row = e.row;
				if(typeof(row.url) != 'undefined' && row && field == "url"){
					e.cellHtml = "<a href='"+row.url+"' target='_blank'>"+row.url +"</a>" ;
				}
			})
			
			logoGrid.on("drawcell", function(e){
				var field = e.field;
				var row = e.row;
				if(typeof(row.urlCompanyLogo) != 'undefined' && row && field == "urlCompanyLogo"){
					e.cellHtml = "<a href='javascript:openUrl(\""+row.urlCompanyLogo+"\")'>"+row.urlCompanyLogo +"</a>" ;
				}
			})
			
			coverGrid.on("drawcell", function(e){
				var field = e.field;
				var row = e.row;
				if(typeof(row.urlPositionCover) != 'undefined' && row && field == "urlPositionCover"){
					e.cellHtml = "<a href='javascript:openUrl(\""+row.urlPositionCover+"\")'>"+row.urlPositionCover +"</a>" ;
				}
			})
			
			function openUrl(url) {
				var url= "${pageContext.request.contextPath}/apps/default/admin/rst/company/img_show.jsp?url="+url ;
	        	window.open(url,"_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width="+screen.width+", height="+screen.height+"");
			}
			
			
			function removeImg(type) { //删除职位的企业Logo或职位封面 
				var positionCode = null;
				if("logo" == type) {
					 var row = logoGrid.getSelected();
					 if(!row) {
						 mini.alert("请选择一个企业logo");
						 return ;
					 }
					 positionCode = row.code;
				}
				if("cover" == type) {
					 var row = coverGrid.getSelected();
					 if(!row) {
						 mini.alert("请选择一个职位封面");
						 return ;
					 }
					 positionCode = row.code;
				}
				
				var data = {};
				data.positionCode = positionCode;
				data.serverPath = "";
				data.type = type;
				
				mini.confirm("确定删除？", "确定？",
						function (action) {
							if (action == "ok") {
								deleteImgUrl();
							}
				});
				var deleteImgUrl = function () {
					$.ajax({
						url : "${pageContext.request.contextPath}/rst/position_definition/update_position_img",
						dataType: 'json', type : 'post',
						data: data,
						success : function(text) {
							if("logo" == type) {
								logoGrid.reload();
							}
							if("cover" == type) {
								coverGrid.reload();
							}
						},
						error: function (jqXHR, textStatus, errorThrown) {
				             mini.alert(jqXHR.responseText);
			            }
					});
				}
			}
			
			function refreshImg(type) {
				if("logo" == type) {
					logoGrid.reload();
				}
				if("cover" == type) {
					coverGrid.reload();
				}
			}
			
			function addImg(type) { //上传职位的 企业Logo或职位封面 
				var row = grid.getSelected();
				if (!row) {
					mini.alert("请选择一个职位");
					return ;
				}
				var title = "上传企业Logo图";
				if('cover' == type) {
					title = "上传职位封面";
				}
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/rst/position_definition/upload_img_htmlfile.jsp",
					title : title,
					width : 420,
					height : 330,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							 positionCode: row.code,
							 type : type
						};
						 
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						if ('ok' == action) { 
							logoGrid.reload();
							coverGrid.reload();
						}
						//gridVideo.reload();
					}
				});
			}
			
			
			function refreshVideo() {
				gridVideo.reload();
			}
			
			function removeVideo() {
				var row = grid.getSelected();
				var rows = gridVideo.getSelecteds();
				if (!row) {
					mini.alert("请选择一个职位");
					return ;
				}
				if (rows.length ==0) {
					mini.alert("请至少选择一个职位视频");
					return ;
				}
				var vIds = new Array();
				for (var i=0;i<rows.length;i++) {
					vIds.push(rows[i].id);
				}
				var data = {};
				data.positionCode = row.code;
				data.videoIds = vIds.join(",");
				$.ajax({
					'url': "${pageContext.request.contextPath}/rst/position_definition/delete_position_video",
					type: 'post', dataType:'JSON',
					data: data,
					success: function (json) {
						gridVideo.reload();
					},
					error : function(data) {
				  		mini.alert(data.responseText);
					}
				});
			}
			
			function addVideo() { //上传视频
				var row = grid.getSelected();
				if (!row) {
					mini.alert("请选择一个职位");
					return ;
				}
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/rst/position_definition/upload_video_htmlfile.jsp",
					title : "上传职位视频",
					width : 420,
					height : 330,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							 positionCode: row.code
						};
						 
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						if ('ok' == action) { 
							//mini.alert("上传成功");
							gridVideo.reload();
						}
					}
				});
			}
			
			function search() {
				var data = form.getData();
				grid.load(data)
			}
			
			function refreshUserJobRecord() {
				gridRecord.reload();
			}
			
			grid.on("rowclick", function(e){
				var record = e.record;
				
				gridAdress.load({positionCode: record.code})
				gridVideo.load({positionCode: record.code})
				gridRecord.load({positionCode: record.code})
				
				logoGrid.load({code: record.code,imgType: "logo"});
				coverGrid.load({code: record.code,imgType: "cover"});
				
			});
	
			function removeAddress() {
				var row = grid.getSelected();
				if(!row) {
					mini.alert("请选择一个职位");
					return ;
				}
				
				var rows = gridAdress.getSelecteds();
				if (rows.length == 0) {
					mini.alert("请至少选择一个工作地址");
					return ;
				}
				var temp = new Array();
				for (var i=0;i<rows.length;i++) {
					temp.push(rows[i].code);
				}
				
				var data = {};
				data.positionCode = row.code;
				data.wordAddressCodes = temp.join(",");
				
				mini.confirm("确定删除？", "确定？",
						function (action) {
							if (action == "ok") {
								deletePositionAddress();
							}
				});
				
				var deletePositionAddress = function () {
					$.ajax({
						'url': "${pageContext.request.contextPath}/rst/position_definition/delete_position_addresses",
						type: 'post', dataType:'JSON',
						data: data,
						success: function (json) {
							gridAdress.reload();
						},
						error : function(data) {
					  		mini.alert(data.responseText);
						}
					});
				}
			}
			
			
			
			function addAddress() {
				var row = grid.getSelected();
				if(!row) {
					mini.alert("请选择一个职位");
					return ;
				}
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/rst/position_definition/selector_address.jsp?multiSelect=true",
					title : "地址",
					width : 650,
					height : 550,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							 companyCode: row.companyCode
						};
						 
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						//alert(action);
						if ('ok' == action) {
							var iframe = this.getIFrameEl();
							var rows = iframe.contentWindow.GetDatas();
							rows = mini.clone(rows);
							//console.log(rows);
							
							if (rows.length == 0) {
								mini.alert("没有选择地址");
								return ;
							}
							var temp = new Array();
							for (var i=0;i<rows.length;i++) {
								temp.push(rows[i].code);
							}
							
							var data = {};
							data.positionCode = row.code;
							data.workAddressCodes = temp.join(",");
							
							$.ajax({
								'url': "${pageContext.request.contextPath}/rst/position_definition/save_position_addresses",
								type: 'post', dataType:'JSON',
								data: data,
								success: function (json) {
									gridAdress.reload();
								},
								error : function(data) {
							  		mini.alert(data.responseText);
								}
							});
							
						}
					}
				});
			}
			
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
								'url': "${pageContext.request.contextPath}/rst/position_definition/delete?ids="+ids.join(","),
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
				if ('edit' == action) {
					if (!row) {
						mini.alert("请选择一个职位");
						return ;
					}
				}
				
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/rst/position_definition/edit.jsp",
					title : "编辑信息",
					width : 650,
					height : 620,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : action
						};
						
						if ('edit' == action) {
							data.id = row.id;
						}
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						grid.reload();
					}
				});
			}
			
		</script>
	</body>
</html>