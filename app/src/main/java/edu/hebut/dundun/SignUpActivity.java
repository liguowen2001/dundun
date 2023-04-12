package edu.hebut.dundun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xuexiang.xui.widget.button.roundbutton.RoundButton;

/**
 * 注册
 */
public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        onSignUpClick();
    }

    private void onSignUpClick() {
        RoundButton signUpButton = findViewById(R.id.signUpSubmit);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, DrinkActivity.class);
                startActivity(intent);
            }
        });
    }
}