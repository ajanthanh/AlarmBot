package com.ajanthan.alarmbot.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ajanthan.alarmbot.AlarmHelper;
import com.ajanthan.alarmbot.Objects.Alarm;
import com.ajanthan.alarmbot.R;
import com.ajanthan.alarmbot.Objects.RealmAlarm;

import java.util.ArrayList;
import java.util.Calendar;

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
    private Switch sRepeatWeekly;
    private Spinner sAlarmType;
    private TextView tvTone;
    private SeekBar sVolume;
    private Switch sSnooze;
    private Switch sSmartAlarm;
    private ArrayList<ToggleButton> bActiveDays;

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
        sRepeatWeekly = (Switch) findViewById(R.id.repeatWeekly);
        sAlarmType = (Spinner) findViewById(R.id.alarmType);
        tvTone = (TextView) findViewById(R.id.alarmTone);
        sVolume = (SeekBar) findViewById(R.id.volume);
        sSnooze = (Switch) findViewById(R.id.snooze);
        sSmartAlarm = (Switch) findViewById(R.id.smartAlarm);
        bActiveDays = new ArrayList<ToggleButton>();
        bActiveDays.add((ToggleButton) findViewById(R.id.activeDaySunday));
        bActiveDays.add((ToggleButton) findViewById(R.id.activeDayMonday));
        bActiveDays.add((ToggleButton) findViewById(R.id.activeDayTuesday));
        bActiveDays.add((ToggleButton) findViewById(R.id.activeDayWednesday));
        bActiveDays.add((ToggleButton) findViewById(R.id.activeDayThursday));
        bActiveDays.add((ToggleButton) findViewById(R.id.activeDayFriday));
        bActiveDays.add((ToggleButton) findViewById(R.id.activeDaySaturday));

        mRealm= Realm.getInstance(this);
        if(getIntent().getStringExtra("cmd").equals("edit")){
            RealmResults<RealmAlarm> result = mRealm.where(RealmAlarm.class)
                    .equalTo("key", getIntent().getLongExtra("key", 0))
                    .findAll();
            mAlarm=result.get(0);
            setDetailActivityFields();
        }
        setCustomToolBarListeners();
        setFeildOnClickListener();

    }

    private void setDetailActivityFields() {
        sRepeatWeekly.setChecked(true);
        sAlarmType.setSelection(mAlarm.getAlarmType());
        tvTone.setText(mAlarm.getTone());
        sVolume.setProgress(mAlarm.getVolume());
        sSnooze.setChecked(mAlarm.getSnooze());
        sSmartAlarm.setChecked(mAlarm.getSmartAlarm());

        if(mAlarm.getActiveDays().contains("SUN")) bActiveDays.get(0).setChecked(true);
        if(mAlarm.getActiveDays().contains("MON")) bActiveDays.get(1).setChecked(true);
        if(mAlarm.getActiveDays().contains("TUE")) bActiveDays.get(2).setChecked(true);
        if(mAlarm.getActiveDays().contains("WED")) bActiveDays.get(3).setChecked(true);
        if(mAlarm.getActiveDays().contains("THU")) bActiveDays.get(4).setChecked(true);
        if(mAlarm.getActiveDays().contains("FRI")) bActiveDays.get(5).setChecked(true);
        if(mAlarm.getActiveDays().contains("SAT")) bActiveDays.get(6).setChecked(true);

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
                sRepeatWeekly.performClick();
            }
        });

        lAlarmTypeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sAlarmType.performClick();
            }
        });

        sAlarmType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (sAlarmType.getSelectedItemPosition() == 1) {
                    lvolumeFragment.setVisibility(View.GONE);
                } else {
                    lvolumeFragment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                sAlarmType.setSelection(0);
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
                RealmAlarm alarm;
                if (getIntent().getStringExtra("cmd").equals("new")) {
                    alarm = new RealmAlarm(getHour(), getMinute(), getActiveDaysAsString(getActiveDays()), getRepeatWeekly(), getAlarmType(),
                            getVolume(), getTone(), getSnooze(), getSmartAlarm(), true);
                    mRealm.copyToRealm(alarm);
                    mAlarm=alarm;
                } else {
                    mAlarm.setHour(getHour());
                    mAlarm.setMinute(getMinute());
                    mAlarm.setActiveDays(getActiveDaysAsString(getActiveDays()));
                    mAlarm.setRepeatWeekly(getRepeatWeekly());
                    mAlarm.setAlarmType(getAlarmType());
                    mAlarm.setVolume(getVolume());
                    mAlarm.setTone(getTone());
                    mAlarm.setSnooze(getSnooze());
                    mAlarm.setSmartAlarm(getSmartAlarm());
                }
                mRealm.commitTransaction();

                AlarmHelper alarmHelper = new AlarmHelper(mAlarm);
                Toast.makeText(AlarmDetailActivity.this, alarmHelper.getTimeUntilNextAlarmMessage(), Toast.LENGTH_LONG).show();

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
        Boolean[] activeDays = new Boolean[7];
        for (int i = 0; i < bActiveDays.size(); i++) {
            activeDays[i]=bActiveDays.get(i).isChecked();
        }
        if(areAllFalse(activeDays)){
            Calendar alarmTime = Calendar.getInstance();

            alarmTime.set(Calendar.HOUR_OF_DAY,getHour());
            alarmTime.set(Calendar.MINUTE,getMinute());
            alarmTime.set(Calendar.SECOND,0);
            alarmTime.set(Calendar.AM_PM,(getHour()<12)?Calendar.AM:Calendar.PM);

            if(alarmTime.before(Calendar.getInstance())){
                activeDays[alarmTime.get(Calendar.DAY_OF_WEEK)]=true;
            }else{
                activeDays[alarmTime.get(Calendar.DAY_OF_WEEK)-1]=true;
            }

        }
        return activeDays;
    }

    public Boolean areAllFalse(Boolean[] array)
    {
        for(boolean b : array) if(b) return false;
        return true;
    }

    private Boolean getRepeatWeekly() {
        return sRepeatWeekly.isChecked();
    }

    private int getAlarmType() {
        return sAlarmType.getSelectedItemPosition();
    }

    private int getVolume() {
        return sVolume.getVerticalScrollbarPosition(); //TODO: implement volume
    }

    private String getTone() {
        return tvTone.getText().toString();
    }

    private Boolean getSnooze() {
        return sSnooze.isChecked();
    }

    private Boolean getSmartAlarm() {
        return sSmartAlarm.isChecked();
    }

    private int getHour(){return 10;}
    private int getMinute(){return 10;}

    private String getActiveDaysAsString(Boolean[] activeDays) {
        String day = "";
        for (int j = 0; j < activeDays.length; j++) {
            if (activeDays[j]) {
                day += getDay(j) + " ";
            }
        }
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
