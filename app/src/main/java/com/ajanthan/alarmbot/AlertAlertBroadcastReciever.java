package com.ajanthan.alarmbot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ajanthan on 15-12-19.
 */
public class AlertAlertBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,AlarmService.class);
        context.startService(i);
    }
}
