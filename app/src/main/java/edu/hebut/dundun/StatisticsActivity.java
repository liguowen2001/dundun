package edu.hebut.dundun;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.litepal.LitePal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.hebut.dundun.entity.WaterIntake;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class StatisticsActivity extends AppCompatActivity {

    private String TAG = "StatisticsActivity";
    List<PointValue> values = new ArrayList<PointValue>();//折线上的点
    List<AxisValue> axisXValues = new ArrayList<AxisValue>();
    float averageDrink = 0;
    float completeRate = 0;
    int drinkTimes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        getData();
    }

    private void initChat() {

        Line line = new Line(values).setColor(Color.parseColor("#96CBFD"));//声明线并设置颜色
        line.setCubic(false);//设置是平滑的还是直的
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);


        LineChartView mChartView = findViewById(R.id.chart);

        mChartView.setInteractive(true);//设置图表是可以交互的（拖拽，缩放等效果的前提）
        mChartView.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);//设置缩放方向
        LineChartData data = new LineChartData();
        Axis axisX = new Axis();//x轴
        Axis axisY = new Axis();//y轴

        axisX.setValues(axisXValues);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        data.setLines(lines);
        mChartView.setLineChartData(data);//给图表设置数据

    }

    private void getData() {
        int totalNumber = 10;
        List<WaterIntake> waterIntakes = LitePal.findAll(WaterIntake.class);
        int length = 0;
        for (WaterIntake waterIntake : waterIntakes) {
            length++;
        }

        if (length > totalNumber) {
            int start = length - totalNumber;
            for (int i = 0; i < length; i++) {
                if (i >= start) {
                    values.add(new PointValue(i - start, waterIntakes.get(i).getValue()));
                    axisXValues.add(new AxisValue(i - start, waterIntakes.get(i).getSimpleDate().toCharArray()));
                }
            }

        } else if (waterIntakes.size() != 0) {

            for (int i = 0; i < length; i++) {
                values.add(new PointValue(i, waterIntakes.get(i).getValue()));
                axisXValues.add(new AxisValue(i, waterIntakes.get(i).getSimpleDate().toCharArray()));
            }
        } else {
            Log.d(TAG,"没有数据");
            values.add(new PointValue(0, 0));
            values.add(new PointValue(1, 1));
            String label = "0";
            axisXValues.add(new AxisValue(0, label.toCharArray()));
            axisXValues.add(new AxisValue(0, label.toCharArray()));
            return;
        }

        SharedPreferences preferences = getSharedPreferences("dundun_data", MODE_PRIVATE);
        float drinkTarget = preferences.getFloat("drinkTarget", 3000);

        averageDrink = preferences.getFloat("averageDrink", 0);
        WaterIntake yesterdayWaterIntake = waterIntakes.get(length - 1);
        drinkTimes = yesterdayWaterIntake.getDrinkTimes();
        completeRate = yesterdayWaterIntake.getValue() / drinkTarget;
        BigDecimal b = new BigDecimal(completeRate);
        completeRate = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        initChat();
        initTextView();
    }

    @SuppressLint("SetTextI18n")
    private void initTextView() {
        TextView averageDrink = findViewById(R.id.textView22);
        TextView completeRate = findViewById(R.id.textView23);
        TextView drinkTimes = findViewById(R.id.textView25);
        averageDrink.setText(this.averageDrink + "");
        completeRate.setText(this.completeRate + "%");
        drinkTimes.setText(this.drinkTimes + "次");
    }


}