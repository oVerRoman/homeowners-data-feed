<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <title>Зайти по паролю</title>
</head>

<body>
<sec:authorize access="isAuthenticated()">
    <% response.sendRedirect("/"); %>
</sec:authorize>

<%
    String name = String.valueOf(request.getAttribute("username"));
%>
<div>
    <form method="POST" action="/password" modelAttribute="username">
        <h2>Вход в систему</h2>
        <div>
            <p >Данные пришедшие с модели
                <%
                    out.println(name);
                %>
            </p>

            <input name="username" type="text" placeholder="Password"
                   autofocus="true" value="${name}"/>

            <input name="password" type="password" placeholder="Password"/>
            <button type="submit">Войти</button>
        </div>
    </form>
</div>

</body>
</html>
