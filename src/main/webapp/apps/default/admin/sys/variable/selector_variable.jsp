<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>代码生成</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
		<!-- -->
		<script src="${pageContext.request.contextPath}/scripts/upload/swfupload.js" type="text/javascript"></script>
		
		
		<style type="text/css">
			html, body {
				font-size: 12px;
				padding: 0;
				margin: 0;
				border: 0;
				height: 100%;
				overflow: hidden;
			}
			
			fieldset {
				border: solid 1px #aaa;
			}
			
			.hideFieldset {
				border-left: 0;
				border-right: 0;
				border-bottom: 0;
			}
			
			.hideFieldset .fieldset-body {
				display: none;
			}
</style>
	</head>
	<body> 
		<form id="edit-form1" method="post" style="height:97%; overflow:auto;">
			<input name="id" class="mini-hidden" />
			<div style="padding:4px;padding-bottom:5px;">
				<fieldset style="border:solid 1px #aaa;padding:3px; margin-bottom:5px;" id="fd1">
		            <legend><label><input type="checkbox" checked id="checkbox1" onclick="toggleFieldSet(this, 'fd1')" hideFocus/>常用占位符</label></legend>
		            <div style="padding:3px;" class="fieldset-body">
		            
					    <div id="holdGrid" class="mini-datagrid" style="height:360px;width:100%"  showPager="false"  allowResize="true" 
					        allowCellEdit="true" allowCellSelect="true" multiSelect="true"   editNextOnEnterKey="true"  editNextRowCell="true" >
					        <div property="columns">
					            <div type="checkcolumn"></div>
					            <div field="code" headerAlign="center" allowSort="true" >编码</div>
					            <div field="remark" headerAlign="center" allowSort="true">备注</div>
					            <div field="valueResolveTypeDesc"  headerAlign="center" allowSort="true">值解析类型</div>
					            <div field="useTypeDesc" headerAlign="center" allowSort="true">类型</div>
					        </div>
					    </div>
					    
					    
				    </div>
				</fieldset>
				
				
				
				
				
			</div>
			<div id="subbtn" style="text-align:center;padding:2px;">
				<a class="mini-button" onclick="onOk" id="btnOk" style="width:80px;margin-right:20px;"  iconCls="icon-ok" >确定</a>
				<a class="mini-button" onclick="onCancel" id="btnCancel" style="width:80px;"  iconCls="icon-cancel">取消</a>
			</div>
		</form>
		<script type="text/javascript">
			mini.parse();

			var form = new mini.Form("edit-form1");
			var holdGrid = mini.get("holdGrid");
			var btnOk =  mini.get("btnOk");
			var btnCancel =  mini.get("btnCancel");
			
			
			var url="${pageContext.request.contextPath}/variable/all"+location.search;
			holdGrid.setUrl(url);
			holdGrid.load();
			
			/*
			var map = getParameterMap();
			
			for(var name in map) {
				mini.alert(name+":"+map[name]);
			}
			// 返回"Map"对象
			function getParameterMap() {
				 var query=location.search.substring(1); //获取查询串
				 var arr = query.split("&");
				 
				 var map = {};
				 for (var i=0;i<arr.length;i++) {
					var pair = arr[i];
					var pm = pair.split("=");
					map[pm[0]]=pm[1];
				 }
				 return map;
			}
			*/
			
			
			
			
			
			
			
			function toggleFieldSet(ck, id) {
	            var dom = document.getElementById(id);
	            dom.className = !ck.checked ? "hideFieldset" : "";
	        }
			
			function GetData() {
				var o = holdGrid.getSelecteds();
				return o;
			}

			function CloseWindow(action) {
				if(action == "close" && form.isChanged()) {
					if(confirm("数据被修改了，是否先保存？")) {
						return false;
					}
				}
				if(window.CloseOwnerWindow)
					return window.CloseOwnerWindow(action);
				else
					window.close();
			}

			function onOk(e) {
				CloseWindow("ok");
			}

			function onCancel(e) {
				CloseWindow("cancel");
			}
		</script>
	</body>
</html>
