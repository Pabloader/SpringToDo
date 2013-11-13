package ru.todo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author P@bloid
 */
@Entity
@Table(name = "todo_tasks")
public class TodoTask implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private TodoUser author;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    private String title;

    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    private String content;

    @NotNull
    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @NotNull
    @Column(name = "target_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date targetTime;

    @NotNull
    private boolean completed;

    @NotNull
    private int priority;

    @NotNull
    @Column(name = "pub_status")
    private int pubStatus;

    public TodoTask() {
    }

    public TodoTask(Integer id) {
        this.id = id;
    }

    public TodoTask(Integer id, TodoUser author, String title, String content, Date creationTime, Date targetTime, boolean completed, int priority, int pubStatus) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.creationTime = creationTime;
        this.targetTime = targetTime;
        this.completed = completed;
        this.priority = priority;
        this.pubStatus = pubStatus;
    }

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

    public boolean getCompleted() {
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

    public int getPubStatus() {
        return pubStatus;
    }

    public void setPubStatus(int pubStatus) {
        this.pubStatus = pubStatus;
    }

    @Override
    public String toString() {
        return "TodoTask{" + "id=" + id + ", author=" + author + ", title=" + title + ", content=" + content + ", creationTime=" + creationTime + ", targetTime=" + targetTime + ", completed=" + completed + ", priority=" + priority + ", pubStatus=" + pubStatus + '}';
    }

}
