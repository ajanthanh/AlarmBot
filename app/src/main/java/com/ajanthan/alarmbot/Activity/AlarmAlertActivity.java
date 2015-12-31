package com.ajanthan.alarmbot.Activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ajanthan.alarmbot.AlarmHelper;
import com.ajanthan.alarmbot.Objects.AlarmTone;
import com.ajanthan.alarmbot.R;

import java.util.Calendar;

/**
 * Created by ajanthan on 15-12-20.
 */
public class AlarmAlertActivity extends Activity {

    private TextView tvTime;
    private TextView tvAmPm;
    private Button bSnooze;
    private Button bDismiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_alert);

        tvTime = (TextView) findViewById(R.id.time);
        tvAmPm = (TextView) findViewById(R.id.amPm);
        bSnooze = (Button) findViewById(R.id.snooze);
        bDismiss = (Button) findViewById(R.id.dismiss);

        tvTime.setText(AlarmHelper.getFormatedTime(
                Calendar.getInstance().get(Calendar.HOUR),
                Calendar.getInstance().get(Calendar.MINUTE)));
        tvAmPm.setText((Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 12) ? "AM" : "PM");

        setDismissOnClickListener();
        setSnoozeOnClickListener();

    }

    private void setSnoozeOnClickListener() {
        bSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO implement a 5 minute delayed alarm
                Log.e("AlarmAlert", "Snooze");
            }
        });
    }

    private void setDismissOnClickListener() {
        bDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO stop alarm
                Log.e("AlarmAlert", "Dismiss");
                Intent i=new Intent(AlarmAlertActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}