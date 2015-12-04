package com.ajanthan.alarmbot;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by ajanthan on 15-11-04.
 */
public class RealmAlarm extends RealmObject implements Alarm  {
    @Ignore
    private int hour;
    private int minute;
    private ArrayList<Boolean> activeDays;
    private Boolean state;
    private String amPm;
    private int volume;
    private String tone;
    private Boolean smartAlarm;
    private Boolean snooze;
    private String alarmType;
    private Boolean repeatWeekly;
    private RealmAlarmHelper realmAlarmHelper;

    public RealmAlarm(){
        hour = 12;
        amPm = "PM";
        state=false;
        minute = 0;
        activeDays= new ArrayList<Boolean>(7);
        Collections.fill(activeDays,Boolean.FALSE);
        repeatWeekly = false;
        alarmType = "Default";
        volume = 1;                 //TODO: implement volume
        tone = "Default";
        snooze = false;
        smartAlarm = false;
    }

    public RealmAlarm(int hour, int minute, ArrayList<Boolean> activeDays, Boolean repeatWeekly, String alarmType,
                      int volume, String tone, boolean snooze, boolean smartAlarm, boolean state) {
        if (hour == 0) {
            this.hour = 12;
            amPm = "AM";
        } else if (hour / 12 == 0) {
            this.hour = hour;
            amPm = "AM";
        } else if (hour / 12 == 1) {
            this.hour = hour;
            amPm = "PM";
        }
        this.minute = minute;
        this.activeDays = activeDays;
        this.repeatWeekly = repeatWeekly;
        this.alarmType = alarmType;
        this.volume = volume;                 //TODO: implement volume
        this.tone = tone;
        this.snooze = snooze;
        this.smartAlarm = smartAlarm;
        this.state = state;

    }

    public int getHour() {
        return hour;
    }

    public Boolean getState() {
        return state;
    }

    public int getMinute() {
        return minute;
    }

    public String getAmPm() {
        return amPm;
    }

    public String getActiveDays() {
        String day = "";
        Log.e("stacraft", activeDays.size() + "");
        for (int j = 0; j < activeDays.size(); j++) {
            Log.e("stacraft", j + "");
            if (activeDays.get(j)) {
                day += realmAlarmHelper.getDay(j) + " ";
            }
        }
        return day;
    }

    public int getVolume() {
        return volume;
    }

    public String getTone() {
        return tone;
    }

    public Boolean getSmartAlarm() {
        return smartAlarm;
    }

    public Boolean getSnooze() {
        return snooze;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public Boolean getRepeatWeekly() {
        return repeatWeekly;
    }

    public void setState(Boolean mState) {
        this.state = mState;
    }

    public RealmAlarmHelper getRealmAlarmHelper(){
        return realmAlarmHelper;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setActiveDays(ArrayList<Boolean> activeDays) {
        this.activeDays = activeDays;
    }

    public void setAmPm(String amPm) {
        this.amPm = amPm;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public void setSmartAlarm(Boolean smartAlarm) {
        this.smartAlarm = smartAlarm;
    }

    public void setSnooze(Boolean snooze) {
        this.snooze = snooze;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public void setRepeatWeekly(Boolean repeatWeekly) {
        this.repeatWeekly = repeatWeekly;
    }

    public void setRealmAlarmHelper(RealmAlarmHelper realmAlarmHelper) {
        this.realmAlarmHelper = realmAlarmHelper;
    }
}