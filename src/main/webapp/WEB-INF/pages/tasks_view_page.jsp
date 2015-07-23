<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="org.joda.time.LocalDate" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tasks_view_page.css">
	</head>
	<body>
		<div class="TR_main">
			<div class="TR_main_label">
				tasks review
			</div>
			<div class="TR_big_Container">
				<div class="TR_input_container">
					sort by: <select name="by1"></select>
				</div>
				<div class="TR_label_1">
					list of existing tasks
				</div>
				<div class="TR_table_container">
					<table border="0" cellspacing="1" bgcolor="#000000" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<th width="130px">title</th>
							<th width="100px">description</th>
							<th width="50px">type</th>
							<th width="20px">active</th>
							<th width="12px">necessary hour(s)</th>
							<th width="80px">start date</th>
							<th width="80px">end date</th>
							<th width="175px">active days</th>
							<th width="260px">active hours</th>
							<th>edit</th>
							<th>delete</th>
						</tr>
						<c:forEach var="repr" items="${taskRepr}">
							<tr bgcolor="#FFFFFF">
								<td>${repr.title}</td>
								<td title="${repr.description}">${fn:substring(repr.description, 1, 14)}...</td>
								<td>${repr.type}</td>
								<td>${repr.isActive}</td>
								<td>${repr.necessaryTime}</td>
								<td>${type == 'FlexibleTerm' ? repr.startDate : LocalDate(repr.interval.startMillis)}</td>
								<td>${LocalDate(repr.interval.endMillis)}</td>
								<td>${repr.getActiveDayAt(1) == true ? "Mo, " : ""}
									 ${repr.getActiveDayAt(2) == true ? "Tu, " : ""}
									 ${repr.getActiveDayAt(3) == true ? "We, " : ""}
									 ${repr.getActiveDayAt(4) == true ? "Th, " : ""}
									 ${repr.getActiveDayAt(5) == true ? "Fr, " : ""}
									 ${repr.getActiveDayAt(6) == true ? "Sa, " : ""}
									 ${repr.getActiveDayAt(7) == true ? "Su" : ""}</td>
								<td>0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23</td>
								<td><a href="${pageContext.request.contextPath}/app/task/edit/?task_id=${repr.id}">edit</a></td>
								<td><a href="${pageContext.request.contextPath}/app/task/delete/?task_id=${repr.id}">delete</a></td>
							</tr>
						</c:forEach>
					</table>
				</div><!-- div table -->
			</div><!-- big container -->
			<div class = "buttons">
				<input type="submit" value="add new task">
			</div>
		</div>	
	</body>
</html>
				