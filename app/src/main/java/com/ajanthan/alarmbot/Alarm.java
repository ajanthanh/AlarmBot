package com.ajanthan.alarmbot;

import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

//import io.realm.RealmObject;

/**
 * Created by ajanthan on 15-11-04.
 */
public interface Alarm {

    public int getHour();

    public Boolean getState();

    public ArrayList<Boolean> getmActiveDays();

    public int getMinute();

    public String getAmPm();

    public String getActiveDaysAsString();

    public int getmVolume();

    public String getmTone();

    public Boolean getmSmartAlarm();

    public Boolean getmSnooze();

    public String getmAlarmType();

    public Boolean getmRepeatWeekly();

    public void setState(Boolean mState);
}
