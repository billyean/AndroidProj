package com.haibo.mobile.android.todoapp.model;

import java.util.Date;

/**
 * Created by hyan on 8/12/17.
 */

public class Todo {
    private String todoTitle;

    private Date due;

    private Priority priority;

    private boolean done;

    public Todo(String todoTitle, Date due, Priority priority, boolean done) {
        this.todoTitle = todoTitle;
        this.due = due;
        this.priority = priority;
        this.done = done;
    }

    public Todo(String todoTitle, Date due, Priority priority) {
        this(todoTitle, due, priority, false);
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
