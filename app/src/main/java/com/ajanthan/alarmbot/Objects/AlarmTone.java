package com.ajanthan.alarmbot.Objects;

//import io.realm.RealmObject;

/**
 * Created by ajanthan on 15-11-04.
 */
public class AlarmTone {
    private String mName;
    private String mUri;
    private Boolean isActive;

    public AlarmTone(String alarmName, String alarmUri) {
        mUri = alarmUri;
        mName = alarmName;
        isActive = false;
    }

    public String getName() {
        return mName;
    }

    public String getUri() {
        return mUri;
    }

    public void setActive() {
        isActive = true;
    }

    public void setInActive() {
        isActive = false;
    }

    public Boolean getIsActive() {
        return isActive;
    }
}
