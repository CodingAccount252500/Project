package com.example.capsuls_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.bumptech.glide.Glide;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class _5Profile extends AppCompatActivity {
    public ImageView userImage;
    public BootstrapEditText PhoneNumber,Name,Pio;
    public  String phones,namePatient,pioPatient;
    public Uri imageUri;
    public BootstrapButton uploadImage;
    public CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5_profile);
        FirebaseApp.initializeApp(_5Profile.this);
        uploadImage=findViewById(R.id.uploadProfileImage);
        userImage=findViewById(R.id.profile_image);
        Name = findViewById(R.id.name);
        Pio  = findViewById(R.id.pioODescription);
        PhoneNumber=findViewById(R.id.phone);
        circleImageView = findViewById(R.id.profile_image);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getImageFromGallery=new Intent();
                getImageFromGallery.setType("image/*");
                getImageFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(getImageFromGallery,1);
            }
        });
        LoadMyAccountInfo();
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LoadMyAccountInfo();
    }

    public  void LoadMyAccountInfo(){
        _2Login.RecallUserInfo();
        if(_2Login.currentUser.ProfileImagePath.equals("")){
            userImage.setImageResource(R.drawable.man);
        }else{
            Glide.with(getApplicationContext()).load(_2Login.currentUser.ProfileImagePath).into(userImage);
        }
        PhoneNumber.setText(_2Login.currentUser.PhoneNumber);
        Name.setText(_2Login.currentUser.Name);
        Pio.setText(_2Login.currentUser.MedicalRecord);
    }
    public void UpdateProfile(View view) {
        phones=PhoneNumber.getText().toString();
        pioPatient=Pio.getText().toString();
        namePatient=Name.getText().toString();
        //Fetch User Info By Object Id

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
                        if(imageUri==null){
                            DbRef.child(user.getKey()) .child("Phone").setValue(phones);
                            DbRef.child(user.getKey()) .child("MedicalRecord").setValue(pioPatient);
                            DbRef.child(user.getKey()) .child("Name").setValue(namePatient);
                            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();

                            finish();

                        }else{
                            StorageReference fileUploadingReference = storageRef.child(System.currentTimeMillis()+"."+getFileExtention(imageUri));
                            fileUploadingReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    fileUploadingReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            DbRef.child(_2Login.currentUserId) .child("ProfileImagePath").setValue(uri.toString());
                                            DbRef.child(_2Login.currentUserId) .child("Phone").setValue(phones);
                                            DbRef.child(_2Login.currentUserId) .child("MedicalRecord").setValue(pioPatient);
                                            DbRef.child(_2Login.currentUserId) .child("Name").setValue(namePatient);
                                            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();

                                            finish();


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

                                    Toast.makeText(_5Profile.this, "Upload Operation Failed ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(_5Profile.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data.getData()!=null ){
            try {
                imageUri=data.getData();
                circleImageView.setImageURI(imageUri);

            }catch (Exception exception){

            }
        }else {
            Toast.makeText(this, "Smothing Worng !", Toast.LENGTH_SHORT).show();
        }


    }
    public String getFileExtention(Uri muri){
        ContentResolver mContentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(mContentResolver.getType(muri));

    }

    public void showmore(View view) {
        Intent moveToMoreFields = new Intent(_5Profile.this,_52ProfileSecond.class);
        startActivity(moveToMoreFields);
    }
}