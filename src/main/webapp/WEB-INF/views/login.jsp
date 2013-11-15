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
        <title>Todo login</title>
    </head>
    <body>
        Здесь типа форма регистрации
        <form:form method="POST" action="register" commandName="user">
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
    </body>
</html>
