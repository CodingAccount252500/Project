package com.example.capsuls_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class _7NewOrder extends AppCompatActivity implements LocationListener {
    DatePickerDialog       picker ;
    EditText               eText  ;
    public Uri             imageUri;
    public BootstrapButton uploadImage;
    public BootstrapEditText editTextPio;
    public static double lat,lon;
    public LocationManager myLocationManager;
    ToggleButton toggle ;
    String date,note;
    boolean isLocationToggle;
    public  static  VMOrder currentOrder;
    public  static  String  createdOrderId;

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            myLocationManager=(LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,_7NewOrder.this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lat=location.getLatitude();
        lon=location.getLongitude();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7_new_order);
        eText=(EditText) findViewById(R.id.editTextGetDate);
        toggle = (ToggleButton) findViewById(R.id.locationToggle);
        editTextPio = findViewById(R.id.pioODescription);
        eText.setInputType(InputType.TYPE_NULL);
        isLocationToggle=false;
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(_7NewOrder.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    isLocationToggle=true;
                    getLocation();
                } else {
                    // The toggle is disabled
                    isLocationToggle=false;
                }
            }
        });
        uploadImage=findViewById(R.id.uploadProfileImage);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getImageFromGallery=new Intent();
                getImageFromGallery.setType("application/*");
                getImageFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(getImageFromGallery,1);
            }
        });
    }

    public void StartNewOrder(View view){
        date =  eText.getText().toString();
        note =  editTextPio.getText().toString();
        FirebaseStorage mfirebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageRef = mfirebaseStorage.getReference();
        /*if(date.equals("")||note.equals("")||lat == 0 || lon == 0 || imageUri == null){
            Toast.makeText(_7NewOrder.this,"Please Fill All Required Data",Toast.LENGTH_LONG).show();
        }else{*/
            StorageReference fileUploadingReference = storageRef.child(System.currentTimeMillis()+"."+getFileExtention(imageUri));
            fileUploadingReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    fileUploadingReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                              currentOrder = new VMOrder(lat,lon,uri.toString(),false,note,""
                            ,"UnCompleted",date,new HashMap<String,Integer>(),_2Login.currentUserId);
                            Toast.makeText(getApplicationContext(), "Please Select Your Needs", Toast.LENGTH_SHORT).show();
                            Intent goToSaveOrder=new Intent(getApplicationContext(),_8SelectDrugs.class);
                            startActivity(goToSaveOrder);
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(_7NewOrder.this, "Upload Operation Failed ", Toast.LENGTH_SHORT).show();
                }
            });
        //}

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data.getData()!=null ){
            try {
                imageUri=data.getData();
                //userImage.setImageURI(imageUri);
            }catch (Exception exception){

            }
        }else {
            Toast.makeText(this, "Something Wrong !", Toast.LENGTH_SHORT).show();
        }

    }
    public String getFileExtention(Uri muri){
        ContentResolver mContentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(mContentResolver.getType(muri));

    }
}