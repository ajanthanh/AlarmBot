package com.ajanthan.alarmbot;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private String ALARM_DETAIL_KEY="alarm";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_1, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        lvAlarms = (ListView) getView().findViewById(R.id.alarmList);
        ArrayList<Alarm> aAlarms= new ArrayList<Alarm>();
        adapterAlarm = new AlarmAdapter(getActivity(),aAlarms);
        lvAlarms.setAdapter(adapterAlarm);
        btnAddAlarm = (ImageButton) getView().findViewById(R.id.addAlarm);

        setupAlarmSelectedListener();
        setupAddAlarmListener();

        super.onViewCreated(view, savedInstanceState);
    }

    private void setupAlarmSelectedListener(){
        lvAlarms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
//                Intent i = new Intent(TabFragment1.this, BoxOfficeDetailActivity.class);
//                i.putExtra("1", adapterAlarm.getItem(position));
//                startActivity(i);
            }
        });
    }
    private void setupAddAlarmListener(){
        btnAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "add alarm", Toast.LENGTH_LONG).show();
                Boolean[] temp= new Boolean[7];
                for(int i=0;i<7;i++){
                    temp[i]=true;
                }
                adapterAlarm.add(new Alarm(0,0,true,temp));
            }
        });
    }
}