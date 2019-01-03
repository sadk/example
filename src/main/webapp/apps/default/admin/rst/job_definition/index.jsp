<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>岗位综合管理</title>

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
    			<!-- 
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <span style="padding-left:5px;">租户：</span>
		            <input id="tenantCode" name="tenantCode" width="180" class="mini-buttonedit" onbuttonclick="onButtonEdit" />                 
		        </div>
		         -->
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/rst/job_category/tree" style="width:100%;"
		             expandOnLoad="true" showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" contextMenu="#treeMenu">        
		            </ul>
					<ul id="treeMenu" class="mini-contextmenu"  onbeforeopen="onBeforeOpen">        
						<li name="add" iconCls="icon-add" onclick="onEditNode">新增分类</li>
						<li name="edit" iconCls="icon-edit" onclick="onEditNode">编辑分类</li>
						<li name="remove" iconCls="icon-remove" onclick="onRemoveNode">删除分类</li>        
					</ul>
		        </div>
    </div>
    <div showCollapseButton="true">
    	<div id="tabs1" class="mini-tabs" activeIndex="0" plain="false" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
  			
  			<div title="岗位列表">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-add" onclick="edit('add')">新增</a> 
								<a class="mini-button" iconCls="icon-edit" onclick="edit('edit')">修改</a>
								<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
								<a class="mini-button" iconCls="icon-node" onclick="displayAll()">所有岗位</a>
								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
							</td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
					<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" autoLoad="true"
					url="${pageContext.request.contextPath}/rst/job_definition/page"  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" >
						<div property="columns">
							<div type="checkcolumn" ></div>
							<div field="id" width="60" headerAlign="center" allowSort="true" align="center" >ID</div>
							<div field="categoryName" width="100" headerAlign="center" allowSort="true" align="left" >岗位类别</div>
							<div field="name" width="140" headerAlign="center" allowSort="true" align="left" >名称</div>
							<div field="code" width="140" headerAlign="center" allowSort="true" align="left">编码</div>
							<div field="sn" width="80" headerAlign="center" allowSort="true" align="center">排序号</div>
							<div type="comboboxcolumn" field="enable" width="80" headerAlign="center" align="center" allowSort="true">是否启用
								<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" />
							</div>
							<!-- <div field="gid" width="160" headerAlign="center" allowSort="true" align="left">全局编码</div> -->
							<div field="createTime" width="150" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
							<div field="updateTime" width="150" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
					
						</div>
					</div>
		        </div>
		    </div>    


  			<div title="分类属性">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-add" onclick="editAttr('add')">新增</a> 
								<a class="mini-button" iconCls="icon-edit" onclick="editAttr('edit')">修改</a>
								<a class="mini-button" iconCls="icon-remove" onclick="removeAttr()">删除</a>
								<a class="mini-button" iconCls="icon-node" onclick="displayAttrAll()">所有属性</a>
								
								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-reload" onclick="refreshAttr()">刷新</a>
							</td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
					<div id="datagrid2" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" showPager="true"
						url="${pageContext.request.contextPath}/rst/job_category_attr/page" pageSize="50" idField="id" autoLoad="true">
						<div property="columns">
							<div type="checkcolumn" ></div>
							<div field="categoryName"  headerAlign="center"  align="left">所属分类</div>
							<div field="name"  headerAlign="center"  align="left">属性全称</div>
							<div field="code"  headerAlign="center"  align="center">编码</div>
							<div field="value"  headerAlign="center" align="center">属性值</div>
						 
							<div type="comboboxcolumn" field="enable" width="80" headerAlign="center" align="center" allowSort="true">是否启用
								<input property="editor" class="mini-combobox" showNullItem="false" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" />
							</div>
							
							<div field="createTime" width="120" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
							<div field="updateTime" width="120" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>  
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
	var grid = mini.get("datagrid1");
	var grid2 = mini.get("datagrid2");
	
	tree.on("nodeclick",function(e){
		var node = e.node;
		//mini.alert(node.id);
		grid.load({categoryId: node.id})
		grid2.load({categoryId: node.id});
	})
	
	function displayAll() {
		grid.load({})
	}
	
	function displayAttrAll() {
		grid2.load({})
	}
	
	function refreshAttr() {
		grid2.reload();
	}
	
	function editAttr(action) {
		var row = grid2.getSelected();
		if ('edit' == action) {
			if(!row) {
				mini.alert("请选择一条记录");
				return ;
			}
		}
		
		
		mini.open({
			url : "${pageContext.request.contextPath}/apps/default/admin/rst/job_category_attr/edit.jsp",
			title : "编辑属性",
			width : 490,
			height : 220,
			onload : function() {
				var iframe = this.getIFrameEl();
				var data = {};
				data.action = action
				if ('edit' == action) {
					data.id = row.id
				}
				
				iframe.contentWindow.SetData(data);
			},
			ondestroy : function(action) {
				grid2.reload();
			}
		});
	}
	
	function removeAttr() {
		var row = grid2.getSelecteds();
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
						'url': "${pageContext.request.contextPath}/rst/job_category_attr/delete?ids="+ids.join(","),
						type: 'post', dataType:'JSON',
						success: function (json) {
							mini.alert("删除成功");
							grid2.reload();
						},
						error : function(data) {
					  		mini.alert(data.responseText);
						}
					});
				}
			}
		);
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
						'url': "${pageContext.request.contextPath}/rst/job_definition/delete?ids="+ids.join(","),
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
	}

	function edit(action) {
		var row = grid.getSelected();
		if ('edit' == action) {
			if(!row) {
				mini.alert("请选择一条记录");
				return ;
			}
		}
		
		mini.open({
			url : "${pageContext.request.contextPath}/apps/default/admin/rst/job_definition/edit.jsp",
			title : "编辑岗位",
			width : 490,
			height : 220,
			onload : function() {
				var iframe = this.getIFrameEl();
				
				var data = {};
				data.action = action
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
	
	// --------------------
   function onEditNode(e) {
	   var sender = e.sender;
	  
	   var action = "";
	   if("新增分类" == sender.text) {
		   action = 'add';
	   }
	   
	   if ("编辑分类" == sender.text) {
		   action = 'edit';
	   }
	   
       var node = tree.getSelectedNode();
      // console.log(node);
       
       var data = {};
       data.id= node.id;
       data.name = node.name;
	   data.action = action;
	//   console.log(data)
       mini.open({
			url : "${pageContext.request.contextPath}/apps/default/admin/rst/job_category/edit.jsp",
			title : "编辑节点",
			width : 490,
			height : 220,
			onload : function() {
				var iframe = this.getIFrameEl();
				iframe.contentWindow.SetData(data);
			},
			ondestroy : function(action) {
				//grid.reload();
				tree.reload();
				tree.expandAll();
			}
		});
   }
	
   function onRemoveNode(e) {
       var tree = mini.get("tree1");
       var node = tree.getSelectedNode();

       if (node) {
    	   mini.confirm("确定删除当前节点 ?（注意：子节点也一并删除！！！）","确定?",function(action){
    		   if(action== 'ok') {
    			   tree.removeNode(node);
	   				$.ajax({
						url : "${pageContext.request.contextPath}/rst/job_category/delete?ids="+node.id,
						dataType: 'json',
						success : function(text) {
							tree.reload();
							tree.expandAll();
						}
					});
    		   }
    	   })
       }
   }
   
	function onBeforeOpen(e) {
	    var menu = e.sender;
	    var tree = mini.get("tree1");
	
	    var node = tree.getSelectedNode();
	    if (!node) {
	        e.cancel = true;
	        return;
	    }
	}
    </script>

</body>
</html>