<%--
  Created by IntelliJ IDEA.
  User: sante
  Date: 09.12.2022
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<c:set var="title" value="sign_up" scope="page"/>
<%@include file="../jspf/head.jspf"%>
<body class="back_main">
<form action="${pageContext.request.contextPath}/controller" method="get">
  <input type="hidden" name="command" id="command" value="${title}"/>
  <%--    <input type="submit" name="sessionLocale" value="en"/>--%>
  <select  class="select-css" id="language" name="sessionLocale" onchange="submit()">
    <option value="en" <c:if test="${sessionScope.lang == 'en'}">selected</c:if>><fmt:message
            key="label.lang.en"/></option>
    <option value="uk" <c:if test="${sessionScope.lang == 'uk'}">selected</c:if>><fmt:message
            key="label.lang.uk"/></option>
  </select>

</form>

<div class="login-box">
  <p class="login-title"><fmt:message key="label.signUp"/></p>
  <form class="login-form" action="${pageContext.request.contextPath}/controller?command=sign_up_post" method="post">
    <input name="login" class="login-input" placeholder="Login"
           required minlength="8" maxlength="20" pattern="[a-zA-Z0-9_]+">
    <input type="password" name="password" class="login-input" placeholder="Password"
           required minlength="8" maxlength="20" pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*#?&]{8,}$">
    <input class="login-input" type="email" name="email" placeholder="Email"
    minlength="5">
    <input type="submit" class="login-button" value="<fmt:message key="label.signUp"/>">
  </form>
  <c:if test="${param.err != null}">
    <p class="login-error"><fmt:message key="label.signUpError"/></p>
  </c:if>
  <p class="login-register"><fmt:message key="label.signUpQuestion"/> <a href="${pageContext.request.contextPath}/controller?command=login"><fmt:message key="label.signUpLogIn"/></a> </p>
</div>
<%@include file="../jspf/footer.jspf"%>

</body>
</html>