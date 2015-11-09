package com.ajanthan.alarmbot;

import android.util.Log;

/**
 * Created by ajanthan on 15-11-04.
 */
public class Alarm {
    private int mHour;
    private int mMinute;
    private Boolean[] mActiveDays;
    private Boolean mState;
    private String amPm;

    public Alarm(int hour, int minute, boolean state, Boolean[] activeDays) {
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
        mState = state;
        mActiveDays = activeDays;
        Log.e("testing", activeDays.toString());
    }

    public int getHour() {
        return mHour;
    }

    public Boolean getState() {
        return mState;
    }

    public Boolean[] getmActiveDays() {
        return mActiveDays;
    }

    public int getMinute() {
        return mMinute;
    }

    public String getAmPm() {
        return amPm;
    }

    public String getActiveDaysAsString(){
        String day="";
        Log.e("stacraft",mActiveDays.length+"");
        for(int j=0;j<mActiveDays.length;j++){
            Log.e("stacraft",j+"");
            if(mActiveDays[j]){
                day+=getDay(j)+" ";
            }
        }
        return day;
    }

    private String getDay(int i){
        switch (i){
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
