<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

</head>
<body>
<section>

  <hr>
  <jsp:useBean id="address" class="com.simbirsoftintensiv.intensiv.entity.Address" scope="request"/>
  <jsp:useBean id="user" class="com.simbirsoftintensiv.intensiv.entity.User" scope="request"/>
  <form method="post" action="registration">
    <input type="hidden" name="id" value="${user.id}">
    <dl>
      <dt>phone:</dt>
      <dd><input type="number" value="${user.phone}" name="phone" required></dd>
    </dl>
    <dl>
      <dt>email:</dt>
      <dd><input type="text" value="${user.email}" size=40 name="email" required></dd>
    </dl>
    <dl>
      <dt>first_name:</dt>
      <dd><input type="text" value="${user.firstName}" name="firstName" required></dd>
    </dl>
    <dl>
      <dt>second_name:</dt>
      <dd><input type="text" value="${user.secondName}" name="secondName" required></dd>
    </dl>
    <dl>
      <dt>patronymic:</dt>
      <dd><input type="text" value="${user.patronymic}" name="patronymic" required></dd>
    </dl>

    <dl>
      <dt>city:</dt>
      <dd><input type="text" value="${address.city}" name="city" required></dd>
    </dl>
    <dl>
      <dt>street:</dt>
      <dd><input type="text" value="${address.street}" name="street" required></dd>
    </dl>
    <dl>
      <dt>house:</dt>
      <dd><input type="text" value="${address.house}" name="house" required></dd>
    </dl>
    <dl>
      <dt>building:</dt>
      <dd><input type="text" value="${address.building}" name="building" required></dd>
    </dl>

    <dl>
      <dt>apartment:</dt>
      <dd><input type="text" value="${address.apartment}" name="apartment" required></dd>
    </dl>




    <button type="submit">Save</button>
    <button onclick="window.history.back()" type="button">Cancel</button>
  </form>
</section>
</body>
</html>
