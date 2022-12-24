package com.example.capsuls_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class _52ProfileSecond extends AppCompatActivity {
    public BootstrapEditText userEmail,userSSN,userPassword,userAge;
    public Spinner diseaseType,genderType;
    public ArrayAdapter spinnerAdapter,spinner2Adapter;
    public String diseaseTypeString,userGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_52_profile_second);
        DefineAllScreenObject();
        diseaseType=findViewById(R.id.spn_trainType2);
        genderType=findViewById(R.id.genderField);
        String [] categorySpinnerItems=getResources().getStringArray(R.array.diseses);
        String [] gendersType=getResources().getStringArray(R.array.gender);
        spinnerAdapter =
                new ArrayAdapter(_52ProfileSecond.this,android.R.layout.simple_spinner_item,categorySpinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diseaseType.setAdapter(spinnerAdapter);
        diseaseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diseaseTypeString=categorySpinnerItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diseaseTypeString = "";
            }
        });
        spinner2Adapter =
                new ArrayAdapter(_52ProfileSecond.this,android.R.layout.simple_spinner_item,gendersType);
        spinner2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderType.setAdapter(spinner2Adapter);
        genderType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userGender=gendersType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                userGender = "";
            }
        });
        userEmail.setText(_2Login.currentUser.Email);
        userSSN.setText(_2Login.currentUser.SSN);
        userPassword.setText(_2Login.currentUser.Password);


    }
    public void DefineAllScreenObject() {

        userEmail=findViewById(R.id.newUserEmailField);
        userSSN=findViewById(R.id.newUserSSNField);
        userPassword=findViewById(R.id.newUserPasswordField);
        userAge=findViewById(R.id.ageField);

    }

    public void back(View view) {
        finish();
    }

    public void OnContinueSave(View view) {

        //Fetch User Info By Object Id
        FirebaseApp.initializeApp(_52ProfileSecond.this);
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference().child("User");
        FirebaseStorage mfirebaseStorage=FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = mfirebaseStorage.getReference();
        final Query AccountInfoQuery = DbRef.orderByChild("Email").equalTo(_2Login.currentUser.Email);
        AccountInfoQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user : snapshot.getChildren()) {
                    VMUsers fetchedItem = user.getValue(VMUsers.class);
                    if (fetchedItem.Email.equals(_2Login.currentUser.Email)) {

                            DbRef.child(user.getKey()) .child("Email").setValue(userEmail.getText().toString());
                            DbRef.child(user.getKey()) .child("Password").setValue(userPassword.getText().toString());
                            DbRef.child(user.getKey()) .child("SSN").setValue(userSSN.getText().toString());
                            DbRef.child(user.getKey()) .child("Age").setValue(userAge.getText().toString());
                            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                            finish();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(_52ProfileSecond.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}