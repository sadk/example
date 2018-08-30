<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>报表业务按钮配置</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
    </style>
</head>
<body>


<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:530px;">
    <div title="操作按钮" >
	    <div class="mini-toolbar">
	          <a class="mini-button" iconCls="icon-add" plain="false" onclick="edit('add')">新增</a>
	          <a class="mini-button" iconCls="icon-edit" plain="false" onclick="edit('edit')">编辑</a>
	          <a class="mini-button" iconCls="icon-remove" plain="false" onclick="remove()">删除</a>
	          <!--  -->
	          <a class="mini-button" iconCls="icon-save" plain="false" onclick="save()">保存</a>
	          
	          <span class="separator"></span>
	          <a class="mini-button" iconCls="icon-reload" onclick="ajaxLoad()">刷新</a>
	    </div>
	    <div class="mini-fit">
			<div id="datagrid1"  class="mini-datagrid" style="width:100%;height:100%;" sortMode="client" idField="id" 
				multiSelect="true" showEmptyText="true" emptyText="查无数据" showPager="false" allowResize="false"  
				allowAlternating="true" allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true" editNextRowCell="true"> 
			    <div property="columns">
			        <div type="checkcolumn" ></div>
			        <div field="name" width="100" align="left"  headerAlign="center">按钮名称
			        	<input property="editor" class="mini-textbox" style="width:100%;"/>
			        </div>
			        <div field="code" width="100" align="left"  headerAlign="center">按钮编码
			        	<input property="editor" class="mini-textbox" style="width:100%;"/>
			        </div>
			        <div type="comboboxcolumn" field="type" width="80" headerAlign="center" allowSort="true" align="left">控件类型
						<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=report_biz_controll_type" />
					</div>
					<div field="eventName" width="100" align="left"  headerAlign="center">按钮编码
			        	<input property="editor" class="mini-textbox" style="width:100%;"/>
			        </div>
					<div field="btnScript" width="160" align="left"  headerAlign="center">响应脚本
			        	<input property="editor" class="mini-textarea" style="width:100%;"/>
			        </div>
					<div field="remark" width="100" align="left"  headerAlign="center">备注
						<input property="editor" class="mini-textbox" style="width:100%;"/>
					</div>
			    </div>
			</div>
	    </div>  
    </div>
</div>
         
    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
      <!--   <a class="mini-button" style="width:60px;" onclick="onOk()">确定</a> -->
       <!--  <span style="display:inline-block;width:25px;"></span> -->
       <a class="mini-button" style="width:60px;" onclick="onCancel()">关闭</a>
    </div>
    
	<script type="text/javascript">
	    mini.parse();
	    
	    var definitionId = null;
	    
	    var grid = mini.get("datagrid1");

		grid.on("rowclick", function(e){
			var record = e.record;
 
		});
		
		function edit(action) {
			var row = grid.getSelected();
			if(action == 'edit'){
				if(!row){
					mini.alert("请选择一条记录");
					return ;
				}
			}
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/admin/report/resource/edit.jsp",
				title : "设置报表（按钮）控件",
				width : 530,
				height : 420,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {}		
					if(action == 'edit') {
						data.id=row.id;
					}
					data.definitionId = definitionId; //报表ID
					data.action = action;
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					ajaxLoad();
				}
			});
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		function save() {
		    var data = grid.getChanges();
            var json = mini.encode(data);
            $.ajax({
                url: "${pageContext.request.contextPath}/report/resource/save_or_update_json",
                data: { data: json },  type: "post",
                success: function (text) {
                	ajaxLoad();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
		}
		 
		
	    function remove() {
	    	var row = grid.getSelecteds();
	    	if(row && row.length == 0) {
	    		mini.alert("请选择一条按钮删除");
	    		return ;
	    	}
	    	
	    	var ids = [];
	    	for(var i=0;i<row.length;i++) {
	    		ids.push(row[i].id);
	    	}
	        mini.confirm("确定删除？", "确定？",
	                function (action) {
	                    if (action == "ok") {
		           	    	 $.ajax({
		     	                url: "${pageContext.request.contextPath}/report/resource/delete_by_ids?ids="+ids.join(",") ,
		     	                type: "post",
		     	                success: function () {
		     	                	ajaxLoad();
		     	                	mini.alert("删除成功");
		     	                },
		     	                error: function (jqXHR, textStatus, errorThrown) {
		     	                    alert(jqXHR.responseText);
		     	                }
		     	        	});
	                    }
	                }
	            );
	        

	    }
	    
 
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    function ajaxLoad() {
	    	var data = {};
	    	data.definitionId = definitionId;
	    	
	    	grid.loading("加载中，请稍后......");
	    	var url = "${pageContext.request.contextPath}/report/resource/list";
            $.ajax({
                url: url,
                type: "post",
                data : data,
                success: function (data) {
                	if(data) {
                		data = mini.decode(data);
	                	grid.setData(data);
	                	grid.unmask(); // 取消遮罩
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            }); 
	    }
	    
		////////////////////
		//标准方法接口定义
		function SetData(data) {
			var data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
		 	console.log(data)
		 	
		 	definitionId = data.definitionId;
			
			if(data.action == 'edit') {
				
				if(definitionId!=null && definitionId!='') {
					ajaxLoad();
				}
			}
		}
		
	    function GetData() {
	        var row = grid.getSelected();
	        return row;
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
