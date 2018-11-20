<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>后台管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
   	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
   	<!-- 
   	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/core.js" ></script> -->
    <style type="text/css">
    html, body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }

    .logo
    {
        font-family:"微软雅黑",	"Helvetica Neue",​Helvetica,​Arial,​sans-serif;
        font-size:28px;
        font-weight:bold;        
        cursor:default;
        position:absolute;top:25px;left:14px;        
        line-height:28px;
        color:#444;
    }    
    .topNav
    {
        position:absolute;right:8px;top:12px;        
        font-size:12px;
        line-height:25px;
    }
    .topNav a
    {
        text-decoration:none;        
        font-weight:normal;
        font-size:12px;
        line-height:25px;
        margin-left:3px;
        margin-right:3px;
        color:#333;        
    }
    .topNav a:hover
    {
        text-decoration:underline;        
    }   
     .mini-layout-region-south img
    {
        vertical-align:top;
    }
    </style>
</head>
<body>
    
<!--Layout-->
<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
    <div class="header app-header" region="north"  height="70" showSplit="false" showHeader="false">
          <img src="${pageContext.request.contextPath}/logo.png" style="margin-top: 15px;margin-left: 10px;"/> 
          <!-- <div style="margin-left:5px;line-height:15px;text-align:left;cursor:default;color: green">晴天集团 Copyright © 版权所有 </div> -->
        <div class="topNav">    
            <a href="#">首页</a> |
            <a href="#">在线示例</a> |
            <a href="#">Api手册</a> |            
            <a href="#">开发教程</a> |
            <a href="#">快速入门</a> |
            <a href="#" id="loginNameWelcome">欢迎，登陆</a> |     
            <a href="javascript:updatePwd()">修改密码</a> |    
            <a href="javascript:logout()" style="color:Red;font-family:Tahoma;font-weight: bold;" >退出</a>
        </div>

        <div style="position:absolute;right:12px;bottom:5px;font-size:12px;line-height:25px;font-weight:normal;">
            <span style="color:Red;font-family:Tahoma">（推荐Blue）</span>选择皮肤：
            <select id="selectSkin" onchange="onSkinChange(this.value)" style="width:100px;" >
             	<option value="default">Default</option>
             	<option value="blue">Blue</option>
             	<option value="blue2003">Blue2003</option>
           	 	<option value="blue2010">Blue2010</option>
                <option value="bootstrap">Bootstrap</option>
				<option value="gray">Gray</option>
            </select>
             尺寸：
            <select id="selectMode" onchange="onModeChange(this.value)" style="width:100px;" >
                <option value="">Default</option>
                <option value="medium" >Medium</option>
                <option value="large">Large</option>                
            </select>
        </div>
    </div>
    
    
    
    <!-- 
    <div title="south" region="south" showSplit="false" showHeader="false" height="30" >
        <div style="line-height:28px;text-align:center;cursor:default">Copyright © 版权所有 </div>
    </div> -->
    <div title="center" region="center" style="border:0;" bodyStyle="overflow:hidden;">
        <!--Splitter-->
        <div class="mini-splitter" style="width:100%;height:100%;" borderStyle="border:0;">
            <div size="180" maxSize="250" minSize="100" showCollapseButton="true" style="border:0;">
                <!--OutlookTree-->
                <div id="leftTree" class="mini-outlooktree" url="${pageContext.request.contextPath}/user/get_permission_list?status=1&type=100&sortField=sn&sortOrder=asc" onnodeclick="onNodeSelect"
                    textField="name" idField="id" parentField="pid">
                </div>
                
            </div>
			<div showCollapseButton="false" style="border:0;">
				<!--Tabs-->
				<div id="mainTabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;"
				contextMenu="#tabsMenu"
				plain="false" onactivechanged="onTabsActiveChanged"
				>
					<div name="first" title="首页" url=""></div>
				</div>
				<ul id="tabsMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
					<li onclick="reloadTab">
						刷新当前
					</li>
					<li onclick="closeTab">
						关闭当前
					</li>
					<li onclick="closeAll">
						关闭所有
					</li>
					<li onclick="closeAllButFirst">
						关闭其他
					</li>
				</ul>
			</div>       
        </div>
    </div>
</div>

    

    <script type="text/javascript">
        mini.parse();
	
        var tree = mini.get("leftTree");
        var tabs = mini.get("mainTabs");
        
        var codeGenNode = null; // "代码生成器"节点
        tree.on("drawnode",function(sender){ // 绘制结点时发生
        	/* if("sys-5" == sender.node.id) {
        		codeGenNode = sender.node;
        	} */
        })

        function getLoginUser() {
			$.ajax({
				url : "${pageContext.request.contextPath}/user/get_login_user",
				dataType: 'json',
				success : function(text) {
					var o = mini.decode(text);
					if(o) {
						$("#loginNameWelcome").html("欢迎，<b>"+o.name+"</b>登陆");
					}
				}
			});
        };
        setTimeout(getLoginUser,500);
        
        function updatePwd() {
			mini.open({
				url : "${pageContext.request.contextPath}/apps/default/admin/uum/user/edit_password.jsp",
				title : "修改密码",
				width : 500,
				height : 200,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {
						action : "updatePwd"
					};
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					if("update" == action) {
						mini.alert("密码修改成功，请退出重新登陆");
					}
				}
			});
        }
        
        function logout() {
        	var messageId = mini.loading("正在退出，请稍后 ...", "Loading");
            setTimeout(function () {
            	
                $.ajax({
                    url: "${pageContext.request.contextPath}/user/logout",
                   // data:  form.getData(),
                    type: "post",
                    success: function (text) {
                    	mini.hideMessageBox(messageId);
                    	if(text) {
                    		//console.log(text);
                    		if(text.isSuccess == true) {
                    			window.location = "${pageContext.request.contextPath}/login.jsp";
                    		}else {
                    			mini.alert("退出失败!");
                    		}
                    	}
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        mini.alert(jqXHR.responseText);
                    }
                });
               
            }, 1000);
        }
        
        function expandNode() {
        	/*
        	if(codeGenNode.expanded == false){
        		tree.expandNode(codeGenNode);
        	} else {
        		 window.clearInterval(exp)
        	}*/
        }
        var exp = setInterval(expandNode,500);
       
        
        function showTab(node) {
            var id = "tab$" + node.id;
            var tab = tabs.getTab(id);
            if (!tab) {
                tab = {};
                tab._nodeid = node.id;
                tab.name = id;
                tab.title = node.text;
                tab.showCloseButton = true;

                //这里拼接了url，实际项目，应该从后台直接获得完整的url地址
                tab.url ="${pageContext.request.contextPath}"+ node.url; //mini_JSPath + "../../docs/api/" + node.id + ".html";

                tabs.addTab(tab);
            }
            tabs.activeTab(tab);
        }
        
      	//设置皮肤
	    window.onload = function () {
	        var skin = mini.Cookie.get("miniuiSkin");
	        var selectSkin = document.getElementById("selectSkin");
	     
	        for(var i=0;i<selectSkin.length;i++){
	      	  if(selectSkin[i].value == skin) { 
	      		  selectSkin[i].selected = true;
	      	  }
	      	}
	    }
      	
      	
        function onSkinChange(skin){
        	 mini.Cookie.set('miniuiSkin', skin);
        	 window.location.reload();
        }
        
        function onModeChange(skin) {
            mini.Cookie.set('miniuiMode', skin);
            //mini.Cookie.set('miniuiMode', skin, 100);//100天过期
            window.location.reload()
        }
        
        
        function onNodeSelect(e) {
            var node = e.node;
            var isLeaf = e.isLeaf;

            if (isLeaf) {
                showTab(node);
            }
        }

        function onClick(e) {
            var text = this.getText();
            alert(text);
        }
        function onQuickClick(e) {
            tree.expandPath("datagrid");
            tree.selectNode("datagrid");
        }

        function onTabsActiveChanged(e) {
			var tabs = e.sender;
			var tab = tabs.getActiveTab();
			if(tab && tab._nodeid) {
				var node = tree.getNode(tab._nodeid);
				if(node && !tree.isSelectedNode(node)) {
					tree.selectNode(node);
					// var id = "tab$" + node.id;
					// var tab = tabs.getTab(id);
					// tabs.reloadTab(tab);
				}
			}
		}
        function reloadTab() {
			tabs.reloadTab(currentTab);
		}

		function closeTab() {
			tabs.removeTab(currentTab);
		}
		function closeAll() {
			tabs.removeAll();
		}
		function closeAllButFirst() {
			var but = [currentTab];
			but.push(tabs.getTab("first"));
			tabs.removeAll(but);
		}
        function onBeforeOpen(e) {
			currentTab = tabs.getTabByEvent(e.htmlEvent);
			if(!currentTab) {
				e.cancel = true;
			}
		}
        
    </script>

</body>
</html>