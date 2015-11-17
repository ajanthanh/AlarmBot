package com.ajanthan.alarmbot;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ajanthanP on 15-11-10.
 */
public class AlarmDetailActivity extends AppCompatActivity {
    private TextView tvAlarmDate;
    private TextView tvRepeatWeekly;
    private TextView tvAlarmType;
    private TextView tvTone;
    private SeekBar sVolume;
    private Switch sSnooze;
    private Switch sSmartAlarm;
    private ArrayList<Button> bActiveDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_detail);
        tvAlarmDate = (TextView)findViewById(R.id.alarmDate);
        tvRepeatWeekly = (TextView)findViewById(R.id.repeatWeekly);
        tvAlarmType = (TextView)findViewById(R.id.alarmType);
        tvTone = (TextView)findViewById(R.id.alarmTone);
        sVolume =(SeekBar)findViewById(R.id.volume);
        sSnooze = (Switch)findViewById(R.id.snooze);
        sSmartAlarm = (Switch)findViewById(R.id.smartAlarm);
        bActiveDays = new ArrayList<Button>();
        bActiveDays.add((Button)findViewById(R.id.activeDaySunday));
        bActiveDays.add((Button)findViewById(R.id.activeDayMonday));
        bActiveDays.add((Button)findViewById(R.id.activeDayTuesday));
        bActiveDays.add((Button) findViewById(R.id.activeDayWednesday));
        bActiveDays.add((Button) findViewById(R.id.activeDayThursday));
        bActiveDays.add((Button) findViewById(R.id.activeDayFriday));
        bActiveDays.add((Button) findViewById(R.id.activeDaySaturday));


        tvRepeatWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
