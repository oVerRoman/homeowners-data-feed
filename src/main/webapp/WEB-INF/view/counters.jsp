<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Показания счётчиков</title>
</head>
<body>
	<h2>Показания счётчиков</h2>
	<br><br>
	<form action="counters" method="get">
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
	</form:form>
</body>
</html>