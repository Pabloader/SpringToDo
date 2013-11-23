package ru.todo.web;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.context.request.WebRequest;
import ru.todo.dao.TodoListsDAO;
import ru.todo.dao.TodoTasksDAO;
import ru.todo.dao.TodoUsersDAO;
import ru.todo.model.TodoTask;
import ru.todo.model.TodoUser;

/**
 *
 * @author P@bloid
 */
@Controller
public class LoginController {

    @Autowired
    private TodoTasksDAO todoTaskDAO;
    @Autowired
    private TodoUsersDAO todoUsersDAO;
    @Autowired
    private TodoListsDAO todoListsDAO;

    // Заполняем модель бином задачи (для добавления новой) и выдаем список всех задач
    @RequestMapping("/")
    public String home(Model ui, WebRequest webRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            TodoUser todoUser = todoUsersDAO.findUserByLogin(user.getUsername());
            webRequest.setAttribute("user", todoUser, WebRequest.SCOPE_SESSION);
            //TODO КОд ниже надо убрать, перевести на ОЙАКС
            ui.addAttribute("task", new TodoTask());
            ui.addAttribute("tasksList", todoUser.getLists());
            return "todolist";
        } else
            return "redirect:/login";
    }

    //Обрабока фейла
    @RequestMapping("/loginfailed")
    public String loginFailed(Model ui) {
        ui.addAttribute("error", "invalid.credetials");
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
            return "redirect:/";
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
    public String registerNewUser(@ModelAttribute("newUser") TodoUser user, BindingResult result, Model ui) {

        if (todoUsersDAO.findUserByLogin(user.getLogin()) != null) {
            ui.addAttribute("error", "user.exists");
            ui.addAttribute("newUser", user);
            return "register";
        }
        todoUsersDAO.addUser(user);
        return "redirect:/";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}
