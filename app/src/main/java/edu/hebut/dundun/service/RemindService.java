package edu.hebut.dundun.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.hebut.dundun.DrinkActivity;
import edu.hebut.dundun.R;
import edu.hebut.dundun.entity.RemindText;

public class RemindService extends Service {

    private String TAG = "RemindService";
    private double firstRemindTime;
    private double finalRemindTime;
    private double intervalTime;
    private int intervalMillis;
    private Long oneHour = 60 * 60 * 1000L;
    private RemindText remindText;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "startService");
        getRemindText();
        getData();
        SharedPreferences preferences = getSharedPreferences("dundun_data", MODE_PRIVATE);
        if (preferences.getBoolean("isRemind", false)) {
            repeat();
            remind();
        }
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
                .setContentTitle(remindText.getTitle())
                .setContentText(remindText.getText())
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
        updateIntervalMillis();
        Log.d(TAG, "repeat");
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
     */
    private void getData() {
        SharedPreferences preferences = getSharedPreferences("dundun_data", MODE_PRIVATE);
        firstRemindTime = timeStringToDouble(preferences.getString("firstRemindTime", "8:00"));
        finalRemindTime = timeStringToDouble(preferences.getString("finalRemindTime", "22:00"));
        intervalTime = timeStringToDouble(preferences.getString("intervalTime", "2小时"));
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

    private void updateIntervalMillis() {

        Date date = new Date();
        @SuppressLint("SimpleDateFormat") android.icu.text.SimpleDateFormat dateFormat = new android.icu.text.SimpleDateFormat("YYYY-MM-dd");
        Long time = date2TimeStamp(dateFormat.format(date), "yyyy-MM-dd");
        Log.d(TAG, date.getTime() + "");
        Log.d(TAG, time + (int) (finalRemindTime * oneHour) + "");
        if (date.getTime() < time + firstRemindTime * oneHour) {
            intervalMillis = (int) (time + firstRemindTime * oneHour - date.getTime());
        } else if (date.getTime() < (int) (finalRemindTime * oneHour)) {
            if (date.getTime() + intervalTime * oneHour > firstRemindTime) {
                intervalMillis = (int) (time + finalRemindTime * oneHour - date.getTime());
            } else {
                intervalMillis = (int) (intervalTime * oneHour);
            }
        } else {
            intervalMillis = (int) (time + 24 * oneHour - date.getTime() + firstRemindTime * oneHour);
        }
        Log.d(TAG, intervalMillis + "");
    }

    public static Long date2TimeStamp(String date_str, String format) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date_str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    private void getRemindText() {
        List<RemindText> remindTexts = LitePal.findAll(RemindText.class);
        if (remindTexts.isEmpty()) {
            RemindText remindText = new RemindText();
            remindText.setTitle("喝水提醒");
            remindText.setText("该喝水了！");
            this.remindText = remindText;
        } else {
            remindText = remindTexts.get((int) (Math.random() * remindTexts.size()));
        }
    }
}