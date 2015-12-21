package com.ajanthan.alarmbot.Activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.ajanthan.alarmbot.R;

/**
 * Created by ajanthan on 15-12-20.
 */
public class AlarmAlertActivity extends Activity{

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_alert);
    }
}
