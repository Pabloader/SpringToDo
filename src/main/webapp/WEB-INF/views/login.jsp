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
        <title>Todo login</title>
    </head>
    <body>
        <div class="register-block">
            <h1>Наши милые тудушки!</h1>
            <form:form id="loginForm" method="POST" action="register" commandName="user">
                <table>
                    <tr>
                        <td>Login: </td><td><form:input path="login" /></td>
                    </tr>
                    <tr>
                        <td>Password: </td><td><form:input path="password" /></td>
                    </tr>
                    <form:hidden path="role" />
                    <tr>
                        <td colspan="2"><input type="submit"></td>
                    </tr>
                </table>
            </form:form>
        </div>
    </body>
</html>
