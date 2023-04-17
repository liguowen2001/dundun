package edu.hebut.dundun;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.Date;
import java.util.Objects;

import edu.hebut.dundun.service.RemindService;

/**
 * 喝水页面（主页面）
 */
public class DrinkActivity extends BaseActivity {

    private String TAG = "DrinkActivity";
    private Integer targetWater;
    private Float haveDrunk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        initView();
        onCubClick();
        onTitleClick();
        startService();
    }

    /**
     * 初始化页面
     */
    private void initView() {
        SharedPreferences preferences = getSharedPreferences("dundun_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("dundun_data",
                MODE_PRIVATE).edit();

        float weight = preferences.getFloat("weight", 0);
        int exercise = preferences.getInt("exercise", 0);
        String day = preferences.getString("day", "0");
        float haveDrunk = preferences.getFloat("haveDrunk", 0);
        Log.d(TAG, "haveDrunk" + haveDrunk);
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        if (!Objects.equals(dateFormat.format(date), day)) {
            haveDrunk = 0;
            editor.putFloat("haveDrunk", 0);
            editor.apply();
        }
        this.haveDrunk = haveDrunk;
        this.targetWater = 3000;
        this.setValue();
        Log.d(TAG, weight + "  " + exercise);

    }

    /**
     * 赋值
     */
    @SuppressLint("SetTextI18n")
    private void setValue() {
        TextView haveDrunkTextView = findViewById(R.id.textView5);
        TextView targetWaterTextView = findViewById(R.id.textView6);

        haveDrunkTextView.setText(this.haveDrunk.toString());
        targetWaterTextView.setText(this.targetWater.toString());
    }

    private void onCubClick() {
        ImageView image = findViewById(R.id.cub);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "startWaterActivity");
                Intent intent = new Intent(DrinkActivity.this, WaterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "restart");
        this.initView();
    }

    public void onTitleClick() {
        TitleBar titleBar = findViewById(R.id.titleBar);
        titleBar.addAction(new TitleBar.ImageAction(R.drawable.pen32) {
            @Override
            public void performAction(View view) {
                Log.d(TAG, "clickPen");
                Intent intent = new Intent(DrinkActivity.this, BaseInformationSelect.class);
                startActivity(intent);
            }
        });
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrinkActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("ShortAlarm")
    private void startService() {
        Intent intent = new Intent(this, RemindService.class);
        startService(intent);
    }

}