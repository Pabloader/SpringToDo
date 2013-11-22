package ru.todo.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.todo.model.TodoList;

/**
 *
 * @author P@bloid
 */
@Repository
@Transactional
public class TodoListsDAOImpl implements TodoListsDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<TodoList> getPublicLists() {
        return sessionFactory.getCurrentSession().getNamedQuery("TodoList.findByPubStatus").list(); 
    }
}
