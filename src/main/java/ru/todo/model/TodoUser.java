package ru.todo.model;

import java.util.List;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author P@bloid
 */
@Entity
@Table(name = "todo_users")
public class TodoUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @Size(min = 1, max = 64)
    private String login;
    
    @NotNull
    @Size(min = 1, max = 64)
    private String password;
    
    @NotNull
    private int role;
    
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<TodoTask> tasks;

    public TodoUser() {
    }

    public TodoUser(Integer id) {
        this.id = id;
    }

    public TodoUser(Integer id, String login, String password, int role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public List<TodoTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<TodoTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "TodoUser{" + "id=" + id + ", login=" + login + ", password=" + password + ", role=" + role + '}';
    }
    

}
