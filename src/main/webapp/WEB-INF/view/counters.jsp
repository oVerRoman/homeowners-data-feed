<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Показания счётчиков</title>
</head>
<body>
	<h2>Показания</h2>
	<br><br>
	<form action="counters" method="get">
		<input type="text" name="counter"
		placeholder ="Внесите наименование счётчика">
	</form>
	<br>
	<form:form action="counters" method="get" modelAttribute="value">
		<ul>
			<c:forEach var="counterName" items="${counter.names}"></c:forEach>
			<li> ${counterName} </li>
		</ul>
		Счётчик ${counterValue.value} <form:input path="counterValue"
		placeholder ="Внесите показания счётчика"/>
		<br>
		<input type="submit" name="Отправить">
	</form:form>
</body>
</html>