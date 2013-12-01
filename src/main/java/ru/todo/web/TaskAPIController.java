/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.todo.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import ru.todo.dao.TodoListsDAO;
import ru.todo.dao.TodoTasksDAO;
import ru.todo.model.TodoList;
import ru.todo.model.TodoTask;
import ru.todo.model.TodoUser;

/**
 *
 * @author Sunrise
 */
@Controller
@RequestMapping("/api")
public class TaskAPIController {

    @Autowired
    private TodoTasksDAO todoTasksDAO;
    @Autowired
    private TodoListsDAO todoListsDAO;

    @RequestMapping(value = "addTask", method = RequestMethod.POST)
    public @ResponseBody
    TodoTask addTask(@RequestParam String title, @RequestParam String content,
                     @RequestParam String targetTime, @RequestParam Integer priority,
                     @RequestParam Integer list, WebRequest webRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && (authentication.getPrincipal() instanceof User)) {
            TodoTask task = new TodoTask();
            TodoList todoList = todoListsDAO.findListById(list);
            if (todoList == null)
                return null;
            TodoUser todoUser = (TodoUser) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION);
            task.setAuthor(todoUser);
            task.setList(todoList);
            if (todoList.getAuthor().getId().intValue() != todoUser.getId().intValue()
                && !"ROLE_ADMIN".equals(todoUser.getRole())
                && todoList.getPubStatus() != TodoList.STATUS_PUBLIC_EDIT)
                return null;
            try {
                Date target = TodoController.DATE_FORMAT.parse(targetTime);
                task.setTargetTime(target);
            } catch (ParseException ex) {
                return null;
            }
            task.setTitle(title);
            task.setContent(content);
            task.setPriority(priority);
            todoTasksDAO.addTask(task);
            return task;
        }
        return null;
    }

    @RequestMapping(value = "editTask", method = RequestMethod.POST)
    public @ResponseBody
    TodoTask editTask(@RequestParam Integer id, @RequestParam String title, @RequestParam String content,
                      @RequestParam String targetTime, @RequestParam Integer priority, @RequestParam boolean completed,
                      @RequestParam Integer list, WebRequest webRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && (authentication.getPrincipal() instanceof User)) {
            TodoTask task = todoTasksDAO.findTaskById(id);
            if (task == null)
                return null;

            TodoUser todoUser = (TodoUser) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION);
            TodoList todoList = todoListsDAO.findListById(list);
            // Имеет ли право автор редактировать это сообщение
            if (task.getAuthor().getId().intValue() != todoUser.getId().intValue()
                && !"ROLE_ADMIN".equals(todoUser.getRole())
                && task.getList().getPubStatus() != TodoList.STATUS_PUBLIC_EDIT)
                return null;
            try {
                Date target = TodoController.DATE_FORMAT.parse(targetTime);
                task.setTargetTime(target);
            } catch (ParseException ex) {
                return null;
            }
            task.setPriority(priority);
            task.setTitle(title);
            task.setContent(content);
            task.setCompleted(completed);
            task.setList(todoList);
            todoTasksDAO.addTask(task);
            return task;
        }
        return null;
    }

    @RequestMapping(value = "deleteTask")
    public @ResponseBody
    String deleteTask(@RequestParam Integer id, WebRequest webRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && (authentication.getPrincipal() instanceof User)) {
            TodoUser user = (TodoUser) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION);
            TodoTask task = todoTasksDAO.findTaskById(id);
            if (task.getAuthor().getId().intValue() == user.getId().intValue()
                || task.getList().getPubStatus() == TodoList.STATUS_PUBLIC_EDIT
                || "ROLE_ADMIN".equals(user.getRole())) {
                todoTasksDAO.deleteTask(task);
                return "success";
            }
            return "access denied";
        }
        return "not authorised";
    }
}
