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
        <link rel="stylesheet" href="<c:url value="/resources/jquery-ui.css"/>" />
        <link rel="alternate" title="Spring TODO application" href="<c:url value="/rss"/>" type="application/rss+xml"/>

        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
        <script type="text/javascript" src="<c:url value="/resources/script.js"/>" ></script>
        <title>Todo list!</title>
    </head>
    <body>

        <header>
            <!-- TODO Сделать по человечески -->
            <button class="link-button" name="RSS" value="RSS">
                <a href="<c:url value="/rss"/>">Получить RSSинку</a>
            </button>

            <div id="userinfo">
                <sec:authorize access="isAuthenticated()">
                    Информация о вошедшем юзере
                    <b><sec:authentication property="principal.username" /></b>
                    <button class="disagree-button" name="logout" value="Выйти">
                        <a href="<c:url value="j_spring_security_logout" />">Выйти</a>
                    </button>
                </sec:authorize>
            </div>
        </header>

        <div class="page-block add-task-block">
            <h1>Создание задачи</h1>
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

        <div id="task-dialog" title="Изменение задачи">
            <h1>Изменение задачи</h1>
            <form id="task-add-form" method="POST" action="api/addTask">
                <div class="width34-form-block">
                    <label for="edit-task-title">Заголовок задачи</label>
                    <input type="text" id="edit-task-title" placeholder="Заголовок..."/>
                    <label for="edit-task-parent">Включить в список:</label>
                    <select id="edit-task-parent">
                        <c:forEach items="${lists}" var="list">
                            <c:if test="${(list.author.id == author.id)||(list.pubStatus == 2) || author.role=='ROLE_ADMIN'}">
                                <option value="${list.id}">${list.title}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <label for="edit-task-target-date">Дата выполнения:</label>
                    <input type="text" id="edit-task-target-date" placeholder="Введите дату..."/>
                    <label for="edit-task-priority">Приоритет:</label>
                    <input type="text" id="edit-task-priority" placeholder="Укажите приоритет задачи..."/>
                    <label for="edit-task-completed">Выполнено:</label>
                    <input type="checkbox" id="edit-task-completed">
                </div>
                <div class="width64-form-block">
                    <label for="task-content">Содержание задачи:</label>
                    <textarea id="edit-task-content" rows="12" cols="25" id="task-content" value="task-content" placeholder="Содержание задачи..."></textarea>
                </div>
                <input type="button" class="agree-button" id="edit-send-button" name="edit-task-button" value="Сохранить изменения..." />
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
                                <button data-id="${task.id}" class="edit-task-button" name="edit-task-button" >
                                    <img draggable="false" width="15" height="15" src="<c:url value="/resources/edit-icon.png"/>" >
                                </button>
                            </c:if>
                            <div class="width34-form-block">
                                Автор:
                                <span class="author">${task.author.login}</span><br/>
                                Дата создания:
                                <span class="creation-date"><fmt:formatDate pattern="dd.MM.yyyy" value="${task.creationTime}" /></span><br/>
                                Дата выполнения:<span class="target-date"><fmt:formatDate pattern="dd.MM.yyyy" value="${task.targetTime}" /></span><br/>
                                <span class="completed">${task.completed ? 'Выполнено!' : 'Не выполнено!'}</span><br/>
                                Приоритет:
                                <span class="priority">${task.priority}</span><br/>
                            </div>
                            <div class="width64-form-block">${task.content}</div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
    </body>
</html>
