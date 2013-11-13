package ru.todo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.todo.dao.TodoTasksDAO;

/**
 *
 * @author P@bloid
 */
@Controller
public class TasksController {
    @Autowired
    private TodoTasksDAO todoTaskDAO;
    
    @RequestMapping("/")
    public String home()
    {
       return "redirect:/index";
    }
    
    @RequestMapping("/index")
    public String listTasks(Model ui)
    {
        ui.addAttribute("tasksList", todoTaskDAO.listTasks(0));
        return "todolist";
    }
    

}
