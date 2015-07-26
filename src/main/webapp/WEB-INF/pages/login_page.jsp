<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" href="resources/css/login_page.css">
		<title>Scheduler login page</title>
	</head>
	<body class="body" title="You must sing up before using application. Please use link next to login button">
			<div class="name">
				Scheduler
			</div>
			<div class="centerElement">
				<div class="labels">
					user:<br>
					password:
				</div>
				<div class="inputs">
					<form name ="f" action ="login" method="POST">
						<input type="text" size="19" name="username" value=""/><br>
						<input type="password" size="19" name="password"/>
						<div class="button">
							<input type="submit" value="Login" name="submit">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<a href="${pageContext.request.contextPath}/sing up">registration</a>
						</div>
					</form>
				</div>
			</div>
			<div class="link">
				<a href="https://github.com/yvalera/scheduler">
					project on GitHub</a>
			</div>
	</body>
</html>
				