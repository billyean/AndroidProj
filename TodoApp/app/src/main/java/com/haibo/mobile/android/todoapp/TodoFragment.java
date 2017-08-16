package com.haibo.mobile.android.todoapp;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.haibo.mobile.android.todoapp.data.TodoRepository;
import com.haibo.mobile.android.todoapp.model.Priority;
import com.haibo.mobile.android.todoapp.model.Todo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.haibo.mobile.android.todoapp.data.TodoDatabaseHelper.DATE_TIME_FORMAT;

public class TodoFragment extends DialogFragment {
    private EditText etTodo;

    private DatePicker dpDueDate;

    private TimePicker tpDueTime;

    private Spinner spPriority;

    private Button btnSave;

    private Button btnCancel;

    private TodoRepository repository;

    private CharSequence selectedPriority;

    public TodoFragment(){
        repository = TodoRepository.getRepository(this.getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_todo, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etTodo = (EditText) view.findViewById(R.id.etTodoTitle);
        dpDueDate = (DatePicker) view.findViewById(R.id.dpDueDate);
        tpDueTime = (TimePicker) view.findViewById(R.id.tpDueTime);
//        tpDueTime.setIs24HourView(true);
        spPriority = (Spinner) view.findViewById(R.id.spPriority);

        Priority[] priorities = Priority.values();
        String[] prioritiesString = new String[priorities.length];
        for (int i = 0; i < priorities.length; i++) {
            prioritiesString[i] = priorities[i].toString();
        }
        final ArrayAdapter<CharSequence> spPriorityAdapter
                = ArrayAdapter.createFromResource(this.getContext(), R.array.priority_array,
                        android.R.layout.simple_spinner_item);
        spPriorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(spPriorityAdapter);
        spPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPriority = spPriorityAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final int id = getArguments().getInt("id", -1);

        if (id > 0) {
            try {
                Todo todo = repository.todoById(id);
                etTodo.setText(todo.getTodoTitle());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(todo.getDue());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                dpDueDate.updateDate(year, month, day);
                tpDueTime.setCurrentHour(hour);
                tpDueTime.setCurrentMinute(minute);

                spPriority.setSelection(todo.getPriority().ordinal());

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        btnSave = (Button) view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoTitle = etTodo.getText().toString();
                String dueDate = String.format("%04d-%02d-%02d", dpDueDate.getYear(), dpDueDate.getMonth() + 1, dpDueDate.getDayOfMonth());
                String dueTime= String.format("%02d:%02d", tpDueTime.getCurrentHour(), tpDueTime.getCurrentMinute());
                try {
                    Date due = DATE_TIME_FORMAT.parse(dueDate + " " + dueTime);

                    if (id < 0) {
                        repository.insertTodo(todoTitle, due, selectedPriority.toString());
                    } else {
                        repository.updateTodo(id, todoTitle, due, selectedPriority.toString());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TodoUpdateListener listener = (TodoUpdateListener) getActivity();
                listener.updateTodos();
                TodoFragment.this.dismiss();
            }
        });

        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoFragment.this.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
         super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.new_todo_menu, menu);
    }

    public static TodoFragment newInstance(String title) {
        TodoFragment fragment = new TodoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static TodoFragment newInstance(String title, int todoId) {
        TodoFragment fragment = new TodoFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("id", todoId);
        fragment.setArguments(args);
        return fragment;
    }
}
