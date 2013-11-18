package ru.todo.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
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

    // Основной редирект на главную страницу
    @RequestMapping("/")
    public String home() {
        return "redirect:/login";
    }

    // Заполняем модель бином задачи (для добавления новой) и выдаем список всех задач
    @RequestMapping("/todolist")
    public String listTasks(Model ui, WebRequest webRequest) {
        TodoTask todoTask = new TodoTask();
        TodoUser todoUser = todoUsersDAO.findUserById((Integer) webRequest.getAttribute("user_id", WebRequest.SCOPE_SESSION));
        todoTask.setAuthor(todoUser);
        ui.addAttribute("task", todoTask);
        ui.addAttribute("tasksList", todoTaskDAO.listTasks(0));
        return "todolist";
    }

    // Обработка перехода на форму входа в систему
    @RequestMapping("/login")
    public String login(Model ui) {
        ui.addAttribute("user", new TodoUser());
        return "login";
    }

    // Обработка POST-запроса из формы входа в систему
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String checkUserExists(@ModelAttribute("user") TodoUser user, WebRequest webRequest, BindingResult result) {
        int user_id = todoUsersDAO.getUserID(user);
        if (user_id != 0) {
            webRequest.setAttribute("user_id", user_id, WebRequest.SCOPE_SESSION);
            return "redirect:/todolist";
        } else
            return "error";
    }

    @RequestMapping("/register")
    public String register(Model ui) {
        ui.addAttribute("newUser", new TodoUser());
        return "register";
    }

    // Обработка POST-запроса из формы регистрации нового пользователя
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerNewUser(@ModelAttribute("newUser") TodoUser user, WebRequest webRequest, BindingResult result) {

        int user_id = todoUsersDAO.addUser(user);
        webRequest.setAttribute("user_id", user_id, WebRequest.SCOPE_SESSION);
        System.out.println("User: \n ID: " + user.getId() + "\nLogin:"
                           + user.getLogin() + "\nPassword:" + user.getPassword());
        return "redirect:/todolist";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("task") TodoTask task, WebRequest webRequest, Model ui) {

        TodoUser todoUser = todoUsersDAO.findUserById((Integer) webRequest.getAttribute("user_id", WebRequest.SCOPE_SESSION));
        task.setAuthor(todoUser);

        todoTaskDAO.addTask(task);
        // и не забыли получить обновленный список задач
        ui.addAttribute("tasksList", todoTaskDAO.listTasks(0));

        return "todolist";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
    }

}
