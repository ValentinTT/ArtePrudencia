package com.vtt.arteprudencia;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

public class StartUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isOn = QuotePreferences.isAlarmOn(context);
        JobQuoteReminderService.scheduleService(context, isOn);
    }
}
