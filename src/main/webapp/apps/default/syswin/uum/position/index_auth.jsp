<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>岗位数据授权</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
		<style>
			html, body {
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
		<div class="mini-splitter" style="width:100%;height:100%;">
		    <div size="240" showCollapseButton="true">
		    
		    	<!-- 
		        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <span style="padding-left:5px;">分类名：</span>
		            <input class="mini-textbox" width="100"/>
		            <a class="mini-button" iconCls="icon-search" plain="true">查找</a>                  
		        </div>
		         -->
		         
		        <div class="mini-fit">
		            <!-- 
		            <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/syswin/role_category/all" style="width:100%;"
		                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" >        
		            </ul> -->
	                <ul id="tree1" class="mini-tree" url="${pageContext.request.contextPath}/syswin/position/build_checkbox_tree" style="width:100%;padding:5px;" 
				        showTreeIcon="true" textField="text" idField="id" parentField="pid" resultAsTree="false"  
				        showCheckBox="true" checkRecursive="true" allowSelect="true" onnodeselect="onNodeSelect"
				        onbeforenodecheck="onBeforeNodeCheck" allowSelect="false" enableHotTrack="false">
				    </ul>
		        </div>
		    </div>
			<div showCollapseButton="true">
				<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
					<table style="width:100%;">
						<tr>
							<td style="width:100%;">
								<a class="mini-button" iconCls="icon-save" onclick="save()">保存</a>
								<a class="mini-button" iconCls="icon-edit" onclick="referPosition()">引用岗位</a>
								
								<!-- 
								<a class="mini-button" iconCls="icon-node" onclick="edit('view')">查看</a>
								<span class="separator"></span>  
								<a class="mini-button" iconCls="icon-download" onclick="exportData()">导出</a>
								<input id="exportFileType" name="exportFileType" class="mini-combobox" style="width:60px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"excel"},{id:"1",text:"word"},{id:"2",text:"pdf"},{id:"3",text:"txt"}]' />
								<input id="exportDataType" name="exportDataType" class="mini-combobox" style="width:64px" value="0"  showNullItem="false" nullItemText="请选择..." emptyText="请选择" data='[{id:"0",text:"当前页"},{id:"1",text:"选中行"},{id:"2",text:"全部数据"}]' />
								 -->
							</td>
							<!-- 
							<td style="white-space:nowrap;">
		                        <input id="key2" name="key2" class="mini-textbox" emptyText="请输入关键字" style="width:150px;" onenter="search"/>   
		                        <a class="mini-button" onclick="search()">查询</a>
		                    </td>
		                     -->
						</tr>
					</table>
				</div>
				<div class="mini-fit">
					<div id="datagrid1" class="mini-treegrid" style="width:100%;height:100%;"
					showTreeIcon="true" allowResize="true" expandOnLoad="true" multiSelect="false" 
    				treeColumn="name" idField="id" parentField="pid" resultAsTree="false"  checkRecursive="true"  showCheckBox="false" 
					url="${pageContext.request.contextPath}/syswin/org/list"  ondrawcell="ondrawcell" > 
					    <div property="columns">
					    	<!-- 
					        <div type="checkcolumn"></div>
					         -->
					        <div name="name" field="name" width="180" headerAlign="center">名称</div>
					        <div field="typeDesc" width="80" headerAlign="center" align="center">类型</div>
					        <div field="sn" width="30" align="right" headerAlign="center">序号</div>
					        <!-- <div field="appCode" width="60" align="left">所属应用</div> -->
					        <div field="nodePath" width="100" align="left">结点路径</div>
					        <!-- 
					        <div field="nodePathText" width="200" align="left">结点路径导航</div>
					         -->
					         
					        <div name="id" field="id" width="30" >ID</div>
					        <div name="pid" field="pid" width="30" >父ID</div>      
					        
					        <div field="mySelf" width="40" headerAlign="center" align="center">本人</div>
					        <div field="underMe" width="40" align="center" headerAlign="center">直属下级</div>
					        <div field="all" width="40" align="center" headerAlign="center">所有</div>
					        <div field="useness" width="40" align="center" headerAlign="center">使用</div>
					        <!-- 
					        <div name="id" field="id" width="30" >ID</div>
					        <div name="pid" field="pid" width="30" >父ID</div>
					         -->              
					    </div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		mini.parse();
		
		var tree = mini.get("tree1");
		var grid = mini.get("datagrid1");
        
		//grid.load();
		/*
		grid.on("drawcell", function(e){
			var record = e.record;
			var field = e.field;
			var value = e.value;
			var row = e.row;
			if(typeof(row.name) != 'undefined' && row && field == "name" && row.id){
				e.cellHtml = '<a href="javascript:void(0)" onclick="view('+row.id+')">' + row.name + '</a>';
			}
		});
		*/
		
		function onNodeSelect(e){
			var node = e.node;
			var isLeaf = e.isLeaf;
			//mini.alert(isLeaf);	
			//mini.alert(mini.encode(node));
			function itemClear(){
				$("input[eletype='item']").attr("checked",false); 
			}
			
			if(node.type=='position') {
				grid.loading("加载中权限设置中，请稍后......");
				$.ajax({
					url : "${pageContext.request.contextPath}/syswin/position/get_dataquery_and_useness_config?positionId="+node.originalId ,
					dataType: 'json', cache : false, type : 'get',
					//data : data,
					success : function(text) {
						grid.unmask(); // 取消遮罩
						var list = mini.decode(text);
						
						itemClear();
						for(var i=0;i<list.length;i++){
							$("input[name='"+list[i].colType+"']").each(function(){
								var curr = $(this)
								if(curr.attr("orgid") == list[i].orgId ){
									curr.attr("checked",true);
									return false;
								}
							}) 
						}			
					}
				});	
			}
		}
		
		String.prototype.startWith=function(str){     
			  var reg=new RegExp("^"+str);     
			  return reg.test(this);        
		}  

		function referPosition(){ // 引用岗位
			var value = tree.getValue();
			if(value==""){
				mini.alert("请在左侧岗位导航树中勾选一个或多个岗位");
				return ;
			}
			if(!value.startWith("pos_")){
				mini.alert("请在左侧岗位导航树中勾选岗位节点");
				return ;
			}
			
			value = value.replace(/pos_/g,"");
			
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/syswin/uum/position/selector_position_tree.jsp",
				title : "引用岗位",
				width : 500,
				height : 580,
				onload : function() {
					var iframe = this.getIFrameEl();
					//alert(data.pid+" "+data.pName+" "+data.idNotIn);
					//iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					if(action == 'ok') {
						var iframe = this.getIFrameEl();
						var row = iframe.contentWindow.GetData();
						row = mini.clone(row);
						
						var data = {};
						data.targetPositionId = row.originalId;
						data.positionIds = value;
						$.ajax({
							url : "${pageContext.request.contextPath}/syswin/position/refer_dataquery_and_useness_config" ,
							dataType: 'json', cache : false, type : 'post',
							data : data,
							success : function(text) {
								mini.alert("保存成功");		
							}
						});	
					}
				}
			});
		}
		
		// -------------------------------------------- 岗位授数据查询权限重要交互，开始 ------------------------------------------------
		function ondrawcell(e){
			var record = e.record;
			var field = e.field;
			var value = e.value;
			var row = e.row;
			//if(row.type == 1) return ;
			if((typeof(row.name) != 'undefined' && row  && row.id) && (field == "mySelf" || field=="underMe" || field=="all" || field == "useness")){
				if (field == "useness") {
					if(e.row.propType == '1'){
						e.cellHtml = 
							'<label class="function-item">'+
								'<input eletype="item" title="'+row.name+'" id="'+field+"_"+row.id+'" onclick="casecadeSelect(\''+field+"_"+row.id+'\',\''+field+'\','+row.id+','+row.type+')" type="checkbox" fileorgid="'+field+'_'+row.id+'" fileorgpid="'+field+'_'+row.pid+'" orgid="'+row.id+'" orgpid="'+row.pid+'" orgtype="'+row.type+'" name="'+field+'" />' +  
							'</label>';
					}else {
						/*
						e.cellHtml = 
							'<label class="function-item">'+
								'<input disabled allowUse="true" eletype="item" title="'+row.name+'" id="'+field+"_"+row.id+'" onclick="casecadeSelect(\''+field+"_"+row.id+'\',\''+field+'\','+row.id+','+row.type+')" type="checkbox" fileorgid="'+field+'_'+row.id+'" fileorgpid="'+field+'_'+row.pid+'" orgid="'+row.id+'" orgpid="'+row.pid+'" orgtype="'+row.type+'" name="'+field+'" />' +  
							'</label>';
							*/
					}
				} else {
					e.cellHtml = 
						'<label class="function-item">'+
							'<input eletype="item" title="'+row.name+'" id="'+field+"_"+row.id+'" onclick="casecadeSelect(\''+field+"_"+row.id+'\',\''+field+'\','+row.id+','+row.type+')" type="checkbox" fileorgid="'+field+'_'+row.id+'" fileorgpid="'+field+'_'+row.pid+'" orgid="'+row.id+'" orgpid="'+row.pid+'" orgtype="'+row.type+'" name="'+field+'" />' +  
						'</label>';
				}
			}
		}
		
		function casecadeSelect(id,field,orgid,orgtype){
			var checked = $("#"+id).prop("checked");
			
			// 本人
			if(field =='mySelf'){
				if(orgtype==1) { // 用户点击集团
					$("input[name='mySelf']").attr("checked",checked); 
				
				}else if(orgtype==3) { //用户点击分公司
					$("input[fileorgpid='"+field+"_"+orgid+"']").attr("checked",checked);
				}
			}
			
			// 直属下级
			if(field =='underMe'){
				if(orgtype==1) { // 用户点击集团
					$("input[name='mySelf']").attr("checked",checked); 
					$("input[name='underMe']").attr("checked",checked); 
					
				}else if(orgtype==3) { // 用户点击分公司
					$("input[fileorgid='"+"mySelf"+"_"+orgid+"']").attr("checked",checked);
					$("input[fileorgpid='"+"mySelf"+"_"+orgid+"']").attr("checked",checked);
					
					$("input[fileorgpid='"+field+"_"+orgid+"']").attr("checked",checked);
					
				}else if(orgtype==4) { // 用户点击项目
					$("input[fileorgid='"+"mySelf"+"_"+orgid+"']").attr("checked",checked);
				
				}else if(orgtype==5) { // 用户点击（分公司的）部门或（项目下的）部门
					$("input[fileorgid='"+"mySelf"+"_"+orgid+"']").attr("checked",checked);
				}
			}
			
			// 所有
			if(field =='all'){
				if(orgtype==1) { // 用户点击集团
					$("input[name='mySelf']").attr("checked",checked); 
					$("input[name='underMe']").attr("checked",checked); 
					$("input[name='all']").attr("checked",checked); 
				
				}else if(orgtype==3) { //用户点击分公司
					$("input[fileorgid='"+"mySelf"+"_"+orgid+"']").attr("checked",checked);
					$("input[fileorgpid='"+"mySelf"+"_"+orgid+"']").attr("checked",checked);
					
					$("input[fileorgid='"+"underMe"+"_"+orgid+"']").attr("checked",checked);
					$("input[fileorgpid='"+"underMe"+"_"+orgid+"']").attr("checked",checked);
					
					$("input[fileorgpid='"+field+"_"+orgid+"']").attr("checked",checked);
					
				}else if(orgtype==4) {
					$("input[fileorgid='"+"mySelf"+"_"+orgid+"']").attr("checked",checked);
					$("input[fileorgid='"+"underMe"+"_"+orgid+"']").attr("checked",checked);
					$("input[fileorgid='"+field+"_"+orgid+"']").attr("checked",checked);
				}
			}
			
			if(field =='useness'){
				if(orgtype==1) { // 用户点击集团
					$("input[name='useness']").attr("checked",checked);
				
				}else if(orgtype ==3){ //用户点击分公司
					$("input[fileorgpid='"+field+"_"+orgid+"']").attr("checked",checked);
				}
			}
			
		}
		
		function getCheckedData() {
			var list = new Array();
			
			 $.each($("input[eletype='item']:checked"),function(){
				var ele = $(this);
			 
				var item={};
				item.colType=ele.attr("name");
				item.id=ele.attr("orgid");
				item.pid=ele.attr("orgpid");
				item.type=ele.attr("orgtype");
				item.name=ele.attr("title");
				list.push(item);
			 });
			 return list;
		}
		
		function save() {
			var value = tree.getValue();
			if(value==""){
				mini.alert("请在岗位导航树中选择一个岗位!");
				return ;
			}
			
			// 解析岗位Id
			var posIdList = new Array();
			var positionIds = value.split(",");
			for(var i=0;i<positionIds.length;i++) {
				if (wrapIdNull(positionIds[i]) != null) {
					
					var id = wrapPostionId(positionIds[i]);
					if(id!=null) {
						posIdList.push(id);
					}
				}
			}
			
			var itemList = getCheckedData();
			if(itemList.length==0) {
		        mini.confirm("没有选择岗位数据查询权限点，已存在岗位数据查询权限将会被清空，是否确定？", "确定？",
	                function (action) {
	                    if (action == "ok") {
	                    	// 保存数据
	                    	saveAjax(posIdList.join(","),"",function(){mini.alert("保存成功")});
	                    	return ;
	                    } 
	                }
		        );
			} else {
				saveAjax(posIdList.join(","),mini.encode(itemList),function(text){
					mini.alert("保存成功");
				});
			}
		}
		
		function saveAjax(positionIds,itemsJson,func) {
			var data = {};
			data.positionIds=positionIds;
			data.itemsJson = itemsJson;
		
			$.ajax({
				url : "${pageContext.request.contextPath}/syswin/position/save_position_dataquery_useness_permit" ,
				dataType: 'json', cache : false, type : 'post',
				data : data,
				success : function(text) {
					//var o = mini.decode(text);
					if(typeof(func) !='undefined') {
						func(text);
					}
				}
			});	
		}
		
		function wrapPostionId(text){
			var t = text.split("_");
			if(t.length==2) {
				return t[1];
			}
			return null;
		}
		// -------------------------------------------- 岗位授数据查询权限重要交互，结束 ------------------------------------------------
		function wrapIdNull(id){
			if(typeof(id)=='undefined'){
				return null;
			}
			if(id==null){
				return null;
			}
			if(id==''){
				return null;
			}
			return id;
		}
		
        function onBeforeNodeCheck(e) {
            var tree = e.sender;
            var node = e.node;
            if (tree.hasChildren(node)) {
                //e.cancel = true; 设置为false， checkbox将选不中，特殊场景下使用
            }
        }
   
		

		</script>
	</body>
</html>