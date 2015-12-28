package com.ajanthan.alarmbot.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ajanthan.alarmbot.Activity.AlarmDetailActivity;
import com.ajanthan.alarmbot.AlarmHelper;
import com.ajanthan.alarmbot.AlarmServiceBroadcastReciever;
import com.ajanthan.alarmbot.Fragment.TabFragment1;
import com.ajanthan.alarmbot.Objects.Alarm;
import com.ajanthan.alarmbot.R;
import com.ajanthan.alarmbot.Objects.RealmAlarm;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ajanthan on 15-11-26.
 */
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private LayoutInflater mInflate;
    private ArrayList<Alarm> mAlarms;
    private Context mContext;
    private Realm mRealm;


    public AlarmAdapter(Context context, ArrayList<Alarm> alarms, Realm realm) {
        mInflate = LayoutInflater.from(context);
        if (alarms == null) {
            mAlarms = new ArrayList<Alarm>();
            mAlarms.add(new RealmAlarm());
        } else {
            mAlarms = alarms;
        }
        mContext = context;
        mRealm = realm;
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.alarm_fragment, parent, false);
        AlarmViewHolder holder = new AlarmViewHolder(view, mContext);
        return holder;
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        Alarm alarm = mAlarms.get(position);
        if (alarm.getMinute() < 10) {
            holder.tvAlarmFragment.setText(alarm.getHour() + ":0" + alarm.getMinute());
        } else {
            holder.tvAlarmFragment.setText(alarm.getHour() + ":" + alarm.getMinute());
        }
        holder.tvAmPmFragment.setText(alarm.getAmPm());
        holder.sActiveFragment.setChecked(alarm.getState());
        holder.tvAlarmActiveDaysFragment.setText(alarm.getActiveDays());
    }

    @Override
    public int getItemCount() {
        return mAlarms.size();
    }

    public void update(ArrayList<Alarm> alarms) {
        mAlarms = alarms;
        notifyDataSetChanged();
        mRealm.close();
    }


    class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvAlarmFragment;
        private TextView tvAmPmFragment;
        private TextView tvAlarmActiveDaysFragment;
        private Switch sActiveFragment;
        private Context mContext;

        public AlarmViewHolder(View itemView, Context context) {
            super(itemView);
            tvAlarmFragment = (TextView) itemView.findViewById(R.id.alarmTimeFragment);
            tvAmPmFragment = (TextView) itemView.findViewById(R.id.amPmFragment);
            tvAlarmActiveDaysFragment = (TextView) itemView.findViewById(R.id.alarmActiveDaysFragment);
            sActiveFragment = (Switch) itemView.findViewById(R.id.activeFragment);
            mContext = context;
            itemView.setOnClickListener(this);
            setOnChangeStateListener();
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "Alarm Clicked: " + this.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(mContext, AlarmDetailActivity.class);
            i.putExtra("cmd", "edit");
            i.putExtra("key", mAlarms.get(getAdapterPosition()).getKey());
            mContext.startActivity(i);

        }

        private void setOnChangeStateListener() {
            sActiveFragment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRealm.beginTransaction();
                    if (mAlarms.get(getAdapterPosition()).getState()) {
                        mAlarms.get(getAdapterPosition()).setState(false);
                    } else {
                        mAlarms.get(getAdapterPosition()).setState(true);
                        Toast.makeText(mContext,
                                AlarmHelper.getTimeUntilNextAlarmMessage(mAlarms.get(getAdapterPosition()).getHour(),
                                        mAlarms.get(getAdapterPosition()).getMinute(),
                                        mAlarms.get(getAdapterPosition()).getAmPm(),
                                        mAlarms.get(getAdapterPosition()).getRepeatWeekly(),
                                        mAlarms.get(getAdapterPosition()).getActiveDays()), Toast.LENGTH_LONG).show();
                    }
                    mRealm.commitTransaction();
                    AlarmHelper.callAlarmScheduleService(mContext);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
