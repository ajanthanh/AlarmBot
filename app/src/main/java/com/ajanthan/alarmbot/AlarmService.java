package com.ajanthan.alarmbot;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ajanthan.alarmbot.Objects.Alarm;
import com.ajanthan.alarmbot.Objects.RealmAlarm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ajanthan on 15-12-19.
 */
public class AlarmService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Alarm getNext(){
        Set<Alarm> alarmQueue = new TreeSet<>(new Comparator<Alarm>() {
            @Override
            public int compare(Alarm lhs, Alarm rhs) {

                long diff = AlarmHelper.getAlarmTime(lhs.getHour(),lhs.getMinute(),lhs.getRepeatWeekly(),lhs.getActiveDays()).getTimeInMillis() -
                        AlarmHelper.getAlarmTime(rhs.getHour(),rhs.getMinute(),rhs.getRepeatWeekly(),rhs.getActiveDays()).getTimeInMillis();
                Log.e("AlarmComp", AlarmHelper.getAlarmTime(lhs.getHour(),lhs.getMinute(),lhs.getRepeatWeekly(),lhs.getActiveDays()).get(Calendar.DAY_OF_MONTH)+" "+lhs.getHour()+":"+lhs.getMinute()+"  verses  "+
                        AlarmHelper.getAlarmTime(rhs.getHour(),rhs.getMinute(),rhs.getRepeatWeekly(),rhs.getActiveDays()).get(Calendar.DAY_OF_MONTH)+" "+rhs.getHour()+":"+rhs.getMinute());
                if(diff>0){
                    Log.e("AlarmComp", "G"+ AlarmHelper.getAlarmTime(lhs.getHour(),lhs.getMinute(),lhs.getRepeatWeekly(),lhs.getActiveDays()).get(Calendar.DAY_OF_MONTH)+" "+lhs.getHour()+":"+lhs.getMinute());
                    return 1;
                }else if (diff < 0){
                    Log.e("AlarmComp", "G"+ AlarmHelper.getAlarmTime(rhs.getHour(),rhs.getMinute(),rhs.getRepeatWeekly(),rhs.getActiveDays()).get(Calendar.DAY_OF_MONTH)+" "+rhs.getHour()+":"+rhs.getMinute());
                    return -1;
                }
                return 0;


            }
        });

        List<Alarm> alarms = new ArrayList<Alarm>();
        Realm realm= Realm.getInstance(getApplicationContext());
        RealmResults<RealmAlarm> result = realm.where(RealmAlarm.class)
                .findAll();
        for(int i =0; i<result.size();i++){
            alarms.add(result.get(i));
            Log.e("AlarmTester", alarms.get(i).getHour() + ":" + alarms.get(i).getMinute());
        }

        for(Alarm alarm : alarms){
            if(alarm.getState())
                alarmQueue.add(alarm);
        }
        if(alarmQueue.iterator().hasNext()){
            return alarmQueue.iterator().next();
        }else{
            return null;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Alarm alarm = getNext();
        Log.e("AlarmTester","Current: "+ alarm.getHour()+":"+alarm.getMinute());
        if (alarm!=null){
            AlarmHelper.schedule(getApplicationContext(),alarm.getKey());

        }
        else{
            Intent i = new Intent(getApplicationContext(), AlarmAlertBroadcastReciever.class);
            i.putExtra("alarm",alarm.getKey());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,i,PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager =(AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);

            alarmManager.cancel(pendingIntent);
        }

        return START_NOT_STICKY;
    }
}
