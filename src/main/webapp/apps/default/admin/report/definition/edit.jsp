<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>报表</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>

		<style type="text/css">
			html, body {
				font-size: 12px;
				padding: 0;
				margin: 0;
				border: 0;
				height: 100%;
				overflow: hidden;
			}
			
		</style>
		
	<style type="text/css">
    .asLabel .mini-textbox-border,
    .asLabel .mini-textbox-input,
    .asLabel .mini-buttonedit-border,
    .asLabel .mini-buttonedit-input,
    .asLabel .mini-textboxlist-border
    {
        border:0;background:none;cursor:default;
    }
    .asLabel .mini-buttonedit-button,
    .asLabel .mini-textboxlist-close
    {
        display:none;
    }
    .asLabel .mini-textboxlist-item
    {
        padding-right:8px;
    }    
    </style>
	</head>
	<body> 
		<form id="edit-form1" method="post" style="height:97%; overflow:auto;">
			<input id="id" name="id" class="mini-hidden" />
			<input id="version" name="version" class="mini-hidden" />
			<div style="padding-left:4px;padding-bottom:2px;">
				<fieldset style="border:solid 1px #aaa;padding:2px; margin-bottom:2px;">
		            <legend>报表基本定义</legend>
		            <div style="padding:2px;">
				        <table>
				        
									<tr>
										<td>报表名称：</td>
										<td>
											<input id="name" name="name"  class="mini-textbox"  emptyText="请输入名称" required="true" />
										</td>
										<td>简称：</td>
										<td>	
											<input id="shortName" name="shortName"  class="mini-textbox"  emptyText="请输入简称"  />
										</td>
									</tr>
									
									
									<tr>
										<td>编码：</td>
										<td>
											<input id="code" name="code"  class="mini-textbox"  emptyText="请输入编码" required="true" />
										</td>
										<td>分类：</td>
										<td>
											<input id="categoryId" name="categoryId" class="mini-buttonedit" onbuttonclick="onCategoryButtonEdit" emptyText="请选择报表分类" required="true"/>   
											<input id="categoryName" name="categoryName" class="mini-hidden" />   
										</td>
									</tr>
									
									
									 <tr>
										<td>数据源：</td>
										<td>
											<input id="datasourceId" name="datasourceId" class="mini-buttonedit" onbuttonclick="onDatasourceButtonEdit" emptyText="数据源" required="true"/>   
											<input id="datasourceName" name="datasourceName" class="mini-hidden" />   
										</td>
										<td>数据生成类型：</td>
										<td>
											<input id="type" name="type"  class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rpt_general_type" required="true"/>
										</td>
									</tr>
									
									
									<tr>
										<td>报表地址：</td>
										<td>
											<input id="url" name="url" enabled="false" class="mini-textbox"  emptyText="请输入链接"  />
										</td>
										<td>数据库类型：</td>
										<td>
											<input id="dbType" name="dbType" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=rpt_db_type" required="true"/>
										</td>
									</tr>
									
 
									<tr>
										<td>报表启用 ：</td>
										<td>
											<input id="status" name="status" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" required="true"/>
										</td>
										<td>备注：</td>
										<td>
											<input id="remark" name="remark"  class="mini-textbox" emptyText="请输入备注"  />
										</td>
										<!-- 
										<td>防SQL注入启用 ：</td>
										<td>
											<input id="preventSqlInjection" name="preventSqlInjection" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=enable_status" required="true"/>
										</td>
										 -->
									</tr>
									
									<tr>
										<td>序号:</td>
										<td>
											<input name="sn" id="sn" class="mini-spinner" value="0" minValue="0" maxValue="999999999"  />
										</td>
									</tr>
						
				        </table>
				    </div>
				</fieldset>
				
				<fieldset style="border:solid 1px #aaa;padding:2px; margin-bottom:2px;">
		            <legend>报表外观定义</legend>
		            <div style="padding:2px;">
				        <table>
							<tr>
								<td>报表布局：</td>
								<td>
									<input id="layout" name="layout" value="1" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=report_file_layout" required="true"/>
								</td>
								<td>是否可以导出 ：</td>
								<td>
									<input id="canExport" name="canExport" value="0" class="mini-combobox"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=yes_or_no" required="true"/>
								</td>
							</tr>
							<tr>
								<td>查询区宽度：</td>
								<td>
									<input id="searchAreaWidth" name="searchAreaWidth" class="mini-spinner"  minValue="250" maxValue="6" />
								</td>
								<td>查询区行控件数 ：</td>
								<td>
									 <input id="controlNumPerRow" name="controlNumPerRow" class="mini-spinner"  minValue="1" maxValue="6" />
								</td>
							</tr>
							<tr>
								<td>是否分页 ：</td>
								<td>
									<input id="showPager" name="showPager" value="1" class="mini-combobox"  showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=yes_or_no" required="true"/>
								</td>
								<td>分页大小：</td>
								<td>
									<input id="pageSize" name="pageSize" class="mini-spinner" value="20" minValue="0" allowNull="true" value="null" />
								</td>
							</tr>
							<tr>
								
								<td>分页大小项：</td>
								<td>
									<input id="pageSizeList" name="pageSizeList" class="mini-textbox" value="20,50,100,500" emptyText="逗号分割,如:20,50,100"  />
								</td>
								<td>报表排序模式：</td>
								<td>
									<input id="sortMode" name="sortMode" class="mini-combobox" value="1" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="value" url="${pageContext.request.contextPath}/dictionary/option?code=report_sort_mode" required="true"/>
								</td>
							</tr>
							<tr id="reportButtonTR">
								
								<td>报表按钮：</td>
								<td>
									<input id="resourceIds" name="resourceIds" class="mini-buttonedit" onbuttonclick="onResourceButtonEdit" emptyText="添加报表按钮" />  
								</td>
								
							</tr>
				        </table>
		            </div>
		        </fieldset>
		            
				<fieldset style="border:solid 1px #aaa;padding:2px; margin-bottom:2px;height: 130px">
		            <legend>报表列SQL<font color="red">(用于导入字段使用，可以直接执行)</font></legend>
		            <div style="padding:5px;">
		            	<input id="columnSql" name="columnSql" class="mini-textarea" style="height: 100px;width:100%" required="true"/>
		            </div>
		        </fieldset>
		        
				<fieldset style="border:solid 1px #aaa;padding:2px; margin-bottom:2px;height: 130px">
		            <legend>报表真实SQL</legend>
		            <div style="padding:5px;">
		            	<input id="reportSql" name="reportSql" class="mini-textarea" style="height: 100px;width:100%" required="true"/>
		            </div>
		        </fieldset>
		        
			</div>
			<div id="subbtn" style="text-align:center;padding:10px;">
				<a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>
			</div>
		</form>
		<script type="text/javascript">
			mini.parse();

			var form = new mini.Form("edit-form1");
			var id = mini.get("id");
			var categoryId = mini.get("categoryId");
			var datasourceId = mini.get("datasourceId");
			
			var categoryName = mini.get("categoryName");
			var datasourceName = mini.get("datasourceName");
			
			function onResourceButtonEdit(e) {
				
	            mini.open({
	                url: "${pageContext.request.contextPath}/apps/default/admin/report/resource/index.jsp",
	                title: "【"+mini.get("name").value+"】报表按钮配置",
	                width: 800,
	                height: 600,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : "edit",
							definitionId : id.value
						};
						iframe.contentWindow.SetData(data);
					},
	                ondestroy: function (action) {
	                    //if (action == "close") return false;
	                    if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var data = iframe.contentWindow.GetData();
	                        data = mini.clone(data);    //必须
	                        if (data) {
	                          //  btnEdit.setValue(data.id);
	                          //  btnEdit.setText(data.name);
	                        }
	                    }
	                }
	            });
			}
			
			function onCategoryButtonEdit(e) {
				var btnEdit = this;
				 
	            mini.open({
	                url: "${pageContext.request.contextPath}/apps/default/admin/report/category/seletor_category.jsp",
	                title: "选择列表",
	                width: 650,
	                height: 380,
	                ondestroy: function (action) {
	                    //if (action == "close") return false;
	                    if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var data = iframe.contentWindow.GetData();
	                        data = mini.clone(data);    //必须
	                        if (data) {
	                            btnEdit.setValue(data.id);
	                            btnEdit.setText(data.name);
	                            
	                            categoryName.setValue(data.name);
	                        }
	                    }
	                }
	            });
			}
			
			function onDatasourceButtonEdit(e) {
				var btnEdit = this;
	            mini.open({
	                url: "${pageContext.request.contextPath}/apps/default/admin/sys/datasource/seletor_datasource.jsp",
	                title: "选择列表",
	                width: 650,
	                height: 380,
	                ondestroy: function (action) {
	                    //if (action == "close") return false;
	                    if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var data = iframe.contentWindow.GetData();
	                        data = mini.clone(data);    //必须
	                        if (data) {
	                            btnEdit.setValue(data.id);
	                            btnEdit.setText(data.name);
	                            
	                            datasourceName.setValue(data.name);
	                        }
	                    }
	                }
	            });
			}
			
			function SaveData() {
				var o = form.getData();
				form.validate();
				if(form.isValid() == false) return;

				$.ajax({
					url : "${pageContext.request.contextPath}/report/definition/save_or_update",
					dataType: 'json',
					type : 'post',
					cache : false,
					data: o,
					success : function(text) {
						CloseWindow("save");
					}
				});
			}

			////////////////////
			//标准方法接口定义
			function SetData(data) {
				data = mini.clone(data); //跨页面传递的数据对象，克隆后才可以安全使用
				if(data.action == 'add') {
					$("#reportButtonTR").hide();
					return ;
				}
				
				 if(data.action == "edit" || data.action=='view') {
					$.ajax({
						url : "${pageContext.request.contextPath}/report/definition/get_by_id?id=" + data.id,
						dataType: 'json',
						cache : false,
						success : function(text) {
							var o = mini.decode(text);
							if(o!=null && o.data!=null && o.data.length>0) {
								o = o.data;
							}
							form.setData(o);
							form.setChanged(false);
							
							datasourceId.setText(o.datasourceName);
							categoryId.setText(o.categoryName);
							
							if (data.action == 'view') {
								//form.setEnabled(false);
							}
							
							if(data.action == 'view') {
								var fields = form.getFields();                
					            for (var i = 0, l = fields.length; i < l; i++) {
					                var c = fields[i];
					                if (c.setReadOnly) c.setReadOnly(true);     //只读
					                if (c.setIsValid) c.setIsValid(true);      //去除错误提示
					                if (c.addCls) c.addCls("asLabel");          //增加asLabel外观
					            }
							}
						}
					});
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
