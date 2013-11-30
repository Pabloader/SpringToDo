/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.todo.dao;

import java.util.List;
import ru.todo.model.TodoList;
import ru.todo.model.TodoUser;

/**
 *
 * @author P@bloid
 */
public interface TodoListsDAO {

    public List<TodoList> getPublicLists(TodoUser currentUser);

    public List<TodoList> getPublicLists();

    public List<TodoList> getListsWithPublic(TodoUser user);

    public TodoList findListById(int id);

    public void addList(TodoList list);

    public void deleteList(TodoList list);

}
