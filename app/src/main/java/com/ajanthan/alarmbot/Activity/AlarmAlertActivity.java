package com.ajanthan.alarmbot.Activity;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ajanthan.alarmbot.AlarmHelper;
import com.ajanthan.alarmbot.Objects.Alarm;
import com.ajanthan.alarmbot.Objects.RealmAlarm;
import com.ajanthan.alarmbot.R;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by ajanthan on 15-12-20.
 */
public class AlarmAlertActivity extends Activity {

    private static final String PREF_ALARM_KEY = "alarm";
    private Alarm mAlarm;
    private MediaPlayer mediaPlayer;

    private Boolean alarmActive;

    private TextView tvAlarmName;
    private TextView tvTime;
    private TextView tvAmPm;
    private Button bSnooze;
    private Button bDismiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.alarm_alert);

        tvAlarmName = (TextView) findViewById(R.id.name);
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

        Intent i = getIntent();
        mAlarm = AlarmHelper.getAlarm(i.getLongExtra(PREF_ALARM_KEY, 0), this);

        if (mAlarm == null) {
            mAlarm = new RealmAlarm();
        }

        tvAlarmName.setText(mAlarm.getAlarmName());
        startAlarm();

    }

    @Override
    protected void onResume() {
        super.onResume();
        alarmActive = true;
    }

    @Override
    public void onBackPressed() {
        if (!alarmActive)
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            stopAlarm();
        }
        super.onDestroy();
    }

    private void setSnoozeOnClickListener() {
        bSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarm();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent alarmAlertActivityIntent = new Intent(AlarmAlertActivity.this, AlarmAlertActivity.class);

                        alarmAlertActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        alarmAlertActivityIntent.putExtra(PREF_ALARM_KEY, mAlarm.getKey());

                        alarmAlertActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(alarmAlertActivityIntent);
                    }
                }, 300000);

                Toast.makeText(getApplicationContext(), "Alarm will sound in 5 minutes",
                        Toast.LENGTH_LONG).show();

                Intent i = new Intent(AlarmAlertActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

    }

    private void setDismissOnClickListener() {
        bDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarm();
                Intent i = new Intent(AlarmAlertActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }

    private void startAlarm() {

        if (mAlarm.getToneUri() != "") {
            mediaPlayer = new MediaPlayer();
//            if (alarm.getVibrate()) {
//                vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//                long[] pattern = { 1000, 200, 200, 200 };
//                vibrator.vibrate(pattern, 0);
//            }
            try {
                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.setDataSource(this,
                        Uri.parse(mAlarm.getToneUri()));
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mediaPlayer.setLooping(true);
                mediaPlayer.prepare();
                mediaPlayer.start();

            } catch (Exception e) {
                mediaPlayer.release();
                alarmActive = false;
            }
        }

    }

    private void stopAlarm() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}