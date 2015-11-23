package com.ajanthan.alarmbot;

import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;

//import io.realm.RealmObject;

/**
 * Created by ajanthan on 15-11-04.
 */
public class Alarm {
    private int mHour;
    private int mMinute;
    private ArrayList<Boolean> mActiveDays;
    private Boolean mState;
    private String amPm;
    private int mVolume;
    private String mTone;
    private Boolean mSmartAlarm;
    private Boolean mSnooze;
    private String mAlarmType;
    private Boolean mRepeatWeekly;

    public Alarm(int hour, int minute, ArrayList<Boolean> activeDays, Boolean repeatWeekly, String alarmType,
                 int volume, String tone, boolean snooze, boolean smartAlarm, boolean state) {
        if (hour == 0) {
            mHour = 12;
            amPm = "AM";
        } else if (mHour / 12 == 0) {
            mHour = hour;
            amPm = "AM";
        } else if (mHour / 12 == 1) {
            mHour = hour;
            amPm = "PM";
        }
        mMinute = minute;
        mActiveDays = activeDays;
        mRepeatWeekly = repeatWeekly;
        mAlarmType = alarmType;
        mVolume = volume;                 //TODO: implement volume
        mTone = tone;
        mSnooze = snooze;
        mSmartAlarm = smartAlarm;
        mState = state;

    }

    public int getHour() {
        return mHour;
    }

    public Boolean getState() {
        return mState;
    }

    public ArrayList<Boolean> getmActiveDays() {
        return mActiveDays;
    }

    public int getMinute() {
        return mMinute;
    }

    public String getAmPm() {
        return amPm;
    }

    public String getActiveDaysAsString() {
        String day = "";
        Log.e("stacraft", mActiveDays.size() + "");
        for (int j = 0; j < mActiveDays.size(); j++) {
            Log.e("stacraft", j + "");
            if (mActiveDays.get(j)) {
                day += getDay(j) + " ";
            }
        }
        return day;
    }

    public int getmVolume() {
        return mVolume;
    }

    public String getmTone() {
        return mTone;
    }

    public void setmTone(String mTone) {
        this.mTone = mTone;
    }

    public Boolean getmSmartAlarm() {
        return mSmartAlarm;
    }

    public Boolean getmSnooze() {
        return mSnooze;
    }

    public String getmAlarmType() {
        return mAlarmType;
    }

    public Boolean getmRepeatWeekly() {
        return mRepeatWeekly;
    }

    private String getDay(int i) {
        switch (i) {
            case 0:
                return "SUN";
            case 1:
                return "MON";
            case 2:
                return "TUE";
            case 3:
                return "WED";
            case 4:
                return "THU";
            case 5:
                return "FRI";
            case 6:
                return "SAT";
        }
        return "";
    }
}
