<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>数据预览</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
    </style>
</head>
<body>
    <div class="mini-toolbar" style="text-align:center;line-height:30px;" borderStyle="border:0;">
          <label >关键字：</label>
          <input id="key" class="mini-textbox" style="width:150px;" onenter="onKeyEnter"/>
          <a class="mini-button" style="width:60px;" onclick="search()">查询</a>
    </div>
    <div class="mini-fit">
		<div id="datagrid1" class="mini-treegrid"" style="width:100%;height:100%;"
			showTreeIcon="true" allowResize="true" expandOnLoad="true"
 			treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false"> 
		    <div property="columns">
		        <!-- <div type="indexcolumn"></div> -->
		        <div field="id" width="80" align="center">对象ID</div>
		        <div field="name" width="160" align="left">姓名</div>
		        <div field="email" width="200" align="left">邮箱</div>
		        <div field="mobile" width="200" align="left">手机</div>
		        <div field="remark" width="80" align="left">备注</div>
		    </div>
		</div>
    </div>           
    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
        <!-- <a class="mini-button" style="width:60px;" onclick="onOk()">确定</a>
        <span style="display:inline-block;width:25px;"></span> -->
        <a class="mini-button" style="width:60px;" onclick="onCancel()">关闭</a>
    </div>
    
	<script type="text/javascript">
	    mini.parse();
	    var grid = mini.get("datagrid1");
	    
	    var definitionId = null;
	    var taskKey = null;
		////////////////////
		//标准方法接口定义
		function SetData(data) {
			var data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
			
			if(data) {
				definitionId = data.definitionId;
				taskKey = data.taskKey;
				
				 var url = "${pageContext.request.contextPath}/act/node_user/view_user?definitionId=" + data.definitionId+"&taskKey="+data.taskKey;
				 grid.setUrl(url);
				 grid.loading("加载中，请稍后......");
				 grid.load();
			}
		}
		
	    function GetData() {
	        var row = grid.getSelected();
	        return row;
	    }
	    
	    function search() {
	        var key = mini.get("key").getValue();
	        if (key!=null && key!='') {
		        var data = grid.getData();
		        var rs = [];
		        for(var i=0;i<data.length;i++) {
		        	//console.log(data[i]);
		        	if( (data[i].name + "").indexOf(key) != -1) {
		        		rs.push(data[i]);
		        	}
		        	
		        	if( (data[i].remark + "").indexOf(key) != -1) {
		        		rs.push(data[i]);
		        	}
		        	
		        	if( (data[i].code + "").indexOf(key) != -1) {
		        		rs.push(data[i]);
		        	}
		        	
		        	if( (data[i].id + "").indexOf(key) != -1) {
		        		rs.push(data[i]);
		        	}
		        }
		        grid.clearRows();
		        grid.setData(rs);
	        	return ;
	        }
	        
	       // grid.reload();  // 没有loading效果
	        ajaxLoad(); // 这个有loading效果！！！
	    }
	    
	    function ajaxLoad() {
	    	grid.loading("加载中，请稍后......");
	    	var url = "${pageContext.request.contextPath}/act/node_user/view_user?definitionId=" + definitionId+"&taskKey="+taskKey;
            $.ajax({
                url: url,
                type: "post",
                success: function (text) {
                	grid.setData(text);
                	grid.unmask(); // 取消遮罩
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
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
