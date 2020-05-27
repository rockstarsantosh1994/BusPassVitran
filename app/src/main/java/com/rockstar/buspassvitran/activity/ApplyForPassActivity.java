package com.rockstar.buspassvitran.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rockstar.buspassvitran.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ApplyForPassActivity extends AppCompatActivity  implements
        View.OnClickListener{

    EditText et_name,et_fathername,et_dob,et_age,et_gender,et_address,et_mobileno,et_fromdate,et_selectmonth,et_amount,et_todate,et_selectbuspasstype,et_source,et_destination;
      String st_name,st_fathername,st_dob,st_age,st_gender,st_address,st_mobileno,st_fromdate,st_selectmonth,st_amount,st_todate,st_selectbuspasstype,st_source,st_destination;
    Button submit,btn_choosefile;
    private int mYear, mMonth, mDay;
    String selectOption="";
    static int positionValue;
    TextView tv_file;

    Boolean flag = false;

    DatePickerDialog datePickerDialog;

    public Intent myFileIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_pass);

        et_name=(EditText)findViewById(R.id.et_name);
        et_fathername=(EditText)findViewById(R.id.et_fathername);
        et_dob=(EditText)findViewById(R.id.et_dob);
        et_age=(EditText)findViewById(R.id.et_age);
        et_gender=(EditText)findViewById(R.id.et_gender);
        et_address=(EditText)findViewById(R.id.et_address);
        et_mobileno=(EditText)findViewById(R.id.et_mobileno);
        et_fromdate=(EditText)findViewById(R.id.et_fromdate);
        et_selectmonth=(EditText)findViewById(R.id.et_selectmonth);
        et_amount=(EditText)findViewById(R.id.et_amount);
        et_todate=(EditText)findViewById(R.id.et_todate);
        et_source=(EditText)findViewById(R.id.et_source);
        et_destination=(EditText)findViewById(R.id.et_destination);
        et_selectbuspasstype=(EditText)findViewById(R.id.et_selectbuspasstype);
        tv_file=(TextView) findViewById(R.id.tv_filename);
        submit=(Button)findViewById(R.id.btn_submit);
        btn_choosefile=(Button)findViewById(R.id.btn_choosefile);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean flag = true;
                if (!MyValidator1.isValidField(et_name)){
                    flag = false;
                }
                if(!MyValidator1.isValidField(et_fathername)){
                    flag=false;
                }
                if (!MyValidator1.isValidMobile(et_mobileno)){
                    flag = false;
                }
                if (!MyValidator1.isValidField(et_address)){
                    flag = false;
                }
                if (!MyValidator1.isValidField(et_source)){
                    flag = false;
                }
                if (!MyValidator1.isValidField(et_destination)){
                    flag = false;
                }
                if(flag==true){
                    Intent intent=new Intent (ApplyForPassActivity.this, GenerateQrCodeActivity.class);
                    st_name=et_name.getText().toString().trim();
                    st_fathername=et_fathername.getText().toString().trim();
                    st_dob=et_dob.getText().toString().trim();
                    st_age=et_age.getText().toString().trim();
                    st_gender=et_gender.getText().toString().trim();
                    st_address=et_address.getText().toString().trim();
                    st_mobileno=et_mobileno.getText().toString().trim();
                    st_fromdate=et_fromdate.getText().toString().trim();
                    st_selectmonth=et_selectmonth.getText().toString().trim();
                    st_todate=et_todate.getText().toString().trim();
                    st_amount=et_amount.getText().toString().trim();
                    st_source=et_source.getText().toString().trim();
                    st_destination=et_destination.getText().toString().trim();
                    st_selectbuspasstype=et_selectbuspasstype.getText().toString().trim();


                    intent.putExtra("i_name",st_name);
                    intent.putExtra("i_fathername",st_fathername);
                    intent.putExtra("i_dob",st_dob);
                    intent.putExtra("i_age",st_age);
                    intent.putExtra("i_gender",st_gender);
                    intent.putExtra("i_address",st_address);
                    intent.putExtra("i_mobileno",st_mobileno);
                    intent.putExtra("i_fromdate",st_fromdate);
                    intent.putExtra("i_selectmonth",st_selectmonth);
                    intent.putExtra("i_todate",st_todate);
                    intent.putExtra("i_amount",st_amount);
                    intent.putExtra("i_source",st_source);
                    intent.putExtra("i_destination",st_destination);
                    intent.putExtra("i_selectbuspasstype",st_selectbuspasstype);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ApplyForPassActivity.this, "Something is missing", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_choosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    myFileIntent=new Intent(Intent.ACTION_GET_CONTENT);
                    myFileIntent.setType("*/*");
                    startActivityForResult(myFileIntent,3);

            }
        });

        et_dob.setOnClickListener((View.OnClickListener) this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 3:
                if (requestCode == RESULT_OK) {
                    String path = data.getData().getPath();
                    tv_file.setText(path);
                    Toast.makeText(this, "" + path, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {

        if (v == et_dob) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);



            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            et_dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            //Date currentDate = new Date();
                            int age = Calendar.getInstance().get(Calendar.YEAR)- year;
                            et_age.setText( new StringBuilder().append("")
                                    .append(age).append(""));

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }


    }

    public void selectGender(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Gender");
        final String[] paymentOptions = new String[]{"Male", "Female", "Transgender"};
        builder.setSingleChoiceItems(paymentOptions, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                selectOption = paymentOptions[position];

                Toast.makeText(ApplyForPassActivity.this, ""+positionValue, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(MainActivity.this, "Selected option : "+selecteOption, Toast.LENGTH_LONG).show();
                et_gender.setText(""+selectOption);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


    public void selectMonth(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Month");
        final String[] paymentOptions = new String[]{"One Month", "Two Month", "Six Month"};
        builder.setSingleChoiceItems(paymentOptions, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int pos) {
                selectOption = paymentOptions[pos];
                positionValue=pos;
                Toast.makeText(ApplyForPassActivity.this, "position"+pos, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(ApplyForPassActivity.this, "Selected position: "+i, Toast.LENGTH_LONG).show();
                et_selectmonth.setText(""+selectOption);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

        String getFromDate=et_fromdate.getText().toString().trim();
        String getMonth=et_selectmonth.getText().toString().trim();
        //Toast.makeText(this, ""+getFromDate+"\n"+getMonth, Toast.LENGTH_SHORT).show();
        if(positionValue==0){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 30);
            String newDate = sdf.format(cal.getTime());
            et_todate.setText(""+newDate);
            et_amount.setText("300");
        }

        else if(positionValue==1){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 60);
            String newDate = sdf.format(cal.getTime());
            et_todate.setText(""+newDate);
            et_amount.setText("600");
        }

        else if(positionValue==2){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 180);
            String newDate = sdf.format(cal.getTime());
            et_todate.setText(""+newDate);
            et_amount.setText("1500");
        }

    }

    public void fromDate(View view) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        et_fromdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }


    public void toDate(View view) {


    }

    public void selectbuspasstype(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Gender");
        final String[] paymentOptions = new String[]{"Break Pass", "Service Pass", "Student Pass"};
        builder.setSingleChoiceItems(paymentOptions, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                selectOption = paymentOptions[position];

                Toast.makeText(ApplyForPassActivity.this, ""+positionValue, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(MainActivity.this, "Selected option : "+selecteOption, Toast.LENGTH_LONG).show();
                et_selectbuspasstype.setText(""+selectOption);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


}




