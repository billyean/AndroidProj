package com.haibo.mobile.android.todoapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.haibo.mobile.android.todoapp.data.TodoRepository;
import com.haibo.mobile.android.todoapp.model.Todo;

import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoUpdateListener, GestureDetector.OnGestureListener {

    private TodoListAdapter listViewAdapter;

    private ExpandableListView listview;

    private List<Todo>  todos;

    private HashMap<String, List<Todo>> map;

    private TodoRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = TodoRepository.getRepository(getBaseContext());
        try {
            readData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<String> header = TodoGroupByTime.groups;
        map = TodoGroupByTime.groupTodos(todos);
        listview = (ExpandableListView) findViewById(R.id.todoExpandableListView);
        listViewAdapter = new TodoListAdapter(MainActivity.this, header, map);
        listview.setAdapter(listViewAdapter);
        listViewAdapter.setUpdateListener(this);
        scheduleAlarm();
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
                FragmentManager manager = getSupportFragmentManager();

                TodoFragment newTodoFragment = (TodoFragment)manager.findFragmentByTag("fragment_new_todo");

                if (null == newTodoFragment) {
                    newTodoFragment = TodoFragment.newInstance("Create a new to do");
                }
                newTodoFragment.show(manager, "fragment_new_todo");
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void readData() throws ParseException {
        todos = repository.allNotDone();
    }

    @Override
    public void updateTodos() {
        try {
            // Retrieve all expanded groups
            List<String> headers = listViewAdapter.getHeaders();
            HashSet<String> expandsGroups = new HashSet<>();
            for (int group = 0; group < headers.size(); group++) {
                if (listview.isGroupExpanded(group)) {
                    expandsGroups.add(headers.get(group));
                }
            }

            todos = repository.allNotDone();
            map = TodoGroupByTime.groupTodos(todos);
            headers = TodoGroupByTime.groups;
            listViewAdapter = new TodoListAdapter(MainActivity.this, headers, map);
            headers = listViewAdapter.getHeaders();
            listview.setAdapter(listViewAdapter);
            listViewAdapter.setUpdateListener(this);
            // Restore expanded groups
            for (int group = 0; group < headers.size(); group++) {
                String groupHeader = headers.get(group);
                if (expandsGroups.contains(groupHeader)) {
                    listview.expandGroup(group);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Toast.makeText(getBaseContext(), "Swipe", Toast.LENGTH_LONG).show();
        return false;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getApplicationContext().getResources()
                .getDisplayMetrics());
    }

    public void scheduleAlarm() {
        Intent intent = new Intent(getApplicationContext(), TodoAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, TodoAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                10 * 1000, pIntent);
    }
}


