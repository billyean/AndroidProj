package com.haibo.mobile.android.selfiefun;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * Created by hyan on 3/4/17.
 */

public class SelfieNowReceiver extends BroadcastReceiver {
    // Notification ID to allow for future updates
    public static final int SELF_NOW_NOTIFICATION_ID = 824097708;

    private static final String TAG = "SelfieNowReceiver";

    private final CharSequence contentTitle = "Notification";

    private Intent mNotificationIntent;

    private PendingIntent mContentIntent;

    RemoteViews mContentView = new RemoteViews(
            "com.haibo.mobile.android.selfiefun",
            R.layout.notification);

    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationIntent = new Intent(context, MainActivity.class);
        mContentIntent = PendingIntent.getActivity(context, 0,
                mNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.ic_menu_camera)
                        .setContentIntent(mContentIntent)
                        .setContent(mContentView);

        NotificationManager mNotificationManager
                = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(SELF_NOW_NOTIFICATION_ID,
                mBuilder.build());

    }
}
