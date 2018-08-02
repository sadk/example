<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>技能信息</title>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
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
			<div size="240" showCollapseButton="true">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <span style="padding-left:5px;">租户：</span>
		            <input id="tenantCode" name="tenantCode" width="180" class="mini-buttonedit" onbuttonclick="onButtonEdit" /> 
		            <!--    
		            <a class="mini-button" iconCls="icon-search" plain="true">查找</a>   
		             -->               
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/yue/tech/list" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false">        
		            </ul>
		        </div>      
			</div>
			<div showCollapseButton="true">
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
						    <div title="技能信息" refreshOnClick="true">
						        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="editInfo('add')">添加</a>
												<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editInfo('edit')">编辑</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-edit" onclick="viewFullForm()">技能清单</a>
											</td>
											
										</tr>
									</table>
								</div>
						
						        <div class="mini-fit">
						            <div id="techInfoGrid" class="mini-datagrid" style="width:100%;height:100%;" showReloadButton="true"
						                url="${pageContext.request.contextPath}/yue/tech_info/page" idField="id" allowResize="false"
						                sizeList="[5,10,20,50]" pageSize="20" showEmptyText="true" emptyText="暂无待修改信息" sortMode="client" >
						                <div property="columns">
											<div type="checkcolumn" ></div>
											<div field="techName" width="150" headerAlign="center" align="left" allowSort="true">技能定义</div>	
											<div field="name" width="160" headerAlign="center" align="center" allowSort="true">名称</div>										
											<div field="code" width="100" headerAlign="center" align="center" allowSort="true">编码</div>
											
											<div field="appCode" width="80" headerAlign="center" align="left" allowSort="true">应用系统</div>
											<div field="remark" width="300" headerAlign="center" align="left" allowSort="true">说明</div>
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
							
							<div title="技能选项" refreshOnClick="true" name="oroTmpl">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="addItem()">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editItem()">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeItem()">删除</a>
											</td>
											<td style="white-space:nowrap;">
						                        <input id="key_oro" name="key_oro" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
						                        <a class="mini-button" onclick="search()">搜索</a>
						                    </td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="techInfoItemGrid" class="mini-datagrid" style="width:100%;height:100%;"
										url="${pageContext.request.contextPath}/yue/tech_info_item/page"  idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据"
										sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" sortMode="client" >
										<div property="columns">
											<div type="checkcolumn" ></div>
											<div field="name" width="80" headerAlign="center" allowSort="true">技能细项名</div>
											<div field="code" width="80" headerAlign="center" allowSort="true">编码</div>
											 
											<div field="sn" width="60" headerAlign="center" allowSort="true">序号</div>
											<div field="remark" width="60" headerAlign="center" allowSort="true">备注</div>
											<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        				<div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>
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
			var tree = mini.get("tree1");
			
 			var grid = mini.get("techInfoGrid");
 			var gridItem = mini.get("techInfoItemGrid");
 			
			grid.on("rowclick", function(e){
				var record = e.record;
				// record.code;
			});
			
	        
			function editInfo(action) {
				var row = grid.getSelected();
				var node = tree.getSelectedNode();
				var id = null;
				var techId = null;
				var techName = null;
				if('edit' == action) {
					if(!row) {
						mini.alert("请选择一个技能信息");
						return ;
					}
				}
				if(row) {
					id = row.id;
				}
				if(action == 'add' && node) {
					techId = node.id;
					techName = node.name;
				}
				
				if(action == 'edit') {
					techId = row.techId;
					techName = row.name
				}
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/yue/tech_info/edit.jsp",
					title : "编辑",
					width : 490,
					height : 250,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : action,
							id : id,
							techId : techId,
							techName: techName
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						grid.reload();
					}
				});
			}

			function remove() {
				var rows = grid.getSelecteds();
				if(rows.length == 0) {
					mini.alert('请选择一条记录');
					return ;
				}
				var ids = new Array();
				for(var i=0;i<rows.length;i++) {
					ids.push(rows[i].id);
				}
				$.ajax({
					url : "${pageContext.request.contextPath}/yue/tech_info/delete?ids=" + ids.join(","),
					dataType: 'json',
					cache : false,
					success : function(text) {
						var o = mini.decode(text);
						if(o!=null && o.data!=null && o.data.length>0) {
							o = o.data[0];
						}
						form.setData(o);
						form.setChanged(false);
						
					}
				});
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
		</script>
	</body>
</html>