<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<title>Добавление счётчика</title>
</head>
<body>
	<h2>Добавление счётчика</h2>
	<br><br>
	<form:form action="saveCounter" modelAttribute="counter">
		Наименование <form:input path="name"/>
		<input type="submit" value="Добавить">
	</form:form>
</body>
</html>