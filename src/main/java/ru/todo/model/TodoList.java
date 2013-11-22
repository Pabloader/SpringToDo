/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.todo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Sunrise
 */
@Entity
@Table(name = "todo_lists")
@NamedQueries({
    @NamedQuery(name = "TodoList.findAll", query = "SELECT t FROM TodoList t"),
    @NamedQuery(name = "TodoList.findById", query = "SELECT t FROM TodoList t WHERE t.id = :id"),
    @NamedQuery(name = "TodoList.findByTitle", query = "SELECT t FROM TodoList t WHERE t.title = :title"),
    @NamedQuery(name = "TodoList.findByPubStatus", query = "SELECT t FROM TodoList t WHERE t.pubStatus = :pubStatus")})
public class TodoList implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int STATUS_PRIVATE = 0;
    public static final int STATUS_PUBLIC_READ = 1;
    public static final int STATUS_PUBLIC_EDIT = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private TodoUser author;

    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "pub_status")
    private int pubStatus = STATUS_PRIVATE;

    public TodoList() {
    }

    public TodoList(Integer id) {
        this.id = id;
    }

    public TodoList(Integer id, String title, int pubStatus) {
        this.id = id;
        this.title = title;
        this.pubStatus = pubStatus;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPubStatus() {
        return pubStatus;
    }

    public void setPubStatus(int pubStatus) {
        this.pubStatus = pubStatus;
    }
//</editor-fold>

    @Override
    public String toString() {
        return "TodoList{" + "id=" + id + ", author=" + author + ", title=" + title + ", pubStatus=" + pubStatus + '}';
    }

}
