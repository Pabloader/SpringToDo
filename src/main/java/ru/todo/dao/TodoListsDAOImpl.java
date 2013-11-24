package ru.todo.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.todo.model.TodoList;
import ru.todo.model.TodoTask;
import ru.todo.model.TodoUser;

/**
 *
 * @author P@bloid
 */
@Repository
@Transactional
public class TodoListsDAOImpl implements TodoListsDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private TodoTasksDAO todoTasksDAO;

    @Override
    public List<TodoList> getPublicLists() {
        List<TodoList> lists = sessionFactory.getCurrentSession().getNamedQuery("TodoList.findByPubStatus").list();

        TodoList freeList = new TodoList(0, "Без категории", TodoList.STATUS_PRIVATE);
        List<TodoTask> freeTasks = todoTasksDAO.listFreeTasks();
        freeList.setTasks(freeTasks);
        lists.add(freeList);
        return lists;
    }

    @Override
    public List<TodoList> getListsWithPublic(TodoUser user) {
        List<TodoList> lists = user.getLists();
        lists.addAll(this.getPublicLists());
        return lists;
    }
}
