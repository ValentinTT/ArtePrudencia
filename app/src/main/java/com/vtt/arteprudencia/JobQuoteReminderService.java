package com.vtt.arteprudencia;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by vtt on 10/05/17.
 */

public class JobQuoteReminderService extends JobService {
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

    private class ReminderAsyncTask extends AsyncTask<JobParameters, Void, Void>{

        @Override
        protected Void doInBackground(JobParameters... params) {
            return null;
        }
    }
}
