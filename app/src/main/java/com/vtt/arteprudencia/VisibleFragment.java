package com.vtt.arteprudencia;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;

/**
 * Created by vtt on 13/05/17.
 */

public abstract class VisibleFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(mNotificationReceiver,
                new IntentFilter(JobQuoteReminderService.ACTION_SHOW_NOTIFICATION),
                JobQuoteReminderService.PERM_PRIVATE, null);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mNotificationReceiver);
    }

    private BroadcastReceiver mNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setResultCode(Activity.RESULT_CANCELED);
        }
    };
}
