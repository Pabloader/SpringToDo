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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import ru.todo.dao.TodoListsDAO;
import ru.todo.dao.TodoUsersDAO;
import ru.todo.model.TodoUser;

/**
 *
 * @author P@bloid
 */
@Controller
public class TodoController {
    /**
     * Формат даты, используемый в приложении
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
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
            ui.addAttribute("lists", todoListsDAO.getListsWithPublic(todoUser));
            ui.addAttribute("author", todoUser);
            return "todolist";
        } else
            return "redirect:/login";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(DATE_FORMAT, false));
    }

}
