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

import java.text.ParseException;
import java.util.Date;

import static com.haibo.mobile.android.todoapp.data.TodoDatabaseHelper.DATE_TIME_FORMAT;

public class NewTodoFragment extends DialogFragment {
    private EditText etTodo;

    private DatePicker dpDueDate;

    private TimePicker tpDueTime;

    private Spinner spPriority;

    private Button btnSave;

    private Button btnCancel;

    private TodoRepository repository;

    private CharSequence selectedPriority;

    public NewTodoFragment(){
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
        tpDueTime.setIs24HourView(true);
        spPriority = (Spinner) view.findViewById(R.id.spPriority);

        Priority[] priorities = Priority.values();
        String[] prioritiesString = new String[priorities.length];
        for (int i = 0; i < priorities.length; i++) {
            prioritiesString[i] = priorities[i].toString();
        }
        final ArrayAdapter<CharSequence> spPriorityAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.priority_array, android.R.layout.simple_spinner_item);
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

        btnSave = (Button) view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoTitle = etTodo.getText().toString();
                String dueDate = String.format("%04d%02d%02d", dpDueDate.getYear(), dpDueDate.getMonth() + 1, dpDueDate.getDayOfMonth());
                String dueTime= String.format("%02d%02d", tpDueTime.getCurrentHour(), tpDueTime.getCurrentMinute());
                try {
                    Date due = DATE_TIME_FORMAT.parse(dueDate + " " + dueTime);
                    repository.insertTodo(todoTitle, due, selectedPriority.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TodoUpdateListener listener = (TodoUpdateListener) getActivity();
                listener.updateTodos();
                NewTodoFragment.this.dismiss();
            }
        });

        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTodoFragment.this.dismiss();
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

    public static NewTodoFragment newInstance(String title) {
        NewTodoFragment fragment = new NewTodoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }
}
