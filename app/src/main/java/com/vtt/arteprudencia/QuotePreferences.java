package com.vtt.arteprudencia;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by vtt on 10/05/17.
 */

public class QuotePreferences {
    private static final String PREF_LAST_QUOTE_ID = "last_quote_id";
    private static final String PREF_ALARM = "alarm_time";
    private static final String PREF_IS_ALARM_ON = "is_alarm_on";
    private static final int DEFAULT_ALARM_TIME = 1000 * 60; //1000 * 60 * 60 * 24; //A day as default

    public static void setLastQuoteId(Context context, int lastQuoteId) {
        if(lastQuoteId > 299 || lastQuoteId < 1) lastQuoteId = 1;
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putInt(PREF_LAST_QUOTE_ID, lastQuoteId).apply();
    }

    public static int getLastQuoteId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_LAST_QUOTE_ID, 1);
    }

    public static void setAlarm(Context context, int alarmNewTime) {
        //TODO check with an if() that the new time should be long enough and no too long
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putInt(PREF_ALARM, alarmNewTime);
    }

    public static int getAlarm(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_ALARM, DEFAULT_ALARM_TIME);
    }

    public static void setAlarmOn(Context context, boolean setOn) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putBoolean(PREF_IS_ALARM_ON, setOn);
    }

    public static boolean isAlarmOn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_IS_ALARM_ON, false);
    }
}
