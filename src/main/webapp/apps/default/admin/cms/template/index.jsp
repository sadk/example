<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>模板管理</title>
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
			
			.mini-messagebox-content {
				padding: 0;
			}
			
			.mini-panel-body {
			    position: relative;
			    text-align: left;
			    width: auto;
			    clear: both;
			    padding: 0;
			    overflow: auto;
			}
		</style>
	</head>
	<body>
		<div class="mini-splitter" style="width:100%;height:100%; overflow:auto;">
		    <div size="240" showCollapseButton="true">
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <span style="padding-left:5px;">租户：</span>
		            <!-- 
		            <input class="mini-textbox" width="120"/>
		            <a class="mini-button" iconCls="icon-search" plain="true">查找</a> -->
		            <input id="tenantCode" name="tenantCode" width="180" class="mini-buttonedit" onbuttonclick="onButtonEdit" />   
		        </div>
		        <div class="mini-fit">
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/channel/tree" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" >        
		            </ul>
		        </div>
		    </div>
			<div showCollapseButton="true">
				<div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
					<div size="50%" showCollapseButton="true">
						<div id="tabs1" contextMenu="#refreshTabMenu"  class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
				  			
				  			<div title="模板定义" >
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
					<div showCollapseButton="true">
						<div id="tabs2" contextMenu="#refreshTabMenu" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
							
							<div title="模板内容" refreshOnClick="true" name="tabUserReses">
								<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
									<table style="width:100%;">
										<tr>
											<td style="width:100%;">
												<a class="mini-button" iconCls="icon-add" onclick="editContent('add')">添加</a>
												<a class="mini-button" iconCls="icon-edit" onclick="editContent('edit')">编辑</a>
												<a class="mini-button" iconCls="icon-remove" onclick="removeContent()">删除</a>
												<span class="separator"></span>  
												<a class="mini-button" iconCls="icon-save" onclick="saveContent()">保存</a>
												<a class="mini-button" iconCls="icon-reload" onclick="reloadContent()">刷新</a>
											</td>
										</tr>
									</table>
								</div>
								<div class="mini-fit">
									<div id="contentGrid" class="mini-datagrid" style="width:100%;height:100%;" sortMode="client"
										url="${pageContext.request.contextPath}/content/page"  idField="id" multiSelect="true" allowResize="false"
										showEmptyText="true" emptyText="查无数据" sizeList="[5,10,20,50]" pageSize="20" allowAlternating="true" 
										 allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true" >
										<div property="columns">
									        <div type="checkcolumn" ></div>
									        <div field="id" width="60" headerAlign="center" align="center">内容ID</div>
									        <div field="title" width="160" headerAlign="center">模板标题</div>
									         <!-- 
									        <div field="title" width="160" headerAlign="center" align="left" allowSort="true">模板标题
												<input property="editor" class="mini-textbox" style="width:100%;"/>
											</div>
									         -->
									        <div field="typeDesc" width="180" headerAlign="center"  align="center">类型</div>
									        
											<div type="comboboxcolumn" field="enable" width="80" headerAlign="center" align="center" allowSort="true">是否启用
												<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" />
											</div>
									        
									        <div field="content" width="180" headerAlign="center">内容</div>
									        <div field="createTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">创建日期</div>
									        <div field="updateTime" width="80" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">更新日期</div>     
											
										</div>
									</div>
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
        var contentGrid = mini.get("contentGrid");
        
        grid.load();
        
        tree.on("nodeselect", function (e) {
        	if(e.node.nodeType == "1") { //1=应用 2=栏目
        		console.log(e.node)
        		grid.load({ appCode: e.node.code });
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
				e.cellHtml = "<a href='javascript:showImg(\""+row.pathThumbnailMiddle+"\")'>" + row.pathThumbnailMiddle +"</a>"; //'<img  src="${pageContext.request.contextPath}'+row.pathThumbnailSmall+'"/>';  
			}
		});
		
		contentGrid.on("drawcell", function(e){
			var record = e.record;
			var field = e.field;
			var value = e.value;
			var row = e.row;
			
			if(typeof(row.content) != 'undefined' && row && field == "content" && row.content){
				//alert(row.content);
				e.cellHtml = jqueryHtmlEncode(row.content);
			}
		});
		
		/*利用jquery库转义html，还是很好用的！！*/
		function jqueryHtmlEncode(value) {
			return $("<div />").text(value).html();
		}
		
		/**利用jquery库转义html*/
		function jqueryHtmlDecode() {
			return $("<div />").html(value).text();
		}

		function showImg(path) {
			var imgPath = '<img width="400px" height="300px" src="${pageContext.request.contextPath}'+path+'"/>';
	        mini.showMessageBox({
	            width: 400,
	            height:350,
	            title: "模板缩略图",
	            buttons: ["ok"],
	            message: "查看",
	            html: imgPath,
	            showModal: false,
	            callback: function (action) {
	               	// alert(action);
	            }
	        });	
		}
		
		grid.on("rowclick", function(e){
			var record = e.record;
			var data ={objectId:record.id};
			contentGrid.load(data);
		});
		
		function reloadContent() {
			contentGrid.reload();
		}
		
		function editContent(action) {
			var row = null; // 内容行
			 
			if(action == 'add') {
				var r = grid.getSelected();
				if(!r) {
					mini.alert("请选择一个模板定义");
					return ;
				}
			}
			
			if(action == 'edit') {
				row = contentGrid.getSelected();
				if(!row){
					mini.alert("请选择一个模板内容");
					return ;
				}
			}
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/admin/cms/content/edit.jsp",
				title : "编辑模板",
				width : 900,
				height : 600,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {};
					
					if(action == 'add') {
						var tmpl = grid.getSelected();
						data.type="303";
						data.title = tmpl.title;
						data.objectId = tmpl.id;
						data.enable = 0;
					}
					
					if(action == 'edit') {
						data.id = row.id;
						data.type= row.type;
						data.objectId = row.objectId;
						data.title = row.title;
						data.enable = row.enable;
						data.content = row.content;
					}
					data.action =action;
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					if(action == 'ok'){
						contentGrid.reload();
						return ;
					}
				}
			});
		}
		
		function saveContent() {
		    var data = contentGrid.getChanges();
            var json = mini.encode(data);
            contentGrid.loading("保存中，请稍后......");
            $.ajax({
                url: "${pageContext.request.contextPath}/content/update_short",
                data: { data: json },
                type: "post",
                success: function (text) {
                	contentGrid.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
		}
		
        function removeContent() {
            var rows = contentGrid.getSelecteds();
            if(rows.length == 0) {
         	   mini.alert("请至少选择一条记录")
         	   return ;
            }
            
            var ids = new Array();
            for(var i=0;i<rows.length;i++) {
         	   ids.push(rows[i].id);
            }
 			$.ajax({
 				url : "${pageContext.request.contextPath}/content/delete?ids=" + ids.join(","),
 				success : function(text) {
 					mini.alert("删除成功");
 					contentGrid.reload();
 				}
 			});
         }
		
        function onButtonEdit(e) {
        	var node = tree.getSelectedNode();
        	console.log(node)
        	if(node) {
        		
        		return ;
        	}
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