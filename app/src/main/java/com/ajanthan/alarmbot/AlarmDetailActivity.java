package com.ajanthan.alarmbot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ajanthan on 15-11-10.
 */
public class AlarmDetailActivity extends Activity {
    private static final String PREFS_NAME = "currentAlarmTone";

    private Alarm mAlarm;
    private Realm mRealm;

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

        mRealm= Realm.getInstance(this);
        RealmResults<RealmAlarm> result = mRealm.where(RealmAlarm.class)
                .equalTo("key", getIntent().getLongExtra("key", 0))
                .findAll();
        mAlarm=result.get(0);
        setCustomToolBarListeners();
        setFeildOnClickListener();

    }

    @Override
    protected void onStart() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("currentAlarmToneName", "");
        if(!restoredText.isEmpty()) {
            tvTone.setText(restoredText);
        }
        super.onStart();
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
                mRealm.beginTransaction();
                mAlarm.setHour(getHour());
                mAlarm.setMinute(getMinute());
                mAlarm.setActiveDays(getActiveDaysAsString(getActiveDays()));
                mAlarm.setRepeatWeekly(getRepeatWeekly());
                mAlarm.setAlarmType(getAlarmType());
                mAlarm.setVolume(getVolume());
                mAlarm.setTone(getTone());
                mAlarm.setSnooze(getSnooze());
                mAlarm.setSmartAlarm(getSmartAlarm());
                Log.e("kool", mAlarm.toString());
                mRealm.commitTransaction();
                finish();

            }
        });
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Boolean[] getActiveDays() {
        Log.e("Blahblah","getActiveDays");
        Boolean[] activeDays = new Boolean[7];
        for (int i = 0; i < bActiveDays.size(); i++) {
            activeDays[i]=bActiveDays.get(i).isSelected();
                Log.e("Blahblah",bActiveDays.get(i).isSelected()+" | "+bActiveDays.get(i).isActivated()+" | "+ bActiveDays.get(i).isSelected());
        }
        Log.e("Blahblah",activeDays.toString());
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

    private int getHour(){return 10;}
    private int getMinute(){return 10;}

    private String getActiveDaysAsString(Boolean[] activeDays) {
        Log.e("Blahblah","getActiveDaysAsString");
        String day = "";
        for (int j = 0; j < activeDays.length; j++) {
            Log.e("stacraft", j + "");
            if (activeDays[j]) {
                day += getDay(j) + " ";
            }
        }
        Log.e("Blahblah2",day);
        return day;

    }

    public String getDay(int i) {
        switch (i) {
            case 0:
                return "SUN";
            case 1:
                return "MON";
            case 2:
                return "TUE";
            case 3:
                return "WED";
            case 4:
                return "THU";
            case 5:
                return "FRI";
            case 6:
                return "SAT";
        }
        return "";
    }

}
