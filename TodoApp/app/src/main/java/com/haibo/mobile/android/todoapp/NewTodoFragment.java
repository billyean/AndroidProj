package com.haibo.mobile.android.todoapp;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

public class NewTodoFragment extends DialogFragment {
    private EditText etTodo;

    private DatePicker dpDueDate;

    private TimePicker tpDueTime;

    private Spinner spPriority;

    private Button btnSave;

    private Button btnCancel;

    public NewTodoFragment(){}

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
        spPriority = (Spinner) view.findViewById(R.id.spPriority);

        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTodoFragment.this.dismiss();
            }
        });
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
