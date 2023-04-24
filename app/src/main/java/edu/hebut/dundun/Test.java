package edu.hebut.dundun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

import edu.hebut.dundun.entity.WaterIntake;

public class Test extends AppCompatActivity {
    private String TAG = "Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        onAddClick();
        onShowClick();
    }

    private void onAddClick() {
        Button button = findViewById(R.id.button_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }

    private void onShowClick() {
        Button button = findViewById(R.id.button_show);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
    }

    private void add() {
        WaterIntake waterIntake = new WaterIntake();
        waterIntake.setDate(1 + "");
        waterIntake.setValue(200);
        waterIntake.save();
        Log.d(TAG, "add");
    }

    private void show() {
        List<WaterIntake> list = LitePal.findAll(WaterIntake.class);
        for (int i = 0; i < list.size(); i++) {
            Log.d(TAG, list.get(i).getId() + "");
        }
    }
}