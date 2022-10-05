package com.firstapp.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class BroadCastActivity extends AppCompatActivity {

    AirplaneModeReceiver airplaneModeChangeReceiver = new AirplaneModeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity);
    }

    @Override
    protected void onStart(){
        super.onStart();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeChangeReceiver, filter);
    }

    @Override
    protected void onStop(){
        super.onStop();
        unregisterReceiver(airplaneModeChangeReceiver);
    }

}