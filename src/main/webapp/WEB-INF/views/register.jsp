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
                <c:if test="${!empty error}">
                    <div class="ui-state-error ui-corner-all"> 
                        <p>
                            <span class="ui-icon ui-icon-alert" 
                                  style="float: left; margin-right: .3em;"></span>
                            ${error}
                        </p>
                    </div>
                </c:if>
                <label for="login">Как ВасЪ величать:</label>
                <form:input id="login" path="login" />
                <label for="password">Извольте указать кодовое слово:</label>
                <form:input id="password" path="password" />
                <input type="submit" class="agree-button" name="register-submit" value="Отправить в реестрЪ"/>
                <a href="<c:url value="/login"/>">
                    <input type="button" id="registerRedirect" class="link-button" value="Предъявить документЪ" />
                </a>
            </form:form>
        </div>
    </body>
</html>
