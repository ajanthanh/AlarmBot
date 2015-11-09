package com.ajanthan.alarmbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ajanthan on 15-11-04.
 */
public class AlarmAdapter extends ArrayAdapter<Alarm> {
    public AlarmAdapter(Context context, ArrayList<Alarm> aAlarm) {
        super(context,0, aAlarm);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Alarm alarm = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.alarm_fragment, null);
        }
        // Lookup view for data population
        TextView tvAlarmFragment = (TextView) convertView.findViewById(R.id.alarmFragment);
        TextView tvAmPmFragment = (TextView) convertView.findViewById(R.id.amPmFragment);
        TextView tvAlarmActiveDaysFragment = (TextView) convertView.findViewById(R.id.alarmActiveDaysFragment);
        Switch sActiveFragment = (Switch) convertView.findViewById(R.id.activeFragment);
        // Populate the data into the template view using the data object
        if (alarm.getMinute()<10){
            tvAlarmFragment.setText(alarm.getHour()+":0"+alarm.getMinute());
        }
        else{
            tvAlarmFragment.setText(alarm.getHour()+":"+alarm.getMinute());
        }
        tvAmPmFragment.setText(alarm.getAmPm());
        sActiveFragment.setChecked(alarm.getState());
        tvAlarmActiveDaysFragment.setText(alarm.getActiveDaysAsString());

        // Return the completed view to render on screen
        return convertView;
    }
}
