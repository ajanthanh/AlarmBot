package com.ajanthan.alarmbot;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by ajanthanP on 15-11-10.
 */
public class AlarmDetailActivity extends Activity{
    private TextView tvAlarmDate;
    private TextView tvRepeatWeekly;
    private TextView tvAlarmType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_detail);


    }
}
