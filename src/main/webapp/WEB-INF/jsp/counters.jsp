<%@page import="org.apache.catalina.filters.ExpiresFilter.XServletOutputStream"%>
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
	<%--<form:form action="getAllCounterValues" modelAttribute="address">
		<table>
			<tr>
				<th>Город *</th>
				<th>Улица *</th>
				<th>Дом *</th>
				<th>Корпус</th>
				<th>Квартира *</th>
			</tr>
			<tr>
				<td>
					<form:hidden path="id"/>
					<form:input path="city"/>
				</td>
				<td>
					<form:input path="street"/>
				</td>
				<td>
					<form:input path="house"/>
				</td>
				<td>
					<form:input path="building"/>
				</td>
				<td>
					<form:input path="apartment"/>
				</td>
			</tr>
		</table>
	</form:form>
	<br> --%>
	<form:form action="saveCounterValues" modelAttribute="allCurrentValues">
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
						<form:input type="number" path="counterValues[${status.index}].value"
						placeholder="Введите показания"/>
						<form:hidden path="counterValues[${status.index}].id"/>
					</td>
					<td>${allValueErrors[status.index]}</td>
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