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
        <link rel="stylesheet" href="<c:url value="/resources/style.css"/>" />

        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js">
        </script>
        <script type="text/javascript" src="<c:url value="/resources/script.js" />" >
        </script>
        <title>Todo list!</title>
    </head>
    <body>

        <div id="userinfo">
            <sec:authorize access="isAuthenticated()">
                Информация о вошедшем юзере
                <sec:authentication property="principal.username" />
            </sec:authorize>

            <div class="page-block add-task-block">
                <h1>Форма создания новой задачи (JSP)</h1>
                <form:form id="addTaskForm" method="POST" action="add" commandName="task">
                    <table>
                        <tr>
                            <th colspan="2">
                            </th>
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
                            <td colspan="2"><input class="agree-button" type="submit"/></td>
                        </tr>
                    </table>
                </form:form>
            </div>

            <div class="page-block add-task-block"><h1>Форма создания новой задачи (jQuery)</h1>
                <form id="task-add-form" method="POST" action="api/addTask">
                    <div class="half-width-form">
                        <label for="task-title">Заголовок задачи</label>
                        <input type="text" id="task-title" placeholder="Заголовок..."/>
                        <label for="task-parent">Включить в список:</label>
                        <input disabled type="text" id="task-parent"/>
                        <label for="task-title">Дата выполнения:</label>
                        <input type="text" id="task-target-date" placeholder="Введите дату..."/>
                        <label for="task-title">Приоритет:</label>
                        <input type="text" id="task-priority" placeholder="Укажите приоритет задачи..."/>
                    </div>
                    <div class="half-width-form">
                        <label for="task-content">Содержание задачи:</label>
                        <textarea id="task-content" rows="13" cols="25" id="task-content" value="task-content" >ЖОПА</textarea>
                    </div>
                    <input type="button" class="agree-button" id="add-task-button" name="add-task-button" value="Сохранить задачу" />
            </div>

            <br/><br/><br/>
            <h1>Здесь типа список доступных задач</h1>
            <c:forEach items="${tasksList}" var="list" varStatus="i">
                <div class="page-block task-block">
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
