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
						<tr bgcolor="#FFFFFF">
							<td>some long title</td>
							<td>start of desc...</td>
							<td>flexible</td>
							<td>yes</td>
							<td>999</td>
							<td>9999-99-99</td>
							<td>9999-99-99</td>
							<td>Mo, Tu, We, Th, Fr, St, Su</td>
							<td>0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23</td>
							<td>edit</td>
							<td>delete</td>
						</tr>
					</table>
				</div><!-- div table -->
			</div><!-- big container -->
			<div class = "buttons">
				<input type="submit" value="add new task">
			</div>
		</div>	
	</body>
</html>
				