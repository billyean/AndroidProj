package com.haibo.mobile.android.todoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.haibo.mobile.android.todoapp.data.TodoRepository;
import com.haibo.mobile.android.todoapp.model.Todo;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class TodoAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 245026355;

    @Override
    public void onReceive(Context context, Intent intent) {
        TodoRepository repository = TodoRepository.getRepository(context);

        try {
            List<Todo> expired = repository.toExpired(15);

            for (Todo todo: expired) {
                Intent aletServiceIntent = new Intent(context, TodoAlertService.class);
                aletServiceIntent.putExtra("todo", todo);
                context.startService(aletServiceIntent);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
