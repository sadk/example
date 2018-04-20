﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>首页-菜单</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
   	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/boot.js"></script>
   	
    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    .header
    {
        background:url(../header.gif) repeat-x 0 -1px;
        height:60px;
    }

    </style>   
</head>
<body>
    <div class="header" >        
        <div style="height:40px;line-height:40px;padding-left:15px;font-family:Tahoma;font-size:16px;font-weight:bold;">
            下拉菜单（顶部）
        </div>
        
    </div>
    <ul id="menu1" class="mini-menubar" style="width:100%;"
            url="../data/listTree.txt" onitemclick="onItemClick" 
            textField="text" idField="id" parentField="pid" 
        >
    </ul>
    <div class="mini-fit" style="padding-top:5px;">
        <!--Tabs-->
        <div id="mainTabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;"      
                 
        > 
            <div title="首页" url="../overview.html" >        
            </div>
            <div title="子页面关闭" url="../tabs/pages/page1.html" >        
            </div>
        </div>        
    </div>
    <div style="line-height:28px;text-align:center;cursor:default">Copyright © 上海普加软件有限公司版权所有 </div>
    
<script type="text/javascript">
    mini.parse();

    function showTab(node) {
        var tabs = mini.get("mainTabs");

        var id = "tab$" + node.id;
        var tab = tabs.getTab(id);
        if (!tab) {
            tab = {};
            tab.name = id;
            tab.title = node.text;
            tab.showCloseButton = true;

            //这里拼接了url，实际项目，应该从后台直接获得完整的url地址
            tab.url = mini_JSPath + "../../docs/api/" + node.id + ".html";

            tabs.addTab(tab);
        }
        tabs.activeTab(tab);
    }
    function onItemClick(e) {        
        var item = e.item;
        var isLeaf = e.isLeaf;

        if (isLeaf) {
            showTab(item);
        }            
    }
</script>

    
</body>
</html>