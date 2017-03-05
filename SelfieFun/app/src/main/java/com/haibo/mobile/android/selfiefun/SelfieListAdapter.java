package com.haibo.mobile.android.selfiefun;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyan on 3/3/17.
 */

public class SelfieListAdapter extends ArrayAdapter<String> {
    private final Context context;

    private final List<Selfie> selfies;

    private final List<String> texts;

    private final SelfieDBHelper dbHelper;

    public SelfieListAdapter(Context context, List<Selfie> selfies, List<String> texts,
                             SelfieDBHelper dbHelper) {
        super(context, R.layout.list_main, texts);
        this.context = context;
        this.selfies = selfies;
        this.texts = texts;
        this.dbHelper = dbHelper;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View rowView= inflater.inflate(R.layout.list_main, null, true);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.selfie_pic);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.selfie_timestamp);
        ImageView deleteView = (ImageView) rowView.findViewById(R.id.delete);

        txtTitle.setText(selfies.get(position).getImage_timestamp());
        txtTitle.setGravity(Gravity.CENTER_VERTICAL);
        imageView.setImageBitmap(selfies.get(position).getBitmap());

        deleteView.setOnClickListener(new DeletePositionOnClickListener(this, position));
        return rowView;
    }

    public void addNewSelfie(Selfie selfie) {
        texts.add(selfie.getImage_timestamp());
        selfies.add(selfie);
        dbHelper.insert(selfie);
    }

    public void deleteSelfie(int position) {
        texts.remove(position);
        Selfie removed = selfies.remove(position);
        dbHelper.deleteSelfie(removed.getId());
        this.notifyDataSetChanged();
    }

    public Bitmap getBitmapByPosition(int position) {
        return selfies.get(position).getBitmap();
    }

    public String getTextByPosition(int position) {
        return selfies.get(position).getImage_timestamp();
    }

    public int acquireNextID() {
        int max = 0;
        for (Selfie selfie : selfies) {
            if (selfie.getId() > max) {
                max = selfie.getId();
            }
        }
        return max + 1;
    }

    private static class DeletePositionOnClickListener  implements View.OnClickListener {
        private final SelfieListAdapter adapter;

        private final int position;

        DeletePositionOnClickListener(SelfieListAdapter adapter, int position) {
            this.adapter = adapter;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            adapter.deleteSelfie(position);
        }
    }
}
