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

    public String getActiveDays();

    public int getVolume();

    public String getTone();

    public Boolean getSmartAlarm();

    public Boolean getSnooze();

    public String getAlarmType();

    public Boolean getRepeatWeekly();

    public void setState(Boolean mState);
}
