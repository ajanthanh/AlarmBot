package com.ajanthan.alarmbot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ajanthan on 15-11-26.
 */
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private LayoutInflater mInflate;
    private ArrayList<Alarm> mAlarms;
    private Context mContext;


    public AlarmAdapter(Context context, ArrayList<Alarm> alarms){
        mInflate = LayoutInflater.from(context);
        if (alarms ==null){
            mAlarms= new ArrayList<Alarm>();
            mAlarms.add(new Alarm());
        }else{
            mAlarms=alarms;
        }
        mContext=context;

    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mInflate.inflate(R.layout.alarm_fragment, parent, false);
        AlarmViewHolder holder = new AlarmViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        Alarm alarm= mAlarms.get(position);
        if (alarm.getMinute()<10){
            holder.tvAlarmFragment.setText(alarm.getHour()+":0"+alarm.getMinute());
        }
        else{
            holder.tvAlarmFragment.setText(alarm.getHour()+":"+alarm.getMinute());
        }
        holder.tvAmPmFragment.setText(alarm.getAmPm());
        holder.sActiveFragment.setChecked(alarm.getState());
        holder.tvAlarmActiveDaysFragment.setText(alarm.getActiveDaysAsString());
    }

    @Override
    public int getItemCount() {
        return mAlarms.size();
    }

    public void addAlarm(Alarm alarm){
        mAlarms.add(alarm);
        notifyDataSetChanged();
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvAlarmFragment;
        private TextView tvAmPmFragment;
        private TextView tvAlarmActiveDaysFragment;
        private Switch sActiveFragment;

        public AlarmViewHolder(View itemView) {
            super(itemView);
            tvAlarmFragment = (TextView) itemView.findViewById(R.id.alarmTimeFragment);
            tvAmPmFragment = (TextView) itemView.findViewById(R.id.amPmFragment);
            tvAlarmActiveDaysFragment = (TextView) itemView.findViewById(R.id.alarmActiveDaysFragment);
            sActiveFragment = (Switch) itemView.findViewById(R.id.activeFragment);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext,"Alarm Clicked: "+this.getAdapterPosition(),Toast.LENGTH_SHORT).show();
            if(sActiveFragment.isChecked()){
                sActiveFragment.setChecked(false);
                mAlarms.get(this.getAdapterPosition()).setState(false);
            }else{
                sActiveFragment.setChecked(true);
                mAlarms.get(this.getAdapterPosition()).setState(true);
            }

        }
    }
}
