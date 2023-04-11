package edu.hebut.dundun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

public class WaterActivity extends AppCompatActivity {

    private final String TAG = "WaterActivity";
    private int addOrReduce = 0;
    private View selectedView;

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
                MaterialEditText materialEditText = findViewById(R.id.waterVolume);
                float haveDrunk = Float.parseFloat(materialEditText.getEditValue());
                SharedPreferences preferences = getSharedPreferences("dundun_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = getSharedPreferences("dundun_data",
                        MODE_PRIVATE).edit();
                int day = preferences.getInt("day", 0);
                Date date = new Date();
                if (day == date.getDay()) {
                    haveDrunk += preferences.getFloat("haveDrunk", 0);
                } else {
                    editor.putInt("day", date.getDay());
                }
                editor.putFloat("haveDrunk", haveDrunk);
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
                    break;
                case 2:
                    setColor(v);
                    addOrReduce = 2;
                    break;
                case 3:
                    setColor(v);
                    addOrReduce = 3;
                    break;
                case 4:
                    setColor(v);
                    addOrReduce = 4;
                    break;
                case 5:
                    setColor(v);
                    addOrReduce = 5;
                    break;
                case 6:
                    setColor(v);
                    addOrReduce = 6;
                    break;
                case 7:
                    setColor(v);
                    addOrReduce = 7;
                    break;
                case 8:
                    setColor(v);
                    addOrReduce = 8;
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