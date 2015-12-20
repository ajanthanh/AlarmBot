package com.ajanthan.alarmbot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ajanthan.alarmbot.Objects.Alarm;

/**
 * Created by ajanthan on 15-12-19.
 */
public class AlarmService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
