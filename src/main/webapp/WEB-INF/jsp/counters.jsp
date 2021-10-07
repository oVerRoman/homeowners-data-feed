<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Показания счётчиков</title>
</head>
<body>
	<h2>Показания</h2>
	<br><br>
	<form:form action="saveCounterValues" method="post" modelAttribute="allCurrentValues">
	
	<table>
		<tr>
			<th>Счётчик</th>
			<th>Предыдущее значение</th>
			<th>Текущее значение</th>
		</tr>
		<c:forEach var="counter" items="${allCounters}" varStatus="status">
			<tr>
				<td>${counter.name}</td>
				<td>${allCounterValues[status.index].value}</td>
				<td>
					<form:input path="counterValues[${status.index}].value"/>
					<form:hidden path="counterValues[${status.index}].id"/>
				</td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<table>
		<tr>
			<td>
				<input type="button" value="+"
				onclick="window.location.href='addCounter'"> Добавить счётчик
			</td>
			<td width=170></td>
			<td>
				<input type="submit" value="Отправить">
			</td>
		</tr>
	</table>
	
	</form:form>
</body>
</html>