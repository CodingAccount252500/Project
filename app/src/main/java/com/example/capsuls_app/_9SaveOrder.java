package com.example.capsuls_app;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class _9SaveOrder extends AppCompatActivity {
    public TextView screenTitle,itemsTitleLabel,itemsQuantityLabel;
    public ListView orderListView;
    public ArrayAdapter checkedOutListAdapter;
    public Button assignOrderButton;
    public ArrayList<String> namesofSelectedItems;
    public double calculateTotalPrice;
    public ArrayList<Integer>calculateQuantity;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9_save_order);
        //Define All Object
        screenTitle=findViewById(R.id.tilteOrderName2);
        itemsTitleLabel=findViewById(R.id.itemsNameLabel2);
        itemsQuantityLabel=findViewById(R.id.itemsquantityLabel2);
        orderListView=findViewById(R.id.orederDetailsListView2);
        assignOrderButton=findViewById(R.id.buttonAssignButton2);

        //Get Actual Screen Design
        Display displayManeger=getWindowManager().getDefaultDisplay();
        Point displayPoint=new Point();
        displayManeger.getSize(displayPoint);
        int width=displayPoint.x;
        int height=displayPoint.y;
        //Apply Responsive Design Pattern
        ApplyResponsiveDesign(width,height);

        //get The Orders
        // 1- store the items name in special array
        namesofSelectedItems=new ArrayList<String>();
        calculateQuantity=new ArrayList<Integer>();
        calculateTotalPrice=0;
        for(String i : _8SelectDrugs.cartOfItems.keySet()){
            namesofSelectedItems.add(i);
            calculateQuantity.add(_8SelectDrugs.cartOfItems.get(i));
            calculateTotalPrice+=_8SelectDrugs.cartOfItems.get(i)*_8SelectDrugs.pricing.get(i);
        }
        checkedOutListAdapter=new ArrayAdapter
                (getApplicationContext(),R.layout.orderitemsdetails,R.id.itemsNameListLabel,namesofSelectedItems){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view= super.getView(position, convertView, parent);
                TextView quantity=view.findViewById(R.id.itemsquantityListLabel8585);
                quantity.setText(calculateQuantity.get(position)+"");
                return view;
            }
        };
        orderListView.setAdapter(checkedOutListAdapter);
        assignOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(_9SaveOrder.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Done ?")
                        .setMessage("Confirm Order ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ProgressDialog createNewAdminDialog;
                                createNewAdminDialog = new ProgressDialog(_9SaveOrder.this);
                                createNewAdminDialog.setMessage("Please Wait ... ");
                                createNewAdminDialog.show();
                                _7NewOrder.currentOrder.CartOfItems=_8SelectDrugs.cartOfItems;
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                reference.child("Orders").push().setValue(_7NewOrder.currentOrder)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                                                Intent goToSaveOrder=new Intent(getApplicationContext(),_4Main_Activity.class);
                                                startActivity(goToSaveOrder);
                                            }
                                        });




                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // here we don't do any thing
                            }
                        }).show();

            }
        });
    }
    //Apply Responsive Design
    public void ApplyResponsiveDesign(int width,int height){
        LinearLayout.LayoutParams applayDesignPaternforTextView=
                new LinearLayout.LayoutParams((int)(width*0.45),(int)(height*0.10));
        applayDesignPaternforTextView.setMargins(0,(int)(height*0.01),0,0);

        itemsTitleLabel.setLayoutParams(applayDesignPaternforTextView);
        itemsQuantityLabel.setLayoutParams(applayDesignPaternforTextView);
        LinearLayout.LayoutParams listviewDesign=
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)(height*0.590));
        applayDesignPaternforTextView.setMargins(0,(int)(height*0.03),0,0);
        orderListView.setLayoutParams(listviewDesign);

        LinearLayout.LayoutParams buttonDesign=
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)(height*0.0950));
        applayDesignPaternforTextView.setMargins(0,(int)(height*0.0450),0,0);
        assignOrderButton.setLayoutParams(buttonDesign);
        screenTitle.setLayoutParams(buttonDesign);

    }
}