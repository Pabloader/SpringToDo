package ru.todo.dao;

import ru.todo.model.TodoUser;

/**
 *
 * @author Sunrise
 */
public interface TodoUsersDAO {

    public int addUser(TodoUser user);

    public TodoUser findUserById(int id);

    public TodoUser findUserByLogin(String username);

}
