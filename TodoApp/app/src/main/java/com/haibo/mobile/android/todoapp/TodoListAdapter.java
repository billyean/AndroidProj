package com.haibo.mobile.android.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.haibo.mobile.android.todoapp.data.TodoRepository;
import com.haibo.mobile.android.todoapp.model.Todo;
import com.haibo.mobile.android.todoapp.model.TodoGroup;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by hyan on 8/12/17.
 */

public class TodoListAdapter extends BaseExpandableListAdapter {
    private Context context;

    private List<String> headers;

    private HashMap<String, List<Todo>> todos;

    private TodoRepository repository;

    private TodoUpdateListener updateListener;

    public void setUpdateListener(TodoUpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public TodoListAdapter(Context context, List<String> headers, HashMap<String, List<Todo>> todos) {
        this.context = context;
        this.todos = todos;

        this.headers = new ArrayList<>();
        for (String header : headers) {
            List<Todo> todoListByHeader = todos.get(header);
            if (todoListByHeader != null && todoListByHeader.size() > 0) {
                this.headers.add(header);
            }
        }
        repository = TodoRepository.getRepository(context);
    }

    public void updateTodos(HashMap<String, List<Todo>> todos) {
        this.todos = todos;

        this.headers = new ArrayList<>();
        for (String header : headers) {
            List<Todo> todoListByHeader = todos.get(header);
            if (todoListByHeader != null && todoListByHeader.size() > 0) {
                this.headers.add(header);
            }
        }
    }

    @Override
    public int getGroupCount() {
        return headers.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return todos.get(headers.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        String headerTitle = headers.get(groupPosition);
        return new TodoGroup(headerTitle, todos.get(headerTitle));
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return todos.get(headers.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TodoGroup group = (TodoGroup) getGroup(groupPosition);

        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group, null);
        }

        TextView headView = (TextView) convertView.findViewById(R.id.groupHeader);
        headView.setText(group.getSectionHeader());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Todo todo = (Todo) getChild(groupPosition, childPosition);

        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, null);
        }

        CheckBox chkDone = (CheckBox) convertView.findViewById(R.id.chkDone);
        chkDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.updateTodoDone(todo.getId());
                updateListener.updateTodos();
            }
        });
//        if (todo.isDone()) {
//            chkDone.setChecked(true);
//        }

        TextView todoTitle = (TextView) convertView.findViewById(R.id.todoTitle);
        todoTitle.setText(todo.getTodoTitle());

        Locale locale = Locale.getDefault();
        TextView due = (TextView) convertView.findViewById(R.id.due);

        TodoGroup group = (TodoGroup) getGroup(groupPosition);
        String groupHeader = group.getSectionHeader();

        if (groupHeader.equals("Today") || groupHeader.equals("Tomorrow")) {
            DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
            due.setText(df.format(todo.getDue()));
        } else {
            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, locale);
            due.setText(df.format(todo.getDue()));
        }

        TextView tvPriority = (TextView) convertView.findViewById(R.id.priority);
        tvPriority.setText(todo.getPriority().toString());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public List<String> getHeaders(){   return headers; }
}
