package ru.todo.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.todo.dao.TodoTasksDAO;
import ru.todo.model.TodoTask;

@Controller
public class RssController {
    @Autowired
    TodoTasksDAO todoTasksDAO;

    @RequestMapping(value = "/rss", method = RequestMethod.GET)
    public ModelAndView getFeedInRss() {

        List<TodoTask> items = todoTasksDAO.getAllPublicTasks();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("rssViewer");
        mav.addObject("todoList", items);

        return mav;

    }

}
