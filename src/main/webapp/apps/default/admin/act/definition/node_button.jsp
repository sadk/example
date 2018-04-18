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


<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:530px;">
    <div title="操作按钮" >
	    <div class="mini-toolbar">
	          <a class="mini-button" iconCls="icon-node" plain="false" onclick="initButton()">初使化所有按钮</a>
	          <span class="separator"></span>
	          <a class="mini-button" iconCls="icon-add" plain="false" onclick="edit('add')">新增</a>
	          <a class="mini-button" iconCls="icon-edit" plain="false" onclick="edit('edit')">编辑</a>
	          <a class="mini-button" iconCls="icon-remove" plain="false" onclick="remove()">删除</a>
	          <a class="mini-button" iconCls="icon-save" plain="false" onclick="save()">保存</a>
	          
	          <span class="separator"></span>
	          <a class="mini-button" iconCls="icon-reload" onclick="ajaxLoad()">刷新</a>
	    </div>
	    <div class="mini-fit">
			<div id="datagrid1"  class="mini-datagrid" style="width:100%;height:100%;" sortMode="client" idField="id" 
				multiSelect="true" showEmptyText="true" emptyText="查无数据" showPager="false" allowResize="false"  
				allowAlternating="true" allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true"  editNextRowCell="true"
				> 
			    <div property="columns">
			        <!-- <div type="indexcolumn"></div> -->
			        <div type="checkcolumn" ></div>
			        <div field="taskName" width="100" align="left"  headerAlign="center">任务名称</div>
			        <div field="btnName" width="100" align="left"  headerAlign="center">按钮名称
			        	<input property="editor" class="mini-textbox" style="width:100%;"/>
			        </div>
			        <div field="dataTypeDesc" width="70" align="left"  headerAlign="center">按钮范围</div>
			        <!-- 
			        <div field="beforeScript" width="160" align="left"  headerAlign="center">前置脚本
			        	<input property="editor" class="mini-textarea" style="width:100%;"/>
			        </div>
			        <div field="afterScript" width="160" align="left"  headerAlign="center">后置脚本
			        	<input property="editor" class="mini-textarea" style="width:100%;"/>
			        </div>
			        
			         
					<div type="comboboxcolumn" field="btnType" width="80" headerAlign="center" allowSort="true" align="left">映射类型
						<input property="editor" class="mini-combobox" showNullItem="true" nullItemText="请选择..." emptyText="请选择" textField="name" valueField="code" url="${pageContext.request.contextPath}/dictionary/option?code=form_node_button_type" />
					</div>
					 -->
					<div field="remark" width="250" align="left"  headerAlign="center">备注
						<input property="editor" class="mini-textbox" style="width:100%;"/>
					</div>
			        <div field="btnCode" width="160" align="left"  headerAlign="center">按钮编码
			        	<input property="editor" class="mini-textbox" style="width:100%;"/>
			        </div>
			       
			    </div>
			</div>
	    </div>  
    </div>
    <div title="前置脚本"  >
	    <div class="mini-toolbar">
	         <a class="mini-button" iconCls="icon-save" plain="false"  onclick="save()">保存【<span id="beforeBtn" style="font-weight: bold;color: red;"></span>】按钮前置脚本</a>
	    </div>
	    <div class="mini-fit">
       		<input id="beforeScript" name="beforeScript" class="mini-textarea" style="width:100%;height:275px;"/>
        	<div style="width: 100%;border: 0;margin: 0;padding: 0;color: green">
       			内置变量说明:
	    		 <ul style="margin: 2px;">
	    			<li>loginUser=登陆用户（常用属性:userId、loginNo、userName等）</li>
	    			<li>processInstanceId=流程实例ID</li>
	          		<li>processDefinitionId=流程定义ID</li>
	          		<li>businessKey=业务主键</li>
	          		<li>taskId=当前任务ID</li>
	          		<li>action=审批按钮编码（agree=同意、reject_to_starter=不同意（退回到发起人)、disagree_continue_go（最后一个节点流程继续往下走） 、any_reback=撤回、abort=作废、add_assign=加签、forword_read=转发、copy_send=抄送）</li>
	          	 </ul>
	    	</div>
       	</div>
    </div>
    <div title="后置脚本">
	    <div class="mini-toolbar">
	         <a class="mini-button" iconCls="icon-save" plain="false" onclick="save()">保存【<span id="afterBtn" style="font-weight: bold;color: red;"></span>】按钮后置脚本</a>     
	    </div>
	    <div class="mini-fit">
        	<input id="afterScript" name="afterScript" class="mini-textarea" style="width:100%;height:275px;"/>
        	<div style="width: 100%;border: 0;margin: 0;padding: 0;color: green">
       			内置变量说明:
	    		 <ul style="margin: 2px;">
	    			<li>loginUser=登陆用户（常用属性:userId、loginNo、userName等）</li>
	    			<li>processInstanceId=流程实例ID</li>
	          		<li>processDefinitionId=流程定义ID</li>
	          		<li>businessKey=业务主键</li>
	          		<li>taskId=当前任务ID</li>
	          		<li>action=审批按钮编码（agree=同意、reject_to_starter=不同意（退回到发起人)、disagree_continue_go（最后一个节点流程继续往下走） 、any_reback=撤回、abort=作废、add_assign=加签、forword_read=转发、copy_send=抄送）</li>
	          	 </ul>
	    	</div>
        </div>
    </div>
</div>



         
    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderStyle="border:0;">
       <!--  <a class="mini-button" style="width:60px;" onclick="onOk()">确定</a> -->
       <!--  <span style="display:inline-block;width:25px;"></span> -->
        <a class="mini-button" style="width:60px;" onclick="onCancel()">关闭</a>
    </div>
    
	<script type="text/javascript">
	    mini.parse();
	    
	    var definitionId = "";
	    var taskKey = "";
	    var taskName = "";
	    var dataType = "";
	    
	    var grid = mini.get("datagrid1");
	    var afterScriptCtl = mini.get("afterScript");
	    var beforeScriptCtl = mini.get("beforeScript");

		grid.on("rowclick", function(e){
			var record = e.record;
			$("#beforeBtn").html(record.btnName);
			$("#afterBtn").html(record.btnName);
			if(record.id==null ){return ;}
            $.ajax({
                url: "${pageContext.request.contextPath}/act/node_button/get_by_id?id="+record.id ,
                type: "post",
                success: function (data) {
                	if(data) {
                		afterScriptCtl.setValue(data.afterScript);
                		beforeScriptCtl.setValue(data.beforeScript);
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            }); 
		});
		
		function save() {
		    var data = grid.getChanges();
            var json = mini.encode(data);
            $.ajax({
                url: "${pageContext.request.contextPath}/act/node_button/save_or_update_json",
                data: { data: json },
                type: "post",
                success: function (text) {
                	ajaxLoad();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
		}
		
		function edit(action) {
			var row = grid.getSelected();
			if(action == 'edit'){
				if(!row){
					mini.alert("请选择一条记录");
					return ;
				}
			}
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/admin/act/node_button/edit.jsp",
				title : "设置节点按钮",
				width : 510,
				height : 590,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {definitionId : definitionId, taskKey :taskKey,taskName: taskName, action : action}
					if(action == 'edit') {
						data.id=row.id;
					}
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					ajaxLoad();
				}
			});
		}
		
		function save(){
			var row = grid.getSelected();
			if(!row) {
				mini.alert("请选择一个按钮");
				return ;
			}
			var data = {};
			data.id = row.id;
			data.btnName=row.btnName;
			data.btnCode = row.btnCode;
			data.remark = row.remark;
			
			data.beforeScript = beforeScriptCtl.value;
			data.afterScript = afterScriptCtl.value;
			 $.ajax({
	                url: "${pageContext.request.contextPath}/act/node_button/update_simple",
	                type: "post", dataType: 'json',
	                data : data,
	                success: function (data) {
	                	mini.alert("保存成功");
	                	
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
	     	                url: "${pageContext.request.contextPath}/act/node_button/delete_by_ids?ids="+ids.join(",") ,
	     	                type: "post",
	     	                success: function () {
	     	                	
	     	                	var url = "${pageContext.request.contextPath}/act/node_button/list";
	     	                	var data = {}
	     	                	data.definitionId = definitionId;
	     	        	    	data.taskKey = taskKey;
	     	        	    	data.taskName = taskName;
	     	        	    	grid.setUrl(url);
	     	                	grid.load(data);
	     	                	
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
	    
	   	function initButton() {
	   		var data = {};
	    	data.definitionId = definitionId;
	    	data.taskKey = taskKey;
	    	data.taskName = taskName;
	    	data.dataType = dataType;
	    	
	   		var url = "${pageContext.request.contextPath}/act/node_button/init";
	        mini.confirm("确定重新初使化按钮？", "确定？",
	                function (action) {
	                    if (action == "ok") {
	                        $.ajax({
	                            url: url,
	                            type: "post",
	                            data : data,
	                            success: function (data) {
	                            	if(data) {
	                            		data = mini.decode(data);
	            	                	grid.setData(data);
	            	                	grid.unmask(); // 取消遮罩
	            	                	mini.alert("初使化成功");
	                            	}
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
	    	data.taskKey = taskKey;
	    	data.taskName = taskName;
	    	
	    	grid.loading("加载中，请稍后......");
	    	var url = "${pageContext.request.contextPath}/act/node_button/list";
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
			console.log(data);
			if(data) {
				definitionId = data.definitionId;
				taskKey = data.taskKey;
				taskName = data.taskName;
				dataType = data.dataType;
				
				ajaxLoad();
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
