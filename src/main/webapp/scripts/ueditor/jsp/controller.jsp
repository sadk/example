<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>

<%@ page import="org.lsqt.components.ext.ueditor.UrlFix" %>

<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = application.getRealPath( "/" );
	
	String json = new ActionEnter( request, rootPath ).exec() ;

	out.write(UrlFix.toJSONFix(json));
	
%>