package com.ajanthan.alarmbot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class TabFragment1 extends Fragment {

    private RecyclerView rAlarms;
    private AlarmAdapter adapterAlarm;
    private ImageButton btnAddAlarm;
    private Realm mRealm;
    private String ALARM_DETAIL_KEY = "alarm";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        rAlarms = (RecyclerView) view.findViewById(R.id.alarmList);
        ArrayList<Alarm> aAlarms = new ArrayList<Alarm>();
        mRealm= Realm.getInstance(getActivity());
        adapterAlarm = new AlarmAdapter(getActivity(), fetchAlarms(),mRealm);
        rAlarms.setAdapter(adapterAlarm);
        rAlarms.setLayoutManager(new LinearLayoutManager(getActivity()));
        btnAddAlarm = (ImageButton) view.findViewById(R.id.addAlarm);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupAddAlarmListener();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        update();
        super.onResume();
    }

    private void setupAddAlarmListener() {
        btnAddAlarm.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AlarmDetailActivity.class);
                i.putExtra("cmd", "new");
                getContext().startActivity(i);
                Toast.makeText(getActivity().getApplicationContext(), "add alarm", Toast.LENGTH_LONG).show();
            }
        });
    }

    private ArrayList<Alarm> fetchAlarms(){
        ArrayList<Alarm> alarms = new ArrayList<Alarm>();
        Realm realm= Realm.getInstance(getActivity());
        RealmResults<RealmAlarm> result = realm.where(RealmAlarm.class)
                .findAll();
        for(int i =0; i<result.size();i++){
            alarms.add(result.get(i));
        }
        return alarms;
    }

    private void update(){
        adapterAlarm.update(fetchAlarms());
    }

}