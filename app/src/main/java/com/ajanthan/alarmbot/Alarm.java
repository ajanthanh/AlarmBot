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

    int getAlarmType();

    Boolean getRepeatWeekly();

    long getKey();

    void setState(Boolean mState);

    void setHour(int hour);

    void setActiveDays(String activeDays);

    void setMinute(int minute);

    void setAmPm(String amPm);

    void setVolume(int volume);

    void setTone(String tone);

    void setSmartAlarm(Boolean smartAlarm);

    void setSnooze(Boolean snooze);

    void setAlarmType(int alarmType);

    void setRepeatWeekly(Boolean repeatWeekly);

    void setKey(long key);


}
