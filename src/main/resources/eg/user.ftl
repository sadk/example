//~~~~~~~~~~~~~~~~~~~~~
hello ${user}  ！

${user.id}
${user.name}
日期不能直接显示，需在后面声明为日期类型：
显示日期和时间：${user.birthday?datetime}
只显示时间：${user.birthday?time}
只显示日期：${user.birthday?date}
友好显示某对象属性格试化后的日期：${user.birthday?string("yyyy-MM-dd HH:mm:ss")}

<#-- 这只是注释，不会解析 -->

<#if user.address??>
  已填写地址
<#else>
  未填写地址
</#if>
//~~~~~~~~~~~~~~~~~~~~~~~循环
<#list 1..5 as t>
 ${t}
</#list>

<#list userList as user>
 ${user.name}
</#list>



//~~~~~~~~~~~~~~~~~
 Hash的内置函数
<#assign user={"name":"hailang", "sex":"man"}>
<#assign keys=user?keys>

<#list keys as key>
	${key}=${user[key]}
</#list>
//~~~~~~~~~~~~~~~~~~

<#assign date1="2009-10-12"?date("yyyy-MM-dd")>
<#assign date2="9:28:20"?time("HH:mm:ss")>
<#assign date3="2009-10-12 9:28:20"?time("yyyy-MM-dd HH:mm:ss")>
<#assign date3="2014-08-02 19:38:44"?time("yyyy-MM-dd HH:mm:ss")>
${date1}
${date2}
${date3}

<#list student as stu> 
${stu}
</#list>
