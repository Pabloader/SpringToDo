/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
