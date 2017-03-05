package com.haibo.mobile.android.selfiefun;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

/**
 * Created by hyan on 3/4/17.
 */

public class SelfieActivity extends AppCompatActivity {
    private ImageView vImage;

    protected static final String BITMAP_NAME = "selfie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selfie_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);

        vImage = (ImageView)findViewById(R.id.selfie_pic);

        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap)intent.getParcelableExtra(BITMAP_NAME);
        vImage.setImageBitmap(bitmap);
        vImage.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
