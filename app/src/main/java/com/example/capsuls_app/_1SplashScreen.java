package com.example.capsuls_app;

import android.content.Intent;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class _1SplashScreen extends AppCompatActivity {
    public CircleImageView logo;
    public TextView appName;

    private void SetAnimation(){
        logo.setY(-4200);
        appName.setX(-3500);
        logo.animate().translationYBy(4200).alpha(1).setDuration(3500);
        appName.animate().translationXBy(3500).alpha(1).rotation(360).setDuration(3500);
    }
    private void ApplyThread(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(4000);
                    Intent intent = new Intent(getApplicationContext(),_2Login.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_splash_screen);

        logo=findViewById(R.id.loto);
        appName=findViewById(R.id.applicationName);
        SetAnimation();
        ApplyThread();
        //Call Code
    }
}