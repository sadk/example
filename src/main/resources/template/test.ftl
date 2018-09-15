<html> 
<head> 
    <title>Welcome!</title> 
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
</head> 
<body> 
    <h1>Welcome ${user.id}!</h1> 
    <p>Our latest product: 
    <a href="${user.name}">${user.age}</a>! <br>
    
    <#if userList?? && (userList?size > 0)>
    	<#list userList as user>
    		${user.name} <br>
    		<#if user_has_next>,</#if>
		</#list>
	</#if>

</body> 
</html> 