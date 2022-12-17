package com.example.capsuls_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class _10OrderDetails extends AppCompatActivity {
    public TextView screenTitle,itemsTitleLabel,itemsQuantityLabel;
    public ListView orderListView;
    public ArrayAdapter checkedOutListAdapter;
    public Button assignOrderButton;
    public ArrayList<String> namesofSelectedItems;
    public ArrayList<Integer>calculateQuantity;
    public static HashMap<String,Integer> cartOfItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_10_order_details);
        screenTitle=findViewById(R.id.tilteOrderName3);
        itemsTitleLabel=findViewById(R.id.itemsNameLabel3);
        itemsQuantityLabel=findViewById(R.id.itemsquantityLabel3);
        orderListView=findViewById(R.id.orederDetailsListView3);
        assignOrderButton=findViewById(R.id.buttonAssignButton3);

        namesofSelectedItems=new ArrayList<String>();
        calculateQuantity=new ArrayList<Integer>();

        FirebaseApp.initializeApp(_10OrderDetails.this);
        FirebaseDatabase.getInstance().getReference().child("Order").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    VMOrder fetchedItem=child.getValue(VMOrder.class);
                    cartOfItems=fetchedItem.CartOfItems;
                    for(String i : cartOfItems.keySet()){
                        namesofSelectedItems.add(i);
                        calculateQuantity.add(cartOfItems.get(i));
                        //calculateTotalPrice+=_8SelectDrugs.cartOfItems.get(i)*_8SelectDrugs.pricing.get(i);
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

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Display displayManeger=getWindowManager().getDefaultDisplay();
        Point displayPoint=new Point();
        displayManeger.getSize(displayPoint);
        int width=displayPoint.x;
        int height=displayPoint.y;
        //Apply Responsive Design Pattern
        ApplyResponsiveDesign(width,height);
    }

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