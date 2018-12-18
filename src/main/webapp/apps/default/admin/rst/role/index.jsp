<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>用户角色表</title>

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
				<div id="form1"  style="padding:8px;">
					<table>	
						<tr>
							<td>关键字 ：</td>
							<td>
								<input id="reportDefinitionId" name="reportDefinitionId" class="mini-hidden" value=""/>
								<input id="key" name="key" style="width:140px" class="mini-textbox" emptyText="请输入关键字搜索" style="width: 150px;" onenter="search"/>
							</td>
						</tr>
						
						<tr>
							<td>角色名称：</td>
							<td>
								<input id="name" name="name"  style="width:140px" class="mini-textbox"  emptyText="请输入角色名称"  onenter="search"  />
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
    	<div id="tabs1" class="mini-tabs" activeIndex="0" plain="false" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
  			
  			<div title="角色信息">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-add" onclick="edit('add')">新增</a>
								<a class="mini-button" iconCls="icon-edit" onclick="edit('edit')">编辑</a>
								<a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
								<span class="separator"></span>
								<a class="mini-button" iconCls="icon-reload" onclick="refresh()">刷新</a>
							</td>
							 
						</tr>
					</table>
		        </div>
		        <div class="mini-fit" >
					<div id="grid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" showPager="true"
						url="${pageContext.request.contextPath}/rst/role/page"  idField="id" autoLoad="true">
						<div property="columns">
							<div type="checkcolumn" ></div>
							<div field="id" width="40"  headerAlign="center">ID</div>
							<div field="code"   headerAlign="center">编码</div>
							<div field="name"   headerAlign="center">名称</div>
							<div field="remark"   headerAlign="center">备注</div>
							<div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
						</div>
					</div>
		        </div>
		    </div>    

        </div>
    </div>        
</div>
    
    <script type="text/javascript">
    mini.parse();
	var grid = mini.get("grid1");
	var form = new mini.Form("form1");
	
	function remove() {
		var rows=grid.getSelecteds();
		if(rows.length ==0){
			mini.alert("请至少选择一个角色");
			return ;
		}
		var arr = [];
		for(var i=0;i<rows.length;i++) {
			arr.push(rows[i].id);
		}


        mini.confirm("确定删除记录？", "确定？",
                function (action) {
                    if (action == "ok") {
                    	deleteByIds();
                    }
                }
            );
      
        var deleteByIds = function() {
			$.ajax({
				url : "${pageContext.request.contextPath}/rst/role/delete?ids=" + arr.join(","),
				dataType: 'json',
				success : function(text) {
					grid.reload();
				}
			});
        }
	}
	
	function edit(action) {
		var row = grid.getSelected();
		if(action == 'edit') {
			if(!row){
				mini.alert("请选择一个角色");
				return ;
			}
		}
		
		mini.open({
			url : "${pageContext.request.contextPath}/apps/default/admin/rst/role/edit.jsp",
			title : "编辑角色",
			width : 300,
			height : 250,
			onload : function() {
				var iframe = this.getIFrameEl();
				var data = {};
				data.action = action;
				if(action == 'edit') {
					data.id = row.id;
				}
				
				
				iframe.contentWindow.SetData(data);
			},
			ondestroy : function(action) {
				if ('save' == action) {
					grid.reload();
				}
			}
		});
	}

	
	function refresh() {
		var messageid = mini.loading("Loading, Please wait ...", "Loading");
        setTimeout(function () {
            mini.hideMessageBox(messageid);
        }, 1000);
        grid.reload();
	}
	
 
    function search() {
    	var data = form.getData();
    	form.validate();
		if(form.isValid() == false) return;
		
    	
     
    	grid.load(data);
    	
		grid.on("drawcell", function (e) { //如果有日期类型，转换为人工可讯形式
		   var record = e.record,
		   column = e.column,
		   field = e.field,
		   value = e.value;
		   
		   if (mini.isDate(value)) {
	    		e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm:ss");
		   }
		   
 

		})
    }
    
    function clear() {
    	form.clear();
    }
    
    // ----------------------------
    function loading(){
        mini.mask({
            el: document.body,
            cls: 'mini-mask-loading',
            html: '正在批量处理数据，请稍后 ...'
        });
	}
	
    function loadingAutoClose(timeout) {
        mini.mask({
            el: document.body,
            cls: 'mini-mask-loading',
            html: '正在批量处理数据，请稍后 ...'
        });
        
        setTimeout(function () {
            mini.unmask(document.body);
        }, timeout);
    }
    
    function loadingClose(){
    	 mini.unmask(document.body);
    }
    </script>

</body>
</html>