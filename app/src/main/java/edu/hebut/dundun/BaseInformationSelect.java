package edu.hebut.dundun;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.gzuliyujiang.wheelview.widget.WheelView;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.picker.RulerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 体重和运动强度选择
 */
public class BaseInformationSelect extends AppCompatActivity {

    private String TAG = "BaseInformationSelect";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_information_select);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        onButtonClick();
        initView();
        onTitleClick();
    }

    /**
     * 点击按钮提交信息
     */
    public void onButtonClick() {
        Button button = (Button) findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RulerView rulerView = (RulerView) findViewById(R.id.weight);
                WheelView wheelView = (WheelView) findViewById(R.id.wheelView);
                float weight = rulerView.getCurrentValue();
                int exercise = exerciseStringToInt(wheelView.getCurrentItem().toString());
                saveData(weight, exercise);
                Log.d(TAG, "体重" + rulerView.getCurrentValue());
                Log.d(TAG, "运动量" + wheelView.getCurrentItem().toString());
                Intent intent = new Intent(BaseInformationSelect.this, DrinkActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化运动量选择
     */
    public void initView() {
        WheelView wheelView = (WheelView) findViewById(R.id.wheelView);
        List<String> data = new ArrayList<>();
        data.add("不运动（一周几乎不运动）");
        data.add("轻度运动（一周运动2-5小时）");
        data.add("喜欢运动（一周运动5-7小时）");
        data.add("热爱运动（一周运动7小时以上）");
        wheelView.setData(data);
    }

    /**
     * 运动量转化为int
     *
     * @param s
     * @return
     */
    public int exerciseStringToInt(String s) {
        int result = 0;
        switch (s) {
            case ("轻度运动（一周运动2-5小时）"):
                result = 1;
                break;
            case ("喜欢运动（一周运动5-7小时）"):
                result = 2;
                break;
            case ("热爱运动（一周运动7小时以上）"):
                result = 3;
                break;
        }
        return result;

    }

    /**
     * 数据保存
     *
     * @param weight   体重
     * @param exercise 运动量
     */
    private void saveData(float weight, int exercise) {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        SharedPreferences.Editor editor = getSharedPreferences("dundun_data",
                MODE_PRIVATE).edit();
        editor.putFloat("weight", weight);
        editor.putInt("exercise", exercise);
        editor.putString("day", dateFormat.format(date));
        editor.apply();
    }

    public void onTitleClick() {
        TitleBar titleBar = findViewById(R.id.titleBar);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseInformationSelect.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}