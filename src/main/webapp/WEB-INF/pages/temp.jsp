<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" href="resources/css/login.css">
		<title>Scheduler temp page</title>
	</head>
<body>
    <h1>Security passed</h1>	
    
    <form action="/Scheduler/j_spring_security_logout" method="post">
		<input type="submit" value="Log out" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>
</body>
</html>