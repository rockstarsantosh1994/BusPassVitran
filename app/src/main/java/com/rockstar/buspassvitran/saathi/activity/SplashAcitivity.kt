package com.rockstar.buspassvitran.saathi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.asmobisoft.digishare.CommonMethods
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.rockstar.buspassvitran.R
import com.rockstar.buspassvitran.activity.MainActivity
import java.io.IOException

class SplashAcitivity : AppCompatActivity() {

    /** Duration of wait **/
    private var SPLASH_DISPLAY_LENGTH:Long=2000;
    private var mDelayHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_acitivity)

        FirebaseApp.initializeApp(this@SplashAcitivity)

        //Initialize the Handler
        mDelayHandler = Handler()
        val mRunnable: Runnable = Runnable {

            //Log.e("firebase_id", FirebaseInstanceId.getInstance().getToken(getString(R.string.SENDER_ID), "FCM"))
            //Firebase token generation
            Thread(Runnable {
                try {
                    /*CommonMethods.setPreference(applicationContext,AllKeys.FCM_TOKEN,
                        FirebaseInstanceId.getInstance().getToken(getString(R.string.SENDER_ID), "FCM"))
                   */ Log.e("firebaseid", FirebaseInstanceId.getInstance().getToken(getString(R.string.SENDER_ID), "FCM"))
                    CommonMethods.setPreference(this@SplashAcitivity, CommonMethods.GCM_TOKEN, FirebaseInstanceId.getInstance().getToken(getString(R.string.SENDER_ID), "FCM"))
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }).start()

            if (!isFinishing) {
                if(CommonMethods.getPrefrence(applicationContext, CommonMethods.USER_ID).equals("DNF")){
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DISPLAY_LENGTH)
    }
}
