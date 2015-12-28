package com.ajanthan.alarmbot;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ajanthan.alarmbot.Objects.Alarm;
import com.ajanthan.alarmbot.Objects.RealmAlarm;

import java.util.ArrayList;
import java.util.Arrays;
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

public class AlarmHelper {

//    Return Calendar of set to the next active alarm time and date

    static public Calendar getAlarmTime(int hour, int minute, Boolean repeatWeekly, String activeDaysString) {
        Log.e("AlarmI",hour+": "+minute);
        Calendar alarmTime = getCalendarAlarmTime(hour, minute);
        Boolean[] activeDays = getActiveDays(activeDaysString);
        if (alarmTime.before(Calendar.getInstance())) {
            alarmTime.add(Calendar.DAY_OF_MONTH, 1);
            Log.e("AlarmLess", Calendar.getInstance().toString()+"  ||  "+alarmTime.toString());
        }

        for (int i = alarmTime.get(Calendar.DAY_OF_WEEK) - 1; i < 7 && activeDays[i] == false; i++) {
            Log.e("AlarmMore", alarmTime.get(Calendar.DAY_OF_WEEK) + " | " + i);
            alarmTime.add(Calendar.DAY_OF_MONTH, 1);
            if (i == 6 && repeatWeekly) {
                for (int j = 0; activeDays[j] == false && j < 7; j++) {
                    alarmTime.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
        }
        return alarmTime;
    }

    static public void schedule(Context context, long key) {
        Intent i = new Intent(context, AlarmAlertBroadcastReciever.class);
        i.putExtra("alarm", key);

        Alarm alarm = getAlarm(key, context);

        Log.e("AlarmSc",alarm.getHour()+":"+alarm.getMinute());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, getAlarmTime(
                alarm.getHour(),alarm.getMinute(),alarm.getRepeatWeekly(),alarm.getActiveDays()).getTimeInMillis(), pendingIntent);
    }

    static public String getTimeUntilNextAlarmMessage(int hour, int minute, String amPm, Boolean repeatWeekly, String activeDaysString) {
        long timeDifference = getAlarmTime(hour, minute, repeatWeekly, activeDaysString).getTimeInMillis() - System.currentTimeMillis();
        long days = timeDifference / (1000 * 60 * 60 * 24);
        long hours = timeDifference / (1000 * 60 * 60) - (days * 24);
        long minutes = timeDifference / (1000 * 60) - (days * 24 * 60) - (hours * 60);
        long seconds = timeDifference / (1000) - (days * 24 * 60 * 60) - (hours * 60 * 60) - (minutes * 60);
        String alert = "Alarm will sound in ";
        if (days > 0) {
            alert += String.format(
                    "%d days, %d hours, %d minutes and %d seconds", days,
                    hours, minutes, seconds);
        } else {
            if (hours > 0) {
                alert += String.format("%d hours, %d minutes and %d seconds",
                        hours, minutes, seconds);
            } else {
                if (minutes > 0) {
                    alert += String.format("%d minutes, %d seconds", minutes,
                            seconds);
                } else {
                    alert += String.format("%d seconds", seconds);
                }
            }
        }
        return alert;
    }

//    Returns true if there is a possible active alarm, and false if alarm has expired

    static public Boolean isAlarmValid(int hour, int minute, String amPm, Boolean repeatWeekly, String activeDaysString) {
        if (repeatWeekly) {
            return true;
        } else {
            Boolean[] activeDays = getActiveDays(activeDaysString);
            Calendar alarmTime = getCalendarAlarmTime(hour, minute);
            int lastDayOfWeek = 0;

            for (int i = 0; i < 7; i++) {
                if (activeDays[i]) {
                    lastDayOfWeek = i;
                }
            }
            if (alarmTime.get(Calendar.DAY_OF_WEEK) < lastDayOfWeek + 1) {
                return true;
            } else if (alarmTime.get(Calendar.DAY_OF_WEEK) == lastDayOfWeek + 1) {
                if (alarmTime.get(Calendar.HOUR_OF_DAY) < Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
                    return true;
                } else if (alarmTime.get(Calendar.HOUR_OF_DAY) == Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
                    if (alarmTime.get(Calendar.MINUTE) < Calendar.getInstance().get(Calendar.MINUTE)) {
                        return true;
                    } else if (alarmTime.get(Calendar.MINUTE) == Calendar.getInstance().get(Calendar.MINUTE)) {
                        if (alarmTime.get(Calendar.SECOND) < Calendar.getInstance().get(Calendar.SECOND)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    static private Calendar getCalendarAlarmTime(int hour, int minute) {
        Calendar alarmTime = Calendar.getInstance();

        alarmTime.set(Calendar.HOUR_OF_DAY, hour);
        alarmTime.set(Calendar.MINUTE, minute);
        alarmTime.set(Calendar.SECOND, 0);

        return alarmTime;

    }

    static private Boolean[] getActiveDays(String activeDaysString) {
        String activeDaysAsString = activeDaysString;
        Boolean[] activeDays = new Boolean[7];
        Arrays.fill(activeDays, Boolean.FALSE);

        if (activeDaysAsString.contains("SUN")) activeDays[0] = true;
        if (activeDaysAsString.contains("MON")) activeDays[1] = true;
        if (activeDaysAsString.contains("TUE")) activeDays[2] = true;
        if (activeDaysAsString.contains("WED")) activeDays[3] = true;
        if (activeDaysAsString.contains("THU")) activeDays[4] = true;
        if (activeDaysAsString.contains("FRI")) activeDays[5] = true;
        if (activeDaysAsString.contains("SAT")) activeDays[6] = true;

        return activeDays;
    }

    static public Alarm getAlarm(long key, Context context) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmAlarm> result = realm.where(RealmAlarm.class)
                .equalTo("key", key)
                .findAll();
        realm.close();
        return result.get(0);
    }

    static public void callAlarmScheduleService(Context context) {
        Intent mathAlarmServiceIntent = new Intent(context, AlarmServiceBroadcastReciever.class);
        context.sendBroadcast(mathAlarmServiceIntent, null);
    }

    static public Alarm getNext(){
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
        Realm realm= Realm.getDefaultInstance();
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

}
