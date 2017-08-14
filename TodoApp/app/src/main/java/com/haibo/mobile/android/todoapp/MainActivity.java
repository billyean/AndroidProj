package com.haibo.mobile.android.todoapp;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.haibo.mobile.android.todoapp.model.Priority;
import com.haibo.mobile.android.todoapp.model.Todo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private TodoListAdapter listViewAdapter;

    private ExpandableListView listview;

    private List<Todo>  todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readData();

        List<String> header = TodoGroupByTime.groups;
        HashMap<String, List<Todo>> map = TodoGroupByTime.groupTodos(todos);
        listview = (ExpandableListView) findViewById(R.id.todoExpandableListView);
        listViewAdapter = new TodoListAdapter(MainActivity.this, header, map);
        listview.setAdapter(listViewAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newTodo:
                //Toast.makeText(this, "Add to do", LENGTH_SHORT).show();
                FragmentManager manager = getSupportFragmentManager();

                NewTodoFragment newTodoFragment = (NewTodoFragment)manager.findFragmentByTag("fragment_new_todo");

                if (null == newTodoFragment) {
                    newTodoFragment = NewTodoFragment.newInstance("Create a new to do");
                }
                newTodoFragment.show(manager, "fragment_new_todo");
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void readData() {
        todos = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyyMMdd hhmm");


        try {
            todos.add(new Todo("Test2", df.parse("20170813 1100"), Priority.High));
            todos.add(new Todo("Test1", df.parse("20170814 2300"), Priority.High));
            todos.add(new Todo("Test3", df.parse("20170814 2315"), Priority.High));
            todos.add(new Todo("Test4", df.parse("20170815 1200"), Priority.High));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}


