<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Регистрация</title>
</head>

<body>
<div>
  <form:form method="POST" modelAttribute="userForm">
    <h2>Регистрация</h2>
    <div>
      <form:input type="text" path="username" placeholder="Фамилия"
                 ></form:input>
    </div>
    <div>
      <form:input type="text" path="username" placeholder="Имя"
                ></form:input>
    </div>
    <div>
      <form:input type="text" path="username" placeholder="Отчество"
                 ></form:input>
    </div>
    <div>
      <form:input type="text" path="username" placeholder="Улица"
                 ></form:input>
    </div>
    <div>
      <form:input type="text" path="username" placeholder="Дом"
                  ></form:input>
    </div>
    <div>
      <form:input type="text" path="username" placeholder="Корпус"
                  ></form:input>
    </div>
    <div>
      <form:input type="text" path="username" placeholder="Квартира"
                  ></form:input>
    </div>
    <div>
      <form:input type="text" path="username" placeholder="Счёт"
                  ></form:input>
    </div>
    <div>
      <form:input type="text" path="username" placeholder="Телефон"
                  ></form:input>
    </div>
    <div>
      <form:input type="text" path="username" placeholder="Электронаая почта"
                  ></form:input>
    </div>
    <button type="submit">Зарегистрироваться</button>
  </form:form>
  <a href="/">Главная</a>
</div>
</body>
</html>