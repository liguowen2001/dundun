package edu.hebut.dundun.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import edu.hebut.dundun.DrinkActivity;
import edu.hebut.dundun.R;

public class RemindService extends Service {

    private String TAG = "RemindService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "startService");
        remind();
        repeat();
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "destroyService");
    }

    private void remind() {
        Intent intent = new Intent(this, DrinkActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        String channelId = createNotificationChannel("my_channel_ID", "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("通知")
                .setContentText("收到一条消息")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.drop)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(100, notification.build());

    }

    private String createNotificationChannel(String channelID, String channelNAME, int level) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channelID, channelNAME, level);
        manager.createNotificationChannel(channel);
        return channelID;
    }

    @SuppressLint("ShortAlarm")
    private void repeat() {
        Log.d(TAG, "repeat");
        int intervalMillis = 500000;
        Intent i = new Intent();
        i.setClass(this, RemindService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarmMgr = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + intervalMillis,
                intervalMillis, pi);
    }

    /**
     * 读数据
     *
     * @param name 数据名称
     * @return 返回数据值
     */
    private String getData(String name) {
        SharedPreferences preferences = getSharedPreferences("dundun_data", MODE_PRIVATE);
        return preferences.getString(name, "");
    }

    /**
     * String 类型时间，次数转化为Double类型
     *
     * @param stringTime
     * @return
     */
    private double timeStringToDouble(String stringTime) {
        double floatTime = 0.0;
        String[] temp;
        temp = stringTime.split("小时");
        temp = temp[0].split(":");
        if (temp.length == 1) {
            floatTime = Double.parseDouble(temp[0]);
        } else {
            int a = Integer.parseInt(temp[0]);
            int b = Integer.parseInt(temp[1]);
            floatTime = a + b / 60F;
        }
        Log.d(TAG, floatTime + "");
        return floatTime;
    }


}