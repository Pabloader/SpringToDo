package ru.todo.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    public List<TodoList> getPublicLists(TodoUser currentUser) {
        List<TodoList> lists = sessionFactory.getCurrentSession().getNamedQuery("TodoList.findByPubStatus").setParameter("author_id", currentUser.getId()).list();
        TodoList freeList = new TodoList(0, "Без категории", TodoList.STATUS_PRIVATE);
        List<TodoTask> freeTasks = todoTasksDAO.listFreeTasks();
        List<TodoTask> tasks = new ArrayList<TodoTask>();
        for (TodoTask task : freeTasks)
            if (task.getAuthor().getId() == currentUser.getId())
                tasks.add(task);
        freeList.setTasks(tasks);
        lists.add(freeList);
        return lists;
    }

    @Override
    public List<TodoList> getListsWithPublic(TodoUser user) {
        List<TodoList> lists = user.getLists();
        lists.addAll(this.getPublicLists(user));
        Comparator<TodoTask> priorityComparator = new Comparator<TodoTask>() {
            @Override
            public int compare(TodoTask t, TodoTask t1) {
                return t1.getPriority() - t.getPriority();
            }
        };
        for (TodoList list : lists)
            Collections.sort(list.getTasks(), priorityComparator);
        return lists;
    }
}
