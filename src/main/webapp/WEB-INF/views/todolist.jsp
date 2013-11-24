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
        <script type="text/javascript" src="<c:url value="/resources/script.js"/>" >
        </script>
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
                <div class="half-width-form">
                    <label for="task-title">Заголовок задачи</label>
                    <input type="text" id="task-title" placeholder="Заголовок..."/>
                    <label for="task-parent">Включить в список:</label>
                    <select id="task-parent">
                        <c:forEach items="${lists}" var="list">
                            <c:if test="${(list.author.id == author.id)||(list.pubStatus == 2)}">
                                <option value="${list.id}">${list.title}</option>
                            </c:if>
                        </c:forEach>    
                    </select>
                    <label for="task-title">Дата выполнения:</label>
                    <input type="text" id="task-target-date" placeholder="Введите дату..."/>
                    <label for="task-title">Приоритет:</label>
                    <input type="text" id="task-priority" placeholder="Укажите приоритет задачи..."/>
                </div>
                <div class="half-width-form">
                    <label for="task-content">Содержание задачи:</label>
                    <textarea id="task-content" rows="12" cols="25" id="task-content" value="task-content" placeholder="Содержание задачи..."></textarea>
                </div>
                <input type="button" class="agree-button" id="add-task-button" name="add-task-button" value="Сохранить задачу" />
            </form>
        </div>

        <!-- Списки задач, в каждом списке - по пиписке -->
        <h1>Здесь типа список списков, а внутри - списки доступных задач</h1>
        <c:forEach items="${lists}" var="list">
            <c:if test="${!empty list.tasks}" >
                <div class="task-list-div">
                    ${list.title}
                    <c:forEach items="${list.tasks}" var="task">
                        <div class="page-block task-block">
                            <c:if test="${author.id == task.author.id}">
                                <button data-id="${task.id}" class="delete-task-button" name="delete-task-button" >
                                    <img draggable="false" width="15" height="15" src="<c:url value="/resources/delete-icon.png"/>" >
                                </button>
                            </c:if>
                            <!-- Сверстать не по-мудацццки -->
                            Task ID:${task.id}<br/>
                            ${task.title}<br/>
                            Автор: ${task.author.login}<br/>
                            Дата создания: ${task.creationTime}<br/>
                            Дата выполнения: ${task.targetTime}<br/>
                            Коньтент: ${task.content}<br/>
                            Выполнено: ${task.completed}<br/>
                            Приоритет: ${task.priority}
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </c:forEach>
    </body>
</html>
