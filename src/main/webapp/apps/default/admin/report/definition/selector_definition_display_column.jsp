<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>用户选择器</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
    </style>
</head>
<body>
	<!-- 
    <div class="mini-toolbar" style="text-align:center;line-height:30px;" borderStyle="border:0;">
          <label >关键字：</label>
          <input id="key" class="mini-textbox" style="width:150px;" onenter="onKeyEnter"/>
          <a class="mini-button" style="width:60px;" onclick="search()">查询</a>
    </div>
     -->
    <div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowSort="false" allowResize="false" multiSelect="true" 
			  idField="id" sizeList="[20,50,100,150,200]" pageSize="50" showPager="false" > 
		    <div property="columns">
				
		    </div>
		</div>
    </div>                
    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
        <a class="mini-button" style="width:60px;" onclick="onOk()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>
    
	<script type="text/javascript">
	    mini.parse();
	    
	    var grid = mini.get("datagrid1");
	    grid.set({
	        columns: [
	            { type: "indexcolumn",width: 20 }
	           // ,{ field: "loginname", header: "loginname<a>bbb</a>",width: 120, headerAlign: "center", allowSort: true },
	           // { field: "name", header: "name", width: 120, headerAlign: "center", allowSort: true }
	        ]
	    });
		//grid.setData([{loginname:"aaaa",name:"zs"},{loginname:"bbb",name:"z2s"}]);
		
	    function GetData() {
	    	var isMutil = '${param.isMutil}';
	    	var row = {};
	    	if(isMutil == 'true') {
	    		row = grid.getSelecteds();
		       
	    	}else {
	        	row = grid.getSelected();
	    	}
	        return row;
	    }
	    
	    function GetDatas() {
	        var rows = grid.getSelecteds();
	        return rows;
	    }
	    
		function GetColumn() {
			var val = $("input[name='head']:checked").val();
			var data = {};
			data.code = val;
			return data;
		}
		
	    function search() {
	        var key = mini.get("key").getValue();
	        grid.load({ key: key });
	    }
	    
		////////////////////
		//标准方法接口定义
		function SetData(data) {
			data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
			
			$.ajax({
				url : "${pageContext.request.contextPath}/report/definition/display_column",
				dataType: 'json', type : 'post', data: data,
				success : function(text) {
					if(text) {
						var header = [];
						if(text && text.data.length>0) {
							var o = mini.decode(text).data;
							header = Object.keys(o[0]);
						}
				       var  columns = [
				  	           	// { type: "indexcolumn",width: 50 }
				  	        ]
				       for(var i=0;i<header.length;i++) {
				    	   var hd='<input id="'+(""+header[i]+i)+'" name="head" type="radio" value="'+header[i]+'"/> <label for="'+(""+header[i]+i)+'">'+header[i]+'</label>';
				    	   columns.push({ field: header[i], header: hd,width: 120, headerAlign: "center", allowSort: true })
				       }
				       
				       grid.set({columns: columns,allowSortColumn:false  });
					   grid.setData(o);
					   
				       grid.on("drawcell", function (e) {
				            var record = e.record,
						        column = e.column,
						        field = e.field,
						        value = e.value;
				           
				              if (mini.isDate(value)) {
				            	 // e.cellStyle = "width:280px;align:center"; 样式没有效...
				            	  e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm:ss");
				              }
				        })

					}
				}
			});
		 
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
