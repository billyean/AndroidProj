package com.haibo.mobile.android.todoapp;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.haibo.mobile.android.todoapp.data.TodoRepository;
import com.haibo.mobile.android.todoapp.model.Todo;

import java.text.ParseException;
import java.util.List;

public class TodoAlertService extends IntentService {
    public TodoAlertService() {
        super("TodoAlertService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle bundle = intent.getExtras();
        Todo todo = (Todo) bundle.getSerializable("todo");

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("You have a todo to be expired!")
                .setContentText(todo.getTodoTitle())
                .setAutoCancel(true);
        mNotificationManager.notify(todo.getId(), builder.build());
    }
}
