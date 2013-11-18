/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.todo.dao;

import java.util.List;
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
        int user_id = -1;
        if (null == sessionFactory.getCurrentSession().createQuery("from TodoUser where login = :name")
                .setParameter("name", user.getLogin()).uniqueResult()) {
            sessionFactory.getCurrentSession().save(user);
            user_id = user.getId();
        } else {
            System.out.println("User with this name already exists");
        }
        return user_id;
    }

    // Ищем в бд пользователя по логину и паролю.
    // Если пользователь найден - возвращаем его ID. Иначе возвращаем -1.
    @Override
    public int checkUserExists(TodoUser user) {
        int user_id = -1;
        TodoUser tmpUser = (TodoUser) sessionFactory.getCurrentSession().createQuery("from TodoUser where login = :name and password = :pass")
                .setParameter("name", user.getLogin())
                .setParameter("pass", user.getPassword()).uniqueResult();
        user_id = tmpUser.getId();
        return user_id;
    }

    @Override
    public TodoUser findUserById(int id) {
        return (TodoUser)sessionFactory.getCurrentSession().getNamedQuery("TodoUser.GetUserByID").setParameter("user_id", id).uniqueResult();
    }
}
