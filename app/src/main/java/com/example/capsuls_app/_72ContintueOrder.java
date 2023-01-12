package com.example.capsuls_app;

import static com.example.capsuls_app.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;

public class _72ContintueOrder extends AppCompatActivity {

    public BootstrapEditText provinceField,regionField,streetField,buildingField;
    public String province,region,street,building;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_72_contintue_order);
        provinceField = findViewById(id.addressField1);
        regionField = findViewById(id.addressField2);
        streetField = findViewById(id.addressField3);
        buildingField = findViewById(id.addressField4);



    }


    public void onContinue(View view) {
        province = provinceField.getText().toString();
        region = regionField.getText().toString();
        street = streetField.getText().toString();
        building = buildingField.getText().toString();
        String fullAddress = province+"-"+regionField.getText().toString()+"-"+
                streetField.getText().toString()+"-"+buildingField.getText().toString()+"-";

        if(province.equals("") || region.equals("") || street.equals("") || building.equals("")){
            Toast.makeText(this, "Please Fill All Fields ", Toast.LENGTH_SHORT).show();
        }else{
            _7NewOrder.currentOrder.Address = fullAddress;
            Intent goToSaveOrder=new Intent(getApplicationContext(), _8SelectPayment.class);
            startActivity(goToSaveOrder);

        }



    }
}