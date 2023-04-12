package edu.hebut.dundun;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xuexiang.xui.widget.button.roundbutton.RoundButton;

/**
 * 登录
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        onSignUpClick();
        onLoginClick();
        onPassClick();
    }

    /**
     * 点击登录
     */
    private void onLoginClick() {
        RoundButton loginSubmit = findViewById(R.id.loginSubmit);
        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDrinkActivity();
            }
        });
    }

    /**
     * 点击注册
     */
    private void onSignUpClick() {
        TextView signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onPassClick() {
        RoundButton pass = findViewById(R.id.pass);
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert();
            }
        });
    }

    /**
     * 启动喝水活动
     */
    private void startDrinkActivity() {
        Intent intent = new Intent(LoginActivity.this, DrinkActivity.class);
        startActivity(intent);
    }

    private void alert() {
        AlertDialog alertDialog2 = new AlertDialog.Builder(this)
                .setTitle("确定跳过登录吗")
                .setMessage("登录后可将数据存储在云端，避免数据丢失，否则只在本地存储，你可以随时在设置中登录")
                .setIcon(R.drawable.smile)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startDrinkActivity();
                    }
                })

                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
        alertDialog2.show();
        alertDialog2.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#96CBFD"));
        alertDialog2.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#96CBFD"));

    }
}