package com.example.capsuls_app;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class _12ResetPassword extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    public BootstrapEditText userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_12_reset_password);
        userEmail = findViewById(R.id.ResetEmailField);
        FirebaseApp.initializeApp(_12ResetPassword.this);
        firebaseAuth= FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();

    }

    public void  OnContinueEvent(View view){
        if(userEmail.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Fill your email It's required" , Toast.LENGTH_SHORT).show();
        }
        else {

            firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(getApplicationContext(),"Check For your E-mail" , Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(_12ResetPassword.this,_2Login.class));

                    }else {
                        Toast.makeText(getApplicationContext(),"Wrong Email" , Toast.LENGTH_LONG).show();


                    }
                }
            });
        }
    }
}