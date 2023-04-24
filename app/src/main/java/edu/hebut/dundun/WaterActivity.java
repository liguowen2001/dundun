package edu.hebut.dundun;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import java.util.Date;

/**
 * 添加喝水
 */
public class WaterActivity extends AppCompatActivity {

    private final String TAG = "WaterActivity";
    private float addOrReduce = 0;
    private View selectedView;
    private float haveDrunk;
    private int drinkTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        onSubmit();
        onVolumeClick();
        onIconClick();
        onTitleClick();
    }

    private void onSubmit() {
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHaveDrunk();
                SharedPreferences preferences = getSharedPreferences("dundun_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = getSharedPreferences("dundun_data",
                        MODE_PRIVATE).edit();
                String day = preferences.getString("day", "");
                Date date = new Date();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
                if (day.equals(dateFormat.format(date))) {
                    haveDrunk += preferences.getFloat("haveDrunk", 0);
                    drinkTimes = preferences.getInt("drinkTimes", 0) + 1;

                } else {
                    editor.putString("day", dateFormat.format(date));
                }
                editor.putFloat("haveDrunk", haveDrunk);
                editor.putInt("drinkTimes", drinkTimes);
                editor.apply();
                Log.d(TAG, "haveDrunk" + preferences.getFloat("haveDrunk", 0));
                WaterActivity.this.finish();
            }
        });
    }

    /**
     * 饮水量点击
     */
    private void onVolumeClick() {
        MyListener waterVolumeListener = new MyListener();
        RoundButton roundButton100 = findViewById(R.id.roundButton100);
        RoundButton roundButton200 = findViewById(R.id.roundButton);
        RoundButton roundButton300 = findViewById(R.id.roundButton300);
        RoundButton roundButton500 = findViewById(R.id.roundButton500);
        roundButton100.setTag(100);
        roundButton100.setOnClickListener(waterVolumeListener);
        roundButton200.setTag(200);
        roundButton200.setOnClickListener(waterVolumeListener);
        roundButton300.setTag(300);
        roundButton300.setOnClickListener(waterVolumeListener);
        roundButton500.setTag(500);
        roundButton500.setOnClickListener(waterVolumeListener);
    }


    /**
     * 图标点击
     */
    private void onIconClick() {
        MyListener myListener = new MyListener();

        ImageView glass = findViewById(R.id.glass);
        ImageView juice = findViewById(R.id.juice);
        ImageView coffee = findViewById(R.id.coffee);
        ImageView beer = findViewById(R.id.beer);
        ImageView firewater = findViewById(R.id.firewater);
        ImageView steamWater = findViewById(R.id.steamwater);
        ImageView milk = findViewById(R.id.milk);
        ImageView flip = findViewById(R.id.filp);

        glass.setTag(1);
        juice.setTag(2);
        coffee.setTag(3);
        beer.setTag(4);
        firewater.setTag(5);
        steamWater.setTag(6);
        milk.setTag(7);
        flip.setTag(8);

        glass.setOnClickListener(myListener);
        juice.setOnClickListener(myListener);
        coffee.setOnClickListener(myListener);
        beer.setOnClickListener(myListener);
        firewater.setOnClickListener(myListener);
        steamWater.setOnClickListener(myListener);
        milk.setOnClickListener(myListener);
        flip.setOnClickListener(myListener);

    }

    public class MyListener implements View.OnClickListener {
        MaterialEditText materialEditText = findViewById(R.id.waterVolume);

        @Override
        public void onClick(View v) {
            int tag = (Integer) v.getTag(); //找到每个button的标记
            switch (tag) {
                case 100:
                    materialEditText.setText("100");
                    break;
                case 200:
                    materialEditText.setText("200");
                    break;
                case 300:
                    materialEditText.setText("300");
                    break;
                case 500:
                    materialEditText.setText("500");
                    break;
                case 1:
                    setColor(v);
                    addOrReduce = 1;
                    break;
                case 2:
                case 6:
                    setColor(v);
                    addOrReduce = 0.86F;
                    break;
                case 3:
                    setColor(v);
                    addOrReduce = 0.9F;
                    break;
                case 4:
                    setColor(v);
                    addOrReduce = -0.6F;
                    break;
                case 5:
                    setColor(v);
                    addOrReduce = -3;
                    break;
                case 7:
                    setColor(v);
                    addOrReduce = 0.88F;
                    break;
                case 8:
                    setColor(v);
                    addOrReduce = -1.8F;
                    break;
            }
        }

        private void setColor(View v) {
            if (selectedView != null) {
                selectedView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            v.setBackgroundColor(Color.parseColor("#96CBFD"));
            selectedView = v;
        }
    }

    private void getHaveDrunk() {
        MaterialEditText materialEditText = findViewById(R.id.waterVolume);
        haveDrunk = Float.parseFloat(materialEditText.getEditValue()) * addOrReduce;
    }

    public void onTitleClick() {
        TitleBar titleBar = findViewById(R.id.titleBar);
        titleBar.addAction(new TitleBar.ImageAction(R.drawable.pen32) {
            @Override
            public void performAction(View view) {
                Log.d(TAG, "clickPen");
                Intent intent = new Intent(WaterActivity.this, BaseInformationSelect.class);
                startActivity(intent);
            }
        });
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaterActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }


}