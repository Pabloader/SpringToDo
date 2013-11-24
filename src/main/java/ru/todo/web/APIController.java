/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.todo.web;

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
public class APIController {

    @Autowired
    private TodoTasksDAO todoTasksDAO;
    @Autowired
    private TodoListsDAO todoListsDAO;

    @RequestMapping(value = "addTask", method = RequestMethod.POST)
    public @ResponseBody
    TodoTask addTask(@RequestParam String title, @RequestParam String content,
                     @RequestParam Date targetTime, @RequestParam Integer priority,
                     @RequestParam Integer list, WebRequest webRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && (authentication.getPrincipal() instanceof User)) {
            TodoTask task = new TodoTask();
            task.setTitle(title);
            task.setContent(content);
            task.setTargetTime(targetTime);
            task.setPriority(priority);
            TodoUser todoUser = (TodoUser) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION);
            task.setAuthor(todoUser);
            TodoList todoList = todoListsDAO.findListById(list);
            task.setList(todoList);
            todoTasksDAO.addTask(task);
            return task;
        }
        return null;
    }

    @RequestMapping(value = "deleteTask", method = RequestMethod.GET)
    public @ResponseBody
    String deleteTask(@RequestParam Integer id, WebRequest webRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && (authentication.getPrincipal() instanceof User)) {
            TodoUser user = (TodoUser) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION);
            TodoTask task = todoTasksDAO.findTaskById(id);
            if (task.getAuthor().getId() == user.getId() || "ROLE_ADMIN".equals(user.getRole())) {
                todoTasksDAO.deleteTask(task);
                return "success";
            }
        }
        return "error";
    }
}
