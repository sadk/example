<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>栏目选择器</title>
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
								<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
							</td>
							<td style="white-space:nowrap;">
		                        <input id="key" name="key" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="search()">查询</a>
		                    </td>
						</tr>
					</table>
				</div>
				<div class="mini-fit">
					<div id="datagrid1" class="mini-treegrid"" style="width:100%;height:100%;"
					showTreeIcon="true" allowResize="true" expandOnLoad="true"  multiSelect="true" 
    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false" 
					url="${pageContext.request.contextPath}/channel/list" > 
					    <div property="columns">
					        <div type="checkcolumn" ></div>
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
				    <div class="mini-toolbar" style="text-align:center;padding-top:6px;padding-bottom:6px;" borderStyle="border:0;">
				        <a class="mini-button" style="width:60px;" onclick="onOk()">确定</a>
				        <span style="display:inline-block;width:25px;"></span>
				        <a class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
				    </div>
			</div>
		</div>

		<script type="text/javascript">
		mini.parse();
		
		var tree = mini.get("tree1");
		var grid = mini.get("datagrid1");
		var key = mini.get("key");
		
        tree.on("nodeselect", function (e) {
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

        function search() {
        	var data = {};
        	var node = tree.getSelectedNode();
        	if(node) {
        		data.appCode = node.code;
        	}
        	data.key = key.getValue();
        	
        	grid.load(data);
        }
        
	    function GetData() {
	    	row = grid.getSelected();
	        return row;
	    }

	    function GetDatas() {
	    	rows = grid.getSelecteds();
	        return rows;
	    }
	    
	    function onKeyEnter(e) {
	        search();
	    }
	    function onRowDblClick(e) {
	        onOk();
	    }
	    //////////////////////////////////
	    function CloseWindow(action) {
	        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	        else window.close();
	    }
	
	    function onOk() {
	        CloseWindow("ok");
	    }
	    function onCancel() {
	        CloseWindow("cancel");
	    }
	
		</script>
	</body>
</html>