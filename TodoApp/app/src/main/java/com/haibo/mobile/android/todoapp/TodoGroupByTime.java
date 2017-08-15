package com.haibo.mobile.android.todoapp;

import com.haibo.mobile.android.todoapp.model.Todo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hyan on 8/12/17.
 */

public class TodoGroupByTime {
    public static final List<String> groups =  Arrays.asList("Overdue",
                "Today",
                "Tomorrow",
                "This week",
                "This month",
                "Others");

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMDD");

    private static final DateFormat WEEK_FORMAT = new SimpleDateFormat("yyyyww");

    private static final DateFormat MONTH_FORMAT = new SimpleDateFormat("yyyyMM");

    private static final DateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");

    public static HashMap<String, List<Todo>> groupTodos (List<Todo> todos) {
        HashMap<String, List<Todo>> map = new HashMap<>();

        for (Todo todo: todos) {
            Date due = todo.getDue();

            Date now = Calendar.getInstance().getTime();

            Calendar yCal = Calendar.getInstance();
            yCal.add(Calendar.DAY_OF_MONTH, 1);
            Date tomorrow = yCal.getTime();

            if (due.before(now)) {
                List<Todo> overdues = map.get("Overdue");
                if (null == overdues) {
                    overdues = new ArrayList<>();
                }
                overdues.add(todo);
                map.put("Overdue", overdues);
            } else if (DATE_FORMAT.format(due).equals(DATE_FORMAT.format(now))) {
                List<Todo> todays = map.get("Today");
                if (null == todays) {
                    todays = new ArrayList<>();
                }
                todays.add(todo);
                map.put("Today", todays);
            } else if (DATE_FORMAT.format(due).equals(DATE_FORMAT.format(tomorrow))) {
                List<Todo> tomorrows = map.get("Tomorrow");
                if (null == tomorrows) {
                    tomorrows = new ArrayList<>();
                }
                tomorrows.add(todo);
                map.put("Tomorrow", tomorrows);
            } else if (WEEK_FORMAT.format(due).equals(WEEK_FORMAT.format(now))) {
                List<Todo> weeks = map.get("This week");
                if (null == weeks) {
                    weeks = new ArrayList<>();

                }
                weeks.add(todo);
                map.put("This week", weeks);
            } else if (MONTH_FORMAT.format(due).equals(MONTH_FORMAT.format(now))) {
                List<Todo> months = map.get("This month");
                if (null == months) {
                    months = new ArrayList<>();
                }
                months.add(todo);
                map.put("This month", months);
            } else {
                List<Todo> others = map.get("Others");
                if (null == others) {
                    others = new ArrayList<>();
                }
                others.add(todo);
                map.put("Others", others);
            }
        }

        for (Map.Entry<String, List<Todo>> entry : map.entrySet()) {
            Comparator<Todo> comp = new Todo.TodoComparator();
            List<Todo> list = entry.getValue();
            Collections.sort(list, comp);
        }
        return map;
    }

}
