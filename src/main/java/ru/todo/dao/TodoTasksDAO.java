/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.todo.dao;

import java.util.List;
import ru.todo.model.TodoTask;

/**
 *
 * @author P@bloid
 */
public interface TodoTasksDAO {

    public void addTask(TodoTask task);

    public void deleteTask(TodoTask task);

    public TodoTask findTaskById(int id);

    public List<TodoTask> getAllPublicTasks();

}
