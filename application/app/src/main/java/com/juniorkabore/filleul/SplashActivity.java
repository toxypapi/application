package com.juniorkabore.filleul;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class SplashActivity extends AppCompatActivity {
    private static boolean mDebugLog = true;
    private static String mDebugTag = "LoginUsingActivity";




    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;

    void logDebug(String msg) {
        if (mDebugLog) {
            Log.d(mDebugTag, msg);
        }
    }
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            Thread.sleep(1000);
            Intent i = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }*/





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final SessionManager mSessionManager = new SessionManager(SplashActivity.this);
        logDebug("onCreate mSessionManager " + mSessionManager);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(mSessionManager.isLoggedIn() && mSessionManager.isFinish()) {
                    logDebug("onCreate user is logedind  " + mSessionManager);
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                } /*else if(mSessionManager.isLoggedIn() && (mSessionManager.isFinish() == false)){
                    logDebug("onCreate user is logedind  " + mSessionManager);
                    Intent i = new Intent(SplashActivity.this, Je_suis.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }*/else
                    logDebug("onCreate  user not loged in ");
                    // FlurryAgent.logEvent("SplahScreenCompleted");
                    Intent mIntent = new Intent(SplashActivity.this, MainActivity.class);
                    // Intent mIntent=new Intent(SplashActivity.this,
                    // MainActivity.class);
                    startActivity(mIntent);
                    finish();
                
            }
        }, SPLASH_TIME_OUT);



    }

















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
