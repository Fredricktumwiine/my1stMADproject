package com.firstapp.madproject;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    private Timer mTimer;
    private Handler mHandler = new Handler();

    private static final int TIMER_INTERVAL = 40000; // 2 Minute
    private static final int TIMER_DELAY = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        if (mTimer != null)
            mTimer = null;
            mTimer = new Timer();

        mTimer.scheduleAtFixedRate(new DisplayToastTimerTask(), TIMER_DELAY, TIMER_INTERVAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mTimer.cancel();
    }

    private class DisplayToastTimerTask extends TimerTask {

        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "service is running", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
