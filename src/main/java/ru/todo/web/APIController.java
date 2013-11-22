/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.todo.web;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import ru.todo.dao.TodoListsDAO;
import ru.todo.dao.TodoTasksDAO;
import ru.todo.dao.TodoUsersDAO;
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
    private TodoTasksDAO todoTaskDAO;
    @Autowired
    private TodoUsersDAO todoUsersDAO;
    @Autowired
    private TodoListsDAO todoListsDAO;

    @RequestMapping(value = "getLists", method = RequestMethod.GET)
    public @ResponseBody
    List<TodoList> getLists(WebRequest webRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && (authentication.getPrincipal() instanceof User)) {
            TodoUser todoUser = (TodoUser) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION);
            List<TodoList> lists = todoUser.getLists();
            lists.addAll(todoListsDAO.getPublicLists());
            return lists;
        }
        return null;
    }

    @RequestMapping(value = "addTask", method = RequestMethod.POST)
    public void addTask(@RequestBody TodoTask task, WebRequest webRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && (authentication.getPrincipal() instanceof User)) {
            TodoUser todoUser = (TodoUser) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION);
            task.setAuthor(todoUser);
            todoTaskDAO.addTask(task);
        }
    }

    @RequestMapping(value = "deleteTask", method = RequestMethod.GET)
    public void deleteTask(@RequestParam("id") Integer id, WebRequest webRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && (authentication.getPrincipal() instanceof User)) {
            TodoUser user = (TodoUser) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION);
            TodoTask task = todoTaskDAO.findTaskById(id);
            if (task.getAuthor().getId() == user.getId() || "ROLE_ADMIN".equals(user.getRole()))
                todoTaskDAO.deleteTask(task);
        }
    }
}
