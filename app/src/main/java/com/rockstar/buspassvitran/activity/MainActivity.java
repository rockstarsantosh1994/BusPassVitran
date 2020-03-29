package com.rockstar.buspassvitran.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.asmobisoft.digishare.CommonMethods;
import com.rockstar.buspassvitran.CommonMethodsJava;
import com.rockstar.buspassvitran.R;
import com.rockstar.buspassvitran.saathi.activity.DashBoardActivity;
import com.rockstar.buspassvitran.saathi.activity.LoginActivity;

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

    public void logout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("BussPass");
        builder.setMessage("Are you sure want to logout?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        CommonMethodsJava.setPreference(getApplicationContext(), CommonMethodsJava.USER_ID,"DNF");
                        Intent intent= new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(),"See you again!", Toast.LENGTH_LONG).show();

                    }
                });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}
