package edu.hebut.dundun;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.actionbar.TitleUtils;

import java.util.Date;

public class DrinkActivity extends BaseActivity {

    private String TAG = "DrinkActivity";
    private Integer targetWater;
    private Float haveDrunk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        initView();
        //onNextRemindClick();
        onCubClick();
        onTitleClick();
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
        int day = preferences.getInt("day", 0);
        float haveDrunk = preferences.getFloat("haveDrunk", 0);
        Log.d(TAG, "haveDrunk" + haveDrunk);
        Date date = new Date();
        if (date.getDay() != day) {
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

    /**
     * 绑定点击
     */
//    private void onNextRemindClick() {
//        TextView nextRemind = findViewById(R.id.nextRemind);
//        nextRemind.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "setting");
//                Intent intent = new Intent(DrinkActivity.this, SettingActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
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
                Log.d(TAG,"clickPen");
                Intent intent = new Intent(DrinkActivity.this,BaseInformationSelect.class);
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

}