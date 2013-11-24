package ru.todo.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.todo.model.TodoTask;
import ru.todo.model.TodoUser;

/**
 *
 * @author P@bloid
 */
@Repository
@Transactional
public class TodoTasksDAOImpl implements TodoTasksDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addTask(TodoTask task) {
        sessionFactory.getCurrentSession().save(task);
    }

    @Override
    public List<TodoTask> listTasks(TodoUser user) {
        return sessionFactory.getCurrentSession().createQuery("from TodoTask "
                                                              + "where author_id = :userID "
                                                              + "order by priority desc creationDate asc")
                .setParameter("userID", user.getId()).list();
    }

    @Override
    public void deleteTask(TodoTask task) {
        sessionFactory.getCurrentSession().delete(task);
    }

    @Override
    public TodoTask findTaskById(int id) {
        return (TodoTask) sessionFactory.getCurrentSession().getNamedQuery("TodoTask.findById").setParameter("id", id).uniqueResult();
    }

    @Override
    public List<TodoTask> listFreeTasks() {
        return sessionFactory.getCurrentSession().getNamedQuery("TodoTask.findAllFree").list();
    }

}
