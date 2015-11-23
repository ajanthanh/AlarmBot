package com.ajanthan.alarmbot;

import android.media.Ringtone;
import android.util.Log;

import java.util.ArrayList;

//import io.realm.RealmObject;

/**
 * Created by ajanthan on 15-11-04.
 */
public class AlarmTone {
    private String mName;
    private String mUri;

    public AlarmTone(String alarmName,String alarmUri) {
        mUri=alarmUri;
        mName=alarmName;
    }

    public String getName() {
        return mName;
    }
    public String getUri(){
        return mUri;
    }
}
