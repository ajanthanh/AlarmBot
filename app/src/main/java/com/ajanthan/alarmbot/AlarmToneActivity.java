package com.ajanthan.alarmbot;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ajanthan on 15-11-22.
 */
public class AlarmToneActivity extends Activity {
    private ListView lvOptions;
    private AlarmToneAdapter adapterAlarmTone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_tone_list);

        lvOptions =(ListView)findViewById(R.id.optionsList);
        ArrayList<AlarmTone> aAlarmTone = new ArrayList<AlarmTone>();
        adapterAlarmTone = new AlarmToneAdapter(this,aAlarmTone);
        lvOptions.setAdapter(adapterAlarmTone);
        fetchAlarmTone();

    }
    private void fetchAlarmTone(){
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = manager.getCursor();

        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
            adapterAlarmTone.add(new AlarmTone(notificationTitle, notificationUri));
//            playAlarmTone(notificationUri);
        }

    }

    private void playAlarmTone(String notificationUri){
        Uri notification = Uri.parse(notificationUri);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
    }
}
