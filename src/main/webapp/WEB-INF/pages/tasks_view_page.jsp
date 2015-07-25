<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="org.joda.time.LocalDate" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tasks_view_page.css">
		
		<!-- css for both panels ebd body -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/upper_panel.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/side_panel.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/body_common.css">
	</head>
	<body class="Body_body">
		<%@ include file="upper_panel.jsp" %>
		<%@ include file="side_panel.jsp" %>
	
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
								<td>
									<c:set var="days" value="${repr.getActiveDayAt(1) == true ? 'Mo, ' : ''}"/>
									<c:set var="days" value="${days} ${repr.getActiveDayAt(2) == true ? 'Tu, ' : ''}"/>
									<c:set var="days" value="${days} ${repr.getActiveDayAt(3) == true ? 'We, ' : ''}"/>
									<c:set var="days" value="${days} ${repr.getActiveDayAt(4) == true ? 'Th, ' : ''}"/>
									<c:set var="days" value="${days} ${repr.getActiveDayAt(5) == true ? 'Fr, ' : ''}"/>
									<c:set var="days" value="${days} ${repr.getActiveDayAt(6) == true ? 'Sa, ' : ''}"/>
									<c:set var="days" value="${days} ${repr.getActiveDayAt(7) == true ? 'Su' : ''}"/>						
								
									<!-- removes last comma, so complicated construction because jstl has bug with endsWith -->
									<c:if test="${fn:endsWith(fn:substring(days, fn:length(fn:trim(days)),
											fn:length(fn:trim(days))+1), ',')}">
										<c:set var="days" value="${fn:substring(days, 0, (fn:length(fn:trim(days))))}"/>
									</c:if>
									${days}
								</td>
								<td>
									<c:set var="hours" value="${repr.getActiveHourAt(0) == true ? '0, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(1) == true ? '1, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(2) == true ? '2, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(3) == true ? '3, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(4) == true ? '4, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(5) == true ? '5, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(6) == true ? '6, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(7) == true ? '7, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(8) == true ? '8, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(9) == true ? '9, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(10) == true ? '10, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(11) == true ? '11, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(12) == true ? '12, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(13) == true ? '13, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(14) == true ? '14, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(15) == true ? '15, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(16) == true ? '16, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(17) == true ? '17, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(18) == true ? '18, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(19) == true ? '19, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(20) == true ? '20, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(21) == true ? '21, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(22) == true ? '22, ' : ''}"/>
									<c:set var="hours" value="${hours} ${repr.getActiveHourAt(23) == true ? '23, ' : ''}"/>
									
									<!-- always cutts the last symbol (comma) -->
									${fn:substring(hours, 0, (fn:length(fn:trim(hours)))-1)}
								</td>
								<td><a href="${pageContext.request.contextPath}/app/task/edit/?task_id=${repr.id}">edit</a></td>
								<td><a href="${pageContext.request.contextPath}/app/task/delete/?task_id=${repr.id}">delete</a></td>
							</tr>
						</c:forEach>
					</table>
				</div><!-- div table -->
			</div><!-- big container -->
			<div class = "buttons">
				<form action="${pageContext.request.contextPath}/app/task/edit" method="GET">
					<input type="submit" value="add new task">
					<!-- input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/-->
				</form>
			</div>
		</div>	
	</body>
</html>
				