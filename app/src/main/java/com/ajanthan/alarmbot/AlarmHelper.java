package com.ajanthan.alarmbot;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ajanthan.alarmbot.Objects.Alarm;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by ajanthan on 15-12-19.
 */

public class AlarmHelper {

    private final Alarm alarm;

    public AlarmHelper(Alarm alarm){
        this.alarm=alarm;
    }

//    Return Calendar of set to the next active alarm time and date

    public Calendar getAlarmTime(){
        Calendar alarmTime = getCalendarAlarmTime();
        Boolean[] activeDays=getActiveDays();
        if (alarmTime.before(Calendar.getInstance()))
            alarmTime.add(Calendar.DAY_OF_MONTH, 1);

//        Log.e("Alarm",alarmTime.get(Calendar.DAY_OF_WEEK)+"");

        for (int i = alarmTime.get(Calendar.DAY_OF_WEEK) - 1;i < 7 && activeDays[i] == false; i++) {
            Log.e("Alarm",alarmTime.get(Calendar.DAY_OF_WEEK)+" | "+i);
            alarmTime.add(Calendar.DAY_OF_MONTH, 1);
            if (i == 6 && alarm.getRepeatWeekly()) {
                for (int j = 0; activeDays[j] == false && j < 7; j++) {
                    alarmTime.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
        }
        return alarmTime;
    }

    public void schedule(Context context){
        Intent i = new Intent(context,AlertAlertBroadcastReciever.class);
        i.putExtra("alarm", alarm.getKey());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP,getAlarmTime().getTimeInMillis(),pendingIntent);
    }

    public String getTimeUntilNextAlarmMessage(){
        long timeDifference = getAlarmTime().getTimeInMillis() - System.currentTimeMillis();
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

    public Boolean isAlarmValid(){
        if (alarm.getRepeatWeekly()){
            return true;
        }
        else{
            Boolean[] activeDays=getActiveDays();
            int lastDayOfWeek=0;

            for(int i=0;i<7;i++){
                if (activeDays[i]){
                    lastDayOfWeek=i;
                }
            }
            if (getCalendarAlarmTime().get(Calendar.DAY_OF_WEEK)<lastDayOfWeek+1){
                return true;
            }
            else if (getCalendarAlarmTime().get(Calendar.DAY_OF_WEEK)==lastDayOfWeek+1){
                if (getCalendarAlarmTime().get(Calendar.HOUR_OF_DAY)< Calendar.getInstance().get(Calendar.HOUR_OF_DAY)){
                    return true;
                }
                else if (getCalendarAlarmTime().get(Calendar.HOUR_OF_DAY)==Calendar.getInstance().get(Calendar.HOUR_OF_DAY)){
                    if (getCalendarAlarmTime().get(Calendar.MINUTE)< Calendar.getInstance().get(Calendar.MINUTE)){
                        return true;
                    }
                    else if (getCalendarAlarmTime().get(Calendar.MINUTE)==Calendar.getInstance().get(Calendar.MINUTE)){
                        if (getCalendarAlarmTime().get(Calendar.SECOND)< Calendar.getInstance().get(Calendar.SECOND)){
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    private Calendar getCalendarAlarmTime(){
        Calendar alarmTime = Calendar.getInstance();

        alarmTime.set(Calendar.HOUR_OF_DAY,alarm.getHour());
        alarmTime.set(Calendar.MINUTE,alarm.getMinute());
        alarmTime.set(Calendar.SECOND,0);
        alarmTime.set(Calendar.AM_PM,(alarm.getAmPm()=="PM")?Calendar.PM:Calendar.AM);

        return alarmTime;

    }

    private Boolean[] getActiveDays(){
        String activeDaysAsString=alarm.getActiveDays();
        Boolean[] activeDays = new Boolean[7];
        Arrays.fill(activeDays, Boolean.FALSE);

        if(activeDaysAsString.contains("SUN")) activeDays[0]=true;
        if(activeDaysAsString.contains("MON")) activeDays[1]=true;
        if(activeDaysAsString.contains("TUE")) activeDays[2]=true;
        if(activeDaysAsString.contains("WED")) activeDays[3]=true;
        if(activeDaysAsString.contains("THU")) activeDays[4]=true;
        if(activeDaysAsString.contains("FRI")) activeDays[5]=true;
        if(activeDaysAsString.contains("SAT")) activeDays[6]=true;

        return activeDays;
    }

}
