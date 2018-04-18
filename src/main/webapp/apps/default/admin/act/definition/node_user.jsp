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
            <span style="padding-left:5px;">名称：</span>
            <input class="mini-textbox" width="120"/>
            <a class="mini-button" iconCls="icon-search" plain="true">查找</a>                  
        </div>
        <div class="mini-fit">
            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/application/all_tree" style="width:100%;"
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
								<a class="mini-button" iconCls="icon-downgrade" onclick="saveAsApprove()">加入到审批对象</a>
							</td>
							<td style="white-space:nowrap;">
		                        <input id="key1" name="key1" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="search()">查询</a>
		                    </td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
					<div id="dataGridOrg" class="mini-treegrid"" style="width:100%;height:100%;"
					showTreeIcon="true" allowResize="true" expandOnLoad="true"
    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false" 
					url="${pageContext.request.contextPath}/act/user_data_config/org_list" > 
					    <div property="columns">
					        <div type="checkcolumn"></div>
					        <div name="name" field="name" width="160" headerAlign="center">名称</div>
					        <div name="code" field="code" width="80" headerAlign="center">编码</div>
					        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
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
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-reload" onclick="dataRefresh()">加载</a>
								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-downgrade" onclick="saveAsApprove()">加入到审批对象</a>
							</td>
							<td style="white-space:nowrap;">
		                        <input id="key3" name="key3" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="search()">查询</a>
		                    </td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
		            <div id="dataGridPosition" class="mini-datagrid" style="width:100%;height:100%;" multiSelect="true" 
		                borderStyle="border:0;"  url="${pageContext.request.contextPath}/act/user_data_config/position_list"
		                showFilterRow="false" allowCellSelect="true" allowCellEdit="true">
		                <div property="columns">
		                	<div type="checkcolumn"></div>        
		                    <div field="id" width="120" headerAlign="center" allowSort="true">岗位ID</div>      
		                    <div field="name" width="120" headerAlign="center" allowSort="true">名称</div>                
		                    <div field="code" width="100" allowSort="true" align="center" headerAlign="center">编码</div>      
					        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>   
					        <!-- 
					        <div name="remark" field="remark" width="30" >备注</div>
					         -->            
		                </div>
		            </div>  
		        </div>
		    </div>


  			<div title="用户" >
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-reload" onclick="dataRefresh()">加载</a>
								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-downgrade" onclick="saveAsApprove()">加入到审批对象</a> 
							</td>
							<td style="white-space:nowrap;">
		                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="search()">查询</a>
		                    </td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
		            <div id="dataGridUser" class="mini-datagrid" style="width:100%;height:100%;" multiSelect="true" 
		                borderStyle="border:0;"  url="${pageContext.request.contextPath}/act/user_data_config/user_list"
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
					        <!-- 
					        <div name="remark" field="remark" width="30" >备注</div>
					         -->                
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
								<a class="mini-button" iconCls="icon-remove" onclick="clearApprove()">清空</a>
							</td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
		            <div id="dataGridApprove" class="mini-datagrid" style="width:100%;height:100%;" multiSelect="true" 
		                borderStyle="border:0;" showFilterRow="false" allowCellSelect="true" allowCellEdit="true" showPager="false"
		                 url="${pageContext.request.contextPath}/node_user/list" >
		                <div property="columns">
		                	<div type="checkcolumn"></div>        
		                    <!-- <div field="id" width="120" headerAlign="center" allowSort="true">ID</div> -->
		                    <div field="approveObjectId" width="120" headerAlign="center" allowSort="true">审批对象ID</div>
		                    <div field="name" width="120" headerAlign="center" allowSort="true">名称</div>
		                    <div field="code" width="100" allowSort="true" align="center" headerAlign="center">对象编码</div>      
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
		
        var tree = mini.get("tree1");
        var dataGridOrg = mini.get("dataGridOrg");
		var dataGridUser = mini.get("dataGridUser");
		var dataGridPosition = mini.get("dataGridPosition");
		var dataGridApprove = mini.get("dataGridApprove");
		
		function clearApprove() {
			dataGridApprove.clearRows(); 
		}
		
		function saveAsApprove() {
			var data = []; // nodeUserList
			var row = dataGridOrg.getSelected();
			
			// 组织
			if(row) {
				//结点用户类型: 1=职称 2=岗位 3=组织 5=用户 7=角色 4=用户组(与流程引擎的group不是一个概念，主要是第三方系统的UUM)； 脚本计算：10=java脚本计算 11=groovy脚本计算 12=javascript脚本计算(注：脚本计算必须返回Collection<String>集合
				data.push({approveObjectId: row.id ,definitionId:definitionId,definitionName:definitionName,taskKey:taskKey,name:row.name,userType:3,userFromType:2})
				
				var json = mini.encode(row);
				//mini.alert(json);
				
			}
			
			// 岗位
			var rows = dataGridPosition.getSelecteds();
			if(rows && rows.length>0) {
				for(var i=0;i<rows.length;i++) {
					data.push({approveObjectId: rows[i].id ,definitionId:definitionId,definitionName:definitionName,taskKey:taskKey,name:rows[i].name,userType:2,userFromType:2})
				}
			}
			
			dataGridApprove.setData(data);
		}
		
		function dataRefresh(){
			var node = tree.getSelectedNode();
            if (node && node.id!=-1) {
                dataGridOrg.load({appId: node.id,loginUserId : loginUserId})
                dataGridUser.load({appId: node.id,loginUserId : loginUserId});
                dataGridPosition.load({appId: node.id,loginUserId : loginUserId});
            }else {
            	mini.alert("请选择一个系统");
            }
		}
        
        function saveData() {
            var data = dataGridApprove.getSelecteds();
            var json = mini.encode(data);
            // mini.alert(json);
            // if(true) return ;
            
            var saveGridToDb = function () {
            	$.ajax({
                    url: "${pageContext.request.contextPath}/node_user/save_or_update_json",
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
                mini.confirm("您没有选择任务审批对象，确定提交吗？", "确定？",
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