package com.haibo.mobile.android.nytimer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Haibo(Tristan) Yan on 9/23/17.
 */

public class SettingFragment extends DialogFragment {
    private EditText etBeginDate;

    private Spinner sSortOrder;

    private CheckBox cbArts;

    private CheckBox cbFashionStyle;

    private CheckBox cbSports;

    private Button btSave;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container);

        etBeginDate = view.findViewById(R.id.etBeginDate);
        sSortOrder = view.findViewById(R.id.sSortOrder);
        cbArts = view.findViewById(R.id.cbArts);
        cbFashionStyle = view.findViewById(R.id.cbFashionStyle);
        cbSports = view.findViewById(R.id.cbSports);
        btSave = view.findViewById(R.id.btSave);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getContext().getSharedPreferences("setting", Context.MODE_PRIVATE).edit();
                editor.putString("beginDate", etBeginDate.getText().toString());
                editor.putString("sortOrder", sSortOrder.toString());
                editor.putBoolean("arts", cbArts.isChecked());
                editor.putBoolean("fashionStyle", cbFashionStyle.isChecked());
                editor.putBoolean("sports", cbSports.isChecked());
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
}
