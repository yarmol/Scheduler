<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="org.joda.time.LocalDate, org.joda.time.Period,
			 org.joda.time.PeriodType,  org.joda.time.Interval,
			 org.joda.time.Days,
			java.util.Map, java.util.LinkedHashMap,
			java.util.Locale" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	/*
	* this code parses schedule to mark months boundaries
	* for nice representing
	*/

	//these two values must be equal to values in "days_view_page.css"
	int sqSize = 18;//size of every square element
	int space = 2;//space between days
	
			int totalMonths;//total monthes in requested interval
	int startYear;//year of start interval
	int startMonths;//months in beginning of interval
	int firstMonthStartDay;//start day in first months
	int daysInFirstMonth;//number of days in first months
	int lastDayOfLastMonth;//last days in last months
	int totalDays;//total days, necessary to count min general month div width
	
	String firstMonthName;//name of the firts month
	String lastMonthName;//name of last months
	
	Interval interval = (Interval) request.getAttribute("interval");
	//System.out.println(interval);
	
	//days in certain months in interval
	//keeps insertion order
	Map<String, Integer> daysInMonthes = null;
	
	//how days in period
	totalDays = Days.daysBetween(interval.getStart(), interval.getEnd())
			.getDays();
	
	//how months in period
	Period p = new Period(interval.getStartMillis(),
			interval.getEndMillis(), PeriodType.months().
				withDaysRemoved());
	totalMonths = p.getMonths() + 1;
	
	/*
	 * parsed start position for pointer (pointer increase 
	 * on 1 month in loop)
	 */
	daysInMonthes = new LinkedHashMap<String, Integer>();
	startYear = new LocalDate(interval.getStartMillis()).
			getYear();
	startMonths = new LocalDate(interval.getStartMillis()).
			getMonthOfYear();
	
	//days in first months
	firstMonthStartDay = new LocalDate(interval.getStartMillis()).
			getDayOfMonth();
	daysInFirstMonth = new LocalDate(interval.getStartMillis()).
			dayOfMonth().getMaximumValue();
	firstMonthName = new LocalDate(interval.getStartMillis()).
			monthOfYear().getAsText(new Locale("en")) + " " +
			new LocalDate(interval.getStartMillis()).getYear();
	daysInMonthes.put(firstMonthName, daysInFirstMonth);
	
	//days in other months except last
	for(int i = 0; i < totalMonths - 2; i++){
		//begining of the start months
		LocalDate date = new LocalDate(startYear, startMonths, 1);
		//retrieving necessary months
		date = date.plusMonths(i+1);
		//retrieving last day in months
		int daysQuantity = date.dayOfMonth().getMaximumValue();
		//retrieving months name
		String monthName = date.monthOfYear().getAsText(new Locale("en")) +
				" " + date.getYear();
		//saves data in the Map
		daysInMonthes.put(monthName, daysQuantity);
	}
	
	//necessary days in last months
	lastDayOfLastMonth = new LocalDate(interval.getEndMillis()).
			getDayOfMonth();
	lastMonthName = new LocalDate(interval.getEndMillis()).
			monthOfYear().getAsText(new Locale("en")) + " " +
			new LocalDate(interval.getEndMillis()).getYear();
	daysInMonthes.put(lastMonthName, lastDayOfLastMonth);
	
	//makes variable avaible with JSP EL and JSTL 
	request.setAttribute("sqSize", sqSize);
	request.setAttribute("space", space);
	request.setAttribute("totalMonths", totalMonths);
	request.setAttribute("startYear", startYear);
	request.setAttribute("startMonths", startMonths);
	request.setAttribute("firstMonthStartDay", firstMonthStartDay);
	request.setAttribute("daysInFirstMonth", daysInFirstMonth);
	request.setAttribute("lastDayOfLastMonth", lastDayOfLastMonth);
	request.setAttribute("firstMonthName", firstMonthName);
	request.setAttribute("lastMonthName", lastMonthName);
	request.setAttribute("daysInMonthes", daysInMonthes);
	request.setAttribute("totalDays", totalDays);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<!-- ${pageContext.request.contextPath} returns "/Scheduller" -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/days_view_page.css">
	</head>
	<body>
		<div>
			<div class = "DaysView_5">
				<!-- to make all month stay in one line -->
				<!-- math: (day + both day spaces(margins)) * number of days + total months * months margin + 
						+ column with time + constant (experementaly 20 px)>
				<!-- CONTAINER FOR ALL MONTHS -->
				<div style="min-width: ${totalDays * (sqSize + 2 * space) + totalMonths * 8 + 20 + sqSize * 5.3}px;">
					<c:set var="counter" value="1"/>
					
					<!-- column with time -->
					<div style="width: ${sqSize * 5.3}px; float: left; margin: 2px">
						<c:forEach var="i" begin="1" end="26">
							<c:choose>
								<c:when test="${i < 3}">
									<div class="DVP_hour_empty_label"></div>
								</c:when>
								<c:otherwise>
									<div class="DVP_hour_label">
										<c:choose>
											<c:when test="${i > 2 and i < 12}">
												0${i - 3}:00-0${i-2}:00
											</c:when>
											<c:when test="${i == 12}">
												09:00-10:00
											</c:when>
											<c:when test="${i > 12}">
												${i - 3}:00-${i-2}:00
											</c:when>
										</c:choose>
									</div>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div><!--end column with time -->
					
					<!-- MAIN MOTHS LOOP -->
					<c:forEach var="monthsMapElement" items="${daysInMonthes}">				
					
						<!-- ternary if -->
						<c:set var="containerWidth" value="${ counter == 1 ? 
								((monthsMapElement.value-firstMonthStartDay + 1) * 	sqSize + 
									(monthsMapElement.value-firstMonthStartDay + 1) * 2 * space + 1)
							:	
									(monthsMapElement.value * sqSize + monthsMapElement.value * 2 * space + 1)}"/>
						
						<c:set var="labelWidth" value = "${containerWidth - 
								2 * space}"/> 
						 
						<!-- FROM THIS POINT GO MONTHS WITH LABELS -->
						<div style="width: ${containerWidth}px; float: left; margin-right: 5px">
							
							
							<!-- months name -->
							<div style="width: ${labelWidth}px; text-align: center; background-color: #AAAAFF; margin: ${space}px">
								${monthsMapElement.key}
							</div>
							
							<!-- month's schedule representation div -->
							<div style="width: ${containerWidth}px">
								
								<!-- days string -->
								<c:forEach var="i" begin="${counter == 1 ? firstMonthStartDay : 1}" 
										end="${monthsMapElement.value}">
									<div class="DVP_number_square">
										${i}
									</div>
								</c:forEach>
								
								<!-- for every 24 hours in day -->
								<c:forEach var="i" begin="1" end="24">
									
									<!-- for every hour in day sequence -->
									<c:forEach var="i" begin="${counter == 1 ? firstMonthStartDay : 1}" 
											end="${monthsMapElement.value}">
										<div class="DVP_square">
											<!-- temporary just unsigned square 
												here must be code which retrieves
												data for every code and changes 
												style for this div-->
										</div>
									</c:forEach>
								</c:forEach><!-- end for every 24 hours in day -->
							</div><!-- end month's schedule representation div -->
						</div><!--END FROM THIS POINT GO MONTHS WITH LABELS -->
						
						<c:set var="counter" value="${counter + 1}"/>
					</c:forEach><!-- END MAIN MOTHS LOOP -->
				</div><!--END CONTAINER FOR ALL MONTHS -->
			
			
			<div class= "DVP_dataSelect">
			<!-- ADD FORM -->
				<div class="DVP_inputContainer1">
					from date<br>
					<input type="date" name="calendar" size="10">
				</div>
				<div class="DVP_inputContainer1">
					to date<br>
					<input type="date" name="calendar" size="10">
				</div>
				<div class="DVP_inputContainer1">
					mode<br>
					<select>
   						<option disabled>days</option>
   						<option disabled>months</option>
  					</select>
				</div>
				<div class="DVP_inputContainer1">
					<br>
					<input type="submit" value="show" name="show">
				</div>
			</div>
		</div>
	</body>
</html>
				