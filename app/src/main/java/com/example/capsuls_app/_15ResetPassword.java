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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class _15ResetPassword extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    public BootstrapEditText userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_15_reset_password);
        userEmail = findViewById(R.id.ResetEmailField);
        firebaseAuth= FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
    }

    public void  OnContinueEvent(View view){
        if(userEmail.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Fill your email" , Toast.LENGTH_SHORT).show();
        }
        else {
            firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(getApplicationContext(),"Check For your E-mail" , Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(_15ResetPassword.this,_2Login.class));

                    }else {
                        Toast.makeText(getApplicationContext(),"Error with your email" , Toast.LENGTH_SHORT).show();


                    }
                }
            });
        }
    }
}