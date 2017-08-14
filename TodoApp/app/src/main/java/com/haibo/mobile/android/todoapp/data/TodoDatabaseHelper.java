package com.haibo.mobile.android.todoapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by hyan on 8/14/17.
 */

public class TodoDatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_TODO = "create table Todo ("
            + "id integer primary key autoincrement, "
            + "title text,"
            + "due text, "
            + "priority integer,"
            + "done integer)";

    private Context mContext;

    public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMdd hhmm");

    public TodoDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
