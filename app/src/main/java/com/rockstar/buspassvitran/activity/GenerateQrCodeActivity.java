package com.rockstar.buspassvitran.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.MediaScannerConnection;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.rockstar.buspassvitran.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateQrCodeActivity extends AppCompatActivity implements PaymentResultListener{

    String TAG = "GenerateQRCode";
    ImageView qrImage;
    Button  save;
    String st_name,st_fathername,st_dob,st_age,st_gender,st_address,st_mobileno,st_fromdate,st_selectmonth,st_amount,st_todate,st_selectbuspasstype,st_source,st_destination;
    String combined;
   // String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    int amount;
    public final static int QRcodeWidth = 500 ;
    private static final String IMAGE_DIRECTORY = "/QRcodeDemonuts";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr_code);
        qrImage = (ImageView) findViewById(R.id.QR_Image);
        save = (Button) findViewById(R.id.btn_save);
        st_name=getIntent().getExtras().getString("i_name");
        st_fathername=getIntent().getExtras().getString("i_fathername");
        st_dob=getIntent().getExtras().getString("i_dob");
        st_age=getIntent().getExtras().getString("i_age");
        st_gender=getIntent().getExtras().getString("i_gender");
        st_address=getIntent().getExtras().getString("i_address");
        st_mobileno=getIntent().getExtras().getString("i_mobileno");
        st_fromdate=getIntent().getExtras().getString("i_fromdate");
        st_selectmonth=getIntent().getExtras().getString("i_selectmonth");
        st_todate=getIntent().getExtras().getString("i_todate");
        st_amount=getIntent().getExtras().getString("i_amount");
        st_selectbuspasstype=getIntent().getExtras().getString("i_selectbuspasstype");
        st_source=getIntent().getExtras().getString("i_source");
        st_destination=getIntent().getExtras().getString("i_destination");
        generateQrCode();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*boolean save;
                String result;
                try {
                    save = QRGSaver.save(savePath, combined, bitmap, QRGContents.ImageType.IMAGE_JPEG);
                    result = save ? "Image Saved" : "Image Not Saved";
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                try {
                    bitmap = TextToImageEncode(combined);
                    qrImage.setImageBitmap(bitmap);
                    String path = saveImage(bitmap);  //give read write permission
                    Toast.makeText(GenerateQrCodeActivity.this, "QRCode saved to -> "+path, Toast.LENGTH_LONG).show();
                    startPayment();
                    finish();

                    /*Intent intent=new Intent(GenerateQrCodeActivity.this,MainActivity.class);
                    startActivity(intent);*/

                } catch (WriterException e) {
                    e.printStackTrace();
                }


            }
        });
    }



    public void generateQrCode(){
        combined=st_name + "\n" + st_fathername +"\n" + st_dob + "\n" +st_age+ "\n" +st_gender+ "\n"
                    +st_address+ "\n" +st_fromdate+ "\n" +st_selectmonth+ "\n"+st_source+"\n"+st_destination+"\n"
                    +st_todate+ "\n" +st_amount+"\n"+st_mobileno;

        if (combined.length() > 0) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    combined, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                qrImage.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.v(TAG, e.toString());
            }
        } else {
            Toast.makeText(this, "required", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            try {
                f.createNewFile();   //give read write permission
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }
    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


    private void startPayment() {
        amount= Integer.parseInt(st_amount);
        Checkout checkout=new Checkout();
        checkout.setImage(R.mipmap.ic_launcher);  //logo
        final Activity activity=this;

        try {
            JSONObject options=new JSONObject();

            options.put("description","order #123456");
            options.put("currency","INR");
            options.put("amount",amount*100);
            //Toast.makeText(activity, ""+st_amount, Toast.LENGTH_SHORT).show();
            checkout.open(activity,options);

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Transaction Successful", Toast.LENGTH_LONG).show();
        finish();
        Intent intent=new Intent(GenerateQrCodeActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Transaction Unsuccessful Please try again later", Toast.LENGTH_LONG).show();
        finish();
        Intent intent=new Intent(GenerateQrCodeActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
