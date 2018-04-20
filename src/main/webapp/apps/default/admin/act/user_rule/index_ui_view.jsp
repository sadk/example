<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户规则定义</title>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/pagertree.js" ></script>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<style type="text/css">
			body {
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
   <div class="mini-toolbar" style="padding:1px;border-bottom:0;">
        <table style="width:100%;">
            <tr>
            <td style="width:100%;">
                <a class="mini-button" iconCls="icon-save" onclick="save()">保存</a>
                <span class="separator"></span>
                <a class="mini-button" iconCls="icon-reload" plain="true" onclick="refresh()">刷新</a>
                <a class="mini-button" iconCls="icon-edit" plain="true" onclick="download()">导出Excel</a>
            </td>
            <td style="white-space:nowrap;"><label style="font-family:Verdana;">名称: </label>
                <input class="mini-textbox" id="key" name="key"/>
                <a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查询</a>
                </td>
            </tr>
        </table>
    </div>
    
    <!--撑满页面-->
    <div class="mini-fit" >
		<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true" showColumns="true"
			url="${pageContext.request.contextPath}/act/user_rule_matrix_dept_user/org/page"  idField="id" sizeList="[20,50,100,200,500,1000,2000,5000]" pageSize="50"
			 frozenStartColumn="0" frozenEndColumn="3"   allowCellEdit="true" allowCellSelect="true" showCellTip="true" 
			 editNextOnEnterKey="true"  editNextRowCell="true"
			 allowAlternating="false" sortMode="client">
	        <div property="columns">
		        <div field="id" width="60" headerAlign="center">ID</div>
		        <div field="pid" width="60" headerAlign="center">父ID</div>
		        <div field="name" width="150" headerAlign="center" >名称</div> 
		        <!-- 
		        <div field="typeDesc" width="80" headerAlign="center">类型</div>
		         -->
		        <div field="nodePathText" width="350" headerAlign="center" align="left">结点路径导航</div>
				
				<c:forEach items="${columns}" var="item">
					 <div field="userRule_${item.id}" width="200" headerAlign="center">${item.name}(<a href="javascript:openSet(1,${item.id},'${item.name}')">设置</a>)&nbsp;(<a href="javascript:openSet(0,${item.id},'${item.name}')">查看</a>)
					 	<input property="editor" class="mini-textbox" onclick="onButtonEdit" emptyText="请选择用户"/>
					 </div>
				</c:forEach>
		        <!-- 
		        <div field="sn" width="30" align="right" headerAlign="center">序号</div>
		        <div field="appCode" width="60" align="left">所属应用</div>
		        <div field="nodePath" width="100" align="left">结点路径</div>
		        -->
		       
		       
		        <!--  
		        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
		        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
		         -->         
	        </div>
	    </div>
    </div>




</body>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        var key = mini.get("key");
        
       // grid.frozenColumns (0,2)
        grid.load({sortOrder:"asc",sortFieldGbk:"A.org_node_path_text"});
       
       var lastSelector = null;
       
       function exportExcel() {
			//loadingAutoClose(20000);
			//window.location = "${pageContext.request.contextPath}/act/user_rule_matrix_dept_user/export_all";
    	  
			//window.open("${pageContext.request.contextPath}/act/user_rule_matrix_dept_user/export_all","_blank");
    	   
			loading();
			$.ajax({
				url : "${pageContext.request.contextPath}/act/user_rule_matrix_dept_user/export_all",
				//responseType : "Blob",
				type: "get",
				dataType: "binary",
				success : function(text) {
					loadingClose()
					//console.log(text)
					// 搞不懂文件没有自动弹到浏览器，因text是字符型，不是blob型，使用原生ajax请求,见download
    	           
				},error : function(data) {
			  		mini.alert(data.responseText);
				}
			});
       }
       
       function download() {
    	   loading();
    	   var url = '${pageContext.request.contextPath}/act/user_rule_matrix_dept_user/export_all';
    	   var xhr = new XMLHttpRequest();
    	   xhr.open('GET', url, true);        // 也可以使用POST方式，根据接口
    	   xhr.responseType = "blob";    // 返回类型blob
    	   // 定义请求完成的处理函数，请求前也可以增加加载框/禁用下载按钮逻辑
    	   xhr.onload = function () {
    	       // 请求完成
    	       if (this.status === 200) {
    	    	   
    	    	   loadingClose()
    	    	   
    	           // 返回200
    	           var blob = this.response;
    	           var reader = new FileReader();
    	           reader.readAsDataURL(blob);    // 转换为base64，可以直接放入a表情href
    	           reader.onload = function (e) {
    	        	   
    	               // 转换完成，创建一个a标签用于下载
    	               var a = document.createElement('a');
    	               a.download = 'data.xlsx';
    	               a.href = e.target.result;
    	               $("body").append(a);    // 修复firefox中无法触发click
    	               a.click();
    	               $(a).remove();
    	           }
    	       }
    	   };
    	   // 发送ajax请求
    	   xhr.send()
    	}
       
       function save() {
    	  var rows= grid.getChanges();
    	  for(var i=0;i<rows.length;i++){
    		//console.log(rows[i])  
    	  }
    	 // if(true)return ;
    	  var json = mini.encode(rows);
			$.ajax({
				url : "${pageContext.request.contextPath}/act/user_rule_matrix_dept_user/save_rule_dept_ids",
				type : 'post',data: {data:json},
				success : function(text) {
					grid.reload();
					mini.alert("保存成功");
				},error : function(data) {
			  		mini.alert(data.responseText);
				}
			});
       }
       
       function openSet(viewFlag,userRuleId,userRuleName) { // 点击表头规则流程节点设置
	    	if(viewFlag == 1) {// 0=查看 1=设置
		           mini.open({
		               url: "${pageContext.request.contextPath}/apps/default/admin/act/definition/selector_4_user_rule_jz.jsp",
		               title: "【"+userRuleName+"】启用设置",
		               width: 924,
		               height: 600,
		               ondestroy: function (action) {
		                   if (action == "ok") {
		                       var iframe = this.getIFrameEl();
		                       var data = iframe.contentWindow.GetData();
		                       data = mini.clone(data);
		                       data.userRuleId = userRuleId;
		                       data.userRuleName = userRuleName;
		                       
		                       saveSetting(data);
		                   }
		               }
		           });
		    	} else {
			           mini.open({
			               url: "${pageContext.request.contextPath}/apps/default/admin/act/user_rule/view_setted.jsp?userRuleId="+userRuleId,
			               title: "查看启用设置",
			               width: 924,
			               height: 600,
			               ondestroy: function (action) {
			                   if (action == "ok") {
			                       var iframe = this.getIFrameEl();
			                       var data = iframe.contentWindow.GetData();
			                   }
			               }
			           });
		    	}
       }
       
       function saveSetting(data) {
			$.ajax({
				url : "${pageContext.request.contextPath}/act/user_rule_matrix_flow/save_matrix_flow_setting",
				type : 'post',data: data,
				success : function(text) {
					//grid.reload();
					mini.alert("保存成功");
				},error : function(data) {
			  		mini.alert(data.responseText);
				}
			});
       }
       /*
       function onheadercellclick(e) {
    		//  onheadercellclick="onheadercellclick" 
    	   //  var rowCell = grid.getHeaderCellEl(5);

       }*/
       
        function onButtonEdit(e) {
            var btnEdit = this;
            mini.open({
                url: "${pageContext.request.contextPath}/apps/default/syswin/uum/user/selector_user.jsp",
                title: "选择用户",
                width: 800,
                height: 500,
                ondestroy: function (action) {
                    //if (action == "close") return false;
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var datas = iframe.contentWindow.GetDatas();
                        if(datas.length>0) {
                        	 datas = mini.clone(datas);    //必须
                        	 var userStr = new Array();
                        	 for(var i=0;i<datas.length;i++) {
                        		 if(datas[i].loginNo == '') {
                        			 mini.alert("检测到用户ID为"+datas[i].userId+"的用户，登陆账号为空！！！请联系管理员检查数据！");
                        		 }
                        		 userStr.push(datas[i].userName+"("+datas[i].loginNo+")");
                        	 }
	                         btnEdit.setValue(userStr.join(","));
                        }
                    }
                }
            });
        }
        
        function search() {
        	var keyStr = key.getValue();
        	grid.load({key:keyStr});
        }
        function refresh() {
        	grid.reload();
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
</html>