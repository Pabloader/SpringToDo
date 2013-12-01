package ru.todo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import ru.todo.dao.TodoUsersDAO;
import ru.todo.model.TodoUser;

/**
 *
 * @author P@bloid
 */
@Controller
public class LoginController {

    @Autowired
    private TodoUsersDAO todoUsersDAO;

    //Обрабока фейла
    @RequestMapping("/loginfailed")
    public String loginFailed(Model ui) {
        ui.addAttribute("error", "Введен неправильный логин/пароль!");
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
            ui.addAttribute("error", "Пользователь уже существует!");
            ui.addAttribute("newUser", user);
            return "register";
        }
        todoUsersDAO.addUser(user);
        return "redirect:/";
    }

}
