package com.haibo.mobile.android.modernartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SeekBar bar = (SeekBar) findViewById(R.id.seekbar);
        final ImageView view1 = (ImageView) findViewById(R.id.view1);
        final ImageView view2 = (ImageView) findViewById(R.id.view2);
        final ImageView view3 = (ImageView) findViewById(R.id.view3);
        final ImageView view4 = (ImageView) findViewById(R.id.view4);
        final ImageView view5 = (ImageView) findViewById(R.id.view5);

        final int view1Start = ContextCompat.getColor(getApplicationContext(), R.color.blue_color);
        final int view2Start = ContextCompat.getColor(getApplicationContext(), R.color.pink_color);
        final int view3Start = ContextCompat.getColor(getApplicationContext(), R.color.red_color);
        final int view5Start = ContextCompat.getColor(getApplicationContext(), R.color.dark_blue_color);

        final int view1End = ContextCompat.getColor(getApplicationContext(), R.color.light_yellow_color);
        final int view2End = ContextCompat.getColor(getApplicationContext(), R.color.yellow_color);
        final int view3End = ContextCompat.getColor(getApplicationContext(), R.color.orange_color);
        final int view5End = ContextCompat.getColor(getApplicationContext(), R.color.green_color);

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                view1.setBackgroundColor(getProgressColor(view1Start, view1End, progress));
                view2.setBackgroundColor(getProgressColor(view2Start, view2End, progress));
                view3.setBackgroundColor(getProgressColor(view3Start, view3End, progress));
                view5.setBackgroundColor(getProgressColor(view5Start, view5End, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private int getProgressColor(int start, int end, int progress) {
        int startRed = (start & 0xFF0000) >>> 16;
        int startGreen = (start & 0x00FF00) >>> 8;
        int startBlue = start & 0x0000FF;

        int endRed = (end & 0xFF0000) >>> 16;
        int endGreen = (end & 0x00FF00) >>> 8;
        int endBlue = end & 0x0000FF;

        int currentRed = startRed + (int)((endRed - startRed) / 100.0 * progress);
        int currentGreen = startGreen + (int)((endGreen - startGreen) / 100.0 * progress);
        int currentBlue = startBlue + (int)((endBlue - startBlue) / 100.0 * progress);
        return Color.argb(0xFF, currentRed, currentGreen, currentBlue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_more_information) {
            if (dialog == null) {
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.check_moma);
            }
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void visitMoma(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://MoMA.org"));
        startActivity(intent);
    }

    public void notNow(View view) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
