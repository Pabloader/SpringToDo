<%--
    Document   : todolist
    Created on : 13.11.2013, 18:26:50
    Author     : P@bloid
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
        <title>Todo list!</title>
    </head>
    <body>
        Здесь типа форма создания новой задачи
        <div class="page-block">
            <form:form id="addTaskForm" method="POST" action="add" commandName="task">
                <table>
                    <tr>
                        <th colspan="2">New task creator!</th>
                    </tr>
                    <tr>
                        <td>Task Title:</td>
                        <td rowspan="7">
                            <form:textarea rows="10" cols="15" path="content"/>
                        </td>
                    </tr>
                    <tr>
                        <td><form:input path="title"/></td>
                    </tr>
                    <td>TargetDate:</td>
                    </tr>
                    <tr>
                        <td>
                            <form:input path="targetTime"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Accessibility
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:radiobutton path="pubStatus" value="0" checked="true"/> <!-- notPublic -->
                            <form:radiobutton path="pubStatus" value="1"/> <!-- publicReadable -->
                            <form:radiobutton path="pubStatus" value="2"/> <!-- publicEditable -->
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Priority: <form:input path="priority"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="submit"/></td>
                    </tr>
                </table>
            </form:form>
        </div>

        <br/><br/><br/>
        Здесь типа список доступных задач
        <c:forEach items="${tasksList}" var="task">
            <div class="page-block">
                <table class="task-table">
                    <tr>
                        <th colspan="2">${task.title}</th>
                    </tr>
                    <tr>
                        <td>Автор: <b>${task.author.login}</b></td>
                    </tr>
                    <tr>
                        <td>Дата создания:</td>
                        <td rowspan="6">${task.content}</td>
                    </tr>
                    <tr>
                        <td>${task.creationTime}</td>
                    </tr>
                    <tr>
                        <td>Дата выполнения:</td>
                    </tr>
                    <tr>
                        <td>${task.targetTime}</td>
                    </tr>
                    <tr>
                        <td>Приоритет: ${task.priority}</td>
                    </tr>
                </table>
            </div>
        </c:forEach>
    </body>
</html>
