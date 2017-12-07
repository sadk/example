<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>内容管理模板</title>

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
            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/channel/tree" style="width:100%;"
                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false"
            >        
            </ul>
        </div>
    </div>
    <div showCollapseButton="true">
    	<div id="tabs1" class="mini-tabs" activeIndex="0" plain="false" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
  			<div title="模板列表" >
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-add" plain="false" onclick="edit('add')">添加</a>
								<a class="mini-button" iconCls="icon-edit" plain="false" onclick="edit('edit')">编辑</a>
								<a class="mini-button" iconCls="icon-remove" plain="false" onclick="remove()">删除</a>
								<span class="separator"></span>  
								<a class="mini-button" iconCls="icon-reload" plain="true" onclick="refresh()">刷新</a>
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
		                borderStyle="border:0;"  url="${pageContext.request.contextPath}/template/page"
		                showFilterRow="false" allowCellSelect="true" allowCellEdit="true">
		                <div property="columns">
		                	<div type="checkcolumn"></div>        
		                    <div field="id" width="60" headerAlign="center" allowSort="true">ID</div>      
		                    <div field="name" width="120" headerAlign="center" allowSort="true">名称</div>                
		                    <div field="title" width="200" allowSort="true" align="center" headerAlign="center">标题</div>   
		                    <!--    
		                    <div field="content" width="100" allowSort="true">内容</div>
		                     --> 
		                    <div field="channelName" width="80" allowSort="true" align="center" headerAlign="center">栏目</div> 
		                    <div field="pathThumbnailSmall" width="100" allowSort="true" align="left" headerAlign="center">缩略图</div>
					        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
					        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>                
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
        var grid = mini.get("grid1");
        
        grid.load();
        
        tree.on("nodeselect", function (e) {
        	if(e.node.nodeType == "1") { //1=应用 2=栏目
        		grid.load({ appCode: e.node.appCode });
        	} else {
        	 	grid.load({ channelId: e.node.id });
        	}
        });

		grid.on("drawcell", function(e){
			var record = e.record;
			var field = e.field;
			var value = e.value;
			var row = e.row;
			if(typeof(row.pathThumbnailSmall) != 'undefined' && row && field == "pathThumbnailSmall" && row.id){
				//alert(11);
				e.cellHtml = '<img src="${pageContext.request.contextPath}'+row.pathThumbnailSmall+'"/>';  
			}
		});
        
		function refresh() {
			 grid.reload();
		}
		
		function search() {
			var key=mini.get("key2").getValue();
			 grid.load({key:key});
		}
		
        function edit(action){
        	var title ="";
        	if(action == 'add') {
        		title="添加"
        	}else if(action =='edit') {
        		title="编辑"
        	}
        	
        	var row = grid.getSelected();
        	if(action == 'edit' && !row) {
        		alert("请选择一条记录");
        		return ;
        	}
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/admin/cms/template/edit.jsp",
				title : title,
				width : 750,
				height : 480,
				
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {
						action : action
					};
					if(action == 'edit') {
						data.id = row.id
					}
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					//if (action == "ok") {
			            var iframe = this.getIFrameEl();
			            /*
			            var datas = iframe.contentWindow.GetDatas();
			            datas = mini.clone(datas);   
			           */
			            grid.reload();
			       // }
				}
			});
        }
         
        function remove() {
           var rows = grid.getSelecteds();
           if(rows.length == 0) {
        	   mini.alert("请至少选择一条记录")
        	   return ;
           }
           
           var ids = new Array();
           for(var i=0;i<rows.length;i++) {
        	   ids.push(rows[i].id);
           }
			$.ajax({
				url : "${pageContext.request.contextPath}/template/delete?ids=" + ids.join(","),
				dataType: 'json',
				cache : false,
				success : function(text) {
					mini.alert("删除成功");
					grid.reload();
				}
			});
        }
      
    </script>

</body>
</html>