package com.example.capsuls_app;

import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;


import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class _3Register extends AppCompatActivity {
    public BootstrapEditText userName,userEmail,userPhone,userSSN,userPassword,userAge,userGender;
    public FirebaseAuth mAuth;
    public DatabaseReference databaseReference;
    public ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_3_register);
        DefineAllScreenObject();
    }
    public void OnContinueSignUpEvent(View view) {
        FirebaseApp.initializeApp(_3Register.this);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String name,email,phone,ssn,password,age,gender;
        mAuth = FirebaseAuth.getInstance();
        name=userName.getText().toString();
        email=userEmail.getText().toString();
        phone=userPhone.getText().toString();
        ssn=userSSN.getText().toString();
        password=userPassword.getText().toString();
        age=userAge.getText().toString();
        gender=userGender.getText().toString();

        if(name.equals("")||email.equals("")||phone.equals("")||ssn.equals("")||ssn.length()<10||
                password.equals("")||age.equals("")||gender.equals("")){
            Toast.makeText(getApplicationContext(), "Please Fill The Required Data \uD83D\uDE4F", Toast.LENGTH_SHORT).show();
        }else{
            showIndeterminateProgressDialog();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            VMUsers person=new VMUsers(name,email,phone,0.0,0.0,ssn,false,"",gender,age,"user",password);
                                            databaseReference.child("User").push().setValue(person);
                                            progressDialog.hide();
                                            Toast.makeText(getApplicationContext(), "Please Verify Your email ✌️ ", Toast.LENGTH_SHORT).show();
                                            finish();

                                        }
                                    }
                                });
                            } else {
                                // If sign in fails, display a message to the user.
                                progressDialog.hide();
                                Log.w("Data", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed."+task.getException(),
                                        Toast.LENGTH_LONG).show();


                            }
                        }
                    });


        }
    }

    public void DefineAllScreenObject() {
        userName=findViewById(R.id.newUserNameField);
        userPhone=findViewById(R.id.newUserPhoneField);
        userEmail=findViewById(R.id.newUserEmailField);
        userSSN=findViewById(R.id.newUserSSNField);
        userPassword=findViewById(R.id.newUserPasswordField);
        userAge=findViewById(R.id.ageField);
        userGender=findViewById(R.id.genderField);
    }
    private void showIndeterminateProgressDialog() {
        progressDialog = new ProgressDialog(_3Register.this);
        // Set horizontal progress bar style.
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Set progress dialog icon.
        progressDialog.setIcon(R.drawable.clock);
        // Set progress dialog title.
        progressDialog.setTitle("Please Waiting");
        // Whether progress dialog can be canceled or not.
        progressDialog.setCancelable(false);
        // When user touch area outside progress dialog whether the progress dialog will be canceled or not.
        progressDialog.setCanceledOnTouchOutside(false);
        // Set progress dialog message.
        progressDialog.setMessage("Waiting For Complete Register...");
        // Popup the progress dialog.
        progressDialog.show();
        // Create a new thread object.
    }
}