package com.ajanthan.alarmbot;

import java.util.Arrays;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by ajanthan on 15-11-04.
 */
public class RealmAlarm extends RealmObject implements Alarm  {
    @Ignore
    private int hour;
    private int minute;
    private String activeDays;
    private Boolean state;
    private String amPm;
    private int volume;
    private String tone;
    private Boolean smartAlarm;
    private Boolean snooze;
    private String alarmType;
    private Boolean repeatWeekly;

    public RealmAlarm(){
        hour = 12;
        amPm = "PM";
        state=false;
        minute = 0;
        activeDays ="";
        repeatWeekly = false;
        alarmType = "Default";
        volume = 1;                 //TODO: implement volume
        tone = "Default";
        snooze = false;
        smartAlarm = false;
    }

    public RealmAlarm(int hour, int minute, String activeDays, Boolean repeatWeekly, String alarmType,
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
        return activeDays;
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

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setActiveDays(String activeDays) {
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

}