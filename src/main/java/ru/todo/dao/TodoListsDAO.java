/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.todo.dao;

import java.util.List;
import ru.todo.model.TodoList;

/**
 *
 * @author P@bloid
 */
public interface TodoListsDAO {

    public List<TodoList> getPublicLists();
    
}
