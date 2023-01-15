package com.example.capsuls_app;

import android.content.Intent;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class _1SplashScreen extends AppCompatActivity {
    public CircleImageView logo;
    public TextView appName;
    public FirebaseAuth mAuth;
    public DatabaseReference databaseReference;
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
                    /*VMPharma pharma = new VMPharma("Rasya Pharmacy","","Amman ",
                            "065353461","Ali bani hassan",32.0272776,35.8419917,"Dr-Nuha Ayman");
                    FirebaseApp.initializeApp(_1SplashScreen.this);
                    mAuth = FirebaseAuth.getInstance();
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Pharma").push().setValue(pharma);*/
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