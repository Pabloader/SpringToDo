/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.todo.web;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

   @RequestMapping("pipizka")
   public @ResponseBody TodoUser getRandom() {
       return todoUsersDAO.findUserByLogin("admin");
   }

}
