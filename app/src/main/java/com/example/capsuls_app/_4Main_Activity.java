package com.example.capsuls_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;

public class _4Main_Activity extends AppCompatActivity implements LocationListener {
    public static double lat,lon;
    public LocationManager myLocationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_main);

    }

    public void CreateOrder(View view) {
        getLocation();

        Intent moving=new Intent(_4Main_Activity.this, _7NewOrder.class);
        startActivity(moving);
    }

    public void History(View view) {
        Intent moving=new Intent(_4Main_Activity.this,_6History.class);
        startActivity(moving);
    }

    public void EditProfile(View view) {
        Intent moving=new Intent(_4Main_Activity.this,_5Profile.class);
        startActivity(moving);
    }

    public void Logout(View view) {
        Intent moving=new Intent(_4Main_Activity.this,_2Login.class);
        _2Login.currentUser=null;
        _2Login.currentUserId="";
        startActivity(moving);
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            myLocationManager=(LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,_4Main_Activity.this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lat=location.getLatitude();
        lon=location.getLongitude();

    }
}