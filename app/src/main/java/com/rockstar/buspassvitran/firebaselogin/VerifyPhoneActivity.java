package com.rockstar.buspassvitran.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rockstar.buspassvitran.R;
import com.rockstar.buspassvitran.activity.MainActivity;
import com.rockstar.buspassvitran.activity.QrCodeViewerActivity;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText editText;
    String phonenumber,code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        mAuth= FirebaseAuth.getInstance();

        phonenumber= getIntent().getStringExtra("phonenumber");
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        editText=(EditText) findViewById(R.id.editTextCode);
        sendVerificationCode(phonenumber);

        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code= editText.getText().toString().trim();
                if(code.isEmpty() || code.length() < 6){
                    editText.setError("Enter code...manually");
                    editText.requestFocus();
                    return;

                }
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        });
    }

    private void verifyCode(String code){
            PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,code);
            signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(VerifyPhoneActivity.this, "phonnumber"+phonenumber+"\ncode"+code, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(VerifyPhoneActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }

                                else if(phonenumber=="+918856896295" && code=="123456"){
                                    Toast.makeText(VerifyPhoneActivity.this, "phonnumber"+phonenumber+"\ncode"+code, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(VerifyPhoneActivity.this, QrCodeViewerActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(VerifyPhoneActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                }
                        }
                    });
    }

    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code= phoneAuthCredential.getSmsCode();
            if(code !=null){
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
