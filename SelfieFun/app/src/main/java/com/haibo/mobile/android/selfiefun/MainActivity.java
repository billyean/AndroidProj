package com.haibo.mobile.android.selfiefun;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final long INTERVAL_TWO_MINUTES = 2 * 60 * 1000;

    private SelfieListAdapter adapter;

    private AlarmManager alarmMgr;

    private PendingIntent alarmIntent;

    private SelfieDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new SelfieDBHelper(this);

        List<Selfie> selfies = dbHelper.getAllSelfies();
        List<String> titles = new ArrayList<>();
        for (Selfie selfie: selfies) {
            titles.add(selfie.getImage_timestamp());
        }
        adapter = new SelfieListAdapter(getApplicationContext(), selfies, titles, dbHelper);
        ListView listView = (ListView)findViewById(R.id.selfieList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent selfieView = new Intent(getApplicationContext(), SelfieActivity.class);
                selfieView.putExtra(SelfieActivity.BITMAP_NAME, adapter.getBitmapByPosition(position));
                // Launch the Activity using the intent
                startActivity(selfieView);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        if (id == R.id.camera) {
            Intent takePictureIntent = new Intent(ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Date now = Calendar.getInstance().getTime();
            DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String current_timestamp = df.format(now);
            Selfie selfie = new Selfie(adapter.acquireNextID(), current_timestamp, imageBitmap);
            adapter.addNewSelfie(selfie);
        }
    }

    @Override
    protected void onPause() {
        Context context = getApplicationContext();
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, SelfieNowReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                INTERVAL_TWO_MINUTES,
                alarmIntent);
        super.onPause();
    }

    @Override
    protected void onResume() {
        Context context = getApplicationContext();
        NotificationManager mNotificationManager
                = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(SelfieNowReceiver.SELF_NOW_NOTIFICATION_ID);
        super.onResume();
    }
}
