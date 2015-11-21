package com.ajanthan.alarmbot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Scroller;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ajanthanP on 15-11-10.
 */
public class AlarmDetailActivity extends AppCompatActivity {
    private Button bSave;
    private Button bCancel;
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
        bSave = (Button) findViewById(R.id.save);
        bCancel = (Button) findViewById(R.id.cancel);
        tvAlarmDate = (TextView) findViewById(R.id.alarmDate);
        tvRepeatWeekly = (TextView) findViewById(R.id.repeatWeekly);
        tvAlarmType = (TextView) findViewById(R.id.alarmType);
        tvTone = (TextView) findViewById(R.id.alarmTone);
        sVolume = (SeekBar) findViewById(R.id.volume);
        sSnooze = (Switch) findViewById(R.id.snooze);
        sSmartAlarm = (Switch) findViewById(R.id.smartAlarm);
        bActiveDays = new ArrayList<Button>();
        bActiveDays.add((Button) findViewById(R.id.activeDaySunday));
        bActiveDays.add((Button) findViewById(R.id.activeDayMonday));
        bActiveDays.add((Button) findViewById(R.id.activeDayTuesday));
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

    private void setCustomToolBarListeners() {
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarm newAlarm = new Alarm(0, 0, getActiveDays(), getRepeatWeekly(), getAlarmType(),
                        getVolume(), getTone(), getSnooze(), getSmartAlarm(), true);

                //TODO save alarm instance and go back to list view
            }
        });
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private ArrayList<Boolean> getActiveDays() {
        ArrayList<Boolean> activeDays = new ArrayList<Boolean>();
        for (int i = 0; i < bActiveDays.size(); i++) {
            activeDays.add(bActiveDays.get(i).isSelected());
        }
        return activeDays;
    }

    private Boolean getRepeatWeekly() {
        if (tvRepeatWeekly.getText().equals("never")) {
            return false;
        } else if (tvRepeatWeekly.getText().equals("always")) {
            return true;
        } else {
            return false;
        }
    }

    private String getAlarmType() {
        return tvAlarmType.getText().toString();
    }

    private int getVolume() {
        return sVolume.getVerticalScrollbarPosition(); //TODO: implement volume
    }

    private String getTone() {
        return tvTone.getText().toString();
    }

    private Boolean getSnooze() {
        return sSnooze.isSelected();
    }

    private Boolean getSmartAlarm() {
        return sSmartAlarm.isSelected();
    }
}
