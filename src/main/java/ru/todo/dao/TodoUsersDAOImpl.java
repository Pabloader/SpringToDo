/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.todo.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.todo.model.TodoUser;

/**
 *
 * @author Sunrise
 */
@Repository
@Transactional
public class TodoUsersDAOImpl implements TodoUsersDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addUser(TodoUser user) {
        if (null == sessionFactory.getCurrentSession().createQuery("from TodoUser where login = :name")
                .setParameter("name", user.getLogin()).uniqueResult()) {
            sessionFactory.getCurrentSession().save(user);
        } else System.out.println("User with this name already exists");
    }

    @Override
    public boolean checkUserExists(TodoUser user) {
        return (null != sessionFactory.getCurrentSession()
                .createQuery("from TodoUser where login = :name and password = :pass")
                .setParameter("name", user.getLogin())
                .setParameter("pass", user.getPassword()).uniqueResult());
    }

}
