<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>授权管理</title>
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
			
    .function-item 
    {
        margin-left:5px;
        margin-right:5px;
    }
    .function-item input
    {
        vertical-align:bottom;
    }
		</style>
	</head>
	<body>
		<div class="mini-splitter" style="width:100%;height:100%;">
		    <div size="240" showCollapseButton="true">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;"> 
		        	<!--                
		            <span style="padding-left:5px;">角色：</span>
		             -->
		            <input id="myRoleId" name="myRoleId" width="170" emptyText="请输入或选择角色名"  onenter="search" class="mini-buttonedit" onbuttonclick="onButtonEdit" /> 
		            <a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查找</a>
		            <!--    
		            <a class="mini-button" iconCls="icon-search" plain="true">查找</a>   
		             -->               
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/role/tree" style="width:100%;"
		                 showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false">        
		            </ul>
		        </div>
		    </div>
			<div showCollapseButton="true">
				<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<!-- 
								<a class="mini-button" iconCls="icon-add" onclick="add()">添加</a>
								<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
								<a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
							 	-->
							 	<a class="mini-button" iconCls="icon-save" plain="true"  onclick="save()">保存</a>
								<a class="mini-button" iconCls="icon-node"  onclick="getFuns()">查看</a>
								 
								<span class="separator"></span>
								<!-- 
								<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
								<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
								<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
								 -->
							</td>
							
							<!-- 
							<td style="white-space:nowrap;">
		                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="search()">查询</a>
		                    </td>
		                     -->
						</tr>
					</table>
				</div>
				<div class="mini-fit">
				
					<div id="treegrid1" class="mini-treegrid" style="width:100%;height:100%;"     
					    url="${pageContext.request.contextPath}/res/tree" multiSelect="true" 
					    treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  
					    allowResize="true" expandOnLoad="true" showTreeIcon="true"  showCheckBox="true" checkRecursive="true"
					    allowSelect="false" allowCellSelect="false" enableHotTrack="false"
					    ondrawcell="ondrawcell" >
					    <div property="columns">
					        <div type="indexcolumn" ></div>
					        <div name="name" field="name" width="220" headerAlign="center">模块菜单</div>
					        <div field="functions" width="100%" headerAlign="center">权限</div>
					    </div>
					</div>
					
				</div>
			</div>
		</div>
		<script type="text/javascript">
		mini.parse();
		
		var treeLeft = mini.get("tree1");
	    var tree = mini.get("treegrid1");
	    
		var grid = mini.get("datagrid1");
		var myRoleId = mini.get("myRoleId");
		
		function save() {
			console.log(myRoleId)
			var roleIdString = "";
			var roleIds = myRoleId.getValue(); //选择器的角色
			var roleId  = treeLeft.getValue();
			
			if(roleIds == '' && roleId == '') {
				mini.alert("请至少选择一个角色");
				return ;
			}
			
			if(roleIds!= '') {
				roleIdString += roleIds;
			}
			if(roleId!='') {
				roleIdString += roleId;
			}
			
			var menuIds = tree.getValue(true);
			
			var ajaxSave = function (o) {
				console.log(o)
				$.ajax({
					url : "${pageContext.request.contextPath}/auth/grant/save_or_update_menu_function",
					dataType: 'json', type : 'post',
					data: o,
					success : function(text) {
						
					}
				});
			}
			
			if (menuIds == null || menuIds == '') {
		        mini.confirm("您没有选择任何权限项，已选的角色权限将会清除!!!", "确定？",
		                function (action) {
		                    if (action == "ok") {
		                        var data = {};
		                        data.roleIds = roleIdString;
		                        data.menuIds = menuIds;
		                        ajaxSave(data);
		                    } 
		                }
		            );
			}
		}
		
		 
		
		treeLeft.on("nodeselect", function (e) {
        	//0=租户 1=应用 2=栏目
        	/*
        	if(e.node.nodeType == "2") {
        		grid.load({ parentCode: e.node.code });
        	} else if(e.node.nodeType == "1") {
        		grid.load({ appCode: e.node.code });
        	} else if(e.node.nodeType == "0") {
        	 	grid.load({ tenantCode: e.node.code });
        	}*/
        	grid.load({ id: e.node.id });
        });
        
        function search() {
        	grid.load({ id: roleId.getValue()});
        }
        
        function onButtonEdit(e) {
            var btnEdit = this;
            mini.open({
                url: "${pageContext.request.contextPath}/apps/default/admin/uum/role/selector_role.jsp?isMutil=true",
                title: "选择角色",
                width: 650,
                height: 380,
                ondestroy: function (action) {
                    
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);    //必须
                        if (data && data.length>0) {
                        	var ids = new Array();
                        	var names = new Array();
                        	for(var i=0;i<data.length;i++){
                        		ids.push(data[i].id);
                        		names.push(data[i].name);
                        	}
                            btnEdit.setValue(ids.join(","));
                            btnEdit.setText(names.join(","));
                            
                           
                            treeLeft.load({ids:ids.join(",")})
                           
                        } else {
                        	btnEdit.setValue(null);
                            btnEdit.setText(null);
                        	tree.load()
                        }
                    }

                }
            });
        }
         
        // -------------------------------------------------------
    function ondrawcell(e) {
        var tree = e.sender,
            record = e.record,            
            column = e.column,
            field = e.field,
            id = record[tree.getIdField()],
            funs = record.functions;

        function createCheckboxs(funs) {
            if (!funs) return "";
            var html = "";
            for (var i = 0, l = funs.length; i < l; i++) {
                var fn = funs[i];
                var clickFn = 'checkFunc(\'' + id + '\',\'' + fn.action + '\', this.checked)';
                var checked = fn.checked ? 'checked' : '';
                html += '<label class="function-item"><input onclick="' + clickFn + '" ' + checked + ' type="checkbox" name="'
                        + fn.action + '" hideFocus/>' + fn.name + '</label>';
            }
            return html;
        }

        if (field == 'functions') {
            e.cellHtml = createCheckboxs(funs);
        }
    }

    function getFuns() {
        var data = tree.getData();
        var json = mini.encode(data);
      //console.log(json)
      
      
        var value = tree.getValue(true);
        alert(value);
        
    }
    function checkFunc(id, action, checked) {
        var record = tree.getRecord(id);
        if(!record) return;
        var funs = record.functions;
        if (!funs) return;
        function getAction(action) {
            for (var i = 0, l = funs.length; i < l; i++) {
                var o = funs[i];
                if (o.action == action) return o;
            }
        }
        var obj = getAction(action);
        if (!obj) return;
        obj.checked = checked;
    }
		
		</script>
	</body>
</html>
