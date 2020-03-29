package com.rockstar.buspassvitran.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.rockstar.buspassvitran.R;


public class LoginActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spinner=(Spinner)findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,CountryData.countryNames));

        editText=(EditText) findViewById(R.id.editTextPhone);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code= CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                String number= editText.getText().toString().trim();

                if(number.isEmpty() || number.length() >10){
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                String phoneNumber = "+"+code +number;

                Intent intent=new Intent(LoginActivity.this,VerifyPhoneActivity.class);
                intent.putExtra("phonenumber",phoneNumber);
                startActivity(intent);
            }
        });
    }

    /*public void signup(View view) {
        Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(intent);
    }*/
}
