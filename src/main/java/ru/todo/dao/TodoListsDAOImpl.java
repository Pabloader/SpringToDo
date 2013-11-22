package ru.todo.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author P@bloid
 */
@Repository
@Transactional
public class TodoListsDAOImpl implements TodoListsDAO {
    @Autowired
    private SessionFactory sessionFactory;
}
