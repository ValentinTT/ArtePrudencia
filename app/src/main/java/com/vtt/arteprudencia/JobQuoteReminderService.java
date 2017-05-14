package com.vtt.arteprudencia;

import android.app.Activity;
import android.app.Notification;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;

/**
 * Created by vtt on 10/05/17.
 */

public class JobQuoteReminderService extends JobService {
    public static final String ACTION_SHOW_NOTIFICATION =
            "com.vtt.arteprudencia.SHOW_NOTIFICATION";
    public static final String PERM_PRIVATE =
            "com.vtt.arteprudencia.PRIVATE";
    public static final String REQUEST_CODE = "request_code";
    public static final String NOTIFICATION = "notification";

    private static final String DEBUG_TAG = "JobQuoteReminderService";
    private static final int JOB_ID = 1;
    private ReminderAsyncTask mTask;

    @Override
    public boolean onStartJob(JobParameters params) {
        mTask = new ReminderAsyncTask();
        mTask.execute(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if(mTask != null)
            mTask.cancel(true);
        return true;
    }

    public static boolean isJobScheduled(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(
                Context.JOB_SCHEDULER_SERVICE);
        for(JobInfo j: jobScheduler.getAllPendingJobs()) {
            if(j.getId() == JOB_ID) return true;
        }
        return false;
    }

    public static void scheduleService(Context context, boolean schedule) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(
                Context.JOB_SCHEDULER_SERVICE);
        if(!schedule) scheduler.cancel(JOB_ID);
        else {
            int periodicAlarm = QuotePreferences.getAlarm(context);
            JobInfo jobInfo = new JobInfo.Builder(
                    JOB_ID, new ComponentName(context,
                    JobQuoteReminderService.class))
                    .setPeriodic(periodicAlarm)
                    .setPersisted(true).build();
            scheduler.schedule(jobInfo);
        }
        QuotePreferences.setAlarmOn(context, schedule);
    }

    private void showBackgroundNotification(int requestCode, Notification n) {
        Intent intent = new Intent(ACTION_SHOW_NOTIFICATION);
        intent.putExtra(NOTIFICATION, n);
        intent.putExtra(REQUEST_CODE, requestCode);
        sendOrderedBroadcast(intent, PERM_PRIVATE, null, null, Activity.RESULT_OK, null, null);
    }

    private class ReminderAsyncTask extends AsyncTask<JobParameters, Void, Void>{

        @Override
        protected Void doInBackground(JobParameters... params) {
            int lastQuoteId = QuotePreferences.getLastQuoteId(getApplicationContext());


            Resources r = getResources();
            Notification n = new Notification.Builder(getApplicationContext())
                    .setTicker(r.getString(R.string.notification_tiker))
                    //TODO find a good icon for the notification
                    //.setSmallIcon(android.R.drawable.ic_popup_reminder)
                    .setContentTitle(r.getString(R.string.notification_title))
                    .setContentText(r.getString(R.string.notification_text))
                    //TODO add contentIntent when the UI is done.
                    .setContentIntent(null)
                    .setAutoCancel(true)
                    .build();

            showBackgroundNotification(0, n);

            JobParameters jobParameters = params[0];
            jobFinished(jobParameters, true);
            return null;
        }
    }
}
