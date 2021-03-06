<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8" />
	<title>用户文件上传</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
	<%-- <script src="${pageContext.request.contextPath}/scripts/upload/swfupload.js" type="text/javascript"></script> --%>
	<style type="text/css">
    	body{ margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;}
	</style>
</head>
<body>
		<div class="mini-splitter" vertical="true" style="width:100%;height:330px;">
		    <div size="30%" showCollapseButton="true">
			   <div class="mini-fit">
					<div style="padding-left:5px;padding-bottom:5px;">
					
							    <form id="edit-form1" action="${pageContext.request.contextPath}/chk/user_crime/upload" method="post" enctype="multipart/form-data">
							    	<table>
						         	<tr>
						                <td>导入记要:</td>
						            	<td colspan="3"> 
						            		<input name="remark" id="remark" class="mini-textbox" width="320px" emptyText="请输入导入记要" required="true" value="${remark }"/>
						            	</td>
						            </tr>    
						         	<tr>
						            	<td>文件上传:</td>
						            	<td colspan="3">
						            		<input class="mini-htmlfile" name="dataFile" limitType="*.xlsx" style="width:224px;" />
											<a class="mini-button" onclick="startUpload()">上传</a>
											<a class="mini-button" onclick="viewData()">预览</a>
						            	</td>
						            </tr>
						       		<tr>
						            	<td>文件路径:</td>
						            	<td colspan="3">
						            		<input class="mini-textbox" width="270px" readonly="readonly" name="serverPath" id="serverPath" value="${serverPath}"/>
						            		<a class="mini-button" onclick="clearFileImport">清空</a>
						            		
						            	</td>
						            </tr>
						            </table>
							    </form>
					</div>
				</div>
		    </div>
		    <div showCollapseButton="true">
		        <div class="mini-fit" >
					<div id="grid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" multiSelect="true"  showPager="false" allowAlternating="true" showEmptyText="true" emptyText="暂无查询信息">
						<div property="columns">
							
						</div>
					</div>
		        </div>
		    </div>
		</div>
		    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
			   	<a class="mini-button" onclick="onOk" style="width:80px;margin-right:20px;">执行导入</a>
				<a class="mini-button" onclick="onCancel" style="width:80px;">取消</a>
			</div>
	<script type="text/javascript">
	    mini.parse();
	    var form = new mini.Form("edit-form1");
	   	var grid = mini.get("grid");
	   	var serverPath = mini.get("serverPath");
	   	var remark = mini.get("remark");
	   	
		var definitionCode = "report_user_crime"; // 犯罪记录报表配置编码
		
		function viewData() {
			var data = {};
			data.definitionCode = definitionCode; 
			data.serverPath = mini.get("serverPath").value;
			if(data.serverPath == null || data.serverPath== '') {
				mini.alert("请上传Excel数据文件");
				return ;
			}			
			
			loading();
			
			grid.clearRows();
			
			$.ajax({
				url : "${pageContext.request.contextPath}/chk/user_crime/view_data",
				dataType: 'json', type : 'post',
				data: data,
				success : function(data) {
					loadingClose();
					
					if (data && data.data.length>0) {
						var  columns = [];
						columns.push( { type: "indexcolumn",width: 40 }); //添加行号
						
						if(data.hook && data.hook.length>0) {
							for (var i=0;i<data.hook.length; i++) {
								columns.push({ field: data.hook[i].columnConfig.propertyName, header: data.hook[i].columnConfig.name,width: 120, headerAlign: "center", allowSort: true })
							}
						}
					   
						grid.set({columns: columns,allowSortColumn:false});
						grid.setData(data.data);
						
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
				}
			});
		}
		
 
		
		function clearFileImport() {
			//form.reset();
			serverPath.setValue("");
			remark.setValue("");
			grid.clearRows();
		}
		
	    function startUpload(id) {
			var data = form.getData();
			form.validate();
			if(form.isValid() == false) return;
			
	        $("#edit-form1").submit();
	    }
	    
	    function onUploadSuccessImport(e) {
	    	//console.log(e.serverData);
	    	//mini.alert(typeof e.serverData);
	        mini.alert("上传成功!");
	        mini.get("serverPath").setValue(mini.decode(e.serverData));
	    }
	    
	    function onUploadError(e) {
	    	mini.alert("上传失败：" + e.serverData);
	    }

	    
		function loading(){
			mini.mask({
                el: document.body,
                cls: 'mini-mask-loading',
                html: '加载中...'
            });
		}
		
		function loadingClose(){
			 mini.unmask(document.body);
		}
	    

		function SaveData() {
			var data = form.getData();
			form.validate();
			if(form.isValid() == false) return;
			
			if(grid.getData() == null || grid.getData().length == 0) {
				mini.alert("请选预览数据,再执行导入");
				return ;
			}
			
			data.definitionCode = definitionCode;
			
			$.ajax({
				url : "${pageContext.request.contextPath}/chk/user_crime/execute_import",
				dataType: 'json', type : 'post',
				data: data,
				success : function(text) {
					CloseWindow("ok");
				},
				error: function (jqXHR, textStatus, errorThrown) {
	                mini.showTips({
	                    content: jqXHR.responseText,
	                    state: 'danger',  x: "right",  y: "bottom",
	                    timeout: 10000
	                });
	            }
			});
			
		}

		////////////////////
		//标准方法接口定义
		function SetData(data) {
			data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
			definitionId = data.definitionId;
			//console.log(data)
			 if(data.action == "importData") {
				 
			}
		}
 
		function GetData() {
			var o = form.getData();
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
			SaveData();
		}

		function onCancel(e) {
			CloseWindow("cancel");
		}
	
	</script>
</body>
</html>
