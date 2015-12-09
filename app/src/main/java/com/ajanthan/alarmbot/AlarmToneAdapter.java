package com.ajanthan.alarmbot;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ajanthan on 15-11-26.
 */
public class AlarmToneAdapter extends RecyclerView.Adapter<AlarmToneAdapter.AlarmToneViewHolder> {

    private LayoutInflater mInflate;
    private ArrayList<AlarmTone> mAlarmTones;
    private Context mContext;
    private Ringtone currentRingTone;
    private AlarmTone currentAlarmTone;


    public AlarmToneAdapter(Context context, ArrayList<AlarmTone> alarmTones) {
        mInflate = LayoutInflater.from(context);
        if (alarmTones == null) {
            mAlarmTones = new ArrayList<AlarmTone>();       //TODO handle no alarm tones case properly
        } else {
            mAlarmTones = alarmTones;
        }
        mContext = context;

    }

    @Override
    public AlarmToneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.alarm_tone_fragment, parent, false);
        AlarmToneViewHolder holder = new AlarmToneViewHolder(view, mContext);
        return holder;
    }

    @Override
    public void onBindViewHolder(AlarmToneViewHolder holder, int position) {
        AlarmTone alarmTone = mAlarmTones.get(position);
        holder.rbAlarmToneActive.setChecked(alarmTone.getIsActive());
        holder.tvAlarmToneName.setText(alarmTone.getName());
    }

    @Override
    public int getItemCount() {
        return mAlarmTones.size();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (currentRingTone != null) {
            currentRingTone.stop();
        }
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public AlarmTone getCurrentAlarmTone() {
        return currentAlarmTone;
    }

    public void stopCurrentRingTone(){
        if(currentRingTone!=null){
            currentRingTone.stop();
        }
    }

    class AlarmToneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvAlarmToneName;
        private RadioButton rbAlarmToneActive;
        private Context mContext;

        public AlarmToneViewHolder(View itemView, Context context) {
            super(itemView);
            tvAlarmToneName = (TextView) itemView.findViewById(R.id.alarmToneName);
            rbAlarmToneActive = (RadioButton) itemView.findViewById(R.id.alarmToneActive);
            mContext = context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (currentAlarmTone!=null){
                currentAlarmTone.setInActive();
            }
            currentAlarmTone = mAlarmTones.get(this.getAdapterPosition());
            currentAlarmTone.setActive();
            playAlarmTone(currentAlarmTone.getUri());
            notifyDataSetChanged();
        }
    }

    private void playAlarmTone(String notificationUri) {
        stopCurrentRingTone();
        Uri notification = Uri.parse(notificationUri);
        currentRingTone = RingtoneManager.getRingtone(mContext, notification);
        currentRingTone.play();
    }
}
