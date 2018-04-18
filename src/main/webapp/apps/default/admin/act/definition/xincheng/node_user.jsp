<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>节点用户</title>

    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>  
    
   <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
</head>
<body>
<div class="mini-fit">
<div class="mini-splitter" style="width:100%;height:100%;">
    <div size="240" showCollapseButton="true">
        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
            <span style="padding-left:5px;">应用：</span>
            <input class="mini-textbox" width="120"/>
            <a class="mini-button" iconCls="icon-search" plain="true" onenter="search2">查找</a>                  
        </div>
        <div class="mini-fit">
            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/application/all" style="width:100%;"
                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false">        
            </ul>
        </div>
    </div>
    <div showCollapseButton="true">
    	<div id="tabs1" class="mini-tabs" activeIndex="0" plain="false" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
  			<div title="组织" >
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-reload" onclick="dataRefresh()">加载</a>
								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-downgrade" onclick="saveAsApprove(3)">加入到审批对象</a>
							</td>
							<td style="white-space:nowrap;">
		                        <input id="key1" name="key1" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="search()" onenter="search">查询</a>
		                    </td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
					<div id="dataGridOrg" class="mini-treegrid"" style="width:100%;height:100%;"
					showTreeIcon="true" allowResize="true" expandOnLoad="false"  multiSelect="true" 
    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false" 
					url="${pageContext.request.contextPath}/syswin/org/all" > 
					    <div property="columns">
					        <div type="checkcolumn"></div>
					        <div name="name" field="name" width="160" headerAlign="center">名称</div>
					        <div name="nodePath" field="nodePath" width="160" headerAlign="center">节点路径</div><!-- orgUnitId -->
					        <!-- 
					        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
					         -->
					        <div name="id" field="id" width="50" headerAlign="center">ID</div>
					        <div name="pid" field="pid" width="50" headerAlign="center">父ID</div>     
					        <!-- 
					        <div name="remark" field="remark" width="30" >备注</div>
					         -->  
					    </div>
					</div>
		        </div>
		    </div>

  			<div title="岗位" > 
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						    <div title="组织机构" refreshOnClick="true">
						        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-reload" onclick="posOrgGridRefresh()">刷新</a>
											</td>
										</tr>
									</table>
						        </div>
						        <div class="mini-fit">
									<div id="datagrid1" class="mini-treegrid"" style="width:100%;height:100%;"
									showTreeIcon="true" allowResize="true" expandOnLoad="false"
				    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false" 
									url="${pageContext.request.contextPath}/syswin/org/all" > 
									    <div property="columns">
									       <!--  <div type="checkcolumn"></div> -->
									        <div name="name" field="name" width="160" headerAlign="center">名称</div>
									        <div field="typeDesc" width="80" headerAlign="center" align="center">类型</div>
									       <!--  <div field="sn" width="30" align="right" headerAlign="center">序号</div>
									        <div field="appCode" width="60" align="left">所属应用</div> -->
									        <div field="nodePath" width="100" align="left">结点路径</div>
									        
									        <!-- 
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
									         -->
									        <div name="id" field="id" width="30" >ID</div>
									        <div name="pid" field="pid" width="30" >父ID</div>               
									    </div>
									</div>
						        </div>
						    </div>
						    
						</div>
					</div>
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							
							<div title="机构下的岗位" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="saveAsApprove(2)">加入到审批对象</a>
												
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="positionGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" 
										multiSelect="true" allowResize="false" sizeList="[20,50,100,150,200]" pageSize="50"  
					 					url="${pageContext.request.contextPath}/syswin/position/page">
										<div property="columns">
									        <div type="checkcolumn" ></div>
									        <div name="id" field="id" width="80" headerAlign="center">岗位ID</div>
									        <div name="name" field="name" width="160" headerAlign="center">岗位名称</div>
									        <div field="directId" width="80" headerAlign="center">直属上级ID</div>
									        <div field="directName" width="80" headerAlign="center">直属上级名称</div>
									        <div field="typeDesc" width="80" headerAlign="center">岗位级别</div>
									        <!-- 
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
											 -->
										</div>
									</div>
								</div>
							</div>
							
						</div>
					</div>
				</div>
		    </div>


  			<div title="用户" >
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-reload" onclick="loadUser()">加载</a>
								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-downgrade" onclick="saveAsApprove()">加入到审批对象</a> 
							</td>
							<td style="white-space:nowrap;">
		                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="searchUser()" onenter="searchUser">查询</a>
		                    </td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
		            <div id="dataGridUser" class="mini-datagrid" style="width:100%;height:100%;" multiSelect="true" 
		                borderStyle="border:0;"  url="${pageContext.request.contextPath}/syswin/user/page" sizeList="[20,50,100,150,200]" 
		                showFilterRow="false" allowCellSelect="true" allowCellEdit="true" showPager="true" pageSize="50">
		                <div property="columns">
		                	<div type="checkcolumn"></div>   
		                	<div field="userId" width="50" headerAlign="center" allowSort="true">用户ID</div>           
		                    <div field="userName" width="120" headerAlign="center" allowSort="true">姓名</div>    
		                    <div field="loginNo" width="120" headerAlign="center" allowSort="true">账号</div>
		                    <div field="workNo" width="120" headerAlign="center" allowSort="true">工号</div>   
		                    
		                    <div field="mobile" width="100" allowSort="true">手机</div>      
		                    <div field="email" width="160" allowSort="true">邮箱</div>
				    	
		                </div>
		            </div>  
		        </div>
		    </div>
		    
		    
		    
		    
		    
  			<div title="规则用户" >
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-reload" onclick="loadRule()">加载</a>
								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-downgrade" onclick="saveAsApprove()">加入到审批对象</a> 
							</td>
							<td style="white-space:nowrap;">
		                        <input id="key3" name="key3" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="searchRule()" onenter="searchRule">查询</a>
		                    </td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
		            <div id="dataGridRule" class="mini-datagrid" style="width:100%;height:100%;" multiSelect="true" 
		                borderStyle="border:0;"  url="${pageContext.request.contextPath}/act/user_rule/page" sizeList="[20,50,100,150,200]" 
		                showFilterRow="false" allowCellSelect="true" allowCellEdit="true" showPager="true" pageSize="50">
		                <div property="columns">
		                	<div type="checkcolumn"></div>   
							<div field="id" width="40" headerAlign="center" allowSort="true" align="left">ID</div>
							<div field="name" width="150" headerAlign="center" allowSort="true" align="left">名称</div>
							<div field="code" width="120" headerAlign="center" allowSort="true" align="left">编码</div>
							<div field="categoryName" width="60" headerAlign="center" allowSort="true" align="left">分类名称</div>
							<div field="resolveTypeDesc" width="80" headerAlign="center" allowSort="true" align="center">解析引擎</div>
							<div field="content" width="180" headerAlign="center" allowSort="true" align="left">内容</div>
				    	
		                </div>
		            </div>  
		        </div>
		    </div>
		    
  			<div title="审批对象" >
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<!-- <a class="mini-button" iconCls="icon-downgrade" onclick="saveAsApprove()">加载选择的审批对象</a> 
								<a class="mini-button" iconCls="icon-remove" onclick="dataRefresh()">移除</a> -->
								<a class="mini-button" iconCls="icon-remove" onclick="removeApprove()">移除</a>
								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-reload" onclick="refreshApproveDataGrid()">加载已审批对象</a>
							</td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
		            <div id="dataGridApprove" class="mini-datagrid" style="width:100%;height:100%;" multiSelect="true" 
		                borderStyle="border:0;" showFilterRow="false" allowCellSelect="true" allowCellEdit="true" showPager="false"
		                 url="${pageContext.request.contextPath}/act/node_user/list" >
		                <div property="columns">
		                	<div type="checkcolumn"></div>        
		                    <!-- <div field="id" width="120" headerAlign="center" allowSort="true">ID</div> -->
		                    <div field="approveObjectId" width="60" headerAlign="center" allowSort="true">审批对象ID</div>
		                    <div field="name" width="80" headerAlign="center" allowSort="true">名称</div>
		                   <!--   <div field="approveObjectJson" width="180" align="center">审批对象值
		                    	<input property="editor"  class="mini-textarea" style="width:100%;" minWidth="200" minHeight="300" />
		                    </div>
		                    <div field="code" width="100" allowSort="true" align="center" headerAlign="center">对象编码</div> -->
		                	<div field="userTypeDesc" width="60" allowSort="true" align="center" headerAlign="center">对象类型</div>
		                </div>
		            </div>  
		        </div>
		    </div>
		    
        </div>
    </div> 
</div>
</div>
    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
        <a class="mini-button" style="width:60px;" onclick="onOk()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>
    <script type="text/javascript">
        mini.parse();
        
        var loginUserId = "${param.userId}";
		var taskKey = null;
		var definitionId = null;
		var definitionName = null;
		
		// dataGridOrg  datagrid1  dataGridUser  dataGridApprove
        var tree = mini.get("tree1");
        var dataGridOrg = mini.get("dataGridOrg");  // 组织
		var dataGridUser = mini.get("dataGridUser");  // 用户
		
		var posOrgGrid= mini.get("datagrid1"); //岗位tab的组织架构
		var dataGridPosition = mini.get("positionGrid"); // 岗位
		
		var dataGridRule = mini.get("dataGridRule"); // 用户规则
		dataGridRule.load();
		
		var dataGridApprove = mini.get("dataGridApprove"); // 审批对象
		
		function loadUser(){
			dataGridUser.load();
		}
		
		function searchUser(){
			var key = mini.get("key2").value;
			dataGridUser.load({key: key});
		}
		
		posOrgGrid.on("rowclick", function(e){
			var record = e.record;
			dataGridPosition.load({orgId:record.id}); 
		});
		
		function posOrgGridRefresh () {
			posOrgGrid.reload();
		}
		
		function refreshApproveDataGrid () {
			dataGridApprove.load({definitionId : definitionId, taskKey : taskKey});
			//dataGridApprove.reload();
		}
		
		tree.on("nodeclick",function(e){
			dataGridOrg.reload();
			dataGridUser.reload();
			dataGridPosition.reload();
			dataGridApprove.load({definitionId : definitionId, taskKey : taskKey});
			
		})
		
		function removeApprove() {
			var rows = dataGridApprove.getSelecteds();
			dataGridApprove.removeRows(rows); 
		}
		
		function saveAsApprove() {
			var data = [];
			var row = dataGridOrg.getSelecteds();
			
			// 组织
			if(row && row.length>0) {
				// 结点用户类型: 1=职称 2=岗位 3=组织 5=用户 7=角色 4=用户组(与流程引擎的group不是一个概念，主要是第三方系统的UUM)；100= 脚本计算 
				for(var i=0;i<row.length;i++) {
					data.push({approveObjectId: row[i].id, name: row[i].name, approveObjectJson: mini.encode(row[i]), definitionId:definitionId,definitionName:definitionName,taskKey:taskKey,userType:3,userTypeDesc:"组织" ,userFromType:1})
				}
				//mini.alert("所选组织已加入到【审批对象】成功！");
			}
			
			// 岗位
			var rows = dataGridPosition.getSelecteds();
			if(rows && rows.length>0) {
				for(var i=0;i<rows.length;i++) {
					data.push({approveObjectId: rows[i].id, name: rows[i].name, approveObjectJson: mini.encode(rows[i]), definitionId:definitionId,definitionName:definitionName,taskKey:taskKey,userType:2,userTypeDesc:"岗位",userFromType:1})
				}
				//mini.alert("所选岗位已加入到【审批对象】成功！");
			}
			
			// 用户
			rows = dataGridUser.getSelecteds();
			if(rows && rows.length>0) {
				for(var i=0;i<rows.length;i++) {
					data.push({approveObjectId: rows[i].userId ,name: rows[i].userName, approveObjectJson : mini.encode(rows[i]),definitionId:definitionId,definitionName:definitionName,taskKey:taskKey,userType:5,userTypeDesc:"用户",userFromType:1})
				}
			}
			
			// 规则用户
			rows = dataGridRule.getSelecteds();
			if(rows && rows.length>0) {
				for(var i=0;i<rows.length;i++) {
					data.push({approveObjectId: rows[i].id ,name: rows[i].name, approveObjectJson : mini.encode(rows[i]),definitionId:definitionId,definitionName:definitionName,taskKey:taskKey,userType:100,userTypeDesc:"规则用户",userFromType:1})
				}
			}
			
			mini.alert("所选记录已加入到【审批对象】成功！");
			
			
			var temp = dataGridApprove.getData(); //已加入的审批对象
			if(temp == null || temp.length==0){
				dataGridApprove.setData(data);
				return ;
			}
			
			// 去除重复对象
			var approveData = mini.clone(temp);
			for(var n=0;n<data.length;n++){  //用户勾选的记录
				var isExists = false;
				
				for(var i=0;i<temp.length;i++){
					if((data[n].approveObjectId+"-"+data[n].userType) == (temp[i].approveObjectId+"-"+temp[i].userType)){
						isExists = true;
						break;				
					}	
				}
				if(!isExists){
					approveData.push(data[n]);
				}
			}
			dataGridApprove.setData(approveData);
		}
		/*
		function dataRefresh(){
			var node = tree.getSelectedNode();
			//mini.alert(node.value);
            if (node && node.id!=-1) {
                dataGridOrg.load({orgId: node.value,loginUserId : loginUserId})
                dataGridUser.load({orgId: node.value,loginUserId : loginUserId});
                dataGridPosition.load({orgId: node.value,loginUserId : loginUserId});
            }else {
            	mini.alert("请选择一个系统");
            }
		}*/
        
        function saveData() {
            var data = dataGridApprove.getData();
            var json = mini.encode(data);
            // mini.alert(json);
            // if(true) return ;
            
            var saveGridToDb = function () {
            	$.ajax({
                    url: "${pageContext.request.contextPath}/act/node_user/save_or_update_json",
                    data: { data: json ,definitionId: definitionId, taskKey: taskKey},
                    type: "post",
                    success: function (text) {
                       // grid.reload();
                    	  CloseWindow("ok");
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert(jqXHR.responseText);
                    }
                });
            }
            
            if(data.length == 0) {
                mini.confirm("您没有任务审批对象，确定提交吗？", "确定？",
                        function (action) {
                            if (action == "ok") {
                                dataGridApprove.loading("保存中，请稍后......");
                                saveGridToDb();
                                return ;
                            } 
                        }
                );
            }else {
            	saveGridToDb();
            }


        }
        
        
        //////////////////////////////////
        function SetData(data) {
			data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
			taskKey = data.taskKey;
			definitionId = data.definitionId;
			definitionName = data.definitionName;
			//mini.alert(taskKey + " "+definitionId+" "+definitionName);
			
			dataGridApprove.load({definitionId : definitionId, taskKey : taskKey});
			
        }
	    function CloseWindow(action) {
	        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	        else window.close();
	    }
	
	    function onOk() {
	    	saveData();
	    }
	    
	    function onCancel() {
	        CloseWindow("cancel");
	    }
    </script>

</body>
</html>