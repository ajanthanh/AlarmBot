package com.ajanthan.alarmbot;

/**
 * Created by ajanthanP on 15-12-03.
 */
public class RealmAlarmHelper {

    public String getDay(int i) {
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
