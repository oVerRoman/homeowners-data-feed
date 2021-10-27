<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE HTML>
<html>
<head>
  <title>Главная</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<div>
  <h3>${pageContext.request.userPrincipal.name}</h3>
<%--  // если не авторизован показываем--%>
  <sec:authorize access="!isAuthenticated()">

<%--    <h4><a href="/username">Войти по SMS паролю</a></h4>--%>
    <h4><a href="/registration">Зарегистрироваться</a></h4>

<%--    <h4>Версия 0.9.1</h4>--%>
  </sec:authorize>

<%--// а если авторизован что показваем на стартовой странице --%>
  <sec:authorize access="isAuthenticated()">
    <h4><a href="/logout">Выйти</a></h4>
    <h4><a href="/news">Новости(для Юзеров)</a></h4>
    <h4><a href="/rest/admin/users">Пользователи (только админ)</a></h4>
    <h4><a href="/counters">Счетчики</a></h4>
    <h4><a href="/add-counter">добавить счетчик</a></h4>
  </sec:authorize>
</div>
</body>
</html>
