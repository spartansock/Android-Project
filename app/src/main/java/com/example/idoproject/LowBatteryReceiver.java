package com.example.idoproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LowBatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BATTERY_LOW"))
            Toast.makeText(context, "Your battery is too low", Toast.LENGTH_SHORT).show();
    }
}