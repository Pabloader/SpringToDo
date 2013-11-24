/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.todo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Sunrise
 */
@Entity
@Table(name = "todo_tasks")
@NamedQueries({
    @NamedQuery(name = "TodoTask.findAll", query = "SELECT t FROM TodoTask t"),
    @NamedQuery(name = "TodoTask.findAllFree", query = "SELECT t FROM TodoTask t WHERE t.list IS null OR t.list.id = 0"),
    @NamedQuery(name = "TodoTask.findById", query = "SELECT t FROM TodoTask t WHERE t.id = :id"),
    @NamedQuery(name = "TodoTask.findByTitle", query = "SELECT t FROM TodoTask t WHERE t.title = :title"),
    @NamedQuery(name = "TodoTask.findByCompleted", query = "SELECT t FROM TodoTask t WHERE t.completed = :completed"),
    @NamedQuery(name = "TodoTask.findByPriority", query = "SELECT t FROM TodoTask t WHERE t.priority = :priority")})
public class TodoTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private TodoUser author;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private TodoList list;

    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "title")
    private String title;

    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime = new Date();

    @NotNull
    @Column(name = "target_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date targetTime;

    @NotNull
    @Column(name = "completed")
    private boolean completed = false;

    @Column(name = "priority")
    private int priority = 0;

    public TodoTask() {
    }

    public TodoTask(Integer id) {
        this.id = id;
    }

    public TodoTask(Integer id, TodoUser author, TodoList list, String title, String content, Date targetTime, int priority) {
        this.id = id;
        this.author = author;
        this.list = list;
        this.title = title;
        this.content = content;
        this.targetTime = targetTime;
        this.priority = priority;
    }

    //<editor-fold defaultstate="collapsed" desc="getset">
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TodoUser getAuthor() {
        return author;
    }

    public void setAuthor(TodoUser author) {
        this.author = author;
    }

    public TodoList getList() {
        return list;
    }

    public void setList(TodoList list) {
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(Date targetTime) {
        this.targetTime = targetTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    //</editor-fold>
    @Override
    public String toString() {
        return "TodoTask{" + "id=" + id + ", author=" + author + ", list=" + list + ", title=" + title + ", content=" + content + ", creationTime=" + creationTime + ", targetTime=" + targetTime + ", completed=" + completed + ", priority=" + priority + '}';
    }

}
