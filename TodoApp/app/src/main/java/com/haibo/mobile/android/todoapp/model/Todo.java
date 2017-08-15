package com.haibo.mobile.android.todoapp.model;

import java.util.Comparator;
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

    public int getId() {
        return id;
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

    public static class TodoComparator implements Comparator<Todo> {

        @Override
        public int compare(Todo o1, Todo o2) {
            int priComp = o1.getPriority().compareTo(o2.getPriority());
            if (priComp != 0) {
                return -priComp;
            }

            int dueComp = o1.getDue().compareTo(o2.getDue());
            if (dueComp != 0) {
                return dueComp;
            }

            return new Integer(o1.getId()).compareTo(o2.getId());
        }
    }
}
