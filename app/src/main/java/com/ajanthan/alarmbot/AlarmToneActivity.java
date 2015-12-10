package com.ajanthan.alarmbot;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by ajanthan on 15-11-22.
 */
public class AlarmToneActivity extends Activity {
    private RecyclerView rAlarmTones;
    private AlarmToneAdapter adapterAlarmTone;

    public static final String PREFS_NAME = "currentAlarmTone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_tone_list);

        rAlarmTones = (RecyclerView) findViewById(R.id.alarmTonesList);
        adapterAlarmTone = new AlarmToneAdapter(this, fetchAlarmTone());
        rAlarmTones.setAdapter(adapterAlarmTone);
        rAlarmTones.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onPause() {
        adapterAlarmTone.stopCurrentRingTone();
        AlarmTone alarmTone = adapterAlarmTone.getCurrentAlarmTone();
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("currentAlarmToneName", alarmTone.getName());
        editor.putString("currentAlarmToneUri", alarmTone.getUri());
        editor.commit();

        super.onPause();
    }

    private ArrayList<AlarmTone> fetchAlarmTone() {
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = manager.getCursor();
        ArrayList<AlarmTone> alarmTones = new ArrayList<AlarmTone>();

        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
            alarmTones.add(new AlarmTone(notificationTitle, notificationUri));
        }
        return alarmTones;

    }


}
