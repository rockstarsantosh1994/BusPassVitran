package com.rockstar.buspassvitran.activity;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rockstar.buspassvitran.R;
import com.rockstar.buspassvitran.saathi.activity.DashBoardActivity;

public class MainActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
    }


    public void share(View view) {
        Intent intent=new Intent(MainActivity.this,QrCodeViewerActivity.class);
        startActivity(intent);
    }

    public void howToApply(View view) {
        Intent intent=new Intent(MainActivity.this, DashBoardActivity.class);
        startActivity(intent);

    }

    public void busPassCenter(View view) {
        Intent intent=new Intent(MainActivity.this,BusPassCenterActivity.class);
        startActivity(intent);
    }

    public void applyForPass(View view) {
        Intent intent=new Intent(MainActivity.this,ApplyForPassActivity.class);
        startActivity(intent);
    }

    public void viewPass(View view) {
        Intent intent=new Intent(MainActivity.this,ViewPassActivity.class);
        startActivity(intent);
    }

    public void aboutWhatsapp(View view) {
        Intent intent=new Intent(MainActivity.this,ImageUpload.class);
        startActivity(intent);

    }
}
