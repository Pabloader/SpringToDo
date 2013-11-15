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
        <title>Todo list!</title>
    </head>
    <body>
        Здесь типа форма создания новой задачи
        <form id="addTaskForm" method="POST" action="add">
            <table>
                <tr>
                    <th colspan="2">New task creator!</th>
                </tr>
                <tr>
                    <td>Task Title:</td>
                    <td rowspan="7">
                        <textarea name="taskContent">
                            Покормите меня содержанием задачи!!!
                        </textarea>
                    </td>
                </tr>
                <tr>
                    <td><input type="text" name="taskTitle" /></td>
                </tr>
                    <td>TargetDate:</td>
                </tr>
                <tr>
                    <td>
                        <input type="text" name="targetDate" />
                    </td>
                </tr>
                <tr>
                    <td>
                        Accessibility
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="radio" name="pubAccess" value="0" checked /> <!-- notPublic -->
                        <input type="radio" name="pubAccess" value="1" /> <!-- publicReadable -->
                        <input type="radio" name="pubAccess" value="2" /> <!-- publicEditable -->
                    </td>
                </tr>
                <tr>
                    <td>
                        Priority: <input type="text" name="taskPriority" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit"/></td>
                </tr>
            </table>
        </form>

        <br/><br/><br/>
        Здесь типа список доступных задач
        <c:forEach items="${tasksList}" var="task">
            <table>
                <tr>
                    <th colspan="2">${task.title}</th>
                </tr>
                <tr>
                    <td>Дата создания:</td>
                    <td rowspan="5">${task.content}</td>
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
        </c:forEach>
    </body>
</html>
