package com.ajanthan.alarmbot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class TabFragment1 extends Fragment {

    private RecyclerView rAlarms;
    private AlarmAdapter adapterAlarm;
    private ImageButton btnAddAlarm;
    private String ALARM_DETAIL_KEY = "alarm";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        rAlarms = (RecyclerView) view.findViewById(R.id.alarmList);
        ArrayList<Alarm> aAlarms = new ArrayList<Alarm>();
        adapterAlarm = new AlarmAdapter(getActivity(), getAlarms());
        rAlarms.setAdapter(adapterAlarm);
        rAlarms.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterAlarm.addAlarm(new RealmAlarm());
        btnAddAlarm = (ImageButton) view.findViewById(R.id.addAlarm);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        setupAddAlarmListener();

        super.onViewCreated(view, savedInstanceState);
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
                adapterAlarm.addAlarm(new RealmAlarm());
//                Intent i = new Intent(getActivity(), AlarmDetailActivity.class);
//                startActivity(i);
//                adapterAlarm.add(new Alarm(0, 0, true, temp));
            }
        });
    }

    private ArrayList<Alarm> getAlarms(){
        return null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }
}