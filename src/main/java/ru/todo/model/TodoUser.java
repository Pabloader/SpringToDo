/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.todo.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Sunrise
 */
@Entity
@Table(name = "todo_users")
@NamedQueries({
    @NamedQuery(name = "TodoUser.findAll", query = "SELECT t FROM TodoUser t"),
    @NamedQuery(name = "TodoUser.findById", query = "SELECT t FROM TodoUser t WHERE t.id = :id"),
    @NamedQuery(name = "TodoUser.findByLogin", query = "SELECT t FROM TodoUser t WHERE t.login = :login"),
    @NamedQuery(name = "TodoUser.findByPassword", query = "SELECT t FROM TodoUser t WHERE t.password = :password"),
    @NamedQuery(name = "TodoUser.findByRole", query = "SELECT t FROM TodoUser t WHERE t.role = :role")})
public class TodoUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "login")
    private String login;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "password")
    private String password;

    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "role")
    private String role = "ROLE_USER";

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<TodoTask> tasks;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<TodoList> lists;

    public TodoUser() {
    }

    public TodoUser(Integer id) {
        this.id = id;
    }

    public TodoUser(Integer id, String login, String password, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    //<editor-fold defaultstate="collapsed" desc="getset">
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<TodoTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<TodoTask> tasks) {
        this.tasks = tasks;
    }

    public List<TodoList> getLists() {
        return lists;
    }

    public void setLists(List<TodoList> lists) {
        this.lists = lists;
    }

    //</editor-fold>
    @Override
    public String toString() {
        return "TodoUser{" + "id=" + id + ", login=" + login + ", role=" + role + '}';
    }

}
