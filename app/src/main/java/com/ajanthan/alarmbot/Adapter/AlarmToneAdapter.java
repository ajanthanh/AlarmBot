package com.ajanthan.alarmbot.Adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ajanthan.alarmbot.Objects.AlarmTone;
import com.ajanthan.alarmbot.R;

import java.util.ArrayList;

/**
 * Created by ajanthan on 15-11-26.
 */
public class AlarmToneAdapter extends RecyclerView.Adapter<AlarmToneAdapter.AlarmToneViewHolder> {

    private LayoutInflater mInflate;
    private ArrayList<AlarmTone> mAlarmTones;
    private Context mContext;
    private MediaPlayer mediaPlayer;
    private AlarmTone currentAlarmTone;
    private CountDownTimer alarmToneTimer;


    public AlarmToneAdapter(Context context, ArrayList<AlarmTone> alarmTones, String alarmToneName) {
        Log.e("AlarmToneLog", alarmToneName);
        mInflate = LayoutInflater.from(context);
        if (alarmTones == null) {
            mAlarmTones = new ArrayList<AlarmTone>();       //TODO handle no alarm tones case properly
        } else {
            mAlarmTones = alarmTones;
        }
        mContext = context;

        if (alarmToneName.isEmpty()) {
            mAlarmTones.get(0).setActive();
            currentAlarmTone = mAlarmTones.get(0);

        } else {
            for (int i = 0; i < mAlarmTones.size(); i++) {
                if (mAlarmTones.get(i).getName().equals(alarmToneName)) {
                    mAlarmTones.get(i).setActive();
                    currentAlarmTone = mAlarmTones.get(i);
                }
            }
        }
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
        stopCurrentRingTone();
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public AlarmTone getCurrentAlarmTone() {
        return currentAlarmTone;
    }

    public void stopCurrentRingTone() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
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
            if (currentAlarmTone != null) {
                currentAlarmTone.setInActive();
            }
            currentAlarmTone = mAlarmTones.get(this.getAdapterPosition());
            currentAlarmTone.setActive();
            playAlarmTone(currentAlarmTone.getUri());
            notifyDataSetChanged();
        }
    }

    private void playAlarmTone(String notificationUri) {
        if (notificationUri != null) {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            } else {
                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                mediaPlayer.reset();
            }
            try {
                mediaPlayer.setVolume(0.2f, 0.2f);
                mediaPlayer.setDataSource(mContext, Uri.parse(notificationUri));
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mediaPlayer.setLooping(false);
                mediaPlayer.prepare();
                mediaPlayer.start();

                if (alarmToneTimer != null)
                    alarmToneTimer.cancel();
                alarmToneTimer = new CountDownTimer(3000, 3000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        try {
                            if (mediaPlayer.isPlaying())
                                mediaPlayer.stop();
                        } catch (Exception e) {

                        }
                    }
                };
                alarmToneTimer.start();
            } catch (Exception e) {
                try {
                    if (mediaPlayer.isPlaying())
                        mediaPlayer.stop();
                } catch (Exception e2) {

                }
            }
        }
    }
}
