package com.ajanthan.alarmbot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ajanthan.alarmbot.Activity.AlarmAlertActivity;
import com.ajanthan.alarmbot.Objects.Alarm;

/**
 * Created by ajanthan on 15-12-20.
 */
public class AlarmAlertBroadcastReciever extends BroadcastReceiver {

    private final static String PREF_ALARM_KEY= "alarm";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AlarmServiceBroadcastReciever.class);
        context.sendBroadcast(i, null);

        Bundle bundle = intent.getExtras();

        final Alarm alarm = AlarmHelper.getAlarm(bundle.getLong(PREF_ALARM_KEY), context);

        Intent alarmAlertActivityIntent = new Intent(context, AlarmAlertActivity.class);

        alarmAlertActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        alarmAlertActivityIntent.putExtra(PREF_ALARM_KEY, alarm.getKey());

        alarmAlertActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(alarmAlertActivityIntent);

    }
}
