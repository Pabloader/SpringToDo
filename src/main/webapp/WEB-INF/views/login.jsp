<%--
    Document   : login
    Created on : 15.11.2013, 17:40:10
    Author     : Sunrise
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/resources/style.css"/>"/>
        <title>Логин в тудушках</title>
    </head>
    <body>
        <div class="register-block">
            <h1>Наши милые тудушки!</h1>
            <form:form id="loginForm" method="POST" action="login" commandName="user">
                <label for="login">Login:</label>
                <form:input id="login" path="login" />
                <label for="password">Password:</label>
                <form:input id="password" path="password" />
                <input type="submit">
            </form:form>
        </div>
        <a href="${pageContext.servletContext.contextPath}/register">Регистрация</a>
    </body>
</html>
