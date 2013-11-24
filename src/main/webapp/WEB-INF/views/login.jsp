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
        <div class="page-block register-block">
            <h1>Наши милые тудушки!</h1>
            <form name='f' action="<c:url value='j_spring_security_check' />" method='POST'>
                <label for="login-field">Имя пользователя</label>
                <input type='text' name='j_username' value=''>
                <label for="password-field">Пароль</label>
                <input type='password' name='j_password' />
                <input class="agree-button" name="submit" type="submit"
                       value="Авторизоваться" />
                <a href="<c:url value="/register"/>">
                    <input type="button" id="registerRedirect" class="link-button" value="Регистрация" />
                </a>
            </form>
        </div>
    </body>
</html>
