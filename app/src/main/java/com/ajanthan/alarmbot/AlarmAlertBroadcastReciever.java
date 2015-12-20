package com.ajanthan.alarmbot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ajanthan.alarmbot.Objects.Alarm;

/**
 * Created by ajanthan on 15-12-20.
 */
public class AlarmAlertBroadcastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,AlarmServiceBroadcastReciever.class);
        context.sendBroadcast(i, null);

        StaticWakeLock.lockOn(context);
        Bundle bundle = intent.getExtras();

        AlarmHelper alarmHelper= new AlarmHelper(null);
        final Alarm alarm = alarmHelper.getAlarm(bundle.getLong("alarm"),context);

        Intent alarmAlertActivityIntent = new Intent(context,AlarmAlertActivity.class);

        alarmAlertActivityIntent.putExtra("alarm",alarm.getKey());
        
        alarmAlertActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(alarmAlertActivityIntent);

    }
}
