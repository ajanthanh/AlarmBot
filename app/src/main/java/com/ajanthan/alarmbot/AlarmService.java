package com.ajanthan.alarmbot;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.ajanthan.alarmbot.Objects.Alarm;
import com.ajanthan.alarmbot.Objects.RealmAlarm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajanthan on 15-12-19.
 */
public class AlarmService extends Service {

    public List<Alarm> alarms = new ArrayList<Alarm>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Alarm alarm = AlarmHelper.getNext();
        if (alarm != null && alarm.getState() == true) {
            Log.e("AlarmTester", "Current: " + alarm.getHour() + ":" + alarm.getMinute());
            AlarmHelper.schedule(getApplicationContext(), alarm.getKey());

        } else {
            Intent i = new Intent(getApplicationContext(), AlarmAlertBroadcastReciever.class);
            i.putExtra("alarm", new RealmAlarm().getKey());
            Toast.makeText(getApplicationContext(), "No Active Alarms", Toast.LENGTH_LONG).show();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

            alarmManager.cancel(pendingIntent);
        }
        return START_NOT_STICKY;
    }
}
