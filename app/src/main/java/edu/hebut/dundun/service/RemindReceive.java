package edu.hebut.dundun.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RemindReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, RemindService.class);
        context.startService(i);
    }
}
