<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/resources/style.css"/>"/>
        <title>Регистрация в тудушках</title>
    </head>
    <body>
        <div class="page-block register-block">
            <h1>Форма регистрации</h1>
            <form:form id="registerForm" method="POST" action="register" commandName="newUser">
                <label for="login">Login:</label>
                <form:input id="login" path="login" />
                <label for="password">Password:</label>
                <form:input id="password" path="password" />
                <input type="submit" class="agree-button" name="register-submit" value="Зарегистрироваться"/>
                <a href="${pageContext.servletContext.contextPath}/login">
                    <input type="button" id="registerRedirect" class="link-button" value="Авторизация" />
                </a>
            </form:form>
        </div>
    </body>
</html>
