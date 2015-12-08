package com.ajanthan.alarmbot;

import java.util.ArrayList;

/**
 * Created by ajanthan on 15-11-04.
 */
public interface Alarm {

    int getHour();

    Boolean getState();

    String getActiveDays();

    int getMinute();

    String getAmPm();

    int getVolume();

    String getTone();

    Boolean getSmartAlarm();

    Boolean getSnooze();

    String getAlarmType();

    Boolean getRepeatWeekly();

    void setState(Boolean mState);
}
