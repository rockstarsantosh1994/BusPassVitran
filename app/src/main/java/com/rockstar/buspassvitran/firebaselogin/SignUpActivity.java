package com.rockstar.buspassvitran.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.rockstar.buspassvitran.R;


public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void signUp(View view) {
        Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
