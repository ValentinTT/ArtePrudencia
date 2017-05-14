package com.vtt.arteprudencia;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String DEBUG_TAG = "NotificationReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(getResultCode() != Activity.RESULT_OK) { //Now the user is in the app
            Log.i(DEBUG_TAG, "The user is in the app");
            return;
        }
        NotificationManagerCompat managerCompat =
                NotificationManagerCompat.from(context);
        Notification notification = intent.getParcelableExtra(
                JobQuoteReminderService.NOTIFICATION);
        int requestCode = intent.getIntExtra(JobQuoteReminderService.REQUEST_CODE, 0);
        managerCompat.notify(requestCode, notification);
    }
}
