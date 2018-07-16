package com.vrishankgupta.triposse;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Shivam Gupta on 17-06-2018.
 */

public class SplashActivity extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    String token="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences preferences = getSharedPreferences(getString(R.string.preference),MODE_PRIVATE);

        token = preferences.getString("token", "");
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                if(token.equals(""))
                {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }

                else {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
