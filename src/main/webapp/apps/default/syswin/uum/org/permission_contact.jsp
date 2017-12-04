<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>组织机构-通讯录</title>

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
        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
            <span style="padding-left:5px;">名称：</span>
            <input class="mini-textbox" width="120"/>
            <a class="mini-button" iconCls="icon-search" plain="true">查找</a>                  
        </div>
        <div class="mini-fit">
            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/org/all_selector" style="width:100%;"
                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false"
            >        
            </ul>
        </div>
    </div>
    <div showCollapseButton="true">
    	<div id="tabs1" class="mini-tabs" activeIndex="0" plain="false" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
  			<div title="部门的用户列表" >
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-user" plain="false" onclick="grant()">部门授权</a>
								<span class="separator"></span>  
								<a class="mini-button" iconCls="icon-add" plain="false" onclick="add()">分配部门用户</a>
								<a class="mini-button" iconCls="icon-downgrade" plain="false" onclick="remove()">移出部门用户</a>
							</td>
							<td style="white-space:nowrap;">
		                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="search()">查询</a>
		                    </td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
		            <div id="grid1" class="mini-datagrid" style="width:100%;height:100%;" multiSelect="true" 
		                borderStyle="border:0;"  url="${pageContext.request.contextPath}/user/get_all_user_by_orgid"
		                showFilterRow="false" allowCellSelect="true" allowCellEdit="true">
		                <div property="columns">
		                	<div type="checkcolumn"></div>        
		                    <div field="loginName" width="120" headerAlign="center" allowSort="true">员工帐号</div>      
		                    <div field="name" width="120" headerAlign="center" allowSort="true">员工姓名</div>                
		                    <div field="sex" width="100" allowSort="true" align="center" headerAlign="center">性别</div>      
		                    <div field="mobile" width="100" allowSort="true">手机</div>      
		                    <div field="email" width="100" allowSort="true">邮箱</div>
		                    <div field="tel" width="100" allowSort="true">电话</div>
		                    <div field="birthday" width="100" allowSort="true" dateFormat="yyyy-MM-dd">出生日期</div>                                    
					        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>                
		                </div>
		            </div>  
		        </div>
		    </div>    
		    
		    <!--  
		    <div title="部门的角色列表" >
			        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
						<table style="width:100%;">
							<tr>
								<td style="width:100%;">
									<a class="mini-button" iconCls="icon-add" plain="false" onclick="add()">分配部门角色</a>
									<a class="mini-button" iconCls="icon-downgrade" plain="false" onclick="remove()">移除部门角色</a>
								</td>
								<td style="white-space:nowrap;">
			                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
			                        <a class="mini-button" onclick="search()">查询</a>
			                    </td>
							</tr>
						</table>
			        </div>
			        <div class="mini-fit" >
						<div id="datagrid2" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" 
						url="${pageContext.request.contextPath}/role/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
						<div property="columns">
							<div type="checkcolumn" ></div>
							<div field="name" width="160" headerAlign="center" allowSort="true" align="center">名称</div>
							<div field="nameShort" width="160" headerAlign="center" allowSort="true" align="center">简称</div>
							<div field="code" width="160" headerAlign="center" allowSort="true" align="center">编码</div>
							<div field="statusDesc" width="160" headerAlign="center" allowSort="true" align="center">状态</div>
							
							<div field="sn" width="160" headerAlign="center" allowSort="true" align="center">序号</div>
							<div field="remark" width="160" headerAlign="center" allowSort="true" align="center">备注</div>
							<div field="appCode" width="160" headerAlign="center" allowSort="true" align="center">系统编码</div>
							<div field="gid" width="160" headerAlign="center" allowSort="true" align="center">全局编码</div>
							<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">创建日期</div>
							<div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="160" headerAlign="center" allowSort="true" align="center">更新日期</div>
							
						</div>
						</div>
					</div>
		    </div>
		    -->
        
        </div>
    </div>        
</div>
    
    <script type="text/javascript">
        mini.parse();

        var tree = mini.get("tree1");
        var grid = mini.get("grid1");
      //  var grid2 = mini.get("datagrid2");
      //  grid2.load();

        tree.on("nodeselect", function (e) {
        	 grid.load({ orgId: e.node.id });
        });
       
        function grant(){
        	var node = tree.getSelectedNode();
            if (!node) {
            	return  mini.alert("请选择一个部门");
            }
            
            mini.showMessageBox({
                title: "部门授权",
                iconCls: "mini-messagebox-question",
                buttons: ["yes", "no"],
                message: "授权给所选择的部门，是否含下级?",
                callback: function (action) {
                	var isIncludeChild = false;
                	
                	if(action == 'yes') {
                		isIncludeChild = true;
                	}else if(action == 'no') {
                		isIncludeChild = false;
                	}else {
                		return;
                	}
                  
                 	 var node = tree.getSelectedNode();
                     var orgId = node.id;
             			mini.open({
            				url : "${pageContext.request.contextPath}/apps/default/admin/uum/role/seletor_role.jsp",
            				title : "选择角色",
            				width : 700,
            				height : 500,
            				ondestroy : function(action) {
            					if (action == "ok") {
            			            var iframe = this.getIFrameEl();
            			            var datas = iframe.contentWindow.GetDatas();
            			            datas = mini.clone(datas);    //必须。克隆数据。
            			           
            			            if(!datas)return;
            			            
            			            var rids = new Array();
            			            for(var i=0;i<datas.length;i++){
            			            	rids.push(datas[i].id);
            			            }
            			            
            			            var data = {roleIds:rids.join(","),orgId:orgId,isIncludeChild:isIncludeChild};
            			            $.ajax({
            							url : "${pageContext.request.contextPath}/org/grant",
            							dataType: 'json',
            							type : 'post',
            							cache : false,
            							data: data,
            							success : function(text) {
            								grid.reload();
            							}
            						});
            			        }  
            					
            				}
            			}); 
                }
            });
                    
        }
        
        function add() { //添加部门用户            
            var node = tree.getSelectedNode();
            if (node) {
                var orgId = node.id;
    			mini.open({
    				url : "${pageContext.request.contextPath}/apps/default/admin/uum/user/selector_user.jsp",
    				title : "添加部门用户",
    				width : 490,
    				height : 250,
    				/*
    				onload : function() {
    					var iframe = this.getIFrameEl();
    					var data = {
    						action : "add"
    					};
    					//alert(data.pid+" "+data.pName+" "+data.idNotIn);
    					iframe.contentWindow.SetData(data);
    				},*/
    				ondestroy : function(action) {
    					if (action == "ok") {
    			            var iframe = this.getIFrameEl();
    			            var datas = iframe.contentWindow.GetDatas();
    			            datas = mini.clone(datas);    //必须。克隆数据。
    			            //alert(mini.encode(datas));
    			            if(!datas)return;
    			            
    			            var uids = new Array();
    			            for(var i=0;i<datas.length;i++){
    			            	uids.push(datas[i].id);
    			            }
    			            
    			            var data = {userIds:uids.join(","),orgId:orgId};
    			            $.ajax({
    							url : "${pageContext.request.contextPath}/user/add_user_to_org",
    							dataType: 'json',
    							type : 'post',
    							cache : false,
    							data: data,
    							success : function(text) {
    								grid.reload();
    							}
    						});
    			        }  
    					
    				}
    			});                
            }else{
            	mini.alert("请选择一个部门");
            }
        }
        function remove() { //删除部门用户
            var rows = grid.getSelecteds();
            var node = tree.getSelectedNode();
            
            var orgId = null;
            var userIds = null;
            
            
            if (node) {
                orgId = node.id;
            }else {
            	mini.alert("请选择一个部门");
            	return;
            }
            
            if (rows.length > 0) {
            	var userIdsArr = new Array();
            	for(var i=0;i<rows.length;i++) {
            		userIdsArr.push(rows[i].id);
            	}
            	userIds = userIdsArr.join(",");

				var data = {orgId:orgId,userIds:userIds};
            	$.ajax({
					url : "${pageContext.request.contextPath}/user/delete_user_from_org",
					dataType: 'json',
					type : 'post',
					cache : false,
					data: data,
					success : function(text) {
						grid.reload();
					}
				});
            } else {
            	mini.alert("请选择用户");
            }
        }
        
        function saveData() {
            var data = grid.getChanges();
            var json = mini.encode(data);
            grid.loading("保存中，请稍后......");
            $.ajax({
                url: "../data/AjaxService.aspx?method=SaveEmployees",
                data: { data: json },
                type: "post",
                success: function (text) {
                    grid.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
        }
    </script>

</body>
</html>