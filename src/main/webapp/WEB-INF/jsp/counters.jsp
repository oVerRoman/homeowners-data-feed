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
					<input type="text" name="currentValue"
					placeholder ="Введите показания счётчика">
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
				<form:form action="saveCounterValues" modelAttribute="counterValue">
					<input type="submit" value="Отправить">
				</form:form>
			</td>
		</tr>
	</table>
	<%--<form action="counters" method="get">
		<input type="text" name="counter"
		placeholder ="Введите наименование счётчика">
	</form>
	<br>
	<form:form action="counters" method="get" modelAttribute="value">
		<ul>
			<c:forEach var="counterName" items="${counter.names}"></c:forEach>
			<li> ${counterName} </li>
		</ul>
		Счётчик ${counterValue.value} <form:input path="counterValue"
		placeholder ="Введите показания счётчика"/>
		<br>
		<input type="submit" name="Отправить">
	</form:form>--%>
</body>
</html>