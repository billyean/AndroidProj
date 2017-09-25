package com.haibo.mobile.android.nytimer.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.haibo.mobile.android.nytimer.R;
import com.haibo.mobile.android.nytimer.listeners.SearchUpdateListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Haibo(Tristan) Yan on 9/23/17.
 */
public class SettingFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private TextView tvBeginDate;

    private Spinner sSortOrder;

    private CheckBox cbArts;

    private CheckBox cbFashionStyle;

    private CheckBox cbSports;

    private Button btCancel;

    private Button btSave;

    private SearchUpdateListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container);

        tvBeginDate = (TextView)view.findViewById(R.id.tvBeginDate);
        sSortOrder = (Spinner)view.findViewById(R.id.sSortOrder);
        cbArts = (CheckBox)view.findViewById(R.id.cbArts);
        cbFashionStyle = (CheckBox)view.findViewById(R.id.cbFashionStyle);
        cbSports = (CheckBox)view.findViewById(R.id.cbSports);
        btCancel = (Button)view.findViewById(R.id.btCancel);
        btSave = (Button)view.findViewById(R.id.btSave);

        tvBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                FragmentManager fm = getFragmentManager();
                datePickerFragment.setTargetFragment(SettingFragment.this, 300);
                datePickerFragment.show(fm, "Pick Begin Date");
            }
        });

        SharedPreferences pref = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);

        if (pref != null) {
            String beginDate = pref.getString("beginDate", "");
            if (!beginDate.isEmpty()) {
                tvBeginDate.setText(beginDate);
            } else {
                DateFormat df = new SimpleDateFormat("YYYYMMdd");
                tvBeginDate.setText(df.format(Calendar.getInstance().getTime()));
            }
            String sortOrder = pref.getString("sortOrder", "");
            if (!sortOrder.isEmpty()) {
                if (sortOrder.equals("oldest")) {
                    sSortOrder.setSelection(0);
                } else {
                    sSortOrder.setSelection(1);
                }
            }

            boolean arts = pref.getBoolean("arts", false);
            boolean fashionStyle = pref.getBoolean("fashionStyle", false);
            boolean sports = pref.getBoolean("sports", false);
            cbArts.setChecked(arts);
            cbFashionStyle.setChecked(fashionStyle);
            cbSports.setChecked(sports);
        }

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingFragment.this.dismiss();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
                editor.putString("beginDate", tvBeginDate.getText().toString());
                editor.putString("sortOrder", sSortOrder.getSelectedItem().toString().toLowerCase());
                editor.putBoolean("arts", cbArts.isChecked());
                editor.putBoolean("fashionStyle", cbFashionStyle.isChecked());
                editor.putBoolean("sports", cbSports.isChecked());
                editor.apply();
                SearchUpdateListener listener = (SearchUpdateListener)getActivity();
                listener.updateSearchResult();
                SettingFragment.this.dismiss();
            }
        });

        return view;
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Be careful month is based on zero here
        tvBeginDate.setText(String.format("%4d%02d%02d", year, month + 1, dayOfMonth));
    }
}
