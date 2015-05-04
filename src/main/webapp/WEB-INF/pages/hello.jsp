<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<body>
		<h1>Some text</h1>
		


   		<c:forEach items="${toPrint}" var="string"> 
        	<c:out value="${string}"/>  
        	<br>
     	</c:forEach>
	</body>
</html>