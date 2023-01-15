package com.example.capsuls_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class _8SelectPayment extends AppCompatActivity {
    BootstrapEditText card,cvv,exp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_8_select_drugs);
        FirebaseApp.initializeApp(getApplicationContext());
        card=findViewById(R.id.f1);
        cvv=findViewById(R.id.f2);
        exp=findViewById(R.id.f3);



    }

    public void PayAndSendOrder(View view) {
        ProgressDialog loadingDialog;
        loadingDialog = new ProgressDialog(_8SelectPayment.this);
        loadingDialog.setMessage("Please Wait ... ");
        loadingDialog.show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Payment");
        final Query gameQuery = ref;
        gameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot item : snapshot.getChildren()) {
                        VMPayment fetchedRecorde = item.getValue(VMPayment.class);
                        if (fetchedRecorde.cardNumber.equals(card.getText().toString())&& fetchedRecorde.ccv2.equals(cvv.getText().toString())
                                && fetchedRecorde.holder.equals(exp.getText().toString())) {
                            _7NewOrder.currentOrder.IsPaid = true;
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            reference.child("Orders").push().setValue(_7NewOrder.currentOrder)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            loadingDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                                            Intent goToSaveOrder=new Intent(_8SelectPayment.this,_4Main_Activity.class);
                                            startActivity(goToSaveOrder);
                                        }
                                    });


                        }else{
                            loadingDialog.dismiss();
                            Toast.makeText(_8SelectPayment.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                loadingDialog.dismiss();
                Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void PayLater(View view) {
        _7NewOrder.currentOrder.IsPaid = false;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Orders").push().setValue(_7NewOrder.currentOrder)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                        Intent goToSaveOrder=new Intent(_8SelectPayment.this,_4Main_Activity.class);
                        startActivity(goToSaveOrder);
                    }
                });
    }
}