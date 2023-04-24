package edu.hebut.dundun;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.math.BigDecimal;
import java.util.Date;

import edu.hebut.dundun.entity.WaterIntake;
import edu.hebut.dundun.service.RemindService;

/**
 * 喝水页面（主页面）
 */
public class DrinkActivity extends BaseActivity {

    private String TAG = "DrinkActivity";
    private float drinkTarget;
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

        float drinkTarget = preferences.getFloat("drinkTarget", 3000);
        String day = preferences.getString("day", "0");
        float haveDrunk = preferences.getFloat("haveDrunk", 0);
        int drinkTimes = preferences.getInt("drinkTimes", 0);

        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        if (!day.equals(dateFormat.format(date))) {
            Log.d(TAG, "不相等");
            saveWaterIntake(day, haveDrunk, drinkTimes);
            haveDrunk = 0;
            drinkTimes = 0;
            editor.putFloat("haveDrunk", 0);
            editor.putInt("drinkTimes", drinkTimes);
            editor.putString("day", dateFormat.format(date));
            editor.apply();
        }
        this.haveDrunk = haveDrunk;
        this.drinkTarget = drinkTarget;
        this.setValue();
    }

    /**
     * 赋值
     */
    @SuppressLint("SetTextI18n")
    private void setValue() {
        TextView haveDrunkTextView = findViewById(R.id.textView5);
        TextView targetWaterTextView = findViewById(R.id.textView6);
        TextView drinkRateTextView = findViewById(R.id.textView1);

        BigDecimal b = new BigDecimal(haveDrunk / drinkTarget);
        float drinkRate = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

        haveDrunkTextView.setText(this.haveDrunk.toString());
        targetWaterTextView.setText(this.drinkTarget + "");
        drinkRateTextView.setText(drinkRate + "");
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

    private void saveWaterIntake(String day, float value, int drinkTimes) {
        Log.d(TAG, "saveWaterIntake");
        WaterIntake waterIntake = new WaterIntake();
        waterIntake.setDate(day);
        waterIntake.setDrinkTimes(drinkTimes);
        waterIntake.setValue(value);
        waterIntake.save();
    }

}