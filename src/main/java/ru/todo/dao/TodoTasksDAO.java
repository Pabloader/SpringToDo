/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.todo.dao;

import java.util.List;
import ru.todo.model.TodoTask;
import ru.todo.model.TodoUser;

/**
 *
 * @author P@bloid
 */
public interface TodoTasksDAO {

    public void addTask(TodoTask task);
    public void deleteTask(TodoTask task);
    public List<TodoTask> listTasks(TodoUser user);
    public List<TodoTask> listFreeTasks();
    public TodoTask findTaskById(int id);

}
