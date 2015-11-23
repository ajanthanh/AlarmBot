package com.ajanthan.alarmbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ajanthan on 15-11-04.
 */
public class AlarmToneAdapter extends ArrayAdapter<AlarmTone> {
    public AlarmToneAdapter(Context context, ArrayList<AlarmTone> aAlarmTone) {
        super(context, 0, aAlarmTone);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        AlarmTone alarmTone = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.alarm_tone_fragment, null);
        }
        // Lookup view for data population
        TextView tvAlarmToneName = (TextView) convertView.findViewById(R.id.alarmToneName);
        TextView rAlarmToneActive = (RadioButton) convertView.findViewById(R.id.alarmToneActive);

        tvAlarmToneName.setText(alarmTone.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}
