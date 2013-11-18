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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
            User user = (User)authentication.getPrincipal();
            TodoUser todoUser = todoUsersDAO.findUserByLogin(user.getUsername());
            webRequest.setAttribute("user", todoUser, WebRequest.SCOPE_SESSION);
            ui.addAttribute("task", new TodoTask());
            ui.addAttribute("tasksList", todoTaskDAO.listTasks(0));
            return "todolist";
        } else {
            return "error";
        }

    }

    //Обрабока фейла
    @RequestMapping("/loginfailed")
    public String loginFailed() {
        return "login";
    }

    // Обработка перехода на форму входа в систему
    @RequestMapping("/login")
    public String login(WebRequest wr) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && (authentication.getPrincipal() instanceof User)) {
            User user = (User) authentication.getPrincipal();
            TodoUser todoUser = todoUsersDAO.findUserByLogin(user.getUsername());
            wr.setAttribute("user", todoUser, WebRequest.SCOPE_SESSION);
            return "redirect:/todolist";
        }
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model ui) {
        ui.addAttribute("newUser", new TodoUser());
        return "register";
    }

    // Обработка POST-запроса из формы регистрации нового пользователя
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerNewUser(@ModelAttribute("newUser") TodoUser user, BindingResult result) {

        todoUsersDAO.addUser(user);
        //TODO Проверка на существование пользователя
        System.out.println("User: \n ID: " + user.getId() + "\nLogin:"
                           + user.getLogin() + "\nPassword:" + user.getPassword());
        return "redirect:/todolist";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("task") TodoTask task, WebRequest webRequest, Model ui) {

        TodoUser todoUser = (TodoUser) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION);
        task.setAuthor(todoUser);

        todoTaskDAO.addTask(task);

        return "redirect:/todolist";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}
