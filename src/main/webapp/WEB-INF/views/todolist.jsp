<%--
    Document   : todolist
    Created on : 13.11.2013, 18:26:50
    Author     : P@bloid
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/resources/style.css"/>" />
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/cupertino/jquery-ui.css">

        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
        <script type="text/javascript" src="<c:url value="/resources/script.js"/>" ></script>
        <title>Todo list!</title>
    </head>
    <body>

        <div id="userinfo">
            <sec:authorize access="isAuthenticated()">
                Информация о вошедшем юзере
                <b><sec:authentication property="principal.username" /></b><br/>
                <a href="<c:url value="j_spring_security_logout" />"> ВЫЙТЕ!!!11</a>
            </sec:authorize>
        </div>

        <div class="page-block add-task-block">
            <h1>Форма создания новой задачи (jQuery)</h1>
            <form id="task-add-form" method="POST" action="api/addTask">
                <div class="width34-form-block">
                    <label for="task-title">Заголовок задачи</label>
                    <input type="text" id="task-title" placeholder="Заголовок..."/>
                    <label for="task-parent">Включить в список:</label>
                    <select id="task-parent">
                        <c:forEach items="${lists}" var="list">
                            <c:if test="${(list.author.id == author.id)||(list.pubStatus == 2) || author.role=='ROLE_ADMIN'}">
                                <option value="${list.id}">${list.title}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <label for="task-title">Дата выполнения:</label>
                    <input type="text" id="task-target-date" placeholder="Введите дату..."/>
                    <label for="task-title">Приоритет:</label>
                    <input type="text" id="task-priority" placeholder="Укажите приоритет задачи..."/>
                </div>
                <div class="width64-form-block">
                    <label for="task-content">Содержание задачи:</label>
                    <textarea id="task-content" rows="12" cols="25" id="task-content" value="task-content" placeholder="Содержание задачи..."></textarea>
                </div>
                <input type="button" class="agree-button" id="add-task-button" name="add-task-button" value="Сохранить задачу" />
            </form>
        </div>

        <h1>Здесь типа список списков, а внутри - списки доступных задач</h1>
        <c:forEach items="${lists}" var="list">
            <div class="task-list-div" data-list-id="${list.id}">
                <h1>${list.title}</h1>
                <div class="content-wrapper">
                    <c:forEach items="${list.tasks}" var="task">
                        <div class="page-block task-block">
                            <h1>${task.title}</h1>
                            <c:if test="${author.id == task.author.id || (list.pubStatus == 2) || author.role=='ROLE_ADMIN'}">
                                <button data-id="${task.id}" class="delete-task-button" name="delete-task-button" >
                                    <img draggable="false" width="15" height="15" src="<c:url value="/resources/delete-icon.png"/>" >
                                </button>
                            </c:if>
                            <div class="width34-form-block">
                                Автор: <strong>${task.author.login}</strong><br/>
                                Дата создания: <fmt:formatDate pattern="dd.MM.yyyy" value="${task.creationTime}" /><br/>
                                Дата выполнения: <fmt:formatDate pattern="dd.MM.yyyy" value="${task.targetTime}" /><br/>
                                ${task.completed ? 'Выполнено!' : 'Не выполнено!'}<br/>
                                Приоритет: ${task.priority}<br/>
                            </div>
                            <div class="width64-form-block">
                                ${task.content}
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
    </body>
</html>
