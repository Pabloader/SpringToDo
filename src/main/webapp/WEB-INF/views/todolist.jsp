<%--
    Document   : todolist
    Created on : 13.11.2013, 18:26:50
    Author     : P@bloid
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="POST" action="register">
            Логин: <input type="text" name="username"><br/>
            Пассворд: <input type="text" name="password"><br/>
            <input type="submit">
        </form>
        <c:forEach items="${tasksList}" var="task">
            ${task}<br>
        </c:forEach>
    </body>
</html>
