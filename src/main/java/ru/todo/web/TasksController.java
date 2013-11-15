package ru.todo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.todo.dao.TodoTasksDAO;
import ru.todo.dao.TodoUsersDAO;
import ru.todo.model.TodoTask;
import ru.todo.model.TodoUser;

/**
 *
 * @author P@bloid
 */
@Controller
@SessionAttributes
public class TasksController {

    @Autowired
    private TodoTasksDAO todoTaskDAO;
    @Autowired
    private TodoUsersDAO todoUsersDAO;

    @RequestMapping("/")
    public String home()
    {
       return "redirect:/todolist";
    }

    @RequestMapping("/login")
    public String login(Model ui) {
        ui.addAttribute("user", new TodoUser());
        return "login";
    }

    @RequestMapping("/todolist")
    public String listTasks(Model ui)
    {
        ui.addAttribute("task", new TodoTask());
        ui.addAttribute("tasksList", todoTaskDAO.listTasks(0));
        return "todolist";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String checkUserExists(@ModelAttribute("user") TodoUser user, BindingResult result) {

        if (todoUsersDAO.checkUserExists(user))
            return "redirect:/todolist";
        else
            return "error";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addTask(@ModelAttribute("task") TodoTask task, BindingResult result) {

        todoTaskDAO.addTask(task);
        return "todolist";
        
    }


}
