<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>栏目管理</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/pagertree.js" ></script>
		<style>
			html, body {
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
		<div class="mini-splitter" style="width:100%;height:100%;">
		    <div size="240" showCollapseButton="true">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <span style="padding-left:5px;">租户：</span>
		            <input id="tenantCode" name="tenantCode" width="180" class="mini-buttonedit" onbuttonclick="onButtonEdit" /> 
		            <!--    
		            <a class="mini-button" iconCls="icon-search" plain="true">查找</a>   
		             -->               
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/application/list" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false">        
		            </ul>
		        </div>
		    </div>
			<div showCollapseButton="true">
				<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-add" onclick="add()">添加</a>
								<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
								<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
								<a class="mini-button" iconCls="icon-node" onclick="edit('view')">查看</a>
								<span class="separator"></span>  
								<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
								<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
								<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
								
							</td>
							<td style="white-space:nowrap;">
		                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="search()">查询</a>
		                    </td>
						</tr>
					</table>
				</div>
				<div class="mini-fit">
					<div id="datagrid1" class="mini-treegrid"" style="width:100%;height:100%;"
					showTreeIcon="true" allowResize="true" expandOnLoad="true"
    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false" 
					url="${pageContext.request.contextPath}/channel/list" > 
					    <div property="columns">
					        <div type="indexcolumn"></div>
					        <div name="name" field="name" width="160" headerAlign="center">名称</div>
					        <div field="code" width="80" headerAlign="center">编码</div>
					         <div field="statusDesc" width="80" headerAlign="center" align="center" >是否启用</div>
					        <div field="sn" width="30" align="right" headerAlign="center">序号</div>
					        <div field="appCode" width="60" align="left">所属应用</div>
					        <div field="nodePath" width="60" align="left">结点路径</div>
					        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
					        <div name="id" field="id" width="30" >ID</div>
					        <div name="pid" field="pid" width="30" >父ID</div>              
					    </div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		mini.parse();
		
		var tree = mini.get("tree1");
		var grid = mini.get("datagrid1");
		
        tree.on("nodeselect", function (e) {
        	//0=租户 1=应用 2=栏目
        	/*
        	if(e.node.nodeType == "2") {
        		grid.load({ parentCode: e.node.code });
        	} else if(e.node.nodeType == "1") {
        		grid.load({ appCode: e.node.code });
        	} else if(e.node.nodeType == "0") {
        	 	grid.load({ tenantCode: e.node.code });
        	}*/
        	grid.load({ appCode: e.node.code });
        });
        
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
                            
                           
                            tree.load({tenantCode:data.code})
                        } else {
                        	btnEdit.setValue(null);
                            btnEdit.setText(null);
                        	tree.load()
                        }
                    }

                }
            });
        }
        
		function remove() {
			var row = grid.getSelected();
			if(row) {
				mini.confirm("删除当前分类，其子分类也即将删除，确定删除？", "确定？",
						function (action) {
							if (action == "ok") {
								$.ajax({
									'url': "${pageContext.request.contextPath}/channel/delete?ids="+row.id,
									type: 'post',
									dataType:'JSON',
									cache: false,
									async:false,
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
			} else {
				mini.alert("请选中一条以上的记录");
			}
		}
		
		function add() {
			var row = grid.getSelected();
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/admin/cms/channel/edit.jsp",
				title : "添加栏目",
				width : 490,
				height : 350,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {
						action : "add",
						
					};
					if(row){
						data.pid = row.id
					}
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					grid.reload();
				}
			});
		}
		

		function edit(action) {
			var row = grid.getSelected();
			if(typeof(action) == 'undefined') {
				action = "edit";
			}
			
			if (row) {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/cms/channel/edit.jsp",
					title : "编辑栏目",
					width : 490,
					height : 350,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : action,
							id : row.id
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						grid.reload();
					}
				});
			} else {
				mini.alert("请选中一条记录");
			}
		}
		
		</script>
	</body>
</html>