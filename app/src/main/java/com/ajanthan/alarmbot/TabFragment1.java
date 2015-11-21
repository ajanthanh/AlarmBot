package com.ajanthan.alarmbot;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TabFragment1 extends Fragment {

    private ListView lvAlarms;
    private AlarmAdapter adapterAlarm;
    private ImageButton btnAddAlarm;
    private String ALARM_DETAIL_KEY = "alarm";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        lvAlarms = (ListView) view.findViewById(R.id.alarmList);
        ArrayList<Alarm> aAlarms = new ArrayList<Alarm>();
        adapterAlarm = new AlarmAdapter(getActivity(), aAlarms);
        lvAlarms.setAdapter(adapterAlarm);
        btnAddAlarm = (ImageButton) view.findViewById(R.id.addAlarm);
        lvAlarms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                Toast.makeText(getActivity().getApplicationContext(), "alarm clicked", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), AlarmDetailActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        setupAddAlarmListener();

        super.onViewCreated(view, savedInstanceState);
    }

    private void setupAlarmSelectedListener() {
        Toast.makeText(getActivity().getApplicationContext(), "alarm click listener called", Toast.LENGTH_LONG).show();
    }

    private void setupAddAlarmListener() {
        btnAddAlarm.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "add alarm", Toast.LENGTH_LONG).show();
                Boolean[] temp = new Boolean[7];
                for (int i = 0; i < 7; i++) {
                    temp[i] = true;
                }
                Intent i = new Intent(getActivity(), AlarmDetailActivity.class);
                startActivity(i);
//                adapterAlarm.add(new Alarm(0, 0, true, temp));
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }
}