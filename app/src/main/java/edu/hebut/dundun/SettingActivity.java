package edu.hebut.dundun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.Objects;

/**
 * 设置
 */

public class SettingActivity extends AppCompatActivity {

    private final String TAG = "SettingActivity";
    private ConstraintLayout firstRemind;
    private ConstraintLayout finalRemind;
    private ConstraintLayout intervalTime;
    private ConstraintLayout selectedConstraintLayout;
    private String name = "firstRemindTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setRemindItem();
        this.onRemindItemClick();
        onTitleClick();
    }

    /**
     * 监听点击事件
     */
    private void onRemindItemClick() {

        this.firstRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "finalRemindClick");
                showTimePicker();
                selectedConstraintLayout = firstRemind;
            }
        });

        this.finalRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "finalRemindClick");
                showTimePicker();
                selectedConstraintLayout = finalRemind;
                name = "finalRemindTime";
            }
        });

        this.intervalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "intervalTime");
                showIntervalTimePicker();
                selectedConstraintLayout = intervalTime;
                name = "intervalTime";
            }
        });
    }

    /**
     * 初始化
     */
    private void setRemindItem() {
        String firstRemindTime = getData("firstRemindTime");
        String finalRemindTime = getData("finalRemindTime");
        String intervalTime = getData("intervalTime");
        this.firstRemind = findViewById(R.id.firstRemind);
        this.finalRemind = findViewById(R.id.finalRemind);
        this.intervalTime = findViewById(R.id.intervalTime);

        if (Objects.equals(firstRemindTime, "")) {
            firstRemindTime = "8:00";
        }
        if (Objects.equals(finalRemindTime, "")) {
            finalRemindTime = "22:00";
        }
        if (Objects.equals(intervalTime, "")) {
            intervalTime = "2.0小时";
        }

        String times = getTimes(firstRemindTime, finalRemindTime, intervalTime);
        setValue("第一杯水时间", "建议设置为您的起床时间", firstRemindTime, this.firstRemind);
        setValue("最后一次提醒时间", "建议设置为您睡觉的前半个小时", finalRemindTime, this.finalRemind);
        setValue("每隔多久提醒", times, intervalTime, this.intervalTime);
    }

    /**
     * 赋值
     *
     * @param mainTitle
     * @param smallTitle
     * @param time
     * @param constraintLayout
     */
    private void setValue(String mainTitle, String smallTitle, String time, ConstraintLayout constraintLayout) {
        TextView mainTitleTextView = constraintLayout.findViewById(R.id.mainTitle);
        TextView smallTitleTextView = constraintLayout.findViewById(R.id.smallTitle);
        TextView timeTextView = constraintLayout.findViewById(R.id.time);
        mainTitleTextView.setText(mainTitle);
        smallTitleTextView.setText(smallTitle);
        timeTextView.setText(time);
    }

    /**
     * 根据开始时间和结束时间，间隔时间计算提醒次数
     *
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @param intervalTime 间隔时间
     * @return 提醒次数
     */
    private String getTimes(String startTime, String endTime, String intervalTime) {
        double dStartTime = timeStringToDouble(startTime);
        double dEndTime = timeStringToDouble(endTime);
        double dIntervalTime = timeStringToDouble(intervalTime);

        int times = 1;
        for (; dStartTime < dEndTime; times++) {
            dStartTime += dIntervalTime;
        }
        return "每日将提醒" + times + "次";
    }

    /**
     * 时间选择弹窗
     */
    private void showTimePicker() {
        new TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = hourOfDay + ":" + minute;
                if (minute < 10) {
                    time = hourOfDay + ":0" + minute;
                }
                SettingActivity.this.setTime(time);
            }
        }, 0, 0, true).show();
    }

    /**
     * 对time赋值
     *
     * @param time 时间/次数
     */
    private void setTime(String time) {
        TextView timeTextView = this.selectedConstraintLayout.findViewById(R.id.time);
        timeTextView.setText(time);
        saveData(time);
        Log.d(TAG, time);
    }

    /**
     * 选择间隔时间
     */
    private void showIntervalTimePicker() {
        final String[] selectedNumber = new String[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提醒间隔时长(小时)");
        String[] numbers = {"0.5", "1", "1.5", "2", "2.5", "3"};

        builder.setSingleChoiceItems(numbers, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedNumber[0] = numbers[which] + "小时";
            }
        });
        //设置正面按钮
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "确认");
                dialog.dismiss();
                SettingActivity.this.setTime(selectedNumber[0]);
            }
        });
        //设置反面按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }

    /**
     * 存数据
     *
     * @param value 值
     */
    private void saveData(String value) {
        SharedPreferences.Editor editor = getSharedPreferences("dundun_data",
                MODE_PRIVATE).edit();
        editor.putString(name, value);
        editor.apply();
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

    public void onTitleClick() {
        TitleBar titleBar = findViewById(R.id.titleBar);
        titleBar.addAction(new TitleBar.ImageAction(R.drawable.pen32) {
            @Override
            public void performAction(View view) {
                Log.d(TAG,"clickPen");
                Intent intent = new Intent(SettingActivity.this,BaseInformationSelect.class);
                startActivity(intent);
            }
        });
        titleBar.setLeftImageResource(R.drawable.icon_back_white);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
    }

}
