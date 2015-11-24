package com.ajanthan.alarmbot;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ajanthan on 15-11-22.
 */
public class AlarmToneActivity extends Activity {
    private ListView lvOptions;
    private AlarmToneAdapter adapterAlarmTone;
    private Ringtone currentRingTone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_tone_list);

        lvOptions = (ListView) findViewById(R.id.optionsList);
        ArrayList<AlarmTone> aAlarmTone = new ArrayList<AlarmTone>();
        adapterAlarmTone = new AlarmToneAdapter(this, aAlarmTone);
        lvOptions.setAdapter(adapterAlarmTone);
        fetchAlarmTone();
        setOnClickListener();

    }

    @Override
    protected void onPause() {
        if(currentRingTone!=null){
            currentRingTone.stop();
        }
        super.onPause();
    }

    private void fetchAlarmTone() {
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = manager.getCursor();

        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
            adapterAlarmTone.add(new AlarmTone(notificationTitle, notificationUri));
        }

    }

    private void setOnClickListener() {
        lvOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playAlarmTone(adapterAlarmTone.getItem(position).getUri());
            }
        });
    }

    private void playAlarmTone(String notificationUri) {
        if (currentRingTone != null) {
            Log.e("Piano", notificationUri);
            currentRingTone.stop();
        }
        Uri notification = Uri.parse(notificationUri);
        currentRingTone = RingtoneManager.getRingtone(getApplicationContext(), notification);
        currentRingTone.play();
    }
}
