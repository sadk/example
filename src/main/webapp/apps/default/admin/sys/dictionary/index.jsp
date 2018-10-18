<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>字典管理</title>
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
			<div size="270" showCollapseButton="true">
				<div id="form1"  style="padding:8px;">
					<table>						
						<tr>
							<td>关键字 ：</td>
							<td>
								<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
							</td>
						</tr>
						<tr>
							<td>所属应用：</td>
							<td>
								<input id="appCode" name="appCode"  onvaluechanged="onApplicationChanged" class="mini-combobox" style="width:140px"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/application/all" />
							</td>
						</tr>
						<tr>
							<td>字典名称：</td>
							<td>
								<input id="name" name="name"  style="width:140px" class="mini-textbox"  emptyText="请输入字典名称"  onenter="search"/>
							</td>
						</tr>
						<tr>
							<td>字典值：</td>
							<td>
								<input id="value" name="value"  style="width:140px" class="mini-textbox"  emptyText="请输入字典值"  onenter="search"/>
							</td>
						</tr>
						<tr>
							<td>字典编码：</td>
							<td>
								<input id="code" name="code" class="mini-textbox" style="width:140px" emptyText="请输入备注" onenter="search"/>
							</td>
						</tr>
						<tr>
							<td>备注：</td>
							<td>
								<input id="remark" name="remark" class="mini-textbox" style="width:140px" emptyText="请输入备注" onenter="search"/>
							</td>
						</tr>
						 
						<tr>
							<td>创建时间(开始)：</td>
							<td>
								<input id="createTimeBegin" name="createTimeBegin" class="mini-datepicker" style="width:140px" emptyText="请输入"/>
							</td>
						</tr>
						<tr>
							<td>创建时间(结束)：</td>
							<td>
								<input id="createTimeEnd" name="createTimeEnd" class="mini-datepicker" style="width:140px"  emptyText="请输入"/>
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
				<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-add" onclick="add()">添加</a>
								<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
								<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
								<!-- 
								<a class="mini-button" iconCls="icon-node" onclick="edit('view')">查看</a>
								 -->
								 <a class="mini-button" iconCls="icon-node" onclick="repairNodePath()">修复节点路径</a>
								
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
					showTreeIcon="true" allowResize="true" expandOnLoad="false"  contextMenu="#gridMenu"
					allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" 
    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false" 
					url="${pageContext.request.contextPath}/dictionary/list?isEnableTreeQuery=false" > 
					    <div property="columns">
					        <div type="indexcolumn"></div>
					        <div name="name" field="name" width="160" headerAlign="center">名称</div>
					        <div field="code" width="80" headerAlign="center">编码</div>
					        <div field="value" width="80" headerAlign="center">编码值</div>
					        <div type="comboboxcolumn" field="enable" width="60" headerAlign="center" align="center" allowSort="true">启用/禁用
								<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" />
							</div>
					        <div field="categoryName" width="80" align="center" headerAlign="center">类别</div>
					        <div field="sn" width="30" align="right" headerAlign="center">序号</div>
					        <div field="appCode" width="60" align="left">所属应用</div>
					        <div field="nodePath" width="60" align="left">结点路径</div>
					        <!-- 
					        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
					         -->
					        <div name="id" field="id" width="30" >ID</div>
					        <div name="pid" field="pid" width="30" >父ID</div>              
					    </div>
					</div>
				    <ul id="gridMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">              
				        <li name="expandAll" iconCls="icon-expand" onclick="grid.expandAll()">展开全部</li>
					    <li name="closeAll" iconCls="icon-collapse" onclick="grid.collapseAll()">关闭全部</li>
					    <li name="closeOther" iconCls="icon-collapse" onclick="closeOther()">关闭其它</li>
				    </ul>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		mini.parse();

		var form = new mini.Form("#form1");
		var grid = mini.get("datagrid1");

		function closeOther() {
			var row = grid.getSelected();
			if(row) {
				grid.collapseAll();
				grid.expandPath (row);

				/*
				var nodes = grid.findNodes(function(node){
				    if(node.id == row.id) return true;
				});
				
				if(nodes!=null && nodes.length>0) {
					var node = nodes[0];
					//console.log(grid.isExpandedNode (node))
					//grid.expandNode(node) 这个方法简直不生效！！！
					
					grid.collapseAll();
					grid.expandPath (node);
				}
				*/
			}
		}
		
		function onBeforeOpen(e) {
		    var menu = e.sender; 
		    var row = grid.getSelected();
		    var rowIndex = grid.indexOf(row);            
		    if (!row ||  rowIndex== 0) {
		        e.cancel = true;
		        //阻止浏览器默认右键菜单
		        e.htmlEvent.preventDefault();
		        return;
		    }
		}
		
		function clear() {
			 form.clear();
		}
		 
		function repairNodePath() {
			$.ajax({
				'url': "${pageContext.request.contextPath}/dictionary/repair_node_path",
				type: 'post',
				dataType:'JSON',
				cache: false,
				async:false,
				success: function (json) {
					mini.alert("修复成功");
					grid.reload();
				},
				error : function(data) {
			  		mini.alert(data.responseText);
				}
			});
		}
		
		function search() {
			var data = form.getData();
			
			var createTimeBegin = mini.get('createTimeBegin').text;
			var createTimeEnd = mini.get('createTimeEnd').text;
			
			data.createTimeBegin = createTimeBegin;
			data.createTimeEnd = createTimeEnd;
			
			var key2 = mini.get("key2").value;
			if( (data.key==null || data.key=="") && (key2!=null && key2!="")){
				data.key = key2;
			}
			
			//遍历对象所有属性值，如果都为空，查询所有数据
			var hasValue = false; //是否有输入查询值
            for(var name in data){
               var value = data[name];
               if( (typeof(value) != 'undefined') && value != null && value != '') {
            	   hasValue = true;
            	   break;
               }
            }
	       	
            grid.setAutoLoad(false); // 阻止grid.setUrl方法一调用就自动加载数据!!!
            var url = "${pageContext.request.contextPath}/dictionary/list";
            if(hasValue) {
            	grid.setExpandOnLoad(true);//打开全部节点!!
				grid.setUrl(url + "?isEnableTreeQuery=true")
            } else {
            	grid.setExpandOnLoad(false);
            	grid.setUrl(url + "?isEnableTreeQuery=false")
            }
			grid.load(data);
		}

		function onApplicationChanged (e) {
			search()
		}
		
		function remove() {
			var row = grid.getSelected();
			if(row) {
				mini.confirm("删除当前分类，其子分类也即将删除，确定删除？", "确定？",
						function (action) {
							if (action == "ok") {
								$.ajax({
									'url': "${pageContext.request.contextPath}/dictionary/delete?ids="+row.id,
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
			//var row = grid.getSelected();
			//if(row) {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/sys/dictionary/edit.jsp",
					title : "添加字典",
					width : 500,
					height : 280,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : "add"
							//pid : row.id
						};
						iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						grid.reload();
					}
				});
			//} else {
			//	mini.alert("请选中一条记录");
			//}
		}
		

		function edit(action) {
			var row = grid.getSelected();
			if(typeof(action) == 'undefined') {
				action = "edit";
			}
			
			if (row) {
				mini.open({
					url : "${pageContext.request.contextPath}/apps/default/admin/sys/dictionary/edit.jsp",
					title : "编辑字典信息",
					width : 500,
					height : 250,
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
						
						var nodes = grid.findNodes(function(node){
						    if(node.id == row.id) return true;
						});
						
						if(nodes!=null && nodes.length>0) {
							var node = nodes[0];
							//console.log(grid.isExpandedNode (node))
							grid.expandNode(node) //如果是非叶结点，可以展示下一层！！！
							grid.expandPath (node);
						}
					}
				});
			} else {
				mini.alert("请选中一条记录");
			}
		}
		
		function exportData() {
			var exportDataType = mini.get("exportDataType").value;
			var exportFileType = mini.get("exportFileType").value;
			mini.confirm("确定导出记录？", "确定？",
		            function (action) {
		                if (action == "ok") {
		    				var o = form.getData();
							var url = "${pageContext.request.contextPath}/category/export?exportFileType="+exportFileType+"&exportDataType="+exportDataType;
							location.href=url;
						}
					});
			
		}
		</script>
	</body>
</html>