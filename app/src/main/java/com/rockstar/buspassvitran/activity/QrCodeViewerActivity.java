package com.rockstar.buspassvitran.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Vibrator;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.rockstar.buspassvitran.R;
import com.rockstar.buspassvitran.sharedpreference.SPLib;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.SEND_SMS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class QrCodeViewerActivity extends AppCompatActivity {

    String st_subphonenumber,st_subfirstname,st_subtodate;
    SPLib splib;

    private static final int REQUEST_SMS = 0;
    private BroadcastReceiver sentStatusReceiver, deliveredStatusReceiver;
    public static int counter=0;
    private Vibrator vibrator;

    boolean doubleBackOnce=false;

    Date dateWithoutTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_viewer);
        splib=new SPLib(this);
        //initiate scan with our custom scan activity
        new IntentIntegrator(QrCodeViewerActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();

        vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //We will get scan results here
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //show dialogue with result
                showResultDialogue(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //method to construct dialogue with scan results
    public void showResultDialogue(final String result) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Scan Result")
                .setMessage(" " + result)
                .setPositiveButton("Send Notification", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Scan Result", result);
                        clipboard.setPrimaryClip(clip);

                        st_subphonenumber=result.substring(result.length() - 10);
                        st_subtodate=result.substring(result.length() - 25);
                        st_subfirstname=result.substring(0,8);
                        String st_subsubtodate=st_subtodate.substring(0,10);
                        //shared preference data saving....
                        splib.sharedpreferences.edit().putString(SPLib.Key.Sp_subphonenumber,st_subphonenumber).commit();
                        splib.sharedpreferences.edit().putString(SPLib.Key.Sp_subfirstname,st_subfirstname).commit();
                        splib.sharedpreferences.edit().putString(SPLib.Key.Sp_subsubtodate,st_subsubtodate).commit();
                        //viewing shared preference data
                        Log.d("mytag", "Shared Preference Value "+splib.sharedpreferences.getString(SPLib.Key.Sp_subphonenumber,"data available"));
                        Log.d("mytag", "\nOn Scan Result: "+result);
                        Log.d("mytag", "\nThe SubString: "+st_subphonenumber);
                        Log.d("mytag", "\nSub name "+st_subfirstname);
                        Log.d("mytag", "\nto date"+st_subtodate);
                        Log.d("mytag", "\nFianl to date is "+st_subsubtodate);

                        getSmsProgramtically();
                        Toast.makeText(QrCodeViewerActivity.this, "SMS Sent Successfully...", Toast.LENGTH_LONG).show();
                        vibrator.vibrate(1500);

                        Toast.makeText(QrCodeViewerActivity.this, "Result copied to clipboard", Toast.LENGTH_SHORT).show();
                       /* Intent intent=new Intent(QrCodeViewerActivity.this,ScannerActivity.class);
                        startActivity(intent);
*/
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        Intent intent=new Intent(QrCodeViewerActivity.this,ScannerActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .show();
    }


    //Getting SMS using our own app
    public void getSmsProgramtically(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int hasSMSPermission = checkSelfPermission(SEND_SMS);
            if (hasSMSPermission != PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(SEND_SMS)) {
                    showMessageOKCancel("You need to allow access to Send SMS",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[] {SEND_SMS},
                                                REQUEST_SMS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[] {SEND_SMS},
                        REQUEST_SMS);
                return;
            }
            sendMySMS();
        }
    }

    //Getting Permission of sms using alert dialog
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new androidx.appcompat.app.AlertDialog.Builder(QrCodeViewerActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void sendMySMS() {


        String strnum1=splib.sharedpreferences.getString(SPLib.Key.Sp_subphonenumber,"Sp_subphonenumber");
        String firstname=splib.sharedpreferences.getString(SPLib.Key.Sp_subfirstname,"Sp_subfirstname");
        String todate=splib.sharedpreferences.getString(SPLib.Key.Sp_subsubtodate,"Sp_subsubtodate");
        //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
       /* SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        Date date = Calendar.getInstance().getTime();
        String sDate = format.format(date);//31-12-9999
        Log.d("mytag", "todays date:" +sDate);

        /*String st_todatedd=todate.substring(0,2);
        String st_todatemm=todate.substring(3,5);
        String st_todateyyyy=todate.substring(6,10);

        Log.d("mytag", "sendMySMS: split to_dates "+st_todatedd+st_todatemm+st_todateyyyy);

        String st_sdatedd=sDate.substring(0,2);
        String st_sdatemm=sDate.substring(3,5);
        String st_sdateyyyy=sDate.substring(6,10);

        Log.d("mytag", "sendMySMS: split S dates are "+st_sdatedd+st_sdatemm+st_sdateyyyy);

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.set(2019, Integer.parseInt(st_sdatemm), Integer.parseInt(st_sdatedd));
        cal1.set(2019, Integer.parseInt(st_todatemm), Integer.parseInt(st_todatedd));

        // Get the represented date in milliseconds
        long millis1 = cal1.getTimeInMillis();
        long millis2 = cal2.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = millis2 - millis1;

        // Calculate difference in days
        long diffDays = diff / (24 * 60 * 60 * 1000);*/

        Integer i=30;
        i--;
        String message ="Hii "+firstname+", \n Your Pass is Punched.  You have "+i+" days left.  Your Renewe date is"+todate;



        //Check if the phoneNumber is empty
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            if (strnum1.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Enter a Valid Phone Number", Toast.LENGTH_SHORT).show();
            } else {

                SmsManager sms = SmsManager.getDefault();
                // if message length is too long messages are divided
                List<String> messages = sms.divideMessage(message);
                for (String msg : messages) {

                    PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);
                    PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);
                    sms.sendTextMessage(strnum1, null, msg, sentIntent, deliveredIntent);

                }
            }
        }
    }

    //Getting Permission to access sms services of app
    //@RequiresApi(api = Build.VERSION_CODES.DONUT)
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_SMS:
                if (grantResults.length > 0 &&  grantResults[0] == PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access sms", Toast.LENGTH_SHORT).show();
                    sendMySMS();

                }else {
                    Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and sms", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(SEND_SMS)) {
                            showMessageOKCancel("You need to allow access to both the permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{SEND_SMS},
                                                        REQUEST_SMS);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    //Message status
    public void onResume() {
        super.onResume();
        sentStatusReceiver=new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent arg1) {
                String s = "Unknown Error";
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        s = "Message Sent Successfully !!";
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        s = "Generic Failure Error";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        s = "Error : No Service Available";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        s = "Error : Null PDU";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        s = "Error : Radio is off";
                        break;
                    default:
                        break;
                }
                Toast.makeText(QrCodeViewerActivity.this, ""+s, Toast.LENGTH_LONG).show();


            }
        };
        deliveredStatusReceiver=new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent arg1) {
                String s = "Message Not Delivered";
                switch(getResultCode()) {
                    case Activity.RESULT_OK:
                        s = "Message Delivered Successfully";
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                Toast.makeText(QrCodeViewerActivity.this, ""+s, Toast.LENGTH_LONG).show();

                /*phoneEditText.setText("");
                messageEditText.setText("");*/
            }
        };
        registerReceiver(sentStatusReceiver, new IntentFilter("SMS_SENT"));
        registerReceiver(deliveredStatusReceiver, new IntentFilter("SMS_DELIVERED"));
    }


    public void onPause() {
        super.onPause();
        unregisterReceiver(sentStatusReceiver);
        unregisterReceiver(deliveredStatusReceiver);
    }

    private boolean checkPermission() {
        return ( ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS ) == PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{SEND_SMS}, REQUEST_SMS);
    }






}



