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

    // Проверяем, есть ли в БД пользователь с заданными логином и паролем.
    // Если нет - вставляем такого пользователя в БД и возвращаем его ID
    // Иначе возвращаем ID = -1
    @Override
    public int addUser(TodoUser user) {
        int userID = -1;
        if (findUserByLogin(user.getLogin()) == null) {
            sessionFactory.getCurrentSession().save(user);
            userID = user.getId();
        } else
            System.out.println("User with this name already exists");
        return userID;
    }
    
    @Override
    public TodoUser findUserById(int id) {
        return (TodoUser) sessionFactory.getCurrentSession().getNamedQuery("TodoUser.findById").setParameter("id", id).uniqueResult();
    }
    
    @Override
    public TodoUser findUserByLogin(String username) {
        return (TodoUser) sessionFactory.getCurrentSession().getNamedQuery("TodoUser.findByLogin").setParameter("login", username).uniqueResult();
    }
}
