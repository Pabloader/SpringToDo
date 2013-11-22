<%--
    Document   : todolist
    Created on : 13.11.2013, 18:26:50
    Author     : P@bloid
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/resources/style.css"/>"/>
        <title>Todo list!</title>
    </head>
    <body>

        <div id="userinfo">
            <sec:authorize access="isAuthenticated()">
                Информация о вошедшем юзере
                <sec:authentication property="principal.username" />
            </sec:authorize>

        <h1>Здесь типа форма создания новой задачи</h1>
        <div class="page-block">
            <form:form id="addTaskForm" method="POST" action="add" commandName="task">
                <table>
                    <tr>
                        <th colspan="2">New task creator!</th>
                    </tr>
                    <tr>
                        <td>Заголовок задачи:</td>
                        <td rowspan="9">
                            <form:textarea rows="15" cols="25" path="content"/>
                        </td>
                    </tr>
                    <tr>
                        <td><form:input path="title"/></td>
                    </tr>
                    <td>Принадлежит списку:</td>
                    </tr>
                    <tr>
                        <td>
                            <input disabled="true"/>
                        </td>
                    </tr>
                    <td>Дата выполнения:</td>
                    </tr>
                    <tr>
                        <td>
                            <form:input path="targetTime"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Приоритет: <form:input path="priority"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="submit"/></td>
                    </tr>
                </table>
            </form:form>
        </div>

        <br/><br/><br/>
        <h1>Здесь типа список доступных задач</h1>
        <c:forEach items="${tasksList}" var="list" varStatus="i">
            <div class="page-block">
                ${i.count}
                Task ID:${list.id}
                <table class="task-table">
                    <tr>
                        <th colspan="2">${list.title}</th>
                    </tr>
                    <tr>
                        <td>Автор: <b>${list.author.login}</b></td>
                        <td rowspan="2">Здесь <s>могла быть ваша реклама</s> должен быть список задач, в виде ссылок</td>
                    </tr>
                    <tr>
                        <td>
                            Тип доступа:
                        </td>
                    </tr>
                    <tr>
                        <td>${list.pubStatus}</td>
                    </tr>
                </table>
            </div>
        </c:forEach>
    </body>
</html>
