package ru.todo.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.todo.model.TodoTask;

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
    public List<TodoTask> listTasks(int access) {
        return sessionFactory.getCurrentSession().createQuery("from TodoTask").list();
    }

}
