package com.example.capsuls_app;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;


public class _2Login extends AppCompatActivity {
    public TextView emailLabel,passwordLabel,signupLabel;
    public BootstrapEditText emailField,passwordField;
    private FirebaseAuth mAuth;
    public static String currentUserId="";
    public static VMUsers currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_login);

        DefineObject();
    }
    public void DefineObject(){
        emailField=findViewById(R.id.emailTextField);
        passwordField=findViewById(R.id.passwordTextField);
        emailLabel=findViewById(R.id.emailTextView);
        passwordLabel=findViewById(R.id.passwordTextView);
    }


    public void OnSignupLabelClicked(View view) {
        Intent moveToCreateAccount=new Intent(_2Login.this,_3Register.class);
        startActivity(moveToCreateAccount);
    }

    public void MoveBetweenScreens(Context packageContext, Class<?> cls){
        Intent moveToCenterScreen=new Intent(packageContext,cls);
        startActivity(moveToCenterScreen);
    }

    public void OnResetClicked(View view) {
        Intent moveToCreateAccount=new Intent(_2Login.this, _12ResetPassword.class);
        startActivity(moveToCreateAccount);
    }
    public void OnLoginButtonClicked(View view) {
        //get the email and password then Authenticate user using firebase
        FirebaseApp.initializeApp(_2Login.this);
        mAuth = FirebaseAuth.getInstance();
        String userEmail,userPassword;
        userEmail=emailField.getText().toString();
        userPassword=passwordField.getText().toString();
        if(userEmail.equals("")||userPassword.equals("")){
            Toast.makeText(getApplicationContext(), "Please Enter Email And Password", Toast.LENGTH_SHORT).show();
        }else{
            //Continue Login Operation
                //FirebaseApp.initializeApp(_2Login.this);
                DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference().child("User");
                final Query AccountInfoQuery = DbRef.orderByChild("Email").equalTo(userEmail);
                AccountInfoQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot center : snapshot.getChildren()) {
                            VMUsers fetchedUser = center.getValue(VMUsers.class);
                            if (fetchedUser.Email.equals(userEmail)
                                    && fetchedUser.Password.equals(userPassword)
                            ) {
                                currentUser=fetchedUser;
                                currentUserId=center.getKey();
                                if(fetchedUser.AccountTypeId.equalsIgnoreCase("user")){
                                    Intent moveToCenterScreen=new Intent(_2Login.this,_4Main_Activity.class);
                                    startActivity(moveToCenterScreen);
                                }else{
                                    Intent moveToCenterScreen=new Intent(_2Login.this,_11PendingOrderList.class);
                                    startActivity(moveToCenterScreen);
                                }

                            }else{
                                Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(_2Login.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        public  static  void RecallUserInfo(){
            //Continue Login Operation
            //FirebaseApp.initializeApp(_2Login.this);
            DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference().child("User");
            final Query AccountInfoQuery = DbRef.orderByChild("Email").equalTo(currentUser.Email);
            AccountInfoQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot center : snapshot.getChildren()) {
                        VMUsers fetchedUser = center.getValue(VMUsers.class);
                        if (fetchedUser.Email.equals(currentUser.Email)

                        ) {
                            currentUser=fetchedUser;
                            currentUserId=center.getKey();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //Toast.makeText(, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
