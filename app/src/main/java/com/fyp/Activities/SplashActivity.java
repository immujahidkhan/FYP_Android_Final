package com.fyp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fyp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                //startActivity(new Intent(SplashActivity.this, SignUpServiceProviderActivity.class));
                //startActivity(new Intent(SplashActivity.this, AddProductActivity.class));
                //startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }, 2500);
    }
}