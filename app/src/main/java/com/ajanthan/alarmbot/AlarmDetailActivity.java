package com.ajanthan.alarmbot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ajanthan on 15-11-10.
 */
public class AlarmDetailActivity extends Activity {
    private Button bSave;
    private Button bCancel;

    private LinearLayout lRepeatWeeklyFragment;
    private LinearLayout lAlarmTypeFragment;
    private LinearLayout lvolumeFragment;
    private TextView tvAlarmDate;
    private Spinner tvRepeatWeekly;
    private Spinner tvAlarmType;
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

        lRepeatWeeklyFragment = (LinearLayout) findViewById(R.id.repeatWeeklyFragment);
        lAlarmTypeFragment = (LinearLayout) findViewById(R.id.alarmTypeFragment);
        lvolumeFragment = (LinearLayout) findViewById(R.id.volumeFragment);

        tvAlarmDate = (TextView) findViewById(R.id.alarmDate);
        tvRepeatWeekly = (Spinner) findViewById(R.id.repeatWeekly);
        tvAlarmType = (Spinner) findViewById(R.id.alarmType);
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

        setCustomToolBarListeners();
        setFeildOnClickListener();

    }

    private void setFeildOnClickListener() {
        lRepeatWeeklyFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvRepeatWeekly.performClick();
            }
        });

        lAlarmTypeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAlarmType.performClick();
            }
        });

        tvAlarmType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (tvAlarmType.getSelectedItemPosition() == 1) {
                    lvolumeFragment.setVisibility(View.INVISIBLE);
                } else {
                    lvolumeFragment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                tvAlarmType.setSelection(0);
            }

        });

        tvTone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlarmDetailActivity.this, AlarmToneActivity.class);
                startActivity(i);
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
        if (tvRepeatWeekly.getSelectedItem().equals("never")) {
            return false;
        } else if (tvRepeatWeekly.getSelectedItem().equals("always")) {
            return true;
        } else {
            return false;
        }
    }

    private String getAlarmType() {
        return tvAlarmType.getSelectedItem().toString();
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
