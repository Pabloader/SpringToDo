/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.todo.web;

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
import ru.todo.model.TodoList;
import ru.todo.model.TodoUser;

/**
 *
 * @author Sunrise
 */
@Controller
@RequestMapping("/api")
public class ListAPIController {

    @Autowired
    private TodoListsDAO todoListsDAO;

    @RequestMapping(value = "addList", method = RequestMethod.POST)
    public @ResponseBody
    TodoList addList(@RequestParam String title, @RequestParam int pubStatus, WebRequest webRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && (authentication.getPrincipal() instanceof User)) {
            TodoList list = new TodoList();
            list.setTitle(title);
            list.setPubStatus(pubStatus);
            TodoUser todoUser = (TodoUser) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION);
            list.setAuthor(todoUser);
            todoListsDAO.addList(list);
            return list;
        }
        return null;
    }

    @RequestMapping(value = "editList", method = RequestMethod.POST)
    public @ResponseBody
    TodoList editList(@RequestParam Integer id, @RequestParam String title,
                      @RequestParam int pubStatus, WebRequest webRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && (authentication.getPrincipal() instanceof User)) {
            TodoList list = todoListsDAO.findListById(id);
            if (list == null)
                return null;

            TodoUser todoUser = (TodoUser) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION);
            if (list.getAuthor().getId().intValue() != todoUser.getId().intValue()
                && !"ROLE_ADMIN".equals(todoUser.getRole()))
                return null;
            list.setTitle(title);
            list.setPubStatus(pubStatus);
            todoListsDAO.addList(list);
            return list;
        }
        return null;
    }

    @RequestMapping(value = "deleteList")
    public @ResponseBody
    String deleteList(@RequestParam Integer id, WebRequest webRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && (authentication.getPrincipal() instanceof User)) {
            TodoUser user = (TodoUser) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION);
            TodoList list = todoListsDAO.findListById(id);
            if (list.getAuthor().getId().intValue() == user.getId().intValue()
                || "ROLE_ADMIN".equals(user.getRole())) {
                todoListsDAO.deleteList(list);
                return "success";
            }
        }
        return "error";
    }
}
