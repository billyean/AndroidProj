package com.haibo.mobile.android.todoapp.model;

import com.haibo.mobile.android.todoapp.model.Todo;

import java.util.List;

/**
 * Created by hyan on 8/12/17.
 */

public class TodoGroup {
    private String sectionHeader;

    private List<Todo> todosByGroup;

    public TodoGroup(String sectionHeader, List<Todo> todosByGroup) {
        this.sectionHeader = sectionHeader;
        this.todosByGroup = todosByGroup;
    }

    public String getSectionHeader() {
        return sectionHeader;
    }

    public void setSectionHeader(String sectionHeader) {
        this.sectionHeader = sectionHeader;
    }

    public List<Todo> getTodosByGroup() {
        return todosByGroup;
    }

    public void setTodosByGroup(List<Todo> todosByGroup) {
        this.todosByGroup = todosByGroup;
    }
}
