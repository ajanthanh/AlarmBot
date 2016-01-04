package com.ajanthan.alarmbot.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ajanthan.alarmbot.Adapter.AlarmToneAdapter;
import com.ajanthan.alarmbot.Objects.AlarmTone;
import com.ajanthan.alarmbot.R;

import java.util.ArrayList;

/**
 * Created by ajanthan on 15-11-22.
 */
public class AlarmToneActivity extends Activity {
    private RecyclerView rAlarmTones;
    private AlarmToneAdapter adapterAlarmTone;

    public static final String PREFS_NAME = "currentAlarmTone";

    private static final String PREF_ALARM_TONE_URI_KEY = "currentAlarmToneUri";
    private static final String PREF_ALARM_TOME_NAME_KEY = "currentAlarmToneName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_tone_list);
        rAlarmTones = (RecyclerView) findViewById(R.id.alarmTonesList);
        Intent i = getIntent();
        adapterAlarmTone = new AlarmToneAdapter(this, fetchAlarmTone(), i.getStringExtra("alarmToneName"));
        rAlarmTones.setAdapter(adapterAlarmTone);
        rAlarmTones.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onPause() {
        adapterAlarmTone.stopCurrentRingTone();
        AlarmTone alarmTone = adapterAlarmTone.getCurrentAlarmTone();
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(PREF_ALARM_TOME_NAME_KEY, alarmTone.getName());
        editor.putString(PREF_ALARM_TONE_URI_KEY, alarmTone.getUri());
        editor.commit();
        super.onPause();
        finish();
    }

    private ArrayList<AlarmTone> fetchAlarmTone() {
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_ALARM);
        Cursor alarmsCursor = manager.getCursor();
        ArrayList<AlarmTone> alarmTones = new ArrayList<AlarmTone>(alarmsCursor.getCount());

        if (alarmsCursor.moveToFirst()) {
            do {
                alarmTones.add(new AlarmTone(
                        manager.getRingtone(alarmsCursor.getPosition()).getTitle(this),
                        manager.getRingtoneUri(alarmsCursor.getPosition()).toString()));
            } while (alarmsCursor.moveToNext());
        }
        alarmsCursor.close();
        return alarmTones;
    }


}
