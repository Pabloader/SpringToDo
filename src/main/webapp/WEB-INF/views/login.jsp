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
            <form name='f' action="<c:url value='j_spring_security_check' />" method='POST'>
                <table>
                    <tr>
                        <td>User:</td>
                        <td><input type='text' name='j_username' value=''>
                        </td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type='password' name='j_password' />
                        </td>
                    </tr>
                    <tr>
                        <td colspan='2'><input name="submit" type="submit"
                                               value="submit" />
                        </td>
                    </tr>
                </table>

            </form>
        </div>
        <a href="<c:url value="/register"/>">Регистрация</a>
    </body>
</html>
