<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>站点资源</title>

    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>  
    <link href="filemanager.css" rel="stylesheet" type="text/css" />
   <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
</head>
<body>
   
    
<div class="mini-splitter" style="width:100%;height:100%;">
    <div size="240" showCollapseButton="true">
        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <span style="padding-left:5px;">租户：</span>
		            <input id="tenantCode" name="tenantCode" width="180" class="mini-buttonedit" onbuttonclick="onButtonEdit" />                 
        </div>
        <div class="mini-fit">
            <ul id="folderTree" class="mini-tree" url="${pageContext.request.contextPath}/application/list" style="width:100%;"
                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" 
                 contextMenu="#treeMenu" onnodeselect="onSelectNode">        
            </ul>
			<ul id="treeMenu" class="mini-contextmenu"  onbeforeopen="onBeforeOpen">        
			    <li iconCls="icon-move" onclick="onMoveNode">移动节点</li>
			    <li class="separator"></li>
			    <li>
					<span iconCls="icon-add">新增节点</span>
					<ul>
					    <li onclick="onAddBefore">插入节点前</li>                
			            <li onclick="onAddAfter">插入节点后</li>	
						<li onclick="onAddNode">插入子节点</li>	             
					</ul>
				</li>
				<li name="edit" iconCls="icon-edit" onclick="onEditNode">编辑节点</li>
				<li name="remove" iconCls="icon-remove" onclick="onRemoveNode">删除节点</li>        
			</ul>
        </div>
    </div>
    <div showCollapseButton="true">
    	<div id="tabs1" class="mini-tabs" activeIndex="0" plain="false" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
  			
  			<div title="目录结构管理" >
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-edit" plain="false" onclick="initRoot()">初使化系统根目录</a>
								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-add" onclick="add()">添加</a>
								<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
								<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
								<a class="mini-button" iconCls="icon-node" onclick="edit('view')">查看</a>
								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
							</td>
							<td style="white-space:nowrap;">
		                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="search()">查询</a>
		                    </td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
					<div id="datagrid1" class="mini-treegrid" style="width:100%;height:100%;"
					showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="false" 
    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false" 
					url="${pageContext.request.contextPath}/resource/list" > 
					    <div property="columns">
					        <div type="checkcolumn"></div>
					        <div name="name" field="name" width="160" headerAlign="center">名称</div>
					        <div field="code" width="80" headerAlign="center">编码</div>
					        <div field="sn" width="30" align="right" headerAlign="center">序号</div>
					        <div field="appName" width="80" align="left">所属应用</div>
					        <div field="appCode" width="60" align="left">应用编码</div>
					        
					        <div field="nodePath" width="60" align="left">结点路径</div>
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
		    
		    

  			<div title="文件管理" >
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-add" plain="false" onclick="upload()">上传</a>
								<a class="mini-button" iconCls="icon-download" plain="false">下载</a>
								<span class="separator"></span>
								
							</td>
							<td style="white-space:nowrap;">
		                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="search()">查询</a>
		                    </td>
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
					            <div>
					                <div id="filesDataView" style="width:100%;height:100%;overflow:auto;display:none;">
					                    <div id="filesView" class="filesView">
					                    </div>
					                </div>
					                <div id="filesListBox" textField="name" class="mini-listbox" 
					                    style="width:100%;height:100%;overflow:auto;display:none;" borderStyle="border:0;">
					                    <div property="columns">
					                        <div field="name" width="auto">名称</div>
					                        <div field="updatedate" width="100">修改日期</div>
					                        <div field="type" width="100">类型</div>
					                        <div field="size" width="100">大小</div>
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
	var grid = mini.get("datagrid1");
	var folderTree = mini.get("folderTree");
	
	function refresh() {
		var messageid = mini.loading("Loading, Please wait ...", "Loading");
        setTimeout(function () {
            mini.hideMessageBox(messageid);
        }, 1000);
        grid.load({})
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
                        
                       //	console.log(data.code)
                        folderTree.load({tenantCode:data.code})
                    } else {
                    	btnEdit.setValue(null);
                        btnEdit.setText(null);
                        folderTree.load()
                    }
                }

            }
        });
    }
    
	function initRoot() {
        mini.confirm("确定初使化记录？", "确定？",
                function (action) {
                    if (action == "ok") {
                		$.ajax({
                			'url': "${pageContext.request.contextPath}/resource/init_root",
                			type: 'post',
                			dataType:'JSON',
                			cache: false,
                			async:false,
                			success: function (json) {
                				mini.alert("初使化成功");
                				folderTree.reload();
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
	
	function add() {
		var row = grid.getSelected();
		var idNotIn = null;
		var pid = null;
		var parentName = null;
		if(row){
			idNotIn = row.id;
			pid = row.id;
			parentName = row.name;
		}else{
			idNotIn = -1;
			pid = -1;
		}
		var node = folderTree.getSelected();
		//mini.alert(mini.encode(node))
		mini.open({
			url : "${pageContext.request.contextPath}/apps/default/admin/cms/resource/edit.jsp",
			title : "添加结点",
			width : 490,
			height : 250,
			onload : function() {
				var iframe = this.getIFrameEl();
				var data = {
					action : "add",
					pid : pid ,
					parentName　:　parentName,
					idNotIn : idNotIn,
					appCode : null
				};
				if(node) {
					data.appCode = node.code;
				}
				iframe.contentWindow.SetData(data);
			},
			ondestroy : function(action) {
				grid.reload();
			}
		});
	}
	function remove() {
		var row = grid.getSelected();
		if(row) {
			mini.confirm("删除当前结点，其子结点也即将删除，确定删除？", "确定？",
					function (action) {
						if (action == "ok") {
							$.ajax({
								'url': "${pageContext.request.contextPath}/resource/delete?ids="+row.id,
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
	
	function edit(action) {
		var row = grid.getSelected();
		if(typeof(action) == 'undefined') {
			action = "edit";
		}
		
		if (row) {
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/admin/cms/resource/edit.jsp",
				title : "编辑机构信息",
				width : 490,
				height : 250,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {
						action : action,
						id : row.id
					};
					//alert(row.id);
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
    
    

    ////////////////////////////////////////
    var ViewType = "image"; //list, image
    
    function refreshFiles(data) {
        if (!data) data = [];

        var filesListBox = mini.get("filesListBox");
        var filesDataView = document.getElementById("filesDataView");
        filesListBox.setVisible(ViewType != "image");
        filesDataView.style.display = ViewType == "image" ? "block" : "none";

        if (ViewType == "image") {

            var sb = [];
            for (var i = 0, l = data.length; i < l; i++) {
                var file = data[i];
                sb[sb.length] = '<a class="file" href="javascript:void(0)" onclick="return false;" hideFocus>';
                sb[sb.length] = '<image class="file-image" ';
                var src = "filetype/file.png";
                sb[sb.length] = ' src="' + src + '"';
                sb[sb.length] = ' />';
                sb[sb.length] = '<span class="file-text" >';
                sb[sb.length] = file.name;
                sb[sb.length] = '</span>';
                sb[sb.length] = '</a>';
            }
            sb.push('<div style="clear:both;"></div>');

            document.getElementById("filesView").innerHTML = sb.join('');
        } else {

            filesListBox.load(data);
        }
    }

   

    function showFiles(node) {
        //var messageid = mini.loading("Loading, Please wait ...", "Loading");
        $.ajax({
            url: "${pageContext.request.contextPath}/resource/list?appCode=" + node.code,
            success: function (text) {
                var files = mini.decode(text);
                refreshFiles(files);
            }
        });
    }

    function onSelectNode(e) {
    	//console.log(e.node)
        if (e.node) {
        	showFiles(e.node);
        	grid.load({appCode: e.node.code})
        }
    }

    function onTreeDataLoad(e) {
        var node = folderTree.getNode(1);
        folderTree.selectNode(node);
        showFiles(node);
    }


    folderTree.load("${pageContext.request.contextPath}/application/list");
    
    
    
    // ------------------------------------------- 树控件点右键
        function onAddBefore(e) {
            var tree = mini.get("folderTree");
            var node = tree.getSelectedNode();

            var newNode = {};
            tree.addNode(newNode, "before", node);
        }
        function onAddAfter(e) {
            var tree = mini.get("folderTree");
            var node = tree.getSelectedNode();

            var newNode = {};
            tree.addNode(newNode, "after", node);
        }
        function onAddNode(e) {
            var tree = mini.get("folderTree");
            var node = tree.getSelectedNode();

            var newNode = {};
            tree.addNode(newNode, "add", node);
        }
        function onEditNode(e) {
            var tree = mini.get("folderTree");
            var node = tree.getSelectedNode();
            
            tree.beginEdit(node);            
        }
        function onRemoveNode(e) {
            var tree = mini.get("folderTree");
            var node = tree.getSelectedNode();

            if (node) {
                if (confirm("确定删除选中节点?")) {
                    tree.removeNode(node);
                }
            }
        }
        function onMoveNode(e) {
            var tree = mini.get("folderTree");
            var node = tree.getSelectedNode();

            mini.alert("moveNode");
        }
	    function onBeforeOpen(e) {
	        var menu = e.sender;
	        var tree = mini.get("folderTree");
	
	        var node = tree.getSelectedNode();
	        if (!node) {
	            e.cancel = true;
	            return;
	        }
	        if (node && node.text == "Base") {
	            e.cancel = true;
	            //阻止浏览器默认右键菜单
	            e.htmlEvent.preventDefault();
	            return;
	        }
	
	        ////////////////////////////////
	        var editItem = mini.getbyName("edit", menu);
	        var removeItem = mini.getbyName("remove", menu);
	        editItem.show();
	        removeItem.enable();
	
	        if (node.id == "forms") {
	            editItem.hide();
	        }
	        if (node.id == "lists") {
	            removeItem.disable();
	        }
	    }
    </script>

</body>
</html>