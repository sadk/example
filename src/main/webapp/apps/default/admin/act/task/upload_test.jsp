<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>我的待办（详细版）</title>
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
		</style>
	</head>
	<body>
		 <form enctype="multipart/form-data" method = "post" action = "${pageContext.request.contextPath}/api/workflow/upload">  
	       <input type="text" name="businessKey" value="222"/>  
	       <input type="text" name="definitionId" value="non_contract_payment:2:385004"/>  
	       <input type="text" name="definitionName" value="非合同付款"/>  
	       <input type="text" name="definitionKey" value="non_contract_payment"/>  
	       <input type="text" name="approveProcessInstanceId" value="427917"/>  
	       <input type="text" name="approveTaskId" value="427985"/>  
	       <input type="text" name="approveTaskKey" value="	usertask9"/>  
	        
	       <p>上传文件1:<input type = "file" name = "File1" size = "20" maxlength = "20"><br></p>  
	       <p>上传文件2:<input type = "file" name = "File2" size = "20" maxlength = "20"><br></p>  
	       <p>上传文件3:<input type = "file" name = "File3" size = "20" maxlength = "20"><br></p>  
	       <p>上传文件4:<input type = "file" name = "File4" size = "20" maxlength = "20"><br></p>  
	       <input type = "submit" value = "上传">  
	    </form>
	</body>
</html>