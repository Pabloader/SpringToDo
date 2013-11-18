package ru.todo.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String listTasks(Model ui) {
        ui.addAttribute("task", new TodoTask());
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
        int user_id = todoUsersDAO.checkUserExists(user);
        if (user_id != 0) {
            webRequest.setAttribute("user_id", user_id, 1);
            return "redirect:/todolist";
        } else {
            return "error";
        }
    }

    @RequestMapping("/register")
    public String register(Model ui) {
        ui.addAttribute("newUser", new TodoUser());
        return "register";
    }

    // Обработка POST-запроса из формы регистрации нового пользователя
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String registerNewUser(@ModelAttribute("newUser") TodoUser user, WebRequest webRequest, BindingResult result) {

            int user_id = todoUsersDAO.addUser(user);
            webRequest.setAttribute("user_id", user_id, 1);
            System.out.println("User: \n ID: " + user.getId() +"\nLogin:"
                    + user.getLogin() + "\nPassword:" + user.getPassword());
            return "redirect:/todolist";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTask(WebRequest webRequest, Model ui) {

        // Эта куча должна быть в сервисе, так? Но сервиса у нас пока нет
        // Значит пускай пока что здесь полежит

        // Получение параметров из запроса
        String taskTitle = webRequest.getParameter("taskTitle");
        String taskContent = webRequest.getParameter("taskContent");
        String targetDate = webRequest.getParameter("targetDate");
        int pubAccess = Integer.parseInt(webRequest.getParameter("pubAccess"));
        int taskPriority = Integer.parseInt(webRequest.getParameter("taskPriority"));
        // Получаем автора задачи и создаем текущую дату
        TodoUser author = todoUsersDAO.findUserById((Integer)webRequest.getAttribute("user_id", 1));
        Date tmpDate = new Date();

        // Парсим дату
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/mm/dd/hh/mm/ss");
            tmpDate = formatter.parse(targetDate);
        } catch (ParseException ex) {
            // Empty
        }
        // Создаем задачу
        TodoTask task = new TodoTask(null, author, taskTitle, taskContent, new Date(), tmpDate, false, taskPriority, pubAccess);
        // И пытаемся добавить в базу
        todoTaskDAO.addTask(task);

        // и не забыли получить обновленный список задач
        ui.addAttribute("tasksList", todoTaskDAO.listTasks(0));

        return "todolist";
    }

}
