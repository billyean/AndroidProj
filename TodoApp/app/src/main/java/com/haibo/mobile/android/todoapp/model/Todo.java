package com.haibo.mobile.android.todoapp.model;

import java.util.Date;

/**
 * Created by hyan on 8/12/17.
 */

public class Todo {
    private final int id;

    private final String todoTitle;

    private final Date due;

    private final Priority priority;

    private boolean done;

    public Todo(int id, String todoTitle, Date due, Priority priority, boolean done) {
        this.id = id;
        this.todoTitle = todoTitle;
        this.due = due;
        this.priority = priority;
        this.done = done;
    }

    public Todo(int id, String todoTitle, Date due, Priority priority) {
        this(id, todoTitle, due, priority, false);
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public Date getDue() {
        return due;
    }

    public Priority getPriority() {
        return priority;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
